/**
 * Copyright 2017 BITPlan GmbH
 * Author: Wolfgang Fahl
 *
 * this is the Language definition for the Simple Graph Navigation Language
 * see http://signal.bitplan.com
 *
 * it is specified using antlr syntax and uses the ANTLR V4 parser generator
 * see http://www.antlr.org
 * 
 * for Eclipse you might want to install the IDE support:
 * https://github.com/jknack/antlr4ide
 * 
 */
parser grammar SiDIFParser;

 options {
 // use separate Lexer
 	tokenVocab = SiDIFTokenLexer;
 }
 
  /* Grammar Start */
 links:
 (
 	(link | value )+
 	EOF
 );
 
 link: (
 	( IDENTIFIER IDENTIFIER IDENTIFIER ) |
 	( IDENTIFIER IS IDENTIFIER OF IDENTIFIER ) 
 );
 
 value: (
 	LITERAL IS IDENTIFIER OF IDENTIFIER
 );
 
 