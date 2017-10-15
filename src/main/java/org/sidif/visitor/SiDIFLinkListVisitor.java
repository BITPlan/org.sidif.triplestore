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

import java.util.List;

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
