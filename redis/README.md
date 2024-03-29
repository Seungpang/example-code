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


### AOF(Append Only File)를 사용한 백업

- 모든 쓰기 요청에 대한 로그를 저장
- 재시작 시 AOF에 기록된 모든 동작을 재수행해서 데이터를 복구


### AOF 사용의 장점

- 모든 변경사항이 기록되므로 RDB 방식 대비 안정적으로 데이터 백업 가능
- AOF 파일은 append-only 방식이므로 백업 파일이 손상될 위험이 적음
- 실제 수행된 명령어가 저장되어 있으므로 사람이 보고 이해할 수 있고 수정도 가능

### AOF 사용의 단점

- RDB 방식보다 파일 사이즈가 커짐
- RDB 방식 대비 백업&복구 속도가 느림(백업 성능은 fsync 정책에 따라 조절 가능)

### AOF 설정

```java
AOF 사용(기본값은 no)
appendonly yes

AOF 파일 이름
appendfilename appendonly.aof

fsync 정책 설정(always, everysec, no)
appendfsync everysec
```

### fsync 정책(appendfsync 설정 값)

- fsync() 호출은 OS에게 데이터를 디스크에 쓰도록 함
- 가능한 옵션과 설명
  - always: 새로운 커멘드가 추가될 때마다 수행. 가장 안전하지만 가장 느림
  - everysec: 1초마다 수행. 성능은 RDB 수준에 근접
  - no: OS에 맡김. 가장 빠르지만 덜 안전한 방법(커널마다 수행 시간이 다를 수 있음)

### AOF 관련 개념

- Log rewriting: 최종 상태를 만들기 위한 최소한의 로그만 남기기 위해 일부를 새로 씀 (Ex: 1개의 key값을 100번 수정해도 최종 상태는 1개이므로 SET 1개로 대체 가능)
- Multi Part AOF: Redis 7.0부터 AOF가 단일 파일에 저장되지 않고 여러 개가 사용됨
  - base file: 마지막 rewrite 시의 스냅샷을 저장
  - incremental file: 마지막으로 base file이 생성된 이후의 변경사항이 쌓임
  - manifest file: 파일들을 관리하기 위한 메타 데이터를 저장


### Redis replication(복제)

- 백업만으로는 장애 대비에 부족함(백업 실패 가능성, 복구에 소요되는 시간)
- Redis도 복제를 통해 가용성을 확보하고 빠른 장애조치가 가능
- master가 죽었을 경우 replica 중 하나를 master로 전환해 즉시 서비스 정상화 가능
- 복제본(replica)은 read-only 노드로 사용 가능하므로 traffic 분산도 가능

### Redis 복제 사용

- Replica 노드에서만 설정을 적용해 master-replica 복제 구성 가능

### Redis Sentinel

- Redis에서 HA(high availability)를 제공하기 위한 장치
- master-replica 구조에서 master가 다운 시 replica를 master로 승격시키는 auto-failover를 수행

### Redis Sentinel 실제 구성도

- Sentinel 노드는 3개 이상으로 구성 (Quorum때문)
- Sentinel들은 서로 연결되어 있음
- Sentinel들은 Redis master와 replica를 모니터링
- Client는 Sentinel을 통해 Redis에 접근

### Redis Sentinel 특징

- SDOWN(Subject down)과 ODOWN(Objective down)의 2가지 판단이 있음
  - SDOWN: Sentinel 1대가 down으로 판단(주관적)
  - ODOWN: 정족수가 충족되어 down으로 판단(객관적)
- master 노드가 down 된걸로 판단되기 위해서는 Sentinel 노드들이 정족수(Quorum)을 충족해야함
- 클라이언트는 Sentinel을 통해 master의 주소를 얻어내야 함

### 확장성이란?

- 소프트웨어나 서비스의 요구사항 수준이 증가할 때 대응할 수 있는 능력
- 주로 규모에 대한 확장성을 뜻함(데이터 크기, 요청 트래픽 등)
- 수직 확장(scale-up)과 수평 확장(scale-out)이 사용됨

### 수평 확장(scale-out)

- 처리 요소(ex: 서버)를 여러개 두어서 작업을 분산
- 무중단 확장이 가능
- 이론적으로는 무한대로 확장이 가능

### 분산 시스템에 따라오는 문제

- 부분 장애
- 네트워크 실패
- 데이터 동기화
- 로드밸런싱(또는 discovery)
- 개발 및 관리의 복잡성

### 분산 시스템의 적용

- 분산 시스템으로 인한 trade-off를 판단해서 적합하다면 사용
- 서비스 복잡도와 규모의 증가로 분산은 피할 수 없는 선택
- 분산 시스템의 구현체들은 세부적인 부분에서 튜닝이 가능하게 옵션이 제공됨

  (즉, 분산 시스템의 장단점을 세부적으로 조절 가능)


### Redis Cluster란?

- Redis Cluster가 제공하는 것
  - 여러 노드에 자동적인 데이터 분산
  - 일부 노드의 실패나 통신 단절에도 계속 작동하는 가용성
  - 고성능을 보장하면서 선형 확장성을 제공

### Redis Cluster 특징

- full-mesh 구조로 통신
- cluster bus라는 추가 채널(port) 사용
- gossip protocol 사용
- hash slot을 사용한 키 관리
- DB0만 사용 가능
- multi key 명령어가 제한됨
- 클라이언트는 모든 노드에 접속

### Sentinel과의 차이점

- 클러스터는 데이터 분산(샤딩)을 제공함
- 클러스터는 자동 장애조치를 위한 모니터링 노드(Sentinel)를 추가 배치할 필요가 없음
- 러스터에서는 multi key 오퍼레이션이 제한됨
- Sentinel은 비교적 단순하고 소규모의 시스템에서 HA(고가용성)가 필요할 때 채택


### 데이터를 분산하는  기준

- 특정 key의 데이터가 어느 노드(shard)에 속할 것인지 결정하는 메커니즘이 있어야 함
- 보통 분산 시스템에서 해싱이 사용된다.
- 단순 해싱으로는 노드의 개수가 변할 때 모든 매핑이 새로 계산되어야 하는 문제가 있음

### Hash Slot을 이용한 데이터 분산

- Redis는 16384개의 hash slot으로 key 공간을 나누어 관리
- 각 키는 CRC16 해싱 후 16384로 modulo 연산을 해 각 hash slot에 매핑
- hash slot은 각 노드들에게 나누어 분배됨

### 클라이언트의 데이터 접근

- 클러스터 노드는 요청이 온 key에 해당하는 노드로 자동 redirect를 해주지 않음
- 클라이언트는 MOVED 에러를 받으면 해당 노드로 다시 요청해야 함

### 클러스터를 사용할 때의 성능

- 클라이언트가 MOVED 에러에 대해 재요청을 해야 하는 문제

→ 클라이언트(라이브러리)는 key-node 맵을 캐싱하므로 대부분의 경우 발생하지 않음

- 클라이언트는 단일 인스턴스의 Redis를 이용할 때와 같은 성능으로 이용 가능
- 분산 시스템에 성능은 데이터 일관성(consistency)과 trade-off가 있음

→ Redis Cluster는 고성능의 확장성을 제공하면서 적절한 수준의 데이터 안정성과 가용성을 유지하는 것을 목표로 설계됨

### 클러스터의 데이터 일관성

- Redis Cluster는 strong consistency를 제공하지 않음
- 높은 성능을 위해 비동기 복제를 하기 때문

### 클러스터의 가용성 -auto failover

- 일부 노드(master)가 실패(또는 네트워크 단절)하더라도 과반수 이상의 master가 남아있고, 사라진 master의 replica들이 있다면 클러스터는 failover되어 가용한 상태가 된다.
- node timeout동안 과반수의 Master와 통신하지 못한 master는 스스로 error state로 빠지고 write 요청을 받지 않음

### 클러스터의 가용성 -replica migration

- replica가 다른 master로 migrate 해서 가용성을 높인다.

### 클러스터에서는 DB0만 사용 가능

- Redis는 한 인스턴스에 여러 데이터베이스를 가질 수 있으며 디폴트는 16

→ 설정) databases 16

- multi DB는 용도별로 분리해서 관리를 용이하게 하기 위한 목적
- 클러스터에서는 해당 기능을 사용할 수 없고 DB0으로 고정된다.

### Multi key operation 사용의 제약

- key들이 각각 다른 노드에 저장되므로 MSET과 같은 multi-key operation은 기본적으로 사용할 수 없다.
- 같은 노드 안에 속한 key들에 대해서는 multi-key operation이 가능
- hash tags 기능을 사용하면 여러 key들을 같은 hash slot에 속하게 할 수 있음.

→ key 값 중 {} 안에 들어간 문자열에 대해서만 해싱을 수행하는 원리

```java
MSET {user:a}:age 20 {user:a}:city seoul
```

### 클라이언트 구현의 강제

- 클라이언트는 클러스터의 모든 노드에 접속해야 함
- 클라이언트는 redirect 기능을 구현해야 함(MOVED 에러의 대응)
- 클아이언트 구현이 잘 된 라이브러리가 없는 환경도 있을 수 있음


### 클러스터 설정 파일

- cluster-enabled <yes/no>: 클러스터 모드로 실행할지 여부를 결정
- cluster-config-file <filename>: 해당 노드의 클러스터를 유지하기 위한 설정을 저장하는 파일로, 사용자가 수정하지 않음
- cluster-node-timeout <milliseconds>
  - 특정 노드가 정상이 아닌 것으로 판단하는 기준 시간
  - 이 시간동안 감지되지 않는 master는 replica에 의해 failover가 이루어짐
- cluster-replica-validity-factor <factor>
  - master와 통신한지 오래된 replica가 failover를 수행하지 않게 하기 위한 설정
  - (cluster-node-timeout * factor)만큼 master와 통신이 없었던 replica는 failover 대상에서 제외된다.
- cluster-migration-barrier <count>:
  - 한 master가 유지해야 하는 최소 replica의 개수
  - 이 개수를 충족하는 선에서 일부 replica는 replica를 가지지 않은 master의 replica로 migrate될 수 있다.
- cluster-require-full-coverage <yes/no>:
  - 일부 hash slot이 커버되지 않을 때 write 요청을 받지 않을지 여부
  - no로 설정하게 되면 일부 노드에 장애가 생겨 해당 hash slot이 정상 작동하지 않더라ㄱ도 나머지 hash slot에 대해서는 작동하도록 할 수 있다.
- cluster-allow-reads-when-down <yes/no>:
  - 클러스터가 정상 상태가 아닐 때도 read 요청은 받도록 할지 여부
  - 어플리케이션에서 read 동작의 consistency가 중요치 않은 경우에 yes로 설정할 수 있다.
