package uk.co.newagedev.jnade.graphics;

import java.awt.image.BufferedImage;

import uk.co.newagedev.jnade.Task;
import uk.co.newagedev.jnade.TaskScheduler;

public class AnimatedSprite implements Renderable {

	private Sprite[] sprites;
	private String name;
	private int currFrame, upsBetweenFrames, taskID;
	
	public AnimatedSprite(String path, int upsBetweenFrames, int framesX, int framesY, int frameStart, int frameEnd) {
		this.upsBetweenFrames = upsBetweenFrames;
		sprites = loadAnimation(path, framesX, framesY, frameStart, frameEnd);
	}

	public AnimatedSprite(String path, int framesX, int framesY, int frameStart, int frameEnd) {
		this(path, -1, framesX, framesY, frameStart, frameEnd);
	}

	public AnimatedSprite(String path, int framesX, int framesY) {
		this(path, framesX, framesY, 0, framesX * framesY - 1);
	}

	public AnimatedSprite(String path, int upsBetweenFrames, int framesX, int framesY) {
		this(path, upsBetweenFrames, framesX, framesY, 0, framesX * framesY - 1);
	}

	public int getWidth() {
		return sprites[currFrame].getWidth();
	}

	public int getHeight() {
		return sprites[currFrame].getHeight();
	}
	
	public int getFrame() {
		return currFrame;
	}

	public Sprite[] loadAnimation(String path, int framesX, int framesY, int frameStart, int frameEnd) {
		Sprite[] spriteArray = new Sprite[frameEnd - frameStart + 1];

		BufferedImage img = Sprite.loadImageFile(path);
		
		int tileWidth = img.getWidth() / framesX, tileHeight = img.getHeight() / framesY;

		int workingFrame = 0;
		for (int y = 0; y < framesY; y++) {
			for (int x = 0; x < framesX; x++) {
				if (workingFrame >= frameStart && workingFrame <= frameEnd) {
					spriteArray[workingFrame - frameStart] = new Sprite(img.getSubimage(x * tileWidth, y * tileHeight, tileWidth, tileHeight));
				}
				workingFrame++;
			}
		}

		return spriteArray;
	}

	public void start() {
		if (upsBetweenFrames >= 0) {
			taskID = TaskScheduler.runTask(new Task(upsBetweenFrames) {
				public void run() {
					nextFrame();
				}

				public boolean shouldRepeat() {
					return true;
				}
			});
		}
	}

	public void stop() {
		if (upsBetweenFrames >= 0) {
			TaskScheduler.removeTask(taskID);
		}
	}

	public void nextFrame() {
		currFrame += 1;
		if (currFrame == sprites.length) {
			currFrame = 0;
		}
	}

	public void prevFrame() {
		currFrame -= 1;
		if (currFrame < 0) {
			currFrame = sprites.length - 1;
		}
	}

	public void setFrame(int frame) {
		if (frame < 0) {
			currFrame = 0;
		} else if (frame >= sprites.length) {
			currFrame = sprites.length - 1;
		} else {
			currFrame = frame;
		}
	}

	@Override
	public int[] getPixels() {
		return sprites[currFrame].getPixels();
	}

	public void setFlipX(boolean flipX) {
		for (int i = 0; i < sprites.length; i++) {
			sprites[i].setFlipX(flipX);
		}
	}
	
	public void setFlipY(boolean flipY) {
		for (int i = 0; i < sprites.length; i++) {
			sprites[i].setFlipY(flipY);
		}
	}
	
	@Override
	public String getName() {
		return name;
	}

	@Override
	public void setName(String name) {
		this.name = name;
	}

	@Override
	public void scale(int scale) {
		for (int i = 0; i < sprites.length; i++) {
			sprites[i].scale(scale);
		}
	}
}
