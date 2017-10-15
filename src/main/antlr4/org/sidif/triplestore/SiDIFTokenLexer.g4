/**
 * Copyright 2017 BITPlan GmbH
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

IS: 'is';
OF: 'of';

// Keywords
// Fragments
fragment DIGIT: [0-9];
fragment HEXDIGIT: DIGIT | [A-Fa-f];
fragment WHITESPACE_CHAR:[ \r\t\n];
// Single Quote Escape Sequence '\\\'' 
fragment SQuote: '\'';
fragment DQuote: '"';
// See page 27  - FIXME octal and hex numbers not supported yet
fragment EscSeq:  '\\a' | '\\b' | '\\f' | '\\n' | '\\r' | '\\t' | '\\v' | '\\?'  | '\\\'' | '\\"' | '\\';

/**
 * Identifier which is not a keyword
 */
IDENTIFIER:	([a-zA-ZäöüßÄÖÜ] [a-zA-Z0-9äöüßÄÖÜ_]*) ;

// String Literals
SINGLE_QUOTE_STRING_LITERAL	: SQuote ( EscSeq | ~['\r\n\\] | SQuote SQuote )* SQuote	;
DOUBLE_QUOTE_STRING_LITERAL	: DQuote ( EscSeq | ~["\r\n\\] )*? DQuote	;

/* A number: can be an integer value, or a decimal value */
HEX_LITERAL:  '0' [xX] HEXDIGIT+; 

// shorter INTEGER later
INTEGER_LITERAL: DIGIT +;

BOOLEAN_LITERAL
    :   'true'
    |   'false'
;

FLOAT_LITERAL: INTEGER_LITERAL '.' INTEGER_LITERAL;

LITERAL: DOUBLE_QUOTE_STRING_LITERAL | SINGLE_QUOTE_STRING_LITERAL | INTEGER_LITERAL | HEX_LITERAL |  BOOLEAN_LITERAL | FLOAT_LITERAL;

// Single Line Comment
SINGLE_LINE_COMMENT:  ('//'|'#') ~('\n')* '\n' -> skip;
// http://stackoverflow.com/questions/8284919/negating-inside-lexer-and-parser-rules
MULTI_LINE_COMMENT:  '*>' ( ~'<' | '<' ~'*' )* '<*' -> skip;
/* We're going to ignore all white space characters */
WHITESPACE: WHITESPACE_CHAR+ -> skip;