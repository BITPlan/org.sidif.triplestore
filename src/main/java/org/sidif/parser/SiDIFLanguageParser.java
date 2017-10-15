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
package org.sidif.parser;

import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.Parser;
import org.antlr.v4.runtime.tree.ParseTree;
import org.sidif.triplestore.SiDIFParser;
import org.sidif.triplestore.SiDIFTokenLexer;

import com.bitplan.antlr.LanguageParser;

/**
 * Wrapper for SiDIF Parser to improve testability
 * @author wf
 *
 */
public class SiDIFLanguageParser extends LanguageParser {

  private SiDIFTokenLexer lexer;
  private SiDIFParser parser;

  @Override
  protected ParseTree getRootContext(Parser parser) {
    if (!(parser instanceof SiDIFParser)) {
      throw new RuntimeException("wrong parser type for getRootContext, expected Rule but got "+parser.getClass().getName());
    } else {
      SiDIFParser sidifParser=(SiDIFParser) parser;
      return sidifParser.links();
    }
  }

  @Override
  protected ParseTree parse(CharStream in, String inputText) throws Exception {
    lexer = new SiDIFTokenLexer(in);
    parser=new SiDIFParser(getTokens(lexer));
    ParseTree result=super.parse(lexer,parser);
    return result;
  }

  @Override
  public void showParseTree() {
    super.showParseTree(parser);
  }

}
