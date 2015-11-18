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

import org.sidif.parser.node.Link;
import org.sidif.parser.node.Links;
import org.sidif.parser.node.Literal;
import org.sidif.parser.node.SiDIFVisitor;
import org.sidif.parser.node.SimpleNode;
import org.sidif.parser.node.Value;
import org.sidif.triple.impl.ObjectHolder;

/**
 * a dump visitor for SiDIF
 * @author wf
 *
 */
public class SiDIFDumpVisitor implements SiDIFVisitor {

  
	@Override
	public Object visit(SimpleNode node, ObjectHolder data) {
	  node.childrenAccept(this,data);
		return data;
	}

	@Override
	public Object visit(Links node, ObjectHolder data) {
	  node.childrenAccept(this,data);
		return data;
	}
	

	@Override
	public Object visit(Link node, ObjectHolder data) {
    ObjectHolder<String> stringHolder=(ObjectHolder<String>) data;
    String dump=stringHolder.getObject();
    
    dump+=node.getSubject()+" -- "+node.getLink()+" --> "+node.getTarget()+"\n";
    
    stringHolder.setObject(dump);
    node.childrenAccept(this,data);
    return data;   
	}

	@Override
	public Object visit(Literal node, ObjectHolder data) {
		node.childrenAccept(this,data);
		return data;
	}

	@Override
	public Object visit(Value node, ObjectHolder data) {
    ObjectHolder<String> stringHolder=(ObjectHolder<String>) data;
    String dump=stringHolder.getObject();
    Literal literal=node.getLiteral();
    org.sidif.triple.Value<?> literalValue=literal.getLiteralValue();
    dump+=node.getConcept()+"."+node.getProperty()+"="+literal.getLiteral()+"("+literalValue.type+")\n";
    stringHolder.setObject(dump);
    node.childrenAccept(this,data);
    return data;   
	}

}
