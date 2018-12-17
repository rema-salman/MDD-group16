package mdsd.model;

import mdsd.controller.Robot;
import project.Point;

import javax.vecmath.Point2f;
import java.awt.*;
import java.util.HashSet;
import java.util.Set;

public class University extends Environment {

    public University() {
        super();
        Color c = Color.GRAY; // because it's boring ..

        Point2f p1 = new Point2f(-5, 5);
        Point2f p2 = new Point2f(-5, 0);
        Point2f p3 = new Point2f(-5, -5);
        Point2f p4 = new Point2f(0, 5);
        Point2f p5 = new Point2f(0, 0);
        Point2f p6 = new Point2f(0, -5);
        Point2f p7 = new Point2f(5, 5);
        Point2f p8 = new Point2f(5, 0);
        Point2f p9 = new Point2f(5, -5);

        Set<Point2f> roomA = new HashSet<>();
        roomA.add(p1);
        roomA.add(p2);
        roomA.add(p5);
        roomA.add(p4);
        Area a1 = new Area(roomA);
        this.addArea(a1, true);

        Set<Point2f> roomB = new HashSet<>();
        roomB.add(p2);
        roomB.add(p3);
        roomB.add(p6);
        roomB.add(p5);
        Area a2 = new Area(roomB);
        this.addArea(a2, true);

        Set<Point2f> roomC = new HashSet<>();
        roomC.add(p4);
        roomC.add(p5);
        roomC.add(p8);
        roomC.add(p7);
        Area a3 = new Area(roomC);
        this.addArea(a3, true);

        Set<Point2f> roomD = new HashSet<>();
        roomD.add(p5);
        roomD.add(p6);
        roomD.add(p9);
        roomD.add(p8);
        Area a4 = new Area(roomD);
        this.addArea(a4, true);

        super.addHorizontalBoundary(-5.0f, -5.0f, 5.0f, c);
        super.addHorizontalBoundary(5.0f, -5.0f, 5.0f, c);
        super.addVerticalBoundary(5.0f, -5.0f, -3.0f, c);
        super.addVerticalBoundary(5.0f, -2.0f, 2.0f, c);
        super.addVerticalBoundary(5.0f, 3.0f, 5.0f, c);
        super.addVerticalBoundary(-5.0f, -5.0f, -3.0f, c);
        super.addVerticalBoundary(-5.0f, -2.0f, 2.0f, c);
        super.addVerticalBoundary(-5.0f, 3.0f, 5.0f, c);

        // create four rooms with doors
        super.addHorizontalWall(0f, -2.0f, 2.0f, c);
        super.addVerticalWall(0f, -2.0f, 2.0f, c);
        super.addHorizontalWall(0f, 5.0f, 3.0f, c);
        super.addHorizontalWall(0f, -5.0f, -3.0f, c);
        super.addVerticalWall(0f, 5.0f, 3.0f, c);
        super.addVerticalWall(0f, -5.0f, -3.0f, c);

        // horizontal -> (yPos, xStart, xEnd)
        // vertical   -> (xPos, yStart, yEnd)
        for (Obstacle ob : this.getObstacles()) {
            if (ob.horizontal) {
                super.addHorizontalWall(ob.x, ob.y, ob.length, c);
            } else {
                super.addVerticalWall(ob.x, ob.y, ob.length, c);
            }
        }

        //adding the initial robots' positions inside the environment 
        Robot rover1 = new Robot(new Point(2.5, -7), "Rover 1");
        Robot rover2 = new Robot(new Point(2.5, 7), "Rover 2");
        Robot rover3 = new Robot(new Point(-2.5, 7), "Rover 3");
        Robot rover4 = new Robot(new Point(-2.5, -7), "Rover 4");
        rovers.add(rover1);
        rovers.add(rover2);
        rovers.add(rover3);
        rovers.add(rover4);
    }
}
