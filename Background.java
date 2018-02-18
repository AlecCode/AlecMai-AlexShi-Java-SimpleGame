package com;

import java.awt.*;

/**
 * Created by Alec on 2018-02-12.
 */
public class Background {
    private int x;
    private int y;

    private Image back;
    private boolean visible;

    public Background(int x, int y){
        this.x = x;
        this.y = y;
        this.visible = true;

        initBackground();
    }

    public void initBackground(){
        Sprite backImg = new Sprite();
        backImg.loadImage("Pictures/cloudsBackground_2.png");
        this.back = backImg.getSpriteImage();
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
    public boolean getVisible(){
        return this.visible;
    }
    public Image getImage(){
        return this.back;
    }
}
