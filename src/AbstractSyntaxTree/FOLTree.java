package AbstractSyntaxTree;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Stack;

import FirstOrderLogicSubstitutions.Substitution;
import FormulaRemodelers.PostfixNotationFOL;
import Operators.CorrespondingConnector;
import Operators.TypeTesterFirstOrderLogic;
import Parsers.CheckSyntax;


public class FOLTree {

	private FOLTreeNode root;

	public FOLTree(String formula) {
		List<String> postfix = PostfixNotationFOL.shuntingYard(formula);
		this.buildTree(postfix);
	}
	public FOLTree(FOLTreeNode root)
	{
		this.root=new FOLTreeNode(root);
	}
	
	public FOLTreeNode getRoot() {
		return root;
	}

	public void setRoot(FOLTreeNode root) {
		this.root = root;
	}

	public int getHeight() {
		return calculateHeight(this.root);
	}

	private int calculateHeight(FOLTreeNode currentNode) {
		if (currentNode == null) {
			return 0;
		} else if (currentNode.isConnector()) {
			return 1 + Math.max(calculateHeight(currentNode.getLeftChild()),
					calculateHeight(currentNode.getRightChild()));
		} else if (currentNode.isVariable()) {
			return 1;
		} else {
			if (currentNode.getArguments().size() == 0) {
				return 0;
			} else {
				int maxHeight = 0;
				for (FOLTreeNode arg : currentNode.getArguments()) {
					int currentArgHeight = calculateHeight(arg);
					if (currentArgHeight > maxHeight) {
						maxHeight = currentArgHeight;
					}
				}
				return maxHeight + 1;
			}
		}
	}

	public int getSize() {
		return calculateSize(this.root);
	}

	private int calculateSize(FOLTreeNode currentNode) {
		if (currentNode == null) {
			return 0;
		} else if (currentNode.isConnector()) {
			return 1 + calculateSize(currentNode.getLeftChild()) + calculateSize(currentNode.getRightChild());
		} else if (currentNode.isVariable()) {
			return 1;
		} else {
			int result = 1;
			for (FOLTreeNode arg : currentNode.getArguments()) {
				result += calculateSize(arg);
			}
			return result;
		}
	}

	public void buildTree(List<String> formulaPostfix) {

		TypeTesterFirstOrderLogic operatorType = new TypeTesterFirstOrderLogic();
		Stack<FOLTreeNode> treeStack = new Stack<FOLTreeNode>();
		FOLTreeNode current;
		FOLTreeNode currentLeft;
		FOLTreeNode currentRight;
		for (int i = 0; i < formulaPostfix.size(); i++) {
			if (CheckSyntax.checkFunctionSyntaxBoolean(formulaPostfix.get(i))) {
				current = new FOLTreeNode(formulaPostfix.get(i));
				treeStack.push(current);

			} else if (operatorType
					.isOperatorExcludeNegation(CorrespondingConnector.getRemodeledConnector(formulaPostfix.get(i)))) {
				currentRight = treeStack.pop();
				currentLeft = treeStack.pop();
				current = new FOLTreeNode(formulaPostfix.get(i), currentLeft, currentRight);
				treeStack.push(current);

			} else if (formulaPostfix.get(i).equals("!")
					|| TypeTesterFirstOrderLogic.isCuantifierWithTerm(formulaPostfix.get(i))) {
				currentLeft = treeStack.pop();
				current = new FOLTreeNode(formulaPostfix.get(i), currentLeft, null);
				treeStack.push(current);
			}
		}

		this.root = treeStack.pop();
	}

	public void reaplceImplications(FOLTreeNode currentNode) {
		if (currentNode.getLabel().equals("->")) {
			currentNode.setLabel("\\/");
			FOLTreeNode leftChild = currentNode.getLeftChild();
			currentNode.setLeftChild(new FOLTreeNode("!", leftChild, null));
		} else if (currentNode.getLabel().equals("<-")) {
			currentNode.setLabel("\\/");
			FOLTreeNode rightChild = currentNode.getRightChild();
			currentNode.setRightChild(new FOLTreeNode("!", rightChild, null));
		} else if (currentNode.getLabel().equals("<->")) {
			currentNode.setLabel("/\\");
			FOLTreeNode leftChild = currentNode.getLeftChild();
			FOLTreeNode rightChild = currentNode.getRightChild();
			currentNode.setLeftChild(new FOLTreeNode("->", leftChild, rightChild));
			currentNode.setRightChild(new FOLTreeNode("->", rightChild, leftChild));

		}
		if (currentNode.getLeftChild() != null) {
			reaplceImplications(currentNode.getLeftChild());
		}
		if (currentNode.getRightChild() != null) {
			reaplceImplications(currentNode.getRightChild());
		}
	}

	private  List<String> getBoundVariables(FOLTreeNode t)
	{
		List<String> bound=new ArrayList<String>(); 
		if(t.isConnector() && !TypeTesterFirstOrderLogic.isCuantifierWithTerm(t.getLabel()))
		{
			if(t.getLabel().equals("!"))
			{
				bound=getBoundVariables(t.getLeftChild());
			}
			else
			{
				bound.addAll(getBoundVariables(t.getLeftChild()));
				bound.addAll(getBoundVariables(t.getRightChild()));
			}
		}
		else if(TypeTesterFirstOrderLogic.isCuantifierWithTerm(t.getLabel()))
		{
			bound=getBoundVariables(t.getLeftChild());
			bound.add(t.getLabel().substring(1,t.getLabel().length()-1));
		}
		return new ArrayList<String>(new HashSet<>(bound));

	}
	
	public List<String> getBound()
	{
		return getBoundVariables(this.root);
	}
	
	public static List<String> getFreeVariables(FOLTreeNode t) {
		List<String> free = new ArrayList<String>();
		if(t.isConnector() && !TypeTesterFirstOrderLogic.isCuantifierWithTerm(t.getLabel()))
		{
			if(t.getLabel().equals("!"))
			{
				free=getFreeVariables(t.getLeftChild());
			}
			else
			{
				free.addAll(getFreeVariables(t.getLeftChild()));
				free.addAll(getFreeVariables(t.getRightChild()));
			}
		}
		else if(TypeTesterFirstOrderLogic.isCuantifierWithTerm(t.getLabel()))
			{
				free = getFreeVariables(t.getLeftChild());
				String boundVariable=t.getLabel().substring(1, t.getLabel().length() - 1);
				while(free.contains(boundVariable))
				{
					free.remove(boundVariable);
				}
			}
		 else if (!t.isConnector() && !t.isVariable() && t.getArguments().size()!=0 ) {
			for (FOLTreeNode subTerm : t.getArguments()) {
				if (subTerm.isVariable()) {
					free.add(subTerm.getLabel());
				} else
					free.addAll(getFreeVariables(subTerm));
			}
		}
		
		return new ArrayList<String>(new HashSet<>(free));
	}
	public static void getAllVariables(FOLTreeNode currentNode,List<String> variables)
	{
		String label=currentNode.getLabel();
		if( currentNode.isVariable() && !variables.contains(label))
		{
			variables.add(label);
		}
		else if(currentNode.isConnector())
		{
			if(TypeTesterFirstOrderLogic.isCuantifierWithTerm(currentNode.getLabel()))
			{
				variables.add(currentNode.getLabel().substring(1, currentNode.getLabel().length()-1));
			}
			if(currentNode.getLeftChild()!=null)
			{
				getAllVariables(currentNode.getLeftChild(),variables);
			}
			if(currentNode.getRightChild()!=null)
			{
				getAllVariables(currentNode.getRightChild(),variables);
			}
		}
		else if(!currentNode.isConnector() && !currentNode.isVariable())
		{
			for(FOLTreeNode arg:currentNode.getArguments())
			{
				if(arg.isVariable() && !variables.contains(arg.getLabel()))
				{
					variables.add(arg.getLabel());
				}
				else
				{
					getAllVariables(arg,variables);
				}
			}
		}
	}
	public List<String> getVariables()
	{
		List<String> variables=new ArrayList<String>();
		getAllVariables(this.root,variables);
		return variables;
	}
	public List<String> getFree()
	{
		return getFreeVariables(this.root);
	}
	
	public static void replaceVariable(FOLTreeNode currentNode,Substitution substitution)
	{
		if(currentNode.isConnector())
		{
			if(currentNode.getLeftChild()!=null)
			{
				replaceVariable(currentNode.getLeftChild(),substitution);
			}
			if(currentNode.getRightChild()!=null)
			{
				replaceVariable(currentNode.getRightChild(),substitution);
			}
		}
		else if(!currentNode.isConnector() && !currentNode.isVariable())
		{
			for(FOLTreeNode arg:currentNode.getArguments())
			{
				replaceVariable(arg,substitution);
			}
		}
		else if(currentNode.isVariable() && currentNode.equals(substitution.Initial))
		{
			currentNode.setLabel(substitution.Final.getLabel());
			List<FOLTreeNode> newArg=new ArrayList<FOLTreeNode>();
			for(FOLTreeNode arg:substitution.Final.getArguments())
			{
				newArg.add(new FOLTreeNode(arg));
			}
			currentNode.setArguments(newArg);
			currentNode.setVariable(substitution.Final.isVariable());
		}
	}
	

	public Explanation getSubfExplanation() {
		Explanation exp = new Explanation();
		String initialMessage = "= " + Explanation.addSuform(this.root.toString());
		exp.messages.add(initialMessage);
		exp.previousMessage = initialMessage;
		generateSubfExplanation(this.root, exp);
		return exp;

	}


	private void generateSubfExplanation(FOLTreeNode currentNode, Explanation explanation) {
		if ((!currentNode.isConnector() && !currentNode.isVariable())
				&& explanation.previousMessage.contains(Explanation.addSuform(currentNode.toString()))) {
			String newMessage = explanation.previousMessage.replace(Explanation.addSuform(currentNode.toString()),
					Explanation.addBraces(currentNode.toString()));
			explanation.messages.add(new String(newMessage));
			explanation.previousMessage = newMessage;
		} else if ((currentNode.getLabel().equals("!") || TypeTesterFirstOrderLogic.isCuantifierWithTerm(currentNode.getLabel()))
				&& explanation.previousMessage.contains(Explanation.addSuform(currentNode.toString()))) {
			String newMessage = explanation.previousMessage.replace(Explanation.addSuform(currentNode.toString()),
					Explanation.addBraces(currentNode.toString()) + " U "
							+ Explanation.addSuform((currentNode.getLeftChild().toString())));
			explanation.messages.add(new String(newMessage));
			explanation.previousMessage = newMessage;
			generateSubfExplanation(currentNode.getLeftChild(), explanation);

		} else if (explanation.previousMessage.contains(Explanation.addSuform(currentNode.toString()))) {
			String newMessage = explanation.previousMessage.replace(Explanation.addSuform(currentNode.toString()),
					Explanation.addBraces(currentNode.toString()) + " U "
							+ Explanation.addSuform(currentNode.getLeftChild().toString()) + " U "
							+ Explanation.addSuform((currentNode.getRightChild().toString())));
			explanation.messages.add(new String(newMessage));
			explanation.previousMessage = newMessage;
			generateSubfExplanation(currentNode.getLeftChild(), explanation);
			generateSubfExplanation(currentNode.getRightChild(), explanation);

		}
	}
	public Explanation getVarsExplanation() {
		Explanation exp = new Explanation();
		String initialMessage = "= " + Explanation.addVars(this.root.toString());
		exp.messages.add(initialMessage);
		exp.previousMessage = initialMessage;
		generateVarsExplanation(this.root, exp);
		return exp;

	}
	private void generateVarsExplanation(FOLTreeNode currentNode, Explanation explanation) {
		if (currentNode.isVariable()
				&& explanation.previousMessage.contains(Explanation.addVars(currentNode.toString()))) {
			String newMessage = explanation.previousMessage.replace(Explanation.addVars(currentNode.toString()),
					Explanation.addBraces(currentNode.getLabel()));
			explanation.messages.add(new String(newMessage));
			explanation.previousMessage = newMessage;
		} 
		else if((!currentNode.isConnector() && !currentNode.isVariable())
				&& explanation.previousMessage.contains(Explanation.addVars(currentNode.toString())))
		{
			if(currentNode.getArguments().size()==0)
			{
				String newMessage = explanation.previousMessage.replace(Explanation.addVars(currentNode.toString()),"");
				explanation.messages.add(new String(newMessage));
				explanation.previousMessage = newMessage;
			}
			else
			{
				String replace=Explanation.addVars(currentNode.getArguments().get(0).toString());
				for(int i=1;i<currentNode.getArguments().size();i++)
				{
					replace+=" U "+Explanation.addVars(currentNode.getArguments().get(i).toString());
				}
				String newMessage = explanation.previousMessage.replace(Explanation.addVars(currentNode.toString()),replace);
				explanation.messages.add(new String(newMessage));
				explanation.previousMessage = newMessage;
				for(FOLTreeNode arg:currentNode.getArguments())
				{
					generateVarsExplanation(arg, explanation);
				}

			}
		}
		else if ((currentNode.getLabel().equals("!") || TypeTesterFirstOrderLogic.isCuantifierWithTerm(currentNode.getLabel()))
				&& explanation.previousMessage.contains(Explanation.addVars(currentNode.toString()))) {
			String newMessage = explanation.previousMessage.replace(Explanation.addVars(currentNode.toString()),
					Explanation.addVars((currentNode.getLeftChild().toString())));
			explanation.messages.add(new String(newMessage));
			explanation.previousMessage = newMessage;
			generateVarsExplanation(currentNode.getLeftChild(), explanation);

		} else if (explanation.previousMessage.contains(Explanation.addVars(currentNode.toString()))) {
			String newMessage = explanation.previousMessage.replace(Explanation.addVars(currentNode.toString()),
					Explanation.addVars(currentNode.getLeftChild().toString()) + " U "
							+ Explanation.addVars((currentNode.getRightChild().toString())));
			explanation.messages.add(new String(newMessage));
			explanation.previousMessage = newMessage;
			generateVarsExplanation(currentNode.getLeftChild(), explanation);
			generateVarsExplanation(currentNode.getRightChild(), explanation);

		}
	}
	public FOLTreeNode existentialClosure()
	{
		FOLTreeNode initial=new FOLTreeNode(this.root);
		List<String> free=this.getFree();
		for(String variable:free)
		{
			String cuantifier="E"+variable+".";
			FOLTreeNode newCuantifier=new FOLTreeNode(cuantifier,initial,null);
			initial=newCuantifier;
		}
		return initial;
	}
	
	public FOLTreeNode UniversalClosure()
	{
		FOLTreeNode initial=new FOLTreeNode(this.root);
		List<String> free=this.getFree();
		for(String variable:free)
		{
			String cuantifier="V"+variable+".";
			FOLTreeNode newCuantifier=new FOLTreeNode(cuantifier,initial,null);
			initial=newCuantifier;
		}
		return initial;
	}	
	public void executeSubstitution(Substitution substitution)
	{
		replaceVariable(this.root,substitution);
	}
	public void replaceSubTree(FOLTreeNode toReplace,FOLTreeNode newSubTree)
	{
		this.root.replace(toReplace, newSubTree);
	}
	
	public void getAllSubformulas(FOLTreeNode currentNode,List<String> subformulas)
	{
		String formula=currentNode.toString();
		if(!subformulas.contains(formula))
		{
			subformulas.add(formula);
		}
		if(currentNode.getLeftChild()!=null)
		{
			getAllSubformulas(currentNode.getLeftChild(),subformulas);
		}
		if(currentNode.getRightChild()!=null)
		{
			getAllSubformulas(currentNode.getRightChild(),subformulas);
		}
		
	}
	
	public List<String> getSubformulas()
	{
		List<String> subformulas=new ArrayList<String>();
		getAllSubformulas(this.root,subformulas);
		return subformulas;
	}
	
	public void getAllSubfFunctionsAndPredicates(FOLTreeNode currentNode,List<String> functions)
	{
		String function=currentNode.getLabel();
		if(!currentNode.isVariable() && !currentNode.isConnector())
		{
			if(!functions.contains(function))
		{
				functions.add(function);
		}
		}
		if(currentNode.getLeftChild()!=null)
		{
			getAllSubfFunctionsAndPredicates(currentNode.getLeftChild(),functions);
		}
		if(currentNode.getRightChild()!=null)
		{
			getAllSubfFunctionsAndPredicates(currentNode.getRightChild(),functions);
		}
		if(currentNode.getArguments()!=null)
		{
			for(FOLTreeNode arg:currentNode.getArguments())
			{
				getAllSubfFunctionsAndPredicates(arg,functions);

			}
		}
		
	}
	public void removeCuantifierFromTree(String cuantifierText)
	{
		if(TypeTesterFirstOrderLogic.isCuantifierWithTerm(cuantifierText))
		{	removeCuantifier(this.root,cuantifierText);
		if(this.root.getLabel().equals(cuantifierText))
		{
			this.root=this.root.getLeftChild();
		}
		}
	}
	private  void removeCuantifier(FOLTreeNode currentNode,String cuantifierText)
	{
		if(currentNode.getLeftChild()!=null)
		{
			if(currentNode.getLeftChild().getLabel().equals(cuantifierText))
			{
				currentNode.setLeftChild(currentNode.getLeftChild().getLeftChild());
			}
			removeCuantifier(currentNode.getLeftChild(),cuantifierText);
		}
		if(currentNode.getRightChild()!=null)
		{
			if(currentNode.getRightChild().getLabel().equals(cuantifierText))
			{
				currentNode.setRightChild(currentNode.getRightChild().getLeftChild());
			}
			removeCuantifier(currentNode.getRightChild(),cuantifierText);
		}	
	}
	public List<String> getFunctionsAndPredicates()
	{
		List<String> functions=new ArrayList<String>();
		getAllSubfFunctionsAndPredicates(this.root,functions);
		return functions;
	}
	
	public boolean isSubTree(FOLTree tree)
	{
		return this.getRoot().isSubTree(tree.getRoot());
	}
	@Override
	public String toString() {
		return this.root.toString();
	}

}
