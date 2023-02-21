package org.ioopm.calculator.ast;

/// Super class for all unary operators, e.g. Log and Sin.
public abstract class Unary extends SymbolicExpression
{
  protected SymbolicExpression expression;

  public Unary(SymbolicExpression expression)
  {
    this.expression = expression;
  }

  public String toString()
  {
    return this.getName() + "(" + this.expression + ")";
  }

  public boolean equals(Object other)
  {
    if (other instanceof Unary)
    {
      return this.expression.equals(((Unary)other).expression);
    }
    else
    {
      return false;
    }
  }

  public int hashCode()
  {
    return this.expression.hashCode();
  }

  public int compareTo(SymbolicExpression other)
  {
    if (!(other instanceof Unary))
    {
      return super.compareTo(other);
    }
    Unary unaryOther = (Unary)other;

    if (this.getName().equals(other.getName()))
    {
      return this.expression.compareTo(unaryOther.expression);
    }

    if (this instanceof Negation)
    {
      return -1;
    }
    if (other instanceof Negation)
    {
      return 1;
    }
    return this.getName().compareTo(other.getName());
  }
}
