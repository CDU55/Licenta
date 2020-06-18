package NormalForms.SkolemNormalForm;

import java.util.List;
import java.util.Random;

public class NewFunction {

	public static String rename(int minLength, int maxLength, List<String> existingFunctions) {
		if (minLength <= 0) {
			minLength = 1;
		}
		Random random = new Random();
		int length = random.nextInt(maxLength - minLength + 1);
		length += minLength;
		String result = "";
		while (true) {
			result = "";
			for (int i = 0; i < length; i++) {
				int code = random.nextInt(26);
				code += 65;
				if(code==86 || code == 69)
				{
					code--;
				}
				result += (char) code;
			}
			if (!existingFunctions.contains(result)) {
				return result;
			}
		}
	}
}
