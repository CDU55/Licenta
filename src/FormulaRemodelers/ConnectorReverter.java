package FormulaRemodelers;

public class ConnectorReverter implements FormulaRemodeler {

	@Override
	public String remodel(String formula) {
		
		String newFormula=new String(formula);
		newFormula=newFormula.replace("&", "/\\");
		newFormula=newFormula.replace("|", "\\/");
		newFormula=newFormula.replace("-", "+");
		newFormula=newFormula.replace(">", "->");
		newFormula=newFormula.replace("<", "<-");
		newFormula=newFormula.replace("+", "<->");

		return newFormula;
	}

}
