package org.ioopm.calculator.ast;

public class Addition extends Binary
{
  public Addition(SymbolicExpression lhs, SymbolicExpression rhs)
  {
    super(lhs, rhs);
  }

  public String getName()
  {
    return "+";
  }

  public int getPriority()
  {
    return 6;
  }

  public boolean equals(Object other)
  {
    if (other instanceof Addition)
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
    if (this.lhs.isConstant() && this.rhs.isConstant())
    {
      return new Constant(this.lhs.getValue() + this.rhs.getValue());
    }
    if (this.lhs instanceof Negation)
    {
      Subtraction subtraction = new Subtraction(this.rhs, ((Negation)this.lhs).expression);
      return subtraction.evalTopLevel(vars);
    }
    if (this.rhs instanceof Negation)
    {
      Subtraction subtraction = new Subtraction(this.lhs, ((Negation)this.rhs).expression);
      return subtraction.evalTopLevel(vars);
    }
    if (this.lhs instanceof Addition)
    {
      Addition addition1 = new Addition(((Addition)this.lhs).rhs, this.rhs);
      SymbolicExpression rhs = addition1.evalTopLevel(vars);
      Addition addition2 = new Addition(((Addition)this.lhs).lhs, rhs);
      return addition2.evalTopLevel(vars);
    }
    if (this.rhs instanceof Addition)
    {
      Addition rhs = (Addition)this.rhs;
      if (this.lhs.isConstant() && rhs.lhs.isConstant())
      {
        Addition addition = new Addition(new Constant(this.lhs.getValue() + rhs.lhs.getValue()), rhs.rhs);
        return addition.evalTopLevel(vars);
      }
      if (this.lhs.compareTo(rhs.lhs) < 0)
      {
        Addition addition1 = new Addition(this.lhs, rhs.rhs);
        SymbolicExpression rhs2 = addition1.evalTopLevel(vars);
        Addition addition2 = new Addition(rhs.lhs, rhs2);
        return addition2.evalTopLevel(vars);
      }
      return this;
    }
    if (this.lhs.compareTo(this.rhs) < 0)
    {
      return new Addition(this.rhs, this.lhs);
    }
    return this;
  }

  protected SymbolicExpression evalChildren(Environment vars) throws IllegalExpressionException
  {
    SymbolicExpression reducedLhs = this.lhs.eval(vars);
    SymbolicExpression reducedRhs = this.rhs.eval(vars);
    return new Addition(reducedLhs, reducedRhs);
  }
}
