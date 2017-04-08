package graphics;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
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

public class Balldrag extends JPanel implements MouseListener, MouseMotionListener, WindowListener {

	/**
	 * @param args
	 */

	//private static final int BALL_DIAMETER = 40; 
	private static final int BOUNCY_FACTOR = 3;


	private Ball b=new Ball(200, 200, 20, 0, 70, new Color(100,50,100));
	private int screenX = 800;
	private int screenY = 800;
	private int ground = screenY - 20;

	//private int xPosition= ground/2;
	//private int yPosition = ground - BALL_DIAMETER;
	private final double GRAVITY = .098;
	//private double yVelocity;

	private int dragX = 0;    
	private int dragY = 0;    


	private boolean canDrag  = false;



	public Balldrag()
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
		//b = new Ball(200, 200, 20, 0, 70, new Color(100,50,100));
		Graphics2D g2 = (Graphics2D)g;
		b.calcPosition(5);
		b.draw(g2);

	}

	public void mousePressed(MouseEvent e)
	{
		int x = e.getX();   
		int y = e.getY();   
		if (x >= b.getXposition() && x <= (b.getXposition() + b.getSize())
				&& y >= b.getYposition() && y <= (b.getYposition() + b.getSize())) {
			canDrag = true;
			dragX = x - b.getXposition();  
			dragY = y - b.getYposition();  
		} else {
			canDrag = false;
		}
	}

	public void mouseDragged(MouseEvent e)
	{
		if (canDrag) { 
			b.setXposition(e.getX() - dragX);
			b.setYposition(e.getY() - dragY);


			b.setXposition(Math.max(b.getXposition(), 0));
			b.setXposition(Math.min(b.getXposition(), getWidth() - b.getSize()));


			b.setYposition(Math.max(b.getYposition(), 0));
			b.setYposition(Math.min(b.getYposition(), getHeight() - b.getSize()));

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
					System.out.println("Ball y: "+b.getYposition());
					//	if (b.getYposition() > ground) {
					timer.stop();
					//} else {
					simulateGravity();
					//}
				}
			};
			timer = new Timer(10,animate);
		}
		timer.start();
	}

	public void simulateGravity()
	{

		if(canDrag)
		{
			if (b.getYposition() >= ground - b.getSize())
			{
				//TODO fix here

				b.setYvelocity(-b.getYvelocity()/BOUNCY_FACTOR);
			}

			b.setYvelocity(b.getYvelocity()-(float)GRAVITY);


			b.setYposition(b.getYposition()+(int)b.getYvelocity());
			//this.revalidate();
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
				Balldrag d = new Balldrag();
				JOptionPane.showMessageDialog(null, d, "Ball with gravity", JOptionPane.DEFAULT_OPTION);
			}
		});
	}
}
