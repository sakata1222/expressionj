package jp.gr.java_conf.spica.expressionj;

import jp.gr.java_conf.spica.expressionj.ifexpression.IfExpression;
import jp.gr.java_conf.spica.expressionj.ifexpression.IfFalse;
import jp.gr.java_conf.spica.expressionj.ifexpression.IfTrue;

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
}
