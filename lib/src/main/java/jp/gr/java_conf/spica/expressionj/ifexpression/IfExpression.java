package jp.gr.java_conf.spica.expressionj.ifexpression;

import java.util.function.Supplier;

@FunctionalInterface
public interface IfExpression {

  <V> Then<V> then(Supplier<V> valueSupplier);

}
