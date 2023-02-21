package org.ioopm.calculator.ast;

/// Super class for all binary operators, e.g. Addition and Division.
public abstract class Binary extends SymbolicExpression
{
  protected SymbolicExpression lhs;
  protected SymbolicExpression rhs;

  public Binary(SymbolicExpression lhs, SymbolicExpression rhs)
  {
    this.lhs = lhs;
    this.rhs = rhs;
  }

  public String toString()
  {
    String result = "";

    if (this.lhs.getPriority() < this.getPriority())
    {
      result += "(" + this.lhs + ")";
    }
    else
    {
      result += this.lhs;
    }
    result += " " + this.getName() + " ";

    if (this.rhs.getPriority() < this.getPriority())
    {
      result += "(" + this.rhs + ")";
    }
    else
    {
      result += this.rhs;
    }

    return result;
  }

  public boolean equals(Object other)
  {
    if (other instanceof Binary)
    {
      Binary otherCasted = (Binary)other;
      return this.lhs.equals(otherCasted.lhs) && this.rhs.equals(otherCasted.rhs);
    }
    else
    {
      return false;
    }
  }

  public int hashCode()
  {
    return this.lhs.hashCode() + this.rhs.hashCode();
  }

  private int orderingNumber()
  {
    if (this instanceof Multiplication)
    {
      return 1;
    }
    if (this instanceof Division)
    {
      return 2;
    }
    if (this instanceof Addition)
    {
      return 3;
    }
    if (this instanceof Subtraction)
    {
      return 4;
    }
    return 5; //this instance of Assignment
  }

  public int compareTo(SymbolicExpression other)
  {
    if (!(other instanceof Binary))
    {
      return super.compareTo(other);
    }
    Binary binaryOther = (Binary)other;

    int thisN = this.orderingNumber();
    int otherN = binaryOther.orderingNumber();

    if (thisN == otherN)
    {
      int lhsCnp = this.lhs.compareTo(binaryOther.lhs);
      if (lhsCnp != 0)
      {
        return lhsCnp;
      }
      int rhsCnp = this.rhs.compareTo(binaryOther.rhs);
      return rhsCnp;
    }

    return thisN - otherN;
  }
}
