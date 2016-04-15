package uk.co.newagedev.jnade.map;

import uk.co.newagedev.jnade.util.Location;

public abstract class MapObject {

	private Map map;
	private Location location;
	
	public abstract void render();
	
	public abstract void update();
	
	public abstract void init();
	
	public Location getLocation() {
		return location;
	}
	
	public void setLocation(Location location) {
		this.location = location;
	}
	
	public void setMap(Map map) {
		this.map = map;
	}
	
	public Map getMap() {
		return map;
	}
	
}
