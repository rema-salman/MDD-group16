package mdsd;

import mdsd.controller.Robot;
import mdsd.model.Environment;
import project.AbstractSimulatorMonitor;
import project.Point;
import simbad.sim.*;

import java.awt.*;
import java.util.HashSet;
import java.util.Set;

import mdsd.controller.Robot;

@SuppressWarnings("unused")
public class Main {

 static Environment e = new Environment();

    @SuppressWarnings("unused")
    public static void main(String[] args) {

        Set<Robot> robots = new HashSet<>();
        Robot robot1 = new Robot(new Point(0, 0), "Robot 1");
        Robot robot2 = new Robot(new Point(1, 3), "Robot 2");

        robots.add(robot1);
        robots.add(robot2);
        
        // adding the robots to the environment 
        e.add(robot1);
        e.add(robot2);
        
        AbstractSimulatorMonitor controller = new SimulatorMonitor(robots, e.getEnviroment());

    }

}
