package mdsd.controller;

import mdsd.model.Area;

import java.util.List;

public class RewardSystem implements ScoreCalculator, Runnable {
    private List<IControllableRover> rovers;
    private List<Area> physicalAreas;  // E.g. rooms
    private List<Area> logicalAreas;   // E.g. the coverage of a Wi-Fi router

    private Procedure procedureA;
    private Procedure procedureB;
    public Procedure activeProcedure;
    public int score = 0;
    public boolean running;

    public RewardSystem(List<IControllableRover> rovers,
                        Procedure procedureA, Procedure procedureB,
                        List<Area> physicalAreas, List<Area> logicalAreas) {
        this.rovers = rovers;
        this.procedureA = procedureA;
        this.procedureB = procedureB;
        this.activeProcedure = procedureA;
        this.physicalAreas = physicalAreas;
        this.logicalAreas = logicalAreas;
    }

    public int calculateScore() {

        return 0;
    }

    public int getScore() {
        return score;
    }

    public void stop() {
        this.running = false;
    }

    public void run() {
        this.running = true;
        while (running) {
            try {
                Thread.sleep(20000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            score += activeProcedure.calculateReward();

            boolean changeProcedure = false;
            for (IControllableRover rover : rovers) {
                List<Area> roverAreas = rover.getRooms();
                for (Area area : roverAreas) {
                    if (activeProcedure == procedureA) {
                        if (logicalAreas.contains(area)) {
                            changeProcedure = true;
                            break;
                        }
                    } else {
                        if (physicalAreas.contains(area)) {
                            changeProcedure = true;
                            break;
                        }
                    }
                }
            }

            if (changeProcedure) {
                activeProcedure = (activeProcedure == procedureA ?
                        procedureB : procedureA);
            }
        }
    }
}
