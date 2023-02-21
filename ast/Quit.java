package org.ioopm.calculator.ast;

public class Quit extends Command
{
  private static final Quit theInstance = new Quit();
  private Quit() {}

  public String getName()
  {
    return "quit";
  }

  public static Quit instance()
  {
    return theInstance;
  }

  public boolean equals(Object other)
  {
    return other instanceof Quit;
  }

  public int hashCode()
  {
    return 0;
  }
}
