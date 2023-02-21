package org.ioopm.calculator.ast;

/// Super class for all atomic expressions, e.g. Constant and Variable.
public abstract class Atom extends SymbolicExpression
{
  public int getPriority()
  {
    return 10;
  }

  protected SymbolicExpression evalChildren(Environment vars) throws IllegalExpressionException
  {
    return this;
  }

  private int orderingNumber()
  {
    if (this instanceof Constant)
      return 1;
    if (this instanceof Variable)
      return 2;
    return 3; //this instanceof NamedConstant
  }

  public int compareTo(SymbolicExpression other)
  {
    if (!(other instanceof Atom))
    {
      return super.compareTo(other);
    }
    return this.orderingNumber() - ((Atom)other).orderingNumber();
  }
}
