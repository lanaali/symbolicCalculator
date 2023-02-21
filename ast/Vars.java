package org.ioopm.calculator.ast;

public class Vars extends Command
{
  private static final Vars theInstance = new Vars();
  private Vars() {}

  public String getName()
  {
    return "vars";
  }

  public static Vars instance()
  {
    return theInstance;
  }

  public boolean equals(Object other)
  {
    return other instanceof Vars;
  }

  public int hashCode()
  {
    return 0;
  }
}
