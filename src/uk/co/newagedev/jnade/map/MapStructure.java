package uk.co.newagedev.jnade.map;

import java.util.ArrayList;
import java.util.List;

import uk.co.newagedev.jnade.util.Vector2f;

public abstract class MapStructure extends MapObject {

	private List<MapObject> objects = new ArrayList<MapObject>();
	
	public void addObject(MapObject object) {
		objects.add(objects.size(), object);
	}
	
	public void removeObject(MapObject object) {
		objects.remove(object);
	}
	
	public List<MapObject> getObjects() {
		return objects;
	}
	
	public MapStructure(Vector2f location) {
		setLocation(location);
	}
	
	public void render() {
		for (MapObject object : objects) {
			Vector2f loc = object.getLocation();
			object.setLocation(object.getLocation().add(getLocation()));
			object.render();
			object.setLocation(loc);
		}
	}
	
	public void update() {
		for (MapObject object : objects) {
			Vector2f loc = object.getLocation();
			object.setLocation(object.getLocation().add(getLocation()));
			object.update();
			object.setLocation(loc);
		}
	}
}
