package uk.co.newagedev.jnade.util;

import uk.co.newagedev.jnade.map.MapObject;

public class CollisionBox {

	private MapObject parent;
	private int width, height;
	private boolean collidable;

	public CollisionBox(MapObject parent, int width, int height) {
		this(parent, width, height, true);
	}

	public CollisionBox(MapObject parent, int width, int height, boolean collidable) {
		this.parent = parent;
		this.width = width;
		this.height = height;
		this.collidable = collidable;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	public boolean isColliding(CollisionBox box) {
		if (collidable) {
			Vector2f loc = parent.getLocation();
			Vector2f boxLoc = box.parent.getLocation();

			int[][] boxPoints = new int[][] { { (int) boxLoc.x, (int) boxLoc.y }, { (int) boxLoc.x + box.getWidth(), (int) boxLoc.y }, { (int) boxLoc.x + box.getWidth(), (int) boxLoc.y + box.getHeight() }, { (int) boxLoc.x, (int) boxLoc.y + box.getHeight() } };
			boolean colliding = false;

			for (int[] point : boxPoints) {
				if (point[0] > loc.x && point[0] < loc.x + width) {
					if (point[1] > loc.y && point[1] < loc.y + height) {
						colliding = true;
					}
				}
			}
			return colliding;
		} else {
			return false;
		}
	}
}
