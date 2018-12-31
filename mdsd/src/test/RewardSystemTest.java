package test;

import mdsd.controller.IControllableRover;
import mdsd.controller.Procedure;
import mdsd.controller.RewardSystem;
import mdsd.model.Area;
import org.junit.Assert;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

class RewardSystemTest {

    private List<IControllableRover> rovers;
    private List<Area> physicalAreas;
    private List<Area> logicalAreas;

    private Procedure testProcedureA;
    private Procedure testProcedureB;
    private Procedure testActiveProcedure;
    private int score = 0;
    private boolean paused;
    private boolean running;
    private RewardSystem testRewardsystem;

    private CountDownLatch lock = new CountDownLatch(1);

    @BeforeEach
    void setUp() {
        testRewardsystem = new RewardSystem(rovers, testProcedureA, testProcedureB, physicalAreas, logicalAreas);
        paused = false;
        running = true;
    }

    @Test
    public void testGetScore() {
        testRewardsystem.getScore();
        Assert.assertEquals("Score starts as 0", score, testRewardsystem.getScore());
    }

    @Test
    public void testPause() {

        testRewardsystem.pause();
        Assert.assertTrue(!paused);

    }

    @Test
    public void testResume() {
        testRewardsystem.resume();
        Assert.assertFalse(paused);
    }

    @Test
    public void teststop() {
        testRewardsystem.stop();
        Assert.assertTrue(running);
    }

    @Test
    public void testRewardSystemRunCase() {

        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                testRewardsystem.run();
                Assert.assertTrue(running);

                try {
                    lock.await(20000, TimeUnit.MILLISECONDS);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                Assert.assertTrue(paused);
                try {
                    lock.await(200, TimeUnit.MILLISECONDS);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                Assert.assertEquals(score, score += testActiveProcedure.calculateReward());

            }
        });
        t.start();
    }

    /*
     * Testing the change procedure from A to B in logical Areas
     */
    @Test
    public void testRewardSystemRunCase1() {

        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                testRewardsystem.run();
                Assert.assertTrue(running);

                for (IControllableRover rover : rovers) {
                    List<Area> roverAreas = rover.getRooms();
                    for (Area area : roverAreas) {
                        Assert.assertTrue(testActiveProcedure == testProcedureA);
                        Assert.assertTrue(logicalAreas.contains(area));
                        Assert.assertEquals(testActiveProcedure, testProcedureB);
                    }
                }
            }
        });
        t.start();
    }

    /*
     * Testing the change procedure from B to A in logical Areas
     */
    @Test
    public void testRewardSystemRunCase2() {

        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                testRewardsystem.run();
                Assert.assertTrue(running);

                for (IControllableRover rover : rovers) {
                    List<Area> roverAreas = rover.getRooms();
                    for (Area area : roverAreas) {
                        Assert.assertTrue(testActiveProcedure == testProcedureB);
                        Assert.assertTrue(logicalAreas.contains(area));
                        Assert.assertEquals(testActiveProcedure, testProcedureA);
                    }
                }
            }
        });
        t.start();
    }

    /*
     * Testing the change procedure from A to B in physical Areas
     */
    @Test
    public void testRewardSystemRunCase3() {

        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                testRewardsystem.run();
                Assert.assertTrue(running);

                for (IControllableRover rover : rovers) {
                    List<Area> roverAreas = rover.getRooms();
                    for (Area area : roverAreas) {
                        Assert.assertTrue(testActiveProcedure == testProcedureA);
                        Assert.assertTrue(physicalAreas.contains(area));
                        Assert.assertEquals(testActiveProcedure, testProcedureB);
                    }
                }
            }
        });
        t.start();
    }

    /*
     * Testing the change procedure from B to A in physical Areas
     */
    @Test
    public void testRewardSystemRunCase4() {

        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                testRewardsystem.run();
                Assert.assertTrue(running);

                for (IControllableRover rover : rovers) {
                    List<Area> roverAreas = rover.getRooms();
                    for (Area area : roverAreas) {
                        Assert.assertTrue(testActiveProcedure == testProcedureB);
                        Assert.assertTrue(physicalAreas.contains(area));
                        Assert.assertEquals(testActiveProcedure, testProcedureA);
                    }
                }
            }
        });
        t.start();
    }

    @AfterEach
    void tearDown() {
    }
}
