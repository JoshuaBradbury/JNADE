package uk.co.newagedev.jnade;

import java.awt.Color;

import javax.swing.JFrame;

import uk.co.newagedev.jnade.audio.AudioRegistry;
import uk.co.newagedev.jnade.input.KeyBinding;
import uk.co.newagedev.jnade.openglgraphics.OpenGLScreen;
import uk.co.newagedev.jnade.openglgraphics.SpriteRegistry;
import uk.co.newagedev.jnade.util.Logger;

public class Main implements Runnable {

	private static OpenGLScreen screen;
	public static int WIDTH, HEIGHT;
	public static String TITLE;
	public static final SpriteRegistry RENDERABLE_REGISTRY = new SpriteRegistry();
	public static final AudioRegistry AUDIO_REGISTRY = new AudioRegistry();
	public static final int SPRITE_WIDTH = 32, SPRITE_HEIGHT = 32;
	public int ups, fps, width = 100, height = 100;
	public String title;
	public boolean running;
	public JFrame frame;
	public Thread thread;
	public Game game;
	private TaskScheduler scheduler = new TaskScheduler();
	
	public Color bg = Color.WHITE;
	
	public static OpenGLScreen getScreen() {
		return screen;
	}
	
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
	
	// Game Window Settings Methods End
	
	public static int getWidth() {
		return WIDTH;
	}
	
	public static int getHeight() {
		return HEIGHT;
	}

	public void setGame(Game game) {
		this.game = game;
	}
	
	public Game getGame() {
		return game;
	}
	
	public void init() {
		Main.WIDTH = width;
		Main.HEIGHT = height;
		Main.TITLE = title;
		screen = new OpenGLScreen();
		screen.setClearColour(bg.getRed(), bg.getGreen(), bg.getBlue(), bg.getAlpha());
		game.init();
	}

	public synchronized void start() {
		thread = new Thread("JNADE");
		running = true;
		init();
		run();
	}

	public synchronized void stop() {
		try {
			thread.join();
		} catch (InterruptedException e) {
			Logger.error(e.getMessage());
			for (Object obj : e.getStackTrace()) {
				Logger.error(obj);
			}
		}
		running = false;
	}

	public void run() {
		long secondTime = System.currentTimeMillis();
		int fps = 0;
		while (running) {
			update();
			screen.renderInit();
			game.render();
			screen.postRender();
			fps++;
			if (System.currentTimeMillis() - secondTime >= 1000) {
				screen.setTitle(TITLE + "     FPS: " + fps);
				secondTime += 1000;
				fps = 0;
			}
			if (screen.shouldClose()) {
				stop();
			}
		}
		AUDIO_REGISTRY.cleanUp();
		screen.cleanup();
	}
	
	public void update() {
		KeyBinding.update();
		scheduler.update();
		game.update();
	}
}
