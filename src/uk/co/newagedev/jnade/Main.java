package uk.co.newagedev.jnade;

import java.awt.Color;

import javax.swing.JFrame;

import uk.co.newagedev.jnade.audio.AudioRegistry;
import uk.co.newagedev.jnade.graphics.RenderableRegistry;
import uk.co.newagedev.jnade.graphics.Screen;
import uk.co.newagedev.jnade.input.KeyBinding;
import uk.co.newagedev.jnade.input.Mouse;

public class Main implements Runnable {

	private static final int MAX_UPS = 60;
	public static Screen screen;
	private static KeyBinding keyboard = new KeyBinding();
	private static Mouse mouse = new Mouse();
	public static final RenderableRegistry RENDERABLE_REGISTRY = new RenderableRegistry();
	public static final AudioRegistry AUDIO_REGISTRY = new AudioRegistry();
	public int ups, fps, width = 100, height = 100, scale = 1;
	public String title;
	public boolean running;
	public JFrame frame;
	public Thread thread;
	public Game game;
	private TaskScheduler scheduler = new TaskScheduler();
	
	public Color bg = Color.WHITE;
	
	// Game Screen Settings Methods Start
	
	public void setBackgroundColour(Color colour) {
		bg = colour;
	}
	
	// Game Screen Settings Methods End
	
	// Game Window Settings Methods Start

	public void setTitle(String title) {
		this.title = title;
	}

	public void setScreenSize(int width, int height) {
		this.width = width;
		this.height = height;
	}

	public void setScreenScale(int scale) {
		this.scale = scale;
		RENDERABLE_REGISTRY.scaleAll(scale);
	}
	
	// Game Window Settings Methods End
	
	public int getWidth() {
		return screen.getWidth();
	}
	
	public int getHeight() {
		return screen.getHeight();
	}

	public void setGame(Game game) {
		this.game = game;
		game.init();
	}
	
	public Game getGame() {
		return game;
	}
	
	public void init() {
		screen = new Screen(width, height, scale, keyboard, mouse);
		screen.setBackgroundColour(bg);
		
		frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(width * scale, height * scale);
		frame.setLocationRelativeTo(null);
		frame.setResizable(false);
		frame.setTitle(title);

		frame.add(screen);
		frame.pack();

		frame.setVisible(true);
		
		Mouse.MAIN_INSTANCE = this;
	}

	public synchronized void start() {
		running = true;
		thread = new Thread(this, "JNADE");

		init();
		run();
	}

	public synchronized void stop() {
		running = false;
		try {
			thread.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		frame.dispose();
	}

	public void run() {
		long lastTime = System.nanoTime();
		long secondTime = System.currentTimeMillis();
		final double ns = 1000000000 / MAX_UPS;
		double delta = 0;
		while (running) {
			game.render();
			screen.render();
			fps++;

			long now = System.nanoTime();
			delta += (now - lastTime) / ns;
			lastTime = now;

			while (delta >= 1) {
				update();
				ups++;
				delta--;
			}

			if (System.currentTimeMillis() - secondTime >= 1000) {
				frame.setTitle(title + " FPS: " + fps + " UPS: " + ups);
				secondTime += 1000;
				fps = 0;
				ups = 0;
			}
		}
		AUDIO_REGISTRY.cleanUp();
	}
	
	public void update() {
		KeyBinding.update();
		Mouse.update();
		scheduler.update();
		game.update();
	}
}
