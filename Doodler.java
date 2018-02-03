package com;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 * Created by Alec on 2018-01-23.
 */
public class Doodler extends Sprite implements Commons{

    private int dx = 0;                 //dx holds the direction the player is going in the x-axis
    private double vy = 5;              //vy holds the velocity of the player in the y-axis
                                        //vy is used to simulate a sense of gravity

    private int x = super.getSpriteX(); //x holds the player's position on the x-axis
    private int y = super.getSpriteY(); //y holds the player's position on the y-axis

    public Doodler(int x, int y){
        super(x, y);                    //super(x, y) passes the "x" and the "y" values from the input into the "Sprite" class
        initCharacter();
    }

    public void initCharacter(){             //This loads the sprite image and gets the dimensions
        loadImage("Doodler.png");   //This loads the sprite
        getImageDimensions();               //This gets the dimensions of the sprite
    }

    public void move(){                 //This changes the x and y positions of the player character
        x += dx;
        y += vy;

        //This decreases the velocity in the y-axis to simulate gravity
        vy += 0.04;

        //These if statements prevent the player form moving off-screen
        if(this.x >= S_WIDTH - getWidth()){
            this.x = S_WIDTH - getWidth();
        }
        if(this.x <= 0){
            this.x = 0;
        }

        //This prevents the player from falling out of the bottom of the screen (for testing)
        if(this.y >= S_HEIGHT - getHeight()){
            y = S_HEIGHT - getHeight();
            vy = -5;
        }
    }

    public void collide(){                  //This makes the player change directions
        this.vy = -5;
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
    public int getDx(){
        return dx;
    }
    public double getVy(){
        return vy;
    }
    public Rectangle getBounds(){
        return new Rectangle(this.x, this.y, getWidth(), getHeight());
    }
}
