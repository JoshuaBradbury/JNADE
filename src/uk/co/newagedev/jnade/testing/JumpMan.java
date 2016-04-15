package uk.co.newagedev.jnade.testing;

import uk.co.newagedev.jnade.Main;
import uk.co.newagedev.jnade.graphics.AnimatedSprite;
import uk.co.newagedev.jnade.input.KeyBinding;
import uk.co.newagedev.jnade.map.MapItem;
import uk.co.newagedev.jnade.util.Location;

public class JumpMan extends MapItem {

	private AnimatedSprite running = new AnimatedSprite("assets/jumpManRun.png", 4, 1);
	
	private static final String RUN_ANIM = "jumpManRun";
	
	public JumpMan(Location location) {
		super(RUN_ANIM, location);
		Main.RENDERABLE_REGISTRY.registerRenderable(RUN_ANIM, running);
	}

	private int count = 0;
	
	public void update() {
		boolean left = KeyBinding.isKeyDown("jumpManRunLeft"), right = KeyBinding.isKeyDown("jumpManRunRight");
		if (left && !right) {
			getLocation().x -= 1;
			count++;
			if (count % 3 == 0) {
				count -= 3;
				running.setFlipX(true);
				running.nextFrame();
			}
		} else if (right && !left) {
			getLocation().x += 1;
			count++;
			if (count % 3 == 0) {
				count -= 3;
				running.setFlipX(false);
				running.nextFrame();
			}
		}
	}
}
