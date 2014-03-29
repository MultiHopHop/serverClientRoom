package com.example.game;

public enum Move {
	UP ("UP"),
	DOWN ("DOWN"),
	LEFT("LEFT"),
	RIGHT("RIGHT");
	private String move;
	public String getMove() {
		return move;
	}
	private Move(String move) {
		this.move = move;
	}
}
