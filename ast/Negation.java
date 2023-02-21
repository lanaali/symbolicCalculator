package org.ioopm.calculator.ast;

public class Negation extends Unary
{
  public Negation(SymbolicExpression expression)
  {
    super(expression);
  }

  public String getName()
  {
    return "negation";
  }

  public int getPriority()
  {
    return 5;
  }

  public String toString()
  {
    String result = "-";

    if (this.expression.getPriority() < this.getPriority())
    {
      result += "(" + this.expression + ")";
    }
    else
    {
      result += this.expression;
    }
    return result;
  }

  public boolean equals(Object other)
  {
    if (other instanceof Negation)
    {
      return super.equals(other);
    }
    else
    {
      return false;
    }
  }

  protected SymbolicExpression evalTopLevel(Environment vars)
  {
    if (this.expression.isConstant())
    {
      return new Constant(-this.expression.getValue());
    }
    if (this.expression instanceof Negation)
    {
      return ((Negation)this.expression).expression;
    }
    if (this.expression instanceof Addition)
    {
      Negation lhs = new Negation(((Addition)this.expression).lhs);
      SymbolicExpression rhs = ((Addition)this.expression).rhs;
      return new Subtraction(lhs, rhs);
    }
    if (this.expression instanceof Subtraction)
    {
      SymbolicExpression lhs = ((Subtraction)this.expression).rhs;
      SymbolicExpression rhs = ((Subtraction)this.expression).lhs;
      return new Subtraction(lhs, rhs);
    }
    return this;
  }

  protected SymbolicExpression evalChildren(Environment vars) throws IllegalExpressionException
  {
    SymbolicExpression reduced = this.expression.eval(vars);
    return new Negation(reduced);
  }
}
