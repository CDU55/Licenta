package Parsers;

import java.io.StringReader;

public class CheckSyntax {
	
	public static String checkPropositionalLogicSyntax(String formula)
	{
		String parsingResult="Default";
		 try {
			parsingResult=PropositionalLogicParser.parse(new StringReader(formula));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
		}
		 return parsingResult;
	}

}
