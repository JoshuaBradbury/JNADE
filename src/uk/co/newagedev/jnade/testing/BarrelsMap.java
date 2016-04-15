package uk.co.newagedev.jnade.testing;

import uk.co.newagedev.jnade.Main;
import uk.co.newagedev.jnade.Task;
import uk.co.newagedev.jnade.TaskScheduler;
import uk.co.newagedev.jnade.graphics.AnimatedSprite;
import uk.co.newagedev.jnade.map.Map;
import uk.co.newagedev.jnade.map.MapItem;
import uk.co.newagedev.jnade.map.MapObject;
import uk.co.newagedev.jnade.util.Location;

public class BarrelsMap extends Map {
	
	public void init() {
		registerObject(new MapItem("platform", new Location(88, 56)));
		registerObject(new MapItem("platform", new Location(104, 56)));
		registerObject(new MapItem("platform", new Location(120, 56)));

		Platform plat0 = new Platform(new Location(0, 84), true, false);
		Platform plat1 = new Platform(new Location(224, 109), false, false);
		Platform plat2 = new Platform(new Location(0, 142), true, false);
		Platform plat3 = new Platform(new Location(224, 175), false, false);
		Platform plat4 = new Platform(new Location(0, 208), true, false);
		Platform plat5 = new Platform(new Location(224, 241), false, true);

		registerObject(new Ladder(20, new Location(128, 64)));
		registerObject(new Ladder(52, new Location(80, 32)));
		registerObject(new Ladder(52, new Location(64, 32)));
		registerObject(new Ladder(16, new Location(184, 95)));
		registerObject(new Ladder(4, new Location(88, 92)));
		registerObject(new Ladder(13, new Location(88, 104)));
		registerObject(new Ladder(20, new Location(72, 126)));
		registerObject(new Ladder(16, new Location(32, 128)));
		registerObject(new Ladder(8, new Location(168, 120)));
		registerObject(new Ladder(8, new Location(168, 144)));
		registerObject(new Ladder(6, new Location(64, 154)));
		registerObject(new Ladder(8, new Location(64, 176)));
		registerObject(new Ladder(24, new Location(112, 157)));
		registerObject(new Ladder(16, new Location(184, 161)));
		registerObject(new Ladder(16, new Location(32, 194)));
		registerObject(new Ladder(24, new Location(96, 190)));
		registerObject(new Ladder(3, new Location(80, 221)));
		registerObject(new Ladder(8, new Location(80, 240)));
		registerObject(new Ladder(16, new Location(184, 227)));

		registerObject(plat0);
		registerObject(plat1);
		registerObject(plat2);
		registerObject(plat3);
		registerObject(plat4);
		registerObject(plat5);
		
		MapItem lady = new MapItem("ladyHelp", new Location(88, 32));
		
		registerObject(lady);
		
		JumpMan man = new JumpMan(new Location(50, 240));
		
		registerObject(man);

		((AnimatedSprite) Main.RENDERABLE_REGISTRY.getRenderable(lady.getRenderable())).setFrame(2);
		
		int x = 0;
		for (MapObject object : plat0.getObjects()) {
			if (x - 8 > 0) {
				object.getLocation().moveBy(0, x - 8);
			}
			x += 1;
		}

		plat1.fall();
		plat2.fall();
		plat3.fall();
		plat4.fall();

		int y = 0;
		for (MapObject object : plat5.getObjects()) {
			object.getLocation().moveBy(0, y);
			if (y < 7) {
				y += 1;
			}
		}
		
		
		for (int i = 0; i < 4; i++) {
			TaskScheduler.runTask(new Task(255, 8 * (i * 2)) {
				public void run() {
					((AnimatedSprite) Main.RENDERABLE_REGISTRY.getRenderable(lady.getRenderable())).setFrame(0);
				}

				public boolean shouldRepeat() {
					return true;
				}
			});
			TaskScheduler.runTask(new Task(255, 8 * (i * 2 + 1)) {
				public void run() {
					((AnimatedSprite) Main.RENDERABLE_REGISTRY.getRenderable(lady.getRenderable())).setFrame(1);							
				}
				
				public boolean shouldRepeat() {
					return true;
				}
			});
		}
		
		TaskScheduler.runTask(new Task(255, 64) {
			public void run() {
				((AnimatedSprite) Main.RENDERABLE_REGISTRY.getRenderable(lady.getRenderable())).setFrame(2);							
			}
			
			public boolean shouldRepeat() {
				return true;
			}
		});
	}
}
