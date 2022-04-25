package jp.gr.java_conf.spica.expressionj;

import static jp.gr.java_conf.spica.expressionj.Expressions.ifExp;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class SampleTest {

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
