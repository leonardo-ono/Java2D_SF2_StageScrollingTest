package sf2test.entity;

import sf2test.Camera;
import sf2test.Entity;

/**
 *
 * @author leo
 */
public class Floor2 extends Entity {
    
    private Camera camera;

    public Floor2(Camera camera) {
        this.camera = camera;
    }
    
    @Override
    public void init() {
        loadImage("stage_ken_floor2.png");
        y = 204;
    }

    @Override
    public void update() {
        x = camera.rectangle.x * -0.15;
    }
    
}
