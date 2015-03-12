/**
 * Copyright (C) 2015 BITPlan GmbH
 *
 * Pater-Delp-Str. 1
 * D-47877 Willich-Schiefbahn
 *
 * http://www.bitplan.com
 * 
 */
package org.sidif.triple;

import java.net.URISyntaxException;
import java.sql.Time;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * a value that might be a literal or an object
 * 
 * @author wf
 *
 * @param <T>
 */
public class Value<T> extends ObjectHolder<T> {
  public boolean literal;
  public String type;

  /**
   * get the date format for the given date format description adjusted to UTC
   * 
   * @param format
   * @return - the dateformat
   */
  public SimpleDateFormat getDateFormat(String format) {
    SimpleDateFormat f = new SimpleDateFormat(format);
    // f.setTimeZone(TimeZone.getTimeZone("UTC"));
    return f;
  }

  /**
   * to String for this Value
   */
  public String toString() {
    Object object = this.getObject();
    String result;
    if (object instanceof java.sql.Time) {
      result = object.toString();
    } else if (object instanceof java.sql.Timestamp) {
      SimpleDateFormat f = getDateFormat("yyyy-MM-dd HH:mm:ss");
      result = f.format(object);
    } else if (object instanceof java.util.Date) {
      SimpleDateFormat f = getDateFormat("yyyy-MM-dd");
      result = f.format(object);
    } else {
      result = object.toString();
    }
    return result;
  }

  /**
   * return me as a SiDIF String
   * 
   * @return the sidif representation
   */
  public String asSiDIF() {
    String result;
    if (type != null && type.equals("String")) {
      result = "\"" + toString() + "\"";
    } else {
      result = toString(); // +"("+type+")";
    }
    return result;
  }

  /**
   * get a TimeStamp with the given time value
   * 
   * @param time
   * @return the wrapped time
   */
  public static Value<Timestamp> getTimeStamp(long time) {
    Value<java.sql.Timestamp> result = new Value<java.sql.Timestamp>();
    result.setObject(new java.sql.Timestamp(time));
    result.literal = true;
    result.type = "Timestamp";
    return result;
  }

  /**
   * get a data value from the given long time
   * 
   * @param time
   * @return the wrapped date
   */
  public static Value<Date> getDate(long time) {
    Date date = new Date(time);
    Value<Date> result = getDate(date);
    return result;
  }

  /**
   * get a Date value
   * 
   * @param parseDate
   * @return the wrapped date
   */
  public static Value<Date> getDate(Date parseDate) {
    Value<Date> result = new Value<Date>();
    result.literal = true;
    result.setObject(parseDate);
    result.type = "Date";
    return result;
  }

  /**
   * get a Time Value
   * 
   * @param time
   * @return the wrapped time
   */
  public static Value<Time> getTime(long time) {
    Value<java.sql.Time> result = new Value<java.sql.Time>();
    result.setObject(new java.sql.Time(time));
    result.literal = true;
    result.type = "Time";
    return result;
  }

  /**
   * get a number for the given number string
   * 
   * @param numberString
   * @return the wrapped integer
   */
  public static Value<?> getInteger(String numberString) {
    Value<?> result = getInteger(new Integer(numberString));
    return result;
  }

  /**
   * get the number for the given Integer
   * 
   * @param pInteger
   * @return the wrapped integer
   */
  public static Value<?> getInteger(Integer pInteger) {
    Value<Integer> result = new Value<Integer>();
    result.setObject(pInteger);
    result.literal = true;
    result.type = "Integer";
    return result;
  }

  /**
   * get a number for the given number string
   * 
   * @param numberString
   * @return the wrapped double
   */
  public static Value<?> getDouble(String numberString) {
    Value<?> result = getDouble(new Double(numberString));
    return result;
  }

  /**
   * get the number for the given Double
   * 
   * @param pDouble
   * @return the wrapped double
   */
  public static Value<?> getDouble(Double pDouble) {
    Value<Double> result = new Value<Double>();
    result.setObject(pDouble);
    result.literal = true;
    result.type = "Double";
    return result;
  }

  /**
   * get a Character value
   * 
   * @param pChar
   * @return the wrapped char
   */
  public static Value<?> getCharacter(char pChar) {
    Value<Character> result = new Value<Character>();
    result.setObject(new Character(pChar));
    result.literal = true;
    result.type = "Char";
    return result;
  }

  /**
   * get a String value
   * 
   * @param string
   * @return the wrapped string
   */
  public static Value<?> getString(String string) {
    Value<String> result = new Value<String>();
    result.setObject(string);
    result.literal = true;
    result.type = "String";
    return result;
  }

  /**
   * get the given IRI value
   * 
   * @param iri
   * @return the wrapped iri
   * @throws URISyntaxException
   */
  public static Value<?> getIRI(String iri) throws URISyntaxException {
    Value<java.net.URI> result = new Value<java.net.URI>();
    result.setObject(new java.net.URI(iri));
    result.literal = true;
    result.type = "IRI";
    return result;
  }

  public static Value<?> getBoolean(String image) {
    Value<Boolean> result = new Value<Boolean>();
    result.setObject(new Boolean(image));
    result.literal = true;
    result.type = "Boolean";
    return result;
  }
}
