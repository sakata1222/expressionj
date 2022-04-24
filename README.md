# ExpressionJ

Java library for supporting the following expressions:

- If expression

Java versions:

- 8 or later

## If expression

"FizzBuzz" can be implemented as following:

```java
import static jp.gr.java_conf.spica.expressionj.Expressions.ifExp;

/*..snip..*/

String fizzBuzz = ifExp(i % 15 == 0).then(() -> "FizzBuzz")
    .elseIf(() -> i % 3 == 0).then(() -> "Fizz")
    .elseIf(() -> i % 5 == 0).then(() -> "Buzz")
    .elseEx(() -> String.valueOf(i));
```

## Sample code

See also
the [sample code](https://github.com/sakata1222/expressionj/blob/main/lib/src/test/java/jp/gr/java_conf/spica/expressionj/SampleTest.java)


