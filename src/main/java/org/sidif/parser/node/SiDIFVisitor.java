/**
 *
 * This file is part of the https://github.com/BITPlan/org.sidif.triplestore open source project
 *
 * Copyright © 2015-2018 BITPlan GmbH http://www.bitplan.com
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
/* Generated By:JavaCC: Do not edit this line. SiDIFVisitor.java Version 5.0 */
package org.sidif.parser.node;

import org.sidif.parser.jjtree.*;

public interface SiDIFVisitor
{
  public Object visit(SimpleNode node, org.sidif.triple.impl.ObjectHolder data);
  public Object visit(Links node, org.sidif.triple.impl.ObjectHolder data);
  public Object visit(Link node, org.sidif.triple.impl.ObjectHolder data);
  public Object visit(Value node, org.sidif.triple.impl.ObjectHolder data);
  public Object visit(Literal node, org.sidif.triple.impl.ObjectHolder data);
}
/* JavaCC - OriginalChecksum=747c34c893ad9c86b419c6b03ca8e47f (do not edit this line) */
