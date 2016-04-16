package uk.co.newagedev.jnade.audio;

import javax.sound.sampled.Clip;

public class AudioClip {

	private Clip clip;
	private String name;

	protected AudioClip(Clip clip) {
		this.clip = clip;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public boolean isPlaying() {
		return clip.isRunning();
	}

	public void play() {
		if (!isPlaying()) {
			clip.setFramePosition(0);
			clip.start();
		}
	}

	public void stop() {
		clip.stop();
	}
	
	public void cleanUp() {
		clip.flush();
		clip.close();
	}
}
