package Operators;

import java.util.Arrays;
import java.util.List;

public class CorrespondingConnector {
	
	private static List<String> originalConnectors=Arrays.asList("/\\","\\/","->","<-","<->");;
	private static String remodeledConnectors="&|><-";
	
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
		if(!originalConnectors.contains(connector))
		{
			return '\0'; 
		}
		else
		{
			return remodeledConnectors.charAt(originalConnectors.indexOf(connector));
		}
	}

}
