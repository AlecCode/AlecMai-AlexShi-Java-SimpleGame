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

        playerSprite = new Doodler(S_WIDTH/2, 0);

        Platform p = new Platform(10,800, 100,20, "horizontal");
        p.setTravelLength(200);
        platforms.add(p);

        addKeyListener(this);
        timer = new Timer(DELAY, this);
        timer.start();
        setVisible(true);
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
            //System.out.println(playerSprite.getBounds().toString());
            //System.out.println(playerSprite.getBounds().intersects(r));
            if(playerSprite.getBounds().intersects(p.getBounds()) && playerSprite.getVy() > 0){
                playerSprite.setVy(-5);
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
