package edu.uiuc.cs.fsl.propertydocs.util;

public class DefaultMap<K,V> extends java.util.HashMap<K,V> implements java.io.Serializable {
  public static final long serialVersionUID = 0L;

  protected final V defaultValue;

  public DefaultMap(V defaultValue){
    this.defaultValue = defaultValue;
  }

  @Override
  public V get(Object key){
    V val = super.get(key);
    if(val == null) return defaultValue;
    return val;
  }
}
