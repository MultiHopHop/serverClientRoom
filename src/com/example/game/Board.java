package com.example.game;

import java.util.ArrayList;


public class Board {
	private final int SIZE = 8;
	private int numPlayer;
	private Square[][] squares;
	private ArrayList<Player> players;
	private Timer timer;
	private boolean start;
	private Coord powerUpCoord;
	private Coord[] startingPositions = { new Coord(0, 0),
			new Coord(SIZE - 1, SIZE - 1), new Coord(0, SIZE - 1),
			new Coord(SIZE - 1, 0) };

	/**
	 * General Work Flow
	 * 
	 * After players threads connected to the server, the server records the
	 * number of clients and initialize a board. The server also sends numPlayer
	 * to each client to initialize their own board.
	 * 
	 * The server should send the player index to each client. A player index
	 * indicates the player's index in the players array. When a client wants to
	 * make a move, it should send a string to server indicating its index and
	 * the move.
	 * 
	 */
	public Board(int numPlayer) {
		this.numPlayer = numPlayer;
		start = false;

		// initialize squares
		squares = new Square[SIZE][SIZE];
		for (int i = 0; i < SIZE; i++) {
			for (int j = 0; j < SIZE; j++) {
				squares[i][j] = new Square(VisibleState.UNTOUCHED);
			}
		}

		// initialize players
		this.players = new ArrayList<Player>();
		for (int i = 0; i < numPlayer; i++) {
			Player player = new Player(startingPositions[i]);
			int x = startingPositions[i].getX();
			int y = startingPositions[i].getY();
			squares[x][y].setVisibleState(player);
			this.players.add(player);
		}
	}

	/**
	 * Call this method when the game is ready to start
	 */
	public void start() {
		// initialize timer
		timer = new Timer();
		start = true;
	}

	public boolean checkStart() {
		return (start == true);
	}

	/**
	 * The server will attempt requested moves from clients before checking
	 * validity of the move. Then the server will send validity of the move to
	 * the requesting client.
	 * 
	 * @param player
	 * @param move
	 */
	public void update(Player player, Move move) {
		int x = player.getCoord().getX();
		int y = player.getCoord().getY();

		switch (move) {
		case UP:
			if (y > 0)
				update(player, new Coord(x, y - 1));
			break;
		case DOWN:
			if (y < SIZE - 1)
				update(player, new Coord(x, y + 1));
			break;
		case RIGHT:
			if (x < SIZE - 1)
				update(player, new Coord(x + 1, y));
			break;
		case LEFT:
			if (x > 0)
				update(player, new Coord(x - 1, y));
			break;
		}
	}

	/**
	 * On the client side, interval of update = default interval / player.speed
	 * 
	 * @param player
	 * @param newCoord
	 */
	public void update(Player player, Coord newCoord) {
		int newX = newCoord.getX();
		int newY = newCoord.getY();

		int lastX = player.getCoord().getX();
		int lastY = player.getCoord().getY();

		if (player.getSpeed() != 1 && player.getTime() > 5000)
			player.resetSpeed();
		synchronized (squares[newX][newY]) {
			if (squares[newX][newY].getVisibleState() == VisibleState.POWERUP
					&& !squares[newX][newY].getLock()) {
				player.setSpeed(2);
			}

			if (squares[newX][newY].setVisibleState(player)) {
				squares[lastX][lastY].resetLock();
				player.setMoveStatus(true);
				player.setCoord(newCoord);

				System.out.println("update player coord");
			}

		}

	}

	// check validity of previous move
	public boolean checkMove(Player player) {
		return player.getMoveStatus();
	}

	// immutable method
	public Square[][] getSquares() {
		Square[][] clone = new Square[SIZE][SIZE];
		for (int i = 0; i < SIZE; i++) {
			for (int j = 0; j < SIZE; j++) {
				clone[i][j] = new Square(this.squares[i][j].getVisibleState(),
						this.squares[i][j].getPlayer());
			}
		}
		return clone;
	}

	// immutable method
	public ArrayList<Player> getPlayers() {
		ArrayList<Player> clone = new ArrayList<Player>(players.size());
		for (Player player : players)
			clone.add(new Player(player.getCoord()));
		return clone;
	}

	// in milliseconds
	public long getTime() {
		return timer.checkTime();
	}

	/**
	 * After generating power up, server sends the coordinate of power up to its
	 * clients.
	 */
	public void spawnPowerup() {
		while (true) {
			int x = 0 + (int) (Math.random() * (SIZE));
			int y = 0 + (int) (Math.random() * (SIZE));
			System.out.println("X, Y " + x + " " + y);
			if (!squares[x][y].getLock()) {
				squares[x][y].spawnPowerup();
				powerUpCoord = new Coord(x, y);
				break;
			}
		}
	}

	public void spawnPowerup(Coord coord) {
		int x = coord.getX();
		int y = coord.getY();
		squares[x][y].spawnPowerup();
	}
	
	public Coord getPowerUpCoord() {
		return new Coord(powerUpCoord.getX(), powerUpCoord.getY());
	}
}
