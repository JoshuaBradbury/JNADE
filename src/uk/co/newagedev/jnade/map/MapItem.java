package uk.co.newagedev.jnade.map;

import uk.co.newagedev.jnade.Main;
import uk.co.newagedev.jnade.graphics.Renderable;
import uk.co.newagedev.jnade.util.Location;


public class MapItem extends MapObject {

	private String renderableName;
	
	public MapItem(String renderableName, Location location) {
		setLocation(location);
		this.renderableName = renderableName;
	}
	
	public String getRenderable() {
		return renderableName;
	}

	public void setRenderable(String renderableName) {
		this.renderableName = renderableName;
	}
	
	public void init() {
		
	}

	public void render() {
		Renderable renderable = Main.RENDERABLE_REGISTRY.getRenderable(renderableName);
		Main.screen.renderImage((int) getLocation().x, (int) getLocation().y, renderable.getWidth(), renderable.getHeight(), renderable.getPixels());
	}
	
	public void update() {
	}
}
