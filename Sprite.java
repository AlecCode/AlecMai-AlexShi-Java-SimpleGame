package com;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

/**
 * Created by Alec on 2018-01-19.
 */
public class Sprite {

    private int width;
    private int height;
    private Image image;

    public Sprite(){
    }

    public void loadImage(String imgName){
        ImageIcon image = new ImageIcon(imgName);
        this.image = image.getImage();

        this.width = this.image.getWidth(null);
        this.height = this.image.getHeight(null);
    }

    //These are accessor methods
    public int getSpriteWidth(){
        return this.width;
    }
    public int getSpriteHeight(){
        return this.height;
    }
    public ArrayList<Integer> getDimensions(){
        ArrayList<Integer> dimensions = new ArrayList<Integer>();
        dimensions.add(this.width);
        dimensions.add(this.height);
        return dimensions;
    }
    public Image getSpriteImage(){
        return this.image;
    }
}
