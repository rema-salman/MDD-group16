package mdsd.controller;

import mdsd.model.Area;
import mdsd.model.Environment;

import java.util.ArrayList;
import java.util.List;

public class MainController implements Observer {
    private List<IControllableRover> rovers;
    private Environment environment;
    private ScoreCalculator scoreCalculator;
    private static MainController mainController = null;

    private MainController() {
        this.rovers = new ArrayList<>();
        this.environment = new Environment();
    }

    public static MainController getInstance() {
        if (mainController == null) {
            mainController = new MainController();
        }
        return mainController;
    }

    public void setEnvironment(Environment environment) {
        this.environment = environment;
    }

    /*
     * Main loop for the MainController.
     */
    /*public void loopForever() {
        try {
            while (true) {
                // Update rover areas
                for (IControllableRover rover : rovers) {
                    rover.update();
                    Point2f roverPos = rover.getJavaPosition();
                    if (rover.getRoom() == null || !rover.getRoom().contains(roverPos)) {
                        Runnable roverUpdate = () -> {

                            for (Area room : environment.getAreas()) {
                                if (room.contains(roverPos)) {
                                    rover.stop();
                                    rover.setRoom(room);
                                    try {
                                        Thread.sleep(2000);
                                    } catch (InterruptedException e) {
                                        e.printStackTrace();
                                    } finally {
                                        rover.start();
                                    }
                                    break;
                                }
                            }

                        };

                        Thread roverUpdateThread = new Thread(roverUpdate);
                        roverUpdateThread.start();
                    }
                }
                Thread.sleep(16);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }*/

    public void addRovers(List<IControllableRover> rovers) {
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
        return new ArrayList<>(rovers);
    }

    public void stopRover(IControllableRover rover) {
        rover.stop();
    }

    public void stop() {
        for (IControllableRover r : rovers) {
            r.stop();
        }
        scoreCalculator.pause();
    }

    public void start() {
        for (IControllableRover r : rovers) {
            r.start();
        }
        scoreCalculator.resume();
    }

    @Override
    public void receiveEvent(Object event) {
        // TODO
    }

    public void setScoreCalculator(ScoreCalculator sc) {
        this.scoreCalculator = sc;
    }
}
