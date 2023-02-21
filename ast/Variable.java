package org.ioopm.calculator.ast;

public class Variable extends Atom
{
  private String identifier;

  public Variable(String identifier)
  {
    this.identifier = identifier;
  }

  public String getName()
  {
    return "variable";
  }

  public String toString()
  {
    return this.identifier;
  }

  public boolean equals(Object other)
  {
    if (other instanceof Variable)
    {
      return this.identifier.equals(((Variable)other).identifier);
    }
    else
    {
      return false;
    }
  }

  public int hashCode()
  {
    return this.identifier.hashCode();
  }

  protected SymbolicExpression evalTopLevel(Environment vars)
  {
    if (vars.containsKey(this))
    {
      return vars.get(this);
    }
    else
    {
      return this;
    }
  }

  public int compareTo(SymbolicExpression other)
  {
    if (!(other instanceof Variable))
    {
      return super.compareTo(other);
    }
    return this.identifier.compareTo(((Variable)other).identifier);
  }
}
