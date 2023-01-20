# Redis 정리
### Redis Data Type: Strings

- 가장 기본적인 데이터 타입으로 제일 많이 사용됨
- 바이트 배열을 저장(binary-safe)
- 바이너리로 변환할 수 있는 모든 데이터를 저장 가능(JPG와 같은 파일 등)
- 최대 크기는 512MB

| 명령어 | 기능 | 예제 |
| --- | --- | --- |
| SET | 특정 키에 문자열 값을 저장한다. | SET say hello |
| GET | 특정 키의 문자열 값을 얻어온다. | GET say |
| INCR | 특정 키의 값을 Integer로 취급하여 1 증가시킨다. | INCR mycount |
| DECR | 특정 키의 값을 Integer로 취급하여 1 감소시킨다. | DECR mycount |
| MSET | 여러 키에 대한 값을 한번에 저장한다. | MSET mine milk yours coffe |
| MGET | 여러 키에 대한 값을 한번에 얻어온다. | MGET mine yours |

작은 사이즈로 여러번 호출하면 네트워크 비용이 많이 든다.

패킷에 헤더가 붙는다 low level에서 추가적으로 발생하는 작업이 발생

### Redis Data Type: Lists

- Linked-list 형태의 자료구조(인덱스 접근은 느리지만 데이터 추가/삭제가 빠름)
- Queue와 Stack으로 사용할 수 있음

| 명령어 | 기능 | 예제 |
| --- | --- | --- |
| LPUSH | 리스트의 왼쪽(head)에 새로운 값을 추가한다. | LPUSH mylist apple |
| RPUSH | 리스트의 오른쪽(tail)에 새로운 값을 추가한다. | RPUSH mylist banana |
| LLEN | 리스트에 들어있는 아이템 개수를 반환한다. | LLEN mylist |
| LRANGE | 리스트의 특정 범위를 반환한다. | LRANGE mylist 0 -1 |
| LPOP | 리스트의 왼쪽(head)에서 값을 삭제하고 반환한다. | LPOP mylist |
| RPOP | 리스트의 오른쪽(tail)에서 값을 삭제하고 반환한다. | RPOP mylist |

### Redis Data Type: Sets

- 순서가 없는 유니크한 값의 집합
- 검색이 빠름
- 개별 접근을 위한 인덱스가 존재하지 않고, 집합 연산이 가능(교집합, 합집합 등)

| 명령어 | 기능 | 예제 |
| --- | --- | --- |
| SADD | Set에 데이터를 추가한다. | SADD myset apple |
| SREM | Set에서 데이터를 삭제한다. | SREM myset apple |
| SCARD | Set에 저장된 아이템 개수를 반환한다. | SCARD myset |
| SMEMBERS | Set에 저장된 아이템들을 반환한다. | SMEMBERS myset |
| SISMEMBER | 특정 값이 Set에 포함되어 있는지를 반환한다. | SISMEMBER myset apple |

ex) 웹페이지에서 서비스가 특정 시간동안 유효한 쿠폰을 발급, 유저는 단 한번만 발급 그 유저가 이번시간대에 쿠폰을 발급받았는지 확인하기 위해 Set에 저장해놓으면 쉽게 확인 가능

SISMEMBER는 데이터가 얼마가 있든 속도가 동일하도록 보장해준다.

### Redis Data Type: Hashes

- 하나의 key 하위에 여러개의 field-value 쌍을 저장
- 여러 필드를 가진 객체를 저장하는 것으로 생각할 수 있음
- HINCRBY 명령어를 사용해 카운터로 활용 가능

각각의 필드를 따로 사용할 때 사용성이 커진다.

카운터로 활용가능하다.

| 명령어 | 기능 | 예제 |
| --- | --- | --- |
| HSET | 한개 또는 다수의 필드에 값을 저장한다. | HSET user1 name bear age 10 |
| HGET | 특정 필드의 값을 반환한다. | HGET user1 name |
| HMGET | 한개 이상의 필드 값을 반환한다. | HMGET user1 name age |
| HINCRBY | 특정 필드의 값을 Integer로 취급하여 지정한 숫자를 증가시킨다. | HINCRBY user1 viewcount |
| HDEL | 한개 이상의 필드를 삭제한다. | HDEL user1 name age |

### Redis Data Type: Sorted Sets

- Set과 유사하게 유니크한 값의 집합
- 각 값은 연관된 score를 가지고 정렬되어 있음
- 정렬된 상태이기에 빠르게 최소/최대값을 구할 수 있음
- 순위 계산, 리더보드 구현 등에 활용

| 명령어 | 기능 | 예제 |
| --- | --- | --- |
| ZADD | 한개 또는 다수의 값을 추가 또는 업데이트한다. | ZADD myrank 10 apple 20 banana |
| ZRANGE | 특정 범위의 값을 반환한다. (오름차순으로 정렬된 기준) | ZRANGE myrank 0 1 |
| ZRANK | 특정 값의 위치(순위)를 반환한다. (오름차순으로 정렬된 기준) | ZRANK myrank apple |
| ZREVRANK | 특정 값의 위치(순위)를 반환한다.(내림차순으로 정렬된 기준) | ZREVRANK myrank apple |
| ZREM | 한개 이상의 값을 삭제한다. | ZREM myrank apple |

### Redis Data Type: Bitmaps

- 비트 벡터를 사용해 N개의 Set을 공간 효율적으로 저장
- 하나의 비트맵이 가지는 공간은 4,294,967,295(2^32-1)
- 비트 연산 가능 (2일 연속으로 방문한 사람들을 계산할 경우 유용)

ex) 오늘 방문한 사람들을 알고 싶을 경우

| 명령어 | 기능 | 예제 |
| --- | --- | --- |
| SETBIT | 비트맵의 특정 오프셋에 값을 변경한다. | SETBIT visit 10 1 |
| GETBIT | 비트맵의 특정 오프셋의 값을 반환한다. | GETBIT visit 10 |
| BITCOUNT | 비트맵에서 set(1) 상태인 비트의 개수를 반환한다. | BITCOUNT visit |
| BITOP | 비트맵들간의 비트 연산을 수행하고 결과를 비트맵에서 저장한다. | BITOP AND result today yesterday |

### Redis Data Type: HyperLogLog

- 유니크한 값의 개수를 효율적으로 얻을 수 있음
- 확률적 자료구조로서 오차가 있으며, 매우 큰 데이터를 다룰 때 사용
- 18,446,744,073,709,551,616(2^64)개의 유니크 값을 계산 가능
- 12KB까지 메모리를 사용하며 0.81% 오차율을 허용

대규모의 데이터를 처리할 때 100% 정확한 값보다는 99% 정확도라면 처리할 수 있는 경우가 많음

| 명령어 | 기능 | 예제 |
| --- | --- | --- |
| PFADD | HyperLogLog에 값들을 추가한다. | PFADD visit Jay Peter Jane |
| PFCOUNT | HyperLogLog에 입력된 값들의 cardinality(유일값의 수)를 반환한다. | PFCOUNT visit |
| PFMERGE | 다수의 HyperLogLog를 병합한다. | PFMERGE result visit1 visit2 |
|  |  |  |


### Redis 라이브러리 사용

- Lettuce: 가장 많이 사용되는 라이브러리로, Spring Data Redis에 내장되어 있음
- Spring Data Redis는 Redis Template이라는 Redis 조작의 추상 레이러를 제공함

### 세션

- 네트워크 상에서 두 개 이상의 통신장치간에 유지되는 상호 연결
- 연결된 일정 시간 동안 유지되는 정보를 나타냄
- 적용 대상에 따라 다른 의미를 가짐

### 분산 환경에서의 세션 처리

- Server는 세션 정보를 저장해야 함
- Server가 여러 대라면 최초 로그인한 Server가 아닌 Server는 세션 정보를 알지 못함
- 세션 정보를 Server간에 공유할 방법이 필요 (Session Clustering)

### 분산 환경에서의 세션 처리 - RDB 사용

- 관계형 데이터 모델이 필요한가?
- 영속성이 필요한 데이터인가?
- 성능 요구사항을 충족하는가?

### 분산 환경에서의 세션 처리 - Redis 사용

- 세션 데이터는 단순 key-value 구조
- 세션 데이터는 영속성이 필요 없음
- 세션 데이터는 변경이 빈번하고 빠른 액세스 속도가 필요

### 세션 관리를 위한 서버의 역할

- 세션 생성: 요청이 들어왔을 때 세션이 없다면 만들어서 응답에 set-cookie로 넘겨줌
- 세션 이용: 요청이 들어왔을 때 세션이 있다면 해당 세션의 데이터를 가져옴
- 세션 삭제: 타임아웃이나 명시적인 로그아웃 API를 통해 세션을 무효화 함
- 모든 요청에 대해서 cookie를 파싱해 확인하고, 세션 생성시에는 set-cookie값까지 설정해야 함

### HttpSession

- 세션을 손쉽게 생성하고 관리할 수 있게 해주는 인터페이스
- UUID로 세션 ID를 생성
- JSESSIONID라는 이름의 cookie를 설정해 내려줌

### Web 로그인 세션

- Web 상에서 특정 유저가 로그인했음을 나타내는 정보
- 브라우저는 Cookie를, 서버는 해당 Cookie에 연관된 세션 정보를 저장한다.
- 유저가 로그아웃하거나 세션이 만료될 때 까지 유지되어 유저에 특정한 서비스 가능

### 캐싱

- Cache: 성능 향상을 위해 값을 복사해놓는 임시 기억 장치
- Cache에 복사본을 저장해놓고 읽음으로서 속도가 느린 장치로의 접근 횟수를 줄임
- Cache의 데이터는 원본이 아니며 언제든 사라질 수 있음

데이터 일관성 문제도 신경써야함

### 캐싱 관련 개념들

- 캐시 적중 (Cache Hit): 캐시에 접근해 데이터를 발견함
- 캐시 미스 (Cache Miss): 캐시에 접근했으나 데이터를 발견하지 못함
- 캐시 삭제 정책(Eviction Policy): 캐시의 데이터 공간 확보를 위해 저장된 데이터를 삭제
- 캐시 전략: 환경에 따라 적합한 캐시 운영 방식을 선택할 수 있음(Cache-Aside, Write-Through..)

### Cache-Aside(Lazy Loading)

- 항상 캐시를 먼저 체크하고, 없으면 원본(ex. DB)에서 읽어온 후에 캐시에 저장함
- 장점: 필요한 데이터만 캐시에 저장되고, Cache Miss가 있어도 치명적이지 않음
- 단점: 최초 접근은 느림, 업데이트 주기가 일정하지 않기 때문에 캐시가 최신 데이터가 아닐 수 있음

### Write-Through

- 데이터를 쓸 때 항상 캐시를 업데이트하여 최신 상태를 유지함
- 장점: 캐시가 항상 동기화되어 있어 데이터가 최신이다.
- 단점: 자주 사용하지 않는 데이터도 캐시되고, 쓰기 지연시간이 증가한다.

### Write-Back

- 데이터를 캐시에만 쓰고, 캐시의 데이터를 질정 주기로 DB에 업데이트
- 장점: 쓰기가 많은 경우 DB 부하를 줄일 수 있음
- 단점: 캐시가 DB에 쓰기 전에 장애가 생기면 데이터 유실 가능

### 데이터 제거 방식

- 캐시에서 어떤 데이터를 언제 제거할 것인가?
- Expiration: 각 데이터에 TTL(Time-To-Live)을 설정해 시간 기반으로 삭제
- Eviction Algorithm: 공간을 확보해야 할 경우 어떤 데이터를 삭제할지 결정하는 방식
    - LRU(Least Recently Used): 가장 오랫동안 사용되지 않은 데이터를 삭제
    - LFU(Least Frequently Used): 가장 적게 사용된 데이터를 삭제(최근에 사용되었더라도)
    - FIFO(First in First Out):먼저 들어온 데이터를 삭제

### Redis를 사용한 캐싱

- Cache-Aside 전략으로 구현

⇒ 요청에 대해 캐시를 먼저 확인하고, 없으면 원천 데이터 조회 후 캐시에 저장

### Spring의 캐시 추상화

- CacheManager를 통해 일반적인 캐시 인터페이스 구현(다양한 캐시 구현체가 존재)
- 메소드에 캐시를 손쉽게 적용 가능

### 리더보드

- 게임이나 경쟁에서 상위 참가자의 랭킹과 점수를 보여주는 기능
- 순위로 나타낼 수 있는 다양한 대상에 응용(최다 구매 상품, 리뷰 순위 등)

### 리더보드의 동작(API 관점)

- 점수 생성/업데이트 ⇒ ex: SetScore(userId, score)
- 상위 랭크 조회(범위 기반 조회) ⇒ ex: getRange(1~10)
- 특정 대상 순위 조회(값 기반 조회) ⇒ ex: getRank(userId)

### 데이터 구조와 성능 문제

- 관계형 DB 등의 레코드 구조를 사용했을 때
- 업데이트: 한 행에만 접근하므로 비교적 빠름
- 랭킹 범위나 특정 대상의 순위 조회

⇒ 데이터를 정렬하거나 count()등의 집계 연산을 수행해야 하므로 데이터가 많아질수록 속도가 느려짐

### Redis를 사용했을 때의 장점

- 순위 데이터에 적합한 Sorted-Set의 자료구조를 사용하면 score를 통해 자동으로 정렬됨
- 용도에 특화된 오퍼레이션(Set 삽입/업데이트, 조회)이 존재하므로 사용이 간담함
- 자료구조의 특성으로 데이터 조회가 빠름(범위 검색, 특정 값의 순위 검색)
- 빈번한 액세스에 유리한 In-memory DB의 속도

### Pub/Sub 패턴

- 메시징 모델 중의 하나로 발행과 구독 역할로 개념화 한 형태
- 발행자와 구독자는 서로에 대한 정보 없이 특정 주제(토픽 or 채널)를 매개로 송수신

### 메시징 미들웨어 사용의 장점

- 비동기: 통신의 비동기 처리
- 낮은 결합도: 송신자와 수신자가 직접 서로 의존하지 않고 공통 미들웨어에 의존
- 탄력성: 구성원들간에 느슨한 연결로 인해 일부 장애가 생겨도 영향이 최소화됨

### Redis의 Pub/Sub 특징

- 메시지가 큐에 저장되지 않음
- Kafka의 컨슈머 그룹같은 분산처리 개념이 없음
- 메시지 발행 시 push 방식으로 subscriber들에게 전송
- subscriber가 늘어날수록 성능이 저하

### Redis의 Pub/Sub의 유즈케이스

- 실시간으로 빠르게 전송되어야 하는 메시지
- 메시지 유실을 감내할 수 있는 케이스
- 최대 1회 전송(at-most-once) 패턴이 적합한 경우
- Subscriber들이 다양한 채널을 유동적으로 바꾸면서 한시적으로 구독하는 경우

### Redis의 백업과 장애 복구

백업 → InMemory에 있는 데이터를 디스크로 저장, 레디스가 내려갔다 올라가도 유지

### RDB(Redis Database)를 사용한 백업

- 특정 시점의 스냅샷으로 데이터 저장
- 재시작시 RDB 파일이 있으면 읽어서 복구

### RDB 사용의 장점

- 작은 파일 사이즈로 백업 파일 관리가 용이(원격지 백업, 버전 관리 등) → AOF방식에 대비해서
- fork를 이용해 백업하므로 서비스 중인 프로세스는 성능에 영향 없음
- 데이터 스냅샷 방식이므로 빠른 복구가 가능
  - 파일에 저장된걸 그대로 메모리에 올림

### RDB 사용의 단점

- 스냅샷을 저장하는 시점 사이의 데이터 변경사항은 유실될 수 있음
- fork를 이용하기 때문에 시간이 오래 걸릴 수 있고, CPU와 메모리 자원을 많이 소모
- 데이터 무결성이나 정합성에 대한 요구가 크지 않은 경우 사용 가능 (마지막 백업 시 에러 발생 등의 문제)

### RDB 설정

- 설정파일이 없어도 기본값으로 RDB를 활성화되어 있음
- 설정 파일 만드려면 템플릿을 받아서 사용(https://redis.io/docs/management/config/)

```java
저장 주기 설정(EX: 60초마다 10개 이상의 변경이 있을 때 수행
save 60 10

스냅샷을 저장할 파일 이름
dbfilename dump.rdb

수동으로 스냅샷 저장
bgsave
```

### Docker를 사용해 Redis 설정 파일 적용하기

- docker run 사용 시 -v 옵션을 이용해 디렉토리 또는 파일을 마운팅할 수 있음
- redis 이미지 실행 시 redis-server에 직접 redis 설정파일 경로 지정 가능
