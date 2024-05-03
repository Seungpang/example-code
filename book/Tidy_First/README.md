### 보호 구문
```java
if (조건)
    if (다른 조건 부정)
        ... 코드 ...
```
아래와 같이 변경 할 수 있음
```java
if (조건 부정) return
if (다른 조건) return
... 코드 ...
```

다만, 보호 구문을 남용하지는 말자
조건에 딱 부합하는 경우
```java
if (조건)
        ...루틴의 나머지 모든 코드..
```

정리하고 싶은데 정리할 수 없는 코드
```java
if (조건)
    ...코드...
... 다른 코드...
```

### 안 쓰는 코드
지워 버리자! 끝

### 대칭으로 맞추기
코드를 읽을 때 기존과 다르다면 `다른 동작의 코드겠지`라고 예단한다. 여기서 차이가 있다 보니,
같은 일임에도 다른 일인 것처럼 뜻이 흐려진다.

한 가지 방식을 선택해서 정하자. 다른 방식으로 작성한 코드를 선택한 방식으로 고치자


### 새로운 인터페이스로 기존 루틴 부르기
호출하고 싶은 인터페이스를 새롭게 구현해서 호출하자. 

새로 만든 인터페이스는 그저 기존 인터페이스를 호출하는 것으로 구현할 수 있다.  
(기존 인터페이스를 호출하는 코드를 새 인터페이스를 호출하도록 모두 이전한 후에는 이전 인터페이스를 제거하고 
새 인터페이스가 직접 루틴을 구현하도록 변경할 수 있다.)

아래의 경우 톨로 인터페이스 적용때와 비슷한 느낌을 받음
+ 거꾸로 코딩하기: 루틴의 마지막 줄부터 시작
+ 테스트 우선 코딩: 테스트부터 작성하여 통과 요건을 정함
+ 도우미 설계: 특정 업무를 해 주는 루틴, 객체, 서비스가 있다면 나머지 작업은 쉬움

### 읽는 순서
완벽한 순서는 없다. 때로는 기본 요소를 먼저 이해한 다음 구성 방법을 이해하고 싶을 때가 있고, API를 먼저 이해한 다음 세부 구현을 이해하고 싶을 때도 있다.

스스로가 코드를 읽어 왔을 테니 최근 경험을 살려 판단해라

### 응집도를 높이는 배치
가장 좋은 코드 정리법
```
결합도 제거 비용 + 변경 비용 < 결합도에 따른 비용 + 변경비용
```
아래와 같은 이유로 결합도 제거가 어려울 수 있음

+ 당장 어떻게 해야 할지 모른다면 결합도 제거가 곤란할 수 있다.
+ 할 수 있더라도 지금 당장은 시간적 여유가 없다면 결합도 제거는 부담스러운 시간 투자가 된다.
+ 팀이 이미 충분한 변경을 수행하고 있다면 결합도를 제거하는 일이 팀원 간의 잠재적 갈등으로 번질 수 있다.

응집도가 좋아지면 결합도 역시 덩달아 좋아진다.


### 선언과 초기화를 함께 옮기기
변수 선언과 초기화 위치는 종종 서로 떨어져 있기도 한다. 변수 이름에서 힌트를 얻어 프로그램에서 역할을 알게 되지만, 변수 초기화는 이름이 주는 의미를 더 강화한다.
타입이 포함된 선언과 초기화 코드가 떨어져 있기라도 하면 읽어 내기는 더 어려워 진다.


### 설명하는 변수
어렵고 크고 복잡한 코드의 표현식을 이해했다면 전체에서 일부 표현식을 추출한 후, 표현식의 의도가 드로나도록 변수 이름을 만들어 할당하자

```java
    public static void main(String[] args) {
        String name = "Kim Seungrae";
        System.out.println("Kim Seungrae".split(" ")[1].toUppercase());
    }
```

```java
    public static void main(String[] args) {
        String[] name = "Kim Seungrae".split(" ");
        String lastName = name[1];
        String uppercaseLastName = lastName.toUpperCase();
        System.out.println(uppercaseLastName);
    }
```

### 설명하는 상수
`상징적인 상수를 만드세요. 리터럴 상수로 사용된 곳은 상징적인 상수로 바꿉니다.`


### 명시적인 매개변수
코드를 읽는 중에 루틴에서 다루고 있는 일부 데이터가 명시적으로 전달되지 않는 것을 발견했다.

루틴을 나누자. 앞부분에서 매개변수 값을 채운 후, 뒷부분에서 명시적으로 전달한다.

```
params = {a : 1, b : 2}
foo(params)

function foo(params)
    ...params.a... ...params.b...
```

```
function foo(params)
    foo_body(params.a, params.b)
    
fuction foo_body(a, b)
    ...a... ...b..
```

매개변수를 명시적으로 드러나게 만든 다음, 함수를 연쇄적으로 호출할 수 있게 하자. 이렇게 만들면 코드는 읽기와 테스트, 분석이 쉬워진다.


### 비슷한 코드끼리
긴 코드 덩어리를 읽다가 `아, 이 부분은 이렇게 하고, 저 부분은 저렇게 하는구나`라고 구분이 될 때는 두 부분 사이에 빈 줄을 넣어 분리하자

제대로 된 소프트웨어 설계는 유연성을 확보하지만, 그렇지 못한 경우는 자칫 변화 자체를 망각하고 소프트웨어 설계의 소용돌이에 빠질 수 있다.

관련 있는 코드를 뭉쳐두면 다양한 길로 나아갈 수 있다. 


### 도우미 추출
목적이 분명하고 나머지 코드와는 상호작용이 적은 코드 블록을 만날 때가 있다. 그 코드 블록을 추려내고, 도우미로 추출한 후에 이름을 붙인다.

도우미를 만들다 보면, 몇 시간 혹은 몇 분만에 다시 사용하고 싶어 하는 자신의 욕구를 발견 할 겁니다.
이 과정에서 인터페이스는 문제를 생각하도록 이끄는 도구가 된다.

우리가 만든 설계 요소에 새로운 이름을 붙일 수 있을 때, 새로운 인터페이스가 떠오른다.

### 하나의 더미
코드를 만드는 데, 가장 큰 비용이 들어가는 일은 코드 작성이 아니라 읽고 이해하는 데 드는 비용이다. 코드 정리를 선행하면 더 작은 조각 단위로 결합을 제거하는 길을 제시하여 응집도를 높일 수 있다. 이론적으로는 그렇고, 실무적으로 말하면 한 번에 머릿속에 기억하고 있어야 할 코드의 상세 내용을 줄여준다.

작은 코드 조각을 지향하는 목적은 코드를 한 번에 조금씩 이해할 수 있도록 하는 것이다. 때때로 이 과정이 잘못될 수 있다. 작은 코드 조각들이 서로 교류하는 방식은 코드를 더 알기 어렵게 한다.

다음 증상들을 찾아보기
+ 길고 반복되는 인자 목록
+ 반복되는 코드, 그 중에서도 반복되는 조건문
+ 도우미에 대한 부적절한 이름
+ 공유되어 변경에 노출된 데이터 구조

더 작은 조각을 지향한다고 말하면서, 동시에 하나의 더미로 만드는 일은 앞뒤가 맞지 않은 느낌이 들기도 한다. 하지만 신기하게도 직접 해 보면 분명 만족을 느끼게 될 것이다.


### 설명하는 주석
코드를 읽다가 `아, 이건 이렇게 돌아가는 거구나!` 라는 생각이 드는 순간 기록해라

```
파일 앞에 주석이 없는 경우, 설명을 추가하여 파일을 읽을 사람이 얻게 될 유용함을 미리 알려줍니다. (알란 메르트너)
```

### 불필요한 주석 지우기
코드만으로 내용을 모두 이해할 수 있다면 주석은 삭제해라.

### 코드 정리 구분
작은 PR은 보통 검토 시간 단축으로 환영 받는다. 코드를 신속히 검토할 수 있으므로, 더 작은 PR을 만들도록 동기부여가 될 수 있다.
초점이 분명할수록 PR은 더 빠른 검토를 장려한다. 

이렇게 강화된 반복 절차가 후톼하는 경우도 있는데, 대체로 코드를 느리게 검토한다면, 더 큰 PR을 만드는 결과를 초래하여 검토를 더욱 느리게 만든다.

### 연쇄적인 정리
코드 구조를 대대적으로 바꾸려고 코드 정리를 시작하는 경우가 많다. 너무 많이, 너무 빠르게 변경하지 않도록 주의하자.
대개 작은 정리를 순차적으로 성공하는 것이 무리한 정리로 실패하는 것보다 시간을 아껴준다.

### 코드 정리의 일괄 처리량
`많은 조직에서 하나의 변경 사항을 검토하고 배포하는 데 드는 고정 비용은 상당히 많습니다.` 이러한 비용을 체감하기 때문에 충돌, 상호작용, 추측으로 인한 비용이 증가됨에도 타협점을 찾으려고 노력하자.

코드 정리 비용을 줄이고자 한다면, 코드 정리 개수를 늘려서 동작 변경에 소용되는 비용을 줄이자. 그러면, 검토 비용을 줄일 수 있다.

### 리듬
`동작 변경은 코드 안에 뭉쳐서 나타나는 경향이 있다`
코드 정리를 한다면, 대부분의 변경 작업은 이미 정리된 코드 안에서 이루어지게 된다. 결국 시스템에 있는 대부분의 코드에 손을 대지 않았음에도, 정리되지 않은 코드를 만나는 일이 급격하게 줄어든다.

코드 정리는 몇 분에서 한 시간 정도면 충분하다고 자신 있게 말할 수 있다.

### 얽힘 풀기
+ 변경 대상인 동작을 모두 알게 되었고
+ 그 동작들을 쉽게 변경하려면 어떤 코드를 정리해야 하는지 모두 알게 되었으나
+ 문제는 정리한 코드와 변경할 동작이 함께 얽혀 버렸다.

+ 그대로 배포할 수 있다. 검토하는 사람들이 무례하다 느끼고, 오류가 발생하기 쉽지만 당장 처리할 수 있다.
+ 코드 정리와 변경 사항을 별도의 하나 이상의 PR로 나누거나 하나의 PR을 여러 번의 커밋으로 나눌 수 있다. 무례함은 줄어들지만 작업 횟수는 늘어난다.
+ 진행 중인 작업을 버리고, 코드 정리를 선행하는 순서로 다시 시작할 수 있다. 이렇게 하면 작업은 더 많아지지만, 이어지는 커밋과의 일관성은 분명해진다.

처음으로 코드 정리를 하기로 마음먹고 진행하다 보면, 먼저 정리할 것인지 나중에 정리할 것인지 고민하다가 코드 정리와 동작 변경 사이에서 전환 시점을 놓치기 일쑤다.
코드 정리와 동작 변경 사으이 선후 문제는 보통 시간이 지나면 해결되는 경우가 많다.


### 코드 정리 시점
코드 정리를 하지 않는 경우
+ 앞으로 다시는 코드를 변경하지 않을 때
+ 설꼐를 개선하더라도 배울 것이 없을 때

나중으로 정리를 미룰 경우
+ 정리할 코드 분량이 많은데, 보상이 바로 보이지 않을 때
+ 코드 정리에 대한 보상이 잠재적일 때
+ 작은 묶음으로 여러 번에 나눠서 코드 정리를 할 수 있을 때

동작 변경 후에 정리하는 경우
+ 다음 코드 정리까지 기다릴수록 비용이 더 불어날 때
+ 코드 정리를 하지 않으면 일을 끝냈다는 느낌이 들지 않을 때

코드 정리 후에 동작 변경을 해야 하는 경우
+ 코드 정리를 했을 때, 코드 이해가 쉬워지거나 동작 변경이 쉬워지는 즉각적인 효과를 얻을 수 있을 때
+ 어떤 코드를 어떻게 정리해야 하는지 알고 있을 때

### 요소들을 유익하게 관계 맺는 일
요소
+ 토큰 -> 식(expression) -> 문(statement) -> 함수 -> 객체/모듈 -> 시스템

관계 맺기
+ 호출
+ 발행
+ 대기
+ 참좀(변수의 값을 가져오기)

`요소들을 유익하게 관계 맺는 일`, 설계란 무엇일까? 설계를 구성하는 요소들과 그들의 관계, 그리고 그 관계에서 파생 되는 이점이 바로 설계이다.

설계자는 요소들을 유익하게 관계 맺는 일을 한다.
+ 요소를 만들고 삭제한다.
+ 관계를 만들고 삭제한다.
+ 관계의 이점을 높인다.

시스템의 구조
+ 요소 계층 구조
+ 요소 사이의 관계
+ 이러한 관계가 만들어내는 이점

### 구조와 동작
구조 변경과 동작 변경은 모두 가치를 만들어내지만, 근본적으로 다르다는 것을 이해하는 것부터 시작해라.
한만디로 말하면 되돌릴 수 있는 능력 즈, `가역성`이다.

### 경제 이론: 시간 가치와 선택 가능성
소프트웨어 설꼐는 `먼저 벌고 나중에 써야 한다`와 `물건이 아닌 옵션을 만들어야 한다`는 두 가지 속성을 조화시켜야 한다.

### 오늘의 1달러가 내일의 1달러보다 크다
돈의 시간 가치는 코드 정리를 먼저 하기보다는 나중에 하는 것을 권장한다. 지금 당장 돈을 벌고 나중에 코드를 정리하는 행동 변화를 실천할 수 있다면
점차 먼저 돈을 벌고 나중에 돈을 쓸 수 있을 것이다. (선행 코드 정리는 코드 정리와 뒤따르는 동작 변경의 비용 합계가 코드를 정리하지 않고 
동작 변경을 하는 경우보다 더 적을 수 있다는 것을 의미한다. 이런 경우에는 항상 먼저 정리해라)

### 옵션
옵션은 `선택`의 의미를 내포하는데, 그래서 소프트웨어 설계 문제에서 옵션을 보유하는 일은 당장 모든 비용을 `동작 변경`에 모두 투자하는 대신에 `선택 가능성` 혹은 `선택의 기회`를 유지하는 일을 제안하는 격이다.

+ "다음에 어떤 동작을 구현할 수 있을까"라는 질문은 동작을 구현하기 전에도, 그 자체로 가치가 있다. 
+ "다음에 어떤 동작을 구현할 수 있을까"라는 질문은 동작 후보 목록이 많을수록 더 가치가 있다. 목록의 규모를 늘릴 수 있다면 가치를 창출한 것이다.
+ 어떤 항목이 가장 가치가 있을지는 구현할 수 있는 옵션을 계속 열어두는 한 신경 쓸 필요가 없다.
+ 가치에 대한 예측이 불확실할수록 바로 구현하는 것보다 옵션이 지닌 가치가 더 커진다. 바로 구현하는 것보다 변화를 포용하면 내가 창출한 가치를 극대화할 수 있는데, 그 상황은 통상적으로 소프트웨어 개발이 가장 극적으로 실패하는 바로 그 지점이다.

옵션을 만드는 것과 동작을 변경하는 것의 균형을 맞추는 데 집중하자.
+ 잠재적인 동작 변경의 가치가 변동성이 클수록 더 좋다
+ 개발 기간이 길면 길수록 좋다.
+ 앞으로 더 저렴하게 개발할 수 있다면 더 좋겠지만, 그것은 가치의 극히 일부분에 불과했다.
+ 더 작은 설계 작업으로 옵션을 만들 수 있다면 더 좋았다.

### 옵션과 현금 흐름 비교
코드 정리부터 해야 할 때가 있다. 언제일까?
```
비용 (코드 정리) + 비용 (코드 정리 후 동작 변경) < 비용 (바로 동작 변경)
```

곤란한 상황은 다음과 같은 경우에 발생한다.
```
비용 (코드 정리) + 비용 (코드 정리 후 동작 변경) > 비용 (바로 동작 변경) 
```

두 가지 중요한 형태의 판단력을 길러서, 나중에 더 큰 일을 실행하려고 한다.
+ 소프트웨어 설계의 시기와 범위에 영향을 미치는 인센티브를 인식하는데 익숙해지기
+ 대인 관계 기술을 우리 자신에게 연습해서, 나중에 밀접하게 일하는 동료부터 더 넓은 범위의 동료에게까지 활용하기

### 되돌릴 수 있는 구조 변경
동작 변경과 구조 변경에 대해서는 어떤가? 선행 코드 정리의 특징은 구조 변경은 대체로 되돌릴 수 있다는 점이다.

일반적으로 되돌릴 수 있는 결정은 되돌릴 수 없는 결정과 다르게 취급해야 한다. 되돌릴 수 없는 결정에 대해서는 면밀히 검토하고 두 번, 세 번 확인하는 일은 매우 가치 있는 일이다.

### 결합도
겹합도를 주목하는 이유는 결합도가 가진 두 가지 설질 때문이다.
+ 일대다 : 어떤 변경이 일어나면, 한 요소는 여러 요소와 결합이 일어난다.
+ 연쇄작용: 일단 병경이 일어나면, 한 요소에서 다른 요소로 변경이 파급되고, 그 변경은 그 자체로 또 다른 변경을 촉발하고, 스스로도 변경을 촉발할 수 있다.

일대다 문제는 개발 도구를 이용하면 어느 정도 해결할 수도 있다.

연쇄적인 변경이 더 큰 문제다. 변경 비용은 멱법칙 분포를 따른다. 소프트웨어 설계를 해서 연쇄적인 변경의 확률과 규모를 줄일 수 있다.


### 콘스탄틴의 등가성
가장 비용이 많이 드는 하나의 변경이 나머지 변경을 모두 합친 것보다 훨씬 더 많은 비용이 든다. 다시 말해, 변경 비용은 큰 변경들의 비용과 거의 같다는 뜻이다.
`비용 (전체 변경) ~= 비용 (큰 변경들)`

큰 변경들의 비용이 비싼 이유는 무엇일까? 이 요소를 변경하려고 했더니 다른 두 요소를 변경해야 하고, 각각마다 또 다른 요소를 변경해야하며 전파가 된다.
바로 결합도다. 따라서 소프트웨어 비용은 결합도와 거의 같다.

`비용 (큰 변경들) ~= 결합도`

콘스탄틴의 등식
`비용 (소프트웨어) ~= 비용 (전체 변경) ~= 비용 (큰 변경들) ~= 결합도`

소프트웨어 설계의 중요성을 강조하기 위해 이렇게 바꿀 수 있다.
`비용 (스프트웨어) ~= 결합도`

따라서 소프트웨어 비용을 줄이려면 결합도를 줄여야한다.

### 결합도와 결합도 제거
`한 종류의 코드 변경에 대한 결합도를 줄일수록 다른 종류의 코드 변경에 대한 결합도가 커진다는 것이다.` 이것이 의미하는 실질적인 의미는 모든 결합을 다 색출하듯 없애려고 애쓰지 말아야 한다는 거싱다.
그렇게 해서 만들어진 결합도는 그만한 가치가 없다.

### 응집도
결합된 요소들은 둘을 포함하는 같은 요소의 하위 요소여야 한다. 이것이 응집도가 내포하는 첫 번째 의미다.
응집도의 두 번째 의미는 거름이 아닌 이물질은 이 더미가 아닌 다른 곳으로 이동해야 한다는 것이다.

+ 결합된 요소를 자체 하위 요소로 묶는 방법
+ 결합되지 않은 요소를 가져와 다른 곳에 배치하는 방법

### 결론
코드 정리가 먼저인가?
+ 비용: 코드를 정리하면 비용이 줄까? 아니면 나중에 하는 편이 나을까? 아니면, 줄일 가능성이 있을까?
+ 수익: 코드를 정리하면 수익이 더 커질까? 혹은 더 빨리 발생하거나 커질 가능성이 있나?
+ 결합도: 코드를 정리하면 변경에 필요한 요소의 수가 줄어드나?
+ 응집도: 코드를 정리하면 변경을 더 작고 좁은 범위로 집중시켜 더 적은 수의 요소만 다룰 수 있을까?
