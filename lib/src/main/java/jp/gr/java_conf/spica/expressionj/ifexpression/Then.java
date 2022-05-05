package jp.gr.java_conf.spica.expressionj.ifexpression;

import java.util.Optional;
import java.util.function.BooleanSupplier;
import java.util.function.Supplier;

/**
 * Then.
 *
 * @param <V> Result type of if expression
 */
public interface Then<V> {

  /**
   * Ends if/else-if expression with default value.
   *
   * @param elseValueSupplier the result will be returned when all previous if/else-if conditions
   *                          are false
   * @return the result of expression
   */
  V elseExp(Supplier<V> elseValueSupplier);

  /**
   * Else-if.
   *
   * @param condition condition for else-if.
   * @return else-if
   */
  ElseIfStatement<V> elseIf(BooleanSupplier condition);

  /**
   * Ends if/else-if expression without default value.
   *
   * @return empty when all previous if/else-if conditions are false
   */
  Optional<V> end();
}
