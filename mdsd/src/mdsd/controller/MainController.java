package mdsd.controller;

import mdsd.model.Area;
import mdsd.model.Environment;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.vecmath.Point2f;


public class MainController implements Observer {
    private Set<IControllableRover> rovers;
    private Environment environment;
    private ScoreCalculator scoreCalculator;
    private static MainController mainController = null;

    private MainController() {
        this.rovers = new HashSet<IControllableRover>();
        this.environment = new Environment();
    }

    public static MainController getInstance() {
        if (mainController == null) {
            mainController = new MainController();
        }
        return mainController;
    }
    
    /**
     * Main loop for the MainController.
     */
    public void loopForever() {
        while (true) {
            // Update rover areas
            for (IControllableRover rover : rovers) {
                Point2f roverPos = rover.getJavaPosition();
                if (!rover.getRoom().contains(roverPos)) {
                    Runnable roverUpdate = () -> {
                        rover.stop();

                        for (Area room : environment.getPhysicalAreas()) {
                            if (room.contains(roverPos)) {
                                rover.setRoom(room);
                                break;
                            }
                        }

                        try {
                            Thread.sleep(2000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }

                        rover.start();
                    };

                    Thread roverUpdateThread = new Thread(roverUpdate);
                    roverUpdateThread.start();
                }
            }
        }
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

    public int getScore() {
        return (scoreCalculator == null ? 0 : scoreCalculator.getScore());
    }

    public Environment getEnvironment() {
        return environment;
    }

    // TODO, return type
    public void getRoverStatus() {

    }

    public List<IControllableRover> getRoverList() {
        List<IControllableRover> list = new ArrayList<>();
        list.addAll(rovers);
        return list;
    }

    public void stopRover(IControllableRover rover) {
        rover.stop();
    }

    public void stop() {
        for (IControllableRover r : rovers) {
            r.stop();
        }
    }

    public void start() {
        for (IControllableRover r : rovers) {
            r.start();
        }
    }

    @Override
    public void receiveEvent(Object event) {
        // TODO
    }

    public void setScoreCalculator(ScoreCalculator sc) {
        this.scoreCalculator = sc;
    }
}
