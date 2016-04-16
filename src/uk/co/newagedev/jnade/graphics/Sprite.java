package uk.co.newagedev.jnade.graphics;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Sprite implements Renderable {

	private int[] pixels;
	private String name;
	private int width, height;
	private boolean flipX = false, flipY = false;
	
	public Sprite(String path) {
		this(loadImageFile(path));
	}

	public Sprite(BufferedImage image) {
		this.pixels = loadPixels(image);
		if (image != null) {
			width = image.getWidth();
			height = image.getHeight();
		}
	}

	public static int[] loadPixels(BufferedImage image) {
		int[] pixels = new int[0];

		if (image != null) {
			pixels = new int[image.getWidth() * image.getHeight()];
			for (int x = 0; x < image.getWidth(); x++) {
				for (int y = 0; y < image.getHeight(); y++) {
					pixels[x + image.getWidth() * y] = image.getRGB(x, y);
				}
			}
		}

		return pixels;
	}

	public static BufferedImage loadImageFile(String path) {
		File imageFile = new File(path);
		if (imageFile.exists()) {
			try {
				return ImageIO.read(imageFile);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return null;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}
	
	public void setFlipX(boolean flipX) {
		this.flipX = flipX;
	}
	
	public void setFlipY(boolean flipY) {
		this.flipY = flipY;
	}

	public int[] getPixels() {
		int[] finalPixels = pixels;

		if (flipX) {
			int[] tempPixels = new int[finalPixels.length];
			for (int x = 0; x < width; x++) {
				for (int y = 0; y < height; y++) {
					tempPixels[x + (y * width)] = finalPixels[((width - 1) - x) + (y * width)];
				}
			}
			finalPixels = tempPixels;
		}

		if (flipY) {
			int[] tempPixels = new int[finalPixels.length];
			for (int x = 0; x < width; x++) {
				for (int y = 0; y < height; y++) {
					tempPixels[x + y * width] = finalPixels[x + ((height - 1) - y) * width];
				}
			}
			finalPixels = tempPixels;
		}

		return finalPixels;
	}

	@Override
	public void scale(int scale) {
		int[] tempPixels = new int[pixels.length * scale * 2];
		for (int x = 0; x < width; x++) {
			for (int y = 0; y < height; y++) {
				for (int i = 0; i < scale; i++) {
					for (int j = 0; j < scale; j++) {
						int x_ = (x * scale) + i;
						int y_ = (y * scale) + j;
						tempPixels[x_ + (width * scale * y_)] = pixels[x + y * width];
					}
				}
			}
		}
		pixels = tempPixels;
		width *= scale;
		height *= scale;
	}
}
