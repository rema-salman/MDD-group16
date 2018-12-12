package mdsd.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import javax.vecmath.Point2f;

@SuppressWarnings("serial")
public class Division extends Area {
    public List<Area> rooms;

    public Division(Set<Point2f> shapes) {
        super(shapes);
        rooms = new ArrayList<>();
        rooms.add(new Area(shapes));
    }

    public void addRoom(Area room) {
        shapes.addAll(room.getShapes());
        antiShapes.addAll(room.getAntiShapes());
        rooms.add(room);
    }
}
