package mdsd.controller;

import java.util.List;
import java.util.Set;

import mdsd.model.Area;

public class RewardSystem implements ScoreCalculator, Runnable {
    private Set<IControllableRover> rovers;
    private List<Area> physicalAreas;  // E.g. rooms
    private List<Area> logicalAreas;   // E.g. the coverage of a Wi-Fi router
    
    private Procedure procedureA;
    private Procedure procedureB;
    public Procedure activeProcedure;
    public int score = 0;
    public boolean running;

    public RewardSystem(Set<IControllableRover> rovers,
                        Procedure procedureA, Procedure procedureB,
                        List<Area> physicalAreas, List<Area> logicalAreas) {
        this.rovers = rovers;
        this.procedureA = procedureA;
        this.procedureB = procedureB;
        this.activeProcedure = procedureA;
        this.physicalAreas = physicalAreas;
        this.logicalAreas  = logicalAreas;
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
            score += activeProcedure.calculateReward();

            boolean changeProcedure = false;
            for (IControllableRover rover : rovers) {
                Area roverArea = rover.getRoom();
                if (activeProcedure == procedureA) {
                    if (logicalAreas.contains(roverArea)) {
                        changeProcedure = true;
                        break;
                    }
                } else {
                    if (physicalAreas.contains(roverArea)) {
                        changeProcedure = true;
                        break;
                    }
                }
            }

            if (changeProcedure) {
                activeProcedure = (activeProcedure == procedureA ?
                        procedureB : procedureA);
            }

            try {
                this.wait(20000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
