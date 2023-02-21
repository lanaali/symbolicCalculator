package org.ioopm.calculator.ast;

public class Log extends Unary
{
  public Log(SymbolicExpression expression)
  {
    super(expression);
  }

  public String getName()
  {
    return "log";
  }

  public int getPriority()
  {
    return 10;
  }

  public boolean equals(Object other)
  {
    if (other instanceof Log)
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
      return new Constant(Math.log(this.expression.getValue()));
    }
    return this;
  }

  protected SymbolicExpression evalChildren(Environment vars) throws IllegalExpressionException
  {
    SymbolicExpression reduced = this.expression.eval(vars);
    return new Log(reduced);
  }
}