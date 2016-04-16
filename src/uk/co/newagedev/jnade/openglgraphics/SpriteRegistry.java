package uk.co.newagedev.jnade.openglgraphics;

import java.util.ArrayList;

import uk.co.newagedev.jnade.Main;
import uk.co.newagedev.jnade.util.Logger;

public class SpriteRegistry {

	private static ArrayList<Sprite> sprites = new ArrayList<Sprite>();
	
	public static void registerSprite(String name, String path) {
		Sprite sprite = Main.getScreen().loadImageFromFile(path);
		if (sprite != null) {
			sprite.setName(name);
			sprites.add(sprite);
			Logger.info("\"" + path + "\" loaded as \"" + name + "\"");
		} else {
			Logger.error("The path \"" + path + "\" couldn't be loaded.");
		}
	}
	
	public static void registerSprite(String name, Sprite sprite) {
		sprite.setName(name);
		sprites.add(sprite);
	}
	
	public static Sprite getSprite(String name) {
		for (Sprite sprite : sprites) {
			if (sprite.getName().equalsIgnoreCase(name))
				return sprite;
		}
		return null;
	}
	
	public static boolean doesSpriteExist(String name) {
		return getSprite(name) != null;
	}
	
	public static void removeSprite(String name) {
		if (doesSpriteExist(name)) {
			Sprite sprite = getSprite(name);
			sprite.release();
			sprites.remove(sprite);
		}
	}
	
	public static void clear() {
		for (Sprite sprite : sprites) {
			sprite.release();
		}
		sprites.clear();
	}
}