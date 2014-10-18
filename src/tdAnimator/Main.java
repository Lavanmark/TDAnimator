package tdAnimator;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;


/**************************
 *                        *
 * @author Tyler Draughon *
 *                        *
 **************************/


public class Main extends JFrame{

	private static final long serialVersionUID = 1L;
	//DONT MESS WITH THIS STUFF WINDOW AND THREAD RELATED
	static final int UPDATE_RATE = 50;    // number of game update per second
	static final long UPDATE_PERIOD = 1000000000L / UPDATE_RATE;  // nanoseconds
	static final int CANVAS_WIDTH = 800;    // width and height of the game screen
	static final int CANVAS_HEIGHT = 600;
	//JPANEL for drawing
	private GameCanvas canvas;
	private ResourceLoader rl;
	
	/******************************
	 * 
	 * 
	 * PUT THE IMAGES HERE.
	 * so I am assuming you are going to use a sprite SHEET. if not let me know and we can code it different 
	 * 
	 * 
	 ******************************/
	//main sheet
	private BufferedImage spriteSheet;
	//array of animations used to export them etc.
	public Animation[] anims;
	//PUT TOTAL NUMBER OF ANIMATIONS HERE. VERY IMPORTANT
	public int totAnims = 2;
	//make one of these for every animation i.e. run, walk, idle, jump, attack, etc.
	public Animation quickloop;
	public Animation looptwo;
	
	
	//use this for counting ticks for animation and such
	public int ticks = 0;//dont change
	
	//add one of these for every animation you want to see.
	public int controlledStep = 1;
	public int controlledStep2 = 1;
	
	//main function don't need to do anything
	public Main(){
		//initialize classes
		gameInit();
		//set up the window
		canvas.setPreferredSize(new Dimension(CANVAS_WIDTH, CANVAS_HEIGHT));
		this.setContentPane(canvas);
	      // Other UI components such as button, score board, if any.
	      // ......
	      //this.setIconImage(gameIcon);
	      this.setDefaultCloseOperation(EXIT_ON_CLOSE);
	      this.pack();
	      //do most things after packing it
	      this.setLocationRelativeTo(null);
	      this.setTitle("Animator");
	      this.setVisible(true);
	      this.setResizable(false);
	      //Start the game
		gameStart();
	}
	
	private void gameInit(){
		canvas = new GameCanvas();
		rl = new ResourceLoader();
		anims = new Animation[totAnims];
		try {
			//change the file name here to whatever you have.
			spriteSheet = rl.getSheet("squareSheet.png");
		} catch (IOException e) {
			e.printStackTrace();
			log("COULD NOT FIND SHEET!!!");
		}
		//do this for every animation.
		quickloop = new Animation(15,spriteSheet,3,1);
		quickloop.setImg(60, 20, 20, 20);
		quickloop.setImg(80, 20, 20, 20);
		quickloop.setImg(100, 20, 20, 20);
		
		looptwo = new Animation(10,spriteSheet,4,1);
		looptwo.setImg(60, 0, 20, 20);
		looptwo.setImg(80, 0, 20, 20);
		looptwo.setImg(100, 0, 20, 20);
		looptwo.setImg(120, 0, 20, 20);
		
		//for every animation add it to the array once you initialize it.
		anims[0] = quickloop;
		anims[1] = looptwo;
	}
	//DONT EDIT.
	public void gameStart() { 
	      // Create a new thread
	      Thread gameThread =  new Thread() {
	         // Override run() to provide the running behavior of this thread.
	         @Override
	         public void run() {
	            gameLoop();
	         }
	      };
	      // Start the thread. start() calls run(), which in turn calls gameLoop().
	      gameThread.start();
	   }
	
	
	private void gameLoop(){
		//variables for thread
		long beginTime, timeTaken, timeLeft;
		//WHERE STUFF HAPPENS
		while(true){
			//thread work
			beginTime = System.nanoTime();
			//REPAINTS
			repaint();
			//thread work
			timeTaken = System.nanoTime() - beginTime;
	        timeLeft = (UPDATE_PERIOD - timeTaken) / 1000000L;  // in milliseconds
	        if(timeLeft < 10) timeLeft = 6;   // set a minimum
	        ticks++;
	        //sleep the thread
	        try{
	        	// Provides the necessary delay and also yields control so that other thread can do work.
	            Thread.sleep(timeLeft);
	         }catch(InterruptedException ex) { }
		}
	}
	//CALL LOG("ERROR (WHATEVER YOu WANT) TO DEBUG")
	public void log(String error){
		System.out.println(error);
	}
	
	
	private void gameDraw(Graphics2D g2d){
		if(ticks-quickloop.getLast() >= quickloop.getUpdateTime()){
		//add a new case for every new image. put it before the default case
			g2d.drawImage(quickloop.getStepImg(), 50, 50, null);
			quickloop.nextStep();
			quickloop.setLast(ticks);
		}else{
			//if its not changing it still needs to be redrawing everything
			g2d.drawImage(quickloop.getStepImg(), 50, 50, null);

		}
		
		if(ticks-looptwo.getLast() >= looptwo.getUpdateTime()){
		//add a new case for every new image. put it before the default case
			g2d.drawImage(looptwo.getStepImg(), 250, 50, null);
			looptwo.nextStep();
			looptwo.setLast(ticks);
		}else{
			//if its not changing it still needs to be redrawing everything
			g2d.drawImage(looptwo.getStepImg(), 250, 50, null);

		}
		
		
		
		//this will draw the stuff on the right when you press space
		//make one for every animation you want to add.
		switch (controlledStep){
		case 1: 
			g2d.drawImage(quickloop.getStepImg(1), 150, 50, null);
			break;
		case 2:
			g2d.drawImage(quickloop.getStepImg(2), 150, 50, null);
			break;
		case 3:
			g2d.drawImage(quickloop.getStepImg(3), 150, 50, null);
			break;
		default:
			log("made it to default not supposed to");
			g2d.drawImage(quickloop.getStepImg(1), 150, 50, null);
			
		break;
		}
		
		switch (controlledStep2){
		case 1: 
			g2d.drawImage(looptwo.getStepImg(controlledStep2), 350, 50, null);
			break;
		case 2:
			g2d.drawImage(looptwo.getStepImg(controlledStep2), 350, 50, null);
			break;
		case 3:
			g2d.drawImage(looptwo.getStepImg(controlledStep2), 350, 50, null);
			break;
		case 4:
			g2d.drawImage(looptwo.getStepImg(controlledStep2), 350, 50, null);
			break;
		default:
			log("made it to default not supposed to");
			g2d.drawImage(looptwo.getStepImg(), 350, 50, null);
		break;
		}
		//draws the line
		g2d.setColor(Color.WHITE);
		//write one of these to split every drawing
		g2d.drawRect(100, 0, 1, 600);
		g2d.drawRect(200, 0, 1, 600);
		g2d.drawRect(300, 0, 1, 600);
	}

	
	class GameCanvas extends JPanel implements KeyListener{

		private static final long serialVersionUID = 1L;

		
		public GameCanvas() {
	         setFocusable(true);  // so that can receive key-events
	         requestFocus();
	         setResizable(false);
	         addKeyListener(this);
	      }
	   
	      // Override paintComponent to do custom drawing.
	      // Called back by repaint().
	      
	      public void paintComponent(Graphics g) {
	         Graphics2D g2d = (Graphics2D)g;
	         super.paintComponent(g2d);   // paint background
	         setBackground(Color.BLACK);  // may use an image for background
	   
	         // Draw the game objects
	         gameDraw(g2d);
	      }
		
		
		
		
		
		@Override
		public void keyPressed(KeyEvent arg0) {
			//do nothing
		}

		@Override
		public void keyReleased(KeyEvent e) {
			//press this button to increment the image on the right.
			if(e.getKeyCode() == KeyEvent.VK_SPACE){
				//add this for each drawing you want to be stepped through by pressing space
				controlledStep++;
				controlledStep2++;
				if(controlledStep > 3) controlledStep = 1;
				if(controlledStep2 > 4) controlledStep2 = 1;
			}
			if(e.getKeyCode() == KeyEvent.VK_X){
				try {
					rl.saveAnim(anims, "animTry");
				} catch (IOException e1) {
					e1.printStackTrace();
					log("Youuu Can't Do That!");
				}
			}
		}

		@Override
		public void keyTyped(KeyEvent arg0) {
			//do nothing
		}
		
	}
	
	
	
	
	
	public static void main(String[] args) {
		// Use the event dispatch thread to build the UI for thread-safety.
	      SwingUtilities.invokeLater(new Runnable() {
	         @Override
	         public void run() {
	            new Main();
	            
	         }
	      });
	}
}
