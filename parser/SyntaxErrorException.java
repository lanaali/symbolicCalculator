package org.ioopm.calculator.parser;

public class SyntaxErrorException extends Exception
{
  SyntaxErrorException(String message)
  {
    super(message);
  }
}