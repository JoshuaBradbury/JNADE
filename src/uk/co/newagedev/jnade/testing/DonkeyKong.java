package uk.co.newagedev.jnade.testing;

import java.awt.Color;
import java.awt.event.KeyEvent;

import uk.co.newagedev.jnade.Game;
import uk.co.newagedev.jnade.Main;
import uk.co.newagedev.jnade.Task;
import uk.co.newagedev.jnade.TaskScheduler;
import uk.co.newagedev.jnade.graphics.AnimatedSprite;
import uk.co.newagedev.jnade.graphics.Sprite;
import uk.co.newagedev.jnade.input.KeyBinding;
import uk.co.newagedev.jnade.map.Map;

public class DonkeyKong implements Game {

	public static Main main;
	public Map currentMap;

	public static void main(String[] args) {
		main = new Main();

		main.setScreenSize(224, 256);
		main.setScreenScale(2);
		main.setTitle("Donkey Kong");

		main.setBackgroundColour(Color.BLACK);

		DonkeyKong kong = new DonkeyKong();

		main.setGame(kong);

		main.start();
	}

	public void init() {
		KeyBinding.bindKey("exit", KeyEvent.VK_ESCAPE);

		KeyBinding.bindKey("jumpManRunLeft", KeyEvent.VK_LEFT);
		KeyBinding.bindKey("jumpManRunRight", KeyEvent.VK_RIGHT);

		currentMap = new StartAnimation();
		currentMap.setGame(this);

		Main.RENDERABLE_REGISTRY.setRemoveColour(new Color(0, 0, 0));

		Main.RENDERABLE_REGISTRY.registerRenderable("dkTop", new AnimatedSprite("assets/dkTop.png", 2, 1));

		AnimatedSprite dkClimb = new AnimatedSprite("assets/dkClimb.png", 4, 1);
		dkClimb.setFrame(2);
		Main.RENDERABLE_REGISTRY.registerRenderable("dkClimb", dkClimb);

		Main.RENDERABLE_REGISTRY.registerRenderable("platform", new Sprite("assets/platform.png"));
		Main.RENDERABLE_REGISTRY.registerRenderable("ladyTop", new Sprite("assets/ladyTop.png"));

		Main.RENDERABLE_REGISTRY.registerRenderable("ladyHelp", new AnimatedSprite("assets/ladyHelp.png", 1, 3));
		
		TaskScheduler.runTask(new Task(300) {
			public void run() {
				currentMap.init();
			}
		});
	}

	public void postStartAnimation() {
		currentMap = new BarrelsMap();
		currentMap.setGame(this);
		currentMap.init();
	}

	public void update() {
		currentMap.update();
		if (KeyBinding.isKeyDown("exit")) {
			main.stop();
		}
	}

	public void render() {
		currentMap.render();
	}
}
