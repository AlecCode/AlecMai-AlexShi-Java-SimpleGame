package com;

import java.awt.*;
import java.util.Random;

/**
 * Created by Alec on 2018-02-16.
 */
class Bullet {
    private int x, y, mx, my;
    private double angle;
    private Rectangle bulletBox;
    private boolean visible;

    public Bullet(int x, int y, int mx, int my) {
        this.x = x + 20;
        this.y = y - 20;
        this.mx = mx;
        this.my = my;
        this.visible = true;
        this.angle = Math.atan2(my - (y - 20) ,mx - (x + 20));
    }

    public void move() {
        y += 4*Math.sin(angle);
        x += 4*Math.cos(angle);

        this.bulletBox = new Rectangle(x, y,10,10);
    }

    public void scroll(int offset){
        this.y -= offset;
    }

    public void setVisible(boolean vis){
        this.visible = vis;
    }

    public int getBulX() {
        return x;
    }
    public int getBulY() {
        return y;
    }
    public Rectangle getBounds(){
        return bulletBox;
    }
    public boolean getVisible(){
        return visible;
    }
}