package org.ioopm.calculator.ast;

public class NamedConstant extends Atom
{
  private String name;

  public NamedConstant(String name)
  {
    this.name = name;
  }

  public String getName()
  {
    return "namedConstant";
  }

  protected SymbolicExpression evalTopLevel(Environment vars) throws IllegalExpressionException
  {
    Double result = Constants.namedConstants.get(this.name);
    if (result == null)
    {
      throw new IllegalExpressionException(this.name + " is not a named constant");
    }
    return new Constant(result);
  }

  public int compareTo(SymbolicExpression other)
  {
    if (!(other instanceof NamedConstant))
    {
      return super.compareTo(other);
    }
    return this.name.compareTo(((NamedConstant)other).name);
  }
}
