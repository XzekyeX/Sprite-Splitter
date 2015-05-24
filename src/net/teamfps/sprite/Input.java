package net.teamfps.sprite;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;

import javax.swing.event.MouseInputListener;

/**
 * @author Zekye
 *
 */
public class Input implements KeyListener, MouseListener, MouseInputListener, MouseMotionListener, MouseWheelListener {
	public static boolean ML = false;
	public static boolean MC = false;
	public static boolean MR = false;
	public static int DY, DX;
	public static int SCROLL = -1;
	private static boolean[] keys = new boolean[65536];

	@Override
	public void mouseWheelMoved(MouseWheelEvent e) {
		SCROLL = e.getWheelRotation();
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		DX = e.getX();
		DY = e.getY();
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		DX = e.getX();
		DY = e.getY();
	}

	@Override
	public void mouseClicked(MouseEvent e) {
	}

	@Override
	public void mouseEntered(MouseEvent e) {
	}

	@Override
	public void mouseExited(MouseEvent e) {
	}

	@Override
	public void mousePressed(MouseEvent e) {
		if (e.getButton() == MouseEvent.BUTTON1) {
			ML = true;
		}
		if (e.getButton() == MouseEvent.BUTTON2) {
			MC = true;
		}
		if (e.getButton() == MouseEvent.BUTTON3) {
			MR = true;
		}
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		if (e.getButton() == MouseEvent.BUTTON1) {
			ML = false;
		}
		if (e.getButton() == MouseEvent.BUTTON2) {
			MC = false;
		}
		if (e.getButton() == MouseEvent.BUTTON3) {
			MR = false;
		}
	}

	@Override
	public void keyPressed(KeyEvent e) {
		int key = e.getKeyCode();
		keys[key] = true;
	}

	@Override
	public void keyReleased(KeyEvent e) {
		int key = e.getKeyCode();
		keys[key] = false;
	}

	@Override
	public void keyTyped(KeyEvent e) {
	}

	public static boolean isKeyDown(int key) {
		return keys[key];
	}

}
