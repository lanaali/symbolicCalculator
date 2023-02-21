package org.ioopm.calculator.tests;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.io.StringReader;
import java.util.List;
import org.ioopm.calculator.ast.*;
import org.ioopm.calculator.parser.*;
import org.junit.Test;

public class TestParser
{
  private static final List<Pair<String, String>> parsEvalTable = List.of(new Pair<>("3=x", "3.0"), new Pair<>(" x + y ", "y + 3.0"),
                                                                          new Pair<>("z+2=y", "z + 2.0"), new Pair<>("3=z", "3.0"), new Pair<>("y", "z + 2.0"), new Pair<>("y=f", "z + 2.0"),
                                                                          new Pair<>("f+z*2", "z + 8.0"), new Pair<>("z+x=g", "6.0"), new Pair<>("g", "6.0"),
                                                                          new Pair<>("f", "z + 2.0"), new Pair<>("z", "3.0"), new Pair<>("(f+z*z)", "z + 11.0"), new Pair<>("L", "6.022140857E23"),
                                                                          new Pair<>("5 = x = y", "5.0"), new Pair<>("x", "5.0"), new Pair<>("y", "5.0"), new Pair<>("0/0", "NaN"), new Pair<>("4/0", "Infinity"), new Pair<>("4/2", "2.0"));
  private static final String[] unParsableStrings = {"", "5=quit", "42=x+y", "3**5", "5 ++ x", "foo(x+y)", "6*(4+5", "(5+3))", "clear + vars"};
  private static final String[] commands = {"quit", "vars", "clear"};

  @Test
  public void testParsEval()
  {
    Environment environment = new Environment();
    for (Pair<String, String> pair : TestParser.parsEvalTable)
    {
      CalculatorParser parser = new CalculatorParser(new StringReader(pair.getFst() + "\n"));
      try
      {
        SymbolicExpression expression = parser.parseSymbolicExpression();
        String evaluated = expression.eval(environment).toString();
        assertEquals(pair.getSnd(), evaluated, "Evaluation missmatch, expected: " + pair.getSnd() + ", got: " + evaluated);
      }
      catch (SyntaxErrorException e)
      {
        fail("Pars error: tried to pars " + pair.getFst() + ", error: " + e);
      }
      catch (IllegalExpressionException e)
      {
        fail("Illegal expression: " + e);
      }
      catch (IOException e)
      {
        fail("Internal error: " + e);
      }
    }
  }

  @Test
  public void testUnParsable()
  {
    for (String line : TestParser.unParsableStrings) // can be this.unParsableStrings if method is not static
    {
      CalculatorParser parser = new CalculatorParser(new StringReader(line + "\n"));
      try
      {
        parser.parseSymbolicExpression();
        // the parsing should fail, so we should never come here
        fail("The parsing of " + line + " succeeded, but it should not.");
      }
      catch (SyntaxErrorException | IOException e)
      {
      }
    }
  }

  @Test
  public void testParsCommands()
  {
    for (String command : this.commands)
    {
      CalculatorParser parser = new CalculatorParser(new StringReader(command + "\n"));
      try
      {
        SymbolicExpression parsed = parser.parseSymbolicExpression();
        assertTrue(parsed instanceof Command, "Parsed command was not instance of Command");
        assertEquals(command, parsed.getName());
      }
      catch (SyntaxErrorException | IOException e)
      {
        fail("Failed to parse command " + command);
      }
    }
  }

  @Test
  public void testUnparsableFollowedParsable()
  {
    CalculatorParser parser = new CalculatorParser(new StringReader("vars + quit + clear\n 5 + 3\n"));
    try
    {
      try
      {
        parser.parseSymbolicExpression();
        // the parsing should fail, so we should never come here
        fail("The parsing of 'vars + quit + clear' succeeded, but it should not.");
      }
      catch (SyntaxErrorException e)
      {
      }
      assertEquals(new Constant(8), parser.parseSymbolicExpression().eval(new Environment()), "Bad evaluation of '5 + 3'.");
    }
    catch (SyntaxErrorException e)
    {
      fail("The parsing of '5 + 3' after the failed parsing of 'vars + quit + clear' failed.");
    }
    catch (IOException | IllegalExpressionException e)
    {
      fail("A very unexpected exception occurred: " + e);
    }
  }
}
