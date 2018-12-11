package mdsd;

import javafx.application.Application;
import mdsd.controller.IControllableRover;
import mdsd.controller.MainController;
import mdsd.controller.Robot;
import mdsd.model.Environment;
import mdsd.model.Environment;
import mdsd.model.Hospital;
import mdsd.model.University;
import mdsd.view.GUI;
import project.AbstractSimulatorMonitor;
import project.Point;
import simbad.sim.*;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@SuppressWarnings("unused")
public class Main {

    static MainController mc = MainController.getInstance();

    @SuppressWarnings("unused")
    public static void main(String[] args) {

//       TODO: maybe the user can use an environment if hospitalEnv or uniEnv
        // getUserChoiceOfEnv can be a boolean flag according to a checkbox or something
//        if (mc.getUserChoiceOfEnv) {
        Environment hospitalEnv = new Hospital();
        Set<IControllableRover> rovers = hospitalEnv.getRovers();
        mc.addRovers(rovers);

        Set<Robot> robots = new HashSet<>();
        for (IControllableRover rover : rovers) {
            robots.add((Robot) rover);
        }
        AbstractSimulatorMonitor<Robot> controller = new SimulatorMonitor(
                robots, (EnvironmentDescription) hospitalEnv);

//} else {
        // Environment uniEnv = new University();
//        Set<Robot> uniEnvRobots = uniEnv.getRovers(); 
//        
//        List<IControllableRover> rovers = new ArrayList<>(uniEnvRobots);
//        mc.addRovers(rovers);
//
//        AbstractSimulatorMonitor controller = new SimulatorMonitor(uniEnvRobots,  (EnvironmentDescription)uniEnv);
//
//        Application.launch(GUI.class);
//    }}
        Application.launch(GUI.class);
    }

}
