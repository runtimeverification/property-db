package edu.uiuc.cs.fsl.propertydocs.util;

public class Pair<T1, T2> implements java.io.Serializable {
  public static final long serialVersionUID = 0L;
  public T1 first;
  public T2 second;

  public Pair(T1 first, T2 ssecond){
    this.first = first;
    this.second = second;
  }

  @Override
  public String toString(){
    return "<" + first.toString() + ", " + second.toString() + ">";
  }
}
