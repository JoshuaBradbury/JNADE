package uk.co.newagedev.jnade.graphics;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

public class RenderableRegistry {

	private final List<Renderable> renderables = new ArrayList<Renderable>();
	private Color removeColour;
	private int scale = 1;

	public void registerRenderable(String name, Renderable renderable) {
		if (!renderableExists(name)) {
			renderable.setName(name);
			renderable.scale(scale);
			renderables.add(renderable);
		}
	}
	
	public void setRemoveColour(Color removeColour) {
		this.removeColour = removeColour;
	}
	
	public Color getRemoveColour() {
		return removeColour;
	}
	
	public void scaleAll(int scale) {
		this.scale = scale;
		for (Renderable renderable : renderables) {
			renderable.scale(scale);
		}
	}

	public boolean renderableExists(String name) {
		return getRenderable(name) != null;
	}
	
	public Renderable getRenderable(String name) {
		for (Renderable renderable : renderables) {
			if (renderable.getName().equals(name)) {
				return renderable;
			}
		}
		return null;
	}
	
	public void removeRenderable(String name) {
		Renderable renderable = getRenderable(name);
		if (renderable != null) {
			renderables.remove(renderable);
		}
	}
}
