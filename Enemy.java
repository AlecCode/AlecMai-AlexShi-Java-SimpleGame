package com;

import java.awt.*;
import java.util.ArrayList;

/**
 * Created by Alec on 2018-02-08.
 */
public class Enemy {
    private int x;
    private int y;
    private int dx = 0;
    private int dy = 0;

    private int pathLength;
    private int distTraveled = 0;

    private ArrayList<Sprite> frames = new ArrayList<Sprite>();
    private int currentFrame = 0;

    private boolean visible;

    public Enemy(int x, int y, String direction, int travelLength, int speed){
        this.x = x;
        this.y = y;
        this.pathLength = travelLength;
        this.visible = true;

        if(direction.equalsIgnoreCase("vertical")){
            dy = speed;
        }
        else{
            dx = speed;
        }

        initEnemy();
    }

    public void initEnemy(){
        Sprite alien = new Sprite();
        alien.loadImage("Pictures/monster1.png");
        frames.add(alien);
    }

    public void move(){
        this.x += this.dx;
        this.y += this.dy;
        this.distTraveled = this.distTraveled + this.dx + this.dy;

        if((this.distTraveled + this.dx + this.dy) >= this.pathLength || (this.distTraveled + this.dx + this.dy)*-1 >= this.pathLength ){
            this.dx *= -1;
            this.dy *= -1;

            this.distTraveled = 0;
        }
    }

    public void scroll(int offset){
        this.y -= offset;
    }

    public void setVisible(boolean vis){
        this.visible = vis;
    }

    public int getX(){
        return this.x;
    }
    public int getY(){
        return this.y;
    }
    public Image getImage(){
        return this.frames.get(currentFrame).getSpriteImage();
    }
    public Rectangle getBounds(){
        return new Rectangle(this.x, this.y, frames.get(currentFrame).getSpriteWidth(), frames.get(currentFrame).getSpriteWidth());
    }
    public boolean getVisible(){
        return this.visible;
    }
}
