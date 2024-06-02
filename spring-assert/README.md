+ notNull(Object object, String message)
  + 대상 객체가 null이 아니어야 함을 검증합니다. null일 경우 지정된 메시지와 함께 IllegalArgumentException을 던집니다.
+ isNull(Object object, String message)
  + 대상 객체가 null이어야 함을 검증합니다. null이 아닐 경우 지정된 메시지와 함께 IllegalArgumentException을 던집니다.
+ hasLength(String text, String message)
  + 문자열이 null이 아니며 길이가 0이 아님을 검증합니다. 비어있거나 null일 경우 지정된 메시지와 함께 IllegalArgumentException을 던집니다.
+ hasText(String text, String message)
  + 문자열이 null이 아니며, 공백만 포함하지 않음을 검증합니다. 비어있거나 공백만 포함할 경우 지정된 메시지와 함께 IllegalArgumentException을 던집니다.
+ notEmpty(Collection<?> collection, String message)
  + 컬렉션이 null이 아니며 비어 있지 않음을 검증합니다. 비어있거나 null일 경우 지정된 메시지와 함께 IllegalArgumentException을 던집니다.
+ isTrue(boolean expression, String message)
  + 표현식이 true임을 검증합니다. false일 경우 지정된 메시지와 함께 IllegalArgumentException을 던집니다.
+ state(boolean expression, String message)
  + 주어진 표현식이 참인지 검증합니다. 거짓일 경우 지정된 메시지와 함께 IllegalStateException을 던집니다.
+ `Supplier<String> messageSupplier` 이 있는 경우
  + `Supplier<String>`를 `Assert`와 함께 사용하면 메시지를 필요할 때까지 지연시켜 생성할 수 있습니다. 이로 인해 메시지 생성 비용을 줄일 수 있습니다.
  + 이 방식은 메시지가 실제로 필요할 때까지 생성되지 않으므로 성능 최적화에 도움이 됩니다.
