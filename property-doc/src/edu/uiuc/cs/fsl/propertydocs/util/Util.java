package edu.uiuc.cs.fsl.propertydocs.util;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.TimeZone;

public class Util{
  public static String getDate(){
    Calendar calendar = new GregorianCalendar();
    String timezone = TimeZone.getDefault().getDisplayName();
    String day = (new String[] {"Sun", "Mon", "Tue", "Wed", "Thurs", "Fri", "Sat"}) 
         [calendar.get(Calendar.DAY_OF_WEEK) - 1];
    String month = (new String[] {"Jan", "Feb", "March", 
                                  "April", "May", "Jun", 
                                  "Jul", "Aug", "Sept",
                                  "Oct", "Nov", "Dec"})
         [calendar.get(Calendar.MONTH)];
    int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
    int hour = calendar.get(Calendar.HOUR);
    int minute = calendar.get(Calendar.MINUTE);
    int second = calendar.get(Calendar.SECOND);
    int year = calendar.get(Calendar.YEAR);
    return day + " " + month + " " + dayOfMonth + " " 
           + hour + ":" + minute + ":" + second + " " 
           + timezone + " " + year;
   }   
}

