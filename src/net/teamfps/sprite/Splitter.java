package net.teamfps.sprite;

import java.awt.event.*;

public class Splitter {
	protected int x, y, w, h;
	protected int sw, sh, tw, th;
	protected Sprite sprite;
	protected Splitted[][] split;
	protected String name;
	protected String dir;
	private int delay = 20;
	private double z = 1;
	private double ZoomSpeed = 0.05D;

	private Explode explode;

	public Splitter(Sprite sprite, String dir, String name, int x, int y, int sw, int sh) {
		this.sprite = sprite;
		this.dir = dir;
		this.name = name;
		this.x = x;
		this.y = y;
		this.w = sprite.getWidth();
		this.h = sprite.getHeight();
		this.sw = sw;
		this.sh = sh;
		this.tw = sprite.getWidth() / sw;
		this.th = sprite.getHeight() / sh;
		Sprite[] sprites = Sprite.SplitSprite(sprite, sw, sh);
		split = new Splitted[tw][th];
		for (int xx = 0; xx < tw; xx++) {
			for (int yy = 0; yy < th; yy++) {
				split[xx][yy] = new Splitted(sprites[xx + yy * tw], x + xx * sw, y + yy * sh, sw, sh);
			}
		}
	}

	public Splitter(Sprite sprite, String dir, String name, int x, int y, int sw, int sh, int color) {
		this.sprite = sprite.removeColor(color);
		this.dir = dir;
		this.name = name;
		this.x = x;
		this.y = y;
		this.w = sprite.getWidth();
		this.h = sprite.getHeight();
		this.sw = sw;
		this.sh = sh;
		this.tw = sprite.getWidth() / sw;
		this.th = sprite.getHeight() / sh;
		Sprite[] sprites = Sprite.SplitSprite(sprite, sw, sh);
		split = new Splitted[tw][th];
		for (int xx = 0; xx < tw; xx++) {
			for (int yy = 0; yy < th; yy++) {
				split[xx][yy] = new Splitted(sprites[xx + yy * tw].removeColor(color), x + xx * sw, y + yy * sh, sw, sh);
			}
		}
	}

	public void update(Screen screen) {
		if (delay > 0) delay--;
		for (int x = 0; x < tw; x++) {
			for (int y = 0; y < th; y++) {
				if (split[x][y] != null) {
					if (split[x][y].isMouseLeft(screen) && delay == 0) {
						delay = 20;
						if (!split[x][y].isSelected()) {
							explode = new Explode(split[x][y].getSprite(), 0, 0);
							System.out.println("explode: " + explode);
							split[x][y].Select();
						} else {
							split[x][y].unSelect();
						}
						System.out.println("Selected: " + split[x][y].isSelected());
					}
				}
			}
		}
		for (int x = 0; x < tw; x++) {
			for (int y = 0; y < th; y++) {
				if (split[x][y] != null) {
					split[x][y].setZoom(z);
				}
			}
		}

		if (Input.SCROLL == 1) {
			Input.SCROLL = 0;
			z += ZoomSpeed;
		}
		if (Input.SCROLL == -1) {
			Input.SCROLL = 0;
			if (z > 1) z -= ZoomSpeed;
		}
		screen.getCamera().setSpeed(z >= 4 ? 4 : z);
		if (Input.isKeyDown(KeyEvent.VK_CONTROL)) {
			screen.getCamera().disable();
			if (Input.isKeyDown(KeyEvent.VK_SHIFT)) {
				if (Input.isKeyDown(KeyEvent.VK_S) && delay == 0) {
					delay = 20;
					Save();
				}
				if (Input.isKeyDown(KeyEvent.VK_A) && delay == 0) {
					delay = 20;
					SelectAll();
				}
				if (Input.isKeyDown(KeyEvent.VK_D) && delay == 0) {
					delay = 20;
					unSelectAll();
				}
			}
		} else {
			screen.getCamera().enable();
		}
		if (explode != null) {
			explode.update(screen);
			explode.setZoom(z);
			if (explode.isMouseOver(screen)) {
				if (Input.ML) explode.explode();
			}
			if (explode.isRemoved()) {
				explode = null;
			}
		}
	}

	public void render(Screen screen) {
		for (int x = 0; x < tw; x++) {
			for (int y = 0; y < th; y++) {
				if (split[x][y] != null) {
					split[x][y].render(screen);
				}
			}
		}

		for (int x = 0; x < tw; x++) {
			for (int y = 0; y < th; y++) {
				if (split[x][y] != null) {
					split[x][y].renderRect(screen);
				}
			}
		}

		if (explode != null) {
			explode.render(screen);
			explode.renderRect(screen);
		}
	}

	public boolean isOverHalfSelected() {
		return (getSelectedAmount() >= ((tw * th) / 2));
	}

	public int getSelectedAmount() {
		int amount = 0;
		for (int x = 0; x < tw; x++) {
			for (int y = 0; y < th; y++) {
				if (split[x][y] != null && split[x][y].isSelected()) {
					amount++;
				}
			}
		}
		return amount;
	}

	public void SelectAll() {
		for (int x = 0; x < tw; x++) {
			for (int y = 0; y < th; y++) {
				if (split[x][y] != null) {
					split[x][y].Select();
				}
			}
		}
	}

	public void unSelectAll() {
		for (int x = 0; x < tw; x++) {
			for (int y = 0; y < th; y++) {
				if (split[x][y] != null) {
					split[x][y].unSelect();
				}
			}
		}
	}

	private void Save() {
		for (int x = 0; x < tw; x++) {
			for (int y = 0; y < th; y++) {
				if (split[x][y] != null && split[x][y].isSelected()) {
					int src = x + y * tw;
					split[x][y].WriteSprite(dir, name + "_" + src + ".png");
				}
			}
		}
	}

	public double getZoom() {
		return z;
	}

}