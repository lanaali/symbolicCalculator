package org.ioopm.calculator.ast;

public class Assignment extends Binary
{
  public Assignment(SymbolicExpression lhs, SymbolicExpression rhs)
  {
    super(lhs, rhs);
  }

  public String getName()
  {
    return "=";
  }

  public int getPriority()
  {
    return 4;
  }

  //TODO: make test for this function!
  protected SymbolicExpression evalTopLevel(Environment vars) throws IllegalExpressionException
  {
    Variable variable = (Variable)this.rhs;
    vars.put(variable, this.lhs);
    return this.lhs;
  }

  protected SymbolicExpression evalChildren(Environment vars) throws IllegalExpressionException
  {
    SymbolicExpression reducedLhs = this.lhs.eval(vars);
    return new Assignment(reducedLhs, this.rhs);
  }
}
