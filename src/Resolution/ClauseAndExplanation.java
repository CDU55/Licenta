package Resolution;

public class ClauseAndExplanation {
	
	ResolutionClause clause;
	String explanation;
	public ClauseAndExplanation(ResolutionClause clause, String explanation) {
		super();
		this.clause = clause;
		this.explanation = explanation;
	}
	
	public ClauseAndExplanation(ClauseAndExplanation toCopy) {
		super();
		this.clause = new ResolutionClause(toCopy.clause);
		this.explanation = new String(toCopy.explanation);
	}

}
