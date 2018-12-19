package mdsd.controller;

import mdsd.model.Area;

import javax.vecmath.Point2f;
import java.util.ArrayList;
import java.util.List;

public class Procedure {
    private List<IControllableRover> rovers;
    private List<List<Area>> areaLists;
    //private int[] rewards;

    public Procedure(List<IControllableRover> rovers, List<List<Area>> areaLists) {//, int[] rewards) {
        this.rovers = rovers;
        this.areaLists = new ArrayList<>();
        this.areaLists.addAll(areaLists);
        //this.rewards = rewards;
    }

    public int calculateReward() {
        int reward = 0;

        for (IControllableRover rover : rovers) {
            Point2f pos = rover.getJavaPosition();
            for (List<Area> areas : areaLists) {
                //for (int i = 0; i < areas.size(); ++i) {
                //if (areas.get(i).contains(pos)) {
                //reward += rewards[i];
                for (Area area : areas) {
                    if (area.contains(pos)) {
                        reward += area.getReward();
                    }
                }
            }
        }

        return reward;
    }
}
