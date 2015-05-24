package net.teamfps.sprite;

import java.awt.*;
import java.io.*;

/**
 * @author Zekye
 *
 */
public class Bitmap {
	protected Graphics g;
	protected int width, height;
	private Camera camera;

	public Bitmap(Camera camera, int width, int height) {
		this.camera = camera;
		this.width = width;
		this.height = height;
	}

	public Font loadFont(String path) {
		try {
			GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
			Font font = Font.createFont(Font.TRUETYPE_FONT, getClass().getResourceAsStream(path));
			ge.registerFont(font);
			return font;
		} catch (FontFormatException | IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	public void initGFX(Graphics g) {
		this.g = g;
	}

	public void renderString(String str, int fsize, int x, int y, int color, boolean offset) {
		if (offset) {
			x -= getCamera().getX();
			y -= getCamera().getY();
		}
		g.setColor(new Color(color));
		g.setFont(new Font("Tahoma", 1, fsize));
		g.drawString(str, x, y);
		g.setColor(new Color(0xffffff));
	}

	public void renderFillRect(int x, int y, int w, int h, Color color, boolean offset) {
		if (offset) {
			x -= getCamera().getX();
			y -= getCamera().getY();
		}
		g.setColor(color);
		g.fillRect(x, y, w, h);
		g.setColor(new Color(0xffffff));
	}

	public void renderFillRect(int x, int y, int w, int h, int color, boolean offset) {
		renderFillRect(x, y, w, h, new Color(color), offset);
	}

	public void renderRect(int x, int y, int w, int h, Color color, boolean offset) {
		if (offset) {
			x -= getCamera().getX();
			y -= getCamera().getY();
		}
		g.setColor(color);
		g.drawRect(x, y, w, h);
		g.setColor(new Color(0xffffff));
	}

	public void renderRect(int x, int y, int w, int h, int color, boolean offset) {
		renderRect(x, y, w, h, new Color(color), offset);
	}

	public void renderRect(int x, int y, int w, int h, int color, int alpha, boolean offset) {
		Color c = new Color(color);
		renderFillRect(x, y, w, h, new Color(c.getRed(), c.getGreen(), c.getBlue(), alpha), offset);
		renderRect(x, y, w, h, new Color(color), offset);
	}

	public void renderLine(int sx, int sy, int tx, int ty, Color color, boolean offset) {
		if (offset) {
			sx -= getCamera().getX();
			sy -= getCamera().getY();
		}
		g.setColor(color);
		g.drawLine(sx, sy, sx + tx, sy + ty);
		g.setColor(new Color(0xffffff));
	}

	public void drawLine(int x1, int y1, int x2, int y2, Color color, boolean offset) {
		if (offset) {
			x1 -= getCamera().getX();
			y1 -= getCamera().getY();
			x2 -= getCamera().getX();
			y2 -= getCamera().getY();
		}
		g.setColor(color);
		g.drawLine(x1, y1, x2, y2);
		g.setColor(new Color(0xffffff));
	}

	public void renderSprite(Sprite sprite, int x, int y, int w, int h, boolean offset) {
		if (sprite == null || sprite.getImage() == null) {
			System.err.println("sprite or image is null! Sprite: " + sprite);
			return;
		}
		if (offset) {
			x -= getCamera().getX();
			y -= getCamera().getY();
		}
		g.drawImage(sprite.getImage(), x, y, w, h, null);
	}

	public void renderSprite(Sprite sprite, int x, int y, int w, int h, boolean flip, boolean offset) {
		if (sprite == null || sprite.getImage() == null) {
			System.err.println("sprite or image is null! Sprite: " + sprite);
			return;
		}
		if (offset) {
			x -= getCamera().getX();
			y -= getCamera().getY();
		}
		if (flip) {
			w = -w;
			x = x - w;
		}
		g.drawImage(sprite.getImage(), x, y, w, h, null);
	}

	/**
	 * @return the width
	 */
	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	/**
	 * @return the height
	 */
	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	/**
	 * @return the camera
	 */
	public Camera getCamera() {
		return camera;
	}
}
