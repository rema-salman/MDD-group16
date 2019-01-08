package mdsd.controller;

public interface Observable {

    /**
     * Adds an observer to the rover
     *
     * @param observer the observer to add
     */
    void addObserver(Observer observer);

    /**
     * Removes an observer from the rover
     *
     * @param observer the observer to remove
     */
    void removeObserver(Observer observer);

    /**
     * Reports an event to all added observers
     *
     * @param event the event to send to all observers
     */
    void reportEvent(Object event);

}
