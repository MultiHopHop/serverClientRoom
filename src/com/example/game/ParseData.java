package com.example.game;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ParseData {
	// Example String input: "Player1 move up 0",
	// "Server spawnpowerup 23 4", "Player move down deny -1"

	private final int REQUEST = 0;
	private final int DENY = -1;
	private final int APPROVE = 1;

	private String agent, command, argument;
	private int flag = 10;

	private String input;
	private String output = null;
	private int playerIndex;
	private ArrayList<Player> players;
	private Board board;
	private boolean checkMove;

	public ParseData(String input, ArrayList<Player> players, Board board) {
		this.input = input;
		this.players = players;
		this.board = board;
	}

	public void Parser() {
			Lexer(input);
			if (flag == -1) {
				return;
			}
			if (agent.equals("Player[0-3]")) {
				playerIndex = agent.charAt(agent.length()-1);
			}
			if (command.equals("move")) {
				if (argument.equals("up")) {
					board.update(players.get(playerIndex), Move.UP);
				}
				else if (argument.equals("down")) {
					board.update(players.get(playerIndex), Move.DOWN);
				}
				else if (argument.equals("right")) {
					board.update(players.get(playerIndex), Move.RIGHT);
				}
				else if (argument.equals("left")) {
					board.update(players.get(playerIndex), Move.LEFT);
				}
				checkMove = board.checkMove(players.get(playerIndex));
				
				if (checkMove == true) {
					output = agent + " " + command + " " + argument + " " + 1;
				}
			}
			if (command.equals("spawnpowerup") && agent.equals("Server")) {
				String[] xy = argument.split(" ");
				int x = Integer.parseInt(xy[0]);
				int y = Integer.parseInt(xy[1]);
				board.spawnPowerup(new Coord(x, y));
			}
		}

	public boolean isRequest() {
		return (flag == 0);
	}
	
	public String getOutput() {
		return this.output;
	}
	
	private void Lexer(String input) {
		Pattern patterns = Pattern.compile("((Player[0-3])|Server)|"
				+ "(move|spawnpowerup)|"
				+ "(up|down|right|left|([0-9]+ [0-9]+))|" + "(-?[01])");
		Matcher matcher = patterns.matcher(input);
		while (matcher.find()) {
			if (matcher.group().matches("(Player[0-3])|Server")) {
				agent = matcher.group();
			} else if (matcher.group().matches("(move|spawnpowerup)")) {
				command = matcher.group();
			} else if (matcher.group().matches(
					"(up|down|right|left|([0-9]+ [0-9]+))")) {
				argument = matcher.group();
			} else if (matcher.group().matches("-?[01]")) {
				System.out.println("Flag: " + matcher.group());
				flag = Integer.parseInt(matcher.group());
			}
		}
	}
}
