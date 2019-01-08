package mdsd.controller;

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

    public void addRovers(List<IControllableRover> rovers) {
        this.rovers.addAll(rovers);
    }

    public int getScore() {
        return (scoreCalculator == null ? 0 : scoreCalculator.getScore());
    }

    public Environment getEnvironment() {
        return environment;
    }

    public void getAllRoversStatus() {
        for (IControllableRover r : rovers) {
            r.getStatus();
        }
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

    public void setScoreCalculator(ScoreCalculator sc) {
        this.scoreCalculator = sc;
    }

    public List<String> getFaults() {
        List<String> faults = new ArrayList<>();
        for (IControllableRover r : rovers) {
            faults.addAll(r.getFaults());
        }
        return faults;
    }

    @Override
    public void onEvent(IControllableRover rover, Object event) {
        System.out.println("Event received from rover " + rover.getId() + ": " + event);
    }
}
