package jp.gr.java_conf.spica.expressionj.ifexpression;

import java.util.function.Supplier;

/**
 * If.
 */
public class IfFalse implements IfExpression {

  private final FalseThen<?> falseThen = new FalseThen<>();

  @Override
  @SuppressWarnings("unchecked")
  public <V> Then<V> then(Supplier<V> valueSupplier) {
    Validations.requireNonNullValueSupplier(valueSupplier);
    return (Then<V>) falseThen;
  }
}
