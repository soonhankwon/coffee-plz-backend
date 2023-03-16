# coffee-plz-backend
> 커피샵 온라인 주문 시스템 개인 프로젝트 **coffee-plz**
- [Coffee-Plz Notion Page](https://www.notion.so/coffee-plz-java11-backend-46f6d2efb26f45f39ec42010399f7728)

## 구현 기능
> * 커피 메뉴 목록 조회
> * 포인트 충전하기, 포인트 이력 기록
> * 커피 주문 & 결제하기
> * 유저 결제 시 주문서 조회
> * 지난 7일간 인기메뉴 목록 조회
> * 데이터 수집 플랫폼으로 주문 데이터 실시간 전송

## ERD
<details>
<summary><strong> Diagram </strong></summary>
<div markdown="1">       

![coffee_plz_erd](https://user-images.githubusercontent.com/113872320/217168702-03d1db0b-3aee-4932-87a7-f73034332697.png)

</div>
</details>

## API
- [Swagger UI](http://localhost:8080/swagger-ui/index.html)
- [상세 API Notion Page](https://amusing-child-e0e.notion.site/Coffee-Plz-API-84a27c008dc943409c70df9d6015275e)

## TECH STACK
- Java 11
- SpringBoot 2.7.7
- MySQL 8.0.31
- QueryDsl 5.0.0
- JPA
- Redis 7.0.8
- Redisson 3.19.1
- Kafka 3.1.2
- Docker
- Swagger 3.0
- JUnit5
- Thymeleaf
- HTML5, CSS3, Javascript
---

## ☕️ 프로젝트 설계 내용 및 이유 
> - 이 프로젝트는 커피샵 온라인 주문 시스템입니다. 커피 주문 및 포인트로 결제가 가능한 시스템입니다. 
> - 오프라인(키오스크) & 시스템 확장성을 고려해서 설계했습니다.
> - 특히 동시성 이슈, 데이터 일관성과 애플리케이션이 다수의 서버에서 동작하는 것을 고려해서 시스템을 구현 및 핵심문제 사항들을 개선했습니다.
### API 문서 자동화
> - springdoc-openapi-ui를 사용하여 Swagger3.0 적용
> - API 문서의 자동화로 API Test의 편의성 증대 및 체계화된 API 문서 작성

### Entity Modeling

#### Table

> - item : 커피 메뉴 이름, 가격 정보 테이블
> - order : 주문한 유저 아이디, 주문 타입, 총 가격, 주문 상태, 주문 시간 테이블
> 
>       - 주문 타입과 상태는 enum 클래스 사용 (enum.String)
>       - 주문 타입 : 테이크아웃, 매장
>       - 주문 상태 : 주문 대기, 주문 완료, 결제 완료
>       
> - order_item : item 과 order는 N:N 관계 → 연결 테이블
> 
>       - 메뉴 가격 변경 대비로 주문한 메뉴 아이디, 가격, 수량 저장
>       - 추가로 메뉴의 사이즈 저장
>   
> - user : 회원 정보, 포인트 정보 테이블
> 
> - point_history : 포인트 사용 이력 테이블
> 
>       - 1 (user) : N (point_history) 관계
>       - 포인트 사용 타입 enum 클래스 사용 (enum.String)
>       - 사용 타입 : 충전, 사용

#### Data Type

> - BIGINT : 아이디, 가격, 포인트 → 컬럼의 중요성 및 확장성을 고려하여 사용
> - TIMESTAMP : 여러 타임존에서 같은 시간 보장
> - VARCHAR : 불필요한 메모리 낭비 방지를 위해, 입력 데이터 (ex password) 에 따른 VARCHAR 길이 설정

### Unit Test
> * 각 기능별로 Junit5와 Mockito를 사용하여 단위테스트 검증

### 인기메뉴 조회
> * QueryDSL을 사용하여 7일간의 주문 데이터에서 가장 주문량이 많은 세가지 아이템의 id, name, price 를 조회
> * inner_join을 피하기 위해서 item에 있던 size를 order_item 테이블로 이동하여 쿼리 성능 개선

<details>
<summary><strong> Code </strong></summary>
<div markdown="1">       

````java
public List<Long> favoriteItems() {
        LocalDate weekBefore = LocalDate.now().minusDays(7);
        LocalDate yesterday = LocalDate.now();

        return queryFactory.select(orderItem.item.id)
                .from(orderItem)
                .where(orderItem.createdAt.between(weekBefore.atStartOfDay(), yesterday.atStartOfDay()))
                .groupBy(orderItem.item.id)
                .orderBy(orderItem.quantity.sum().desc())
                .limit(3)
                .fetch();
    }
````
</div>
</details>
 
> * Redis를 활용한 인기 메뉴 조회
> * @Cacheable 어노테이션을 사용하여 인기 메뉴 조회 시 캐싱된 데이터 조회
> * Redis 서버가 다운되거나, 캐시가 없어졌을 경우 같은 연산을 하여 조회 정보를 리턴하고, 결과를 다시 캐싱
> * 스케쥴을 활용하여 매일 밤 12시 인기 메뉴 업데이트 & Redis에 캐시 저장
> * 캐시 유효기간을 2일로 설정하여 인기 메뉴 업데이트 시 캐시가 없는 경우를 방지

### 데이터 수집 플랫폼(Mock API)으로 데이터 전송
> * 요구사항 : 단방향 통신, 이벤트가 발생 && 트랜잭션이 성공했을 경우만 데이터 전송, 실시간 데이터 전송
> * Application Event Publisher를 사용하여 OrderEvent가 발생할 경우 주문 데이터 전송
> * Event Listener를 사용하여 주문 트랜잭션 성공 후 비동기로 주문 데이터를 전송하도록 설계
> * @Async를 사용한 주문 데이터 비동기 처리 후 전송으로 핵심 로직과 디커플링 
> * 추후 시스템이 실시간으로 대량의 데이터를 처리할 것이라고 예상하여 Kafka 분산 메세징 시스템 적용
> * OrderEvent가 발생하면 producer가 orderData TOPIC을 브로커에게 PUSH, consumer는 브로커로부터 수신된 메세지를 PULL
> * pub/sub 메세징 시스템으로 비동기 처리를 통한 핵심로직과의 디커플링 및 확장성 확보, 높은 데이터량을 처리하도록 설계
> * 추가로 수평적 확장이 용이한 Kafka를 사용함으로써 확장에 따른 서버 비용 개선

## 핵심 문제해결 전략 및 분석
        
### 동시성 제어 이슈
#### 포인트 충전 및 결제 시 동시성 제어를 위해 낙관적 락 사용
> * 포인트 충전 및 결제는 동일 데이터에 같은 사용자가 동시에 접근하는 동시성 이슈 발생
> * JPA @Version을 활용한 낙관적락을 적용하여 포인트 충전 및 결제 시 애플리케이션 단계에서 동시성 이슈 개선
#### 주문 시 동시성 제어를 위해 Redisson 분산락 사용
> * 서비스 요구사항 : 이전 주문 미결제시 새로운 주문 생성 불가        
> * 주문의 경우 @Version으로 주문생성의 동시성 이슈 제어가 불가능
> * 동시성 처리를 위해서는 락 획득 이후 트랜잭션이 시작되어야 하고, 커밋 후 락이 해제되어야 하기 때문에 락 내부에서 트랜잭션이 동작하도록 적용
> * @Transactional은 동일 클래스 내부 메서드 호출에는 적용되지 않기 때문에, 별도의 TransactionSevice를 구현하여 내부 메서드에 적용 
> * 락을 userId로 설정하여 동시에 주문이 들어 왔을 경우에도 데이터의 일관성과 순차적 처리 및 예외처리를 보장
#### 인기메뉴 캐시 업데이트 시 Redisson 분산락 사용
> * 서비스 요구사항 : 다수의 서버를 사용하는 서비스
> * 다수의 서버가 밤12시에 인기메뉴 캐시 업데이트 메서드에 접근하여 동시에 업데이트하는 비효율적 상황 발생
> * wait time을 0으로 설정한 분산락을 사용하여 락을 획득한 하나의 서버만 캐시 업데이트를 하도록 개선        
### 외부 API와의 의존성 이슈
> * 주문은 시스템에서 핵심적인 서비스이기 때문에, 데이터 수집 플랫폼의 서버 상황에 영향을 받으면 안된다.
> * @Async를 사용하여 Mock Server(데이터 수집 플랫폼)로의 주문 데이터 전송 시 비동기화 기술 사용하여 개선
> * Kafka 분산 메세징 시스템을 사용하여 주문 데이터 전송 시 비동기화 & 시스템 확장성(Scale-out) 구축
> * 데이터 수집 플랫폼의 상황에 구애받지 않는 시스템 구축 & Consumer는 서버 장애 해결 후 메세지 수신 가능
> * pub/sub 메세징 시스템으로 분산 및 실시간 데이터의 높은 처리량과 신뢰성, 높은 데이터량 처리 가능
### 불필요한 코드 개선
#### Cascade 옵션을 활용한 주문과 주문아이템 데이터를 동시 저장
> * 주문 생성 시 order(주문ID, 총액) 데이터 저장 코드와 order_item(주문메뉴, 가격, 수량) 데이터 저장 코드가 따로 존재
> * CascadeType.ALL 옵션을 추가하여 order와 order_item이 같이 저장되도록 개선
