package NaturalDeduction;

import java.util.Arrays;

import Exceptions.InvalidPropositionalLogicFormula;
import Exceptions.InvalidRuleName;
import Parsers.CheckSyntax;

public class RuleExplanation {
	
	public String ruleName;
	public Object[] args;
	public RuleExplanation(String explanation) throws InvalidPropositionalLogicFormula, InvalidRuleName
	{
		explanation=explanation.replace("(", "").replace(")","").replace(" ","").trim();
		String nameAndArgs[]=explanation.split(",");
		if(CheckRuleName.check(nameAndArgs[0]))
		{
			this.ruleName=nameAndArgs[0];
		}
		else
		{
			throw new InvalidRuleName(nameAndArgs[0]+" is not a valid rule name");
		}
		args=new Object[nameAndArgs.length-1];
		for(int i=1;i<nameAndArgs.length;i++)
		{
			if(nameAndArgs[i].matches("[1-9][0-9]*"))
			{
				args[i-1]=Integer.parseInt(nameAndArgs[i]);
			}
			else
			{
				if(CheckSyntax.checkPropositionalLogicSyntax(nameAndArgs[i]).equals("OK"))
				{
					args[i-1]=nameAndArgs[i];
				}
				else
				{
					throw new InvalidPropositionalLogicFormula("Invalid formula found in explanation : "+nameAndArgs[i]);
				}
			}
		}
	}
	@Override
	public String toString() {
		return "RuleExplanation [ruleName=" + ruleName + ", args=" + Arrays.toString(args) + "]";
	}
	

}
