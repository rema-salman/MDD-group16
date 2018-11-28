package mdsd.model;

import javax.vecmath.Point2f;

public class Mission {
    private final Point2f[] points;
    private int currentHeading;

    /**
     * Creates a new mission from an array of points
     *
     * @param points the points to be visited
     * @throws IllegalArgumentException if the argument is null or empty
     */
    public Mission(Point2f[] points) {
        if (points == null) {
            throw new IllegalArgumentException("Points can't be null");
        } else if (points.length == 0) {
            throw new IllegalArgumentException("Points can't be empty");
        }
        this.points = points;
        currentHeading = 0;
    }

    /**
     * Returns the next point in the mission. If the mission is done it returns null
     *
     * @return the next Point to visit, or null if end of list
     */
    public Point2f getNextPoint() {
        if (currentHeading >= points.length) {
            return null;
        }
        return points[currentHeading++];
    }

    /**
     * Returns the current point
     *
     * @return the point being visited
     */
    public Point2f getCurrentPoint() {
        return points[currentHeading];
    }
}
