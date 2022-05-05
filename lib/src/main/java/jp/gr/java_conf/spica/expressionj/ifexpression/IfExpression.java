package jp.gr.java_conf.spica.expressionj.ifexpression;

import java.util.function.Supplier;

/**
 * If.
 */
@FunctionalInterface
public interface IfExpression {

  /**
   * The valueSupplier will be called when the previous condition is true.
   *
   * @param valueSupplier the result will be returned when the else if condition is true
   * @param <V>           Result type of if expression
   * @return Then
   */
  <V> Then<V> then(Supplier<V> valueSupplier);

}
