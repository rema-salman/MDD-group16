package mdsd.model;

import java.awt.Point;
import java.awt.Polygon;
import java.awt.Shape;
import java.util.Set;

import javax.vecmath.Point2f;

public class Area extends Polygon {
    /**
     * These shapes define the area, upon calling contains to check if a point is
     * inside the area these shapes define the area as a whole. If the point is
     * inside any of these shapes then it is considered to be inside of the area,
     * unless it is also inside one of the antiShapes
     */
    private Shape[] shapes;
    /**
     * These shapes are subtracted from the area for the purposes of collision
     * detection, if a point is inside any of the normal shapes but also inside an
     * anti shape then it is considered to not be inside of the area.
     */
    private Shape[] antiShapes;
    Set<Point2f> areaShapes;
    
    public Area(Shape[] shapes, Shape[] antiShapes) {
        this.shapes = shapes;
        this.antiShapes = antiShapes;
    }

    public Area(Set<Point2f> areaShapes) {
        this.setShapes(areaShapes);
        for(Point2f p : areaShapes) {
            this.addPoint((int)(p.getX()), (int)(p.getY()));
        }     
    }
    
    public void setShapes(Set<Point2f> areaShapes) {
        this.areaShapes = areaShapes;
    }

    /**
     * Checks if a given point, p, is inside the area. This is true if the point is
     * NOT inside any of the anti shapes, but is inside one of the normal shapes.
     *
     * @param p The point which is being investigated.
     * @return True if the point is inside the area.
     */
    public boolean contains(Point2f p) {
        // if inside any antiShape: return false
        // if inside any shape: return true
        // return false
        for (Shape shape : shapes) {
            if (shape.contains(p.getX(), p.getY())) {
                return true;
            }
        }
        return false;
    }

    // TODO: Check if actually deep copy
    public Shape[] getShapes() {
        return shapes.clone();
    }

    // TODO: Check if actually deep copy
    public Shape[] getAntiShapes() {
        return antiShapes.clone();
    }
}
