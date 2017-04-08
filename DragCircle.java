package graphics;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class DragCircle extends JPanel implements Runnable{
	//TODO heavy cleanup, docs, styles
	private static final int BOX_WIDTH = 800;
	private static final int BOX_HEIGHT = 800;
	private static final float REDUCTION_FACTOR = 0.7f;
	private static final float BOUNCY_FACTOR = 0.9f;


	// Ball's properties
	private float ballRadius = 100; // Ball's radius
	private float ballX = ballRadius + 50; // Ball's center (x, y)
	private float ballY = ballRadius + 20; 
	private float ballSpeedX = 20;   // Ball's speed for x and y
	private float ballSpeedY = 10;

	private static final int UPDATE_RATE = 30; // Number of refresh per second
	private Thread animator;

	/** Constructor to create the UI components and init game objects. */
	public DragCircle() {
		this.setPreferredSize(new Dimension(BOX_WIDTH, BOX_HEIGHT));
		animator = new Thread(this);
		animator.start();
	}
	// Start the ball bouncing (in its own thread)

	public void run() {
		while (true) { // Execute one update step
			// Calculate the ball's new position
			ballX += ballSpeedX;
			ballY += ballSpeedY;

			// Check if the ball moves over the bounds
			// If so, adjust the position and speed.
			if (ballX - ballRadius < 0) {
				ballSpeedX = -ballSpeedX; // Reflect along normal
				ballX = ballRadius; // Re-position the ball at the edge
				collide();
			} else if (ballX + ballRadius > BOX_WIDTH) {
				ballSpeedX = -ballSpeedX;
				ballX = BOX_WIDTH - ballRadius;
				collide();
			}
			// May cross both x and y bounds
			if (ballY - ballRadius < 0) {
				ballSpeedY = -ballSpeedY;
				ballY = ballRadius;
				collide();
			} else if (ballY + ballRadius > BOX_HEIGHT) {
				ballSpeedY = -ballSpeedY;
				ballY = BOX_HEIGHT - ballRadius;
				collide();
			}
			// Refresh the display
			repaint(); // Callback paintComponent()
			// Delay for timing control and give other threads a chance
			try {
				Thread.sleep(1000 / UPDATE_RATE);  // milliseconds
			} catch (InterruptedException ex) { }
		}

	}

	/** Custom rendering codes for drawing the JPanel */
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);    // Paint background
		gravityIncrement();
		// Draw the box
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, BOX_WIDTH, BOX_HEIGHT);

		// Draw the ball
		g.setColor(Color.BLUE);
		g.fillOval((int) (ballX - ballRadius), (int) (ballY - ballRadius),
				(int)(2 * ballRadius), (int)(2 * ballRadius));
	}

	/** main program (entry point) */
	public static void main(String[] args) {
		// Run GUI in the Event Dispatcher Thread (EDT) instead of main thread.

		// Set up main window (using Swing's Jframe)
		JFrame frame = new JFrame("A Bouncing Ball Screensaver");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setContentPane(new DragCircle());
		frame.pack();
		frame.setVisible(true);


	}

	public void gravityIncrement(){
		ballSpeedY+=0.98;
	}

	public void collide(){
		ballSpeedX *= BOUNCY_FACTOR;
		ballSpeedY *= BOUNCY_FACTOR;
		ballRadius *= REDUCTION_FACTOR;
		if (ballRadius<01){
			ballSpeedX = (int)(Math.random()*20);
			ballSpeedY = (int)(Math.random()*20);
			ballX = (int)(Math.random()*(BOX_WIDTH-ballRadius));
			ballY = (int)(Math.random()*(BOX_HEIGHT-ballRadius));
			ballRadius = 100;
		}
	}

}
