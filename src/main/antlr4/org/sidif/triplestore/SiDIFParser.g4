/**
 * Copyright © 2015-2017 BITPlan GmbH
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
 	( identifier identifier identifier ) |
 	( identifier IS identifier OF identifier ) 
 );
 
 value: (
 	literal IS identifier OF identifier
 );
 
 literal
 :
 	DOUBLE_QUOTE_STRING_LITERAL
 	| SINGLE_QUOTE_STRING_LITERAL
 	| IRI_LITERAL
 	| DATE_LITERAL
 	| INTEGER_LITERAL
 	| HEX_LITERAL
 	| BOOLEAN_LITERAL
 	| FLOAT_LITERAL
 ;
 
 identifier
 :
 	IDENTIFIER
 ;