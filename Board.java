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

    private final int B_WIDTH = 500;
    private final int B_HEIGHT = 1000;

    private Timer timer;
    private final int DELAY = 50;

    //placeholder values rn
    private int playX = 0;
    private int playY = 0;

    public Board(){
        initBoard();
    }

    public void initBoard(){
        setSize(400,1000);

        addKeyListener(this);
        setVisible(true);
    }

    @Override
    public void keyPressed(KeyEvent k){

    }
    @Override
    public void keyReleased(KeyEvent k){

    }
    @Override
    public void keyTyped(KeyEvent k){

    }

    @Override
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        g.setColor(new Color(200,200,0));
        g.drawRect(playX,playY, 10,10);
    }
}
