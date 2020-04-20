package NaturalDeduction;

public interface InferenceRule {
	
	public boolean canApply(Object...objects);
	
	public Sequence Apply(Object...objects);
	
	public String appliedCorrectly(Object...objects);

}
