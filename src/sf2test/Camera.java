package sf2test;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import sf2test.entity.FighterA;
import sf2test.entity.FighterB;

/**
 *
 * @author leonardo
 */
public class Camera extends Entity {
    
    public Rectangle rectangle = new Rectangle(384, 224);

    private FighterA fighterA;
    private FighterB fighterB;

    public void setFighterA(FighterA fighterA) {
        this.fighterA = fighterA;
    }

    public void setFighterB(FighterB fighterB) {
        this.fighterB = fighterB;
    }
    
    @Override
    public void init() {
        rectangle.x = 192;
        rectangle.y = 0;
    }
    
    @Override
    public void update() {
        if (rectangle.x > fighterA.rectangle.x) {
            rectangle.x = fighterA.rectangle.x;
        }
        else if (rectangle.x > fighterB.rectangle.x) {
            rectangle.x = fighterB.rectangle.x;
        }

        if (rectangle.x + rectangle.width < fighterA.rectangle.x + fighterA.rectangle.width) {
            rectangle.x = fighterA.rectangle.x + fighterA.rectangle.width - rectangle.width;
        }
        else if (rectangle.x + rectangle.width < fighterB.rectangle.x + fighterB.rectangle.width) {
            rectangle.x = fighterB.rectangle.x + fighterB.rectangle.width - rectangle.width;
        }
        
        // update scroll y (if one of the players is jumping)
        if (fighterA.rectangle.y <= fighterB.rectangle.y) {
            double p = 1 - (fighterA.rectangle.y - 10) / 80.0;
            p = p < 0 ? 0 : p > 1 ? 1 : p;
            rectangle.y = (int) (-15 * p);
        }
        else if (fighterB.rectangle.y < fighterA.rectangle.y) {
            double p = 1 - (fighterB.rectangle.y - 10) / 80.0;
            p = p < 0 ? 0 : p > 1 ? 1 : p;
            rectangle.y = (int) (-15 * p);
        }
        
    }

    @Override
    public void draw(Graphics2D g) {
        if (View.DEBUG) {
            g.setColor(Color.RED);
            g.draw(rectangle);
            g.drawLine(rectangle.x + rectangle.width / 2, rectangle.y + rectangle.height / 2, rectangle.x + rectangle.width / 2, rectangle.y + rectangle.height);
        }
    }
    
}
