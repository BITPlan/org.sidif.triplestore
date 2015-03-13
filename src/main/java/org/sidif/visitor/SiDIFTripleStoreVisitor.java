/**
 * Copyright (C) 2015 BITPlan GmbH
 *
 * Pater-Delp-Str. 1
 * D-47877 Willich-Schiefbahn
 *
 * http://www.bitplan.com
 * 
 */
package org.sidif.visitor;

import java.io.File;
import java.io.InputStream;

import org.sidif.parser.jjtree.SiDIF;
import org.sidif.parser.node.Link;
import org.sidif.parser.node.Links;
import org.sidif.parser.node.Literal;
import org.sidif.parser.node.SiDIFVisitor;
import org.sidif.parser.node.SimpleNode;
import org.sidif.parser.node.Value;
import org.sidif.triple.ObjectHolder;
import org.sidif.triple.Triple;
import org.sidif.triple.TripleStore;

/**
 * a dump visitor for SiDIF
 * 
 * @author wf
 *
 */
public class SiDIFTripleStoreVisitor implements SiDIFVisitor {

  /**
   * get a triple Store from the given sidifText
   * @param sidifText
   * @throws Exception
   * @return the tripleStore derived form the given sidifText
   */
  public static TripleStore fromSiDIFText(String sidifText) throws Exception {
    SiDIF sidif=SiDIF.fromText(sidifText);
    TripleStore result=fromSiDIF(sidif);
    return result;   
  }
  
  /**
   * get a TripleStore from the given sidif File
   * @param sidifFile
   * @return a triple Store
   * @throws Exception
   */
  public static TripleStore fromSiDIFFile(File sidifFile) throws Exception {
    SiDIF sidif=SiDIF.fromFile(sidifFile);
    TripleStore result=fromSiDIF(sidif);
    return result;
  }
  
  /**
   * create a TripleStore from the given sidif inputStream
   * @param in - the inputStream to read from
   * @return - the tripleStore
   * @throws Exception 
   */
  public static TripleStore fromSiDIFStream(InputStream in) throws Exception {
    SiDIF sidif=SiDIF.fromStream(in);
    TripleStore result=fromSiDIF(sidif);
    return result;
  }
  
  /**
   * create a TripleStore from the given sidif Parser
   * @param sidif
   * @return the tripleStore
   * @throws Exception
   */
  public static TripleStore fromSiDIF(SiDIF sidif) throws Exception {
    SiDIFVisitor visitor = new SiDIFTripleStoreVisitor();
    TripleStore tripleStore = new TripleStore();
    tripleStore = sidif.visit(visitor, tripleStore);
    return tripleStore;
  }
  
  private Object currentSubject;

  /**
   * set the current subject
   * 
   * @param subject
   */
  private void setCurrentSubject(Object subject) {
    this.currentSubject = subject;
  }

  @Override
  public Object visit(SimpleNode node, ObjectHolder data) {
    node.childrenAccept(this, data);
    return data;
  }

  @Override
  public Object visit(Links node, ObjectHolder data) {
    node.childrenAccept(this, data);
    return data;
  }

  /**
   * resolve the subject
   * 
   * @param subject
   *          - if it is "it" then return the name of the current subject
   * @return - the resolved subject
   */
  public Object resolve(Object subject) {
    // resolve "it" references
    if (subject.equals("it")) {
      subject = currentSubject;
    }
    return subject;
  }

  @Override
  public Object visit(Link node, ObjectHolder data) {
    TripleStore TripleStore = (TripleStore) data.getObject();
    Object subject = resolve(node.getSubject());
    Object target = resolve(node.getTarget());
    org.sidif.triple.Value<Object> value=new org.sidif.triple.Value<Object>();
    value.setObject(target);
    value.literal=false;
    value.type="Reference";
    Object link = node.getLink();
    Triple triple = TripleStore.add(subject, link, value);
    if (link.equals("isA")) {
      setCurrentSubject(subject);
    }
    node.childrenAccept(this, data);
    return data;
  }

  @Override
  public Object visit(Literal node, ObjectHolder data) {
    return data;
  }

  @Override
  public Object visit(Value node, ObjectHolder data) {
    TripleStore TripleStore = (TripleStore) data.getObject();
    Object concept = resolve(node.getConcept());
    Object object=node.getLiteral().getLiteralValue();
    Triple triple = TripleStore.add(concept, node.getProperty(), object);
    setCurrentSubject(concept);
    node.childrenAccept(this, data);
    return data;
  }

}
