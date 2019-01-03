package mdsd.controller;

public interface Observer {
    /*
     * Get a description of all faults of the rover, if any.
     */

    String[] getFaults();
}
