package sf2test.entity;

import sf2test.Camera;
import sf2test.Entity;

/**
 *
 * @author leo
 */
public class Floor3 extends Entity {
    
    private Camera camera;
    private int id;
    private double p;
    private int dx;
    
    public Floor3(Camera camera, int id, double p, int y, int dx) {
        this.camera = camera;
        this.id = id;
        this.p = p;
        this.y = y;
        this.dx = dx;
    }
    
    @Override
    public void init() {
        loadImage("floor_a_" + id + ".png");
    }

    @Override
    public void update() {
        x = camera.rectangle.x * p + 384 + dx;
    }
    
}
