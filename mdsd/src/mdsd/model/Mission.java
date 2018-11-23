package mdsd.model;

import java.awt.*;

public class Mission {
    private final Point[] points;
    private int currentHeading;

    public Mission(Point[] points) {
        if (points == null) {
            throw new IllegalArgumentException("Points can't be null");
        } else if (points.length == 0) {
            throw new IllegalArgumentException("Points can't be empty");
        }
        this.points = points;
        currentHeading = 0;
    }

    public Point getNextPoint() {
        if (currentHeading >= points.length) {
            return null;
        }
        return points[currentHeading++];
    }

    public int getCurrentHeading() {
        return currentHeading;
    }
}
