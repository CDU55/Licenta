package Operators;

public class TypeTester {
	
	private String operators;
	
	public TypeTester()
	{
		this.operators="!&|<>-";
	}
	
	public boolean isOperator(char connector)
	{
		if(this.operators.indexOf(connector)>=0)
		{
			return true;
		}
		else
		{
			return false;
		}
	}
	
	public boolean isOperatorExcludeNegation(char connector)
	{
		if(this.operators.indexOf(connector)>=0 && connector!='!')
		{
			return true;
		}
		else
		{
			return false;
		}
	}
	
}
