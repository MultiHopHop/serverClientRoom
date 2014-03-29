package com.example.game;

/**
 * A square stores: visibleState, the most recent player that passes the square
 * and a lock that indicate if the square is occupied
 * 
 * @author zianli
 * 
 */
public class Square {
	private VisibleState visibleState;
	private Player player;
	private boolean lock = false;

	public Square(VisibleState visibleState) {
		this.visibleState = visibleState;
		this.player = null;
	}

	public Square(VisibleState visibleState, Player player) {
		this.visibleState = visibleState;
		this.player = player;
	}

	public synchronized boolean setVisibleState(Player player) {
		if (!this.lock) {
			this.visibleState = VisibleState.TOUCHED;
			this.player = player;
			this.lock = true;
			return true;
		}
		return false;
	}

	public synchronized VisibleState getVisibleState() {
		return this.visibleState;
	}

	public synchronized Player getPlayer() {
		return this.player;
	}

	public synchronized void resetLock() {
		this.lock = false;
	}

	public synchronized boolean getLock() {
		return this.lock;
	}

	public synchronized void spawnPowerup() {
		this.visibleState = VisibleState.POWERUP;
	}
}
