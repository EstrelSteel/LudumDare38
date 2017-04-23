package com.estrelsteel.ld38.handler;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import com.estrelsteel.ld38.LD38;

public class KeyboardHandler implements KeyListener {

	private LD38 ld38;
	
	public boolean up;
	public boolean down;
	public boolean right;
	public boolean left;
	
	public KeyboardHandler(LD38 ld38) {
		up = false;
		down = false;
		right = false;
		left = false;
		
		this.ld38 = ld38;
	}
	
	@Override
	public void keyTyped(KeyEvent e) {
		
	}

	@Override
	public void keyPressed(KeyEvent e) {
		switch(e.getKeyCode()) {
//		case 87: // W
//			up = true;
//			break;
//		case 83: // S
//			down = true;
//			break;
//		case 65: // A
//			left = true;
//			break;
//		case 68: // D
//			right = true;
//			break;
		case 38: // UP
			up = true;
			break;
		case 40: // DOWN
			down = true;
			break;
		case 37: // LEFT
			left = true;
			break;
		case 39: // RIGHT
			right = true;
			break;
		case 32: // SPACE
//			System.out.println("space");
			break;
		case 116: // F5
			ld38.start();
			break;
		default:
			break;
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		switch(e.getKeyCode()) {
//		case 87: // W
//			up = false;
//			break;
//		case 83: // S
//			down = false;
//			break;
//		case 65: // A
//			left = false;
//			break;
//		case 68: // D
//			right = false;
//			break;
		case 38: // UP
			up = false;
			break;
		case 40: // DOWN
			down = false;
			break;
		case 37: // LEFT
			left = false;
			break;
		case 39: // RIGHT
			right = false;
			break;
		case 32: // SPACE
			ld38.interact();
			break;
		case 49: // 1
			ld38.menuInteract(1);
			break;
		case 50: // 2
			ld38.menuInteract(2);
			break;
		case 51: // 3
			ld38.menuInteract(3);
			break;
		case 27: // ESC
			ld38.menuInteract(-1);
			break;
		case 70: // F
			ld38.placeFlag();
			break;
		default:
			break;
		}
	}
}
