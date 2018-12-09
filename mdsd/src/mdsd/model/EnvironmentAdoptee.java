package mdsd.model;

import java.util.HashSet;
import java.util.Set;
import java.awt.Color;

import javax.vecmath.Point2f;

import mdsd.controller.Robot;

public class EnvironmentAdoptee extends EnvironmentAdapter implements Environment {
    private Area[] physicalAreas; // E.g. rooms
    private Area[] logicalAreas; // E.g. the coverage of a Wi-Fi router
    private Obstacle[] obstacles; // E.g. walls

    protected Set<Point2f> positions;
    private Set<Area> areas;
    protected Set<Robot> rovers = new HashSet<>();
    protected Mission[] missions = new Mission[4];

    /**
     * Creates an Environment object from pre-defined areas and obstacles.
     *
     * @param physicalAreas Describes e.g. rooms.
     * @param logicalAreas  Describes e.g. the coverage of a Wi-Fi router.
     * @param obstacles     Describes e.g. walls.
     */
    public EnvironmentAdoptee(Area[] physicalAreas, Area[] logicalAreas, Obstacle[] obstacles) {
        this.physicalAreas = physicalAreas;
        this.logicalAreas = logicalAreas;
        this.obstacles = obstacles;
    }

    /**
     * Creates an Environment object.
     */
    public EnvironmentAdoptee() {
        super();
        areas = new HashSet<>();
    }

    /**
     * Returns a copy of the physical areas that make up the environment.
     *
     * @return An array containing all the physical areas.
     */
    public Area[] getPhysicalAreas() {
        int length = physicalAreas.length;
        Area[] physicalAreasCopy = new Area[length];

        for (int i = 0; i < length; ++i) {
            physicalAreasCopy[i] = new Area(physicalAreas[i].getShapes().clone(),
                    physicalAreas[i].getAntiShapes().clone());
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
            logicalAreasCopy[i] = new Area(logicalAreas[i].getShapes(), logicalAreas[i].getAntiShapes());
        }

        return logicalAreasCopy;
    }

    /**
     * Returns a copy of the obstacles in the environment.
     *
     * @return An array containing all the obstacles.
     */
    public Obstacle[] getObstacles() {
        int length = obstacles.length;
        Obstacle[] obstaclesCopy = new Obstacle[length];

        for (int i = 0; i < length; ++i) {
            obstaclesCopy[i] = new Obstacle(obstacles[i]);
        }

        return obstaclesCopy;
    }

    @Override
    public void setPositions(Set<Point2f> positions) {
        this.positions = positions;
    }

    @Override
    public Set<Point2f> getPositions() {
        return positions;
    }

    @Override
    public Set<Area> getAreas() {
        return areas;
    }

    @Override
    public void addArea(Area a) {
        areas.add(a);
    }

    @Override
    public Set<Robot> getRovers() {
        return rovers;
    }

    @Override
    public Mission[] getMissions() {
        return missions;
    }
   
    public void addBoundry(float p1, float p2, float length, EnvironmentAdapter e, Color c, boolean horizontal) {
        super.addBoundry(p1, p2, length, e, c, horizontal);
    }
    
    public void addWall(float p1, float p2, float length, EnvironmentAdapter e, Color c, boolean horizontal) {
        super.addWall(p1, p2, length, e, c, horizontal);
    }

}
