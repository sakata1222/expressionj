package jp.gr.java_conf.spica.expressionj.ifexpression;

import java.util.function.Supplier;

/**
 * else-if.
 *
 * @param <V> Result type of if expression
 */
public interface ElseIfStatement<V> {

  /**
   * The valueSupplier will be called when the previous condition is true.
   *
   * @param valueSupplier the result will be returned when the else if condition is true
   * @return Then
   */
  Then<V> then(Supplier<V> valueSupplier);
}
