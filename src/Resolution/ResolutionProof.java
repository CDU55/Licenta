package Resolution;

import java.util.List;

import Exceptions.InvalidLiteral;
import Exceptions.InvalidPropositionalLogicFormula;
import PropositionalLogicFormula.Formula;

public class ResolutionProof {
	
	public static Resolution proof;
	public static boolean proofDone;
	
	public static void proofSearch(Resolution currentProof)
	{
		if(proofDone==true)
		{
			return;
		}
		for(int indexClause1=0;indexClause1<currentProof.getClausesNumber()-1;indexClause1++)
		{
			for(int indexClause2=indexClause1+1;indexClause2<currentProof.getClausesNumber();indexClause2++)
			{
				ResolutionClause clause1=currentProof.getClause(indexClause1);
				ResolutionClause clause2=currentProof.getClause(indexClause2);
				List<Literal> appliableLiterals=ResolutionClause.resolutionLiterals(clause1, clause2);
				for(Literal literal:appliableLiterals)
				{
					ResolutionClause result=null;
					if(clause1.literals.contains(literal) && clause2.literals.contains(literal.getComplement()))
					{
						result=currentProof.apply(clause1, clause2, literal);
					}
					else
					{
						result=currentProof.apply(clause2, clause1, literal);
					}
					if(!currentProof.containsClause(result))
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
					}
				}
			}
		}
	}
	
	public static Resolution findProof(Formula formula)
	{
		proof=null;
		proofDone=false;
		try {
			Resolution initial=new Resolution(formula);
			proofSearch(initial);
			return proof;
		} catch (InvalidPropositionalLogicFormula | InvalidLiteral e) {
			System.out.println("Not good");
			return null;
		}
	}

}
