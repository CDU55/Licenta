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
	public boolean appliedCorrectly(Object... objects) {
		if(objects.length!=3)
		{
			return false;
		}
		Sequence result=(Sequence)objects[0];
		Sequence s1=(Sequence)objects[1];
		Sequence s2=(Sequence)objects[2];
		if(result.proven!=null)
		{
			return false;
		}
		if(!canApply(s1,s2))
		{
			return false;
		}
		if(!Sequence.hypothesisEqual(result, s1) || !Sequence.hypothesisEqual(result, s2))
		{
			return false;
		}
		return true;
		
	}

	@Override
	public String toString() {
		return "!e";
	}
	
	

}
