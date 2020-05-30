package NaturalDeduction.NaturalDeductionFOL;

import java.util.ArrayList;
import java.util.List;

import Exceptions.InvalidPropositionalLogicFormula;
import Formulas.FOLFormula;
import Formulas.Formula;

public class SequenceFOL {
	
	public List<FOLFormula> hypothesis;
	public FOLFormula proven;
	public SequenceFOL(List<FOLFormula> hypothesis,FOLFormula proven)
	{
		this.hypothesis=new ArrayList<FOLFormula>();
		for(FOLFormula f:hypothesis)
		{
			this.hypothesis.add(new FOLFormula(f.syntaxTree.getRoot()));
		}
		if(proven!=null)
		{
			this.proven=new FOLFormula(proven.syntaxTree.getRoot());
		}
	}
	
	public SequenceFOL(String sequence) throws InvalidPropositionalLogicFormula
	{
		/*if(!ProofReader.isSequenceString(sequence))
		{
			throw new InvalidPropositionalLogicFormula("Invalid Sequence");
		}*/
		this.hypothesis=new ArrayList<FOLFormula>();

		String[] hypothesisAndProven=sequence.split("\\|\\-");
		if(!hypothesisAndProven[0].trim().matches("\\{\\s*\\}")) 
		{
		String hypothesis=hypothesisAndProven[0].replace("{", "").replace("}", "");
		int parenthesisDiff=0;
		for(int i=0;i<hypothesis.length();i++)
		{
			if(hypothesis.charAt(i)=='(')
			{
				parenthesisDiff++;
			}
			if(hypothesis.charAt(i)==')')
			{
				parenthesisDiff--;
			}
			if(parenthesisDiff==0 && hypothesis.charAt(i)==',')
			{
				hypothesis=hypothesis.substring(0, i)+";"+hypothesis.substring(i+1);
			}
		}
		String[] formulas=hypothesis.split(";");
		for(String formula:formulas)
		{
				this.hypothesis.add(new FOLFormula(formula));
		}
		}
		if(hypothesisAndProven[1].trim().equals("|"))
		{
			
				this.proven=null;
		}
		else
		{
			this.proven=new FOLFormula(hypothesisAndProven[1]);
		}
	}
	public static boolean hypothesisEqual(SequenceFOL s1,SequenceFOL s2)
	{
		if(s1.hypothesis.size()!=s2.hypothesis.size())
		{
			return false;
		}
		for(FOLFormula f:s1.hypothesis)
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
		for(FOLFormula f:this.hypothesis)
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
