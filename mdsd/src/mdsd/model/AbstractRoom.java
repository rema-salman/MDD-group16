package mdsd.model;

import java.util.Set;

import javax.vecmath.Point2f;

public class AbstractRoom extends PhysicalArea {
    AbstractDivision division;

    public AbstractRoom(Set<Point2f> shapes) {
        super(shapes);
    }
    
    public AbstractRoom(Set<Point2f> shapes, AbstractDivision division) {
        super(shapes);
        this.division = division;
    }

}
