package net.teamfps.sprite;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.util.List;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

import javax.swing.JFrame;

/**
 * @author Zekye
 *
 */
public class Main extends Canvas implements Runnable {
	private static final long serialVersionUID = 1L;
	private static String version = "V.0.1.0";
	private static JFrame f = new JFrame("Sprite Splitter " + version);
	private static int width = 640;
	private static int height = 480;
	private Thread thread;
	private boolean running;

	private Input input;
	private Screen screen;
	private Camera camera;
	private Console console;

	private void init() {
		input = new Input();
		camera = new Camera(-64, -128);
		screen = new Screen(camera, width, height);
		if (split) screen.loadSplitted(imagePath, saveFolder, saveName, colorFilter, splitWidth, splitHeight);
		console = new Console(screen);
		console.start();
		addKeyListener(input);
		addMouseListener(input);
		addMouseWheelListener(input);
		addMouseMotionListener(input);
	}

	public void start() {
		if (running) return;
		running = true;
		thread = new Thread(this, "Main Thread!");
		thread.start();
	}

	public void stop() {
		if (!running) return;
		running = false;
		try {
			thread.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void run() {
		long lastTime = System.nanoTime();
		long timer = System.currentTimeMillis();
		double amountOfTicks = 60.0;
		double ns = 1000000000 / amountOfTicks;
		double delta = 0;
		int frames = 0;
		int updates = 0;
		init();
		requestFocus();
		while (running) {
			long now = System.nanoTime();
			delta += (now - lastTime) / ns;
			lastTime = now;
			while (delta >= 1) {
				update();
				updates++;
				delta--;
			}
			render();
			frames++;
			if (System.currentTimeMillis() - timer >= 1000) {
				timer += 1000;
				setFpsAndUps(frames, updates);
				frames = 0;
				updates = 0;
			}
		}
	}

	private int fps, ups;

	private void setFpsAndUps(int frames, int updates) {
		this.fps = frames;
		this.ups = updates;
	}

	public String getFpsAndUps() {
		return "fps[" + fps + "], ups[" + ups + "]";
	}

	private void update() {
		camera.update();
		screen.update();
		screen.setWidth(getWidth());
		screen.setHeight(getHeight());
	}

	private void render() {
		BufferStrategy bs = getBufferStrategy();
		if (bs == null) {
			createBufferStrategy(3);
			return;
		}
		Graphics g = bs.getDrawGraphics();
		g.setColor(new Color(32, 64, 255));
		g.fillRect(0, 0, getWidth(), getHeight());
		screen.initGFX(g);
		screen.render();
		screen.renderString("" + getFpsAndUps(), 18, 16, 24, 0xffffff, false);
		g.dispose();
		bs.show();
	}

	private String imagePath, saveFolder, saveName;
	private int colorFilter, splitWidth, splitHeight;
	private boolean split = false;

	public void loadSplitted(String imagePath, String saveFolder, String saveName, int colorFilter, int splitWidth, int splitHeight) {
		this.split = true;
		this.imagePath = imagePath;
		this.saveFolder = saveFolder;
		this.saveName = saveName;
		this.colorFilter = colorFilter;
		this.splitWidth = splitWidth;
		this.splitHeight = splitHeight;
	}

	public static int toInt(String s) {
		try {
			int i = Integer.parseInt(s);
			return i;
		} catch (NumberFormatException e) {
			System.out.println(s + " is not integer!");
		}
		return 0;
	}

	public static long toLong(String s, int radix) {
		try {
			long i = Long.parseLong(s, radix);
			return i;
		} catch (NumberFormatException e) {
			System.out.println(s + " is not long!");
		}
		return 0;
	}

	public static int toColor(String str) {
		if (str.contains("0x")) {
			String[] splitOx = str.split("0x");
			if (splitOx.length > 1) {
				String s = splitOx[1];
				int col = (int) toLong(s, 16);
				return col;
			}
		}
		return 0;
	}

	protected static boolean CurrentVersion() {
		String newest = getNewestVersionGZIP();
		System.out.println("Newest: " + newest);
		return version.equals(newest);
	}

	protected static String getNewestVersion() {
		String link = "https://raw.githubusercontent.com/XzekyeX/Sprite-Splitter/master/Version.fps";
		try {
			URL url = new URL(link);
			BufferedReader br = new BufferedReader(new InputStreamReader(url.openStream()));
			return "" + br.readLine();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return version;
	}

	protected static String getNewestVersionGZIP() {
		String link = "https://raw.githubusercontent.com/XzekyeX/Sprite-Splitter/master/Version.fps";
		try {
			URL url = new URL(link);
			BufferedReader br = new BufferedReader(new InputStreamReader(new GZIPInputStream(url.openStream())));
			return "" + br.readLine();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return version;
	}

	public void writeVersionFile() {
		try {
			System.out.println("Creating Version file!");
			DataOutputStream dos = new DataOutputStream(new GZIPOutputStream(new FileOutputStream(new File("Version.fps"))));
			byte[] bytes = version.getBytes();
			dos.write(bytes);
			System.out.println("Writing: " + bytes);
			dos.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public String readVersionFile() {
		try {
			File f = new File("Version.fps");
			InputStream fileStream = new FileInputStream(f);
			InputStream gzipStream = new GZIPInputStream(fileStream);
			Reader decoder = new InputStreamReader(gzipStream);
			BufferedReader br = new BufferedReader(decoder);
			String version = "" + br.readLine();
			br.close();
			return version;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return "";
	}

	public byte[] toBytes(List<Byte> list) {
		byte[] result = new byte[list.size()];
		for (int i = 0; i < list.size(); i++) {
			result[i] = list.get(i);
		}
		return result;
	}

	public static void main(String[] args) {
		if (CurrentVersion()) {
			Main m = new Main();
			if (args.length > 5) {
				String imagePath = args[0];
				String saveFolder = args[1];
				String saveName = args[2];
				int colorFilter = toColor(args[3]);
				int splitWidth = toInt(args[4]);
				int splitHeight = toInt(args[5]);
				m.loadSplitted(imagePath, saveFolder, saveName, colorFilter, splitWidth, splitHeight);
			}
			f.add(m, "Center");
			f.pack();
			f.setSize(width, height);
			f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			f.setLocationRelativeTo(null);
			f.setResizable(true);
			f.setVisible(true);
			m.start();
		} else {
			
		}
	}
}
