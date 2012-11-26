package edu.uiuc.cs.fsl.propertydocs.util;

import com.sun.javadoc.SourcePosition;

import java.io.File;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.TimeZone;


public class PositionWrapper {
  private int column;
  private File file;
  private int line;
  private int hashCode;
  
  public String toString(){
    return file.toString() + ":" + line;
  }

  public PositionWrapper(SourcePosition p){
    column = p.column();
    file   = p.file();
    line   = p.line();
    hashCode = column ^ file.hashCode() ^ line;
  }

  @Override public int hashCode(){
    return hashCode;
  }

  @Override public boolean equals(Object o){
    if(!(o instanceof PositionWrapper)) return false;
    PositionWrapper p = (PositionWrapper) o;
    return (column == p.column) && (line == p.line) && (file.equals(p.file));
  }
}

