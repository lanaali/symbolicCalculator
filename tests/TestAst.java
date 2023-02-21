package org.ioopm.calculator.tests;

import static org.junit.jupiter.api.Assertions.*;

import org.ioopm.calculator.ast.*;
import org.junit.Test;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;

public class TestAst
{
  private void testToString(SymbolicExpression expression, String string)
  {
    assertEquals(string, expression.toString(), "ToString failed: got " + expression + ", expected " + string);
  }

  private void testEval(SymbolicExpression toEval, SymbolicExpression expected, Environment vars)
  {
    try
    {
      SymbolicExpression evaluated = toEval.eval(vars);
      assertEquals(expected, evaluated, "Eval failed: " + toEval + " became " + evaluated + ", expected " + expected);
    }
    catch (IllegalExpressionException e)
    {
      fail("Illegal expression: " + e);
    }
  }

  @BeforeAll
  static void initAll()
  {
  }

  @BeforeEach
  void init()
  {
  }

  @Test
  public void testCostant()
  {
    SymbolicExpression expression = new Constant(42.0);
    testToString(expression, "42.0");
    testEval(expression, expression, new Environment());
  }

  @Test
  public void testNegation()
  {
    SymbolicExpression expression = new Negation(new Constant(42));
    testToString(expression, "-42.0");
    testEval(expression, new Constant(-42), new Environment());
  }

  @Test
  public void testExp()
  {
    SymbolicExpression expression = new Exp(new Constant(0));
    testToString(expression, "exp(0.0)");
    testEval(expression, new Constant(1), new Environment());
  }

  @Test
  public void testDivision()
  {
    SymbolicExpression expression = new Division(new Addition(new Constant(42), new Constant(42)), new Constant(12));
    testToString(expression, "(42.0 + 42.0) / 12.0");
    testEval(expression, new Constant(7), new Environment());
  }

  @Test
  public void testAssignment()
  {
    SymbolicExpression expression = new Assignment(new Multiplication(new Constant(42), new Constant(42)), new Variable("x"));
    testToString(expression, "42.0 * 42.0 = x");
    Environment environment = new Environment();
    testEval(expression, new Constant(1764), environment);
    assert environment.get(new Variable("x")).equals(new Constant(1764));
  }

  @Test
  public void testVariable()
  {
    SymbolicExpression expression = new Variable("Elias Castegren");
    testToString(expression, "Elias Castegren");
    Environment environment = new Environment();
    testEval(expression, expression, environment);
    environment.put(new Variable("Elias Castegren"), new Constant(Double.POSITIVE_INFINITY));
    testEval(expression, new Constant(Double.POSITIVE_INFINITY), environment);
    assert environment.get(new Variable("Elias Castegren")).equals(new Constant(Double.POSITIVE_INFINITY));
  }

  @AfterEach
  void tearDown()
  {
  }

  @AfterAll
  static void tearDownAll()
  {
  }
}