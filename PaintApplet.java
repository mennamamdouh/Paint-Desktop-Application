import java.applet.Applet;
import java.awt.Graphics;
import java.util.ArrayList;
import java.awt.Button;
import java.awt.event.MouseEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Color;

public class PaintApplet extends Applet{
	
	// Shapes numbering rules
	public static final int LINE = 1;
	public static final int RECTANGLE = 2;
	public static final int OVAL = 3;
	
	// Colors numbering rules
	public static final int RED = 1;
	public static final int GREEN = 2;
	public static final int BLUE = 3;
	
	// Define the variables which hold the current states
	int currentlyDrawing = 0;	// The shape number from the shapes numbering rules
	int currentColor;	// The color number from the colors numbering rules
	Shape currentShape;
	Color shapeColor;
	
	// The parameters of any shape
	private static int x1;
	private static int y1;
	private static int x2;
	private static int y2;
	
	int indexOfShapes = 0;    // Index of the array list. At first, number of shapes = 0

	// The array list which stores the shapes
	ArrayList<Shape> shapes = new ArrayList<Shape>();
	
	public void init(){
		
		/*  Options of Shapes --> New Line - New Rectangle - New Oval  */
		
		// Our event source for the line button
		Button lineButton = new Button("Line");
		
		// Register the lineButton listener to the lineButton source
		lineButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
						currentlyDrawing = 1;
			}
		});
		add(lineButton);
		
		// Our event source for the rectangle button
		Button rectangleButton = new Button("Rectangle");
		
		// Register the rectangleButton listener to the rectangleButton source
		rectangleButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
						currentlyDrawing = 2;
			}
		});
		add(rectangleButton);
		
		// Our event source for the oval button
		Button ovalButton = new Button("Oval");
		
		// Register the rectangleButton listener to the rectangleButton source
		ovalButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
						currentlyDrawing = 3;
			}
		});
		add(ovalButton);
		
		/*  Options of Colors --> Red - Green - Blue  */
		
		// Our event source for the red button
		Button redButton = new Button("Red");
		
		// Register the redButton listener to the redButton source
		redButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				currentColor = 1;
			}
		});
		add(redButton);
		
		// Our event source for the green button
		Button greenButton = new Button("Green");
		
		// Register the greenButton listener to the greenButton source
		greenButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				currentColor = 2;
			}
		});
		add(greenButton);
		
		// Our event source for the blue button
		Button blueButton = new Button("Blue");
		
		// Register the blueButton listener to the blueButton source
		blueButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				currentColor = 3;
			}
		});
		add(blueButton);
		
		
		/*  Mouse events and listeners  */
		
		// Register the mouse listener
		addMouseListener(new MouseAdapter(){
			public void mousePressed(MouseEvent e){
				switch(currentlyDrawing){
					case 1:
						currentShape =  new Line();
					break;
					
					case 2:
						currentShape = new Rectangle();
					break;
					
					case 3:
						currentShape = new Oval();
					break;
				}
				if(currentShape != null){	// To handle if the user didn't choose a button
					// Check for the color
					switch(currentColor){
						case 1:
							shapeColor = Color.RED;
						break;
						
						case 2:
							shapeColor = Color.GREEN;
						break;
						
						case 3:
							shapeColor = Color.BLUE;
						break;
					}
					currentShape.setColor(shapeColor);
					
					x1 = e.getX();
					y1 = e.getY();
					currentShape.setFirstPoint(x1, y1);
				}
			}
			public void mouseReleased(MouseEvent e){
				if(currentShape != null){
					x2 = e.getX();
					y2 = e.getY();
					currentShape.setEndPoint(x2, y2);
					repaint();
					shapes.add(indexOfShapes, currentShape);
					indexOfShapes++;
				}
			}
		});

		// Register the mouse motion listener
		addMouseMotionListener(new MouseAdapter(){
			public void mouseDragged(MouseEvent e){
				if(currentShape != null){
					x2 = e.getX();
					y2 = e.getY();
					currentShape.setEndPoint(x2, y2);
					repaint();
				}
			}
		});
	}

	public void paint(Graphics graphicsObj){
		// Define 2 variables: width & height for the shapes rectangle and oval.
		int width;
		int height;
		
		// Draw the stored shapes
		for(int counter = 0; counter < indexOfShapes; counter ++)
		{
			Shape currentShape = shapes.get(counter);
			currentShape.draw(graphicsObj);
		}
		
		// Draw the currently drawing shape
		if(currentShape != null){
			graphicsObj.setColor(shapeColor);		// Set the shape's color
			switch(currentlyDrawing){
				case 1:
					graphicsObj.drawLine(x1, y1, x2, y2);
				break;
				
				case 2:
					graphicsObj.drawRect(x1, y1, x2 - x1, y2 - y1);
				break;
				
				case 3:
					graphicsObj.drawOval(x1, y1, x2 - x1, y2 - y1);
				break;
			}
		}
	}
}

abstract class Shape{
	// Define the parameters of any shape
	int x1, y1;
	int x2, y2;
	Color shapeColor;
	
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
		this.x2 =x2;
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
	Rectangle(){
		
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
		this.x2 = x2 - x1;
		this.y2 = y2 - y1;
	}
	
	// The draw method of the Line
	@Override
	void draw(Graphics graphicsObj){
		graphicsObj.setColor(shapeColor);
		graphicsObj.drawRect(x1, y1, x2, y2);
	}
}

class Oval extends Shape{
	// The default constructor of the Rectangle
	Oval(){
		
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
		this.x2 = x2 - x1;
		this.y2 = y2 - y1;
	}
	
	// The draw method of the Line
	@Override
	void draw(Graphics graphicsObj){
		graphicsObj.setColor(shapeColor);
		graphicsObj.drawOval(x1, y1, x2, y2);
	}
}