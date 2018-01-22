package com;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 * Created by Alec on 2018-01-18.
 */
public class Board extends JPanel implements KeyListener {

    private int HEIGHT = 1000;
    private int WIDTH = 400;
    private int PWIDTH = 25;
    private int PHEIGHT = 25;
    private int dx = 0;
    private int dy = 5;

    private Timer timer;
    private final int DELAY = 50;

    //These hold the player's position on the x and y axis respectively
    private int playerX = 0;
    private int playerY = 0;

    public Board(){
        initBoard();
    }

    public void initBoard(){
        setSize(WIDTH,HEIGHT);

        addKeyListener(this);
        setVisible(true);
    }

    public void move(){
        requestFocus();

        this.playerX += dx;
        this.playerY += dy;

        System.out.println(playerX + " " + (WIDTH - PWIDTH));

        if(playerX >= WIDTH - PWIDTH){
            playerX = WIDTH - PWIDTH;
        }
        if(playerX <= 0){
            playerX = 0;
        }

        if(playerY >= HEIGHT - PHEIGHT - 25){
            playerY = HEIGHT - PHEIGHT - 25;
            dy = -5;
        }
        if(playerY <= 0){
            playerY = 0;
            dy = 5;
        }
    }

    //(Changes direction)
    @Override
    public void keyPressed(KeyEvent k){
        int key = k.getKeyCode();

        if(key == KeyEvent.VK_RIGHT){
            this.dx = 5;
        }
        if(key == KeyEvent.VK_LEFT){
            this.dx = -5;
        }

        /*if(key == KeyEvent.VK_D){
            this.dx = 5;
        }
        if(key == KeyEvent.VK_A){
            this.dx = -5;
        }*/
    }
    //(Resets direction)
    @Override
    public void keyReleased(KeyEvent k){
        int key = k.getKeyCode();

        if(key == KeyEvent.VK_RIGHT && dx > 0){
            dx = 0;
        }
        if(key == KeyEvent.VK_LEFT && dx < 0){
            dx = 0;
        }

        /*if(key == KeyEvent.VK_D && dx > 0){
            dx = 0;
        }
        if(key == KeyEvent.VK_A && dx < 0){
            dx = 0;
        }*/
    }
    @Override
    public void keyTyped(KeyEvent k){

    }

    @Override
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        g.setColor(new Color(0,200,0));
        g.fillRect(playerX, playerY, PWIDTH, PHEIGHT);
    }
}
