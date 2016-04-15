package uk.co.newagedev.jnade.map;

import uk.co.newagedev.jnade.util.Location;

public abstract class MapCustom extends MapItem {

	public MapCustom(Location location) {
		super("", location);
	}
	
	@Override
	public abstract void render();
}
