import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Random;

public class Board extends JPanel implements KeyListener, MouseListener, ActionListener, Commons {

    private int mX;
    private int mY;

    private Doodler playerSprite;                                       //playerSprite holds the player's character info
    private ArrayList<Platform> platforms = new ArrayList<Platform>();         //platforms holds the platform information of each Platform class
    private ArrayList<Enemy> enemies = new ArrayList<Enemy>();              //enemies holds the enemy information of each Enemy class
    private ArrayList<Bullet> bullets = new ArrayList<Bullet>();
    private ArrayList<Background> backgrounds = new ArrayList<Background>();     //backgrounds holds the background information for each Background class

    private int distanceTraveled = 0;
    private int score = 0;

    private Timer timer;                                                //timer is a Timer

    private boolean gameOver = false;                                   //This holds whether or not the player has lost
    private int offset = 0;

    private Sound shot;

    public Board(){
        initBoard();
    }

    public void initBoard(){
        setSize(S_WIDTH,S_HEIGHT);

        createPlayer();

        createPlatform(10,800, 100,10, "horizontal", 200, 2);
        createPlatform(10,500, 100,10, "horizontal", 0, 0);
        createPlatform(10,200, 100,10, "horizontal", 0 ,0);

        createEnemy(20, 50, "horizontal", 100, 2);

        createBackground(0,0);

        shot = new Sound("Sounds/Silencer.wav");

        addKeyListener(this);
        addMouseListener(this);
        timer = new Timer(DELAY, this);
        timer.start();
        setVisible(true);
    }



    //This creates the "Doodler" player class
    public void createPlayer(){
        playerSprite = new Doodler(S_WIDTH/2, 0);
    }

    //This creates a new "Platform" class
    public void createPlatform(int x, int y, int width, int height, String direction, int travel, int speed){
        //System.out.println("ran");
        Platform p = new Platform(x, y, width, height, direction, speed);
        p.setTravelLength(travel);
        platforms.add(p);
    }
    //This creates a random platform at the top of the screen
    public void createRandomPlatform(){
        Random rand = new Random();

        String newDirection;
        if(rand.nextInt(2) == 1){
            newDirection = "vertical";
        }
        else{
            newDirection = "horizontal";
        }

        int x = rand.nextInt(340);
        int width = rand.nextInt(190)+50;
        int travel = S_WIDTH - x - width;

        //This limits the minimum size of the travel field so no platforms appear to jitter in place
        int speed;
        if(travel < 50 || rand.nextInt(5) % 2 == 0){
            speed = 0;
        }
        else{
            speed = rand.nextInt(3)+2;
        }

        createPlatform(x, -10, width, 10, newDirection, travel, speed);
    }

    //This creates a new "Enemy" class
    public void createEnemy(int x, int y, String direction, int travelLength, int speed){
        Enemy e = new Enemy(x, y, direction, travelLength, speed);
        enemies.add(e);
    }
    public void createRandomEnemy(){
        Random rand = new Random();

        String newDirection;
        if(rand.nextInt(2) == 1){
            newDirection = "vertical";
        }
        else{
            newDirection = "horizontal";
        }

        int x = rand.nextInt(440);
        int travel = rand.nextInt(470)+30;

        //This limits the minimum size of the travel field so no platforms appear to jitter in place
        int speed;
        if(S_WIDTH - x - travel <= 0 || rand.nextInt(5) % 2 == 0){
            speed = 0;
        }
        else{
            speed = rand.nextInt(3)+2;
        }

        createEnemy(x, -50, newDirection, travel, speed);
    }

    public void createBackground(int x, int y){
        Background b = new Background(x, y);
        backgrounds.add(b);
    }

    public void createBullet(int x, int y, int mX, int mY){
        Bullet b = new Bullet(x, y, mX, mY);
        bullets.add(b);
    }



    public void updatePlatforms(){                  //This moves every platform and removes the platforms that are not visible

        ArrayList<Platform> remove = new ArrayList<Platform>(); //remove holds all of the "Platform" objects that are no longer visible

        for(Platform p : platforms){
            p.move();
            if(!p.getVisible()){
                remove.add(p);
            }
        }

        //This removes all of the not visible "Platform" objects that are not visible
        for(Platform p : remove){
            platforms.remove(p);
        }

        remove.clear();
    }

    public void updateEnemies(){                            //This moves every enemy and removes the enemies that are not visible

        ArrayList<Enemy> remove = new ArrayList<Enemy>();   //remove holds all of the "Enemy" objects that are no longer visible

        for(Enemy e : enemies){
            e.move();
            if(!e.getVisible()){
                remove.add(e);
            }
        }

        for(Enemy e : remove){
            enemies.remove(e);
        }

        remove.clear();
    }

    public void updateBackgrounds(){

        ArrayList<Background> remove = new ArrayList<Background>();

        for(int i = 0; i > backgrounds.size(); i++){
            Background b = backgrounds.get(i);

            if(!b.getVisible()){
                remove.add(b);
            }
        }

        for(Background b : remove){
            backgrounds.remove(b);
        }

        remove.clear();

        if(backgrounds.get(backgrounds.size() - 1).getY() > 0){
            createBackground(0, backgrounds.get(backgrounds.size() - 1).getY() - 1000);
        }
    }

    public void updateBullets(){

        ArrayList<Bullet> remove = new ArrayList<Bullet>();

        for(Bullet b : bullets){
            if(!b.getVisible()){
                remove.add(b);
            }
        }

        for(Bullet b : remove){
            bullets.remove(b);
        }

        remove.clear();

        for(Bullet b : bullets){
            b.move();
        }
    }



    public void checkCollisions(){
        //This checks if the player is falling and has hit a platform and causes the player to jump
        for(Platform p : platforms){
            int playerFeet = (playerSprite.getY()+playerSprite.getHeight());
            if(playerSprite.getBounds().intersects(p.getBounds()) && playerFeet > p.getY() && playerFeet < p.getY()+p.getHeight() && playerSprite.getVy() > 0){
                playerSprite.collide();
            }
        }

        //This checks if the player collides with an enemy
        for(Enemy e : enemies){
            if(e.getBounds().intersects(playerSprite.getBounds())){
                gameOver = true;
                System.out.println("Game Over");
            }

            for(Bullet b : bullets){
                if(b.getBounds().intersects(e.getBounds())){
                    e.setVisible(false);
                    score += 500;
                }
            }
        }
    }

    public void scroll(){ //Used to scroll everything on the screen down (except for the player)
        if(playerSprite.getY() <= 200 && playerSprite.getVy() < 0){
            offset = (int)playerSprite.getVy(); //calculating where the player 'should' be numerically and moves objects down by the offset var
            playerSprite.setY(200);

        }
        if(playerSprite.getVy() > 0){ //if the velocity in y direction is greater than 0, offset is 0
            offset = 0;
        }
		
		//Scrolling everything down by the offset
        for(Platform p : platforms){
            p.scroll(offset);
        }

        for(Enemy e : enemies){
            e.scroll(offset);
        }

        for(Background b : backgrounds){
            b.scroll(offset);
        }

        for(Bullet b : bullets){
            b.scroll(offset);
        }
    }

    public void checkOffScreen(){ //Checking if objects are off the player's screen

        if(playerSprite.getY() > S_HEIGHT){ //when the player lower than the screen height, its game over
            this.gameOver = true;
        }

        for(Platform p : platforms){ //when a platform's y-coord is offscreen, it is no longer visisble
            if(p.getY() > 1000){
                p.setVisible(false);
            }
        }

        for(Enemy e : enemies){ //when an enemy's y-coord is offscreen, it is no longer visisble
            if(e.getY() > 1000){
                e.setVisible(false);
            }
        }

        for(Background b : backgrounds){ //when the background's y-coord is offscreen, it is no longer visisble
            if(b.getY() > 1000){
                b.setVisible(false);
            }
        }

        for(Bullet b : bullets){ //checks the bullet's x and y-coord and sets it to non-visisble when off the screen
            if(b.getBulX() < 0 || b.getBulX() > S_WIDTH || b.getBulY() < 0 || b.getBulY() > S_HEIGHT){
                b.setVisible(false);
            }
        }
    }

    public void gameOverScreen() { //used to stop the game when the player dies
        if(gameOver) {
            timer.stop();
        }
    }

    public void setGameOver(boolean game) { //setting the gameOver boolean back to true
        gameOver = game;
    }
	
	//\/ --- Accessor methods to get GameOver boolean and score --- \/
    public boolean getGameOver() {
        return gameOver;
    }
    public int getScore() {
        return score;
    }

    //This updates the gameBoard
    @Override
    public void actionPerformed(ActionEvent evt){
        Random rand = new Random();                     //rand is a random number generator

        requestFocus();                                 //This gets input focus from the computer
        checkOffScreen();                               //This checks for objects that are off the screen and are no longer visible

        //This keeps track of how far the player has gone in the y-axis
        if(playerSprite.getVy() < 0){
            distanceTraveled += playerSprite.getVy();
            score += (playerSprite.getVy() * -1);
        }

        //This creates a new platform at the top of the screen so the play can continue to advance every 200 pixels the player advances
        if(distanceTraveled / 200 < 0 && distanceTraveled != 0){
            createRandomPlatform();

            if(rand.nextInt(5) == 3){
                createRandomEnemy();
            }

            distanceTraveled = 0; //resetting the distance traveled
        }

        playerSprite.move();            //This moves the player
        updatePlatforms();              //This moves all of the platforms & removes platforms that are below the screen
        updateEnemies();                //This moves all of the enemies & removes enemies that are below the screen
        updateBackgrounds();            //This removes the backgrounds that are below the screen
        updateBullets();                //This moves all of the bullets & removes bullets that are offscreen

        checkCollisions();              //This checks for collisions

        gameOverScreen();               //This checks if the player has died or not

        scroll();                       //This scrolls everything on the player's screen down

        repaint();
    }



    //These pass user input into
    @Override
    public void keyPressed(KeyEvent k){
        playerSprite.keyPressed(k);
    }
    @Override
    public void keyReleased(KeyEvent k){
        playerSprite.keyReleased(k);
    }
    @Override
    public void keyTyped(KeyEvent k){}

    @Override
    public void mousePressed(MouseEvent m) {
        playerSprite.mousePressed(m);
    }
    @Override
    public void mouseReleased(MouseEvent m) {
        playerSprite.mouseReleased(m);
    }
    @Override
    public void mouseClicked(MouseEvent m) {
        if(m.getButton() == MouseEvent.BUTTON1) { //when mouse is clicked, it gets the x and y pos of the click
            mX = m.getX();
            mY = m.getY();

            if(mY < playerSprite.getY()) { //if the position of the mouse is above the player, it will create a bullet and shoot
                createBullet(playerSprite.getX(), playerSprite.getY(), mX, mY);
                shot.play(); //plays the sound effect
            }
        }
    }
    @Override
    public void mouseEntered(MouseEvent m) {}
    @Override
    public void mouseExited(MouseEvent m) {}

    @Override
    public void paintComponent(Graphics g){
        super.paintComponent(g);

        for(Background b : backgrounds){ //if the background is visible it will draw the background img
            if(b.getVisible()){
                g.drawImage(b.getImage(), b.getX(), b.getY(), this);
            }
        }

        if(playerSprite.getDirection().equalsIgnoreCase("right") && !playerSprite.getShoot()){ //moves the player when facing right and shooting to avoid jitter
        	g.drawImage(playerSprite.getImage(), playerSprite.getX() - 20, playerSprite.getY(), this);
       	}
       	
        else{
        	g.drawImage(playerSprite.getImage(), playerSprite.getX(), playerSprite.getY(), this);
        }

        g.setColor(Color.black);
        for(Platform p : this.platforms){ //if a platform is visible, it will draw the platform
            if(p.getVisible()){
                g.fillRect(p.getX(), p.getY(), p.getWidth(), p.getHeight());
            }
        }

        for(Enemy e : enemies){ //if an enemy is visible, it will draw the img
            if(e.getVisible()){
                g.drawImage(e.getImage(), e.getX(), e.getY(), this);
            }
        }

        for(Bullet b : bullets){ //if a bullet is visible, it will draw the bullet
            if(b.getVisible()){
                g.setColor(Color.yellow);
                g.fillOval(b.getBulX(),b.getBulY(),10,10);
                g.setColor(Color.black);
                g.drawOval(b.getBulX(),b.getBulY(),10,10);
            }
        }
		//\/ --- Used to display the score that is accumulated over the game --- \/
        g.setColor(Color.black);
        g.setFont(new Font("Comic Sans MS",Font.PLAIN,30));
        g.drawString("Score:",20,30);
        g.drawString(String.valueOf(score),110,30);
    }
    
    public static void main(String[] args){}
}
