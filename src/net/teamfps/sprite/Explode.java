package net.teamfps.sprite;

import java.util.ArrayList;
import java.util.List;

public class Explode {
	private Sprite sprite;
	private int x, y, w, h;
	private List<Pixel> pixels = new ArrayList<Pixel>();
	private boolean removed = false;
	private boolean explode = false;
	private double z = 1;

	public Explode(Sprite sprite, int x, int y) {
		this.sprite = sprite;
		this.x = x;
		this.y = y;
		this.w = sprite.getWidth();
		this.h = sprite.getHeight();
		for (int xx = 0; xx < w; xx++) {
			for (int yy = 0; yy < h; yy++) {
				pixels.add(new Pixel(x + xx, y + yy + (h / 2), sprite.getPixels()[xx + yy * w]));
			}
		}
	}

	public void update(Screen screen) {
		if (explode) {
			for (int i = 0; i < pixels.size(); i++) {
				pixels.get(i).update(screen);
				if (pixels.get(i).isRemoved()) {
					pixels.remove(i);
				}
			}
		}
		if (pixels.size() <= 0) {
			remove();
		}
	}

	public void render(Screen screen) {
		if (explode) {
			for (int i = 0; i < pixels.size(); i++) {
				pixels.get(i).render(screen);
			}
		} else {
			screen.renderSprite(sprite, (int) (x * z), (int) (y * z), (int) (w * z), (int) (h * z), true);
		}
	}

	public void renderRect(Screen screen) {
		screen.renderRect((int) (x * z), (int) (y * z), (int) (w * z), (int) (h * z), isMouseOver(screen) ? 0xff0000 : 0xffffff, true);
	}

	public boolean isMouseOver(Screen screen) {
		return (Input.DX + screen.getCamera().getX() >= x * z && Input.DX + screen.getCamera().getX() <= x * z + w * z && Input.DY + screen.getCamera().getY() >= y * z && Input.DY + screen.getCamera().getY() <= y * z + h * z);
	}

	public void setZoom(double z) {
		this.z = z;
		for (int i = 0; i < pixels.size(); i++) {
			pixels.get(i).setZoom(z);
		}
	}

	public void explode() {
		explode = true;
	}

	public void remove() {
		removed = true;
	}

	public boolean isRemoved() {
		return removed;
	}

	public Sprite getSprite() {
		return sprite;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

}
