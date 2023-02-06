# coffee-plz-backend
> 커피샵 온라인 주문 시스템 개인 프로젝트 **coffee-plz**
- [Coffee-Plz Notion Page](https://www.notion.so/coffee-plz-java11-backend-46f6d2efb26f45f39ec42010399f7728)

## API
- [Swagger UI](http://localhost:8080/swagger-ui/index.html)

## STACK
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
### 구현 기능
> * 커피 메뉴 목록 조회
> * 포인트 충전하기, 포인트 이력 기록
> * 커피주문 & 결제하기
> * 지난 7일간 인기메뉴 목록 조회 (자동 업데이트)
> * 데이터 수집 플랫폼으로 주문 데이터 실시간 전송

## ☕️ 프로젝트 설계 내용 및 이유 
> 이 프로젝트는 커피샵 온라인 주문 시스템입니다. 유저의 주문과 포인트 충전 및 결제의 동시성 이슈를 해결하도록 설계한 프로젝트입니다. 

### TDD
> * 각 기능별로 Junit5와 Mockito를 사용하여 단위테스트 작성

### Redis
> * Redis Cache를 활용한 인기 메뉴 조회를 캐싱 

## 핵심 문제해결 전략 및 분석

### 동시성 제어 이슈 
> * JPA @Version을 활용한 낙관적락을 적용하여 포인트 충전 및 결제 시 동시성 제어 
> * Redis 라이브러리인 Redisson을 활용한 인기메뉴 업데이트, 주문 시 분산락 적용

### 외부 API와의 의존성 이슈
> * 주문은 핵심적인 서비스이기 때문에, 데이터 수집 플랫폼의 서버 상황에 영향을 받으면 안된다.
> * @Async를 사용하여 Mock Server(데이터 수집 플랫폼)로의 주문 데이터 전송 시 비동기화 기술 사용
> * Kafka 메세지 브로커를 사용하여 주문 데이터 전송 시 비동기화 & 시스템 확장성 구축
