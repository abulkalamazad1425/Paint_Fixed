package edu.cmu.hcii.paint;

import java.awt.*;

public class LinePaint extends PaintObject {

    private Point[] points;

    public void define(Point[] points) {
        this.points = points;
    }

    public double getStartX() {
        return points[0].getX();
    }

    public double getStartY() {
        return points[0].getY();
    }

    public double getEndX() {
        return points[points.length - 1].getX();
    }

    public double getEndY() {
        return points[points.length - 1].getY();
    }

    public Rectangle getBoundingBox() {
        if(points == null || points.length == 0)
            return new Rectangle(0, 0, 0, 0);

        int x1 = (int)getStartX();
        int y1 = (int)getStartY();
        int x2 = (int)getEndX();
        int y2 = (int)getEndY();

        int minX = Math.min(x1, x2) - thickness / 2;
        int minY = Math.min(y1, y2) - thickness / 2;
        int maxX = Math.max(x1, x2) + thickness / 2;
        int maxY = Math.max(y1, y2) + thickness / 2;

        return new Rectangle(minX, minY, maxX - minX, maxY - minY);
    }

    public void paint(Graphics2D g) {
        if(points == null || points.length == 0)
            return;

        Stroke oldStroke = g.getStroke();
        g.setStroke(new BasicStroke(thickness));
        g.setColor(color);

        if(points.length == 1 || (getStartX() == getEndX() && getStartY() == getEndY())) {
            int x = (int)getStartX();
            int y = (int)getStartY();
            int size = Math.max(1, thickness);
            g.fillOval(x - size / 2, y - size / 2, size, size);
        } else {
            g.drawLine((int)getStartX(), (int)getStartY(), (int)getEndX(), (int)getEndY());
        }

        g.setStroke(oldStroke);
    }
}
