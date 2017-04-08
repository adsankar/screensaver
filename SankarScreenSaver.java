package graphics;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.concurrent.CopyOnWriteArrayList;

import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 * Animated Screen Saver Program
 * @author Aleksander Sankar
 * Graphics Pd. 1
 * Mr. Fowler
 */
public class SankarScreenSaver extends JPanel implements Runnable{

	//constant fields
	private static final int BOX_WIDTH = 800;
	private static final int BOX_HEIGHT = 800;
	private static final float REDUCTION_FACTOR = 0.6f;
	private static final float BOUNCY_FACTOR = 0.9f;
	private static final int FRAME_RATE = 30; 

	private boolean skip = false;
	
	//list that stores all of the balls
	private CopyOnWriteArrayList<Ball> ballList = new CopyOnWriteArrayList<Ball>();
	private Thread animator;


	/**
	 * The main method creates the frame, initializes a ScreenSaver object, adds it to the frame and displays it.
	 * @param args not used
	 */
	public static void main(String[] args) {

		JFrame frame = new JFrame("A Bouncing Ball Screensaver");//set the title
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setContentPane(new SankarScreenSaver());
		frame.pack();
		frame.setVisible(true);//show the screen saver

	}//end main

	/**
	 * The constructor for the Screensaver object.
	 * It adds a ball to the screen, sets the screen size and begins the thread.
	 */
	public SankarScreenSaver() {

		ballList.add(randomBall(800, 20, 180));
		this.setPreferredSize(new Dimension(BOX_WIDTH, BOX_HEIGHT));
		animator = new Thread(this);
		animator.start();
		this.addMouseListener(new MouseListener() {

			public void mouseClicked(MouseEvent e) {
				//ballList.add(randomBall(800, 10, 180));
				Ball z = randomBall(800, 10, 180);
				z.setXposition(e.getX());
				z.setYposition(e.getY());
				ballList.add(z);
				//ballList.add(new Ball(e.getX(),e.getY(),)
			}

			public void mouseEntered(MouseEvent e){}			
			public void mouseExited(MouseEvent e){}
			public void mousePressed(MouseEvent e){}
			public void mouseReleased(MouseEvent e){}
			
		});
	}//end constructor

	/**
	 * Generates a random ball within the given parameters as limits to the size and velocity and position.
	 * It has a random position, velocity, size, and color.
	 * @param positionBound the maximum position that the ball can be placed
	 * @param maxVelocity the maximum velocity that the ball can initially have
	 * @param maxSize  the maximum size that the ball can have
	 * @return a new ball
	 */
	public Ball randomBall(int positionBound, int maxVelocity, int maxSize){
		return new Ball((float)(Math.random()*positionBound),(float)(Math.random()*positionBound),
				(float)(Math.random()*maxVelocity), (float)(Math.random()*maxVelocity),
				(int)(30+Math.random()*maxSize),
				new Color((int)(Math.random()*255),(int)(Math.random()*255),(int)(Math.random()*255),(int)(Math.random()*255)));
	}//end randomBall
	
	/**
	 * Runs the animation and calls the collision and physics-calculating methods
	 */
	public void run() {
		while (!false) { 

			for (Ball z: ballList){//use the position and velocity to find the new position
				z.setXposition(z.getXposition()+z.getXvelocity());
				z.setYposition(z.getYposition()+z.getYvelocity());
			}//end for-each loop calculating positions
			for (Ball t: ballList){

				if ( t.getXposition() - t.getSize() < 0) {
					t.setXvelocity(-t.getXvelocity()); //reflect if ball hits a wall
					t.setXposition(t.getSize());
					collide();

				} else if ( t.getXposition() + t.getSize()> BOX_WIDTH) {
					t.setXvelocity(-t.getXvelocity());
					t.setXposition(BOX_WIDTH - t.getSize());
					collide();

				}//end if

				if ( t.getYposition() - t.getSize()< 0) {
					t.setYvelocity(-t.getYvelocity());
					t.setYposition(t.getSize());
					collide();

				} else if ( t.getYposition() + t.getSize()/2> BOX_HEIGHT) {
					t.setYvelocity(-t.getYvelocity());
					t.setYposition(BOX_HEIGHT - t.getSize());
					collide();
				}//end if

				if(skip) break;//so that collisions are not called more than once for the same position
			}//
			repaint();
			try {
				Thread.sleep(1000 / FRAME_RATE);  
			} catch (InterruptedException ex) { }
		}//end try-catch
	}//end run

	/**
	 * Draws the picture on the screen.
	 */
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D)g;
		g2.setColor(Color.black);
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g2.fillRect(0,0,BOX_WIDTH, BOX_HEIGHT);
		gravityIncrement();		

		for (Ball t: ballList){//draw each ball in the list
			g2.setColor(t.getBallColor());

			g2.fillOval((int) (t.getXposition() - t.getSize()), (int) (t.getYposition() - t.getSize()),(int)(2*t.getSize()), (int)(2*t.getSize()));

		}//end for-each loop
	}//end paintComponent

	/**
	 * Increments the velocity in the y-direction by using a gravity constant.
	 */
	public void gravityIncrement(){
		for (Ball t: ballList){
			t.setYvelocity(t.getYvelocity()+0.98f);
		}//end for-each loop
	}//end gravityIncrement

	/**
	 * Process for splitting and reducing size and velocity after the ball hits the wall.
	 */
	public void collide(){
		for (Ball t: ballList){
			if (t.getSize()<5){

				ballList.clear();
				ballList.add(randomBall(800, 10, 180));
			}//end if
			t.setXvelocity(t.getXvelocity()*BOUNCY_FACTOR+8);//set velocity again
			t.setYvelocity(t.getYvelocity()*BOUNCY_FACTOR);
			t.setSize((int) (t.getSize()*REDUCTION_FACTOR));//decrease size
			ballList.add(new Ball(t.getXposition(),t.getYposition(),-t.getXvelocity(),t.getYvelocity(),(int)(t.getSize()*REDUCTION_FACTOR),t.getBallColor()));
			ballList.add(new Ball(t.getXposition(),t.getYposition(),t.getXvelocity(),t.getYvelocity(),(int)(t.getSize()*REDUCTION_FACTOR),t.getBallColor()));
			skip = true;
			break;
		}//end for-each loop
	}//end collide

}//end class