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
  import org.sidif.triple.impl.Resolver;
  import org.sidif.triple.Value;
}

 @members {
 	Resolver resolver=new Resolver();
	
	/**
	 * create a new triple from the given subject, predicate and object
	 * 
	 * remember the current subject and if the reference "it" is used replace it with
	 * the current subject
	 * 
	 * @param subject - the subject
	 * @param predicate - the  predicate
	 * @param object - the object
	 * @return - the triple
	 */
	public Triple createTriple(Object subject, Object predicate, Object object) {
	  Triple triple=resolver.createTriple(subject,predicate,object);
	  return triple;
	}
	
	/**
	 * return the unquoted version or the given quoted string
	 * by removing the first and last character
	 * @param quoted - the quoted string
	 * @return - the  unquoted string
	 */
	public String noQuotes(String quoted) {
		return quoted.substring(1,quoted.length()-1);
	}
}

 /* Grammar Start */
 /**
  * a SIDIF file simply has a list of triple lines
  */
 triples
 :
 	(
 		tripleline+ EOF
 	)
 ;

 /**
 * a line may either have a simple subject predicate object link 
 * or a value assignment value is predicate of subject
 */
 tripleline returns [Triple triple]
 :
 	(
 		linktriple = link
 		{$triple=$linktriple.triple;}

 		| valuetriple = value
 		{$triple=$valuetriple.triple;}

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
 	)
 	{ 
 			$triple=createTriple($subject.identName,$predicate.identName,$object.identName);
 	}

 ;

 value returns [Triple triple]
 :
 	(
 		object = literal IS predicate = identifier OF subject = identifier
 		{ 
 			$triple=createTriple($subject.identName,$predicate.identName,$object.literalValue);
 		}

 	)
 ;

 literal returns [Value literalValue]
 :
 	s = DOUBLE_QUOTE_STRING_LITERAL
 	{ $literalValue=Value.getString(noQuotes($s.text)); }

 	| s = SINGLE_QUOTE_STRING_LITERAL
 	{ $literalValue=Value.getString(noQuotes($s.text)); }

 	| s = INTEGER_LITERAL
 	{ $literalValue=Value.getInteger($s.text); }

 	| s = IRI_LITERAL
 	{ $literalValue=Value.getIRI($s.text); }

 	| s = DATE_TIME_LITERAL
 	{ $literalValue=Value.getDateTime($s.text); }

 	| s = DATE_LITERAL
 	{ $literalValue=Value.getDateTime($s.text); }

 	| s = TIME_LITERAL
 	{ $literalValue=Value.getDateTime($s.text); }

 	| s = HEX_LITERAL
 	{ $literalValue=Value.getString($s.text); }

 	| s = BOOLEAN_LITERAL
 	{ $literalValue=Value.getBoolean($s.text); }

 	| s = FLOAT_LITERAL
 	{ $literalValue=Value.getDouble($s.text); }
 ;

 identifier returns [String identName]
 :
 	ident = IDENTIFIER
 	{ $identName=$ident.text; }

 ;
 