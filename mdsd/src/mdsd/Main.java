package mdsd;

import javafx.application.Application;
import mdsd.controller.*;
import mdsd.controller.GraphOfPoints.Edge;
import mdsd.controller.GraphOfPoints.Node;
import mdsd.model.Area;
import mdsd.model.Hospital;
import mdsd.model.Mission;
import mdsd.view.GUI;
import project.AbstractSimulatorMonitor;

import javax.vecmath.Point2f;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@SuppressWarnings("unused")
public class Main {

    @SuppressWarnings("unused")
    public static void main(String[] args) {
        MainController mc = MainController.getInstance();

        //       TODO: maybe the user can use an environment if hospitalEnv or uniEnv
        // getUserChoiceOfEnv can be a boolean flag according to a checkbox or something
        //        if (mc.getUserChoiceOfEnv) {
        Hospital hospitalEnv = new Hospital();
        Set<IControllableRover> rovers = hospitalEnv.getRovers();
        mc.addRovers(rovers);
        mc.setEnvironment(hospitalEnv);

        Set<Robot> robots = new HashSet<>();
        for (IControllableRover rover : rovers) {
            robots.add((Robot) rover);
        }
        AbstractSimulatorMonitor<Robot> controller = new SimulatorMonitor(robots,
                hospitalEnv);

        List<List<Area>> areasA = new ArrayList<>();
        List<Area> consultingRoomArea = new ArrayList<>();
        consultingRoomArea.add((hospitalEnv).getConsultingRoom());
        areasA.add(consultingRoomArea);
        areasA.add(hospitalEnv.getSurgeryRooms());

        List<List<Area>> areasB = new ArrayList<>();
        areasB.add(hospitalEnv.getWifiZones());
        areasB.add(hospitalEnv.getEatingAreas());

        Procedure procedureA = new Procedure(rovers, areasA);//, rewards);
        Procedure procedureB = new Procedure(rovers, areasB);//, rewards);
        ScoreCalculator sc = new RewardSystem(rovers, procedureA, procedureB,
                hospitalEnv.getPhysicalAreas(), hospitalEnv.getLogicalAreas());

        mc.setScoreCalculator(sc);

        Thread scoreCalcThread = new Thread((Runnable) sc);
        scoreCalcThread.start();

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

        //Temporary implementation of the graph used for navigation.
        Node nwCorner = new Node(new Point2f(-5, 5));
        Node neCorner = new Node(new Point2f(-5, -5));
        Node seCorner = new Node(new Point2f(5, -5));
        Node swCorner = new Node(new Point2f(5, 5));

        Node nwIntersect = new Node(new Point2f(-4, 4));
        Node neIntersect = new Node(new Point2f(-4, -4));
        Node seIntersect = new Node(new Point2f(4, -4));
        Node swIntersect = new Node(new Point2f(4, 4));

        Node middle = new Node(new Point2f(0, 0));

        nwCorner.edges.add(new Edge(1.4f, nwIntersect));
        nwIntersect.edges.add(new Edge(1.4f, nwCorner));
        neCorner.edges.add(new Edge(1.4f, neIntersect));
        neIntersect.edges.add(new Edge(1.4f, neCorner));
        seCorner.edges.add(new Edge(1.4f, seIntersect));
        seIntersect.edges.add(new Edge(1.4f, seCorner));
        swCorner.edges.add(new Edge(1.4f, swIntersect));
        swIntersect.edges.add(new Edge(1.4f, swCorner));

        swIntersect.edges.add(new Edge(10f, nwCorner));
        swIntersect.edges.add(new Edge(10f, seCorner));
        swIntersect.edges.add(new Edge(7f, middle));

        nwIntersect.edges.add(new Edge(10f, neCorner));
        nwIntersect.edges.add(new Edge(10f, swCorner));
        nwIntersect.edges.add(new Edge(7f, middle));

        neIntersect.edges.add(new Edge(10f, nwCorner));
        neIntersect.edges.add(new Edge(10f, seCorner));
        neIntersect.edges.add(new Edge(7f, middle));

        seIntersect.edges.add(new Edge(10f, neCorner));
        seIntersect.edges.add(new Edge(10f, swCorner));
        seIntersect.edges.add(new Edge(7f, middle));

        middle.edges.add(new Edge(7f, neCorner));
        middle.edges.add(new Edge(7f, seCorner));
        middle.edges.add(new Edge(7f, swCorner));
        middle.edges.add(new Edge(7f, nwCorner));

        List<IControllableRover> r = mc.getRoverList();
        //nw
        r.get(0).setMission(new Mission(GraphOfPoints.NodesWithCostToPointArray(
                GraphOfPoints.shortestPath(middle, nwCorner))));
        r.get(0).start();

        //se
        r.get(1).setMission(new Mission(GraphOfPoints.NodesWithCostToPointArray(
                GraphOfPoints.shortestPath(middle, seCorner))));
        r.get(1).start();

        //sw
        r.get(2).setMission(new Mission(GraphOfPoints.NodesWithCostToPointArray(
                GraphOfPoints.shortestPath(middle, swCorner))));
        r.get(2).start();

        //This try catch is a temporary solution because of the crashes on some computers

        new Thread(() -> mc.loopForever()).start();
        try {
            Application.launch(GUI.class);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("if javafx error: this is sort of normal");
        }
        //ne
//		r.get(3).setMission(new Mission(GraphOfPoints.NodesWithCostToPointArray(		GraphOfPoints.shortestPath(neCorner, seCorner))));
        //r.get(3).start();

    }
}
