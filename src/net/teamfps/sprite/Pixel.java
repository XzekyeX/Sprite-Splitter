package net.teamfps.sprite;

import java.util.Random;

public class Pixel {
	private int x, y, w, h, color;
	protected double xx, yy, zz;
	protected double xa, ya, za;
	private int life;
	private int time = 0;
	private boolean removed = false;
	private boolean bounce = false;
	private Random rand = new Random();
	private double z = 1;

	public Pixel(int x, int y, int color) {
		this.color = color;
		this.x = x;
		this.y = y;
		this.w = 1;
		this.h = 1;
		this.xx = x;
		this.yy = y;
		this.xa = rand.nextGaussian();
		this.ya = rand.nextGaussian();
		this.zz = rand.nextFloat() + 5.0;
		this.life = rand.nextInt(128) + 32;
	}

	public void update(Screen screen) {
		time++;
		if (time >= 7400) time = 0;
		if (time > life) remove();
		if (bounce) {
			za -= 0.1;
			if (zz < 0) {
				zz = 0;
				za *= -0.8;
				xa *= 0.4;
				ya *= 0.4;
			}
		}
		move(xa, ya, za, 0.05D);
	}

	public void render(Screen screen) {
		this.x = (int) xx;
		this.y = (int) (yy - zz);
		// if (color >= 0 || color <= 0xff000000) return;
		screen.renderFillRect((int) (x * z), (int) (y * z), (int) (w * z), (int) (h * z), color, true);
	}

	public boolean isMouseOver(Screen screen) {
		return (Input.DX + screen.getCamera().getX() >= x * z && Input.DX + screen.getCamera().getX() <= x * z + w * z && Input.DY + screen.getCamera().getY() >= y * z && Input.DY + screen.getCamera().getY() <= y * z + h * z);
	}

	public void setZoom(double z) {
		this.z = z;
	}

	public void move(double x, double y, double z, double speed) {
		this.xx += x * speed;
		this.yy += y * speed;
		this.zz += z * speed;
	}

	public void remove() {
		removed = true;
	}

	public boolean isRemoved() {
		return removed;
	}
}