package jp.gr.java_conf.spica.expressionj.switchexpression;

import java.util.Arrays;
import java.util.List;

/**
 * Switch expression.
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
 * @param <I> type of the evaluation target
 */
public class SwitchExpression<I> {

  private final I evaluationTarget;

  /**
   * a constructor.
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
   * @param evaluationTarget a value will be evaluated
   */
  public SwitchExpression(I evaluationTarget) {
    this.evaluationTarget = evaluationTarget;
  }


  /**
   * Evaluate specified cases.
   *
   * @param c1  case1
   * @param c2  case2
   * @param <V> type of the return value
   * @return a value of a case that meets condition
   */
  public <V> V cases(Case<I, V> c1, Case<I, V> c2) {
    return cases(Arrays.asList(c1, c2));
  }

  /**
   * Evaluate specified cases.
   *
   * @param c1  case1
   * @param c2  case2
   * @param c3  case3
   * @param <V> type of the return value
   * @return a value of a case that meets condition
   */
  public final <V> V cases(Case<I, V> c1, Case<I, V> c2, Case<I, V> c3) {
    return cases(Arrays.asList(c1, c2, c3));
  }

  /**
   * Evaluate specified cases.
   *
   * @param c1  case1
   * @param c2  case2
   * @param c3  case3
   * @param c4  case4
   * @param <V> type of the return value
   * @return a value of a case that meets condition
   */
  public <V> V cases(Case<I, V> c1, Case<I, V> c2, Case<I, V> c3, Case<I, V> c4) {
    return cases(Arrays.asList(c1, c2, c3, c4));
  }

  /**
   * Evaluate specified cases.
   *
   * @param c1  case1
   * @param c2  case2
   * @param c3  case3
   * @param c4  case4
   * @param c5  case5
   * @param <V> type of the return value
   * @return a value of a case that meets condition
   */
  public <V> V cases(Case<I, V> c1, Case<I, V> c2, Case<I, V> c3, Case<I, V> c4, Case<I, V> c5) {
    return cases(Arrays.asList(c1, c2, c3, c4, c5));
  }


  /**
   * Evaluate specified cases.
   *
   * @param c1  case1
   * @param c2  case2
   * @param c3  case3
   * @param c4  case4
   * @param c5  case5
   * @param c6  case6
   * @param <V> type of the return value
   * @return a value of a case that meets condition
   */
  public <V> V cases(Case<I, V> c1, Case<I, V> c2, Case<I, V> c3, Case<I, V> c4, Case<I, V> c5,
      Case<I, V> c6) {
    return cases(Arrays.asList(c1, c2, c3, c4, c5, c6));
  }

  /**
   * Evaluate specified cases.
   *
   * @param cases cases
   * @param <V>   type of the return value
   * @return a value of a case that meets condition
   */
  @SuppressWarnings("varargs")
  @SafeVarargs
  public final <V> V cases(Case<I, V>... cases) {
    return cases(Arrays.asList(cases));
  }

  /**
   * Evaluate specified cases.
   *
   * @param cases cases
   * @param <V>   type of the return value
   * @return a value of a case that meets condition
   */
  public <V> V cases(List<Case<I, V>> cases) {
    return cases.stream()
        .filter(c -> c.matches(evaluationTarget))
        .findFirst()
        .map(Case::value)
        .orElseThrow(() -> new IllegalArgumentException(
            "Any cases do not match the value:" + evaluationTarget));
  }
}
