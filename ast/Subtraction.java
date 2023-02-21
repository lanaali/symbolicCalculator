package org.ioopm.calculator.ast;

public class Subtraction extends Binary
{
  public Subtraction(SymbolicExpression lhs, SymbolicExpression rhs)
  {
    super(lhs, rhs);
  }

  public String getName()
  {
    return "-";
  }

  public int getPriority()
  {
    return 6;
  }

  public boolean equals(Object other)
  {
    if (other instanceof Subtraction)
    {
      return super.equals(other);
    }
    else
    {
      return false;
    }
  }

  protected SymbolicExpression evalTopLevel(Environment vars) throws IllegalExpressionException
  {
    if (this.lhs.isConstant() && this.rhs.isConstant())
    {
      return new Constant(this.lhs.getValue() - this.rhs.getValue());
    }
    return this;
  }

  protected SymbolicExpression evalChildren(Environment vars) throws IllegalExpressionException
  {
    SymbolicExpression reducedLhs = this.lhs.eval(vars);
    SymbolicExpression reducedRhs = this.rhs.eval(vars);
    return new Subtraction(reducedLhs, reducedRhs);
  }
}
