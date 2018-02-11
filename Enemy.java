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

    public Enemy(int x, int y, String direction, int travelLength, int speed){
        this.x = x;
        this.y = y;
        this.pathLength = travelLength;

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
        alien.loadImage("alien.png");
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
}
