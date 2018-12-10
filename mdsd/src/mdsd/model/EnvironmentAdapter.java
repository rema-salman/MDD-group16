package mdsd.model;

import java.awt.Color;
import java.util.HashSet;
import java.util.Set;

import simbad.sim.AbstractWall;
import simbad.sim.Boundary;
import simbad.sim.EnvironmentDescription;
import simbad.sim.HorizontalBoundary;
import simbad.sim.HorizontalWall;
import simbad.sim.VerticalBoundary;
import simbad.sim.VerticalWall;

public class EnvironmentAdapter extends EnvironmentDescription {
    private Set<Boundary> boundries;
    private Set<AbstractWall> walls;

    public EnvironmentAdapter() {
        super();
        boundries = new HashSet<>();
        walls = new HashSet<>();
    }

    public void addBoundry(float p1, float p2, float length, EnvironmentAdapter e, Color c, boolean horizontal) {
        if (horizontal) {

            boundries.add(new HorizontalBoundary(p1, p2, length, e, c));
        } else {
            boundries.add(new VerticalBoundary(p1, p2, length, e, c));
        }
    }

    public void addWall(float p1, float p2, float length, EnvironmentAdapter e, Color c, boolean horizontal) {
        if (horizontal) {
            walls.add(new HorizontalWall(p1, p2, length, e, c));
        } else {
            walls.add(new VerticalWall(p1, p2, length, e, c));
        }

    }

}
