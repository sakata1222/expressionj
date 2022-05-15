package jp.gr.java_conf.spica.expressionj.switchexpression;

/**
 * Case.
 *
 * @param <I> the type of the evaluation target
 * @param <V> the type of the return value
 */
public interface Case<I, V> {

  /**
   * evaluate the target.
   *
   * @param evaluationTarget target
   * @return true when matches
   */
  boolean matches(I evaluationTarget);

  /**
   * return value
   *
   * @return value
   */
  V value();
}
