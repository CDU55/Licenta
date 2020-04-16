package NaturalDeduction;

import java.util.ArrayList;
import java.util.List;

import Exceptions.InvalidPropositionalLogicFormula;
import Parsers.CheckSyntax;
import PropositionalLogicFormula.Formula;

public class Sequence {
	
	public List<Formula> hypothesis;
	public Formula proven;
	public Sequence(List<Formula> hypothesis,Formula proven)
	{
		this.hypothesis=new ArrayList<Formula>();
		for(Formula f:hypothesis)
		{
			this.hypothesis.add(new Formula(f.syntaxTree.getRoot()));
		}
		if(proven!=null)
		{
			this.proven=new Formula(proven.syntaxTree.getRoot());
		}
	}
	
	public Sequence(String sequence) throws InvalidPropositionalLogicFormula
	{
		this.hypothesis=new ArrayList<Formula>();

		String[] hypothesisAndProven=sequence.split("\\|\\-");
		String hypothesis=hypothesisAndProven[0].replace("{", "").replace("}", "");
		String[] formulas=hypothesis.split(",");
		for(String formula:formulas)
		{
				this.hypothesis.add(new Formula(formula));
		}
		if(!hypothesisAndProven[1].replace(" ", "").equals("_|_"))
		{
			if(CheckSyntax.checkPropositionalLogicSyntax(hypothesisAndProven[1]).equals("OK"))
			{
				this.proven=new Formula(hypothesisAndProven[1]);
			}
		}
	}
	public static boolean hypothesisEqual(Sequence s1,Sequence s2)
	{
		if(s1.hypothesis.size()!=s2.hypothesis.size())
		{
			return false;
		}
		for(Formula f:s1.hypothesis)
		{
			if(!s2.hypothesis.contains(f))
			{
				return false;
			}
		}
		return true;
	}

	@Override
	public String toString() {
		String message="{ ";
		for(Formula f:this.hypothesis)
		{
			message+=f.toString();
			if(this.hypothesis.indexOf(f)!=this.hypothesis.size()-1)
			{
				message+=", ";
			}
		}
		message+=" }"+" |- ";
		if(this.proven!=null)
		{
			message+=this.proven.toString();
		}
		else
		{
			message+=" | ";
		}
		return message;
		
	}
}
