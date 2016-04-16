package uk.co.newagedev.jnade.map;

import java.util.ArrayList;
import java.util.List;

import uk.co.newagedev.jnade.Game;

public abstract class Map {

	private List<MapObject> objects = new ArrayList<MapObject>();
	private Game game;

	public void setGame(Game game) {
		this.game = game;
	}

	public Game getGame() {
		return game;
	}

	public abstract void init();

	public void render() {
		for (MapObject obj : objects) {
			obj.render();
		}
	}

	public void update() {
		for (MapObject obj : objects) {
			obj.update();
		}
	}

	public void registerObject(MapObject object) {
		if (!objects.contains(object)) {
			objects.add(object);
			object.init();
			object.setMap(this);
		}
	}

	public void removeObject(MapObject object) {
		if (objects.contains(object)) {
			objects.remove(object);
			object.setMap(null);
		}
	}
}
