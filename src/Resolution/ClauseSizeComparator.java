package Resolution;

import java.util.Comparator;

public class ClauseSizeComparator implements Comparator<ResolutionClause> {

	@Override
	public int compare(ResolutionClause arg0, ResolutionClause arg1) {
		
		return Integer.compare(arg0.literals.size(),arg1.literals.size());
	}

}
