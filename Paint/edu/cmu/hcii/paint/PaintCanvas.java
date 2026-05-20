package edu.cmu.hcii.paint;
import javax.swing.*;
import java.awt.*;
import java.util.*;

public class PaintCanvas extends JPanel {

    Vector history;
    
    Vector paintObjects;

    private PaintObject temporaryObject;
    private PaintObject hoveringObject;
    private static final int CANVAS_PADDING = 50;

    public PaintCanvas(int initialWidth, int initialHeight) {
        
        setPreferredSize(new Dimension(initialWidth, initialHeight));
        setBackground(Color.white);

        paintObjects = new Vector();
        
        history = new Vector();
        
    }
    
    public void paintComponent(Graphics g) {
    super.paintComponent(g);
    ((Graphics2D) g).addRenderingHints(
			new java.awt.RenderingHints(
				java.awt.RenderingHints.KEY_ANTIALIASING,
				java.awt.RenderingHints.VALUE_ANTIALIAS_ON));
        
        Rectangle clipBounds = g.getClipBounds();
    g.setColor(getBackground());
    if(clipBounds != null)
      g.fillRect((int)clipBounds.getX(), (int)clipBounds.getY(),
                  (int)clipBounds.getWidth(), (int)clipBounds.getHeight());
    else
      g.fillRect(0, 0, getWidth(), getHeight());

        Iterator paintObjectIterator = paintObjects.iterator();
        while(paintObjectIterator.hasNext())
			try {
		        ((PaintObject)paintObjectIterator.next()).paint((Graphics2D)g); 
			} catch(Exception e) { 
				System.err.println("The graphics context isn't a Graphics2D. No anti-aliasing!");
			}
        
        if(temporaryObject != null) temporaryObject.paint((Graphics2D)g);
        
		if(hoveringObject != null) {
			
			Rectangle rect = hoveringObject.getBoundingBox();
			g.setColor(Color.black);
			g.drawRect((int)rect.getX() - 1, (int)rect.getY() - 1, (int)rect.getWidth() + 2, (int)rect.getHeight() + 2);
			hoveringObject.paint((Graphics2D)g);
			
		}
        
    }
    
    public int sizeOfHistory() { return history.size(); }
    
    public void setTemporaryObject(PaintObject temporaryObject) {
        
        this.temporaryObject = temporaryObject;
        repaint();
        
    }
    
    public void setHoveringObject(PaintObject hoveringObject) {
    	
    	this.hoveringObject = hoveringObject;
    	repaint();
    	
    }
    
    public void addPaintObject(PaintObject newObject) {
        
        history.addElement(new Vector(paintObjects));
        paintObjects.addElement(newObject);

    Rectangle bounds = newObject.getBoundingBox();
    Dimension current = getPreferredSize();
    int requiredWidth = Math.max(current.width, bounds.x + bounds.width + CANVAS_PADDING);
    int requiredHeight = Math.max(current.height, bounds.y + bounds.height + CANVAS_PADDING);
    if(requiredWidth != current.width || requiredHeight != current.height) {
      setPreferredSize(new Dimension(requiredWidth, requiredHeight));
      revalidate();
    }
        repaint();
        
    }
    
    public void clear() {
        
        history.addElement(new Vector(paintObjects));
        paintObjects.removeAllElements();
        repaint();

    }

    public void undo() { 
        
		if(history.size() == 0)
			return;

        paintObjects = (Vector)history.lastElement();
        history.removeElement(history.lastElement());
		repaint();

    }


}
