package NaturalDeduction;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import Exceptions.GoalReached;
import Exceptions.InvalidInferenceRuleApplication;
import PropositionalLogicFormula.Formula;

public class DeductiveSystem {
	
	public List<InferenceRule> rules;
	public List<Sequence> sequences;
	public List<String> explanations;
	public Sequence goal;
	private boolean goalReached;
	private int initialHypothesisSequenceNumber;
	public DeductiveSystem(List<Formula> initialFormulas,Sequence goal)
	{
		this.rules=new ArrayList<InferenceRule>();
		rules.add(new CreateConjunction());
		rules.add(new ExtractFromConjunction1());
		rules.add(new ExtractFromConjunction2());
		rules.add(new ExtractFromImplication());
		rules.add(new CreateImplication());
		rules.add(new CreateDisjunction1());
		rules.add(new CreateDisjunction2());
		rules.add(new RemoveDisjunction());
		rules.add(new CreateBottom());
		rules.add(new CreateNegationFromBottom());
		rules.add(new CreateProvenFromBottom());
		rules.add(new Hypothesis());
		rules.add(new Extension());
		rules.add(new RemoveDoubleNegation());
		this.sequences=new ArrayList<Sequence>();
		this.explanations=new ArrayList<String>();
		for(Formula formula:initialFormulas)
		{
			this.sequences.add(new Sequence(initialFormulas,formula));
			this.explanations.add("(IPOTEZA)");
		}
		this.initialHypothesisSequenceNumber=this.sequences.size();
		this.goal=goal;
		this.goalReached=false;

	}
	
	public void apply(Object...objects) throws InvalidInferenceRuleApplication, GoalReached
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
		for(InferenceRule rule:this.rules)
		{
			if(rule.toString().equals(ruleName))
			{
				if(rule.canApply(args))
				{
					Sequence newSequence=rule.Apply(args);
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
			Sequence toRemove=this.sequences.get(this.sequences.size()-1);
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
	@Override
	public String toString() {
		String message=new String();
		int maxLen=0;
		for(Sequence s:this.sequences)
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
