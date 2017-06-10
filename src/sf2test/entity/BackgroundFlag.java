package sf2test.entity;

import sf2test.Camera;
import sf2test.Entity;

/**
 *
 * @author leo
 */
public class BackgroundFlag extends Entity {
    
    private Camera camera;

    public BackgroundFlag(Camera camera) {
        this.camera = camera;
        y = -15 + 8;
    }
    
    @Override
    public void init() {
        loadCommandFrames("flag", 3);
    }

    @Override
    public void update() {
        setFrame((int) (System.nanoTime() * 0.000000005) % 3);
        x = camera.rectangle.x * 0.5 - 50 + 561;
    }
    
}
