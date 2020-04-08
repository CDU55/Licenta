package FormulaRemodelers;

public class ConnectorReplacer implements FormulaRemodeler {

	@Override
	public String remodel(String formula) {
		
		String newFormula=new String(formula);
		newFormula=newFormula.replaceAll("/\\\\", "&");
		newFormula=newFormula.replaceAll("\\\\/", "|");
		newFormula=newFormula.replaceAll("<\\->", "-");
		newFormula=newFormula.replaceAll("\\->", ">");
		newFormula=newFormula.replaceAll("<\\-", "<");
		return newFormula;
		
	}

}
