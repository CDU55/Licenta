package NormalForms.PrenexNormalForm;

import java.util.List;
import java.util.Random;

public class RenameVariable {

	public static String rename(int minLength, int maxLength, List<String> existingVariables) {
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
				code += 97;
				result += (char) code;
			}
			if (!existingVariables.contains(result)) {
				return result;
			}
		}
	}
}
