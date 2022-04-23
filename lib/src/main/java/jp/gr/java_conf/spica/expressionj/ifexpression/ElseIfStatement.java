package jp.gr.java_conf.spica.expressionj.ifexpression;

import java.util.function.Supplier;

public interface ElseIfStatement<V> {

  Then<V> then(Supplier<V> valueSupplier);
}
