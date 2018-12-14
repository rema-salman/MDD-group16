package mdsd.model;

import javax.vecmath.Point2f;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@SuppressWarnings("serial")
public class Area extends Polygon {
    /**
     * This set of points describe the boundaries of the Area.
     */
    protected List<Shape> shapes;

    Set<Point2f> areaShapes;

    private int reward;

    public Area(List<Shape> shapes, int reward) {
        this.shapes = shapes;
        this.reward = reward;
    }

    public Area(List<Shape> shapes) {
        this(shapes, 0);
    }

    public Area(Set<Point2f> areaShapes, int reward) {
        this.setShapes(areaShapes);
        for(Point2f p : areaShapes) {
            this.addPoint((int)(p.getX()), (int)(p.getY()));
        }
        this.reward = reward;
        this.shapes = new ArrayList<>();
    }

    public Area(Set<Point2f> areaShapes) {
        this(areaShapes, 0);
    }

    public void setShapes(Set<Point2f> areaShapes) {
        this.areaShapes = areaShapes;
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

    public Set<Point2f> getShapes() {
        return areaShapes;
    }

    public int getReward() {
        return reward;
    }
}
