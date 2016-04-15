package uk.co.newagedev.jnade.testing;

import uk.co.newagedev.jnade.Main;
import uk.co.newagedev.jnade.Task;
import uk.co.newagedev.jnade.TaskScheduler;
import uk.co.newagedev.jnade.graphics.AnimatedSprite;
import uk.co.newagedev.jnade.map.Map;
import uk.co.newagedev.jnade.map.MapItem;
import uk.co.newagedev.jnade.map.MapObject;
import uk.co.newagedev.jnade.util.Location;

public class StartAnimation extends Map {

	private void dkJump(MapItem dkClimb, int offset) {
		TaskScheduler.runTask(new Task(2, 358 + offset) {
			private int count = 5;

			public void run() {
				dkClimb.getLocation().x -= 1;
				dkClimb.getLocation().y -= 1;
				count--;
			}

			public boolean shouldRepeat() {
				return count > 0;
			}
		});
		
		TaskScheduler.runTask(new Task(2, 369 + offset) {
			private int[] yChange = new int[] {0, 1, 0, 0, -1};
			private int count = 0;
			
			public void run() {
				dkClimb.getLocation().x -= 1;
				dkClimb.getLocation().y -= yChange[count];
				count++;
			}
			
			public boolean shouldRepeat() {
				return count < yChange.length;
			}
		});
		
		TaskScheduler.runTask(new Task(2, 378 + offset) {
			private boolean first = true;
			private int count = 6;
			
			public void run() {
				dkClimb.getLocation().x -= 1;
				if (!first) {
					dkClimb.getLocation().y += 1;
				}
				first = false;
				count--;
			}
			
			public boolean shouldRepeat() {
				return count > 0;
			}
		});

	}
	
	public void init() {
		registerObject(new MapItem("platform", new Location(88, 56)));
		registerObject(new MapItem("platform", new Location(104, 56)));
		registerObject(new MapItem("platform", new Location(120, 56)));

		MapItem dkClimb = new MapItem("dkClimb", new Location(107, 205));

		Platform plat0 = new Platform(new Location(0, 84), true, false);
		Platform plat1 = new Platform(new Location(224, 109), false, false);
		Platform plat2 = new Platform(new Location(0, 142), true, false);
		Platform plat3 = new Platform(new Location(224, 175), false, false);
		Platform plat4 = new Platform(new Location(0, 208), true, false);
		Platform plat5 = new Platform(new Location(224, 241), false, true);

		Ladder ladder0 = new Ladder(20, new Location(128, 64));
		Ladder ladder1 = new Ladder(52, new Location(80, 32));
		Ladder ladder2 = new Ladder(52, new Location(64, 32));
		Ladder ladder3 = new Ladder(149, new Location(112, 92));
		Ladder ladder4 = new Ladder(149, new Location(128, 92));

		registerObject(plat0);
		registerObject(plat1);
		registerObject(plat2);
		registerObject(plat3);
		registerObject(plat4);
		registerObject(plat5);

		registerObject(ladder0);
		registerObject(ladder1);
		registerObject(ladder2);
		registerObject(ladder3);
		registerObject(ladder4);

		registerObject(dkClimb);

		TaskScheduler.runTask(new Task(15, 15) {
			private int[] list = new int[] { 3, 1, 1, 1, 1, 1, 3, 1, 3, 1, 1, 3, 3, 3, 3, 3 };
			private int count = 0;

			public void run() {
				((AnimatedSprite) Main.RENDERABLE_REGISTRY.getRenderable(dkClimb.getRenderable())).setFrame(list[count]);
				dkClimb.getLocation().y -= 2;
				dkClimb.getLocation().x -= 1;
				count++;
			}

			public boolean shouldRepeat() {
				return count < list.length;
			}
		});

		TaskScheduler.runTask(new Task(15, 22) {
			private int[] list = new int[] { 2, 2, 0, 0, 0, 0, 0, 2, 2, 0, 2, 0, 2, 2, 2 };
			private int count;

			public void run() {
				((AnimatedSprite) Main.RENDERABLE_REGISTRY.getRenderable(dkClimb.getRenderable())).setFrame(list[count]);
				dkClimb.getLocation().y -= 6;
				dkClimb.getLocation().x += 1;
				count++;
			}

			public boolean shouldRepeat() {
				return count < list.length;
			}
		});

		TaskScheduler.runTask(new Task(45) {
			public void run() {
				ladder3.setHeight(ladder3.getHeight() - 1);
				ladder4.setHeight(ladder3.getHeight());
			}
		});

		TaskScheduler.runTask(new Task(15, 45) {
			public void run() {
				ladder3.setHeight(ladder3.getHeight() - 8);
				ladder4.setHeight(ladder3.getHeight());
			}

			public boolean shouldRepeat() {
				return ladder3.getHeight() > 44;
			}
		});

		TaskScheduler.runTask(new Task(2, 287) {
			private float offset = 3.0f;
			private int count = 0;

			public void run() {
				dkClimb.getLocation().y -= offset;
				count++;
				if (count == 7) {
					offset--;
				} else if (count == 13) {
					offset--;
				} else if (count == 17) {
					offset--;
				}
			}

			public boolean shouldRepeat() {
				return offset > 0;
			}
		});

		TaskScheduler.runTask(new Task(2, 322) {
			private int count = 4;

			public void run() {
				dkClimb.getLocation().y++;
				count--;
			}

			public boolean shouldRepeat() {
				return count > 0;
			}
		});

		TaskScheduler.runTask(new Task(330) {
			public void run() {
				removeObject(ladder3);
				removeObject(ladder4);
				int i = 0;
				for (MapObject object : plat0.getObjects()) {
					if (i - 8 > 0) {
						object.getLocation().moveBy(0, i - 8);
					}
					i += 1;
				}
			}
		});

		TaskScheduler.runTask(new Task(331) {
			public void run() {
				dkClimb.setRenderable("dkTop");
				dkClimb.getLocation().y += 2;
				dkClimb.getLocation().x -= 2;
				registerObject(new MapItem("ladyTop", new Location(88, 34)));
				removeObject(dkClimb);
				registerObject(dkClimb);
			}
		});

		dkJump(dkClimb, 0);

		TaskScheduler.runTask(new Task(390) {
			public void run() {
				plat1.fall();
			}
		});
		
		dkJump(dkClimb, 31);
		
		TaskScheduler.runTask(new Task(421) {
			public void run() {
				plat2.fall();
			}
		});
		
		dkJump(dkClimb, 62);

		TaskScheduler.runTask(new Task(452) {
			public void run() {
				plat3.fall();
			}
		});
		
		dkJump(dkClimb, 93);

		TaskScheduler.runTask(new Task(483) {
			public void run() {
				plat4.fall();
			}
		});
		
		dkJump(dkClimb, 124);

		TaskScheduler.runTask(new Task(514) {
			public void run() {
				int i = 0;
				for (MapObject object : plat5.getObjects()) {
					object.getLocation().moveBy(0, i);
					if (i < 7) {
						i += 1;
					}
				}
			}
		});
		
		TaskScheduler.runTask(new Task(544) {
			public void run() {
				((AnimatedSprite) Main.RENDERABLE_REGISTRY.getRenderable(dkClimb.getRenderable())).setFrame(1);
			}
		});
		
		TaskScheduler.runTask(new Task(574) {
			public void run() {
				DonkeyKong dk = (DonkeyKong) getGame();
				dk.postStartAnimation();
			}
		});
	}

}
