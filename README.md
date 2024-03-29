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
import static jp.gr.java_conf.spica.expressionj.Expressions.switchExp;
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

```java
import static jp.gr.java_conf.spica.expressionj.Expressions.switchExp;

import jp.gr.java_conf.spica.expressionj.switchexpression.Cases;

/*..snip..*/

double perimeter = switchExp(shape).cases(
    Cases.<Shape, Rectangle>caseInstanceOf(Rectangle.class)
        .yield(r -> r.length * 2 + r.width * 2),
    Cases.<Shape, Circle>caseInstanceOf(Circle.class)
        .yield(r -> 2 * r.radius * Math.PI));
```

## Sample code

See also
the [sample code](https://github.com/sakata1222/expressionj/blob/main/lib/src/test/java/jp/gr/java_conf/spica/expressionj/SampleTest.java)

## Performance

### If expression

If expression is a little slower than If statement, but it's enough fast when number of calls is
not huge (e.g. over 10000000(10^7)).

Here is a result of
the [benchmark](https://github.com/sakata1222/expressionj/blob/main/lib/src/test/java/jp/gr/java_conf/spica/expressionj/PerformanceTest.java)
in the CI environment.

The benchmark just performs solve FizzBuzz and sum up of the length of the result.

- java.vm.vendor:AdoptOpenJDK
- java.vm.version:11.0.11+9
- os.name:Linux
- os.version:5.13.0-1023-azure

Benchmark of FizzBuzz.

Loop count is 10000000.

Elapsed Time in ms:

|Count|If statement with method|If Expression|
|---|---|---|
|1|252|880|
|2|266|585|
|3|270|582|
|4|270|581|
|5|265|594|
|6|269|580|

### Switch expression

Switch expression is slower than Switch statement, and might be slower than
official switch expression.
When the number of calls is little huge (e.g. over 1000000(10^6)), please use switch statement or
official switch expression.

Here is a result of
the [benchmark](https://github.com/sakata1222/expressionj/blob/main/lib/src/test/java/jp/gr/java_conf/spica/expressionj/PerformanceTest.java)
in the CI environment.

- java.vm.vendor:AdoptOpenJDK
- java.vm.version:11.0.11+9
- os.name:Linux
- os.version:5.13.0-1023-azure

Benchmark of length count of days.
Loop count is 125000.
Elapsed Time in ms:

|Count|Switch statement with method|Switch Expression|
|---|---|---|
|1|95|747|
|2|51|525|
|3|51|481|
|4|28|477|
|5|20|478|
|6|20|476|

## Changelog

See [CHANGELOG.md](CHANGELOG.md)
