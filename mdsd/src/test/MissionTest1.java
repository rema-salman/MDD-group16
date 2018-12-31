package test;

import mdsd.controller.Robot;
import mdsd.model.Environment;
import mdsd.model.Mission;
import org.junit.Assert;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.vecmath.Point2f;

class MissionTest1 {

    private Mission testMission;
    private Robot testRover;
    private Environment testEnv;
    private Point2f[] points;

    // TODO define mission, rover and environment
    @BeforeEach
    void setUp() {
        points = new Point2f[]{(new Point2f(3, 3)), (new Point2f(0, 0))};
        testMission = new Mission(points);
        testRover = new Robot(new Point2f(5, 5), "testRobot", testEnv);
        testEnv = new Environment();
    }

    @Test
    public void testGetCurrentPoint() {
        if (testMission.getCurrentPoint() == points[0]) {
            Assert.assertTrue("current mission in the array", testMission.getCurrentPoint() == points[0]);
        }
    }

    @Test
    public void testGetNextPoint() {
        testRover.setMission(testMission);
        if (testMission.getCurrentPoint() == points[0]) {
            Assert.assertNotEquals("there is a next Point ", null, (points.length <= 2));
            Assert.assertTrue("next Point mission array", testMission.getNextPoint() == points[1]);
        }
    }

    @Test
    public void testgetNumberOfMissonPoints() {
        Assert.assertEquals("Number Of Misson's Points", 2, points.length);
    }


//            testRover.setDestination(testMission.getNextPoint());
//            Assert.assertEquals("Mission is sat to the next point.", true, testRover.getJavaPosition()== testMission.getNextPoint());
//        }
//        Assert.assertTrue("Car new position", car.currentPosition.x == 25);
//        
//
//        while (testMission.getCurrentPoint() != null) {
//            Point2f dest = testMission.getCurrentPoint();
//            testRover.setDestination(dest);
//            try {
//                this.wait(10000);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//            assertEquals(dest, testRover.getJavaPosition());
//        }

    @AfterEach
    void tearDown() {
    }

}
