package Resolution.ResolutionFirstOrderLogic;

public class ClauseAndExplanationFOL {
	ResolutionClauseFOL clause;
	String explanation;
	public ClauseAndExplanationFOL(ResolutionClauseFOL clause, String explanation) {
		super();
		this.clause = clause;
		this.explanation = explanation;
	}
	
	public ClauseAndExplanationFOL(ClauseAndExplanationFOL toCopy) {
		super();
		this.clause = new ResolutionClauseFOL(toCopy.clause);
		this.explanation = new String(toCopy.explanation);
	}
}
