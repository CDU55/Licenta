package Resolution;

import java.util.Comparator;

public class ClauseSizeComparator implements Comparator<ClauseAndExplanation> {

	@Override
	public int compare(ClauseAndExplanation arg0, ClauseAndExplanation arg1) {
		
		return Integer.compare(arg0.clause.literals.size(),arg1.clause.literals.size());
	}

}
