package uk.co.newagedev.jnade.util;

public class Location {
	
	public float x, y;
	
	public Location(float x, float y) {
		moveTo(x, y);
	}
	
	public Location() {
		this.x = 0;
		this.y = 0;
	}
	
	public void moveTo(float x, float y) {
		this.x = x;
		this.y = y;
	}
	
	public void moveBy(float x, float y) {
		this.x += x;
		this.y += y;
	}
	
	public Location getRelativeTo(Location root) {
		return new Location(x + root.x, y + root.y);
	}
	
	public boolean equals(Location loc) {
		return this.x == loc.x && this.y == loc.y;
	}
	
	public String toString() {
		return "(" + x + ", " + y + ")";
	}
	
	public Location clone() {
		return new Location(x, y);
	}
}
