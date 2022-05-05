package jp.gr.java_conf.spica.expressionj.ifexpression;

import java.util.Objects;

class Validations {

  private Validations() {
  }

  static <V> V requireNonNullValueSupplier(V v) {
    return Objects.requireNonNull(v,
        "value supplier should not be null. "
            + "If you want to use null as a return value of if expression, specify \"() -> null\"");
  }

  static <V> V requireNonNullCondition(V v) {
    return Objects.requireNonNull(v, "condition should not be null");
  }
}
