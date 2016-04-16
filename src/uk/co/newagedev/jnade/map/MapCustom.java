package uk.co.newagedev.jnade.map;

import uk.co.newagedev.jnade.util.Vector2f;

public abstract class MapCustom extends MapItem {

	public MapCustom(Vector2f location) {
		super("", location);
	}
	
	@Override
	public abstract void render();
}
