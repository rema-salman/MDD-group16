package mdsd.model;

public class Obstacle {
    public float x;
    public float y;
    public float length;  // Positive length in x or y, depending on horizontal
    public boolean horizontal;  // If not horizontal, vertical
    
    public Obstacle(float x, float y, float length, boolean horizontal) {
        this.x = x;
        this.y = y;
        this.length = length;
        this.horizontal = horizontal;
    }
    
    public Obstacle(Obstacle ob) {
        this(ob.x, ob.y, ob.length, ob.horizontal);
    }
}
