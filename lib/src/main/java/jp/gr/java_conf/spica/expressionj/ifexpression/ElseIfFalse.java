package jp.gr.java_conf.spica.expressionj.ifexpression;

import java.util.function.Supplier;

class ElseIfFalse<V> implements ElseIfStatement<V> {

  @Override
  public Then<V> then(Supplier<V> valueSupplier) {
    return new FalseThen<>();
  }
}
