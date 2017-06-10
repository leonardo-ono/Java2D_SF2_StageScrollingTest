package sf2test.entity;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.geom.AffineTransform;
import sf2test.Camera;
import sf2test.Entity;
import sf2test.Keyboard;
import sf2test.View;

/**
 *
 * @author leo
 */
public class Floor extends Entity {
    
    private Camera camera;
    private double q;
    
    public Floor(Camera camera) {
        this.camera = camera;
    }
    
    @Override
    public void init() {
        loadImage("stage_ken_floor.png");
        y = 0;
    }

    @Override
    public void update() {
        if (Keyboard.isKeyDown(KeyEvent.VK_1)) {
            q = Math.toRadians(45);
        }
        else if (Keyboard.isKeyDown(KeyEvent.VK_2)) {
            q = Math.toRadians(45);
        }
    }

    @Override
    public void draw(Graphics2D g) {
        if (image == null) {
            return;
        }
        AffineTransform at = g.getTransform();
        
        g.translate(384 + camera.rectangle.x - 192, 89);
        
        //q = Math.atan2(192 - camera.rectangle.x, 100.0);
        g.shear((192 - camera.rectangle.x) * 0.01, 0);
        
        if (View.DEBUG) {
            g.setColor(Color.YELLOW);
            g.drawLine(0, 0, 0, 200);
        }
        
        g.drawImage(image, -image.getWidth() / 2, 62, null);
        //super.draw(g);
        
        g.setTransform(at);
    }
    
}
