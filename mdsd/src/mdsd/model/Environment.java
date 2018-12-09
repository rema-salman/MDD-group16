package mdsd.model;

import java.util.Set;

import javax.vecmath.Point2f;

import mdsd.controller.Robot;

public interface Environment {

    void setPositions(Set<Point2f> positions);
    public Set<Point2f> getPositions();
    public Set<Area> getAreas();
    public void addArea(Area a);
    public Set<Robot> getRovers();
    public Mission[] getMissions();
    
}
