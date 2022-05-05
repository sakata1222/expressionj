package jp.gr.java_conf.spica.expressionj.ifexpression;

class InstanceCaches {

  private InstanceCaches() {
  }

  /* reuse instances to reduce cost of "new" */

  static final ElseIfTrue<?> ELSE_IF_TURE = new ElseIfTrue<>();
  static final ElseIfFalse<?> ELSE_IF_FALSE = new ElseIfFalse<>();
  static final FalseThen<?> FALSE_THEN = new FalseThen<>();
}
