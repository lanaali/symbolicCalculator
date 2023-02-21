package org.ioopm.calculator;

import java.io.IOException;
import java.io.PipedReader;
import java.io.PipedWriter;
import java.util.Map;
import org.ioopm.calculator.ast.*;
import org.ioopm.calculator.parser.*;

/// The main class of the program.
public class Calculator
{
  public static void main(String[] args)
  {
    try
    {
      PipedWriter writer = new PipedWriter();
      PipedReader reader = new PipedReader(writer);
      final CalculatorParser parser = new CalculatorParser(reader);
      final Environment vars = new Environment();

      int enteredExpressions = 0;
      int successfullyEvaluated = 0;
      int fullyEvaluated = 0;

      while (true)
      {
        try
        {
          System.out.print("? ");
          String input = System.console().readLine();
          if (input == null)
          {
            System.out.println();
            break;
          }
          enteredExpressions++;
          writer.write(input + "\n");
          writer.flush();
          SymbolicExpression parsedExpression = parser.parseSymbolicExpression();

          if (parsedExpression.isCommand())
          {
            enteredExpressions--;
            if (parsedExpression == Quit.instance())
            {
              System.out.println("Entered expressions: " + enteredExpressions);
              System.out.println("Successfully evaluated: " + successfullyEvaluated);
              System.out.println("Fully evaluated: " + fullyEvaluated);
              System.out.println("Thank you for using calculator, bye!");
              break;
            }
            if (parsedExpression == Vars.instance())
            {
              for (Map.Entry<Variable, SymbolicExpression> entry : vars.entrySet())
              {
                System.out.println("  " + entry.getKey() + " = " + entry.getValue());
              }
            }
            else if (parsedExpression == Clear.instance())
            {
              vars.clear();
            }
            else
            {
              System.out.println("Internal error: unknown command " + parsedExpression);
            }
          }
          else
          {
            SymbolicExpression evaluated = parsedExpression.eval(vars);
            successfullyEvaluated++;
            if (evaluated.isConstant())
            {
              fullyEvaluated++;
            }
            vars.put(new Variable("ans"), evaluated);
            System.out.println(evaluated);
          }
        }
        catch (SyntaxErrorException e)
        {
          System.out.println("Syntax error: " + e);
        }
        catch (IllegalExpressionException e)
        {
          System.out.println("Illegal expression: " + e);
        }
        catch (Exception e)
        {
          System.out.println("Error: unexpected exception: " + e);
          System.out.println("Exiting");
          break;
        }
        System.out.println();
      }
      writer.close();
      reader.close();
    }
    catch (Exception e)
    {
      System.out.println("Error: unexpected exception: " + e);
      System.out.println("Exiting");
    }
  }
}
