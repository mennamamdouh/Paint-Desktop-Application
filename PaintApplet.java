import java.applet.Applet;
import java.awt.Graphics;
import java.util.ArrayList;
import java.awt.Button;
import java.awt.event.MouseEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Color;
import java.awt.Checkbox;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.Dimension;

public class PaintApplet extends Applet{
	
	// Shapes numbering rules
	public static final int LINE = 1;
	public static final int RECTANGLE = 2;
	public static final int OVAL = 3;
	public static final int PENCIL = 4;
	public static final int ERASER = 5;
	
	// Colors numbering rules
	public static final int RED = 1;
	public static final int GREEN = 2;
	public static final int BLUE = 3;
	public static final int PINK = 4;
	public static final int CYAN = 5;
	public static final int MAGENTA = 6;
	public static final int ORANGE = 7;
	
	// Define the variables which hold the current states
	int currentlyDrawing = 0;	// The shape number from the shapes numbering rules
	int currentColor;	// The color number from the colors numbering rules
	Shape currentShape;
	Color shapeColor;
	Checkbox solidCheck;
	boolean currentSolidState;
	
	// The parameters of any shape
	private static int x1;
	private static int y1;
	private static int x2;
	private static int y2;
	
	int indexOfShapes = 0;    // Index of the array list. At first, number of shapes = 0
	ArrayList<Shape> shapes = new ArrayList<Shape>();	// The array list which stores the shapes
	
	int indexOfRemovedShapes = 0;	// Index of the array list which holds the shapes removed from Undo operation
	ArrayList<Shape> removedShapes = new ArrayList<Shape>();	// The array list which stores the removed shapes
	
	public void init(){
		
		/*  Options of Colors --> Red - Green - Blue - Pink  */
		
		// Our event source for the red button
		Button redButton = new Button("Red");
		
		// Register the redButton listener to the redButton source
		redButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				currentColor = 1;
			}
		});
		add(redButton);
		redButton.setBackground(Color.RED);
		redButton.setForeground(Color.WHITE);
		
		// Our event source for the green button
		Button greenButton = new Button("Green");
		
		// Register the greenButton listener to the greenButton source
		greenButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				currentColor = 2;
			}
		});
		add(greenButton);
		greenButton.setBackground(Color.GREEN);
		greenButton.setForeground(Color.WHITE);
		
		// Our event source for the blue button
		Button blueButton = new Button("Blue");
		
		// Register the blueButton listener to the blueButton source
		blueButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				currentColor = 3;
			}
		});
		add(blueButton);
		blueButton.setBackground(Color.BLUE);
		blueButton.setForeground(Color.WHITE);
		
		// Our event source for the pink button
		Button pinkButton = new Button("Pink");
		
		// Register the pinkButton listener to the pinkButton source
		pinkButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				currentColor = 4;
			}
		});
		add(pinkButton);
		pinkButton.setBackground(Color.PINK);
		pinkButton.setForeground(Color.WHITE);
		
		// Our event source for the cyan button
		Button cyanButton = new Button("Cyan");
		
		// Register the cyanButton listener to the cyanButton source
		cyanButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				currentColor = 5;
			}
		});
		add(cyanButton);
		cyanButton.setBackground(Color.CYAN);
		cyanButton.setForeground(Color.WHITE);
		
		// Our event source for the magenta button
		Button magentaButton = new Button("Magenta");
		
		// Register the magentaButton listener to the magentaButton source
		magentaButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				currentColor = 6;
			}
		});
		add(magentaButton);
		magentaButton.setBackground(Color.MAGENTA);
		magentaButton.setForeground(Color.WHITE);
		
		// Our event source for the orange button
		Button orangeButton = new Button("Orange");
		
		// Register the orangeButton listener to the orangeButton source
		orangeButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				currentColor = 7;
			}
		});
		add(orangeButton);
		orangeButton.setBackground(Color.ORANGE);
		orangeButton.setForeground(Color.WHITE);
		
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
		
		// Our event source for the pencil button
		Button pencilButton = new Button("Pencil");
		
		// Register the pencilButton listener to the pencilButton source
		pencilButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
						currentlyDrawing = 4;
			}
		});
		add(pencilButton);
		
		// Our event source for the eraser button
		Button eraserButton = new Button("Eraser");
		
		// Register the pencilButton listener to the pencilButton source
		eraserButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
						currentlyDrawing = 5;
			}
		});
		add(eraserButton);
		
		
		/*  Other options --> Undo - Clear All - Solid Shapes  */
		
		// Our event source for the undo button
		Button undoButton = new Button("Undo");
		
		// Register the undoButton listener to the undoButton source
		undoButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				if(indexOfShapes > 0){
					// The idea behind undo is to remove the previous shape from the array list
					Shape previousShape = shapes.get(indexOfShapes - 1);
					
					// Add it in the removed shapes array list in order to get it back in the redo operation and increment its index
					removedShapes.add(indexOfRemovedShapes, previousShape);
					indexOfRemovedShapes++;
					
					// Remove that shape from the shapes array list
					shapes.remove(indexOfShapes - 1);
					
					// Set the default properties
					currentlyDrawing = 0;
					currentColor = 0;
					currentSolidState = false;
					
					// Then repaint
					repaint();
					indexOfShapes--;
				}
			}
		});
		add(undoButton);
		
		// Our event source for the redo button
		Button redoButton = new Button("Redo");
		
		// Register the redoButton listener to the redoButton source
		redoButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				if(indexOfRemovedShapes > 0){
					// The idea behind redo is to get the last stored shape in the removedShapes array list
					Shape restoredShape = removedShapes.get(indexOfRemovedShapes - 1);
					
					// Add it again in the shapes array list
					shapes.add(indexOfShapes, restoredShape);
					indexOfShapes++;
					
					// Remove that shape from the shapes array list
					removedShapes.remove(indexOfRemovedShapes - 1);
					indexOfRemovedShapes--;
					
					// Then repaint
					repaint();
				}
			}
		});
		add(redoButton);
		
		// Our event source for the clear all button
		Button clearAllButton = new Button("Clear All");
		
		// Register the clearAllButton listener to the clearAllButton source
		clearAllButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				// Applet properties
				Dimension appletDimension = getSize();
				Color appletColor = getBackground();
				currentlyDrawing = 0;
				currentColor = 0;
				currentSolidState = false;
				
				// Draw a rectangle with the applet's background
				currentShape = new Rectangle(true);
				currentShape.setColor(appletColor);
				currentShape.setFirstPoint(0, 0);
				currentShape.setEndPoint(appletDimension.width, appletDimension.height);
				
				// Add the shape to the array list
				repaint();
				shapes.add(indexOfShapes, currentShape);
				indexOfShapes++;
			}
		});
		add(clearAllButton);
		
		// Our event source for the solid check box
		solidCheck = new Checkbox("Solid");
		
		// Register the solidCheck listener to the solidCheck source
		solidCheck.addItemListener(new ItemListener(){
			public void itemStateChanged(ItemEvent e){
				if(e.getStateChange() == ItemEvent.SELECTED){
					currentSolidState = true;
				}
				else{
					currentSolidState = false;
				}
			}
		});
		add(solidCheck);
		
		
		/*  Mouse events and listeners  */
		
		// Register the mouse listener
		addMouseListener(new MouseAdapter(){
			public void mousePressed(MouseEvent e){
				switch(currentlyDrawing){
					case LINE:
						currentShape =  new Line();
					break;
					
					case RECTANGLE:
						currentShape = new Rectangle(currentSolidState);
					break;
					
					case OVAL:
						currentShape = new Oval(currentSolidState);
					break;
					
					case PENCIL:
						currentShape = new Line();
					break;
					
					case ERASER:
						currentShape = new Line();
					break;
					
					default:
						currentShape = null;
					break;
				}
				if(currentShape != null){	// To handle if the user didn't choose a button
					// Check for the color
					switch(currentColor){
						case RED:
							shapeColor = Color.RED;
						break;
						
						case GREEN:
							shapeColor = Color.GREEN;
						break;
						
						case BLUE:
							shapeColor = Color.BLUE;
						break;
						
						case PINK:
							shapeColor = Color.PINK;
						break;
						
						case CYAN:
							shapeColor = Color.CYAN;
						break;
						
						case MAGENTA:
							shapeColor = Color.MAGENTA;
						break;
						
						case ORANGE:
							shapeColor = Color.ORANGE;
						break;
						
						default:
							shapeColor = Color.BLACK;
						break;
					}
					currentShape.setColor(shapeColor);
					
					// Get the first point
					x1 = e.getX();
					y1 = e.getY();
				}
			}
			public void mouseReleased(MouseEvent e){
				if(currentShape != null){
					// Get the end point then add the shape in the array list
					x2 = e.getX();
					y2 = e.getY();
					currentShape.setEndPoint(x2, y2);
					
					// Repaint and add to the array list
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
					// Check the Pencil and Eraser cases
					switch(currentlyDrawing){
						case 4:
							
							// Create new line objects while dragging
							currentShape = new Line();
							currentShape.setColor(shapeColor);
							
							// First coordinate
							currentShape.setFirstPoint(x1, y1);
							
							// Get the end point
							x2 = e.getX();
							y2 = e.getY();
							currentShape.setEndPoint(x2, y2);
							
							// Then add each line object
							shapes.add(indexOfShapes, currentShape);
							indexOfShapes++;
							
							// Update the next first point from we ended
							x1 = x2;
							y1 = y2;
						break;
						
						case 5:
							// Create new line objects while dragging
							currentShape = new Line();
							currentShape.setColor(Color.WHITE);
							
							// First coordinate
							currentShape.setFirstPoint(x1, y1);
							
							// Get the end point
							x2 = e.getX();
							y2 = e.getY();
							currentShape.setEndPoint(x2, y2);
							
							// Then add each line object
							shapes.add(indexOfShapes, currentShape);
							indexOfShapes++;
							
							// Update the next first point from we ended
							x1 = x2;
							y1 = y2;
						break;
						
						default:
							// First coordinate
							currentShape.setFirstPoint(x1, y1);
							
							// Get the end point then add the shape in the array list
							x2 = e.getX();
							y2 = e.getY();
							currentShape.setEndPoint(x2, y2);
						break;
					}
					
					// Repaint and add to the array list
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
				case LINE:
					graphicsObj.drawLine(x1, y1, x2, y2);
				break;
				
				case RECTANGLE:
					if(currentSolidState){
						graphicsObj.fillRect(Math.min(x1, x2), Math.min(y1, y2), Math.abs(x2 - x1), Math.abs(y2 - y1));
					}
					graphicsObj.drawRect(Math.min(x1, x2), Math.min(y1, y2), Math.abs(x2 - x1), Math.abs(y2 - y1));
				break;
				
				case OVAL:
					if(currentSolidState){
						graphicsObj.fillOval(Math.min(x1, x2), Math.min(y1, y2), Math.abs(x2 - x1), Math.abs(y2 - y1));
					}
					graphicsObj.drawOval(Math.min(x1, x2), Math.min(y1, y2), Math.abs(x2 - x1), Math.abs(y2 - y1));
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