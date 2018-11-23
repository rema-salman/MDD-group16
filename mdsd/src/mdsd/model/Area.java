package mdsd.model;

import java.awt.Point;
import java.awt.Shape;

public class Area {
	/**
	 * These shapes define the area, upon calling contains to check if a point
	 * is inside the area these shapes define the area as a whole. If the point
	 * is inside any of these shapes then it is considered to be inside of the
	 * area, unless it is also inside one of the antiShapes
	 */
	private Shape[] shapes;
	/**
	 * These shapes are subtracted from the area for the purposes of collision
	 * detection, if a point is inside any of the normal shapes but also inside
	 * an anti shape then it is considered to not be inside of the area.
	 */
	private Shape[] antiShapes;

	public Area(Shape[] shapes, Shape[] antiShapes)
	{
		this.shapes = shapes;
		this.antiShapes = antiShapes;
	}
	
	//TODO: Implement
	/**
	 * Checks if a given point, p, is inside the area. This is true if the point
	 * is NOT inside any of the anti shapes, but is inside one of the normal
	 * shapes.
	 * 
	 * @param p The point which is being investigated.
	 * @return True if the point is inside the area.
	 */
	public boolean contains(Point p) //Change to point class instead of float?
	{
		//if inside any antiShape: return false
		//if inside any shape: return true
		//return false
		return false;
	}

	//TODO: Implement
	public Shape[] getShapes() {
		return null;
	}

	//TODO: Implement
	public Shape[] getAntiShapes() {
		return null;
	}
}
