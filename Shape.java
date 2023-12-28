import java.awt.Graphics;
import java.awt.Color;

abstract class Shape{
	// Define the parameters of any shape
	int x1, y1;
	int x2, y2;
	Color shapeColor;
	boolean isSolid;
	
	// To set the first points at pressing the mouse
	abstract void setFirstPoint(int x1, int y1);
	
	// To set the end points at releasing the mouse
	abstract void setEndPoint(int x2, int y2);
	
	// To set the color of the shape
	void setColor(Color shapeColor){
		this.shapeColor = shapeColor;
	}
	
	// The draw method of the shape
	abstract void draw(Graphics graphicsObj);	
}

class Line extends Shape{
	
	// The default constructor of the Line
	Line(){
	}
	
	// To set the first points at pressing the mouse
	@Override
	void setFirstPoint(int x1, int y1){
		this.x1 = x1;
		this.y1 = y1;
	}
	
	// To set the end points at releasing the mouse
	@Override
	void setEndPoint(int x2, int y2){
		this.x2 = x2;
		this.y2 = y2;
	}
	
	// The draw method of the Line
	@Override
	void draw(Graphics graphicsObj){
		graphicsObj.setColor(shapeColor);
		graphicsObj.drawLine(x1, y1, x2, y2);
	}
}

class Rectangle extends Shape{
	// The default constructor of the Rectangle
	Rectangle(boolean isSolid){
		this.isSolid = isSolid;
	}
	
	// To set the first points at pressing the mouse
	@Override
	void setFirstPoint(int x1, int y1){
		this.x1 = x1;
		this.y1 = y1;
	}
	
	// To set the end points at releasing the mouse
	@Override
	void setEndPoint(int x2, int y2){
		this.x2 = x2;
		this.y2 = y2;
	}
	
	// The draw method of the Line
	@Override
	void draw(Graphics graphicsObj){
		graphicsObj.setColor(shapeColor);
		if(this.isSolid){
			graphicsObj.fillRect(Math.min(x1, x2), Math.min(y1, y2), Math.abs(x2 - x1), Math.abs(y2 - y1));
		}
		graphicsObj.drawRect(Math.min(x1, x2), Math.min(y1, y2), Math.abs(x2 - x1), Math.abs(y2 - y1));
	}
}

class Oval extends Shape{
	// The default constructor of the Rectangle
	Oval(boolean isSolid){
		this.isSolid = isSolid;
	}
	
	// To set the first points at pressing the mouse
	@Override
	void setFirstPoint(int x1, int y1){
		this.x1 = x1;
		this.y1 = y1;
	}
	
	// To set the end points at releasing the mouse
	@Override
	void setEndPoint(int x2, int y2){
		this.x2 = x2;
		this.y2 = y2;
	}
	
	// The draw method of the Line
	@Override
	void draw(Graphics graphicsObj){
		graphicsObj.setColor(shapeColor);
		if(this.isSolid){
			graphicsObj.fillOval(Math.min(x1, x2), Math.min(y1, y2), Math.abs(x2 - x1), Math.abs(y2 - y1));
		}
		graphicsObj.drawOval(Math.min(x1, x2), Math.min(y1, y2), Math.abs(x2 - x1), Math.abs(y2 - y1));
	}
}