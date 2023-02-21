package org.ioopm.calculator.ast;

public interface Visitor {
  public SymbolicExpression visit(Constant c);
  public SymbolicExpression visit(Variable v);
  public SymbolicExpression visit(NamedConstant c);
  public SymbolicExpression visit(Negation n);
  public SymbolicExpression visit(Cos c);
  public SymbolicExpression visit(Sin s);
  public SymbolicExpression visit(Log l);
  public SymbolicExpression visit(Exp e);
  public SymbolicExpression visit(Addition a);
  public SymbolicExpression visit(Subtraction s);
  public SymbolicExpression visit(Division d);
  public SymbolicExpression visit(Multiplication m);
  public SymbolicExpression visit(Assignment a);
  public SymbolicExpression visit(Quit q);
  public SymbolicExpression visit(Vars v);
  public SymbolicExpression visit(Clear c);
}
