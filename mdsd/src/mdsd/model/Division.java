package mdsd.model;

import javax.vecmath.Point2f;
import java.util.ArrayList;
import java.util.List;

public class Division {
    public List<Area> rooms;

    public Division(List<Point2f> shapes) {
        rooms = new ArrayList<>();
        rooms.add(new Area(shapes));
    }

    public void addRoom(Area room) {
        rooms.add(room);
    }
}
