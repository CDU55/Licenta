package NaturalDeduction;

import java.util.ArrayList;
import java.util.List;

import Exceptions.InvalidPropositionalLogicFormula;
import Exceptions.InvalidRuleName;

public class ProofChecker {

	private List<InferenceRule> rules;
	private List<Sequence> sequences;
	private List<RuleExplanation> explanations;

	public ProofChecker() {
		this.rules = new ArrayList<InferenceRule>();
		rules.add(new CreateConjunction());
		rules.add(new ExtractFromConjunction1());
		rules.add(new ExtractFromConjunction2());
		rules.add(new ExtractFromImplication());
		rules.add(new CreateImplication());
		rules.add(new CreateDisjunction1());
		rules.add(new CreateDisjunction2());
		rules.add(new RemoveDisjunction());
		rules.add(new CreateBottom());
		rules.add(new CreateNegationFromBottom());
		rules.add(new CreateProvenFromBottom());
		rules.add(new Hypothesis());
		rules.add(new Extension());
		rules.add(new RemoveDoubleNegation());
		this.sequences = new ArrayList<Sequence>();
		this.explanations = new ArrayList<RuleExplanation>();
	}

	public void Add(String sequence, String explanation) throws InvalidPropositionalLogicFormula, InvalidRuleName {
		this.sequences.add(new Sequence(sequence));
		this.explanations.add(new RuleExplanation(explanation));
	}

	public String checkProof() {
		for (int i = 0; i < this.sequences.size(); i++) {
			String ruleName = this.explanations.get(i).ruleName;
			List<Object> args = new ArrayList<Object>();
			args.add(this.sequences.get(i));
			for (int j = 0; j < this.explanations.get(i).args.length; j++) {
				Object currentArgument = this.explanations.get(i).args[j];
				if (currentArgument instanceof Integer) {
					args.add(this.sequences.get((Integer) currentArgument - 1));
				}
			}
			for (InferenceRule rule : this.rules) {
				if (rule.toString().equals(ruleName)) {
					/*if (rule.toString().equals("IPOTEZA") && i != 0) {
						Boolean sameHypothesis = false;
						for (int j = 0; j < i; j++) {
							if (Sequence.hypothesisEqual(this.sequences.get(i), this.sequences.get(j))) {
								sameHypothesis = true;
							}
						}
						if (!sameHypothesis) {
							return "Error on the hypothesis rule on line " + String.valueOf(i + 1);
						}
					}*/
					String evaluationResult=rule.appliedCorrectly(args.toArray());
					if (!evaluationResult.equals("Ok")) {
						return "Error on line " + String.valueOf(i + 1)+"\n"+evaluationResult;
					}
				}
			}
		}
		return "OK";
	}

}
