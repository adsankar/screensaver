package graphics;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.Timer;

public class DragBall extends JPanel implements MouseListener, MouseMotionListener, WindowListener {

	/**
	 * @param args
	 */

	private static final int BALL_DIAMETER = 40; 
	private static final int BOUNCY_FACTOR = 3;

	private int screenX = 800;
	private int screenY = 800;
	private int ground = screenY - 20;

	private int xPosition= ground/2;
	private int yPosition = ground - BALL_DIAMETER;
	private final double GRAVITY = -.098;
	private double yVelocity;

	private int dragX = 0;    
	private int dragY = 0;    


	private boolean canDrag  = false;



	public DragBall()
	{
		setPreferredSize(new Dimension(screenX, screenY));
		setBackground(Color.darkGray);
		setForeground(Color.darkGray);

		this.addMouseListener(this);
		this.addMouseMotionListener(this);
	}

	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);  
		g.setColor (Color.green);
		g.fillRect (0, 780, 800, 50 );
		g.setColor (Color.black);
		
		g.fillOval(xPosition, yPosition, BALL_DIAMETER, BALL_DIAMETER);

	}

	public void mousePressed(MouseEvent e)
	{
		int x = e.getX();   
		int y = e.getY();   
		if (x >= xPosition && x <= (xPosition + BALL_DIAMETER)
				&& y >= yPosition && y <= (yPosition + BALL_DIAMETER)) {
			canDrag = true;
			dragX = x - xPosition;  
			dragY = y - yPosition;  
		} else {
			canDrag = false;
		}
	}

	public void mouseDragged(MouseEvent e)
	{
		if (canDrag) { 
			xPosition = e.getX() - dragX;
			yPosition = e.getY() - dragY;


			xPosition = Math.max(xPosition, 0);
			xPosition = Math.min(xPosition, getWidth() - BALL_DIAMETER);


			yPosition = Math.max(yPosition, 0);
			yPosition = Math.min(yPosition, getHeight() - BALL_DIAMETER);

			this.repaint();
		}
	}
	public void mouseExited(MouseEvent e)	{
		runGravity();
	}

	Timer timer;
	ActionListener animate;

	public void runGravity() {
		if (animate==null) {
			animate = new ActionListener() {
				public void actionPerformed(ActionEvent ae) {
					//System.out.println("Ground: " + (_ballY-ground_lvl));
					System.out.println("Ball y: "+yPosition);
					if (yPosition > ground) {
						timer.stop();
					} else {
						simulateGravity();
					}
				}
			};
			timer = new Timer(1,animate);
		}
		timer.start();
	}

	public void simulateGravity()
	{
		//System.out.println("_canDrag: " + _canDrag);

		//if (xVelocity<0.001) xVelocity=0;
		//if (yPosition <= ground - BALL_DIAMETER) yPosition=ground - BALL_DIAMETER;
	
		
		if(canDrag)
		{
			if (yPosition >= ground - BALL_DIAMETER)
			{
				//TODO fix here
				if (yPosition>ground-BALL_DIAMETER){
				yPosition = (int) (yPosition+yVelocity)+ground;
				}
				//We have hit the "ground", so bounce back up. Reverse
				yVelocity = -yVelocity/BOUNCY_FACTOR;
			//	if (xVelocity<0.001) xVelocity=0;
			}

			yVelocity -= GRAVITY;
			if (yVelocity<0) yVelocity=0;
			
			yPosition += yVelocity;
			this.revalidate();
			this.repaint();
		}
	}

	public void mouseMoved   (MouseEvent e){}
	public void mouseEntered (MouseEvent e){}
	public void mouseClicked (MouseEvent e){}
	public void mouseReleased(MouseEvent e){
		runGravity();
	}

	public void windowActivated(WindowEvent arg0) {}
	public void windowDeactivated(WindowEvent arg0) {}
	public void windowDeiconified(WindowEvent arg0) {}
	public void windowIconified(WindowEvent arg0) {}
	public void windowOpened(WindowEvent arg0) {}
	public void windowClosed(WindowEvent arg0) {
		System.exit(0);
	}

	public void windowClosing(WindowEvent arg0) {
		System.exit(0);
	}


	public static void main(String[] args) {
		SwingUtilities.invokeLater( new Runnable() {
			public void run() {
				DragBall d = new DragBall();
				JOptionPane.showMessageDialog(null, d, "Ball with gravity", JOptionPane.DEFAULT_OPTION);
			}
		});
	}
}
