package jp.gr.java_conf.spica.expressionj.ifexpression;

import java.util.function.Supplier;

class ElseIfFalse<V> implements ElseIfStatement<V> {

  @Override
  @SuppressWarnings("unchecked")
  public Then<V> then(Supplier<V> valueSupplier) {
    return (Then<V>) InstanceCaches.FALSE_THEN;
  }
}
