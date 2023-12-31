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
	boolean wasDragging = false;	// To check if I was dragging before pressing or not
	
	// The parameters of any shape
	private static int x1;
	private static int y1;
	private static int x2;
	private static int y2;
	
	int indexOfShapes = 0;    // Index of the array list. At first, number of shapes = 0
	ArrayList<Shape> shapes = new ArrayList<Shape>();	// The array list which stores the shapes
	
	public void init(){
		
		/*  Options of Shapes --> Line - Rectangle - Oval - Free-hand Pencil & Eraser  */
		
		// Our event source for the line button
		Button lineButton = new Button("Line");
		// Register the lineButton listener to the lineButton source
		lineButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
						currentlyDrawing = 1;
			}
		});
		add(lineButton);
		lineButton.setBackground(Color.DARK_GRAY);
		lineButton.setForeground(Color.WHITE);
		
		// Our event source for the rectangle button
		Button rectangleButton = new Button("Rectangle");
		// Register the rectangleButton listener to the rectangleButton source
		rectangleButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
						currentlyDrawing = 2;
			}
		});
		add(rectangleButton);
		rectangleButton.setBackground(Color.DARK_GRAY);
		rectangleButton.setForeground(Color.WHITE);
		
		// Our event source for the oval button
		Button ovalButton = new Button("Oval");
		// Register the rectangleButton listener to the rectangleButton source
		ovalButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
						currentlyDrawing = 3;
			}
		});
		add(ovalButton);
		ovalButton.setBackground(Color.DARK_GRAY);
		ovalButton.setForeground(Color.WHITE);
		
		// Our event source for the pencil button
		Button pencilButton = new Button("Pencil");
		// Register the pencilButton listener to the pencilButton source
		pencilButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
						currentlyDrawing = 4;
			}
		});
		add(pencilButton);
		pencilButton.setBackground(Color.DARK_GRAY);
		pencilButton.setForeground(Color.WHITE);
		
		// Our event source for the eraser button
		Button eraserButton = new Button("Eraser");
		// Register the pencilButton listener to the pencilButton source
		eraserButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
						currentlyDrawing = 5;
			}
		});
		add(eraserButton);
		eraserButton.setBackground(Color.DARK_GRAY);
		eraserButton.setForeground(Color.WHITE);
		
		
		/* Extra options -> Undo - Clear All */
		
		// Our event source for the undo button
		Button undoButton = new Button("Undo");
		
		// Register the undoButton listener to the undoButton source
		undoButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				if(indexOfShapes > 0){
					/* The idea behind undo is to remove the last-added shape from the shapes array list */
					
					// Removed the last-added shape
					shapes.remove(indexOfShapes - 1);
					
					// Set the default properties -> The user has to select the shape again to be able to draw
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
		undoButton.setBackground(Color.DARK_GRAY);
		undoButton.setForeground(Color.WHITE);
		
		// Our event source for the clear all button
		Button clearAllButton = new Button("Clear All");
		
		// Register the clearAllButton listener to the clearAllButton source
		clearAllButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				/* The idea behind the clearAllButton is to draw a rectangle that has the same size and background color of the applet */
				
				// Applet properties -> size and background color
				Dimension appletDimension = getSize();
				Color appletColor = getBackground();
				
				// Set the default properties
				currentlyDrawing = 0;
				currentColor = 0;
				currentSolidState = false;
				
				// Draw the required rectangle
				currentShape = new Rectangle(true);
				currentShape.setColor(appletColor);
				currentShape.setFirstPoint(0, 0);
				currentShape.setEndPoint(appletDimension.width, appletDimension.height);
				
				// Add the rectangle as a shape to the array list
				repaint();
				shapes.add(indexOfShapes, currentShape);
				indexOfShapes++;
			}
		});
		add(clearAllButton);
		clearAllButton.setBackground(Color.DARK_GRAY);
		clearAllButton.setForeground(Color.WHITE);
		
		
		/*  Options of Colors --> Red - Green - Blue - Pink - Cyan - Magenta - Orange  */
		
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
		
		// Our event source for the solid check box
		solidCheck = new Checkbox("Solid");
		
		// Register the solidCheck listener to the solidCheck source
		solidCheck.addItemListener(new ItemListener(){
			public void itemStateChanged(ItemEvent e){
				// Check for the state of the checkbox, if it's selected then marks the currentSolidState to be true
				// so that the shape can be drawn as solid, else marks the currentSolidState to be false
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
				checkShape(currentlyDrawing);	// Call the method that checks the value of currentlyDrawing and creates new object from the target shape class
				if(currentShape != null){	// To handle if the user didn't choose a button
					checkColor(currentColor);	// Call the method that checks the value of currentcolor and set the appropriate color to the currentShape
					// Get the first point
					x1 = e.getX();
					y1 = e.getY();
				}
			}
			
			public void mouseReleased(MouseEvent e){
				if(currentShape != null){
					if(wasDragging){
						x2 = e.getX();
						y2 = e.getY();
						currentShape.setEndPoint(x2, y2);
						// Repaint and add the shape to the array list
						repaint();
						shapes.add(indexOfShapes, currentShape);
						indexOfShapes++;
						wasDragging = false;
					}
				}
			}
		});

		// Register the mouse motion listener
		addMouseMotionListener(new MouseAdapter(){
			public void mouseDragged(MouseEvent e){
				if(currentShape != null){
					// First, raise the flag of wasDragging
					wasDragging = true;
					
					// Initialize the width and height of the rectangle which is drawn using Pencil or Eraser
					int width = 3;
					int height = 3;
					
					/* Check the Pencil and Eraser cases.
						The idea behind them is to add a new line object while I'm dragging. This line will be very short, just one-unit length.
						If it's a pencil, it will be colored with the selected color. If it's an eraser, it will be white. */
					
					switch(currentlyDrawing){
						case PENCIL:
							// Create new line objects while dragging
							currentShape = new Rectangle(true);
							currentShape.setColor(shapeColor);
							// Set the first coordinate, the width and height of the eraser
							currentShape.setFirstPoint(x1, y1);
							// Set the end point as it's 5 unit apart from the first point
							currentShape.setEndPoint(x1 + width, y1 + height);
							// Then add each line object
							shapes.add(indexOfShapes, currentShape);
							indexOfShapes++;
							// Update the next first point from we ended
							x1 = e.getX();
							y1 = e.getY();
						break;
						
						case ERASER:
							// Create new freehand objects while dragging
							currentShape = new Rectangle(true);
							currentShape.setColor(Color.WHITE);
							// Set the first coordinate, the width and height of the eraser
							currentShape.setFirstPoint(x1, y1);
							// Set the end point as it's 5 unit apart from the first point
							currentShape.setEndPoint(x1 + width, y1 + height);
							// Then add each line object
							shapes.add(indexOfShapes, currentShape);
							indexOfShapes++;
							// Update the next first point from we ended
							x1 = e.getX();
							y1 = e.getY();
						break;
						
						default:	// The code for normal shapes
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
		// Draw the stored shapes
		for(int counter = 0; counter < indexOfShapes; counter ++)
		{
			Shape currentShape = shapes.get(counter);
			currentShape.draw(graphicsObj);
		}
		drawCurrentShape(graphicsObj);	// Then call the method that draws the current selected shape
	}
	
	/* This method to check the currentColor choice that results from the color buttons selection, and then switch on this value to set an appropriate color */
	public void checkColor(int currentColor){
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
	}
	
	/* This method to check the currentlyDrawing choice that results from the shape buttons selection, and then creates new object from the appropriate shape */
	public void checkShape(int currentlyDrawing){
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
				currentShape = new Rectangle(true);
			break;
			
			case ERASER:
				currentShape = new Rectangle(true);
			break;
			
			default:
				currentShape = null;
			break;
		}
	}

	/* This method for drawing the current selected shape */
	public void drawCurrentShape(Graphics graphicsObj){
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