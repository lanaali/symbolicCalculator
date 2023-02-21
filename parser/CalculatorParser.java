package org.ioopm.calculator.parser;

import java.io.IOException;
import java.io.Reader;
import java.io.StreamTokenizer;
import java.util.Arrays;
import org.ioopm.calculator.ast.*;

/// The parser is created with a reader.
/// For each line from the reader the parser tries to parse an expression.
public class CalculatorParser
{
  private static final String[] commands = {"quit", "vars", "clear"};
  private static final String[] otherKeyWords = {"exp", "log", "sin", "cos"};

  private StreamTokenizer st;

  public CalculatorParser(Reader reader)
  {
    this.st = new StreamTokenizer(reader);
    this.st.ordinaryChar('-');
    this.st.ordinaryChar('/');
    this.st.eolIsSignificant(true);
  }

  /// Returns the parsed expression.
  public SymbolicExpression parseSymbolicExpression() throws IOException, SyntaxErrorException
  {
    try
    {
      return this.topLevel();
    }
    finally
    {
      this.discardRestOfLine();
    }
  }

  private void discardRestOfLine() throws IOException
  {
    while (this.st.ttype != StreamTokenizer.TT_EOL && this.st.ttype != StreamTokenizer.TT_EOF)
    {
      this.st.nextToken();
    }
  }

  private Unary unary() throws IOException, SyntaxErrorException
  {
    this.st.nextToken();
    if (this.st.ttype == '-')
    {
      return new Negation(this.primary());
    }
    else if (this.st.ttype == StreamTokenizer.TT_WORD)
    {
      if (this.st.sval.equals("exp"))
      {
        return new Exp(this.primary());
      }
      else if (this.st.sval.equals("log"))
      {
        return new Log(this.primary());
      }
      else if (this.st.sval.equals("sin"))
      {
        return new Sin(this.primary());
      }
      else if (this.st.sval.equals("cos"))
      {
        return new Cos(this.primary());
      }
      else
      {
        throw this.error("Expected a unary operator, got: " + this.st.sval);
      }
    }
    else
    {
      throw this.error("Expected an identifier, minus sign '-' or unary operator"); // not a word (exp,.., or invalid word) nor a minus sign.
    }
  }

  private SymbolicExpression primary() throws IOException, SyntaxErrorException
  {
    this.st.nextToken();
    if (this.st.ttype == '(')
    {
      SymbolicExpression result = this.assignment();
      if (this.st.nextToken() != ')')
      {
        throw this.error("Expected closing parenthesis ')'");
      }
      return result;
    }
    else if (this.st.ttype == StreamTokenizer.TT_NUMBER)
    {
      return new Constant(this.st.nval);
    }
    else if (this.st.ttype == StreamTokenizer.TT_WORD && this.validIdentifier(this.st.sval))
    {
      if (Constants.namedConstants.containsKey(this.st.sval))
      {
        return new NamedConstant(this.st.sval);
      }
      return new Variable(this.st.sval);
    }
    else
    {
      this.st.pushBack();
      return this.unary();
    }
  }

  private static boolean validIdentifier(String identifier)
  {
    return !Arrays.asList(CalculatorParser.commands).contains(identifier) && !Arrays.asList(CalculatorParser.otherKeyWords).contains(identifier);
  }

  private SymbolicExpression term() throws IOException, SyntaxErrorException
  {
    SymbolicExpression factor = this.primary();
    this.st.nextToken();
    if (this.st.ttype == '*')
    {
      return new Multiplication(factor, this.term());
    }
    else if (this.st.ttype == '/')
    {
      return new Division(factor, this.term());
    }
    else
    {
      this.st.pushBack();
      return factor;
    }
  }

  private SymbolicExpression expression() throws IOException, SyntaxErrorException
  {
    SymbolicExpression term = this.term();
    this.st.nextToken();
    if (this.st.ttype == '+')
    {
      return new Addition(term, this.expression());
    }
    else if (this.st.ttype == '-')
    {
      return new Subtraction(term, this.expression());
    }
    else
    {
      this.st.pushBack();
      return term;
    }
  }

  private SymbolicExpression assignment() throws IOException, SyntaxErrorException
  {
    SymbolicExpression expression = this.expression();
    while (this.st.nextToken() == '=')
    {
      this.st.nextToken();
      if (!(this.st.ttype == StreamTokenizer.TT_WORD && this.validIdentifier(this.st.sval)))
      {
        throw this.error("Expected identifier");
      }
      Variable identifier = new Variable(this.st.sval);
      if (Constants.namedConstants.containsKey(this.st.sval))
      {
        throw this.error("Is a named constant");
      }
      expression = new Assignment(expression, identifier);
    }
    this.st.pushBack();
    return expression;
  }

  private Command command() throws IOException, SyntaxErrorException
  {
    this.st.nextToken();
    if (this.st.ttype == StreamTokenizer.TT_WORD)
    {
      if (this.st.sval.equals("quit"))
      {
        return Quit.instance();
      }
      if (this.st.sval.equals("vars"))
      {
        return Vars.instance();
      }
      if (this.st.sval.equals("clear"))
      {
        return Clear.instance();
      }
    }
    throw this.error("Expected command");
  }

  private SymbolicExpression statement() throws IOException, SyntaxErrorException
  {
    try
    {
      return this.command();
    }
    catch (SyntaxErrorException e)
    {
      // this.command() consumes exactly one token,
      // so we can safely use pushBack() to get back to the state before we tried to parse a command.
      this.st.pushBack();
      return this.assignment();
    }
  }

  private SymbolicExpression topLevel() throws IOException, SyntaxErrorException
  {
    SymbolicExpression statement = this.statement();
    this.st.nextToken();
    if (this.st.ttype != StreamTokenizer.TT_EOL && this.st.ttype != StreamTokenizer.TT_EOF)
    {
      throw this.error("Expected end of line or binary operator");
    }
    return statement;
  }

  private SyntaxErrorException error(String message)
  {
    String found;
    switch (this.st.ttype)
    {
    case StreamTokenizer.TT_WORD:
      found = this.st.sval;
      break;
    case StreamTokenizer.TT_EOL:
      found = "New line";
      break;
    case StreamTokenizer.TT_EOF:
      found = "End of file";
      break;
    case StreamTokenizer.TT_NUMBER:
      found = ((Double)this.st.nval).toString();
      break;
    default:
      found = Character.toString((char)this.st.ttype);
    }
    return new SyntaxErrorException(message + ", found: " + found);
  }
}
