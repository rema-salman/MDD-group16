package mdsd;

import mdsd.controller.Robot;
import project.AbstractSimulatorMonitor;
import project.Point;
import simbad.sim.*;

import java.awt.*;
import java.util.HashSet;
import java.util.Set;

public class TestMission {

    @SuppressWarnings("unused")
    public static void main(String[] args) {

        EnvironmentDescription e = new EnvironmentDescription();

        Color color = Color.GRAY;

        //horizontal (yPos, xStart,xEnd)
        //vertical   (xPos, yStart, yEnd)
        Boundary w1 = new HorizontalBoundary(-6.0f, -6.0f, 6.0f, e, color);
        Boundary w2 = new HorizontalBoundary(6.0f, -6.0f, 6.0f, e, color);
        Boundary w3 = new VerticalBoundary(6.0f, -6.0f, -4.0f, e, color);
        Boundary w4 = new VerticalBoundary(6.0f, -2.0f, 2.0f, e, color);
        Boundary w5 = new VerticalBoundary(6.0f, 4.0f, 6.0f, e, color);
        Boundary w6 = new VerticalBoundary(-6.0f, -6.0f, -4.0f, e, color);
        Boundary w7 = new VerticalBoundary(-6.0f, -2.0f, 2.0f, e, color);
        Boundary w8 = new VerticalBoundary(-6.0f, 4.0f, 6.0f, e, color);

        AbstractWall roomWall1 = new HorizontalWall(0f, -2f, 2f, e, color);
        AbstractWall roomWall2 = new VerticalWall(0f, -6f, -4f, e, color);
        AbstractWall roomWall3 = new VerticalWall(0f, -2f, 2f, e, color);
        AbstractWall roomWall4 = new VerticalWall(0f, 4f, 6f, e, color);
        AbstractWall roomWall5 = new HorizontalWall(0f, -6f, -4f, e, color);
        AbstractWall roomWall6 = new HorizontalWall(0f, 4f, 6f, e, color);

        Set<Robot> robots = new HashSet<>();
        Robot robot1 = new Robot(new Point(-6, -8), "Robot 1", null); // TODO add environment
        Robot robot2 = new Robot(new Point(-3, -8), "Robot 2", null);
        Robot robot3 = new Robot(new Point(3, -8), "Robot 3", null);
        Robot robot4 = new Robot(new Point(6, -8), "Robot 4", null);

        robots.add(robot1);
        robots.add(robot2);
        robots.add(robot3);
        robots.add(robot4);


        AbstractSimulatorMonitor<Robot> controller = new SimulatorMonitor(robots, e);

        robot1.setDestination(new Point(-7.5, -8));
        robot2.setDestination(new Point(-4.5, -2));
        robot3.setDestination(new Point(3, -3));
        robot4.setDestination(new Point(7.5, -8));
        robot1.start();
        robot2.start();
        robot3.start();
        robot4.start();

        try {
            Thread.sleep(4000);
        } catch (InterruptedException e1) {
            e1.printStackTrace();
        }
        robot4.setDestination(new Point(7.5, 8));
        robot1.setDestination(new Point(-7.5, 8));
        robot4.start();
        robot1.start();

        try {
            Thread.sleep(3000);
        } catch (InterruptedException e1) {
            e1.printStackTrace();
        }
        robot2.setDestination(new Point(3, -3));
        robot3.setDestination(new Point(3, 9));
        robot2.start();
        robot3.start();

        try {
            Thread.sleep(7000);
        } catch (InterruptedException e1) {
            e1.printStackTrace();
        }
        robot2.setDestination(new Point(3, -8));

        try {
            Thread.sleep(7000);
        } catch (InterruptedException e1) {
            e1.printStackTrace();
        }
        robot1.setDestination(new Point(-3, 8));
        robot4.setDestination(new Point(3, 8));
        robot1.start();
        robot4.start();

        try {
            Thread.sleep(5000);
        } catch (InterruptedException e1) {
            e1.printStackTrace();
        }
        robot1.setDestination(new Point(-3, 3));
        robot4.setDestination(new Point(4, 4));
        robot1.start();
        robot4.start();

        try {
            Thread.sleep(7000);
        } catch (InterruptedException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }
        robot1.setDestination(new Point(-3, -8));
        robot4.setDestination(new Point(-3, 3));
        robot1.start();
        robot4.start();

        try {
            Thread.sleep(7000);
        } catch (InterruptedException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }

        robot4.setDestination(new Point(-3, 8));
        robot4.start();
    }
}
