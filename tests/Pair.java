package org.ioopm.calculator.tests;

class Pair<T, U>
{
  private T fst;
  private U snd;

  public Pair(T fst, U snd)
  {
    this.fst = fst;
    this.snd = snd;
  }

  public T getFst()
  {
    return this.fst;
  }

  public U getSnd()
  {
    return this.snd;
  }
}