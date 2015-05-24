package net.teamfps.sprite;

import java.awt.event.KeyEvent;

/**
 * @author Zekye
 *
 */
public class Camera {
	private double x, y, speed = 1.5D;
	private boolean movement = true;

	public Camera() {
	}

	public Camera(int x, int y) {
		this.x = x;
		this.y = y;
	}

	public void update() {
		if (movement) {
			if (Input.isKeyDown(KeyEvent.VK_A)) x -= speed;
			if (Input.isKeyDown(KeyEvent.VK_D)) x += speed;
			if (Input.isKeyDown(KeyEvent.VK_W)) y -= speed;
			if (Input.isKeyDown(KeyEvent.VK_S)) y += speed;
		}
	}

	/**
	 * @return the x
	 */
	public int getX() {
		return (int) x;
	}

	/**
	 * @return the y
	 */
	public int getY() {
		return (int) y;
	}

	public void setX(int x) {
		this.x = x;
	}

	public void setY(int y) {
		this.y = y;
	}
	
	public void setSpeed(double speed) {
		this.speed = speed;
	}
	
	public double getSpeed() {
		return speed;
	}

	public void disable() {
		movement = false;
	}

	public void enable() {
		movement = true;
	}
}
