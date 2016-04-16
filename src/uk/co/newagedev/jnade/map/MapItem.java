package uk.co.newagedev.jnade.map;

import uk.co.newagedev.jnade.Main;
import uk.co.newagedev.jnade.openglgraphics.AnimatedSprite;
import uk.co.newagedev.jnade.openglgraphics.Sprite;
import uk.co.newagedev.jnade.openglgraphics.SpriteRegistry;
import uk.co.newagedev.jnade.util.Vector2f;

public class MapItem extends MapObject {

	private String renderableName;
	private boolean hidden;

	public MapItem(String renderableName, Vector2f location) {
		setLocation(location);
		this.renderableName = renderableName;
	}

	public String getRenderable() {
		return renderableName;
	}

	public void setRenderable(String renderableName) {
		this.renderableName = renderableName;
	}

	public void hide() {
		hidden = true;
	}

	public void show() {
		hidden = false;
	}

	public boolean isHidden() {
		return hidden;
	}

	public void init() {

	}

	public void render() {
		if (!hidden) {
			String spriteName = "";
			Sprite sprite = SpriteRegistry.getSprite(renderableName);
			if (sprite instanceof AnimatedSprite)
				spriteName = ((AnimatedSprite) sprite).getSprite().getName();
			else
				spriteName = renderableName;
			Main.getScreen().renderSpriteIgnoringCamera(spriteName, getLocation(), sprite.getSize(), new float[] { sprite.shouldFlipX() ? 1.0f : 0.0f, sprite.shouldFlipX() ? 0.0f : 1.0f, sprite.shouldFlipY() ? 1.0f : 0.0f, sprite.shouldFlipY() ? 0.0f : 1.0f }, new float[] { 1.0f, 1.0f, 1.0f, 1.0f });
		}
	}

	public void update() {
	}
}
