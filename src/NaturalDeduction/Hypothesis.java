package NaturalDeduction;

public class Hypothesis implements InferenceRule {

	@Override
	public boolean canApply(Object... objects) {
		if(objects.length!=1)
		{
			return false;
		}
		Sequence s =(Sequence)objects[0];
		if(!s.hypothesis.contains(s.proven))
		{
			return false;
		}
		return true;
	}

	@Override
	public Sequence Apply(Object... objects) {
		Sequence s =(Sequence)objects[0];
		return s;
	}

	@Override
	public String appliedCorrectly(Object... objects) {
		 if(objects.length!=1)
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
		 Sequence s=(Sequence)objects[0];
		 if(s.proven==null)
		 {
			 return "Resulting sequence right side cannot be bottom";
		 }
		 if(!s.hypothesis.contains(s.proven))
		 {
			 return "Resulting sequence hypothesis does not contain "+s.proven.toString();
		 }
		 return "Ok";
	}

	@Override
	public String toString() {
		return "IPOTEZA";
	}

	
}
