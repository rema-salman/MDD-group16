package mdsd.model;

public class Environment {
    private Area[] physicalAreas;
    private Area[] logicalAreas;

    // int id; //To be used if we have multiple areas and want an identifier.

    public Environment(Area[] physicalAreas, Area[] logicalAreas) {
        this.physicalAreas = physicalAreas;
        this.logicalAreas = logicalAreas;
    }

    /**
     * Returns the physical areas that make up the environment
     *
     * @return An array containing all the physical areas
     */
    public Area[] getPhysicalAreas() {
        return null;
    }

    /**
     * Returns the logical areas that make up the environment
     *
     * @return An array containing all the logical areas
     */
    public Area[] getLogicalAreas() {
        return null;
    }
}
