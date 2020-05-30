package NaturalDeduction.NaturalDeductionFOL;

public interface InferenceRuleFOL {
	
public boolean canApply(Object...objects);
	
	public SequenceFOL Apply(Object...objects);
	
	public String appliedCorrectly(Object...objects);

}
