package jp.gr.java_conf.spica.expressionj.ifexpression;

import java.util.function.Supplier;

class ElseIfFalse<V> implements ElseIfStatement<V> {

  private static final FalseThen<?> FALSE_THEN = new FalseThen<>();

  @Override
  @SuppressWarnings("unchecked")
  public Then<V> then(Supplier<V> valueSupplier) {
    return (Then<V>) FALSE_THEN;
  }
}
