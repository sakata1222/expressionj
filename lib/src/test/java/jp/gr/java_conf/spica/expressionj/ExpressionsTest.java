package jp.gr.java_conf.spica.expressionj;

import static jp.gr.java_conf.spica.expressionj.Expressions.ifExp;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatNoException;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class ExpressionsTest {

  @Nested
  class IfTest {

    @ParameterizedTest
    @CsvSource(value = {
        "true, trueValue",
        "false, falseValue"})
    void ifElseWithLambda(boolean condition, String expected) {

      String answer = ifExp(condition).then(() -> "trueValue")
          .elseEx(() -> "falseValue");

      assertThat(answer).isEqualTo(expected);
    }

    @Test
    void ifElseDoesNotReferElseValueWhenIfConditionIsTrue() {
      assertThatNoException().isThrownBy(() -> assertThat(
          ifExp(true).then(() -> "trueValue").elseEx(null)).isEqualTo(
          "trueValue"));
    }

    @ParameterizedTest
    @CsvSource(value = {
        "true, true, a",
        "true, false, a",
        "false, true, b",
        "false, false, c",
    })
    void ifElseIfWithLambda(boolean first, boolean second, String expected) {

      String answer = ifExp(first).then(() -> "a")
          .elseIf(() -> second).then(() -> "b")
          .elseEx(() -> "c");

      assertThat(answer).isEqualTo(expected);
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
          .elseEx(() -> String.valueOf(i));

      assertThat(fizzBuzz).isEqualTo(expected);
    }
  }
}
