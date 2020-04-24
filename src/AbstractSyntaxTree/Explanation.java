package AbstractSyntaxTree;

import java.util.ArrayList;
import java.util.List;

public class Explanation {
	
	public List<String> messages;
	public String previousMessage;
	
	public Explanation()
	{
		this.messages=new ArrayList<String>();
	}
	public static String addSuform(String formula)
	{
		return "Subf( "+formula+ " )";
	}
	
	public static String addVars(String formula)
	{
		return "Vars( "+formula+ " )";
	}
	
	public static String addBraces(String formula)
	{
		return "{ "+formula+" }";
	}
	@Override
	public String toString() {
		String message=new String();
		for(String line : messages)
		{
			message+=line+"\n";
		}
		return message;
	}
	
	

}
