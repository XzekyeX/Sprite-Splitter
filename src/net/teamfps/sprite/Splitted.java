package net.teamfps.sprite;

public class Splitted {
	protected int x, y, w, h;
	protected Sprite sprite;
	private boolean selected = false;
	private double z = 1;

	public Splitted(Sprite sprite, int x, int y, int w, int h) {
		this.sprite = sprite;
		this.x = x;
		this.y = y;
		this.w = w;
		this.h = h;
	}

	public void render(Screen screen) {
		screen.renderSprite(sprite, (int) (x * z), (int) (y * z), (int) (w * z), (int) (h * z), true);
	}

	public void renderRect(Screen screen) {
		screen.renderRect((int) (x * z), (int) (y * z), (int) (w * z), (int) (h * z), isMouseOver(screen) ? 0xff0000 : (isSelected() ? 0xffff00 : 0xffffff), isSelected() ? 180 : 0, true);
	}

	public void setZoom(double z) {
		this.z = z;
	}

	public void WriteSprite(String dir) {
		if (sprite != null) {
			sprite.WriteImage(dir);
		}
	}

	public void WriteSprite(String dir, String name) {
		if (sprite != null) {
			sprite.WriteImage(dir, name);
		}
	}

	public void Select() {
		selected = true;
	}

	public void unSelect() {
		selected = false;
	}

	public boolean isSelected() {
		return selected;
	}

	public boolean isMouseLeft(Screen screen) {
		return isMouseOver(screen) && Input.ML;
	}

	public boolean isMouseOver(Screen screen) {
		return (Input.DX + screen.getCamera().getX() >= x * z && Input.DX + screen.getCamera().getX() <= x * z + w * z && Input.DY + screen.getCamera().getY() >= y * z && Input.DY + screen.getCamera().getY() <= y * z + h * z);
	}
	
	public Sprite getSprite() {
		return sprite;
	}
}
