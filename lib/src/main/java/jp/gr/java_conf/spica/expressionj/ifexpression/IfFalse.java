package jp.gr.java_conf.spica.expressionj.ifexpression;

import java.util.function.Supplier;

public class IfFalse implements IfExpression {

  private final FalseThen<?> falseThen = new FalseThen<>();

  @Override
  @SuppressWarnings("unchecked")
  public <V> Then<V> then(Supplier<V> valueSupplier) {
    return (Then<V>) falseThen;
  }
}
