package mdsd.model;

import mdsd.controller.IControllableRover;
import simbad.sim.*;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Environment extends EnvironmentDescription {
    //private List<Division> divisions;  // Should divisions be part of Environment or not?
    private List<Area> physicalAreas;  // E.g. rooms
    private List<Area> logicalAreas;   // E.g. the coverage of a Wi-Fi router
    private List<Obstacle> obstacles;  // E.g. walls
    Set<IControllableRover> rovers;
    private List<Mission> missions;

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
        this.rovers = new HashSet<>();
        this.missions = new ArrayList<>();
    }

    /**
     * Creates an Environment object.
     */
    public Environment() {
        super();
        physicalAreas = new ArrayList<>();
        logicalAreas = new ArrayList<>();
        obstacles = new ArrayList<>();
        rovers = new HashSet<>();
        this.missions = new ArrayList<>();
    }

    /**
     * Returns a copy of the physical areas that make up the environment.
     *
     * @return An ArrayList containing all the physical areas.
     */
    public List<Area> getPhysicalAreas() {
        return getAreasCopy(physicalAreas);
    }

    /**
     * Returns a copy of the logical areas that make up the environment.
     *
     * @return An ArrayList containing all the logical areas.
     */
    public List<Area> getLogicalAreas() {
        return getAreasCopy(logicalAreas);
    }

    private List<Area> getAreasCopy(List<Area> areas) {
        List<Area> areasCopy = new ArrayList<>();

        for (Area area : areas) {
            areasCopy.add(new Area(area.getAreaPoints()));
        }

        return areasCopy;
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

    public List<Mission> getMissions() {
        return missions;
    }

    public void addHorizontalBoundary(float p1x, float p1y, float p2y, Color c) {
        addHorizontalObstacle(p1x, p1y, p2y, c);
        new HorizontalBoundary(p1x, p1y, p2y, this, c);
    }

    public void addVerticalBoundary(float p1y, float p1x, float p2x, Color c) {
        addVerticalObstacle(p1y, p1x, p2x, c);
        new VerticalBoundary(p1y, p1x, p2x, this, c);
    }

    public void addHorizontalWall(float p1x, float p1y, float p2y, Color c) {
        addHorizontalObstacle(p1x, p1y, p2y, c);
        new HorizontalWall(p1x, p1y, p2y, this, c);
    }

    public void addVerticalWall(float p1y, float p1x, float p2x, Color c) {
        addVerticalObstacle(p1y, p1x, p2x, c);
        new VerticalWall(p1y, p1x, p2x, this, c);
    }

    private void addHorizontalObstacle(float p1x, float p1y, float p2y, Color c) {
        if (p1y > p2y) {
            obstacles.add(new Obstacle(p1x, p2y, Math.abs(p1y - p2y), false, c));
        } else {
            obstacles.add(new Obstacle(p1x, p1y, Math.abs(p1y - p2y), false, c));
        }
    }

    private void addVerticalObstacle(float p1y, float p1x, float p2x, Color c) {
        if (p1x > p2x) {
            obstacles.add(new Obstacle(p2x, p1y, Math.abs(p1x - p2x), true, c));
        } else {
            obstacles.add(new Obstacle(p1x, p1y, Math.abs(p1x - p2x), true, c));
        }
    }

    public float getWidth() {
        float xMin = 1000;
        float xMax = -1000;
        for (Obstacle o : obstacles) {
            float x1 = o.x;
            float x2 = o.x;
            if (x1 < xMin) {
                xMin = x1;
            }
            if (x2 > xMax) {
                xMax = x2;
            }
        }
        return Math.abs(xMax - xMin);
    }

    public float getHeight() {
        float yMin = 1000;
        float yMax = -1000;
        for (Obstacle o : obstacles) {
            float y1 = o.y;
            if (y1 < yMin) {
                yMin = y1;
            }
            if (y1 > yMax) {
                yMax = y1;
            }
        }
        return Math.abs(yMax - yMin);
    }

    public void addArea(Area area, boolean physical) {
        Color c = Color.GRAY;
        if (physical) {
            physicalAreas.add(area);
        } else {
            logicalAreas.add(area);
        }
        /*for (Shape shape : area.getShapes()) {
            Rectangle2D bounds = shape.getBounds2D();
            float x = (float) bounds.getX();
            float y = (float) bounds.getY();
            float width = (float) bounds.getWidth();
            float height = (float) bounds.getHeight();
            if (physical) {
                addHorizontalWall(x, y, width, c);
                addVerticalWall(x, y, height, c);
            } else {
                addHorizontalBoundary(x, y, width, c);
                addVerticalBoundary(x, y, height, c);
            }
        }*/
    }

    public void addPhysicalArea(Area area) {
        addArea(area, true);
    }

    public void addLogicalArea(Area area) {
        addArea(area, false);
    }
}
