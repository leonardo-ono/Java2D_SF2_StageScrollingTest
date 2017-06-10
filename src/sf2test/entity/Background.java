package sf2test.entity;

import sf2test.Camera;
import sf2test.Entity;

/**
 *
 * @author leo
 */
public class Background extends Entity {
    
    private Camera camera;

    public Background(Camera camera) {
        this.camera = camera;
        y = -15;
    }
    
    @Override
    public void init() {
        loadImage("stage_ken_background.png");
    }

    @Override
    public void update() {
        // x = camera.rectangle.x * 0.38 - 100;
        x = camera.rectangle.x * 0.5 - 50;
    }
    
}
