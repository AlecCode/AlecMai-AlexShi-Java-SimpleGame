package com;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

/**
 * Created by Alec on 2018-01-23.
 */
public class Doodler implements Commons{

    private int dx = 0;                 //dx holds the direction the player is going in the x-axis
    private double vy = 5;              //vy holds the velocity of the player in the y-axis
                                        //vy is used to simulate a sense of gravity

    private int x;                      //x holds the player's position on the x-axis
    private int y;                      //y holds the player's position on the y-axis

    private ArrayList<Sprite> frames = new ArrayList<Sprite>(); //frames holds each frame of the "doodler" character
    private int currentFrame = 0;                               //currentFrame holds the current frame that is to be displayed
    private String direction = "right";                         //direction holds the direction that the player is facing
    private boolean shoot = false;                              //shoot holds whether or not the player is shooting
    private boolean jump = false;                               //jump holds whether or not the player is going to jump

    public Doodler(int x, int y){
        this.x = x;
        this.y = y;
        initCharacter();
    }

    public void initCharacter(){
        Sprite doodle0 = new Sprite();
        doodle0.loadImage("DoodlerAnimation/doodle_left.png");
        frames.add(doodle0);

        Sprite doodle1 = new Sprite();
        doodle1.loadImage("DoodlerAnimation/doodle_left_jump.png");
        frames.add(doodle1);

        Sprite doodle2 = new Sprite();
        doodle2.loadImage("DoodlerAnimation/doodle_right.png");
        frames.add(doodle2);

        Sprite doodle3 = new Sprite();
        doodle3.loadImage("DoodlerAnimation/doodle_right_jump.png");
        frames.add(doodle3);

        Sprite doodle4 = new Sprite();
        doodle4.loadImage("DoodlerAnimation/doodle_shoot.png");
        frames.add(doodle4);

        Sprite doodle5 = new Sprite();
        doodle5.loadImage("DoodlerAnimation/doodle_shoot_jump.png");
        frames.add(doodle5);
    }

    public void shootProjectile(){
        if(this.shoot){
            //this.shoot = false;
        }
        else{
            //this.shoot = true;
        }
    }

    public void move(){                 //This changes the x and y positions of the player character
        x += dx;
        y += vy;

        vy += 0.04;                     //This decreases the velocity in the y-axis to simulate gravity

        if(vy > 0.4){
            this.jump = true;
        }

        if(dx > 0){
            this.direction = "right";
        }
        else if(dx < 0){
            this.direction = "left";
        }

        if(this.direction.equalsIgnoreCase("right")){
            if(this.jump){
                if(this.shoot){         //if facing right, jumping, and shooting
                    currentFrame = 5;
                }
                else{                   //if facing right, jumping, and not shooting
                    currentFrame = 3;
                }
            }
            else{
                if(this.shoot){         //if facing right, not jumping, shooting
                    currentFrame = 4;
                }
                else{                   //if facing right, not jumping, not shooting
                    currentFrame = 2;
                }
            }
        }
        else{
            if(this.jump){
                if(this.shoot){         //if facing left, jumping, and shooting
                    currentFrame = 5;
                }
                else{                   //if facing left, jumping, and not shooting
                    currentFrame = 1;
                }
            }
            else{
                if(this.shoot){         //if facing left, not jumping, shooting
                    currentFrame = 4;
                }
                else{                   //if facing left, not jumping, not shooting
                    currentFrame = 0;
                }
            }
        }

        Sprite playerFrame = frames.get(currentFrame);

        //These if statements prevent the player form moving off-screen
        if(this.x >= S_WIDTH - playerFrame.getSpriteWidth()){
            this.x = S_WIDTH - playerFrame.getSpriteWidth();
        }
        if(this.x <= 0){
            this.x = 0;
        }

        //This prevents the player from falling out of the bottom of the screen (for testing)
        if(this.y >= S_HEIGHT - playerFrame.getSpriteHeight()){
            y = S_HEIGHT - playerFrame.getSpriteHeight();
            vy = -5;
        }

    }

    public void collide(){                  //This makes the player change directions
        this.vy = -5;

        this.jump = false;
    }

    //This gets input from the player and changes the direction of the character accordingly
    public void keyPressed(KeyEvent k){
        int key = k.getKeyCode();

        if(key == KeyEvent.VK_RIGHT){
            this.dx = 5;
        }
        if(key == KeyEvent.VK_LEFT){
            this.dx = -5;
        }
        if(key == KeyEvent.VK_SPACE){
            this.shoot = true;
        }
    }
    //This resets the direction of the character when the player releases the key
    public void keyReleased(KeyEvent k){
        int key = k.getKeyCode();

        if(key == KeyEvent.VK_RIGHT && dx > 0){
            dx = 0;
        }
        if(key == KeyEvent.VK_LEFT && dx < 0){
            dx = 0;
        }
        if(key == KeyEvent.VK_SPACE){
            this.shoot = false;
        }
    }
    //This is not used
    public void keyTyped(KeyEvent k){}

    //This changes the velocity of the character in the y direction
    public void setVy(double vy){
        this.vy = vy;
    }

    //These are the only required accessor methods
    //Constant accessor methods are inherited from the "Sprite" class
    //The "x" and "y" variable are changed in the "move" method
    //The bounds of the player must be changed as the "x" and/or "y" variables change
    public int getX(){
        return this.x;
    }
    public int getY(){
        return this.y;
    }
    public int getWidth(){
        return frames.get(currentFrame).getSpriteWidth();
    }
    public int getHeight(){
        return frames.get(currentFrame).getSpriteHeight();
    }
    public int getDx(){
        return dx;
    }
    public double getVy(){
        return vy;
    }

    public Image getImage(){
        return frames.get(currentFrame).getSpriteImage();
    }
    public Rectangle getBounds(){
        return new Rectangle(this.x, this.y, frames.get(currentFrame).getSpriteWidth(), frames.get(currentFrame).getSpriteHeight());
    }
}
