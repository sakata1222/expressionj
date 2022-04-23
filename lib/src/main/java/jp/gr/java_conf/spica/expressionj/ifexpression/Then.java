package jp.gr.java_conf.spica.expressionj.ifexpression;

import java.util.function.BooleanSupplier;
import java.util.function.Supplier;

public interface Then<V> {

  V elseEx(Supplier<V> elseValueSupplier);

  ElseIfStatement<V> elseIf(BooleanSupplier condition);
}
