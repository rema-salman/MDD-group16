package test;

import mdsd.controller.IControllableRover;
import mdsd.controller.Procedure;
import mdsd.model.Area;
import org.junit.Assert;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.vecmath.Point2f;
import java.util.ArrayList;
import java.util.List;

class ProcedureTest {

    private List<IControllableRover> rovers;
    private List<List<Area>> areaLists;
    private Procedure testProcedure;
    private int reward;

    @BeforeEach
    void setUp() {

        rovers = new ArrayList<IControllableRover>();
        areaLists = new ArrayList<>();
        areaLists.addAll(areaLists);

        testProcedure = new Procedure(rovers, areaLists);

    }

    /*
     * Case: checking the area and according to it the reward are added.
     */
    @Test
    void calculateRewardTestCase1() {

        testProcedure.calculateReward();

        for (IControllableRover rover : rovers) {
            Point2f pos = rover.getJavaPosition();
            for (List<Area> areas : areaLists) {
                for (Area area : areas) {
                    Assert.assertTrue(area.contains(pos));
                    Assert.assertEquals(reward, reward += area.getReward());
                }
            }
        }
    }

    /*
     * Case; when the rovers are not in areas or before moving
     */
    @Test
    void calculateRewardTestCase2() {

        testProcedure.calculateReward();
        Assert.assertEquals(0, reward);
    }

    @AfterEach
    void tearDown() {
    }
}
