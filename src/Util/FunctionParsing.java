package Util;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FunctionParsing {

	public static String extractFuncitonName(String function)
	{
		String trimmedFunciton=function.replace(" ","");
		Matcher m=Pattern.compile("([A-Z][A-Za-z]*)\\(.*?\\)").matcher(trimmedFunciton);
		String name=null;
		if(m.find())
		{
			name=m.group(1);
		}
		return name;
	}
	
	public static List<String> extractFunctionParameters(String function)
	{
		List<String> parameters=new ArrayList<String>();
		String name=extractFuncitonName(function);
		String f=new String(function);
		f=f.replaceFirst(name, "");
		f=f.substring(1, f.length()-1);
		int parenthesisDiff=0;
		for(int i=0;i<f.length();i++)
		{
			if(f.charAt(i)=='(')
			{
				parenthesisDiff++;
			}
			if(f.charAt(i)==')')
			{
				parenthesisDiff--;
			}
			if(parenthesisDiff==0 && f.charAt(i)==',')
			{
				f=f.substring(0, i)+";"+f.substring(i+1);
			}
		}
		for(String parameter:f.split(";"))
		{
			if(!parameter.trim().equals(""))
			{
				parameters.add(parameter.trim());
			}
		}
		return parameters;
		
	}
	
	public static int parathesisDifference(String formula)
	{
		int difference=0;
		for(int i=0;i<formula.length();i++)
		{
			if(formula.charAt(i)=='(')
				{
				difference++;
				}
			else if(formula.charAt(i)==')')
				{
				difference--;
				if(difference<0)
				{
					return -1;
				}
				}
		}
		return difference;
	}
}
