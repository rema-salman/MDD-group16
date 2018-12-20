package mdsd.controller;

import mdsd.model.Area;

import java.util.List;

public class RewardSystem implements ScoreCalculator, Runnable {
    private List<IControllableRover> rovers;
    private List<Area> physicalAreas;  // E.g. rooms
    private List<Area> logicalAreas;   // E.g. the coverage of a Wi-Fi router

    private Procedure procedureA;
    private Procedure procedureB;
    private Procedure activeProcedure;
    private int score = 0;
    private boolean paused;
    private boolean running;

    public RewardSystem(List<IControllableRover> rovers,
                        Procedure procedureA, Procedure procedureB,
                        List<Area> physicalAreas, List<Area> logicalAreas) {
        this.rovers = rovers;
        this.procedureA = procedureA;
        this.procedureB = procedureB;
        this.activeProcedure = procedureA;
        this.physicalAreas = physicalAreas;
        this.logicalAreas = logicalAreas;
        this.paused = false;
        this.running = true;
    }

    @Override
    public int getScore() {
        return score;
    }

    @Override
    public void pause() {
        paused = true;
    }

    @Override
    public void resume() {
        paused = false;
    }

    @Override
    public void stop() {
        running = false;
    }

    @Override
    public void run() {
        while (running) {
            try {
                Thread.sleep(20000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            while (paused) {
                try {
                    Thread.sleep(200);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
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
