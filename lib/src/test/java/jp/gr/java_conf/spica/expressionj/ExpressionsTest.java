package jp.gr.java_conf.spica.expressionj;

import static jp.gr.java_conf.spica.expressionj.Expressions.ifExp;
import static jp.gr.java_conf.spica.expressionj.Expressions.switchExp;
import static jp.gr.java_conf.spica.expressionj.ExpressionsTest.Color.BLUE;
import static jp.gr.java_conf.spica.expressionj.ExpressionsTest.Color.GREEN;
import static jp.gr.java_conf.spica.expressionj.ExpressionsTest.Color.INDIGO;
import static jp.gr.java_conf.spica.expressionj.ExpressionsTest.Color.ORANGE;
import static jp.gr.java_conf.spica.expressionj.ExpressionsTest.Color.PURPLE;
import static jp.gr.java_conf.spica.expressionj.ExpressionsTest.Color.RED;
import static jp.gr.java_conf.spica.expressionj.ExpressionsTest.Color.YELLOW;
import static jp.gr.java_conf.spica.expressionj.switchexpression.Cases.caseEq;
import static jp.gr.java_conf.spica.expressionj.switchexpression.Cases.caseIn;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;
import static org.assertj.core.api.Assertions.assertThatNoException;

import java.util.Arrays;
import java.util.Optional;
import jp.gr.java_conf.spica.expressionj.switchexpression.Cases;
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
          ifExp(false)
              .then(() -> {
                throw new RuntimeException("should not be called");
              })
              .elseIf(() -> false).then(() -> {
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

  @Nested
  class SwitchTest {

    @ParameterizedTest
    @CsvSource({
        "RED, 1",
        "ORANGE, -1",
        "YELLOW, -1"
    })
    void case2(Color color, int expected) {
      int value = switchExp(color).cases(
          caseEq(RED).yield(() -> 1),
          Cases.<Color>caseDefault().yield(() -> -1));

      assertThat(value).isEqualTo(expected);
    }

    @ParameterizedTest
    @CsvSource({
        "RED, 1",
        "ORANGE, 2",
        "YELLOW, -1",
        "GREEN, -1"
    })
    void case3(Color color, int expected) {
      int value = switchExp(color).cases(
          caseEq(RED).yield(() -> 1),
          caseEq(ORANGE).yield(() -> 2),
          Cases.<Color>caseDefault().yield(() -> -1));

      assertThat(value).isEqualTo(expected);
    }

    @ParameterizedTest
    @CsvSource({
        "RED, 1",
        "ORANGE, 2",
        "YELLOW, 3",
        "GREEN, -1",
        "BLUE, -1"
    })
    void case4(Color color, int expected) {
      int value = switchExp(color).cases(
          caseEq(RED).yield(() -> 1),
          caseEq(ORANGE).yield(() -> 2),
          caseEq(YELLOW).yield(() -> 3),
          Cases.<Color>caseDefault().yield(() -> -1));

      assertThat(value).isEqualTo(expected);
    }

    @ParameterizedTest
    @CsvSource({
        "RED, 1",
        "ORANGE, 2",
        "YELLOW, 3",
        "GREEN, 4",
        "BLUE, -1",
        "INDIGO, -1"
    })
    void case5(Color color, int expected) {
      int value = switchExp(color).cases(
          caseEq(RED).yield(() -> 1),
          caseEq(ORANGE).yield(() -> 2),
          caseEq(YELLOW).yield(() -> 3),
          caseEq(GREEN).yield(() -> 4),
          Cases.<Color>caseDefault().yield(() -> -1));

      assertThat(value).isEqualTo(expected);
    }

    @ParameterizedTest
    @CsvSource({
        "RED, 1",
        "ORANGE, 2",
        "YELLOW, 3",
        "GREEN, 4",
        "BLUE, 5",
        "INDIGO, -1",
        "PURPLE, -1"
    })
    void case6(Color color, int expected) {
      int value = switchExp(color).cases(
          caseEq(RED).yield(() -> 1),
          caseEq(ORANGE).yield(() -> 2),
          caseEq(YELLOW).yield(() -> 3),
          caseEq(GREEN).yield(() -> 4),
          caseEq(BLUE).yield(() -> 5),
          Cases.<Color>caseDefault().yield(() -> -1));

      assertThat(value).isEqualTo(expected);
    }

    @ParameterizedTest
    @CsvSource({
        "RED, 1",
        "ORANGE, 2",
        "YELLOW, 3",
        "GREEN, 4",
        "BLUE, 5",
        "INDIGO, 6",
        "PURPLE, -1",
        "GOLD, -1"
    })
    void caseVarargs(Color color, int expected) {
      int value = switchExp(color).cases(
          caseEq(RED).yield(() -> 1),
          caseEq(ORANGE).yield(() -> 2),
          caseEq(YELLOW).yield(() -> 3),
          caseEq(GREEN).yield(() -> 4),
          caseEq(BLUE).yield(() -> 5),
          caseEq(INDIGO).yield(() -> 6),
          Cases.<Color>caseDefault().yield(() -> -1));

      assertThat(value).isEqualTo(expected);
    }

    @ParameterizedTest
    @CsvSource({
        "RED, 1",
        "ORANGE, 2",
        "YELLOW, 3",
        "GREEN, 4",
        "BLUE, 5",
        "INDIGO, -1",
        "PURPLE, -1",
        "GOLD, -1"
    })
    void caseList(Color color, int expected) {
      int value = switchExp(color).cases(Arrays.asList(
          caseEq(RED).yield(() -> 1),
          caseEq(ORANGE).yield(() -> 2),
          caseEq(YELLOW).yield(() -> 3),
          caseEq(GREEN).yield(() -> 4),
          caseEq(BLUE).yield(() -> 5),
          Cases.<Color>caseDefault().yield(() -> -1)));

      assertThat(value).isEqualTo(expected);
    }

    @Test
    void caseMismatch() {
      assertThatIllegalArgumentException().isThrownBy(
          () -> switchExp(Color.GOLD).cases(
              caseEq(RED).yield(() -> "OK"),
              caseEq(BLUE).yield(() -> "NG")));
    }

    @ParameterizedTest
    @CsvSource({
        "RED, OK",
        "ORANGE, OK",
        "YELLOW, OK",
        "GREEN, OK",
        "BLUE, NG"
    })
    void caseIn4(Color color, String expected) {
      String v = switchExp(color).cases(
          caseIn(RED, ORANGE, YELLOW, GREEN).yield(() -> "OK"),
          Cases.<Color>caseDefault().yield(() -> "NG"));

      assertThat(v).isEqualTo(expected);
    }

    @ParameterizedTest
    @CsvSource({
        "RED, RAINBOW",
        "ORANGE, RAINBOW",
        "YELLOW, RAINBOW",
        "GREEN, RAINBOW",
        "BLUE, RAINBOW",
        "INDIGO, RAINBOW",
        "PURPLE, RAINBOW",
        "GOLD, NG"
    })
    void caseInVarargs(Color color, String expected) {
      String v = switchExp(color).cases(
          caseIn(RED, ORANGE, YELLOW, GREEN, BLUE, INDIGO, PURPLE).yield(() -> "RAINBOW"),
          Cases.<Color>caseDefault().yield(() -> "NG"));

      assertThat(v).isEqualTo(expected);
    }
  }

  @SuppressWarnings("unused")
  enum Color {
    RED,
    ORANGE,
    YELLOW,
    GREEN,
    BLUE,
    INDIGO,
    PURPLE,
    GOLD
  }
}
