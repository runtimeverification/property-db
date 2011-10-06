package edu.uiuc.cs.fsl.propertydocs.util;

public class PropertyMap extends java.util.HashMap<String, DefaultMap<String, Integer>>{
  private final Integer defaultValue; 
  public PropertyMap(Integer i){
    defaultValue = i;
  }

  @Override
  @SuppressWarnings("unchecked")
  public DefaultMap<String, Integer> get(Object key){
    DefaultMap<String, Integer> val = super.get(key);
    if(val == null){
       val = new DefaultMap<String, Integer>(defaultValue);
       put((String) key, val);
    } 
    return val;
  }
}
