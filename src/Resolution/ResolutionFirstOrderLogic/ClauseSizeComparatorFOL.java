package Resolution.ResolutionFirstOrderLogic;

import java.util.Comparator;

public class ClauseSizeComparatorFOL implements Comparator<ClauseAndExplanationFOL> {

	@Override
	public int compare(ClauseAndExplanationFOL arg0, ClauseAndExplanationFOL arg1) {
		
		return Integer.compare(arg0.clause.literals.size(),arg1.clause.literals.size());
	}

}
