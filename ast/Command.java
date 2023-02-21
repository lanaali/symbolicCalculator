package org.ioopm.calculator.ast;

/// Super class for all commands, e.g. Clear and Quit.
public abstract class Command extends SymbolicExpression
{
  public int getPriority()
  {
    return 10;
  }

  public String toString()
  {
    return this.getName();
  }

  public boolean isCommand()
  {
    return true;
  }

  protected SymbolicExpression evalTopLevel(Environment vars) throws IllegalExpressionException
  {
    throw new IllegalExpressionException(this.getName() + " can not be evaluated");
  }

  protected SymbolicExpression evalChildren(Environment vars) throws IllegalExpressionException
  {
    return this;
  }
}
