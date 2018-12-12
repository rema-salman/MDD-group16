package mdsd.controller;

import java.util.List;
import java.util.Set;

import javax.vecmath.Point2f;

import mdsd.model.Area;

public class Procedure {
    private Set<IControllableRover> rovers;
    private List<List<Area>> areaLists;
    private int[] rewards;

    public Procedure(Set<IControllableRover> rovers,
                     List<List<Area>> areaLists, int[] rewards) {
        this.rovers = rovers;
        this.areaLists.addAll(areaLists);
        this.rewards = rewards;
    }

    public int calculateReward() {
        int reward = 0;

        for (IControllableRover rover : rovers) {
            Point2f pos = rover.getJavaPosition();
            for (List<Area> areas : areaLists) {
                for (int i = 0; i < areas.size(); ++i) {
                    if (areas.get(i).contains(pos)) {
                        reward += rewards[i];
                    }
                }
            }
        }

        return reward;
    }
}
