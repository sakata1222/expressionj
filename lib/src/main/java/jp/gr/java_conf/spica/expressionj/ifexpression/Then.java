package jp.gr.java_conf.spica.expressionj.ifexpression;

import java.util.Optional;
import java.util.function.BooleanSupplier;
import java.util.function.Supplier;

public interface Then<V> {

  V elseExp(Supplier<V> elseValueSupplier);

  ElseIfStatement<V> elseIf(BooleanSupplier condition);

  Optional<V> end();
}
