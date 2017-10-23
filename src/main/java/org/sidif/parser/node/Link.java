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
/* Generated By:JJTree: Do not edit this line. Link.java Version 4.3 */
/* JavaCCOptions:MULTI=true,NODE_USES_PARSER=false,VISITOR=true,TRACK_TOKENS=false,NODE_PREFIX=,NODE_EXTENDS=,NODE_FACTORY=,SUPPORT_CLASS_VISIBILITY_PUBLIC=true */
package org.sidif.parser.node;

import org.sidif.parser.jjtree.SiDIF;

public
class Link extends SimpleNode {
  private String link;
  private String target;
  private String subject;

  /**
   * @return the link
   */
  public String getLink() {
    return link;
  }

  /**
   * @param link the link to set
   */
  public void setLink(String link) {
    this.link = link;
  }

  /**
   * @return the target
   */
  public String getTarget() {
    return target;
  }

  /**
   * @param target the target to set
   */
  public void setTarget(String target) {
    this.target = target;
  }

  /**
   * @return the subject
   */
  public String getSubject() {
    return subject;
  }

  /**
   * @param subject the subject to set
   */
  public void setSubject(String subject) {
    this.subject = subject;
  }

  public Link(int id) {
    super(id);
  }

  public Link(SiDIF p, int id) {
    super(p, id);
  }


  /** Accept the visitor. **/
  public Object jjtAccept(SiDIFVisitor visitor, org.sidif.triple.impl.ObjectHolder data) {
    return visitor.visit(this, data);
  }

  /**
   * set a Link with the given subject link and target
   * @param subject
   * @param link
   * @param target
   */
  public void setLink(String subject, String link, String target) {
    this.subject=subject;
    this.link=link;
    this.target=target;
  }

}
/* JavaCC - OriginalChecksum=2ec7d21ab6b9009306f3a53927733e64 (do not edit this line) */
