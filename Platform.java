package com;

import java.awt.*;

/**
 * Created by Alec on 2018-02-02.
 */
public class Platform {

    private int dx;
    private int dy;

    private int x;
    private int y;
    private int width;
    private int height;

    private int dist;
    private int distTraveled;

    private Rectangle bounds;

    public Platform(int x, int y, int width, int height, String direction){
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;

        if(direction.equalsIgnoreCase("horizontal")){
            dx = 2;
        }
        if(direction.equalsIgnoreCase("vertical")){
            dy = 2;
        }
    }

    public void setTravelLength(int l){
        dist = l;
    }

    public void move(){
        this.x += dx;
        this.y += dy;

        System.out.println(dx + " " + dy);

        this.distTraveled = this.distTraveled + dx + dy;

        if((this.distTraveled + dx + dy) >= dist || (this.distTraveled + dx + dy)*-1 >= dist ){
            this.dx *= -1;
            this.dy *= -1;

            this.distTraveled = 0;
        }
    }

    public int getX(){
        return this.x;
    }
    public int getY(){
        return this.y;
    }
    public int getWidth(){
        return this.width;
    }
    public int getHeight(){
        return this.height;
    }
    public Rectangle getBounds(){
        return new Rectangle(this.x, this.y, this.width, this.height);
    }
}
