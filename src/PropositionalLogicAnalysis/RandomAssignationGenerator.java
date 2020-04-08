package PropositionalLogicAnalysis;

import java.util.Random;

public class RandomAssignationGenerator {
	
	public static Boolean[] generate(int variableNumber)
	{
		Boolean[] assignation=new Boolean[variableNumber];
		Random random=new Random();
		int choice;
		for(int currentIndex=0;currentIndex<variableNumber;currentIndex++)
		{
			choice=random.nextInt(2);
			assignation[currentIndex]=choice==0?Boolean.FALSE:Boolean.TRUE;
		}
		return assignation;
	}

}
