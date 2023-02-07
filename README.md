# coffee-plz-backend
> 커피샵 온라인 주문 시스템 개인 프로젝트 **coffee-plz**
- [Coffee-Plz Notion Page](https://www.notion.so/coffee-plz-java11-backend-46f6d2efb26f45f39ec42010399f7728)

## 구현 기능
> * 커피 메뉴 목록 조회
> * 포인트 충전하기, 포인트 이력 기록
> * 커피 주문 & 결제하기
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
---

## ☕️ 프로젝트 설계 내용 및 이유 
> - 이 프로젝트는 커피샵 온라인 주문 시스템입니다. 커피 주문 및 포인트로 결제가 가능한 시스템입니다. 
> - 오프라인(키오스크) & 사업의 확장성을 고려해서 설계했습니다.
> - 특히 동시성 이슈, 데이터 일관성과 애플리케이션이 다수의 서버에서 동작하는 것을 고려해서 구현했습니다.

### Unit Test
> * 각 기능별로 Junit5와 Mockito를 사용하여 단위테스트 검증

### 인기메뉴 조회
> * QuertDsl을 사용하여 일주일 간의 주문 데이터에서 가장 주문량이 많은 세가지 아이템의 id, name, price 를 조회
> * inner_join을 피하기 위해서 item에 있던 size를 order_item 테이블로 이동

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
 
> * Redis Cache를 활용한 인기 메뉴 조회를 캐싱
> * 스케쥴을 활용하여 하루에 한번 인기 메뉴 업데이트

## 핵심 문제해결 전략 및 분석

### 동시성 제어 이슈 
> * JPA @Version을 활용한 낙관적락을 적용하여 포인트 충전 및 결제 시 동시성 제어 
> * Redis 라이브러리인 Redisson을 활용한 인기메뉴 업데이트, 주문 시 분산락 적용

### 외부 API와의 의존성 이슈
> * 주문은 핵심적인 서비스이기 때문에, 데이터 수집 플랫폼의 서버 상황에 영향을 받으면 안된다.
> * @Async를 사용하여 Mock Server(데이터 수집 플랫폼)로의 주문 데이터 전송 시 비동기화 기술 사용
> * Kafka 메세지 브로커를 사용하여 주문 데이터 전송 시 비동기화 & 시스템 확장성 구축
