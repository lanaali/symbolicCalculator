package org.ioopm.calculator.ast;

public class Exp extends Unary
{
  public Exp(SymbolicExpression expression)
  {
    super(expression);
  }

  public String getName()
  {
    return "exp";
  }

  public int getPriority()
  {
    return 10;
  }

  public boolean equals(Object other)
  {
    if (other instanceof Exp)
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
    if (this.expression.isConstant())
    {
      return new Constant(Math.exp(this.expression.getValue()));
    }
    return this;
  }

  protected SymbolicExpression evalChildren(Environment vars) throws IllegalExpressionException
  {
    SymbolicExpression reduced = this.expression.eval(vars);
    return new Exp(reduced);
  }
}
