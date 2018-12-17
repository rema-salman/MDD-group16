package mdsd.model;

import mdsd.controller.Robot;
import project.Point;

import javax.vecmath.Point2f;
import java.awt.*;
import java.util.List;
import java.util.*;

public class Hospital extends Environment {
    private Area consultingRoom;
    private List<Area> surgeryRooms;
    private List<Area> wifiZones;
    private List<Area> eatingAreas;

    public Hospital() {
        super();
        surgeryRooms = new ArrayList<>();
        wifiZones = new ArrayList<>();
        eatingAreas = new ArrayList<>();
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

        Division surgeryDivision1 = new Division(new HashSet<>(Arrays.asList(
                p1, p2, p3, p10, p9, p6, p5, p4, p8, p7)));
        Division surgeryDivision2 = new Division(new HashSet<>(Arrays.asList(
                p7, p8, p11, p12, p13, p9, p10, p16, p15, p14)));
        Division emergencyDivision = new Division(new HashSet<>(Arrays.asList(
                p4, p5, p6, p9, p13, p12, p11, p8)));

        Set<Point2f> sRoom1 = new HashSet<>();
        sRoom1.add(p1);
        sRoom1.add(p2);
        sRoom1.add(p5);
        sRoom1.add(p4);
        sRoom1.add(p8);
        sRoom1.add(p7);
        Area surgeryRoom1 = new Area(sRoom1, 20);
        surgeryDivision1.addRoom(surgeryRoom1);
        this.addArea(surgeryRoom1, true);
        surgeryRooms.add(surgeryRoom1);

        Set<Point2f> sRoom2 = new HashSet<>();
        sRoom2.add(p2);
        sRoom2.add(p3);
        sRoom2.add(p10);
        sRoom2.add(p9);
        sRoom1.add(p6);
        sRoom1.add(p5);
        Area surgeryRoom2 = new Area(sRoom2, 20);
        surgeryDivision1.addRoom(surgeryRoom2);
        this.addArea(surgeryRoom2, true);
        surgeryRooms.add(surgeryRoom2);

        Set<Point2f> sRoom3 = new HashSet<>();
        sRoom3.add(p7);
        sRoom3.add(p8);
        sRoom3.add(p11);
        sRoom3.add(p12);
        sRoom1.add(p15);
        sRoom1.add(p14);
        Area surgeryRoom3 = new Area(sRoom3, 20);
        surgeryDivision2.addRoom(surgeryRoom3);
        this.addArea(surgeryRoom3, true);
        surgeryRooms.add(surgeryRoom3);

        Set<Point2f> sRoom4 = new HashSet<>();
        sRoom4.add(p9);
        sRoom4.add(p10);
        sRoom4.add(p16);
        sRoom4.add(p15);
        sRoom1.add(p12);
        sRoom1.add(p13);
        Area surgeryRoom4 = new Area(sRoom4, 20);
        surgeryDivision2.addRoom(surgeryRoom4);
        this.addArea(surgeryRoom4, true);
        surgeryRooms.add(surgeryRoom4);

        Set<Point2f> cRoom1 = new HashSet<>();
        cRoom1.add(p4);
        cRoom1.add(p6);
        cRoom1.add(p11);
        cRoom1.add(p13);
        consultingRoom = new Area(cRoom1, 10);
        emergencyDivision.addRoom(consultingRoom);
        this.addArea(consultingRoom, true);

        super.addHorizontalBoundary(-6.0f, -6.0f, 6.0f, c2);
        super.addHorizontalBoundary(6.0f, -6.0f, 6.0f, c2);
        super.addVerticalBoundary(6.0f, -6.0f, -3.5f, c2);
        super.addVerticalBoundary(6.0f, -2.5f, 2.5f, c2);
        super.addVerticalBoundary(6.0f, 3.5f, 6.0f, c2);
        super.addVerticalBoundary(-6.0f, -6.0f, -3.5f, c2);
        super.addVerticalBoundary(-6.0f, -2.5f, 2.5f, c2);
        super.addVerticalBoundary(-6.0f, 3.5f, 6.0f, c2);

        // create four rooms with doors
        super.addHorizontalWall(3f, -2.25f, 2.25f, c1);
        super.addVerticalWall(3f, -2.25f, 2.25f, c1);
        super.addHorizontalWall(-3f, -2.25f, 2.25f, c1);
        super.addVerticalWall(-3f, -2.25f, 2.25f, c1);

        super.addHorizontalWall(0f, 6.0f, 4.0f, c2);
        super.addHorizontalWall(0f, -6.0f, -4.0f, c2);
        super.addVerticalWall(0f, 6.0f, 4.0f, c2);
        super.addVerticalWall(0f, -6.0f, -4.0f, c2);

        Robot rover1 = new Robot(new Point(5, -5), "Rover 1", this);
        Robot rover2 = new Robot(new Point(5, 5), "Rover 2", this);
        Robot rover3 = new Robot(new Point(-5, 5), "Rover 3", this);
        Robot rover4 = new Robot(new Point(-5, -5), "Rover 4", this);
        rovers.add(rover1);
        rovers.add(rover2);
        rovers.add(rover3);
        rovers.add(rover4);

        this.wifiZones = new ArrayList<>();
        this.eatingAreas = new ArrayList<>();
    }

    public Area getConsultingRoom() {
        return consultingRoom;
    }

    public List<Area> getSurgeryRooms() {
        return surgeryRooms;
    }

    public List<Area> getWifiZones() {
        return wifiZones;
    }

    public List<Area> getEatingAreas() {
        return eatingAreas;
    }
}
