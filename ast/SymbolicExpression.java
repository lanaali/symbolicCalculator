package org.ioopm.calculator.ast;

import java.util.Comparator;

/// Super class for all expressions.
public abstract class SymbolicExpression implements Comparable<SymbolicExpression>
{
  /// Returns whether the expression is a constant or not.
  public boolean isConstant()
  {
    return false;
  }

  /// Returns the name of the type of the expression.
  abstract public String getName();

  /// Returns the precedence of the operator.
  public abstract int getPriority();

  /// Returns the value if this expression is a constant, otherwise throws an exception.
  public double getValue()
  {
    throw new RuntimeException("Expression is not a constant");
  }

  protected abstract SymbolicExpression evalTopLevel(Environment vars) throws IllegalExpressionException;

  protected abstract SymbolicExpression evalChildren(Environment vars) throws IllegalExpressionException;

  /// Returns the evaluated expression.
  public SymbolicExpression eval(Environment vars) throws IllegalExpressionException
  {
    return this.evalChildren(vars).evalTopLevel(vars);
  }

  /// Returns whether the expression is a command or not.
  public boolean isCommand()
  {
    return false;
  }

  private int orderingNumber()
  {
    if (this instanceof Atom)
      return 1;
    if (this instanceof Unary)
      return 2;
    if (this instanceof Binary)
      return 3;
    return 4; // this instanceof Command
  }

  public int compareTo(SymbolicExpression this, SymbolicExpression other)
  {
    return this.orderingNumber() - other.orderingNumber();
  }
}
