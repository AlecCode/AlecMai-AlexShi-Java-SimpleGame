//Doodlejump.java
//Alex Shi & Alec Mai
//Simple game designed to model the game 'DoodleJump'.

import java.lang.Math.*;
import java.awt.*;
import java.awt.event.*;
import javax.sound.sampled.*;
import javax.swing.*;
import java.util.*;
import java.io.*;

public class DoodleJump extends JFrame implements ActionListener { //CHANGED UP THE MENU, CLEANER LOOKING, ETC...
	
	javax.swing.Timer myTimer;
	
	JPanel cards;
	CardLayout cLayout = new CardLayout();
	JLayeredPane mPage=new JLayeredPane(); 	// LayeredPane allows my to control what shows on top
	
	JButton scoreBut;
	JButton playBut;
	
	Board game;
	Sound music;
	
	public DoodleJump() {
		super("DoodleJump");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(500,1000);

		setLocationRelativeTo(null); //makes the window appear in the center

		this.myTimer = new javax.swing.Timer(10, this);	 // trigger every 10 ms
		
		game = new Board(this);
		this.add(game);
		
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
		this.cards.add(game, "game");
		//this.cards.add()
		this.add(cards);

		requestFocus();
		setResizable(false);
		setVisible(true);
    }

	public void start(){
		myTimer.start();
	}

	public void actionPerformed(ActionEvent evt){
		Object source = evt.getSource();
		if(source == this.playBut){
		    cLayout.show(this.cards,"game");
		    this.myTimer.start();
		}

		else if(source == this.myTimer) {
			this.game.addNotify();
			this.game.jump();
			this.game.repaint();
			this.game.shoot();
		}
	}

	public static void main(String[] args) {
		DoodleJump frame = new DoodleJump();
	}
}

class Board extends JPanel implements KeyListener, MouseListener{
	private int playX, playY, mx, my, score; //NOTICE: MAKE SCORE INCREASE, currently is only at zero; nothing increasing it rn
	private boolean []keys;
	private DoodleJump mainFrame;
	private ArrayList<Bullet> bullets;
	private boolean shootOnce;
	private Sound pow;

	public Board(DoodleJump m){
		setSize(500,1000);
        keys = new boolean[KeyEvent.KEY_LAST+1];
		mainFrame = m;
		playX = 225;
		playY = 900;
		shootOnce = false;
		bullets = new ArrayList<Bullet>();
		pow = new Sound("Sounds/Silencer.wav");
		score = 0;
        addMouseListener(this);
        addKeyListener(this);
	}

	public void addNotify() {
        super.addNotify();
        requestFocus();
        mainFrame.start();
	}

	public void jump(){
		if(keys[KeyEvent.VK_RIGHT] ){
			if(playX < 440) {
				playX += 4;
			}
			else {}
		}

		if(keys[KeyEvent.VK_LEFT] ){
			if(playX > 0) {
				playX -= 4;
			}
			else {}
		}
	}

	public void shoot() {
		if(keys[KeyEvent.VK_SPACE] ){
			if(shootOnce) {
				bullets.add(new Bullet(playX,playY));
				System.out.println(bullets);
				shootOnce = false;
				pow.play();
			}
		}

		else {
			shootOnce = true;
		}
		
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
    	/*
    	if(e.getButton() == MouseEvent.BUTTON1) {
			mx=e.getX();
	    	my=e.getY();
	    	if(my < playY) {
	    		bullets.add(new Bullet(playX,playY, mx, my));
	    	}
	    	System.out.println(mx);
    	}
    }
    */
    }

    @Override
    public void paintComponent(Graphics g){
    	super.paintComponent(g);
    	// new stuff \/
    	Color myColor = new Color(80,80,80,30);
    	g.setColor(myColor);
    	g.fillRect(0,0,500,100);
        g.setColor(Color.black);
        g.drawLine(0,100,500,100);
        g.setFont(new Font("Comic Sans MS",Font.PLAIN,40));
		g.drawString("score:",20,30);
		g.drawString(String.valueOf(score),20,75);
        // /\
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
	private int bulX, bulY;
	private Rectangle bulletBox;

	public Bullet(int x, int y) {
		bulX = x + 20;
		bulY = y - 20;
		bulletBox = new Rectangle(x,y,10,10);
	}

	public int getBulX() {
		return bulX;
	}

	public int getBulY() {
		return bulY;
	}

	public void setCoord() {
		bulY -= 5;
	}
}

class Sound { //each sound/song is conveted into a 'sound' object
	private Clip myClip;
	
	public Sound(String songName) {
	    try {
	    	// Open an audio input stream
	        File file = new File(songName);
            AudioInputStream ais = AudioSystem.getAudioInputStream(file);
            this.myClip = AudioSystem.getClip();
            this.myClip.open(ais);
	    }
	    catch(IOException e) {
	    	e.printStackTrace(); //displays the specific lines that the error has occured within the program
	    	System.out.println("Do you have the right file?");
	    }
	    catch(UnsupportedAudioFileException e) {
	    	e.printStackTrace();
	    	System.out.println("Unsupported audio file");
	    }
	    catch(LineUnavailableException e) {
	    	e.printStackTrace();
	    	System.out.println("Line is unavailable");
	    }
	}
	
	public void loop() {
    	this.myClip.loop(Clip.LOOP_CONTINUOUSLY); //plays continuously
    }
    
    public void play() {
    	this.myClip.setFramePosition(0); //rewinding the sound/song
    	this.myClip.start();
    }
}