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

 /* A number: can be an integer value, or a decimal value */
 HEX_LITERAL
 :
 	'0' [xX] HEXDIGIT+
 ;

 // Fragments

 fragment
 ALPHA
 :
 	[a-zA-Z]
 ;

 fragment
 DIGIT
 :
 	[0-9]
 ;

 fragment
 ALPHANUM
 :
 	DIGIT
 	| ALPHA
 ;

 fragment
 HEXDIGIT
 :
 	DIGIT
 	| [A-Fa-f]
 ;

 ESCAPED
 :
 	'%' HEXDIGIT HEXDIGIT
 ;

 fragment
 Q_RESERVED
 :
 	';'
 	| '/'
 	| '?'
 	| ':'
 	| '@'
 	| '+'
 	| '$'
 	| ','
 ;

 fragment
 UNDERSCORE
 :
 	'_'
 ;

 fragment
 DASH
 :
 	'-'
 ;

 fragment
 DOT
 :
 	'.'
 ;

 fragment
 MARK
 :
 	DASH
 	| UNDERSCORE
 	| DOT
 	| '!'
 	| '~'
 	| '*'
 	| '\''
 	| '('
 	| ')'
 ;

 fragment
 RESERVED
 :
 	Q_RESERVED
 	| '='
 	| '&'
 ;

 UNRESERVED
 :
 	ALPHANUM
 	| MARK
 ;

 fragment
 QC
 :
 	ESCAPED
 	| Q_RESERVED
 	| UNRESERVED
 ;

 fragment
 URIC
 :
 	ESCAPED
 	| RESERVED
 	| UNRESERVED
 ;

 fragment
 WHITESPACE_CHAR
 :
 	[ \r\t\n]
 ;
 // Single Quote Escape Sequence '\\\'' 

 fragment
 SQuote
 :
 	'\''
 ;

 fragment
 DQuote
 :
 	'"'
 ;

 fragment
 EscSeq
 :
 	'\\a'
 	| '\\b'
 	| '\\f'
 	| '\\n'
 	| '\\r'
 	| '\\t'
 	| '\\v'
 	| '\\?'
 	| '\\\''
 	| '\\"'
 	| '\\'
 ;

 // non keyword identfier

 IDENTIFIER
 :
 	(
 		ALPHA
 		(
 			ALPHA
 			| UNDERSCORE
 		)*
 	)
 ;

 // String Literals

 SINGLE_QUOTE_SINGLE_LINE_STRING_LITERAL
 :
 	SQuote
 	(
 		EscSeq
 		| ~['\r\n\\]
 		| SQuote SQuote
 	)* SQuote
 ;

 DOUBLE_QUOTE_SINGLE_LINE_STRING_LITERAL
 :
 	DQuote
 	(
 		EscSeq
 		| ~["\r\n\\]
 	)* DQuote
 ;

 SINGLE_QUOTE_STRING_LITERAL
 :
 	SQuote
 	(
 		EscSeq
 		| ~['\\]
 		| SQuote SQuote
 	)* SQuote
 ;

 DOUBLE_QUOTE_STRING_LITERAL
 :
 	DQuote
 	(
 		EscSeq
 		| ~["\\]
 	)* DQuote
 ;

 // https://tools.ietf.org/html/rfc3986 - URI
 // https://github.com/yaojingguo/antlr-url-grammar/blob/master/src/java/org/jingguo/url/URL.g

 IRI_LITERAL
 :
 	SCHEME ':' HIER_PART
 	(
 		'?' QUERY
 	)?
 	(
 		'#' FRAGMENT
 	)
 ;

 SCHEME
 :
 	ALPHA
 	(
 		ALPHA
 		| DIGIT
 		|
 		(
 			'+'
 			| DASH
 			| DOT
 		)
 	)*
 ;

 HIER_PART
 :
 	'//'
 ;

 QUERY
 :
 	PARAM
 	(
 		'&' PARAM
 	)*
 ;

 PARAM
 :
 	PNAME '=' PVALUE
 ;

 PNAME
 :
 	QC+
 ;

 PVALUE
 :
 	QC+
 ;

 FRAGMENT
 :
 	(
 		URIC
 	)*
 ;

 DATE_LITERAL
 :
 	DIGIT DIGIT DASH DIGIT DIGIT DASH DIGIT DASH DIGIT DIGIT DIGIT DIGIT
 ;

 // shorter INTEGER later

 INTEGER_LITERAL
 :
 	[+-]? DIGIT+
 ;

 BOOLEAN_LITERAL
 :
 	'true'
 	| 'false'
 ;

 FLOAT_LITERAL
 :
 	INTEGER_LITERAL '.' INTEGER_LITERAL
 ;

 // Single Line Comment

 SINGLE_LINE_COMMENT
 :
 	(
 		'//'
 		| '#'
 	) ~( '\n' )* '\n' -> skip
 ;
 // http://stackoverflow.com/questions/8284919/negating-inside-lexer-and-parser-rules

 MULTI_LINE_COMMENT
 :
 	'*>'
 	(
 		~'<'
 		| '<' ~'*'
 	)* '<*' -> skip
 ;
 /* We're going to ignore all white space characters */
 WHITESPACE
 :
 	WHITESPACE_CHAR+ -> skip
 ;