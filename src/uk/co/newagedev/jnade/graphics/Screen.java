package uk.co.newagedev.jnade.graphics;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import java.util.ArrayList;
import java.util.List;

import uk.co.newagedev.jnade.Main;
import uk.co.newagedev.jnade.input.KeyBinding;
import uk.co.newagedev.jnade.input.Mouse;

public class Screen extends Canvas {
	private static final long serialVersionUID = -4168966681058955222L;
	
	private Color bg;
	
	private List<DrawTask> tasks = new ArrayList<DrawTask>();

	private int scale;
	
	private BufferedImage img;
	private int[] pixels;
	private int width, height;
	
	public Screen(int width, int height, int scale, KeyBinding keyboard, Mouse mouse) {
		setSize(width * scale, height * scale);
		addKeyListener(keyboard);
		addMouseListener(mouse);
		addMouseMotionListener(mouse);
		this.width = width * scale;
		this.height = height * scale;
		this.scale = scale;
		img = new BufferedImage(width * scale, height * scale, BufferedImage.TYPE_INT_RGB);
		pixels = ((DataBufferInt) img.getRaster().getDataBuffer()).getData();
	}
	
	public void setBackgroundColour(Color colour) {
		bg = colour;
	}
	
	public void renderImage(float x, float y, int width, int height, int[] pixels) {
		tasks.add(new DrawTask(DrawTask.Type.IMAGE, x, y, pixels, width, height));
	}
	
	public void renderString(float x, float y, String text) {
		tasks.add(new DrawTask(DrawTask.Type.STRING, x, y, text));
	}
	
	public void renderRect(float x, float y, int width, int height, Color colour) {
		tasks.add(new DrawTask(DrawTask.Type.RECT, x, y, width, height, colour));
	}
	
	public void render() {
		BufferStrategy bs = getBufferStrategy();
		if (bs == null) {
			createBufferStrategy(2);
			return;
		}
		
		Graphics g = bs.getDrawGraphics();

		for (int i = 0; i < pixels.length; i++) {
			pixels[i] = bg.getRGB();
		}
		
		Color removeColour = Main.RENDERABLE_REGISTRY.getRemoveColour();
		
		for (DrawTask task : tasks) {
			switch (task.type) {
			case IMAGE:
				int w = (int) task.params[1], h = (int) task.params[2];
				int[] imgPixels = (int[]) task.params[0];
				for (int x = 0; x < w; x++) {
					for (int y = 0; y < h; y++) {
						int pixel = imgPixels[x + w * y];
						if (removeColour != null) {
							if (pixel == removeColour.getRGB()) {
								continue;
							}
						}
						if (task.x * scale + x < width && task.x * scale + x >= 0 && task.y * scale + y < height && task.y * scale + y >= 0)
							pixels[(task.x * scale + x) + width * (task.y * scale + y)] = pixel;
					}
				}
				break;
			case STRING:
				g.drawString((String) task.params[0], task.x * scale, task.y * scale);
				break;
			case RECT:
				Color colour = (Color) task.params[2];
				int x_ = task.x * scale, y_ = task.y * scale, w_ = (int) task.params[0] * scale, h_ = (int) task.params[1] * scale;
				for (int x = x_; x < x_ + w_; x++) {
					for (int y = y_; y < y_ + h_; y++) {
						if (x < width && x >= 0 && y < height && y >= 0)
							pixels[x + width * y] = colour.getRGB();
					}
				}
				break;
			default:
				System.err.println("Sorry, the task" + task.type.name() + "is not supported");
				break;
			}
		}
		
		g.drawImage(img, 0, 0, width, height, null);
		
		tasks.clear();
		
		g.dispose();
		bs.show();
	}
	
	public static class DrawTask {
		public enum Type {
			IMAGE,
			STRING,
			RECT;
		}
		
		public Type type;
		public int x, y;
		public Object[] params;
		
		public DrawTask(Type type, float x, float y, Object... params) {
			this.type = type;
			this.x = (int) x;
			this.y = (int) y;
			this.params = params;
		}
	}
}
