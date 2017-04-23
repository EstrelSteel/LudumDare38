package com.estrelsteel.ld38;

public enum GameState {
	PLAYING, MENU;
	
	public enum SubState {
		PLAYING, MAIN, TUTORIAL_1, ESCAPE, FAIL_NO_WATER, FAIL_SCORE, FAIL_DIRECTIONS, CREDITS, ENDLESS_START;
	}
}

