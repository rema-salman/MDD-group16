package mdsd.controller;

import mdsd.model.Area;
import javax.vecmath.Point2f;
import mdsd.model.Environment;

public class HospitalProcedure implements IProcedure {

    private static final int PROCEDURE_A = 1;
    private static final int PROCEDURE_B = 2;

    private int currentProcedure = PROCEDURE_A;

    @Override
    public void calculateRewards(Robot rover) {
        Environment rovEnv = rover.getEnvironment();

        if (rovEnv == null)
            return;
        if (rovEnv.getPhysicalAreas() == null)
            return;
        if (rovEnv.getLogicalAreas() == null)
            return;
//        for (Area a : rovEnv.getAreas()) {
//            if (a == null)
//                return;
//            if (a.contains(rover.getJavaPosition())) {
//                if (a.getClass().equals(ConsultingRoom.class)) {
//                    rover.addRewardPoints(10);
//                }
//
//                if (a.getClass().equals(SurgeryRoom.class)) {
//                    rover.addRewardPoints(20);
//                }
//
//                if (a.getClass().equals(WiFi.class)) {
//                    rover.addRewardPoints(10);
//                }
//
//                if (a.getClass().equals(EatingArea.class)) {
//                    rover.addRewardPoints(10);
//                }
//            }
//        }

    }

    @Override
    public int calculateScore(Point2f[] roverLocations, Area[] logicalAreas, Area[] physicalAreas) {
        // TODO Auto-generated method stub
        return 0;
    }

}
