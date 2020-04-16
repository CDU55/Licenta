package Parsers;

import java.io.StringReader;

public class CheckSyntax {
	
	public static String checkPropositionalLogicSyntax(String formula)
	{
		String parsingResult="Default";
		 try {
			parsingResult=PropositionalLogicParser.parse(new StringReader("("+formula+")"));
		} catch (ParseException e) {
			System.out.println(e.getMessage());
		}
		 return parsingResult;
	}

}
