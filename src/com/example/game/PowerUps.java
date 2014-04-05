package com.example.game;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public enum PowerUps
{
	DOUBLE_SCORE, SPEED_UP, BOMB, STOP_TIME;

	private static final List<PowerUps> VALUES = Collections
			.unmodifiableList(Arrays.asList(values()));
	private static final int SIZE = VALUES.size();
	private static final Random RANDOM = new Random();

	public static PowerUps randomPowerup()
	{
		return VALUES.get(RANDOM.nextInt(SIZE));
	}
}