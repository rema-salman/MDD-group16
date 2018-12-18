package mdsd.model;

import mdsd.controller.IControllableRover;

import javax.vecmath.Point2f;
import java.awt.*;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentLinkedQueue;

@SuppressWarnings("serial")
public class Area extends Polygon {
    /**
     * These shapes define the area, upon calling contains to check if a point is
     * inside the area these shapes define the area as a whole. If the point is
     * inside any of these shapes then it is considered to be inside of the area,
     * unless it is also inside one of the antiShapes
     */
    protected List<Shape> shapes;

    /**
     * These shapes are subtracted from the area for the purposes of collision
     * detection, if a point is inside any of the normal shapes but also inside an
     * anti shape then it is considered to not be inside of the area.
     */
    protected List<Shape> antiShapes;
    Set<Point2f> areaShapes;

    private int reward;
    private static int idCount = 0;
    private final int id;

    /**
     * A thread safe FIFO queue for the rovers when they try to enter the area
     */
    private final ConcurrentLinkedQueue<IControllableRover> queueToEnter;

    public Area(List<Shape> shapes, List<Shape> antiShapes, int reward) {
        this.shapes = shapes;
        this.antiShapes = antiShapes;
        this.reward = reward;
        queueToEnter = new ConcurrentLinkedQueue<>();
        synchronized (this) {
            id = idCount++;
        }
    }

    public Area(List<Shape> shapes, List<Shape> antiShapes) {
        this(shapes, antiShapes, 0);
    }

    public Area(Set<Point2f> areaShapes, int reward) {
        this.setShapes(areaShapes);
        for (Point2f p : areaShapes) {
            this.addPoint((int) (p.getX()), (int) (p.getY()));
        }
        this.reward = reward;
        this.shapes = new ArrayList<>();
        queueToEnter = new ConcurrentLinkedQueue<>();
        synchronized (this) {
            id = idCount++;
        }
    }

    public Area(Set<Point2f> areaShapes) {
        this(areaShapes, 0);
    }

    public void setShapes(Set<Point2f> areaShapes) {
        this.areaShapes = areaShapes;
    }

    /**
     * Checks if a given point is inside the area. This is true if the point is
     * inside the area but NOT inside any of the anti shapes.
     *
     * @param p The point which is being investigated.
     * @return True if the point is inside the area.
     */
    public boolean contains(Point2f p) {
        /**  float x = p.getX();
         float y = p.getY();
         for (Shape shape : shapes) {
         if (shape.contains(x, y)) {
         for (Shape antiShape : antiShapes) {					--This is all unused since the areas suddenly use a set of points.
         if (antiShape.contains(x, y)) {
         return false;
         }
         }
         return true;
         }
         }*/

        return super.contains(new Point2D.Float(p.x, p.y));
    }

    public List<Shape> getShapes() {
        return shapes;
    }

    public List<Shape> getAntiShapes() {
        return antiShapes;
    }

    public int getReward() {
        return reward;
    }

    /**
     * Enters the current area
     *
     * @param rover the rover to enter
     */
    public void enter(IControllableRover rover) {
        queueToEnter.add(rover);
    }

    /**
     * Leaves the current area
     *
     * @param rover the rover to leave
     */
    public void leave(IControllableRover rover) {
        queueToEnter.remove(rover);
    }

    /**
     * Checks if the given rover can enter the area
     *
     * @param rover the rover to check
     * @return true if no other rover is in the area
     */
    public synchronized boolean canEnter(IControllableRover rover) {
        return rover.equals(queueToEnter.peek());
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Area) {
            return ((Area) obj).id == this.id;
        }
        return false;
    }
}
