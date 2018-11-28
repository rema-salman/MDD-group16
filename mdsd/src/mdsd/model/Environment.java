package mdsd.model;

import java.awt.Color;

import simbad.sim.AbstractWall;
import simbad.sim.Boundary;
import simbad.sim.EnvironmentDescription;
import simbad.sim.HorizontalBoundary;
import simbad.sim.HorizontalWall;
import simbad.sim.VerticalBoundary;
import simbad.sim.VerticalWall;
import simbad.sim.Wall;

public class Environment extends EnvironmentDescription {
    private Area[] physicalAreas;
    private Area[] logicalAreas;
    private EnvironmentDescription e;
    // int id; //To be used if we have multiple areas and want an identifier.

    public Environment(Area[] physicalAreas, Area[] logicalAreas) {
        this.physicalAreas = physicalAreas;
        this.logicalAreas = logicalAreas;
    }
   
    /**
     * Creates a new Environment object that represents the complex number
     */
    public Environment() {
//TODO: the environment can be equals to an physical or logical area
//         this.e .equals(physicalAreas);
//         this.e .equals(logicalAreas);
    }

    /**
     * Returns the physical areas that make up the environment
     *
     * @return An array containing all the physical areas
     */
    public Area[] getPhysicalAreas() {
        return null;
    }

    /**
     * Returns the logical areas that make up the environment
     *
     * @return An array containing all the logical areas
     */
    public Area[] getLogicalAreas() {
        return null;
    }
}
