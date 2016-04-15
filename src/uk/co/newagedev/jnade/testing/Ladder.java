package uk.co.newagedev.jnade.testing;

import java.awt.Color;

import uk.co.newagedev.jnade.Main;
import uk.co.newagedev.jnade.map.MapCustom;
import uk.co.newagedev.jnade.util.Location;

public class Ladder extends MapCustom {

	private int height;
	private static final Color BLUE = new Color(20, 243, 255), BLACK = new Color(0, 0, 0);
	
	public Ladder(int height, Location location) {
		super(location);
		this.height = height;
	}
	
	public void setHeight(int height) {
		this.height = height;
	}

	public int getHeight() {
		return height;
	}
	
	@Override
	public void render() {
		Main.screen.renderRect(getLocation().x, getLocation().y, 8, height, BLACK);
		Main.screen.renderRect(getLocation().x, getLocation().y, 1, height, BLUE);
		Main.screen.renderRect(getLocation().x + 7, getLocation().y, 1, height, BLUE);
		for (int i = (int) getLocation().y; i < getLocation().y + height; i++) {
			if ((i - 1) % 4 == 0) {
				Main.screen.renderRect(getLocation().x, i, 8, 1, BLUE);
			}
		}
	}
}
