package sf2test;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;

/**
 *
 * @author leonardo
 */
public class Entity {

    protected BufferedImage image;
    protected double x, y;
    protected BufferedImage[] frames;
    protected Map<String, BufferedImage[]> commandFrames = new HashMap<String, BufferedImage[]>();
    protected boolean visible = true;
    
    public void init() {
    }
    
    public void update() {
    }

    public void draw(Graphics2D g) {
        if (image != null) {
            g.drawImage(image, (int) x, (int) y, null);
        }
    }
    
    protected void loadImage(String resource) {
        try {
            image = ImageIO.read(getClass().getResourceAsStream("/res/" + resource));
        } catch (IOException ex) {
            Logger.getLogger(Entity.class.getName()).log(Level.SEVERE, null, ex);
            System.exit(-1);
        }
    }
    
    protected void loadFrames(String ... resources) {
        frames = new BufferedImage[resources.length];
        for (int i = 0; i < frames.length; i++) {
            loadImage(resources[i]);
            frames[i] = image;
        }
    }
    
    protected void setFrame(int i) {
        image = frames[i];
    }
    
    protected void loadCommandFrames(String name, int maxFrames) {
        String[] names = new String[maxFrames];
        for (int n = 0; n < maxFrames; n++) {
            names[n] = name + "_" + n + ".png";
        }
        loadFrames(names);
        commandFrames.put(name, frames);
    }
    
    protected void setCommandFrame(String name) {
        frames = commandFrames.get(name);
    }
    
}
