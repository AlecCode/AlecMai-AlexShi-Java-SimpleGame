//Doodlejump.java
//Alex Shi & Alec Mai
//Simple game designed to model the game 'DoodleJump'.

import java.lang.Math.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.*;
import java.io.*;

public class DoodleJump extends JFrame implements ActionListener {
	JPanel cards;
	CardLayout cLayout = new CardLayout();
	JButton playBut = new JButton(new ImageIcon("Pictures/play.png"));

	javax.swing.Timer myTimer;
	Board game;

	public DoodleJump() {
		super("DoodleJump");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(500,1000);

		setLocationRelativeTo(null); //makes the window appear in the center

		myTimer = new javax.swing.Timer(10, this);	 // trigger every 10 ms
		game = new Board(this);
		add(game);

		//\/ --- Menu Code --- \/
		JLayeredPane mPage=new JLayeredPane(); 	// LayeredPane allows my to control what shows on top
		mPage.setLayout(null); //null layout

		setIconImage(new ImageIcon("Pictures/Doodle_Jump.png").getImage()); //sets the icon image for the game

		playBut.addActionListener(this);
		playBut.setSize(400,100);
		playBut.setLocation(50,600);
		playBut.setContentAreaFilled(false);
		playBut.setFocusPainted(false);
		playBut.setBorderPainted(false);
		mPage.add(playBut,1);
		
		JLabel bg = new JLabel(new ImageIcon("Pictures/grid.png"));
		bg.setSize(500,1000);
		bg.setLocation(0,0);
		mPage.add(bg,2);
		
		//new ImageIcon("Pictures/title.png").getImage().getScaledInstance(400,100, Image.SCALE_DEFAULT);
		JLabel title = new JLabel(new ImageIcon("Pictures/title.png"));
		title.setSize(400,100);
		title.setLocation(50,300);
		mPage.add(title,0);

		cards = new JPanel(cLayout);
		cards.add(mPage, "menu");
		cards.add(game, "game");
		add(cards);

		requestFocus();
		setResizable(false);
		setVisible(true);
    }

	public void start(){
		myTimer.start();
	}

	public void actionPerformed(ActionEvent evt){
		Object source =evt.getSource();
		if(source == playBut){
		    cLayout.show(cards,"game");
		    myTimer.start();
		}

		else if(source == myTimer) {
			game.addNotify();
			game.jump();
			game.repaint();
			game.shoot();
		}
	}

	public static void main(String[] args) {
		DoodleJump frame = new DoodleJump();
	}
}

class Board extends JPanel implements KeyListener, MouseListener{
	private int playX, playY, mx, my; //placeholder values, may delete (playX, playY)
	private boolean []keys;
	private DoodleJump mainFrame;
	private ArrayList<Bullet> bullets;
	private boolean shootOnce;

	public Board(DoodleJump m){
		setSize(500,1000);
        keys = new boolean[KeyEvent.KEY_LAST+1];
		mainFrame = m;
		playX = 225;
		playY = 900;
		shootOnce = false;
		bullets = new ArrayList<Bullet>();
        addMouseListener(this);
        addKeyListener(this);
        //timer.start();
	}

	public void addNotify() {
        super.addNotify();
        requestFocus();
        mainFrame.start();
	}

	public void jump(){
		if(keys[KeyEvent.VK_D] ){
			if(playX < 440) {
				playX += 4;
			}
			else {}
		}

		if(keys[KeyEvent.VK_A] ){
			if(playX > 0) {
				playX -= 4;
			}
			else {}
		}
	}

	public void shoot() {
		/*
		if(keys[KeyEvent.VK_SPACE] ){
			if(shootOnce) {
				bullets.add(new Bullet(playX,playY));
				System.out.println(bullets);
				shootOnce = false;
			}
		}

		else {
			shootOnce = true;
		}
		*/
		for(Bullet bull : bullets) {
			bull.setCoord();
		}
	}

	public void keyTyped(KeyEvent e) {}

    public void keyPressed(KeyEvent e) {
        keys[e.getKeyCode()] = true;
    }

    public void keyReleased(KeyEvent e) {
        keys[e.getKeyCode()] = false;
    }

    public void mouseReleased(MouseEvent e) {}

    public void mousePressed(MouseEvent e) {}

    public void mouseExited(MouseEvent e) {}

    public void mouseEntered(MouseEvent e) {}

    public void mouseClicked(MouseEvent e) {
    	if(e.getButton() == MouseEvent.BUTTON1) {
			mx=e.getX();
	    	my=e.getY();
	    	if(my < playY) {
	    		bullets.add(new Bullet(playX,playY, mx, my));
	    	}
	    	System.out.println(mx);
    	}
    }

    @Override
    public void paintComponent(Graphics g){
    	super.paintComponent(g);
        g.setColor(Color.black);
        g.drawRect(playX,playY, 50,100);
        for(int i=0; i<bullets.size(); i++) {
        	if(playY - bullets.get(i).getBulY() > 900) {
        		bullets.remove(bullets.get(i));
        		i--;
        	}

        	else {
        		g.setColor(Color.yellow);
        		g.fillOval(bullets.get(i).getBulX(),bullets.get(i).getBulY(),10,10);
        		g.setColor(Color.black);
        		g.drawOval(bullets.get(i).getBulX(),bullets.get(i).getBulY(),10,10);
        	}
		}
    }
}

class Doodler { //the player character is an object
	private int x,y,velX,velY;
	private Rectangle hitbox;

	public Doodler(int x, int y, int velX, int velY) {
		this.x = x;
		this.y = y;
		this.velX = velX;
		this.velY = velY;
		hitbox = new Rectangle(x,y,50,100);
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public int getVelX() {
		return velX;
	}

	public int getVelY() {
		return velY;
	}
}

class Bullet { //each bullet is going to become an object
	private int bulX, bulY, startX, startY, mx, my;
	private Rectangle bulletBox;

	public Bullet(int x, int y, int mx, int my) {
		startX = x + 20;
		startY = y - 20;
		bulX = startX;
		bulY = startY;
		this.mx = mx;
		this.my = my;
		bulletBox = new Rectangle(x,y,10,10);
	}

	public int getBulX() {
		return bulX;
	}

	public int getBulY() {
		return bulY;
	}

	public void setCoord() {
		double angle = Math.atan2(my - startY,mx - startX);
		bulY += 4*Math.sin(angle);
		bulX += 4*Math.cos(angle);
	}
}