package org.ioopm.calculator.ast;

public class Sin extends Unary
{
  public Sin(SymbolicExpression expression)
  {
    super(expression);
  }

  public String getName()
  {
    return "sin";
  }

  public int getPriority()
  {
    return 10;
  }

  public boolean equals(Object other)
  {
    if (other instanceof Sin)
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
      return new Constant(Math.sin(this.expression.getValue()));
    }
    return this;
  }

  protected SymbolicExpression evalChildren(Environment vars) throws IllegalExpressionException
  {
    SymbolicExpression reduced = this.expression.eval(vars);
    return new Sin(reduced);
  }
}
