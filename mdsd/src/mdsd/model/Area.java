package mdsd.model;

import java.awt.Polygon;
import java.util.Set;

import javax.vecmath.Point2f;

@SuppressWarnings("serial")
public class Area extends Polygon {
    /**
     * This set of points describe the boundaries of the Area.
     */
    Set<Point2f> areaShapes;
    
    public Area(Set<Point2f> areaShapes) {
        this.setShapes(areaShapes);
        for(Point2f p : areaShapes) {
            this.addPoint((int)(p.getX()), (int)(p.getY()));
        }     
    }

    public void setShapes(Set<Point2f> areaShapes) {
        this.areaShapes = areaShapes;
    }

    public Set<Point2f> getShapes() {
        return areaShapes;
    }

    /**
     * Checks if a given point, p, is inside the area.
     *
     * @param p The point which is being investigated.
     * @return True if the point is inside the area.
     */
    public boolean contains(Point2f p) {
        return super.contains(p.getX(), p.getY());
    }
}
