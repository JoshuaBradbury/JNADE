package uk.co.newagedev.jnade.testing;

import uk.co.newagedev.jnade.map.MapItem;
import uk.co.newagedev.jnade.map.MapObject;
import uk.co.newagedev.jnade.map.MapStructure;
import uk.co.newagedev.jnade.util.Location;

public class Platform extends MapStructure {
	
	private boolean left, extra;
	
	public Platform(Location location, boolean left, boolean extra) {
		super(location);
		this.left = left;
		this.extra = extra;
	}
	
	public void fall() {
		int i = 0;
		for (MapObject object : getObjects()) {
			object.getLocation().moveBy(0, i);
			i += 1;
		}
	}
	
	public void init() {
		for (int i = 0; i < (extra ? 14 : 13); i++) {
			addObject(new MapItem("platform", new Location((left ? i : -i - 1) * 16, 0)));
		}
	}
}
