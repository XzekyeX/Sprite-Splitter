package net.teamfps.sprite;

/**
 * @author Zekye
 *
 */
public class Screen extends Bitmap {
	private Splitter split;

	/**
	 * @param width
	 * @param height
	 */
	public Screen(Camera camera, int width, int height) {
		super(camera, width, height);
	}

	public void loadSplitted(String imagePath, String saveFolder, String saveName, int colorFilter, int splitWidth, int splitHeight) {
		split = new Splitter(new Sprite(imagePath), saveFolder, saveName, 64, 64, splitWidth, splitHeight, colorFilter);
		System.out.println("split: " + split);
	}

	public void update() {
		if (split != null) {
			split.update(this);
		}
	}

	public void render() {
		if (split != null) {
			split.render(this);
			renderString("Save Sprites (CTRL + SHIFT + S)", 12, 20, 48, 0xffffff, false);
			renderString("Select All Sprites (CTRL + SHIFT + A)", 12, 20, 48 + 16, 0xffffff, false);
			renderString("unSelect All Sprites (CTRL + SHIFT + D)", 12, 20, 48 + 32, 0xffffff, false);
			renderString("Cam Speed: " + getCamera().getSpeed(), 12, 20, 48 + 48, 0xffffff, false);
			renderString("Zoom: " + split.getZoom(), 12, 20, 48 + 64, 0xffffff, false);
		}
	}

}
