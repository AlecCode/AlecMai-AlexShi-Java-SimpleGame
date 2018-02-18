package com;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by Alec on 2018-01-18.
 */
public class DoodleJump extends JFrame implements ActionListener{

    javax.swing.Timer timer;

    JPanel cards;
    CardLayout cLayout = new CardLayout();
    JLayeredPane mPage=new JLayeredPane(); 	// LayeredPane allows my to control what shows on top

    JButton scoreBut;
    JButton playBut;

    Board gameBoard;
    Sound music;

    public DoodleJump() {
        super("DoodleJump");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(500,1000);

        setLocationRelativeTo(null); //makes the window appear in the center

        this.timer = new javax.swing.Timer(10, this);	 // trigger every 10 ms

        gameBoard = new Board();
        this.add(gameBoard);

        music = new Sound("Sounds/FINAL_FANTASY_XV_OST_-_Galdin_Quay_Theme.wav");
        music.loop();

        //\/ --- Menu Displays --- \

        setIconImage(new ImageIcon("Pictures/Doodle_Jump.png").getImage()); //sets the icon image for the game

        this.mPage.setLayout(null); //null layout

        //Add Play Button
        this.playBut = new JButton(new ImageIcon("Pictures/play.png"));
        this.playBut.addActionListener(this);
        this.playBut.setSize(300,150);
        this.playBut.setLocation(100,400);
        this.playBut.setContentAreaFilled(false);
        this.playBut.setFocusPainted(false);
        this.playBut.setBorderPainted(false);
        this.mPage.add(playBut,1);

        //Add Highscore Button
        this.scoreBut = new JButton(new ImageIcon("Pictures/highscores.png"));
        this.scoreBut.addActionListener(this);
        this.scoreBut.setSize(300,100);
        this.scoreBut.setLocation(100,575);
        this.scoreBut.setContentAreaFilled(false);
        this.scoreBut.setFocusPainted(false);
        this.scoreBut.setBorderPainted(false);
        this.mPage.add(scoreBut,1);

        //Add Background image
        JLabel bg = new JLabel(new ImageIcon("Pictures/grid.png"));
        bg.setSize(500,1000);
        bg.setLocation(0,0);
        mPage.add(bg,2);

        //Add Title
        ImageIcon imageIcon = new ImageIcon("Pictures/title.png"); // load the image to a imageIcon
        Image image = imageIcon.getImage(); // transform it
        Image newimg = image.getScaledInstance(400, 100,  java.awt.Image.SCALE_SMOOTH); // scale it the smooth way
        imageIcon = new ImageIcon(newimg);  // transform it back
        JLabel title = new JLabel(imageIcon);
        title.setSize(400,100);
        title.setLocation(50,100);
        mPage.add(title,0);


        this.cards = new JPanel(cLayout);
        this.cards.add(mPage, "menu");
        this.cards.add(gameBoard, "game");
        //this.cards.add()

        this.add(cards);

        requestFocus();
        setResizable(false);
        setVisible(true);
    }

    public void start(){
        timer.start();
    }

    public void actionPerformed(ActionEvent evt){
        Object source = evt.getSource();
        if(source == this.playBut){
            cLayout.show(this.cards,"game");
            this.timer.start();
        }
    }

    public static void main(String[] args){
        DoodleJump game = new DoodleJump();
    }
}
