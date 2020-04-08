package Operators;

public class TypeTesterFirstOrderLogic extends TypeTester {
	
	private String cuantifiers;
	
	public TypeTesterFirstOrderLogic()
	{
		super();
		this.cuantifiers="VE";
	}
	
	public boolean isCuantifier(char cuantifier)
	{
		if(this.cuantifiers.indexOf(cuantifier)>=0)
		{
			return true;
		}
		else
		{
			return false;
		}
	}
	
	public static boolean isCuantifierWithTerm(String cuantifier)
	{
		return cuantifier.matches("(E[a-zA-Z]+[.])|(V[a-zA-Z]+[.])");
	}

}
