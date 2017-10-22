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

 @header {
  import org.sidif.triple.Triple;
  import org.sidif.triple.impl.TripleImpl;
}

 /* Grammar Start */
 triples
 :
 	(
 		tripleline+ EOF
 	)
 ;

 tripleline returns [Triple triple]
 :
 	(
 		linktriple = link {$triple=$linktriple.triple;}
 		| valuetriple = value {$triple=$valuetriple.triple;}
 	)
 	

 ;

 link returns [Triple triple]
 :
 	(
 		(
 			subject = identifier predicate = identifier object = identifier
 		)
 		|
 		(
 			object = identifier IS predicate = identifier OF subject = identifier
 		)
 		{ 
 			$triple=new TripleImpl($subject.identName,$predicate.identName,$object.identName);
 		}

 	)
 ;

 value returns [Triple triple]
 :
 	(
 		subject = literal IS predicate = identifier OF object = identifier
 		{ 
 			$triple=new TripleImpl($subject.literalValue,$predicate.identName,$object.identName);
 		}

 	)
 ;

 literal returns [Object literalValue]
 :
 	s = DOUBLE_QUOTE_STRING_LITERAL
 	{ $literalValue=$s.text; }

 	| s = SINGLE_QUOTE_STRING_LITERAL
 	{ $literalValue=$s.text; }

 	| INTEGER_LITERAL
 	| IRI_LITERAL
 	| DATE_TIME_LITERAL
 	| DATE_LITERAL
 	| TIME_LITERAL
 	| HEX_LITERAL
 	| BOOLEAN_LITERAL
 	| FLOAT_LITERAL
 ;

 identifier returns [String identName]
 :
 	ident = IDENTIFIER
 	{ $identName=$ident.text; }

 ;
 