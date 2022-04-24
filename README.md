# ExpressionJ

## If expression

"FizzBuzz" can be implemented as following:

```java
String fizzBuzz = ifExp(i % 15 == 0).then(() -> "FizzBuzz")
    .elseIf(() -> i % 3 == 0).then(() -> "Fizz")
    .elseIf(() -> i % 5 == 0).then(() -> "Buzz")
    .elseEx(() -> String.valueOf(i));
```
