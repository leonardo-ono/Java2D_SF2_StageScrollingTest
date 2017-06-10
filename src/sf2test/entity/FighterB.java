package sf2test.entity;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import sf2test.Command;
import sf2test.Entity;
import sf2test.Keyboard;
import sf2test.View;

/**
 *
 * @author leonardo
 */
public class FighterB extends Entity {
    
    public Rectangle rectangle = new Rectangle(60, 100);
    public FighterA fighterA;

    private static enum FighterState { IDLE, WALKING, JUMPING, EXECUTING_COMMAND };
    private FighterState fighterState = FighterState.IDLE;
    
    private double jumpDirectionX;
    private double vy;
    
    private int direction;

    private Color color = new Color(0, 0, 255, 128);

    private Command command = new Command();
    private double commandFrameIndex;
    
    private Fireball fireball = new Fireball();
    
    public FighterB() {
        programCommands();
    }

    public Fireball getFireball() {
        return fireball;
    }

    private void programCommands() {
        command.program("hadouken_right", KeyEvent.VK_DOWN, KeyEvent.VK_RIGHT, KeyEvent.VK_U);
        command.program("hadouken_left", KeyEvent.VK_DOWN, KeyEvent.VK_LEFT, KeyEvent.VK_U);
    }
    private FighterState getFighterState() {
        return fighterState;
    }

    private void changeFighterState(FighterState fighterState) {
        if (this.fighterState != fighterState) {
            this.fighterState = fighterState;
            fighterStateChanged(fighterState);
        }
    }
    
    public void setFighterA(FighterA fighterA) {
        this.fighterA = fighterA;
    }
    
    @Override
    public void init() {
        loadCommandFrames("idle", 5);
        loadCommandFrames("walk", 6);
        loadCommandFrames("hadouken", 7);
        
        idle();
        
        rectangle.x = 500;
        rectangle.y = 90;
    }
    
    @Override
    public void update() {
        switch (fighterState) {
            case IDLE: updateIdle(); break;
            case WALKING: updateWalking(); break;
            case JUMPING: updateJumping(); break;
            case EXECUTING_COMMAND: updateExecutingCommand(); break;
        }
        updateDirection();
    }
    
    private void updateDirection() {
        direction = (rectangle.x + rectangle.width / 2) < (fighterA.rectangle.x + fighterA.rectangle.width / 2) ? 1 : 0;
    }
    
    public void updateCommand() {
        if (Keyboard.isKeyPressed(KeyEvent.VK_U)) {
            command.addKey(KeyEvent.VK_U);
        }
        else if (Keyboard.isKeyPressed(KeyEvent.VK_I)) {
            command.addKey(KeyEvent.VK_I);
        }
        else if (Keyboard.isKeyPressed(KeyEvent.VK_O)) {
            command.addKey(KeyEvent.VK_O);
        }
        else if (Keyboard.isKeyPressed(KeyEvent.VK_J)) {
            command.addKey(KeyEvent.VK_J);
        }
        else if (Keyboard.isKeyPressed(KeyEvent.VK_K)) {
            command.addKey(KeyEvent.VK_K);
        }
        else if (Keyboard.isKeyPressed(KeyEvent.VK_L)) {
            command.addKey(KeyEvent.VK_L);
        }

        if (Keyboard.isKeyPressed(KeyEvent.VK_LEFT)) {
            command.addKey(KeyEvent.VK_LEFT);
        }
        else if (Keyboard.isKeyPressed(KeyEvent.VK_RIGHT)) {
            command.addKey(KeyEvent.VK_RIGHT);
        }
        else if (Keyboard.isKeyPressed(KeyEvent.VK_UP)) {
            command.addKey(KeyEvent.VK_UP);
        }
        else if (Keyboard.isKeyPressed(KeyEvent.VK_DOWN)) {
            command.addKey(KeyEvent.VK_DOWN);
        }

        //command.print();
        String commandName = command.check();
        if (!commandName.isEmpty()) {
            System.out.println("command activated: " + commandName);
            executeCommand(commandName);
        }
    }
    
    public void updateExecutingCommand() {
        setFrame((int) commandFrameIndex);
            
        // TODO ? how to do this ? if (hadouken) and frameIndex=4 spawn fireball ?
        if ((int) commandFrameIndex == 4 && direction == 1) {
            fireball.spawn(rectangle.x + rectangle.width / 2 + 40, rectangle.y + rectangle.height / 2 - 20, 4);
        }
        else if ((int) commandFrameIndex == 4 && direction == 0) {
            fireball.spawn(rectangle.x + rectangle.width / 2 - 40, rectangle.y + rectangle.height / 2 - 20, -4);
        }
            
        commandFrameIndex += 0.15;
        if (commandFrameIndex > frames.length) {

            
            idle();
        }
    }
    
    public void updateControl() {
        int v = 2;
        jumpDirectionX = 0;
        if (Keyboard.isKeyDown(KeyEvent.VK_LEFT) && canMove(-v)) {
            rectangle.x -= v;
            jumpDirectionX = -v * 1.2;
            walk();
        }
        else if (Keyboard.isKeyDown(KeyEvent.VK_RIGHT) && canMove(v)) {
            rectangle.x += v;
            jumpDirectionX = v * 1.2;
            walk();
        }
        else if (!Keyboard.isKeyDown(KeyEvent.VK_LEFT) && !Keyboard.isKeyDown(KeyEvent.VK_RIGHT)) {
            idle();
        }

        if (Keyboard.isKeyDown(KeyEvent.VK_UP)) {
            jump();
        }
        limitMovement();
    }
    
    public void updateIdle() {
        setFrame((int) (System.nanoTime() * 0.00000001) % 5);
        updateControl();
        updateCommand();
    }

    public void updateWalking() {
        setFrame((int) (System.nanoTime() * 0.00000001) % 6);
        updateControl();
        updateCommand();
    }
    
    public void updateJumping() {
        if (canMove(jumpDirectionX)) {
            rectangle.x += jumpDirectionX;
        }
        rectangle.y += vy;
        
        if (rectangle.y > 90) {
            rectangle.y = 90;
            idle();
        }
        
        vy += 0.125;
        limitMovement();
    }
    
    private void limitMovement() {
        if (rectangle.x < 0) {
            rectangle.x = 0;
        }
        else if (rectangle.x > 768 - rectangle.width) {
            rectangle.x = 768 - rectangle.width;
        }        
    }
    
    private boolean canMove(double dx) {
        return Math.abs(rectangle.x - fighterA.rectangle.x + dx) <= 384 - fighterA.rectangle.width;
    }
    
    @Override
    public void draw(Graphics2D g) {
        if (View.DEBUG) {
            g.setColor(color);
            g.fill(rectangle);
        }
        
        if (direction == 1) {
            g.drawImage(image, rectangle.x, rectangle.y + 7, rectangle.x + image.getWidth(), rectangle.y + 7 + image.getHeight(), 0, 0, image.getWidth(), image.getHeight(), null);
        }
        else { // flip image
            g.drawImage(image, rectangle.x, rectangle.y + 7, rectangle.x + image.getWidth(), rectangle.y + 7 + image.getHeight(), image.getWidth(), 0, 0, image.getHeight(), null);
        }
    }

    private void fighterStateChanged(FighterState newFighterState) {
    }
    
    public void idle() {
        setCommandFrame("idle");
        changeFighterState(FighterState.IDLE);
    }
    
    public void walk() {
        if (fighterState != FighterState.WALKING) {
            setCommandFrame("walk");
            changeFighterState(FighterState.WALKING);
        }
    }
    
    public void jump() {
        vy = -4;
        changeFighterState(FighterState.JUMPING);
    }
    
    public void executeCommand(String command) {
        command = command.substring(0, command.indexOf("_"));
        setCommandFrame(command);
        commandFrameIndex = 0;
        changeFighterState(FighterState.EXECUTING_COMMAND);
    }
    
}
