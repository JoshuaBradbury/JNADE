package uk.co.newagedev.jnade.input;

import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;

import javax.swing.event.MouseInputListener;

import uk.co.newagedev.jnade.Main;

public class Mouse implements MouseMotionListener, MouseInputListener {

	public static final int LEFT_BUTTON = 1, RIGHT_BUTTON = 3, MIDDLE_BUTTON = 2, BUTTON_COUNT = 4;
	
	private static boolean[] pressing = new boolean[BUTTON_COUNT];
	
	private static boolean[] down = new boolean[BUTTON_COUNT];
	
	private static boolean[] releasing = new boolean[BUTTON_COUNT];
	
	private static boolean[] awtDown = new boolean[BUTTON_COUNT];
	
	private static int tmx = 0, tmy = 0, mx = 0, my = 0, mdx = 0, mdy = 0, updatesSinceLastMovement = 0, updatesSinceLastButton = 0;
	
	public static Main MAIN_INSTANCE;
	
	public static int getMouseX() {
		return mx;
	}
	
	public static int getMouseY() {
		return MAIN_INSTANCE.getHeight() - my;
	}

	public static int getChangeInMouseX() {
		return mdx;
	}

	public static int getChangeInMouseY() {
		return mdy;
	}

	public static long getMillisSinceLastMovement() {
		return updatesSinceLastMovement;
	}
	
	public static long getMillisSinceLastButton() {
		return updatesSinceLastButton;
	}

	public static void update() {
		int t = 0;
		for (int i = 0; i < BUTTON_COUNT; i++) {
			boolean bd = awtDown[i];
			if (!pressing[i] && !down[i] && bd) {
				pressing[i] = true;
			} else if (pressing[i] && !down[i] && bd) {
				pressing[i] = false;
				down[i] = true;
			} else if (down[i] && !releasing[i] && !bd) {
				down[i] = false;
				releasing[i] = true;
			} else if (!down[i] && releasing[i] && !bd) {
				releasing[i] = false;
			} else {
				t += 1;
			}
			
			if (pressing[i] && releasing[i]) {
				releasing[i] = false;
			}
			if(releasing[i] && down[i]) {
				down[i] = false;
			}
			if (pressing[i] && down[i]) {
				down[i] = false;
			}
		}
		
		if (t == BUTTON_COUNT) {
			updatesSinceLastButton += 1;
		} else {
			updatesSinceLastButton = 0;
		}
		
		int oldmx = mx;
		int oldmy = my;
		mx = tmx;
		my = tmy;
		mdx = mx - oldmx;
		mdy = my - oldmy;
		if (mdx == 0 && mdy == 0) {
			updatesSinceLastMovement += 1;
		} else {
			updatesSinceLastMovement = 0;
		}
	}
	
	public static boolean isButtonPressing(int index) {
		return pressing[index];
	}

	public static boolean isButtonDown(int index) {
		return down[index];
	}
	
	public static boolean isButtonReleasing(int index) {
		return releasing[index];
	}

	public static String getButtonStates(int index) {
		return "Pressing: " + String.valueOf(pressing[index]) + ", Down: " + String.valueOf(down[index]) + ", Releasing: " + String.valueOf(releasing[index]);
	}

	@Override
	public void mouseClicked(MouseEvent me) {}

	@Override
	public void mouseEntered(MouseEvent me) {}

	@Override
	public void mouseExited(MouseEvent me) {}

	@Override
	public void mousePressed(MouseEvent me) {
		tmx = me.getX();
		tmy = me.getY();
		awtDown[me.getButton()] = true;
	}

	@Override
	public void mouseReleased(MouseEvent me) {
		tmx = me.getX();
		tmy = me.getY();
		awtDown[me.getButton()] = false;
	}

	@Override
	public void mouseDragged(MouseEvent me) {
		tmx = me.getX();
		tmy = me.getY();
	}

	@Override
	public void mouseMoved(MouseEvent me) {
		tmx = me.getX();
		tmy = me.getY();
	}
}
