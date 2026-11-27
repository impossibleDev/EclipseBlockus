package model;

import shared.CardTypes;
import shared.GameConstants;
import shared.PiecePlacer;
import shared.Team;

public class Game {

	private Player currentPlayer;
	private int currentPlayerIndex = 0;
	private int maxFlipsAllowed;
	private int turnTimeLimit;
	private int cpuCount;
	private Player[] players = new Player[GameConstants.playerSize];
	private Team[][] board;
	private static final Team[] order = { Team.BLUE, Team.GREEN, Team.YELLOW, Team.RED };
	private static final boolean[][] bonusOneByOnePiece = { { true } };

	// CONSTRUCTOR
	public Game() {
		// INIT PLAYERS
		for (int i = 0; i < players.length; i++) {
			players[i] = new Player(order[i], false);
		}
		currentPlayer = players[0];

		// INIT BOARD
		board = new Team[GameConstants.boardSize][GameConstants.boardSize];
		resetBoard();
	}

	// Helper method to clear the board
	private void resetBoard() {
		for (int x = 0; x < GameConstants.boardSize; x++) {
			for (int y = 0; y < GameConstants.boardSize; y++) {
				board[x][y] = Team.NULL;
			}
		}
	}

	public void startGame() {

	}

	// Helper method to automatically skip players who have no valid moves left
	private void advanceToNextPlayer() {
		if (isGameOver()) {
			return;
		}

		// Loop up to the number of players to find the next active player with valid moves
		for (int i = 0; i < players.length; i++) {
			if (currentPlayerIndex == order.length - 1) {
				currentPlayerIndex = 0;
			} else {
				currentPlayerIndex++;
			}
			currentPlayer = players[currentPlayerIndex];

			// If this player still has valid moves, select them and stop searching
			if (hasValidMoves(currentPlayer)) {
				return;
			}
		}
	}

	public void playTurn(int posX, int posY, boolean[][] pieceData, int pieceIndex) {
		if (!PiecePlacer.validMove(board, posX, posY, pieceData, currentPlayer.getTeam())) {
			throw new IllegalArgumentException("Invalid Move");
		}

		// Place shape
		for (int x = 0; x < pieceData.length; x++) {
			for (int y = 0; y < pieceData[0].length; y++) {
				if (pieceData[x][y]) {
					board[x + posX][y + posY] = currentPlayer.getTeam();
				}
			}
		}

		// Mark the piece as used
		currentPlayer.usePiece(pieceIndex);

		// Advance to the next player who has valid moves (skipping stuck players)
		advanceToNextPlayer();
	}

	public void playBonusOneByOne(int posX, int posY) {
		if (currentPlayer.getBonusOneByOneCount() <= 0) {
			throw new IllegalStateException("Player has no bonus one-by-one piece");
		}
		if (!PiecePlacer.validMove(board, posX, posY, bonusOneByOnePiece, currentPlayer.getTeam())) {
			throw new IllegalArgumentException("Invalid Move");
		}

		board[posX][posY] = currentPlayer.getTeam();
		currentPlayer.useBonusOneByOne();
		advanceToNextPlayer();
	}

	// Card ability support method
	public void playCard(CardTypes cardType, int x, int y) {
		CardTypes.useCard(cardType, board, x, y, currentPlayer);
	}

	// melinda -- advances to next player when the time runs out or turn is passed
	public void passTurn() {
		advanceToNextPlayer();
	}

	// melinda
	public boolean isGameOver() {
		// recursively check if every player has no more valid moves
		return checkPlayerStuckRecursive(0);
	}

	// melinda -- recursive method to check each player
	private boolean checkPlayerStuckRecursive(int playerIndex) {
		// base case: if no player has a valid move left, end game
		if (playerIndex >= players.length) {
			return true;
		}

		// if a player has a valid move left, the game continues
		if (hasValidMoves(players[playerIndex])) {
			return false;
		}

		// recursive step, check the next player
		return checkPlayerStuckRecursive(playerIndex + 1);
	}

	// Method to check if an individual player has any valid moves left
	public boolean hasValidMoves(Player player) {
		boolean[] available = player.getAvailablePieces();
		if (available != null && shared.PieceData.pieces != null) {
			for (int i = 0; i < available.length; i++) {
				if (available[i]) {
					boolean[][] piece = shared.PieceData.pieces[i];
					boolean[][] currentPiece = piece;

					for (int rot = 0; rot < 4; rot++) {
						for (int x = 0; x < board.length; x++) {
							for (int y = 0; y < board[0].length; y++) {
								if (PiecePlacer.validMove(board, x, y, currentPiece, player.getTeam())) {
									return true;
								}
							}
						}
						currentPiece = PiecePlacer.rotate(currentPiece);
					}
				}
			}
		}

		if (player.getBonusOneByOneCount() > 0) {
			for (int x = 0; x < board.length; x++) {
				for (int y = 0; y < board[0].length; y++) {
					if (PiecePlacer.validMove(board, x, y, bonusOneByOnePiece, player.getTeam())) {
						return true;
					}
				}
			}
		}
		return false; // No moves left for this player
	}

	public Team[][] getBoard() {
		return board;
	}

	public Player getCurrentPlayer() {
		return currentPlayer;
	}

	public Player[] getPlayers() {
		return players;
	}

	// melinda
	public void initializeNewGame(int cpuCount, int maxFlips, int timeLimit) {
		this.maxFlipsAllowed = maxFlips;
		this.turnTimeLimit = timeLimit;
		this.cpuCount = cpuCount;

		int validCpuCount = Math.max(0, Math.min(order.length, cpuCount));
		int humanCount = order.length - validCpuCount;

		players = new Player[order.length];

		for (int i = 0; i < order.length; i++) {
			boolean isCpu = i >= humanCount;
			players[i] = new Player(order[i], isCpu);
			players[i].setRemainingFlips(maxFlips);
		}

		resetBoard();
		currentPlayerIndex = 0;
		currentPlayer = players[currentPlayerIndex];
	}

	public boolean attemptFlipOrRotate() {
		return currentPlayer.useFlip();
	}

	public int getMaxFlipsAllowed() {
		return maxFlipsAllowed;
	}

	public int getCpuCount() {
		return cpuCount;
	}

	public int getCurrentPlayerIndex() {
		return currentPlayerIndex;
	}

	// melinda -- restore game state from previous save
	public void restoreGame(Team[][] savedBoard, Player[] savedPlayers, int cpuCount, int maxFlips, int timeLimit,
			int savedPlayerIndex) {
		// copy board
		this.board = new Team[savedBoard.length][savedBoard[0].length];
		for (int x = 0; x < savedBoard.length; x++) {
			for (int y = 0; y < savedBoard[0].length; y++) {
				this.board[x][y] = savedBoard[x][y];
			}
		}

		// copy player information
		this.players = new Player[savedPlayers.length];
		for (int i = 0; i < savedPlayers.length; i++) {
			this.players[i] = savedPlayers[i].copy();
		}

		this.cpuCount = cpuCount;
		this.maxFlipsAllowed = maxFlips;
		this.turnTimeLimit = timeLimit;
		this.currentPlayerIndex = savedPlayerIndex;
		this.currentPlayer = this.players[currentPlayerIndex];
	}

	// melinda
	public Score[] getSortedLeaderboard() {
		Score[] records = new Score[players.length];

		// count squares for each player/team
		for (int i = 0; i < players.length; i++) {
			Team t = players[i].getTeam();
			int count = 0;
			for (int x = 0; x < board.length; x++) {
				for (int y = 0; y < board[0].length; y++) {
					if (board[x][y] == t) {
						count++;
					}
				}
			}
			records[i] = new Score(t, count);
		}

		// BUBBLE SORT
		for (int i = 0; i < records.length - 1; i++) {
			for (int j = 0; j < records.length - i - 1; j++) {
				if (records[j].getScore() < records[j + 1].getScore()) {
					Score temp = records[j];
					records[j] = records[j + 1];
					records[j + 1] = temp;
				}
			}
		}
		return records;
	}
}