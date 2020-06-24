package application.FormulaAnalysis;



import AbstractSyntaxTree.TreeNode;
import Formulas.Formula;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class TreeDraw {
	
	public static void draw(Formula formula)
	{
		Stage window=new Stage();
		window.getIcons().add(new Image("./application/Resources/Logo-FII.png"));
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
	
	public static void drawNode(Canvas canvas,TreeNode node,int x,int y,int length)
	{
		GraphicsContext context=canvas.getGraphicsContext2D();
		context.setFill(Color.MEDIUMSEAGREEN);
		context.fillOval(x, y, 60, 60);
		context.setFont(new Font("Arial", 20));
		if(node.getLabel().matches("[a-zA-Z]"))
		{
			context.setFill(Color.RED);
		}
		else
		{
			context.setFill(Color.BLUE);
		}
		context.fillText(node.getLabel(), x +20, y +40);
		if(node.getLeftChild()!=null)
		{
			int newX;
			int newY;
			if(node.getLabel().equals("!"))
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
