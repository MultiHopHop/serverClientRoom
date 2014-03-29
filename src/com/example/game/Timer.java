package com.example.game;

public class Timer {
	private long startTime;
	private long time;
	
	public Timer() {
		startTime = System.currentTimeMillis();
	}
	
	public long checkTime() {
		long timeNow = System.currentTimeMillis();
		time = timeNow - startTime;
		return time;
	}
	
	
}
