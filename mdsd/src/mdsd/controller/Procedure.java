package mdsd.controller;

import java.util.Set;

public class Procedure extends Thread {
    private Set<Robot> rovers;
    private IProcedure procedure;

    public Procedure(Set<Robot> rovers, IProcedure procedure) {
        this.rovers = rovers;
        this.procedure = procedure;
    }

    @Override
    public void run() {
        System.out.println("Procedure started");

        while (true) {
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            for (Robot rover : rovers) {
                procedure.calculateRewards(rover);
                
                // TODO: connect this with GUI, we need to display each rover's reward points 
                //rover.getRewardPoints();
            }
        }

    }
    
    public void changeProcedureType(IProcedure newProcedure) {
        this.procedure = newProcedure;
    }
}
