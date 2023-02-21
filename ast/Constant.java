package org.ioopm.calculator.ast;

public class Constant extends Atom
{
  private double value;

  public Constant(double value)
  {
    this.value = value;
  }

  public boolean isConstant()
  {
    return true;
  }

  public String getName()
  {
    return "constant";
  }

  public double getValue()
  {
    return this.value;
  }

  public String toString()
  {
    return String.valueOf(this.value); //remember: cannot use method .toString() for primitive data types
  }

  public boolean equals(Object other)
  {
    if (other instanceof Constant)
    {
      return this.value == ((Constant)other).value;
    }
    else
    {
      return false;
    }
  }

  public int hashCode()
  {
    return Double.valueOf(this.value).hashCode(); //remember: cannot use method .hashCode() for primitive data types
  }

  protected SymbolicExpression evalTopLevel(Environment vars)
  {
    return this;
  }

  public int compareTo(SymbolicExpression other)
  {
    if (!(other instanceof Constant))
    {
      return super.compareTo(other);
    }
    return Double.compare(this.value, ((Constant)other).value);
  }
}
