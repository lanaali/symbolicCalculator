package org.ioopm.calculator.ast;

public class Cos extends Unary
{
  public Cos(SymbolicExpression expression)
  {
    super(expression);
  }

  public String getName()
  {
    return "cos";
  }

  public int getPriority()
  {
    return 10;
  }

  public boolean equals(Object other)
  {
    if (other instanceof Cos)
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
      return new Constant(Math.cos(this.expression.getValue()));
    }
    return this;
  }

  protected SymbolicExpression evalChildren(Environment vars) throws IllegalExpressionException
  {
    SymbolicExpression reduced = this.expression.eval(vars);
    return new Cos(reduced);
  }
}
