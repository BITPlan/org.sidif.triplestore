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

import java.io.File;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.Parser;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.ParseTreeWalker;
import org.sidif.triple.Triple;
import org.sidif.triplestore.parser.SiDIFParser;
import org.sidif.triplestore.parser.SiDIFParser.TriplesContext;
import org.sidif.triplestore.parser.SiDIFParserBaseListener;
import org.sidif.triplestore.parser.SiDIFTokenLexer;
import org.sidif.util.SiDIFReader;

import com.bitplan.antlr.LanguageParser;

/**
 * Wrapper for SiDIF Parser to improve testability
 * 
 * @author wf
 *
 */
public class SiDIFLanguageParser extends LanguageParser implements SiDIFReader {

  private SiDIFTokenLexer lexer;
  private SiDIFParser parser;

  @Override
  protected ParseTree getRootContext(Parser parser) {
    if (!(parser instanceof SiDIFParser)) {
      throw new RuntimeException(
          "wrong parser type for getRootContext, expected Rule but got "
              + parser.getClass().getName());
    } else {
      SiDIFParser sidifParser = (SiDIFParser) parser;
      return sidifParser.triples();
    }
  }

  @Override
  protected ParseTree parse(CharStream in, String inputText) throws Exception {
    lexer = new SiDIFTokenLexer(in);
    parser = new SiDIFParser(getTokens(lexer));
    ParseTree result = super.parse(lexer, parser);
    return result;
  }

  @Override
  public void showParseTree() {
    super.showParseTree(parser);
  }

  /**
   * link listener
   * @author wf
   *
   */
  public class LinkListener extends SiDIFParserBaseListener {

    List<Triple> tripleList=new ArrayList<Triple>();
   
    @Override
    public void exitTripleline(SiDIFParser.TriplelineContext ctx) {
      tripleList.add(ctx.triple);
    }

  }

  @Override
  public List<Triple> fromSiDIFText(String sidifText) throws Exception {
    CharStream in = CharStreams.fromString(sidifText);
    List<Triple> tripleList=fromCharStream(in);
    return tripleList;
  }

  @Override
  public List<Triple> fromSiDIFFile(File sidifFile) throws Exception {
    this.setSourceFileName(sidifFile.getPath());
    CharStream in = CharStreams.fromFileName(this.getSourceFileName());
    List<Triple> tripleList=fromCharStream(in);
    return tripleList;
  }

  @Override
  public List<Triple> fromSiDIFStream(InputStream is) throws Exception {
    CharStream in = CharStreams.fromStream(is);
    List<Triple> tripleList=fromCharStream(in);
    return tripleList;
  }
  
  /**
   * get the list of triples from the given CharStream
   * @param in - the CharStream to parse
   * @return - the list of triples
   * @throws Exception
   */
  public List<Triple> fromCharStream(CharStream in) throws Exception {
    lexer = new SiDIFTokenLexer(in);
    parser = new SiDIFParser(getTokens(lexer));
    TriplesContext triplesContext = parser.triples();
    ParseTreeWalker walker = new ParseTreeWalker();
    LinkListener linkListener=new LinkListener();
    walker.walk(linkListener,triplesContext);
    
    return linkListener.tripleList;
  }

}
