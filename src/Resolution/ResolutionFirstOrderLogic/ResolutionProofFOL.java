package Resolution.ResolutionFirstOrderLogic;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import Exceptions.GoalReached;
import Exceptions.InvalidLiteral;
import Exceptions.InvalidPropositionalLogicFormula;
import Exceptions.InvalidSubstitution;
import Formulas.FOLFormula;

public class ResolutionProofFOL {
	public static ResolutionFOL proof;
	public static boolean proofDone;
	
	public static void proofSearch(ResolutionFOL currentProof) throws InvalidSubstitution
	{
		if(proofDone==true)
		{
			return;
		}
		List<ClauseAndExplanationFOL> possibleResultsTemp=new ArrayList<ClauseAndExplanationFOL>();
		for(int indexClause1=0;indexClause1<currentProof.getClausesNumber()-1;indexClause1++)
		{
			for(int indexClause2=indexClause1+1;indexClause2<currentProof.getClausesNumber();indexClause2++)
			{
				ResolutionClauseFOL clause1=currentProof.getClause(indexClause1);
				ResolutionClauseFOL clause2=currentProof.getClause(indexClause2);
				List<LiteralPair> appliableLiterals=ResolutionClauseFOL.resolutionLiterals(clause1, clause2);
				for(LiteralPair pair:appliableLiterals)
				{
					ResolutionClauseFOL result=null;
					if(clause1.literals.contains(pair.literal1) && clause2.literals.contains(pair.literal2))
					{
						result=currentProof.apply(clause1, clause2, pair);
					}
					else
					{
						result=currentProof.apply(clause2, clause1, pair);
					}
					/*if(!currentProof.containsClause(result))
					{
						String explanation="( "+(indexClause1+1)+" , "+(indexClause2+1)+" , "+literal.toString()+" )";
						currentProof.add(result, explanation);
						if(result.literals.size()==0)
						{
							proof=currentProof;
							proofDone=true;
						}
						else
						{
							proofSearch(new Resolution(currentProof));
						}
					}*/
					String explanation="( "+(indexClause1+1)+" , "+(indexClause2+1)+" , "+pair.literal1.predicateSymbol+" )";
					possibleResultsTemp.add(new ClauseAndExplanationFOL(result,explanation));
				}
			}
		}
		List<ClauseAndExplanationFOL> possibleResults=new ArrayList<ClauseAndExplanationFOL>(possibleResultsTemp);
		Collections.sort(possibleResults, new ClauseSizeComparatorFOL());
		for(int i=0;i<possibleResults.size();i++)
		{
			ResolutionClauseFOL currentResult=possibleResults.get(i).clause;
			String currentExplanation=possibleResults.get(i).explanation;
			if(!currentProof.containsClause(currentResult))
			{
				ResolutionFOL possibleProof=new ResolutionFOL(currentProof);
				possibleProof.add(currentResult, currentExplanation);
				if(currentResult.literals.size()==0)
				{
					proof=possibleProof;
					proofDone=true;
				}
				else
				{
					proofSearch(possibleProof);
				}
				
			}
			
		}
	}
	
	public static ResolutionFOL findProof(FOLFormula formula) throws GoalReached, InvalidSubstitution
	{
		proof=null;
		proofDone=false;
		
		try {
			ResolutionFOL initial=new ResolutionFOL(formula);
			proofSearch(initial);
			return proof;
		} catch (InvalidPropositionalLogicFormula | InvalidLiteral e) {
			System.out.println("Not good");
			return null;
		}
	}
}
