package Operators;

public class OperatorPrecedence {
	
	private String connectors;
	
	public OperatorPrecedence(boolean propositionalLogic)
	{
		if(propositionalLogic)
		{
			this.connectors="!&|>-";
		}
		else
		{
			this.connectors="!&|>-VE";
		}
	}
	
	public int getPrecedence(char connector)
	{
		int precedence;
		if(connector=='<')
		{
			precedence=this.connectors.indexOf('>');
		}
		else
		{
			precedence=this.connectors.indexOf(connector);
		}
		if(precedence>=0)
		{
			return precedence+1;
		}
		else
		{
			return this.connectors.length()+1;
		}
	}

}
