# ExpressionJ

[![Maven Central](https://img.shields.io/maven-central/v/io.github.sakata1222/expressionj.svg?label=Maven%20Central)](https://search.maven.org/artifact/io.github.sakata1222/expressionj)

[![Java CI with Gradle](https://github.com/sakata1222/expressionj/actions/workflows/ci.yaml/badge.svg)](https://github.com/sakata1222/expressionj/actions/workflows/ci.yaml)
[![codecov](https://codecov.io/gh/sakata1222/expressionj/branch/main/graph/badge.svg)](https://codecov.io/gh/sakata1222/expressionj)

[![sonar-reliability-rating](https://sonarcloud.io/api/project_badges/measure?project=sakata1222_expressionj&metric=reliability_rating)](https://sonarcloud.io/dashboard?id=sakata1222_expressionj)
[![sonar-security-rating](https://sonarcloud.io/api/project_badges/measure?project=sakata1222_expressionj&metric=security_rating)](https://sonarcloud.io/dashboard?id=sakata1222_expressionj)
[![sonar-sqale-rating](https://sonarcloud.io/api/project_badges/measure?project=sakata1222_expressionj&metric=sqale_rating)](https://sonarcloud.io/dashboard?id=sakata1222_expressionj)

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
    .elseExp(() -> String.valueOf(i));
```

## Sample code

See also
the [sample code](https://github.com/sakata1222/expressionj/blob/main/lib/src/test/java/jp/gr/java_conf/spica/expressionj/SampleTest.java)
