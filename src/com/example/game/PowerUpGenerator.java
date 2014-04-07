package com.example.game;

public class PowerUpGenerator extends Thread // run this as a separate thread
												// from the main server
{
	private int time;
	private int board_size;

	public PowerUpGenerator()
	{
		this.time = 10; // default 10 seconds
		this.board_size = 36; // default 6 by 6
	}

	public void setTime(int time)
	{
		this.time = time; // user / powerup defined time
	}

	public void setBoardSize(int board_size)
	{
		this.board_size = board_size; // user / powerup defined time
	}

	public void run()
	{
		while (!isInterrupted()) // interrupt generation when game is over /
									// powerups are disabled
		{
			try
			{
				sleep(time * 1000); // 1000 milliseconds = 1 second
			}
			catch (InterruptedException e)
			{}
			while (true)
			{
				PowerUps powerup = PowerUps.randomPowerup();
				int location = (int) (Math.random() * board_size);
				// TODO check whether location is used
				// TODO send powerup data to users if valid
			}
		}
	}
}