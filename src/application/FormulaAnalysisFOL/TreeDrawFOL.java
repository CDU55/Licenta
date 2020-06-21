package application.FormulaAnalysisFOL;

import AbstractSyntaxTree.FOLTreeNode;
import Formulas.FOLFormula;
import Operators.TypeTesterFirstOrderLogic;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class TreeDrawFOL {
	public static void draw(FOLFormula formula)
	{
		Stage window=new Stage();
		window.setTitle("Abstract Syntax Tree");
		Canvas canvas = new Canvas();
		canvas.setWidth(1600);
		canvas.setHeight(900);
		VBox layout = new VBox();
		layout.getChildren().add(canvas);
		int height=formula.syntaxTree.getHeight();
		int factor=height/2;
		int initialLenght=0;
		if(factor!=0)
		{
			initialLenght=(int)((canvas.getHeight()-200)/(height/2));
		if(initialLenght>canvas.getHeight()/3)
		{
			initialLenght=(int)(canvas.getHeight()/3);
		}
		}
		else
		{
			initialLenght=(int)(canvas.getHeight()/3);

		}
		int intialX=(int) (canvas.getWidth()/2);
		int initialY=0;
		drawNode(canvas,formula.syntaxTree.getRoot(),intialX,initialY,initialLenght);
		Scene scene = new Scene(layout, 1600, 900);
		window.setScene(scene);
		window.show();
	}
	
	public static void drawNode(Canvas canvas,FOLTreeNode node,int x,int y,int length)
	{
		GraphicsContext context=canvas.getGraphicsContext2D();
		context.setFill(Color.MEDIUMSEAGREEN);
		context.fillOval(x, y, 60, 60);
		context.setFont(new Font("Arial", 20));
		String label=null;
		if(!node.isConnector() && !node.isVariable())
		{
			context.setFill(Color.RED);
			label=node.toString();
			context.fillText(label, x +20, y +40);
			if(node.getArguments()!=null && !node.getArguments().isEmpty())
			{
				int distance=45;
				if(node.getArguments().size()!=1)
				{
					distance=45/(node.getArguments().size()-1);
				}
				int angle=90;
				for(FOLTreeNode arg:node.getArguments())
			{
					
					int newX=(int)(x+length*Math.cos(Math.toRadians(angle)));
					int newY=(int)(y+length*Math.sin(Math.toRadians(angle)));
					context.setStroke(Color.MEDIUMSEAGREEN);
					context.strokeLine(x+30, y+30, newX+30, newY+30);
					drawNode(canvas,arg,newX,newY,length/2);
					angle-=distance;
			}
			}
		}
		else
		{
			context.setFill(Color.BLUE);
			label=node.getLabel();
			context.fillText(label, x +20, y +40);
		}
		if(node.getLeftChild()!=null)
		{
			int newX;
			int newY;
			if(node.getLabel().equals("!") || TypeTesterFirstOrderLogic.isCuantifierWithTerm(node.getLabel()))
			{
				newX=x;
				newY=y+length;
			}
			else
			{
				newX=(int)(x+length*Math.cos(90));
				newY=(int)(y+length*Math.sin(90));
			}
			context.setStroke(Color.MEDIUMSEAGREEN);
			context.strokeLine(x+30, y+30, newX+30, newY+30);
			drawNode(canvas,node.getLeftChild(),newX,newY,length/4*3);
		}
		if(node.getRightChild()!=null)
		{
			int newX;
			int newY;
				newX=(int)(x+length*Math.cos(45));
				newY=(int)(y+length*Math.sin(45));
				context.setStroke(Color.MEDIUMSEAGREEN);
				context.strokeLine(x+30, y+30, newX+30, newY+30);
			drawNode(canvas,node.getRightChild(),newX,newY,length/4*3);
		}
	}
}
