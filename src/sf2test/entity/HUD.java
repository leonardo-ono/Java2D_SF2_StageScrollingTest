package sf2test.entity;

import sf2test.Camera;
import sf2test.Entity;

/**
 * static image for testing purposes just to see how it looks
 * 
 * @author leo
 */
public class HUD extends Entity {
    
    private Camera camera;

    public HUD(Camera camera) {
        this.camera = camera;
    }
    
    @Override
    public void init() {
        loadImage("hud.png");
    }

    @Override
    public void update() {
        x = camera.rectangle.x;
        y = camera.rectangle.y;
    }
    
}
