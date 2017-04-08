package graphics;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;

/**
 * 
 * @author Aleksander
 *
 */
public class Ball {

	private float xposition;
	private float yposition;
	private float xvelocity;
	private float yvelocity;
	private int size;
	private Color ballColor;

	/**
	 * 
	 * @param x
	 * @param y
	 * @param xv
	 * @param yv
	 * @param s
	 * @param c
	 */
	public Ball(float x, float y, float xv, float yv, int s, Color c){
		xposition = x;
		yposition = y;
		xvelocity = xv;
		yvelocity = yv;
		size = s;
		ballColor = c;

	}//

	/**
	 * 
	 * @return
	 */
	public Color getBallColor() {
		return ballColor;
	}//

	/**
	 * 
	 * @param ballColor
	 */
	public void setBallColor(Color ballColor) {
		this.ballColor = ballColor;
	}//

	/**
	 * 
	 * @return
	 */
	public float getXposition() {
		return xposition;
	}//

	/**
	 * 
	 * @return
	 */
	public float getYposition() {
		return yposition;
	}//

	/**
	 * 
	 * @return
	 */
	public float getXvelocity() {
		return xvelocity;
	}//

	/**
	 * 
	 * @return
	 */
	public float getYvelocity() {
		return yvelocity;
	}//

	/**
	 * 
	 * @return
	 */
	public int getSize() {
		return size;
	}//

	/**
	 * 
	 * @param xposition
	 */
	public void setXposition(float xposition) {
		this.xposition = xposition;
	}//

	/**
	 * 
	 * @param yposition
	 */
	public void setYposition(float yposition) {
		this.yposition = yposition;
	}//

	/**
	 * 
	 * @param xvelocity
	 */
	public void setXvelocity(float xvelocity) {
		this.xvelocity = xvelocity;
	}//

	/**
	 * 
	 * @param yvelocity
	 */
	public void setYvelocity(float yvelocity) {
		this.yvelocity = yvelocity;
	}//

	/**
	 * 
	 * @param size
	 */
	public void setSize(int size) {
		this.size = size;
	}//

}//