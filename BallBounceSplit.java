package graphics;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.util.concurrent.CopyOnWriteArrayList;

import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 * 
 * @author Aleksander
 *
 */
public class BallBounceSplit extends JPanel implements Runnable{
	//TODO heavy cleanup, docs, styles

	private static final int BOX_WIDTH = 800;
	private static final int BOX_HEIGHT = 800;
	private static final float REDUCTION_FACTOR = 0.6f;
	private static final float BOUNCY_FACTOR = 0.9f;
	private static final int FRAME_RATE = 30; 

	private boolean skip = false;
	private CopyOnWriteArrayList<Ball> b = new CopyOnWriteArrayList<Ball>();
	private Thread animator;


	/**
	 * 
	 * @param args
	 */
	public static void main(String[] args) {

		JFrame frame = new JFrame("A Bouncing Ball Screensaver");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setContentPane(new BallBounceSplit());
		frame.pack();
		frame.setVisible(true);

	}//

	/**
	 * 
	 */
	public BallBounceSplit() {

		b.add(randomBall(800, 20, 180));
		this.setPreferredSize(new Dimension(BOX_WIDTH, BOX_HEIGHT));
		animator = new Thread(this);
		animator.start();
	}//

	/**
	 * 
	 * @param positionBound
	 * @param maxVelocity
	 * @param maxSize
	 * @return
	 */
	public Ball randomBall(int positionBound, int maxVelocity, int maxSize){
		return new Ball((float)(Math.random()*positionBound),(float)(Math.random()*positionBound),
				(float)(Math.random()*maxVelocity), (float)(Math.random()*maxVelocity),
				(int)(30+Math.random()*maxSize),
				new Color((int)(Math.random()*255),(int)(Math.random()*255),(int)(Math.random()*255),(int)(Math.random()*255)));
	}//
	/**
	 * 
	 */
	public void run() {
		while (!false) { 

			for (Ball z: b){
				z.setXposition(z.getXposition()+z.getXvelocity());
				z.setYposition(z.getYposition()+z.getYvelocity());
			}//
			for (Ball t: b){

				if ( t.getXposition() - t.getSize() < 0) {
					t.setXvelocity(-t.getXvelocity()); 
					t.setXposition(t.getSize());
					collide();

				} else if ( t.getXposition() + t.getSize()> BOX_WIDTH) {
					t.setXvelocity(-t.getXvelocity());
					t.setXposition(BOX_WIDTH - t.getSize());
					collide();

				}//

				if ( t.getYposition() - t.getSize()< 0) {
					t.setYvelocity(-t.getYvelocity());
					t.setYposition(t.getSize());
					collide();

				} else if ( t.getYposition() + t.getSize()/2> BOX_HEIGHT) {
					t.setYvelocity(-t.getYvelocity());
					t.setYposition(BOX_HEIGHT - t.getSize());
					collide();
				}//

				if(skip) break;
			}//
			repaint();
			try {
				Thread.sleep(1000 / FRAME_RATE);  
			} catch (InterruptedException ex) { }
		}//
	}//

	/**
	 * 
	 */
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.setColor(Color.black);
		g.fillRect(0,0,BOX_WIDTH, BOX_HEIGHT);
		gravityIncrement();		

		for (Ball t: b){
			g.setColor(t.getBallColor());

			g.fillOval((int) (t.getXposition() - t.getSize()), (int) (t.getYposition() - t.getSize()),(int)(2*t.getSize()), (int)(2*t.getSize()));

		}//
	}//

	/**
	 * 
	 */
	public void gravityIncrement(){
		for (Ball t: b){
			t.setYvelocity(t.getYvelocity()+0.98f);
		}//
	}//

	/**
	 * 
	 */
	public void collide(){
		for (Ball t: b){
			if (t.getSize()<5){

				b.clear();
				b.add(randomBall(800, 10, 180));
			}//
			t.setXvelocity(t.getXvelocity()*BOUNCY_FACTOR+8);
			t.setYvelocity(t.getYvelocity()*BOUNCY_FACTOR);
			t.setSize((int) (t.getSize()*REDUCTION_FACTOR));
			b.add(new Ball(t.getXposition(),t.getYposition(),-t.getXvelocity(),t.getYvelocity(),(int)(t.getSize()*REDUCTION_FACTOR),t.getBallColor()));
			b.add(new Ball(t.getXposition(),t.getYposition(),t.getXvelocity(),t.getYvelocity(),(int)(t.getSize()*REDUCTION_FACTOR),t.getBallColor()));
			skip = true;
			break;
		}//
	}//

}//