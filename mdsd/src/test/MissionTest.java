package test;

import mdsd.controller.Robot;
import mdsd.model.Environment;
import mdsd.model.Mission;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.vecmath.Point2f;

import static org.junit.jupiter.api.Assertions.assertEquals;

class MissionTest {


    Mission testMission = null;
    Robot testRover = null;
    Environment testEnv = null;

    // TODO define mission, rover and environment
    @BeforeEach
    void setUp() {
        Mission testMission = null;
        Robot testRover = null;
        Environment testEnv = null;
    }

    @Test
    public void testMission() {

        while (testMission.getCurrentPoint() != null) {
            Point2f dest = testMission.getCurrentPoint();
            testRover.setDestination(dest);
            try {
                this.wait(10000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            assertEquals(dest, testRover.getJavaPosition());
        }
    }

    @AfterEach
    void tearDown() {
    }
}


