package NaturalDeduction.NaturalDeductionFOL;

import Exceptions.InvalidSubstitution;

public interface InferenceRuleFOL {
	
public boolean canApply(Object...objects);
	
	public SequenceFOL Apply(Object...objects) throws InvalidSubstitution;
	
	public String appliedCorrectly(Object...objects);

}
