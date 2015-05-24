package net.teamfps.sprite;

import java.io.*;
import java.util.*;

public class Console implements Runnable {
	private Thread thread;
	private boolean running = false;
	private BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	private List<Command> commands = new ArrayList<Command>();
	private Screen screen;

	public Console(Screen screen) {
		this.screen = screen;
		commands.add(new Split());
	}

	public void start() {
		if (running) return;
		running = true;
		thread = new Thread(this, "Console Thread!");
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
		while (running) {
			try {
				String read = "" + br.readLine();
				for (int i = 0; i < commands.size(); i++) {
					String name = commands.get(i).getName();
					if (read.startsWith(name)) {
						commands.get(i).action(screen, read);
					}
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	abstract class Command {
		protected String name;
		protected String info;

		public Command(String name) {
			this.name = name;
		}

		public abstract void action(Screen screen, String act);

		public String getName() {
			return name;
		}

		public String getInfo() {
			return info;
		}
	}

	class Split extends Command {

		public Split() {
			super("Splitter");
		}

		@Override
		public void action(Screen screen, String act) {
			// Splitter(new Sprite("/tiles.png"), "res", 64, 64, 8, 12);

			// Splitter "image.png",width,height
			// Splitter "image.png",width,height

			System.out.println("Action: " + act);
			String[] split = act.split(" ");
			if (split != null && split.length > 1) {
				String a = split[1];
				if (a.contains(",")) {
					String[] split_comma = a.split(",");
					if (split_comma.length > 0) {
						for (int i = 0; i < split_comma.length; i++) {
							String c = split_comma[i];
							System.out.println("C[" + i + "]: " + c);
							if(c.contains("\"")) {
								
							}
						}
					}
				}
			}
		}
	}

	public String substring(String s, char c1, char c2) {
		if (s.contains("" + c1) && s.contains("" + c2)) {
			int bl = s.indexOf(c1);
			int br = s.indexOf(c2);
			String str = "" + s.substring(bl + 1, br);
			return str;
		}
		return "";
	}

}
