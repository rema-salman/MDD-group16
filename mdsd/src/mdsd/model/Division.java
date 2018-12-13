package mdsd.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import javax.vecmath.Point2f;

public class Division {
    public List<Area> rooms;

    public Division(Set<Point2f> shapes) {
        rooms = new ArrayList<>();
        rooms.add(new Area(shapes));
    }

    public void addRoom(Area room) {
        rooms.add(room);
    }
}
