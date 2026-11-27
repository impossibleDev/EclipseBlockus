/*
 * File: GameState.java
 * Project: SDP #1 - BLOKUS
 * Course: [Course code, section, and instructor]
 * Group: 5 (Aiden, Yanjun, Melinda)
 * Contributors: Melinda
 * Date: November 27, 2026
 * Description: Stores serializable state for saving and restoring a game.
 */

package model;

import shared.Team;

// melinda
public class GameState {
	private Team[][] boardState;
	private Player[] playersState;
	private int cpuCount;
	private int maxFlips;
	private int timeLimit;
	private int currentPlayerIndex;
	private boolean hasSave = false;

	public void saveGame(Team[][] board, Player[] players, int cpuCount, int maxFlips, int timeLimit,
			int currentPlayerIndex) {
		// Create an independent copy of the board so future moves don't affect the
		// save
		this.boardState = new Team[board.length][board[0].length];
		for (int x = 0; x < board.length; x++) {
			for (int y = 0; y < board[0].length; y++) {
				this.boardState[x][y] = board[x][y];
			}
		}

		this.playersState = new Player[players.length];
		for (int i = 0; i < players.length; i++) {
			Player original = players[i];
			Player copy = new Player(original.getTeam(), original.isCpu());
			copy.setRemainingFlips(original.getRemainingFlips());
			copy.setBonusOneByOneCount(original.getBonusOneByOneCount());
			copy.setUsedCardThisTurn(original.hasUsedCardThisTurn());
			copy.setCardCooldownTurns(original.getCardCooldownTurns());
			this.playersState[i] = copy;
		}

		this.cpuCount = cpuCount;
		this.maxFlips = maxFlips;
		this.timeLimit = timeLimit;
		this.currentPlayerIndex = currentPlayerIndex;
		this.hasSave = true;
	}

	public boolean hasSavedGame() {
		return hasSave;
	}

	// Getters
	public Team[][] getBoardState() {
		return boardState;
	}

	public Player[] getPlayersState() {
		return playersState;
	}

	public int getCpuCount() {
		return cpuCount;
	}

	public int getMaxFlips() {
		return maxFlips;
	}

	public int getTimeLimit() {
		return timeLimit;
	}

	public int getCurrentPlayerIndex() {
		return currentPlayerIndex;
	}

	// clears the games data, used when the game is complete (no more valid moves)
	public void clear() {
		this.boardState = null;
		this.playersState = null;
		this.cpuCount = 0;
		this.maxFlips = 0;
		this.timeLimit = 0;
		this.currentPlayerIndex = 0;
		this.hasSave = false;
	}
}