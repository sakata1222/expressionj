package jp.gr.java_conf.spica.expressionj;

import jp.gr.java_conf.spica.expressionj.ifexpression.IfExpression;
import jp.gr.java_conf.spica.expressionj.ifexpression.IfFalse;
import jp.gr.java_conf.spica.expressionj.ifexpression.IfTrue;
import jp.gr.java_conf.spica.expressionj.switchexpression.SwitchExpression;

/**
 * Entry point of expressions.
 */
public class Expressions {

  /* Reuse instances for performance */
  private static final IfTrue IF_TRUE = new IfTrue();
  private static final IfFalse IF_FALSE = new IfFalse();

  private Expressions() {
  }

  /**
   * Factory of if expression.
   *
   * <p>FizzBuzz can be implemented as following:
   *
   * <pre>
   * {@code
   * String fizzBuzz = ifExp(i % 15 == 0).then(() -> "FizzBuzz")
   *     .elseIf(() -> i % 3 == 0).then(() -> "Fizz")
   *     .elseIf(() -> i % 5 == 0).then(() -> "Buzz")
   *     .elseExp(() -> String.valueOf(i));
   * }
   * </pre>
   *
   * @param condition condition fo if expression
   * @return IfExpression
   */
  public static IfExpression ifExp(boolean condition) {
    if (condition) {
      return IF_TRUE;
    } else {
      return IF_FALSE;
    }
  }

  /**
   * Factory of switch expression.
   *
   * <p>If you use Java14 or later, you can use <a
   * href="https://docs.oracle.com/en/java/javase/14/language/switch-expressions.html">the official
   * switch expression</a>)
   * <pre>
   * {@code
   * int length = switchExp(day).cases(
   *     caseIn(MONDAY, FRIDAY, SUNDAY).yield(() -> 6),
   *     caseEq(TUESDAY).yield(() -> 7),
   *     caseIn(THURSDAY, SATURDAY).yield(() -> 8),
   *     caseIn(WEDNESDAY).yield(() -> 9),
   *     Cases.<Day>caseDefault().yield(() -> -1));
   * }
   * </pre>
   *
   * @param value switch evaluation target
   * @param <I>   type of the target
   * @return Switch Expression
   */
  public static <I> SwitchExpression<I> switchExp(I value) {
    return new SwitchExpression<>(value);
  }
}
