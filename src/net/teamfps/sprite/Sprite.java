package net.teamfps.sprite;

import java.awt.image.*;
import java.io.*;

import javax.imageio.*;

public class Sprite {
	private BufferedImage image;
	private String path;
	private int[] pixels;
	private int width, height;

	public Sprite(String path) {
		this.path = path;
		load();
	}

	public Sprite(File f) {
		load(f);
	}

	public Sprite(int[] pixels, int width, int height) {
		this.width = width;
		this.height = height;
		this.pixels = pixels;
		this.image = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
		this.image.setRGB(0, 0, width, height, pixels, 0, width);
	}

	public Sprite(String name, int[] pixels, int width, int height) {
		this.path = name;
		this.width = width;
		this.height = height;
		this.pixels = pixels;
		this.image = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
		this.image.setRGB(0, 0, width, height, pixels, 0, width);
	}

	public static Sprite SplitSprite(Sprite s, String name, int x, int y, int width, int height) {
		int[] pixels = new int[width * height];
		for (int h = 0; h < height; h++) {
			for (int w = 0; w < width; w++) {
				pixels[w + h * width] = s.pixels[((w + x) + (h + y) * s.getWidth())];
			}
		}
		return new Sprite(name, pixels, width, height);
	}

	public static Sprite[] SplitSprite(Sprite s, String name, int w, int h) {
		int sw = s.width / w;
		int sh = s.height / h;
		Sprite[] sprites = new Sprite[sw * sh];
		for (int y = 0; y < sh; y++) {
			for (int x = 0; x < sw; x++) {
				sprites[x + y * sw] = SplitSprite(s, name + "_" + (x + y * sw), x * w, y * h, w, h);
			}
		}
		return sprites;
	}

	public static Sprite[] SplitSprite(Sprite s, int w, int h) {
		return SplitSprite(s, s.path, w, h);
	}

	private void load() {
		try {
			String f = path;
			path = f.toUpperCase().contains(".PNG") ? f.substring(0, f.length() - 4) : f;
			if (path.toUpperCase().contains(".PNG")) {
				image = ImageIO.read(getClass().getResourceAsStream(path));
			} else {
				image = ImageIO.read(getClass().getResourceAsStream(path + ".png"));
			}

			width = image.getWidth();
			height = image.getHeight();
			pixels = new int[width * height];
			image.getRGB(0, 0, width, height, pixels, 0, width);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void load(File f) {
		try {
			path = f.getName().toUpperCase().contains(".PNG") ? f.getName().substring(0, f.getName().length() - 4) : f.getName();
			image = ImageIO.read(f);
			width = image.getWidth();
			height = image.getHeight();
			pixels = new int[width * height];
			image.getRGB(0, 0, width, height, pixels, 0, width);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void WriteImage(String dir, String name) {
		if (getImage() == null || dir == null || name == null) return;
		try {
			if (name.contains(".png")) {
				File f = new File(dir);
				if (f.mkdir() || f.exists()) {
					ImageIO.write(getImage(), "png", new File(f, "/" + name));
					System.out.println("Successfully writed new image with png!: " + dir + "/" + name);
				}
			} else {
				ImageIO.write(getImage(), "png", new File(dir + "/" + name + ".png"));
				System.out.println("Successfully writed new image!: " + dir + "/" + name + ".png");
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void WriteImage(String dir) {
		if (path == null) {
			System.out.println("path is null!");
			return;
		}
		try {
			if (path.contains(".png")) {
				File f = new File(dir);
				if (f.mkdir() || f.exists()) {
					File tf = new File(f, "/" + path);
					ImageIO.write(getImage(), "png", tf);
					System.out.println("Successfully writed new image with png!: " + dir + "/" + path);
				}
			} else {
				ImageIO.write(getImage(), "png", new File(dir + "/" + path + ".png"));
				System.out.println("Successfully writed new image!: " + dir + "/" + path + ".png");
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public Sprite removeColor(int color) {
		int[] newPixels = new int[width * height];
		for (int x = 0; x < width; x++) {
			for (int y = 0; y < height; y++) {
				int src = pixels[x + y * width];
				if (src != color) newPixels[x + y * width] = src;
			}
		}
		return new Sprite(path, newPixels, width, height);
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	public BufferedImage getImage() {
		return image;
	}

	public String getPath() {
		return path;
	}
	
	public int[] getPixels() {
		return pixels;
	}

	@Override
	public String toString() {
		return "" + path + " (" + width + "x" + height + ")";
	}

}
