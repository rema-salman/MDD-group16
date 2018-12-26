package test;

import mdsd.controller.Robot;
import mdsd.model.Area;
import mdsd.model.Environment;
import mdsd.model.Mission;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import javax.vecmath.Point2f;

import org.junit.Assert;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RobotTest {

    private Mission testMission;
    private Robot testRover;
    private Environment testEnv;
    private Point2f[] points;
    boolean waitingForEnter = false;
    private List<Area> currentRooms;
    private List<Area> lastRooms;
    private List<Area> newAreas;

    private boolean newRoom = false;
    private CountDownLatch lock = new CountDownLatch(1);
    private boolean stopped = false; // represents the button on GUI

    @BeforeEach
    void setUp() {
        points = new Point2f[] { (new Point2f(3, 3)), (new Point2f(0, 0)) };
        testMission = new Mission(points);
        testRover = new Robot(new Point2f(5, 5), "testRobot", testEnv);
        testEnv = new Environment();
        testRover.setMission(testMission);

        /* initializations for the mission executions */
        currentRooms = new ArrayList<>();
        lastRooms = new ArrayList<>(currentRooms);
        newAreas = new ArrayList<>();
    }

    @Test
    public void testMissionAssigning() {

        Assert.assertEquals("Mission is assigned to Rover.", true, testRover.getMission() == testMission);
        Assert.assertTrue("Mission is not null.", testMission != null);
    }

    @Test
    public void testgettingMission() {

        Assert.assertTrue("Mission is not null.", testMission != null);
        Assert.assertEquals("Mission is the assigned one.", true, testMission == testRover.getMission());
    }

    @Test
    public void testRobotstart() {
        testRover.start();
        Assert.assertTrue("", !waitingForEnter);
        if (testMission.getCurrentPoint() == points[0]) {
            Assert.assertTrue("", testMission.getNextPoint() == points[1]);
            testRover.setDestination(testRover.getPosition());
            Assert.assertTrue("", testRover.getJavaPosition() == testMission.getNextPoint());
        }
    }

    @Test
    public void testRobotStop() {
        testRover.stop();
        Assert.assertFalse("", waitingForEnter);
        if (testMission.getCurrentPoint() == points[0]) {
            Assert.assertTrue(testMission.getNextPoint() == points[1]);
            testRover.setDestination(testRover.getPosition());
            Assert.assertTrue("", testRover.getJavaPosition() == testMission.getCurrentPoint());
        }
    }

    @Test
    public void testRobotUpdate() {
        testRover.update();

        if (testMission.getNextPoint() == points[0]) {

            Assert.assertEquals("Rover next des is next dest in mission.", true,
                    testMission.getNextPoint() == points[1]);

            Assert.assertEquals("Rover next des is next dest in mission.", true,
                    testRover.getJavaPosition() == testMission.getNextPoint());
            testRover.start();
        }
    }

    @Test
    public void testEquals() {
        Object testRover1 = null;
        testRover.equals(testRover1);

        if (testRover1 instanceof Robot) {
            Assert.assertTrue("testRover1 has ID = 2 ", testRover == (Robot) testRover1);
            Assert.assertEquals("Both are the same rover & have same ID ", true,
                    testRover.getId() == ((Robot) testRover1).getId());
        }
    }

    @Test
    public void testRobotExecutionCase1() throws InterruptedException {

        testRover.run();
        Assert.assertTrue(true);
        testRover.update();

        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                Assert.assertFalse(newRoom); // newRoom is false
                Assert.assertTrue(testEnv.getAreas() != null);
                for (Area area : testEnv.getAreas()) {
                    Assert.assertTrue(area.contains(testRover.getJavaPosition()));
                    Assert.assertTrue(!lastRooms.contains(area));
                    Assert.assertTrue(waitingForEnter);
                    currentRooms.add(area);
                    area.enter(testRover);
                    for (Area area2 : testEnv.getAreas()) {
                        Assert.assertTrue(!area2.equals(area)); // Leave all old areas
                        area2.leave(testRover);
                    }
                    Assert.assertTrue(!newRoom);
                    Assert.assertEquals("", true, newAreas.add(area));
                }
            }
        });
        t.start();
    }

    @Test
    public void testRobotExecutionCase2() throws InterruptedException {

        testRover.run();
        Assert.assertTrue(true);
        testRover.update();

        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                Assert.assertFalse(newRoom); // newRoom is false
                Assert.assertTrue(testEnv.getAreas() != null);
                for (Area area : testEnv.getAreas()) {
                    Assert.assertTrue(!area.contains(testRover.getJavaPosition()));
                    Assert.assertTrue(lastRooms.contains(area));
                    currentRooms.remove(area);
                    area.leave(testRover);
                }
            }
        });
        t.start();
    }

    // Case: when Rover enters a new room
    @Test
    public void testRobotExecutionCase3() throws InterruptedException {

        testRover.run();
        Assert.assertTrue(true);

        testRover.update();
        Assert.assertFalse(newRoom);
        testRover.setDestination(testRover.getPosition());

        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    lock.await(2000, TimeUnit.MILLISECONDS);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                Assert.assertFalse(!newAreas.isEmpty());
                newAreas.removeIf(area -> area.canEnter(testRover));
                Assert.assertFalse(!newAreas.isEmpty());
                try {
                    lock.await(200, TimeUnit.MILLISECONDS);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                Assert.assertEquals(false, waitingForEnter);
                Assert.assertFalse(stopped); // button is not pressed on the GUI
                try {
                    lock.await(20, TimeUnit.MILLISECONDS);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                testRover.start();
            }
        });
        t.start();
    }

    @Test
    public void testRobotExecutionCase4() throws InterruptedException {
        testRover.run();
        Assert.assertFalse(newRoom);
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    lock.await(16, TimeUnit.MILLISECONDS);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        t.start();

    }

    @AfterEach
    void tearDown() {
    }
}
