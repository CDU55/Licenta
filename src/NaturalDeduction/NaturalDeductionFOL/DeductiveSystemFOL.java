package NaturalDeduction.NaturalDeductionFOL;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import Exceptions.GoalReached;
import Exceptions.InvalidInferenceRuleApplication;
import Exceptions.InvalidSubstitution;
import Formulas.FOLFormula;

public class DeductiveSystemFOL {
	public List<InferenceRuleFOL> rules;
	public List<SequenceFOL> sequences;
	public List<String> explanations;
	public SequenceFOL goal;
	private boolean goalReached;
	private int initialHypothesisSequenceNumber;
	public DeductiveSystemFOL(List<FOLFormula> initialFormulas,SequenceFOL goal)
	{
		this.rules=new ArrayList<InferenceRuleFOL>();
		rules.add(new CreateConjunctionFOL());
		rules.add(new ExtractFromConjunction1FOL());
		rules.add(new ExtractFromConjunction2FOL());
		rules.add(new ExtractFromImplicationFOL());
		rules.add(new CreateImplicationFOL());
		rules.add(new CreateDisjunction1FOL());
		rules.add(new CreateDisjunction2FOL());
		rules.add(new RemoveDisjunctionFOL());
		rules.add(new CreateBottomFOL());
		rules.add(new CreateNegationFromBottomFOL());
		rules.add(new CreateProvenFromBottomFOL());
		rules.add(new HypothesisFOL());
		rules.add(new ExtensionFOL());
		rules.add(new RemoveDoubleNegationFOL());
		rules.add(new RemoveUniversalCuantifier());
		rules.add(new CreateUniversalCuantifier());
		rules.add(new RemoveExistentialCuantifier());
		rules.add(new CreateExistentialCuantifier());
		this.sequences=new ArrayList<SequenceFOL>();
		this.explanations=new ArrayList<String>();
		for(FOLFormula formula:initialFormulas)
		{
			this.sequences.add(new SequenceFOL(initialFormulas,formula));
			this.explanations.add("(IPOTEZA)");
		}
		this.initialHypothesisSequenceNumber=this.sequences.size();
		this.goal=goal;
		this.goalReached=false;

	}
	
	public void apply(Object...objects) throws InvalidInferenceRuleApplication, GoalReached, InvalidSubstitution
	{
		if(this.goalReached)
		{
			throw new GoalReached("Goal sequence already reached");
		}
		String ruleName=objects[0].toString();
		Object[] args=Arrays.copyOfRange(objects, 1, objects.length);
		for(int i=0;i<args.length;i++)
		{
			if(args[i] instanceof Integer)
			{
				Integer sequenceIndex=(Integer)args[i];
				if(sequenceIndex<1 || sequenceIndex>this.sequences.size())
				{
					return;
				}
				else
				{
					args[i]=(Object)this.sequences.get(sequenceIndex-1);
				}
			}
		}
		for(InferenceRuleFOL rule:this.rules)
		{
			if(rule.toString().equals(ruleName))
			{
				if(rule.canApply(args))
				{
					SequenceFOL newSequence=rule.Apply(args);
					this.sequences.add(newSequence);
					String explanation="( ";
					for(int i=0;i<objects.length-1;i++)
					{
						explanation+=objects[i].toString()+", ";
					}
					explanation+=objects[objects.length-1].toString()+" )";
					this.explanations.add(explanation);
					if(newSequence.equals(this.goal))
					{
						this.goalReached=true;
					}
					break;
				}
				else
				{
					throw new InvalidInferenceRuleApplication("Rule "+rule.toString()+" cannot be applied vith the given parameters");
				}
			}
		}
	}
	
	public void remove()
	{
		if(this.sequences.size()>this.initialHypothesisSequenceNumber)
		{
			SequenceFOL toRemove=this.sequences.get(this.sequences.size()-1);
			if(toRemove.equals(this.goal))
			{
				this.goalReached=false;
			}
			this.sequences.remove(this.sequences.size()-1);
			this.explanations.remove(this.explanations.size()-1);
		}
	}
	public String getSequenceAndExplanation(int index)
	{
		if(index<0||index>this.sequences.size())
		{
			return null;
		}
		else
		{
			return (index+1)+"."+this.sequences.get(index).toString()+"\t\t"+this.explanations.get(index);
		}
	}
	
	public boolean getGoalReached()
	{
		return this.goalReached;
	}
	@Override
	public String toString() {
		String message=new String();
		int maxLen=0;
		for(SequenceFOL s:this.sequences)
		{
			int len=s.toString().length();
			if(len>maxLen)
			{
				maxLen=len;
			}
		}
		for(int i=0;i<sequences.size();i++)
		{
			message+=(i+1)+"."+this.sequences.get(i).toString();
			int diff=maxLen-this.sequences.get(i).toString().length();
			for(int space=0;space<diff+10;space++)
			{
				message+=" ";
			}
			message+=this.explanations.get(i)+"\n";
		}
		return message;
	}
	
}
