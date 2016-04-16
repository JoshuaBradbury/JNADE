package uk.co.newagedev.jnade.audio;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

public class AudioRegistry {

	private ArrayList<AudioClip> audioClips = new ArrayList<AudioClip>();

	public void registerAudioClip(String name, File fileName) {
		if (!audioClipExists(name)) {
			try {
				AudioInputStream stream = AudioSystem.getAudioInputStream(fileName);

				Clip clip = AudioSystem.getClip();
				clip.open(stream);

				AudioClip audioClip = new AudioClip(clip);
				audioClip.setName(name);
				audioClips.add(audioClip);
			} catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
				e.printStackTrace();
			}
		}
	}

	public boolean audioClipExists(String name) {
		return getAudioClip(name) != null;
	}

	public AudioClip getAudioClip(String name) {
		for (AudioClip audioClip : audioClips) {
			if (audioClip.getName().equals(name)) {
				return audioClip;
			}
		}
		return null;
	}

	public void removeAudioClip(String name) {
		AudioClip audioClip = getAudioClip(name);
		if (audioClip != null) {
			audioClips.remove(audioClip);
			if (audioClip.isPlaying())
				audioClip.stop();
			audioClip.cleanUp();
		}
	}
	
	public void cleanUp() {
		for (AudioClip clip : audioClips) {
			clip.cleanUp();
		}
		audioClips.clear();
	}
}
