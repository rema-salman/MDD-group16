package mdsd;

import javafx.application.Application;
import mdsd.controller.MainController;
import mdsd.controller.Robot;
import mdsd.model.Environment;
import mdsd.view.GUI;
import project.AbstractSimulatorMonitor;
import project.Point;
import simbad.sim.*;

import java.awt.Color;
import java.util.HashSet;
import java.util.Set;

@SuppressWarnings("unused")
public class Main {

    static Environment e = new Environment();
    static EnvironmentDescription ed;

    @SuppressWarnings("unused")
    public static void main(String[] args) {

        /*Set<Robot> robots = new HashSet<>();
        Robot robot1 = new Robot(new Point(0, 0), "Robot 1");
        Robot robot2 = new Robot(new Point(1, 3), "Robot 2");

        robots.add(robot1);
        robots.add(robot2);

        ed = getEnvironment();

        // adding the robots to the environment 
        ed.add(robot1);
        ed.add(robot2);

        AbstractSimulatorMonitor controller = new SimulatorMonitor(robots, ed);*/

        MainController mainController = new MainController();
        GUI.init(mainController);

        try {
            Application.launch(GUI.class);
        } catch (Exception e1) {
            e1.printStackTrace();
        }
    }

    /**
     * Returns the designed environment
     *
     * @return object of the designed environment
     */
    public static EnvironmentDescription getEnvironment() {
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

        Wall roomWall1 = new HorizontalWall(0f, -2f, 2f, env, color);
        Wall roomWall2 = new VerticalWall(0f, -6f, -4f, env, color);
        Wall roomWall3 = new VerticalWall(0f, -2f, 2f, env, color);
        Wall roomWall4 = new VerticalWall(0f, 4f, 6f, env, color);
        Wall roomWall5 = new HorizontalWall(0f, -6f, -4f, env, color);
        Wall roomWall6 = new HorizontalWall(0f, 4f, 6f, env, color);

        return env;
    }

}
