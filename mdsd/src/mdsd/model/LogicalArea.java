package mdsd.model;

import java.awt.Shape;
import java.util.Set;

import javax.vecmath.Point2f;

public class LogicalArea extends Area {

    
    public LogicalArea(Set<Point2f> shapes) {
        super(shapes);
    }
    
    public LogicalArea(Shape[] shapes, Shape[] antiShapes) {
        super(shapes, antiShapes);
        // TODO Auto-generated constructor stub
    }

}
