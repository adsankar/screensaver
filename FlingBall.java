package graphics;

import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.JPanel;

public class FlingBall extends JPanel implements MouseListener, MouseMotionListener, WindowListener{

	private int dragX = 0;    
	private int dragY = 0;  
	private boolean canDrag;
	private int xposition = 200;
	private int yposition = 200;
	private int ballDiameter = 80;
	private int yvelocity=0;
	private int xvelocity;
	private int framerate=10;
	
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

	public void update(Graphics g){
		paint(g);
	}
	
	public void paint(Graphics g){
		recalc();
		g.fillOval(xposition, yposition,ballDiameter, ballDiameter);
	}
	
	public void recalc(){
		xposition+=xvelocity/framerate;
		yposition+=yvelocity/framerate;
	}
	
	public void run(){
		while (!false){
			repaint();
			try {
				Thread.sleep(50);
			}
			catch (InterruptedException e){
				System.out.println("Thread ended.");
				System.exit(0);
			}
 		}
	}
	
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		int x = e.getX();   
		int y = e.getY();   
		if (x >= xposition && x <= (xposition + ballDiameter)
				&& y >= yposition && y <= (yposition + ballDiameter)) {
			canDrag = true;
			dragX = x - xposition;  
			dragY = y - yposition;  
		} else {
			canDrag = false;
		}
	}

	
	public void mouseExited(MouseEvent e) {
		// TODO stuff here
		
	}

	public void mousePressed(MouseEvent e) {
		// TODO stuff here
		
	}

	public void mouseReleased(MouseEvent e) {
		// TODO stuff here
	}
	
	public void mouseDragged(MouseEvent e) {
	//TODO stuff here
	}
	
	public void windowClosed(WindowEvent e) {System.exit(0);}

	public void windowClosing(WindowEvent e) {System.exit(0);}
	
	public void windowActivated(WindowEvent e) {}
	public void windowDeactivated(WindowEvent e) {}
	public void windowDeiconified(WindowEvent e) {}
	public void windowIconified(WindowEvent e) {}
	public void windowOpened(WindowEvent e) {}
	public void mouseMoved(MouseEvent e) {}
	public void mouseEntered(MouseEvent e) {}
}
