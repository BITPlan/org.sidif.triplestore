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

 DATE_TIME_LITERAL
 :
 	DateLiteral Hws TimeLiteral
 ;

 DATE_LITERAL
 :
 	DateLiteral
 ;

 TIME_LITERAL
 :
 	TimeLiteral
 ;

 FLOAT_LITERAL
 :
 	FloatLiteral
 ;

 HEX_LITERAL
 :
 	HexLiteral
 ;

 INTEGER_LITERAL
 :
 	(
 		Minus
 		| Plus
 	)? DecimalNumeral
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

IRI_LITERAL
:
  SCHEME ':' ([A-Za-z@]+)? ('//' [A-Za-z]+)? (Dot [A-Za-z]+)* ('/' ISEGMENT)*
;


// IRI fragments
fragment ALPHA
:
 [A-Za-z]
;

fragment DIGIT
:
[0-9]
;

fragment
UCSCHAR
   : '\u00A0' .. '\uD7FF'
   | '\uF900' .. '\uFDCF'
   | '\uFDF0' .. '\uFFEF'
;

fragment IUNRESERVED
:
ALPHA
   | DIGIT
   | ('-' | '.' | '_' | '~' | UCSCHAR)
;

fragment PCT_ENCODED
   : '%' HexDigit HexDigit
   ;
   
fragment SUB_DELIMS
   : '!'
   | '$'
   | '&'
   | '\''
   | '('
   | ')'
   | '*'
   | '+'
   | ','
   | ';'
   | '='
   ;

fragment IPCHAR
:
IUNRESERVED
|PCT_ENCODED
|SUB_DELIMS
| (':'|'@')
;

fragment SCHEME
:
 ALPHA ( ALPHA | DIGIT | ('+' | '-' | '.') )+
;

fragment ISEGMENT
:
IPCHAR*
;
 // Identifiers

 IDENTIFIER
 :
 	NameStartChar NameChar*
 ;

 MULTI_LINE_COMMENT
 :
 	(
 		DocComment
 		| LineComment [\r\n]
 	)+ -> skip
 ;

 SINGLE_LINE_COMMENT
 :
 	LineComment+ -> skip
 ;

 WHITESPACE
 :
 	Ws+ -> skip
 ;