package jp.gr.java_conf.spica.expressionj;

import static jp.gr.java_conf.spica.expressionj.Expressions.ifExp;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatNoException;

import java.util.Optional;
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
    void ifElse(boolean condition, String expected) {
      String answer = ifExp(condition).then(() -> "trueValue")
          .elseExp(() -> "falseValue");

      assertThat(answer).isEqualTo(expected);
    }

    @Test
    void ifElseDoesNotCallTrueValueWhenIfConditionIsFalse() {
      assertThatNoException().isThrownBy(() ->
          ifExp(false).then(() -> {
            throw new RuntimeException("should not be called");
          }).elseExp(() -> "falseValue"));
    }

    @Test
    void ifElseDoesNotCallElseValueWhenIfConditionIsTrue() {
      assertThatNoException().isThrownBy(() ->
          ifExp(true).then(() -> "trueValue")
              .elseExp(() -> {
                throw new RuntimeException("should not be called");
              }));
    }

    @ParameterizedTest
    @CsvSource(value = {
        "true, true, a",
        "true, false, a",
        "false, true, b",
        "false, false, c",
    })
    void ifElseIf(boolean first, boolean second, String expected) {
      String answer = ifExp(first).then(() -> "a")
          .elseIf(() -> second).then(() -> "b")
          .elseExp(() -> "c");

      assertThat(answer).isEqualTo(expected);
    }

    @Test
    void elseIfDoesNotCallElseIfValueWhenIfConditionIsTrue() {
      assertThatNoException().isThrownBy(() ->
          ifExp(true).then(() -> "a")
              .elseIf(() -> true).then(() -> {
                throw new RuntimeException("should not be called");
              })
              .elseExp(() -> {
                throw new RuntimeException("should not be called");
              }));
    }

    @Test
    void elseIfDoesNotCallElseIfValueWhenElseIfConditionIsFalse() {
      assertThatNoException().isThrownBy(() ->
          ifExp(false).then(() -> {
                throw new RuntimeException("should not be called");
              }).elseIf(() -> false).then(() -> {
                throw new RuntimeException("should not be called");
              })
              .elseExp(() -> "c"));
    }

    @Test
    void endReturnsValueWhenConditionIsTrue() {
      Optional<String> answer = ifExp(true).then(() -> "value").end();

      assertThat(answer).hasValue("value");
    }

    @Test
    void endReturnsEmptyWhenConditionIsFalse() {
      Optional<String> answer = ifExp(false).then(() -> "value").end();

      assertThat(answer).isEmpty();
    }

    @ParameterizedTest
    @CsvSource(value = {
        "true, true, a",
        "true, false, a",
        "false, true, b"
    })
    void ifElseIfEnd(boolean first, boolean second, String expected) {
      Optional<String> answer = ifExp(first).then(() -> "a")
          .elseIf(() -> second).then(() -> "b")
          .end();

      assertThat(answer).hasValue(expected);
    }

    @Test
    void ifElseIfEndAllFalse() {
      Optional<String> answer = ifExp(false).then(() -> "a")
          .elseIf(() -> false).then(() -> "b")
          .end();

      assertThat(answer).isEmpty();
    }
  }
}
