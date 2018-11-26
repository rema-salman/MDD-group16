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
     * Returns the designed environment
     *
     * @return object of the designed environment
     */

    public EnvironmentDescription getEnviroment() {
        EnvironmentDescription env = new EnvironmentDescription();
        Color color = Color.GRAY;

        // horizontal (yPos, xStart,xEnd)
        // vertical (xPos, yStart, yEnd)
        Boundary w1 = new HorizontalBoundary(-6.0f, -6.0f, 6.0f, env, color);
        Boundary w2 = new HorizontalBoundary(6.0f, -6.0f, 6.0f, env, color);
        Boundary w3 = new VerticalBoundary(6.0f, -6.0f, -4.0f, env, color);
        Boundary w4 = new VerticalBoundary(6.0f, -2.0f, 2.0f, env, color);
        Boundary w5 = new VerticalBoundary(6.0f, 4.0f, 6.0f, env, color);
        Boundary w6 = new VerticalBoundary(-6.0f, -6.0f, -4.0f, env, color);
        Boundary w7 = new VerticalBoundary(-6.0f, -2.0f, 2.0f, env, color);
        Boundary w8 = new VerticalBoundary(-6.0f, 4.0f, 6.0f, env, color);
        add(w8);

        Wall roomWall1 = new HorizontalWall(0f, -2f, 2f, env, color);
        Wall roomWall2 = new VerticalWall(0f, -6f, -4f, env, color);
        Wall roomWall3 = new VerticalWall(0f, -2f, 2f, env, color);
        Wall roomWall4 = new VerticalWall(0f, 4f, 6f, env, color);
        Wall roomWall5 = new HorizontalWall(0f, -6f, -4f, env, color);
        Wall roomWall6 = new HorizontalWall(0f, 4f, 6f, env, color);

        return env;
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
