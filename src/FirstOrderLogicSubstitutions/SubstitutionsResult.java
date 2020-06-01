package FirstOrderLogicSubstitutions;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class SubstitutionsResult {
	
	public List<Substitution> substitutions;
	public boolean validSubstitution;
	
	public SubstitutionsResult()
	{
		this.substitutions=new ArrayList<Substitution>();
		this.validSubstitution=true;
	}
	
	public void checkValid()
	{
		for(int i=0;i<this.substitutions.size()-1;i++)
		{
			for(int j=i+1;j<this.substitutions.size();j++)
			{
				String initial1=this.substitutions.get(i).Initial.toString();
				String initial2=this.substitutions.get(j).Initial.toString();
				if(initial1.equals(initial2))
				{
					this.validSubstitution=false;
				}
			}
		}
	}

	public void removeDuplicates()
	{
		this.substitutions=new ArrayList<Substitution>(new HashSet<Substitution>(this.substitutions));
	}
	public boolean isSingleVariableSubstitution(String variable)
	{
		if(!this.validSubstitution)
		{
			return false;
		}
		else if(this.substitutions.size()!=1)
		{
			return false;
		}
		else if(!this.substitutions.get(0).Initial.getLabel().trim().equals(variable.trim()))
		{
			return false;
		}
		return true;
	}
	@Override
	public String toString() {
		if(!this.validSubstitution)
		{
			return "No valid substitution could be found";
		}
		else
		{
			String result="[";
			for(Substitution s:this.substitutions)
			{
				result+=s.Initial.toString()+" -> "+s.Final.toString()+" ";
			}
			result+="]";
			return result;
			
		}
	}

}
