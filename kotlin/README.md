# Kotlin


## 재네릭 클래스
```kotlin
class Cage<T> {
}
```
타입 파라미터를 사용한 클래스

## Raw 타입
```java
List list = new ArrayList();
```
재네릭 클래스에서 타입 매개변수를 사용하지 않고 인스턴스화 하는 것
But, 코틀린에서는 Raw 타입 사용이 불가능하다.

## 변성
재네릭 클래스 타입 파라미터에 따라 제네릭 클래스 간의 상속 관계가 어떻게 되는지를 나타내는 용어


### 무공변(불공변, in-variant)
타입 파라미터끼리는 상속관계이더라도, 제네릭 클래스 간에는 상속관계가 없다는 의미

변성을 부여하지 않았다면 제네릭 클래스는 기본적으로 무공변 하다.

### 공변 (co-variant)
타입 파라미터간의 상속관계가 제네릭 클래스에도 동일하게 유지된다는 의미

코틀린에서는 out 변성 어노테이션을 사용한다.

### 반공변 (contra-variant)
타입 파라미터간의 상속관계가 제네릭 클래스에서는 반대로 유지된다는 의미

코틀린에서는 in 변성 어노테이션을 사용한다.

### 선언 지점 변성
```kotlin
class Cage<out T> {
    
}
```
클래스 자체를 공변하거나 반공변하게 만드는 방법

### 사용 지점 변성
```kotlin

    fun moveFrom(otherCage: Cage<out T>) {
        ...
    }
```
특정 함수 또는 특정 변수에 대해 공변/반공변을 만드는 방법

### 제네릭 제약
```kotlin
class Cage<T : Animal> {
}
```
제네릭 클래스의 타입 파라미터에 제한을 거는 방법

### 타입 소거
JDK의 호환성을 위해 런타임 때 제네릭 클래스의 타입 파라미터 정보가 지워지는 것

```kotlin
inline fun <reified T> List<*>.hasAnyInstanceOf(): Boolean {
    return this.any { it is T }
}
```

Kotlin 에서는 inline 함수 + reified 키워드를 이용해 타입 소거를 일부 막을 수 있다.


### Star Projection
```kotlin
fun checkList(data: Any) {
    if (data is List<*>) {
        val element: Any? = data[0]
    }
}
```
어떤 타입이건 들어갈 수 있다는 의미


### 타입 파라미터 섀도잉
```kotlin
class Cage<T : Animal> {
    fun <T : Animal> addAnimal(animal: T) {
        
    }
}
```
똑같은 T문자이지만, 클래스에서의 T와 함수에서의 T가 다른 것으로 간주된다.

클래스의 T가 함수의 T에 의해 shadowing이 된다.

타입 파라미터 섀도잉은 피해야 하고, 만약 함수 타입 파라미터를 쓰고 싶다면 겹치지 않도록 해야 한다.


### 제네릭 클래스의 상속
```kotlin

```
