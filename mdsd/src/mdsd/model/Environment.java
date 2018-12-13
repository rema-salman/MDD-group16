package mdsd.model;

import mdsd.controller.IControllableRover;
import simbad.sim.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.awt.Color;

public class Environment extends EnvironmentDescription {
    //private List<Division> divisions;  // Should divisions be part of Environment or not?
    private List<Area> physicalAreas;  // E.g. rooms
    private List<Area> logicalAreas;   // E.g. the coverage of a Wi-Fi router
    private List<Obstacle> obstacles;  // E.g. walls
    protected Set<IControllableRover> rovers;
    protected Mission[] missions;

    /**
     * Creates an Environment object from pre-defined areas and obstacles.
     *
     * @param physicalAreas Describes e.g. rooms.
     * @param logicalAreas  Describes e.g. the coverage of a Wi-Fi router.
     * @param obstacles     Describes e.g. walls.
     */
    public Environment(List<Area> physicalAreas, List<Area> logicalAreas,
                       List<Obstacle> obstacles) {
        super();
        this.physicalAreas = physicalAreas;
        this.logicalAreas = logicalAreas;
        this.obstacles = obstacles;
        this.rovers = new HashSet<IControllableRover>();
        this.missions = new Mission[4];  // Why 4? How does this work?
    }

    /**
     * Creates an Environment object.
     */
    public Environment() {
        super();
        physicalAreas = new ArrayList<>();
        logicalAreas  = new ArrayList<>();
        obstacles     = new ArrayList<>();
        rovers        = new HashSet<IControllableRover>();
        this.missions = new Mission[4];  // Why 4? How does this work?
    }

    /**
     * Returns a copy of the physical areas that make up the environment.
     *
     * @return An ArrayList containing all the physical areas.
     */
    public List<Area> getPhysicalAreas() {
        List<Area> physicalAreasCopy = new ArrayList<>();

        for (Area area : physicalAreas) {
            physicalAreasCopy.add(new Area(area.getShapes()));
        }

        return physicalAreasCopy;
    }

    /**
     * Returns a copy of the logical areas that make up the environment.
     *
     * @return An ArrayList containing all the logical areas.
     */
    public List<Area> getLogicalAreas() {
        List<Area> logicalAreasCopy = new ArrayList<>();

        for (Area area : logicalAreas) {
            logicalAreasCopy.add(new Area(area.getShapes()));
        }

        return logicalAreasCopy;
    }

    /**
     * Returns a copy of the obstacles in the environment.
     *
     * @return An ArrayList containing all the obstacles.
     */
    public List<Obstacle> getObstacles() {
        List<Obstacle> obstaclesCopy = new ArrayList<>();

        for (Obstacle obstacle : obstacles) {
            obstaclesCopy.add(new Obstacle(obstacle));
        }

        return obstaclesCopy;
    }

    public List<Area> getAreas() {
        List<Area> areas = new ArrayList<>();
        areas.addAll(physicalAreas);
        areas.addAll(logicalAreas);
        return areas;
    }

    public Set<IControllableRover> getRovers() {
        return rovers;
    }

    public Mission[] getMissions() {
        return missions;
    }

    //public void addBoundary(float p1, float p2, float xy2,
    public void addBoundary(float p1, float p2, float len,
            Color c, boolean horiz) {
        if (horiz) {
            //new HorizontalBoundary(p1, p2, xy2,
            new HorizontalBoundary(p1, p2, len,
                    this, c);
        } else {
            //new VerticalBoundary(p1, p2, xy2,
            new VerticalBoundary(p1, p2, len,
                    this, c);
        }
    }

    //public void addWall(float p1, float p2, float xy2, Color c, boolean horiz) {
    public void addWall(float p1, float p2, float len, Color c, boolean horiz) {
        if (horiz) {
            //new HorizontalWall(p1, p2, xy2, (EnvironmentDescription)this, c);
            new HorizontalWall(p1, p2, len, this, c);
        } else {
            //new VerticalWall(p1, p2, xy2, (EnvironmentDescription) this, c);
            new VerticalWall(p1, p2, len, this, c);
        }

        //obstacles.add(new Obstacle(p1, p2, xy2, horiz));
        obstacles.add(new Obstacle(p1, p2, len, horiz));
    }

    public void addPhysicalArea(Area area) {
        physicalAreas.add(area);
    }

    public void addLogicalArea(Area area) {
        logicalAreas.add(area);
    }
}
