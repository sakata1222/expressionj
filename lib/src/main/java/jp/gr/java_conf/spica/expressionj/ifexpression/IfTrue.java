package jp.gr.java_conf.spica.expressionj.ifexpression;

import java.util.function.Supplier;

public class IfTrue implements IfExpression {

  @Override
  public <V> Then<V> then(Supplier<V> valueSupplier) {
    return new TrueThen<>(valueSupplier);
  }
}
