package Operators;

import java.util.Arrays;
import java.util.List;

public class CorrespondingConnector {
	
	private static List<String> originalConnectors=Arrays.asList("!","/\\","\\/","->","<-","<->");;
	private static String remodeledConnectors="!&|><-";
	
	public static String getOriginalConnector(char connector)
	{
		if(remodeledConnectors.indexOf(connector)<0)
		{
			return "";
		}
		else
		{
			return originalConnectors.get(remodeledConnectors.indexOf(connector));
		}
	}
	
	public static char getRemodeledConnector(String connector)
	{
		if(connector.matches("V[a-z][A-za-z]*\\."))
		{
			return 'V';
		}
		else if(connector.matches("E[a-z][A-za-z]*\\."))
		{
			return 'E';
		}
		else if(!originalConnectors.contains(connector))
		{
			return '\0'; 
		}
		else
		{
			return remodeledConnectors.charAt(originalConnectors.indexOf(connector));
		}
	}

}
