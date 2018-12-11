package mdsd.controller;

import mdsd.model.Hospital;
import mdsd.model.Area;
import mdsd.model.Environment;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


public class MainController implements Observer {
    private Hospital hospital;

    private Set<IControllableRover> rovers;
    private Environment environment;
    private ScoreCalculator scoreCalculator;
    //private Procedure procedure;
    private static MainController mainController = null;

    private MainController() {
        rovers = new HashSet<IControllableRover>();
        environment = new Environment();
        scoreCalculator = new ScoreCalculator();
    }

    public static MainController getInstance() {
        if (mainController == null) {
            mainController = new MainController();
        }
        return mainController;
    }

    public void addRovers(Set<IControllableRover> rovers) {
        this.rovers.addAll(rovers);
    }

    // TODO
    public void getAllRoverPositions() {

    }

    // TODO
    public void getRoverPositions(Area area) {

    }

    // TODO
    public int getScore() {
        if (scoreCalculator == null) {
            return 0;
        }
        return scoreCalculator.score;
    }

    public Environment getEnvironment() {
        return environment;
    }

    // TODO, return type
    public void getRoverStatus() {

    }

    // TODO
    public List<IControllableRover> getRovers() {
        List<IControllableRover> list = new ArrayList<>();
        if (rovers == null) {
            return list;
        }

        for (IControllableRover r : rovers) {
            if (r != null) {
                list.add(r);
            }
        }
        return list;
    }

    public void stopRover(IControllableRover rover) {
        rover.stop();
    }

    public void stop() {
        if (rovers != null) {
            for (IControllableRover r : rovers) {
                if (r != null) {  // Why would they be null?
                    r.stop();
                }
            }
        }
    }

    public void start() {
        if (rovers != null) {
            for (IControllableRover r : rovers) {
                if (r != null) {  // Why would they be null?
                    r.start();
                }
            }
        }
    }

    @Override
    public void receiveEvent(Object event) {
        // TODO
    }

    protected class ScoreCalculator implements Runnable {
        private Procedure procedureA;
        private Procedure procedureB;
        public int activeProcedure;
        public int score = 0;
        public boolean running = true;

        public ScoreCalculator() {
            int[] rewards = {10, 10};
            List<List<Area>> areas = new ArrayList<>();
            List<Area> consultingRoomArea = new ArrayList<>();
            consultingRoomArea.add(hospital.getConsultingRoom());
            areas.add(consultingRoomArea);
            areas.add(hospital.getSurgeryRooms());
            procedureA = new Procedure(rovers, areas, rewards);
        }

        public void run() {
            while (running) {

                // score += activeProcedure.calculateScore();

                try {
                    this.wait(20000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
