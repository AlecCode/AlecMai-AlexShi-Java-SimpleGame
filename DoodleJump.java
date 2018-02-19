import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;
import java.io.*;

public class DoodleJump extends JFrame implements ActionListener{

    javax.swing.Timer timer;

    JPanel cards;
    CardLayout cLayout = new CardLayout();
    JLayeredPane mPage=new JLayeredPane(); 	// LayeredPane allows my to control what shows on top
    JLayeredPane hPage = new JLayeredPane();
    JLayeredPane gPage = new JLayeredPane();

    JButton scoreBut;
    JButton playBut;
    JButton soundBut;
	JButton backBut;
	JButton confirmBut;
	
	JTextField scoreName;
	
	ImageIcon soundIcon1 = new ImageIcon("Pictures/sound.png"); //The two icons will be used interchangeably to display whether or not the music is muted or not
	ImageIcon soundIcon2 = new ImageIcon("Pictures/mute.png");

    Board gameBoard;
    Sound music;
    
    ArrayList<String> highNames = new ArrayList<String>(); //since the txt file is overwritten each time, the scores/names are kept track in these arrayLists
	ArrayList<Integer> highscore = new ArrayList<Integer>();

    public DoodleJump() {
        super("DoodleJump");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(500,1000);

        setLocationRelativeTo(null); //makes the window appear in the center

        this.timer = new javax.swing.Timer(10, this);	 // trigger every 10 ms

        music = new Sound("Sounds/FINAL_FANTASY_XV_OST_-_Galdin_Quay_Theme.wav");
        music.loop();

        //\/ --- Menu Displays --- \/
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
        
        //Add Sound Button (playing/pausing music)
		this.soundBut = new JButton(soundIcon1);
		this.soundBut.addActionListener(this);
		this.soundBut.setSize(50,50);
		this.soundBut.setLocation(425,900);
		this.soundBut.setContentAreaFilled(false);
		this.soundBut.setFocusPainted(false);
		this.soundBut.setBorderPainted(false);
		this.mPage.add(soundBut,2);

        //Add Background image for menu
        JLabel bg = new JLabel(new ImageIcon("Pictures/grid.png"));
        bg.setSize(500,1000);
        bg.setLocation(0,0);
        mPage.add(bg,3);

        //Add Title
        ImageIcon titleIcon = new ImageIcon("Pictures/title.png"); // load the image to a imageIcon
		Image titleImage = titleIcon.getImage(); // transform it
		Image newImg = titleImage.getScaledInstance(400, 100,  java.awt.Image.SCALE_SMOOTH); // scale it the smooth way
		titleIcon = new ImageIcon(newImg);  // transform it back
		JLabel title = new JLabel(titleIcon);
        title.setSize(400,100);
		title.setLocation(50,100);
		mPage.add(title,0);
		
		//Back Button (Highscores --> Main menu) 
		this.backBut = new JButton(new ImageIcon("Pictures/back.png"));
		this.backBut.addActionListener(this);
		this.backBut.setSize(75,75);
		this.backBut.setLocation(25,25);
		this.backBut.setContentAreaFilled(false);
		this.backBut.setFocusPainted(false);
		this.backBut.setBorderPainted(true);
		this.hPage.add(backBut,1);
		
		//Bg for highscore screen
		JLabel cover = new JLabel(new ImageIcon("Pictures/paper.png"));
        cover.setSize(500,1000);
        cover.setLocation(0,0);
        hPage.add(cover,3);
		
		//JLabels for hPage
		JLabel highImg = new JLabel(new ImageIcon("Pictures/highscores.png"));
		highImg.setSize(300,100);
		highImg.setLocation(150,25);
		this.hPage.add(highImg,1);
		
		//Add Reset Button
        this.confirmBut = new JButton(new ImageIcon("Pictures/confirm.png"));
        this.confirmBut.addActionListener(this);
        this.confirmBut.setSize(300,100);
        this.confirmBut.setLocation(100,800);
        this.confirmBut.setContentAreaFilled(false);
        this.confirmBut.setFocusPainted(false);
        this.confirmBut.setBorderPainted(true);
        this.gPage.add(confirmBut,1);
        
        //Bg for game over screen
        JLabel cover2 = new JLabel(new ImageIcon("Pictures/paper.png"));
        cover2.setSize(500,1000);
        cover2.setLocation(0,0);
        gPage.add(cover2,3);
        
        //JLabels for gPage
        JLabel gameOverImg = new JLabel(new ImageIcon("Pictures/gameover.png"));
        gameOverImg.setSize(450,125);
		gameOverImg.setLocation(25,0);
		this.gPage.add(gameOverImg,1);
		JLabel label = new JLabel("Enter Name (No Spaces)");
		label.setSize(200,50);
		label.setLocation(100,245);
		this.gPage.add(label,2);
		JLabel label2 = new JLabel("Your Score:");
		label2.setSize(200,50);
		label2.setLocation(100,350);
		this.gPage.add(label2,2);
		
		scoreName = new JTextField(); //text field used to recieve the player's name
		this.scoreName.setSize(300,50);
		this.scoreName.setLocation(100,300);
		this.gPage.add(scoreName,1);
        
		
        this.cards = new JPanel(cLayout);
        this.cards.add(mPage, "menu");
        
		this.cards.add(hPage, "highscore");
		this.cards.add(gPage, "endscreen");
		this.add(cards);

        requestFocus();
        setResizable(false);
        setVisible(true);
    }

    public void actionPerformed(ActionEvent evt){
        Object source = evt.getSource();
        if(source == this.playBut){
        	gameBoard = new Board();
        	this.add(gameBoard);
        	this.cards.add(gameBoard, "game");
            cLayout.show(this.cards,"game");
            this.timer.start();
        }
        
        else if(source == this.scoreBut){
        	JLabel rect2 = new JLabel(new ImageIcon("Pictures/yellowPaper.jpg"));
			rect2.setSize(500,600);
			rect2.setLocation(0,200);
			this.hPage.add(rect2,1);
        	
        	try { //Reading txt File
				BufferedReader in = new BufferedReader(new FileReader("highscore.txt"));
				String line;
				while((line = in.readLine()) != null){ //reads a line if it is not nothing
					String[] parts = line.split(" "); //each line will be an array in the form of [string, int]
					this.highNames.add(parts[0]);
					this.highscore.add(Integer.parseInt(parts[1])); //when reading the file, order doesnt matter since the scores/names in the txt or in order when written
				}
			}
		
			catch (IOException e){ //if theres no txt file, it will add filler stats
				for(int i = 0; i < 5; i++){
					this.highNames.add("?????");
					this.highscore.add(0);
				}
			}
			
			for(int i = 0; i < this.highNames.size(); i++){
				System.out.println(this.highNames.get(i));
				System.out.println(this.highscore.get(i));
			}
			
			JLabel name1 = new JLabel("1. " + highNames.get(0)); //Displaying highscores from txt file
			name1.setFont(new Font("Comic Sans ms", Font.PLAIN, 30));
			name1.setSize(400,100);
			name1.setLocation(50,183);
			this.hPage.add(name1,1);
			JLabel score1 = new JLabel(Integer.toString(highscore.get(0)));
			score1.setFont(new Font("Comic Sans ms", Font.PLAIN, 30));
			score1.setSize(400,100);
			score1.setLocation(82,230);
			this.hPage.add(score1,1);
			
			JLabel name2 = new JLabel("2. " + highNames.get(1));
			name2.setFont(new Font("Comic Sans ms", Font.PLAIN, 30));
			name2.setSize(400,100);
			name2.setLocation(50,303);
			this.hPage.add(name2,1);
			JLabel score2 = new JLabel(Integer.toString(highscore.get(1)));
			score2.setFont(new Font("Comic Sans ms", Font.PLAIN, 30));
			score2.setSize(400,100);
			score2.setLocation(85,355);
			this.hPage.add(score2,1);
			
			JLabel name3 = new JLabel("3. " + highNames.get(2));
			name3.setFont(new Font("Comic Sans ms", Font.PLAIN, 30));
			name3.setSize(400,100);
			name3.setLocation(50,425);
			this.hPage.add(name3,1);
			JLabel score3 = new JLabel(Integer.toString(highscore.get(2)));
			score3.setFont(new Font("Comic Sans ms", Font.PLAIN, 30));
			score3.setSize(300,100);
			score3.setLocation(85,475);
			this.hPage.add(score3,1);
			
			JLabel name4 = new JLabel("4. " + highNames.get(3));
			name4.setFont(new Font("Comic Sans ms", Font.PLAIN, 30));
			name4.setSize(400,100);
			name4.setLocation(50,545);
			this.hPage.add(name4,1);
			JLabel score4 = new JLabel(Integer.toString(highscore.get(3)));
			score4.setFont(new Font("Comic Sans ms", Font.PLAIN, 30));
			score4.setSize(400,100);
			score4.setLocation(85,595);
			this.hPage.add(score4,1);
			
			JLabel name5 = new JLabel("5. " + highNames.get(4));
			name5.setFont(new Font("Comic Sans ms", Font.PLAIN, 30));
			name5.setSize(400,100);
			name5.setLocation(50,645);
			this.hPage.add(name5,1);
			JLabel score5 = new JLabel(Integer.toString(highscore.get(4)));
			score5.setFont(new Font("Comic Sans ms", Font.PLAIN, 30));
			score5.setSize(400,100);
			score5.setLocation(85,692);
			this.hPage.add(score5,1);
			
		    cLayout.show(this.cards,"highscore");
		}
		
		else if(source == this.soundBut){
		    music.playPause(); //calls a Sound method to play or pause the music
		    if(music.getIsPlaying()) {
		    	soundBut.setIcon(soundIcon1); //will display a certain icon depending on if the music is playing
		    }
		    
		    else {
		    	soundBut.setIcon(soundIcon2);
		    }
		}
		
		else if(source == this.backBut){
		    cLayout.show(this.cards,"menu");
		}
		
		else if(source == this.confirmBut) {
			//\/ --- Writing to txt file --- \/
			String scoreStr = scoreName.getText(); //getting the score and name from the run after dying
			int scoreNum = gameBoard.getScore();
			
			ArrayList<String> newHighNames = new ArrayList<>(highNames); //creating new arraylist from original arrayLists to keek track of updated scores
			ArrayList<Integer> newHighscore = new ArrayList<>(highscore);
			
			int changePos = 0; //var to keep track which pos in highscore arrays will be changed (pos 0 is #1, pos 4 is #5)
			
			while(true) {
				if(scoreNum <= highscore.get(changePos)) { //when the score is smaller than or equal to the current score, the pos will increase, otherwise it stops the loop
					changePos ++;
					if(changePos >= 4) {
						break;
					}
				}
				else if(scoreNum > highscore.get(changePos)) {
					break;
				}
			}
			
			if(changePos < 4) { //when the position to be changed is not the last element
				for(int i=1; i<5-changePos; i++) {
					newHighNames.set(4-i+1, highNames.get(4-i)); //it will shift the score down starting from the bottom toward the element to be changed
					newHighscore.set(4-i+1, highscore.get(4-i)); //(ex. if changePos = 2 | pos 4 -- > 5, then pos 3 --> pos 4, then pos changePos --> pos3)
				}
			}
			
			if(changePos <= 4) { //if changePos actually makes the highscore array
				newHighNames.set(changePos, scoreStr); //the current element at the position will be replaced with the new one
				newHighscore.set(changePos, scoreNum);
			}
			
			highNames = new ArrayList<>(newHighNames); //since the new scores were copies of the original arrayLists, the originals now need to be updated
			highscore = new ArrayList<>(newHighscore);
			
			try {
				BufferedWriter writer = new BufferedWriter(new FileWriter("highscore.txt"));
				for(int i=0; i<5; i++) { //writing in the txt file, replacing it each time
					writer.write(newHighNames.get(i) + " " + newHighscore.get(i));
					if(i != 5) { //does not create a new line if it is the last recorded score
						writer.newLine();
					}
				}
				writer.close();
			}
			
			catch(IOException e){
				System.out.println("Scores could not be recorded");
			}
			
			cLayout.show(this.cards,"menu");
		}
		
		if(gameBoard.getGameOver()) { //when the doodler dies, the endscreen appears
			cLayout.show(this.cards,"endscreen");
			gameBoard.setGameOver(); //turns the gameover var back to false
			
			JLabel rect = new JLabel(new ImageIcon("Pictures/rect.jpg"));
			rect.setSize(300,100);
			rect.setLocation(100,375);
			this.gPage.add(rect,2);
			
			JLabel label3 = new JLabel(Integer.toString(gameBoard.getScore())); //displaying the score the player accumulates after game over
			label3.setSize(300,100);
			label3.setLocation(100,375);
			label3.setFont(new Font("Comic Sans ms", Font.PLAIN, 40));
			this.gPage.add(label3,2);
		}
    }

    public static void main(String[] args){
        DoodleJump game = new DoodleJump();
    }
}
