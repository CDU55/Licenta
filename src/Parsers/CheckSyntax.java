package Parsers;

import java.io.StringReader;

import Parsers.FirstOrderLogic.FirstOrderLogicParser;
import Parsers.PropLogic.PropositionalLogicParser;
import Util.FunctionParsing;

public class CheckSyntax {
	
	public static String checkPropositionalLogicSyntax(String formula)
	{
		String parsingResult="Default";
		 try {
			parsingResult=PropositionalLogicParser.parse(new StringReader("("+formula+")"));
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		 if(FunctionParsing.parathesisDifference(formula)!=0)
		 {
			 return "Invalid parenthesis placement";
		 }
		 return parsingResult;
	}
	
	public static String checkFunctionSyntax(String function)
	{
		String parsingResult="Default";
		 try {
			parsingResult=FirstOrderLogicParser.parseFunction(new StringReader(function));
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		 return parsingResult;
	}
	
	public static boolean checkFunctionSyntaxBoolean(String function)
	{
		if(checkFunctionSyntax(function).toUpperCase().equals("OK"))
		{
			return true;
		}
		else
		{
			return false;
		}
	}
	public static String checkFirstOrderLogicSyntax(String formula)
	{
		String parsingResult="Default";
		 try {
			parsingResult=FirstOrderLogicParser.parse(new StringReader("("+formula+")"));
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		 if(FunctionParsing.parathesisDifference(formula)!=0)
		 {
			 return "Invalid parenthesis placement";
		 }
		 return parsingResult;
	}

}
