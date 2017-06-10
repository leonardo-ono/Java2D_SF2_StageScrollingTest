package sf2test.entity;

import java.awt.Graphics2D;
import sf2test.Entity;

/**
 *
 * @author leo
 */
public class Fireball extends Entity {
    
    private double dx;
    
    public Fireball() {
    }
    
    @Override
    public void init() {
        loadCommandFrames("fireball", 6);
        visible = false;
    }

    @Override
    public void update() {
        setFrame((int) (System.nanoTime() * 0.00000002) % frames.length);
        x += dx;
        if (x < image.getWidth() || x > 768 ) {
            visible = false;
        }
    }
    
    public void spawn(int x, int y, double dx) {
        this.x = x;
        this.y = y;
        this.dx = dx;
        visible = true;
    }

    @Override
    public void draw(Graphics2D g) {
        if (dx > 0) {
            g.drawImage(image, (int) x, (int) y, (int) (x + image.getWidth()), (int) (y + image.getHeight()), 0, 0, image.getWidth(), image.getHeight(), null);
        }
        else { // flip image
            g.drawImage(image, (int) x, (int) y, (int) (x + image.getWidth()), (int) (y + image.getHeight()), image.getWidth(), 0, 0, image.getHeight(), null);
        }
    }
    
    
    
}
