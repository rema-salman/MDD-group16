package mdsd.controller;

import java.awt.*;
import mdsd.model.Area;

public interface IProcedure {

	/** Calculate the score based on the procedure, the rovers and the areas
	 *
	 * @param roverLocations list of all the rover positions
	 * @param logicalAreas list of all logical ares
	 * @param physicalAreas list of all physical areas
	 * @return score
	 */

    int calculateScore(Point[] roverLocations, Area[] logicalAreas, Area[] physicalAreas);
}
