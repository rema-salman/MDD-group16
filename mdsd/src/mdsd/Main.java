package mdsd;

import javafx.application.Application;
import mdsd.controller.IControllableRover;
import mdsd.controller.MainController;
import mdsd.controller.Robot;
import mdsd.model.Environment;
import mdsd.model.Obstacle;
import mdsd.view.GUI;
import project.AbstractSimulatorMonitor;
import project.Point;
import simbad.sim.*;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import mdsd.controller.Robot;

@SuppressWarnings("unused")
public class Main {

    static Environment e = new Environment();
    static EnvironmentDescription ed;
    static MainController mc = MainController.getInstance(); //new MainController();

    @SuppressWarnings("unused")
    public static void main(String[] args) {

        Set<Robot> robots = new HashSet<>();
        Robot robot1 = new Robot(new Point(0, 0), "Robot 1");
        Robot robot2 = new Robot(new Point(1, 3), "Robot 2");

        robots.add(robot1);
        robots.add(robot2);

        ed = getEnvironment();

        // adding the robots to the environment 
        ed.add(robot1);
        ed.add(robot2);

        List<IControllableRover> rovers = new ArrayList<>(robots);
        mc.addRovers(rovers);

        AbstractSimulatorMonitor controller = new SimulatorMonitor(robots, ed);

        Application.launch(GUI.class);
    }

    /**
     * Returns a simbad description of the environment
     *
     * @return object of the designed environment  // Rewrite to something that makes sense?
     */
    public static EnvironmentDescription getEnvironment() {
        Color color = Color.GRAY;
        Environment env = mc.getEnvironment();
        EnvironmentDescription envDesc = new EnvironmentDescription();

        for (Obstacle ob : env.getObstacles()) {
            if (ob.horizontal) {
                Wall wall = new HorizontalWall(ob.x, ob.y, ob.length,
                        envDesc, color);
            } else {
                Wall wall = new VerticalWall(ob.x, ob.y, ob.length,
                        envDesc, color);
            }
        }


        // horizontal (yPos, xStart,xEnd)
        // vertical (xPos, yStart, yEnd)
        Boundary w1 = new HorizontalBoundary(-6.0f, -6.0f, 6.0f, envDesc, color);
        Boundary w2 = new HorizontalBoundary(6.0f, -6.0f, 6.0f, envDesc, color);
        Boundary w3 = new VerticalBoundary(6.0f, -6.0f, -4.0f, envDesc, color);
        Boundary w4 = new VerticalBoundary(6.0f, -2.0f, 2.0f, envDesc, color);
        Boundary w5 = new VerticalBoundary(6.0f, 4.0f, 6.0f, envDesc, color);
        Boundary w6 = new VerticalBoundary(-6.0f, -6.0f, -4.0f, envDesc, color);
        Boundary w7 = new VerticalBoundary(-6.0f, -2.0f, 2.0f, envDesc, color);
        Boundary w8 = new VerticalBoundary(-6.0f, 4.0f, 6.0f, envDesc, color);

        Wall roomWall1 = new HorizontalWall(0f, -2f, 2f, envDesc, color);
        Wall roomWall2 = new VerticalWall(0f, -6f, -4f, envDesc, color);
        Wall roomWall3 = new VerticalWall(0f, -2f, 2f, envDesc, color);
        Wall roomWall4 = new VerticalWall(0f, 4f, 6f, envDesc, color);
        Wall roomWall5 = new HorizontalWall(0f, -6f, -4f, envDesc, color);
        Wall roomWall6 = new HorizontalWall(0f, 4f, 6f, envDesc, color);

        return envDesc;
    }

}
