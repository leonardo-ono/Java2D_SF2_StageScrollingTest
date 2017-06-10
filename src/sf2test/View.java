/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sf2test;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Stroke;
import java.awt.event.KeyEvent;
import java.awt.geom.AffineTransform;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JPanel;
import sf2test.entity.Background;
import sf2test.entity.Background2;
import sf2test.entity.BackgroundFlag;
import sf2test.entity.FighterA;
import sf2test.entity.FighterB;
import sf2test.entity.Floor;
import sf2test.entity.Floor2;
import sf2test.entity.Floor3;
import sf2test.entity.HUD;

/**
 *
 * @author leonardo
 */
public class View extends JPanel {
    
    public static boolean DEBUG = false;
    
    private final Stroke stroke = new BasicStroke(2);
    
    private boolean started = false;
    private List<Entity> entities = new ArrayList<Entity>();
    private Camera camera;
    
    public View() {
        setPreferredSize(new Dimension(900, 600));
        addKeyListener(new Keyboard());
    }

    public void start() {
        createAllEntities();

        for (Entity entity : entities) {
            entity.init();
        }
        
        new Timer().scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                update();
                repaint();
                
                try {
                    Thread.sleep(1);
                } catch (InterruptedException ex) {
                }
            }
        }, 100, 1000 / 60);
        
        started = true;
    }
    
    private void createAllEntities() {
        camera = new Camera();
        
        FighterA fighterA = new FighterA();
        FighterB fighterB = new FighterB();
        
        fighterA.setFighterB(fighterB);
        fighterB.setFighterA(fighterA);
        
        camera.setFighterA(fighterA);
        camera.setFighterB(fighterB);
        
        entities.add(new Background(camera));
        entities.add(new BackgroundFlag(camera));
        entities.add(new Background2(camera));
        entities.add(new Floor(camera));
        entities.add(new Floor2(camera));
        entities.add(new Floor3(camera, 1, 0.38, 140, 0));
        entities.add(new Floor3(camera, 1, 0.38, 140, -175));
        entities.add(fighterA);
        entities.add(fighterB);
        entities.add(fighterA.getFireball());
        entities.add(fighterB.getFireball());
        entities.add(new Floor3(camera, 0, -0.1, 178, 145));
        entities.add(new Floor3(camera, 0, -0.1, 178, -145));
        entities.add(camera);
        entities.add(new HUD(camera));
    }
    
    @Override
    protected void paintComponent(Graphics g) {
        if (!started) {
            return;
        }
        
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        
        AffineTransform at = g2d.getTransform();
        
        if (DEBUG) {
            g.translate(24, 300);
            draw(g2d);
        }
        else {
            g.drawString("Press ENTER to DEBUG", 50, 550);
        }
        
        g2d.setTransform(at);
        
        if (DEBUG) {
            g.drawString("Press ENTER to VIEW", 50, 550);
        }
        
        if (!DEBUG) {
            // g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
            g2d.scale(2, 2);
        }
        g2d.clipRect(0, 0, 384, 224);
        g2d.translate(-camera.rectangle.x, -camera.rectangle.y);
        draw(g2d);
    }
    
    private void update() {
        if (Keyboard.isKeyPressed(KeyEvent.VK_ENTER)) {
            DEBUG = !DEBUG;
        }
        
        for (Entity entity : entities) {
            entity.update();
        }
    }
    
    private void draw(Graphics2D g) {
        g.setStroke(stroke);
        
        g.drawLine(0, 0, 768, 224);
        g.drawLine(768, 0, 0, 224);
        
        for (Entity entity : entities) {
            if (entity.visible) {
                entity.draw(g);
            }
        }
        
        g.setColor(Color.MAGENTA);
        g.drawRect(0, 0 - 15, 768, 224 + 15);
    }
    
}
