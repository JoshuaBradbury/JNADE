package uk.co.newagedev.jnade.openglgraphics;

import java.io.IOException;

import uk.co.newagedev.jnade.Main;
import uk.co.newagedev.jnade.Task;
import uk.co.newagedev.jnade.TaskScheduler;
import uk.co.newagedev.jnade.util.FileUtil;

public class AnimatedSprite extends Sprite {

	private Sprite[] sprites;
	private String name;
	private int currFrame, upsBetweenFrames, taskID;

	public AnimatedSprite(String path, String name, int upsBetweenFrames, int frameStart, int frameEnd) {
		this.upsBetweenFrames = upsBetweenFrames;
		setName(name);
		try {
			sprites = loadAnimation(path, frameStart, frameEnd);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public AnimatedSprite(String path, String name, int frameStart, int frameEnd) {
		this(path, name, -1, frameStart, frameEnd);
	}

	public AnimatedSprite(String path, String name) {
		this(path, name, 0, -1);
	}

	public AnimatedSprite(String path, String name, int upsBetweenFrames) {
		this(path, name, upsBetweenFrames, 0, -1);
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

	public Sprite[] loadAnimation(String path, int frameStart, int frameEnd) throws IOException {
		String[] files = FileUtil.getAllFilesInFolder(path);
		if (frameEnd == -1) frameEnd = files.length - 1;
		Sprite[] spriteArray = new Sprite[frameEnd - frameStart + 1];

		for (int i = frameStart; i <= frameEnd; i++) {
				spriteArray[i - frameStart] = Main.getScreen().loadImageFromFile(path + files[i]);
				SpriteRegistry.registerSprite(name + i, spriteArray[i - frameStart]);
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
	
	public Sprite getSprite() {
		return sprites[currFrame];
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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
