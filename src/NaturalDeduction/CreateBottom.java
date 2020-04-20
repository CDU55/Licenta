package NaturalDeduction;

public class CreateBottom implements InferenceRule {

	@Override
	public boolean canApply(Object... objects) {
		if(objects.length!=2)
		{
			return false;
		}
		Sequence s1=(Sequence)objects[0];
		Sequence s2=(Sequence)objects[1];
		if(s1.proven==null || s2.proven==null)
		{
			return false;
		}
		if(!s2.proven.syntaxTree.getRoot().getLabel().equals("!"))
		{
			return false;
		}
		if(!s1.proven.syntaxTree.getRoot().equals(s2.proven.syntaxTree.getRoot().getLeftChild()))
		{
			return false;
		}
		if(!Sequence.hypothesisEqual(s1, s2))
		{
			return false;
		}
		return true;
	}

	@Override
	public Sequence Apply(Object... objects) {
		Sequence s1=(Sequence)objects[0];
		Sequence result=new Sequence(s1.hypothesis,null);
		return result;
	}

	@Override
	public String appliedCorrectly(Object... objects) {
		if(objects.length!=3)
		{
			return "Invalid arguments number";
		}
		for(int i=0;i<objects.length;i++)
		{
			if(!(objects[i] instanceof Sequence))
			{
				return "Argument "+i+" type is not valid";
			}
		}
		Sequence result=(Sequence)objects[0];
		Sequence s1=(Sequence)objects[1];
		Sequence s2=(Sequence)objects[2];
		if(result.proven!=null)
		{
			return "The resulting sequence right side must be bottom";
		}
		if(!canApply(s1,s2))
		{
			return this.toString()+" cannot be aplied on "+s1.toString()+" and "+s2.toString();
		}
		if(!Sequence.hypothesisEqual(result, s1) || !Sequence.hypothesisEqual(result, s2))
		{
			return "The resulting sequence does not have the same right side as the arguments";
		}
		return "Ok";
		
	}

	@Override
	public String toString() {
		return "!e";
	}
	
	

}
