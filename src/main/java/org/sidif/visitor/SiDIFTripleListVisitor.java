/**
 *
 * This file is part of the https://github.com/BITPlan/org.sidif.triplestore open source project
 *
 * Copyright © 2015-2017 BITPlan GmbH https://github.com/BITPlan
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 *
 *  You may obtain a copy of the License at
 *
 *  http:www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.sidif.visitor;

import java.io.File;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.sidif.parser.jjtree.SiDIF;
import org.sidif.parser.node.Link;
import org.sidif.parser.node.Links;
import org.sidif.parser.node.Literal;
import org.sidif.parser.node.SiDIFVisitor;
import org.sidif.parser.node.SimpleNode;
import org.sidif.parser.node.Value;
import org.sidif.triple.Triple;
import org.sidif.triple.impl.ObjectHolder;
import org.sidif.triple.impl.TripleImpl;
import org.sidif.util.SiDIFReader;

/**
 * a visitor for SiDIF that adds triples to a list of Triples
 * 
 * @author wf
 *
 */
public class SiDIFTripleListVisitor implements SiDIFVisitor, SiDIFReader {

  /**
   * get a list of triples from the given sidifText
   * @param sidifText
   * @throws Exception
   * @return the list of triples derived form the given sidifText
   */
  public List<Triple> fromSiDIFText(String sidifText) throws Exception {
    SiDIF sidif=SiDIF.fromText(sidifText);
    List<Triple> result=fromSiDIF(sidif);
    return result;   
  }
  
  /**
   * get a list of triples from the given SiDIF File
   * @param sidifFile
   * @return a list of triples 
   * @throws Exception
   */
  public List<Triple> fromSiDIFFile(File sidifFile) throws Exception {
    SiDIF sidif=SiDIF.fromFile(sidifFile);
    List<Triple> result=fromSiDIF(sidif);
    return result;
  }
  
  /**
   * create a TripleStore from the given sidif inputStream
   * @param in - the inputStream to read from
   * @return - the list of triples 
   * @throws Exception 
   */
  public List<Triple> fromSiDIFStream(InputStream in) throws Exception {
    SiDIF sidif=SiDIF.fromStream(in);
    List<Triple> result=fromSiDIF(sidif);
    return result;
  }
  
  /**
   * create a list of triples from the given sidif Parser
   * @param sidif
   * @return the list of triples
   * @throws Exception
   */
  public static List<Triple> fromSiDIF(SiDIF sidif) throws Exception {
    SiDIFVisitor visitor = new SiDIFTripleListVisitor();
    List<Triple> tripleList=new ArrayList<Triple>();
    sidif.visit(visitor, tripleList);
    return tripleList;
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
  @SuppressWarnings({  "rawtypes" })
  public Object visit(SimpleNode node, ObjectHolder data) {
    node.childrenAccept(this, data);
    return data;
  }

  @Override
  @SuppressWarnings({  "rawtypes" })
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

  @SuppressWarnings({ "unchecked", "rawtypes" })
  @Override
  public Object visit(Link node, ObjectHolder data) {
    List<Triple> tripleList = (List<Triple>) data.getObject();
    Object subject = resolve(node.getSubject());
    Object target = resolve(node.getTarget());
    org.sidif.triple.Value<Object> value=new org.sidif.triple.Value<Object>();
    value.setObject(target);
    value.literal=false;
    value.type="Reference";
    Object link = node.getLink();
    Triple triple = new TripleImpl(subject, link, value);
    tripleList.add(triple);
    if (link.equals("isA")) {
      setCurrentSubject(subject);
    }
    node.childrenAccept(this, data);
    return data;
  }

  @Override
  @SuppressWarnings({ "rawtypes" })
  public Object visit(Literal node, ObjectHolder data) {
    return data;
  }

  @Override
  @SuppressWarnings({ "unchecked", "rawtypes" })
  public Object visit(Value node, ObjectHolder data) {
    List<Triple> tripleList= (List<Triple>) data.getObject();
    Object concept = resolve(node.getConcept());
    Object object=node.getLiteral().getLiteralValue();
    Triple triple = new TripleImpl(concept, node.getProperty(), object);
    tripleList.add(triple);
    setCurrentSubject(concept);
    node.childrenAccept(this, data);
    return data;
  }

}
