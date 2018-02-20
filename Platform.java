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

    private int pathLength = 0;
    private int distTraveled = 0;

    private Rectangle bounds;

    private boolean visible;

    public Platform(int x, int y, int width, int height, String direction, int speed){
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.visible = true;

        if(direction.equalsIgnoreCase("horizontal")){
            dx = speed;
        }
        if(direction.equalsIgnoreCase("vertical")){
            dy = speed;
        }
    }

    public void setTravelLength(int l){
        pathLength = l;
    }

    public void move(){
        if(this.pathLength != 0){
            this.x += dx;
            this.y += dy;

            this.distTraveled = this.distTraveled + dx + dy;

            if((this.distTraveled + dx + dy) >= pathLength || (this.distTraveled + dx + dy)*-1 >= pathLength ){
                this.dx *= -1;
                this.dy *= -1;

                this.distTraveled = 0;
            }
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
    public int getWidth(){
        return this.width;
    }
    public int getHeight(){
        return this.height;
    }
    public int getDx() {
        return dx;
    }
    public int getDy() {
        return dy;
    }
    public Rectangle getBounds(){
        return new Rectangle(this.x, this.y, this.width, this.height);
    }
    public boolean getVisible(){
        return this.visible;
    }
}
