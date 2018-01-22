package com;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyListener;

/**
 * Created by Alec on 2018-01-18.
 */
public class DoodleJump extends JFrame implements ActionListener{

    private static DoodleJump game;
    private Board gameBoard;
    private Timer timer;

    public DoodleJump(){
        super("Doodle Jump");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setSize(400,1000);
        setResizable(false);

        //This makes it such that the screen will appear centered at the top of the monitor
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        //setLocation(dim.width/2-this.getSize().width/2, dim.height/2-this.getSize().height/2);
        setLocation(dim.width/2-this.getSize().width/2, 0);

        gameBoard = new Board();
        add(gameBoard);

        setVisible(true);
        timer = new Timer(10, this);
        timer.start();
    }

    @Override
    public void actionPerformed(ActionEvent evt){
        gameBoard.move();
        gameBoard.repaint();
    }

    public static void main(String[] args){
        game = new DoodleJump();
    }
}
