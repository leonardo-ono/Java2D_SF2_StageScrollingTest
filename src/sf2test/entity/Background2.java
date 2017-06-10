package sf2test.entity;

import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.image.BufferedImage;
import sf2test.Camera;
import sf2test.Entity;

/**
 *
 * @author leo
 */
public class Background2 extends Entity {
    
    private Camera camera;

    private final Point positionBoatA = new Point(132, 99);
    private final Point positionBoatB = new Point(196, 99);
    private final Point positionBoatC = new Point(228, 99);
    private final Point positionBoatD = new Point(292, 91);
    private final Point positionBoatE = new Point(92, 19);
    private final Point positionBoatF = new Point(132, 19);
    
    private BufferedImage background;
    
    public Background2(Camera camera) {
        this.camera = camera;
        y = -15;
    }
    
    @Override
    public void init() {
        loadCommandFrames("boat_a", 3);
        loadCommandFrames("boat_b", 3);
        loadCommandFrames("boat_c", 2);
        loadCommandFrames("boat_d", 3);
        loadCommandFrames("boat_e", 3);
        loadCommandFrames("boat_f", 3);
        loadImage("stage_ken_background2.png");
        background = image;
    }

    @Override
    public void update() {
        // x = camera.rectangle.x * 0.38 - 100;
        x = camera.rectangle.x * 0.38 - 10;
        y = -20 + 5 * Math.sin(System.nanoTime() * 0.000000002);
    }

    @Override
    public void draw(Graphics2D g) {
        image = background;
        super.draw(g); 
        
        drawAnimation(g, "boat_a", positionBoatA);
        drawAnimation(g, "boat_b", positionBoatB);
        drawAnimation(g, "boat_c", positionBoatC);
        drawAnimation(g, "boat_d", positionBoatD);
        drawAnimation(g, "boat_e", positionBoatE);
        drawAnimation(g, "boat_f", positionBoatF);
    }
    
    private void drawAnimation(Graphics2D g, String name, Point position) {
        setCommandFrame(name);
        setFrame((int) (System.nanoTime() * 0.000000005) % frames.length);
        g.drawImage(image, (int) (x + position.x), (int) (y + position.y), null);
    }
    
}
