package mdsd.model;

import java.awt.*;

public class Obstacle {
    public float x;
    public float y;
    //public float xy2;  // Stop coord in x or y, depending on horizontal
    public float length;  // Positive length in x or y, depending on horizontal
    public boolean horizontal;  // If not horizontal, vertical
    public Color color;

    //public Obstacle(float x, float y, float xy2, boolean horizontal) {
    public Obstacle(float x, float y, float length, boolean horizontal, Color color) {
        this.x = x;
        this.y = y;
        this.length = length;
        this.horizontal = horizontal;
        this.color = color;
    }

    public Obstacle(Obstacle ob) {
        //this(ob.x, ob.y, ob.xy2, ob.horizontal);
        this(ob.x, ob.y, ob.length, ob.horizontal, ob.color);
    }
}
