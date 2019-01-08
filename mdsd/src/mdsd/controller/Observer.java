package mdsd.controller;

public interface Observer {

    /**
     * Get events from a rover, e.g. faults
     *
     * @param rover the rover which sent the event
     * @param event the event
     */
    void onEvent(IControllableRover rover, Object event);
}
