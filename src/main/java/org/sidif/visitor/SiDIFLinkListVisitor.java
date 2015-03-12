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

import java.util.List;

import org.sidif.parser.node.Link;
import org.sidif.parser.node.Links;
import org.sidif.parser.node.Literal;
import org.sidif.parser.node.SiDIFVisitor;
import org.sidif.parser.node.SimpleNode;
import org.sidif.parser.node.Value;
import org.sidif.triple.ObjectHolder;

/**
 * a dump visitor for SiDIF
 * @author wf
 *
 */
public class SiDIFLinkListVisitor implements SiDIFVisitor {

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
		List<SimpleNode> nodeList=(List<SimpleNode>) data.getObject();
    nodeList.add(node);
    node.childrenAccept(this,data);
    return data;   
	}

	@Override
	public Object visit(Literal node, ObjectHolder data) {
		return data;
	}

	@Override
	public Object visit(Value node, ObjectHolder data) {
		List<SimpleNode> nodeList=(List<SimpleNode>) data.getObject();
    nodeList.add(node);
    node.childrenAccept(this,data);
		return data;
	}

}
