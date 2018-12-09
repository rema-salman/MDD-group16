package mdsd.model;

import java.awt.Color;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import javax.vecmath.Point2f;

import mdsd.controller.Robot;
import project.Point;
import simbad.sim.HorizontalWall;
import simbad.sim.VerticalWall;
import simbad.sim.Wall;

public class Hospital extends EnvironmentAdoptee {

    public Hospital() {
        super();
        this.rovers = new HashSet<>();
        this.missions = new Mission[4];
        Color c1 = Color.BLUE;
        Color c2 = Color.RED;

        Point2f p1 = new Point2f(-6, 6);
        Point2f p2 = new Point2f(-6, 0);
        Point2f p3 = new Point2f(-6, -6);
        Point2f p4 = new Point2f(-3, 3);
        Point2f p5 = new Point2f(-3, 0);
        Point2f p6 = new Point2f(-3, -3);
        Point2f p7 = new Point2f(0, 6);
        Point2f p8 = new Point2f(0, 3);
        Point2f p9 = new Point2f(0, -3);
        Point2f p10 = new Point2f(0, -6);
        Point2f p11 = new Point2f(3, 3);
        Point2f p12 = new Point2f(3, 0);
        Point2f p13 = new Point2f(3, -3);
        Point2f p14 = new Point2f(6, 6);
        Point2f p15 = new Point2f(6, 0);
        Point2f p16 = new Point2f(6, -6);

        SurgeryDivision sd1 = new SurgeryDivision(
                new HashSet<>(Arrays.asList(p1, p2, p3, p10, p9, p6, p5, p4, p8, p7)));
        SurgeryDivision sd2 = new SurgeryDivision(
                new HashSet<>(Arrays.asList(p7, p8, p11, p12, p13, p9, p10, p16, p15, p14)));
        EmergencyDivision ed = new EmergencyDivision(new HashSet<>(Arrays.asList(p4, p5, p6, p9, p13, p12, p11, p8)));

        Set<Point2f> sRoom1 = new HashSet<>();
        sRoom1.add(p1);
        sRoom1.add(p2);
        sRoom1.add(p5);
        sRoom1.add(p4);
        sRoom1.add(p8);
        sRoom1.add(p7);
        Area a1 = new SurgeryRoom(sRoom1, sd1);
        this.addArea(a1);

        Set<Point2f> sRoom2 = new HashSet<>();
        sRoom2.add(p2);
        sRoom2.add(p3);
        sRoom2.add(p10);
        sRoom2.add(p9);
        sRoom1.add(p6);
        sRoom1.add(p5);
        Area a2 = new SurgeryRoom(sRoom2, sd1);
        this.addArea(a2);

        Set<Point2f> sRoom3 = new HashSet<>();
        sRoom3.add(p7);
        sRoom3.add(p8);
        sRoom3.add(p11);
        sRoom3.add(p12);
        sRoom1.add(p15);
        sRoom1.add(p14);
        Area a3 = new SurgeryRoom(sRoom3, sd2);
        this.addArea(a3);

        Set<Point2f> sRoom4 = new HashSet<>();
        sRoom4.add(p9);
        sRoom4.add(p10);
        sRoom4.add(p16);
        sRoom4.add(p15);
        sRoom1.add(p12);
        sRoom1.add(p13);
        Area a4 = new SurgeryRoom(sRoom4, sd2);
        this.addArea(a4);

        Set<Point2f> cRoom1 = new HashSet<>();
        cRoom1.add(p4);
        cRoom1.add(p6);
        cRoom1.add(p11);
        cRoom1.add(p13);
        Area a5 = new ConsultingRoom(cRoom1, ed);
        this.addArea(a5);

        super.addBoundry(-6.0f, -6.0f, 6.0f, this, c2, true);
        super.addBoundry(6.0f, -6.0f, 6.0f, this, c2, true);
        super.addBoundry(6.0f, -6.0f, -3.5f, this, c2, false);
        super.addBoundry(6.0f, -2.5f, 2.5f, this, c2, false);
        super.addBoundry(6.0f, 3.5f, 6.0f, this, c2, false);
        super.addBoundry(-6.0f, -6.0f, -3.5f, this, c2, false);
        super.addBoundry(-6.0f, -2.5f, 2.5f, this, c2, false);
        super.addBoundry(-6.0f, 3.5f, 6.0f, this, c2, false);

        // create four rooms with doors
        super.addWall(3f, -2.25f, 2.25f, this, c1, true);
        super.addWall(3f, -2.25f, 2.25f, this, c1, false);
        super.addWall(-3f, -2.25f, 2.25f, this, c1, true);
        super.addWall(-3f, -2.25f, 2.25f, this, c1, false);
        
        for (Obstacle ob : this.getObstacles()) {
            if (ob.horizontal) {
                super.addWall(ob.x, ob.y, ob.length, this, c1, true);
            } else {
                super.addWall(ob.x, ob.y, ob.length, this, c1, false);
            }
        }

        super.addWall(0f, 6.0f, 4.0f, this, c2, true);
        super.addWall(0f, -6.0f, -4.0f, this, c2, true);
        super.addWall(0f, 6f, 4.0f, this, c2, false);
        super.addWall(0f, -6f, -4.0f, this, c2, false);
        
        for (Obstacle ob : this.getObstacles()) {
            if (ob.horizontal) {
                super.addWall(ob.x, ob.y, ob.length, this, c2, true);
            } else {
                super.addWall(ob.x, ob.y, ob.length, this, c2, false);
            }
        }

        Robot rover1 = new Robot(new Point(5, -5), "Rover 1");
        Robot rover2 = new Robot(new Point(5, 5), "Rover 2");
        Robot rover3 = new Robot(new Point(-5, 5), "Rover 3");
        Robot rover4 = new Robot(new Point(-5, -5), "Rover 4");
        rovers.add(rover1);
        rovers.add(rover2);
        rovers.add(rover3);
        rovers.add(rover4);

    }

}
