package com;

import javax.swing.*;
import java.awt.*;

/**
 * Created by Alec on 2018-01-19.
 */
public class Sprite {

    private int x;
    private int y;
    private int width;
    private int height;
    private boolean visible;
    private Image image;

    public Sprite(int x, int y){
        this.x = x;
        this.y = y;
        this.visible = true;
    }

    public void loadImage(String imgName){
        ImageIcon image = new ImageIcon(imgName);
        this.image = image.getImage();
    }

    public void getImageDimensions(){
        this.width = this.image.getWidth(null);
        this.height = this.image.getHeight(null);
    }

    public void setVisible(boolean visible){
        this.visible = visible;
    }

    //These are accessor methods
    public int getSpriteX(){  return  this.x;     }
    public int getSpriteY(){  return  this.y;     }
    public int getWidth(){  return this.width;  }
    public int getHeight(){ return this.height; }
    public boolean getVisible(){    return this.visible;    }
    public Image getImage(){    return image;   }
    public Rectangle getSpriteBounds(){   return new Rectangle(x, y, width, height);  }
}
