package com.example.game;

import android.graphics.Color;

/**
 * A player objects stores: current coordinate, previous coordinate, validity of
 * a move, speed and a timer to check duration of a power up
 * 
 * @author zianli
 * 
 */
public class Player {
	private Coord coord, lastCoord;
	private boolean moveStatus;
	private int speed;
	private Timer timer;

	public Player(Coord coord) {
		this.coord = coord;
		this.speed = 1;
	}

	public Coord getCoord() {
		return coord;
	}

	public Coord getLastCoord() {
		return lastCoord;
	}

	public void setCoord(Coord coord) {
		this.coord = coord;
	}

	public void setLastCoord(Coord lastCoord) {
		this.lastCoord = lastCoord;
	}

	public boolean getMoveStatus() {
		return moveStatus;
	}

	public void setMoveStatus(boolean moveStatus) {
		this.moveStatus = moveStatus;
	}

	public void setSpeed(int speed) {
		this.speed = speed;
		timer = new Timer();
	}

	public int getSpeed() {
		return speed;
	}

	public void resetSpeed() {
		this.speed = 1;
	}

	public long getTime() {
		if (timer.equals(null))
			return 0;
		return timer.checkTime();
	}
}
