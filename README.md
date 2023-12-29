# Paint-Desktop-Application

## About Paint Desktop Application
Paint is a simple raster graphics editor. It's built in Java Programming Language with OOP Principles.\
The program lets you draw lines and shapes such as *Rectangle* and *Oval* with different colors picked from a color pallete. Also, you can draw with free-hand pencil and erase whatever you want with the *Eraser*. It has some extra features such as fill shapes, *Undo*, and *Clear All* options.

<p align="center">
<img src="images/Screenshot 2023-12-29 003158.png">
</p>

---

## Implementing Paint Desktop Application in Java
As mentioned, OOP principles such as *Encapsulation*, *Inheritance*, and *Polymorphism* are used to get this project done.\
It's mainly divided into 2 java files. The first file contains the main classes for shapes. *Shape* class is the parent class which has the main features of any shape, such as its dimensions, color, and *draw* method. There're 3 children for the *Shape* class, *Line*, *Rectangle*, and *Oval* classes. Each child class has its own version of the *draw* method as overriden method.
```java
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
```
The second file contains the code of Applet. It has all the buttons needed and mouse events as event sources, and their action and mouse listeners. Also, it contains the strategy of drawing each selected shape with the picked color.

### The strategy of drawing shapes with picked color
- Each shape or color button has an ID which is fixed over the code. Once the user clicked on a button, this ID is stored in a variable.
  ```java
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
  ```
  - Inside the button event, one of each numbering rules is assigned to a variable, one represents the shape and the other represents the picked color.
  - If we pressed the *Rectangle* and *Pink* buttons, those variables are 2 for *Rectangle* shape, and 4 for *Pink* color.
    ```java
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
    ```
    ```java
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
    ```
- Once the mouse is pressed, it checks for the color and the shape that are selected, then creates an object of that shape's class and begins to assign its parameters.
    ```java
	public void checkColor(int currentColor){
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
    ```
    ```java
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
    ```
- After the mouse is released, the drawn shape is added to an array list which contains all the previous drawn shapes.
- The idea behind *Pencil* and *Eraser* is to draw very small rectangles over and over while we're dragging the mouse.
  - Pencil draws with a picked color.
  - Eraser draws with the Applet's background color (white in our case).
- Regarding the extra options, *Solid Shapes*, *Clear All*, and *Undo*.
  - Solid Shapes are drawn through a checkbox. If this box is checked, we use `fill` method instead of `draw`. For example, to draw a solid rectangle.
    ```java
    if(e.getStateChange() == ItemEvent.SELECTED){
        currentSolidState = true;
    }
    else{
        currentSolidState = false;
    }
    ```
	```java
	case RECTANGLE:
		if(currentSolidState){
			graphicsObj.fillRect(Math.min(x1, x2), Math.min(y1, y2), Math.abs(x2 - x1), Math.abs(y2 - y1));
		}
		graphicsObj.drawRect(Math.min(x1, x2), Math.min(y1, y2), Math.abs(x2 - x1), Math.abs(y2 - y1));
	break;
	```
  - Clear options is simply draws a rectangle with the same size and background color of the Applet.
    ```java
    currentShape = new Rectangle(true);
    currentShape.setColor(appletColor);
    currentShape.setFirstPoint(0, 0);
    currentShape.setEndPoint(appletDimension.width, appletDimension.height);
    ```
  - Undo option removes the last drawn shape from the array list.
    ```java
    shapes.remove(indexOfShapes - 1);
    ```

---

## To run the project
1. You need to download the `PaintApplet.java`, `Shape.java`, and `applet.html` files.
   
2. Using your terminal, compile the `PaintApplet.java` file by writing the following command.
	```properties
	javac PaintApplet.java
	```

3. Then run the appletviewer.
   ```properties
   appletviewer applet.html
   ```

4. Enjoy drawing!

---

## Let's have some fun!
[<img src="images/Screenshot 2023-12-29 003935.png"
/>](https://github.com/mennamamdouh/Paint-Desktop-Application/assets/70551007/f292c2fb-3392-44f5-8031-c8cf38893a4a)

---
