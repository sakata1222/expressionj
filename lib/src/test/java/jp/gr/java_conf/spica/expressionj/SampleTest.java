package jp.gr.java_conf.spica.expressionj;

import static jp.gr.java_conf.spica.expressionj.Day.FRIDAY;
import static jp.gr.java_conf.spica.expressionj.Day.MONDAY;
import static jp.gr.java_conf.spica.expressionj.Day.SATURDAY;
import static jp.gr.java_conf.spica.expressionj.Day.SUNDAY;
import static jp.gr.java_conf.spica.expressionj.Day.THURSDAY;
import static jp.gr.java_conf.spica.expressionj.Day.TUESDAY;
import static jp.gr.java_conf.spica.expressionj.Day.WEDNESDAY;
import static jp.gr.java_conf.spica.expressionj.Expressions.ifExp;
import static jp.gr.java_conf.spica.expressionj.Expressions.switchExp;
import static jp.gr.java_conf.spica.expressionj.switchexpression.Cases.caseEq;
import static jp.gr.java_conf.spica.expressionj.switchexpression.Cases.caseIn;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;

import java.util.Optional;
import java.util.stream.Stream;
import jp.gr.java_conf.spica.expressionj.switchexpression.Cases;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;

class SampleTest {

  @Nested
  class IfExpressions {

    @ParameterizedTest
    @CsvSource(value = {
        "1, odd",
        "2, even",
        "3, odd",
        "4, even"
    })
    void evenOdd(int i, String expected) {
      String evenOdd = ifExp(i % 2 == 0).then(() -> "even")
          .elseExp(() -> "odd");

      assertThat(evenOdd).isEqualTo(expected);
    }

    @ParameterizedTest
    @CsvSource(value = {
        "1, 1",
        "2, 2",
        "3, Fizz",
        "4, 4",
        "5, Buzz",
        "6, Fizz",
        "7, 7",
        "8, 8",
        "9, Fizz",
        "10, Buzz",
        "11, 11",
        "12, Fizz",
        "13, 13",
        "14, 14",
        "15, FizzBuzz",
    })
    void fizzBuzz(int i, String expected) {
      String fizzBuzz = ifExp(i % 15 == 0).then(() -> "FizzBuzz")
          .elseIf(() -> i % 3 == 0).then(() -> "Fizz")
          .elseIf(() -> i % 5 == 0).then(() -> "Buzz")
          .elseExp(() -> String.valueOf(i));

      assertThat(fizzBuzz).isEqualTo(expected);
    }

    @Test
    void ifTrueEnd() {
      Optional<String> answer = ifExp(true).then(() -> "true!").end();

      assertThat(answer).hasValue("true!");
    }

    @Test
    void ifFalseEnd() {
      Optional<String> answer = ifExp(false).then(() -> "true!").end();

      assertThat(answer).isEmpty();
    }
  }

  @Nested
  @TestInstance(Lifecycle.PER_CLASS)
  class SwitchExpressions {

    @ParameterizedTest
    @CsvSource({
        "MONDAY, 6",
        "TUESDAY, 7",
        "WEDNESDAY, 9",
        "THURSDAY, 8",
        "FRIDAY, 6",
        "SATURDAY, 8",
        "SUNDAY, 6",
        "INVALID, -1"
    })
    void numLettersOfDay(Day day, int expected) {
      int length = switchExp(day).cases(
          caseIn(MONDAY, FRIDAY, SUNDAY).yield(() -> 6),
          caseEq(TUESDAY).yield(() -> 7),
          caseIn(THURSDAY, SATURDAY).yield(() -> 8),
          caseIn(WEDNESDAY).yield(() -> 9),
          Cases.<Day>caseDefault().yield(() -> -1));

      assertThat(length).isEqualTo(expected);
      if (day != Day.INVALID) {
        assertThat(length).isEqualTo(day.name().length());
      }
    }

    @ParameterizedTest
    @MethodSource("perimeterOfShapeProvider")
    void perimeterOfShape(Shape shape) {
      double perimeter = switchExp(shape).cases(
          Cases.<Shape, Rectangle>caseInstanceOf(Rectangle.class)
              .yield(r -> r.length * 2 + r.width * 2),
          Cases.<Shape, Circle>caseInstanceOf(Circle.class)
              .yield(r -> 2 * r.radius * Math.PI));
      assertThat(perimeter).isEqualTo(shape.perimeter());
    }

    @SuppressWarnings("unused")
    Stream<Shape> perimeterOfShapeProvider() {
      return Stream.of(
          new Rectangle(1, 3),
          new Circle(2));
    }

    @ParameterizedTest
    @MethodSource("perimeterOfShapeProvider")
    void perimeterOfShapeUnmatch() {
      Shape shape = new EquilateralTriangle(1);
      assertThatIllegalArgumentException()
          .isThrownBy(() -> switchExp(shape).cases(
              Cases.<Shape, Rectangle>caseInstanceOf(Rectangle.class)
                  .yield(r -> r.length * 2 + r.width * 2),
              Cases.<Shape, Circle>caseInstanceOf(Circle.class)
                  .yield(r -> 2 * r.radius * Math.PI)));
    }
  }

  interface Shape {

    double perimeter();
  }

  static class Rectangle implements Shape {

    final double length;
    final double width;

    Rectangle(double length, double width) {
      this.length = length;
      this.width = width;
    }

    @Override
    public double perimeter() {
      return length * 2 + width * 2;
    }
  }

  static class Circle implements Shape {

    final double radius;

    Circle(double radius) {
      this.radius = radius;
    }

    @Override
    public double perimeter() {
      return 2 * radius * Math.PI;
    }
  }

  static class EquilateralTriangle implements Shape {

    final double edge;

    EquilateralTriangle(double edge) {
      this.edge = edge;
    }

    @Override
    public double perimeter() {
      return edge * 3;
    }
  }
}
