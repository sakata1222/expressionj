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

## Switch expression

**If you use Java14 or later, you can
use [the official switch expression](https://docs.oracle.com/en/java/javase/14/language/switch-expressions.html)
.**

```java
import static jp.gr.java_conf.spica.expressionj.Expressions.ifExp;
import static jp.gr.java_conf.spica.expressionj.switchexpression.Cases.caseEq;
import static jp.gr.java_conf.spica.expressionj.switchexpression.Cases.caseIn;

import jp.gr.java_conf.spica.expressionj.switchexpression.Cases;

/*..snip..*/

int length = switchExp(day).cases(
    caseIn(MONDAY, FRIDAY, SUNDAY).yield(() -> 6),
    caseEq(TUESDAY).yield(() -> 7),
    caseIn(THURSDAY, SATURDAY).yield(() -> 8),
    caseIn(WEDNESDAY).yield(() -> 9),
    Cases.<Day>caseDefault().yield(() -> -1));
```

## Sample code

See also
the [sample code](https://github.com/sakata1222/expressionj/blob/main/lib/src/test/java/jp/gr/java_conf/spica/expressionj/SampleTest.java)

## Performance

If expression is a little slower than If statement, but it's enough fast when number of calls is
not huge (e.g. over 10000000).

Here is a result of
the [benchmark](https://github.com/sakata1222/expressionj/blob/main/lib/src/test/java/jp/gr/java_conf/spica/expressionj/PerformanceTest.java)
in the CI environment.

The benchmark just performs solve FizzBuzz and sum up of the length of the result.

- java.vm.vendor:AdoptOpenJDK
- java.vm.version:11.0.11+9
- os.name:Linux
- os.version:5.13.0-1022-azure

Benchmark of FizzBuzz.
Loop count is 10000000.
Elapsed Time in ms:

|Count|If statement with method|If Expression|
|---|---|---|
|1|374|701|
|2|247|543|
|3|240|612|
|4|260|591|
|5|231|610|

## Changelog

See [CHANGELOG.md](CHANGELOG.md)
