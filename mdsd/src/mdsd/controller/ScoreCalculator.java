package mdsd.controller;

public interface ScoreCalculator {
    int getScore();

    void pause();

    void resume();

    void stop();
}
