package mdsd.model;

import java.awt.geom.Line2D;

public class Environment {
    private Area[] physicalAreas;  // E.g. rooms
    private Area[] logicalAreas;   // E.g. the coverage of a Wi-Fi router
    private Line2D.Double[] obstacles;    // E.g. walls

    /**
     * Creates an Environment object from pre-defined areas and obstacles.
     *
     * @param physicalAreas Describes e.g. rooms.
     * @param logicalAreas Describes e.g. the coverage of a Wi-Fi router.
     * @param obstacles Describes e.g. walls.
     */
    public Environment(Area[] physicalAreas, Area[] logicalAreas, Line2D.Double[] obstacles) {
        this.physicalAreas = physicalAreas;
        this.logicalAreas  = logicalAreas;
        this.obstacles     = obstacles;
    }

    /**
     * Creates an empty Environment object.
     */
    public Environment() { }

    /**
     * Returns a copy of the physical areas that make up the environment.
     *
     * @return An array containing all the physical areas.
     */
    public Area[] getPhysicalAreas() {
        int length = physicalAreas.length;
        Area[] physicalAreasCopy = new Area[length];

        for (int i = 0; i < length; ++i) {
            physicalAreasCopy[i] = new Area(
                physicalAreas[i].getShapes().clone(),
                physicalAreas[i].getAntiShapes().clone()
            );
        }

        return physicalAreasCopy;
    }

    /**
     * Returns a copy of the logical areas that make up the environment.
     *
     * @return An array containing all the logical areas.
     */
    public Area[] getLogicalAreas() {
        int length = logicalAreas.length;
        Area[] logicalAreasCopy = new Area[length];

        for (int i = 0; i < length; ++i) {
            logicalAreasCopy[i] = new Area(logicalAreas[i].getShapes(),
                                           logicalAreas[i].getAntiShapes());
        }

        return logicalAreasCopy;
    }

    /**
     * Returns a copy of the obstacles in the environment.
     *
     * @return An array containing all the obstacles.
     */
    public Line2D.Double[] getObstacles() {
        int length = obstacles.length;
        Line2D.Double[] obstaclesCopy = new Line2D.Double[length];

        for (int i = 0; i < length; ++i) {
            obstaclesCopy[i] = new Line2D.Double(
                obstacles[i].getX1(), obstacles[i].getY1(),
                obstacles[i].getX2(), obstacles[i].getY2()
            );
        }

        return obstaclesCopy;
    }

}
