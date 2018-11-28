package mdsd.controller;

public interface Observable {
    void addObserver(Observer observer);

    void removeObserver(Observer observer);

    void reportEvent(Object event);
}
