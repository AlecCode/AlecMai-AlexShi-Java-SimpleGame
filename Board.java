package com;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;

/**
 * Created by Alec on 2018-01-18.
 */
public class Board extends JPanel implements KeyListener, ActionListener, Commons {

    private Doodler playerSprite;     //playerSprite holds the player's character info
    private ArrayList<Platform> platforms = new ArrayList<Platform>();

    private Timer timer;

    public Board(){
        initBoard();
    }

    public void initBoard(){
        setSize(S_WIDTH,S_HEIGHT);

        initPlayer();

        initPlatform(10,800, 100,20, "horizontal", 200, 2);
        initPlatform(10,500, 100,20, "horizontal", 0, 0);
        initPlatform(10,200, 100,20, "horizontal", 0 ,0);
        /*Platform p = new Platform(10,800, 100,20, "horizontal");
        p.setTravelLength(200);
        platforms.add(p);
        Platform t = new Platform(10,500, 100,20, "horizontal");
        //p.setTravelLength(200);
        platforms.add(t);
        Platform r = new Platform(10,200, 100,20, "horizontal");
        platforms.add(r);*/

        addKeyListener(this);
        timer = new Timer(DELAY, this);
        timer.start();
        setVisible(true);
    }

    public void initPlayer(){
        playerSprite = new Doodler(S_WIDTH/2, 0);
    }

    public void initPlatform(int x, int y, int width, int height, String direction, int travel, int speed){
        Platform p = new Platform(x, y, width, height, direction, speed);
        p.setTravelLength(travel);
        platforms.add(p);
    }

    @Override
    public void actionPerformed(ActionEvent evt){
        requestFocus();
        movePlayer();
        updatePlatforms();
        repaint();
    }

    public void movePlayer(){
        playerSprite.move();

        for(Platform p : platforms){
            int playerFeet = (playerSprite.getY()+playerSprite.getHeight());
            if(playerSprite.getBounds().intersects(p.getBounds()) && playerFeet > p.getY() && playerFeet < p.getY()+p.getHeight() && playerSprite.getVy() > 0){
                playerSprite.collide();
            }
        }
    }

    public void updatePlatforms(){
        for(Platform p : this.platforms){
            p.move();
        }
    }

    //This passes the pressed key into the "playerSprite"
    @Override
    public void keyPressed(KeyEvent k){
        playerSprite.keyPressed(k);
    }
    //This passes the pressed key into the "playerSprite"
    @Override
    public void keyReleased(KeyEvent k){
        playerSprite.keyReleased(k);
    }
    @Override
    public void keyTyped(KeyEvent k){

    }

    @Override
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        //g.setColor(new Color(0,200,0));
        //
        g.drawImage(playerSprite.getImage(), playerSprite.getX(), playerSprite.getY(), this);
        g.setColor(new Color(0,0,0));
        for(Platform p : this.platforms){
            g.fillRect(p.getX(), p.getY(), p.getWidth(), p.getHeight());
        }
    }
}
