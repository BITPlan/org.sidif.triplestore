/**
 * Copyright © 2015-2017 BITPlan GmbH
 * Author: Wolfgang Fahl
 *
 * this is the lexer grammar part of the  Language definition for the Simple Graph Navigation Language
 * see http://signal.bitplan.com
 *
 * it is specified using ANTLR syntax and uses the ANTLR V4 parser generator
 * see http://www.antlr.org
 * 
 * for Eclipse you might want to install the ANTLR IDE support:
 * https://github.com/jknack/antlr4ide
 */
lexer grammar SiDIFTokenLexer;

 import LexBasic;

 // Keywords

 IS
 :
 	'is'
 ;

 OF
 :
 	'of'
 ;

 // Literals

 INTEGER_LITERAL
 :
 	DecimalNumeral
 ;

 // Identifiers

 IDENTIFIER
 :
 	NameStartChar NameChar*
 ;

 SINGLE_QUOTE_STRING_LITERAL
 :
 	SQuoteLiteral
 ;

 DOUBLE_QUOTE_STRING_LITERAL
 :
 	DQuoteLiteral
 ;

 BOOLEAN_LITERAL
 :
 	BoolLiteral
 ;

 MULTI_LINE_COMMENT
 :
 	DocComment+ -> skip
 ;

 SINGLE_LINE_COMMENT
 :
 	LineComment+ EOF? -> skip
 ;

 WHITESPACE
 :
 	Ws+ -> skip
 ;