/*
 * File: PiecePlacer.java
 * Project: SDP #1 - BLOKUS
 * Course: [Course code, section, and instructor]
 * Group: 5 (Aiden, Yanjun, Melinda)
 * Contributors: Yanjun, Melinda
 * Date: November 27, 2026
 * Description: Provides piece placement and shape transformation utilities.
 */

package shared;

import model.Player;

// Yanjun & Melinda
public class PiecePlacer {

	// Rotates the piece 90 degrees clockwise
	// Returns a new array so the original is not changed
	// -> a piece with R rows and C columns becomes a piece with C rows and R
	// columns
	public static boolean[][] rotate(boolean[][] pieceData) {
		int rows = pieceData.length;
		int cols = pieceData[0].length;
		// rows and columns swap sizes after a 90 degree turn
		boolean[][] result = new boolean[cols][rows];
		for (int r = 0; r < rows; r++) {
			for (int c = 0; c < cols; c++) {
				// the old top row becomes the new right column
				result[c][rows - 1 - r] = pieceData[r][c];
			}
		}
		return result;
	}

	// Flips the piece
	// Returns a new array so the original is not changed
	// The size stays the same
	public static boolean[][] flip(boolean[][] pieceData) {
		int rows = pieceData.length;
		int cols = pieceData[0].length;

		boolean[][] result = new boolean[rows][cols];
		for (int r = 0; r < rows; r++) {
			for (int c = 0; c < cols; c++) {
				// each row is reversed
				// the first column goes to the last column
				result[r][cols - 1 - c] = pieceData[r][c];
			}
		}
		return result;
	}

	// Backward compatibility overload for standard calls without player context
	public static boolean validMove(Team[][] board, int x, int y, boolean[][] pieceData, Team team) {
		return validMove(board, x, y, pieceData, team, null);
	}

	// Checks the rules for placing a piece with its top left corner at (x, y)
	// Returns true only if the move is legal (Supports DETACH card bypass)
	public static boolean validMove(Team[][] board, int x, int y, boolean[][] pieceData, Team team, Player player) {
		int size = board.length;
		boolean touchesCorner = false; // diagonal touch with own color
		boolean coversStart = false; // covers this team's board corner
		boolean hasOwnBlock = false; // team already has a block on the board

		boolean isDetachActive = (player != null && player.isDetachActive());

		// Look through the whole board to see if this team has already placed something
		// Decides whether this is the team's first move
		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {
				if (board[i][j] == team) {
					hasOwnBlock = true;
				}
			}
		}

		// Check every filled square of the piece one by one
		for (int r = 0; r < pieceData.length; r++) {
			for (int c = 0; c < pieceData[0].length; c++) {
				if (!pieceData[r][c])
					continue; // empty part of the piece, skip

				// position of this square on the board
				int bx = x + r;
				int by = y + c;

				// must be inside the board and the cell must be empty
				if (bx < 0 || by < 0 || bx >= size || by >= size)
					return false;
				if (board[bx][by] != Team.NULL)
					return false;

				// If DETACH card is active, skip edge and corner adjacency checks!
				if (!isDetachActive) {
					// no edge contact with own color (the four neighbours)
					if (isTeam(board, bx - 1, by, team) || isTeam(board, bx + 1, by, team)
							|| isTeam(board, bx, by - 1, team) || isTeam(board, bx, by + 1, team)) {
						return false;
					}

					// corner contact with own color
					if (isTeam(board, bx - 1, by - 1, team) || isTeam(board, bx - 1, by + 1, team)
							|| isTeam(board, bx + 1, by - 1, team) || isTeam(board, bx + 1, by + 1, team)) {
						touchesCorner = true;
					}
				}

				// first move: must cover this team's starting corner
				if (bx == startX(team, size) && by == startY(team, size)) {
					coversStart = true;
				}
			}
		}

		// If DETACH card is active, any in-bounds, non-overlapping placement is valid!
		if (isDetachActive) {
			return true;
		}

		// First move: only the starting corner matters
		// Later move: the piece must touch an own block at a corner
		if (!hasOwnBlock) {
			return coversStart;
		}
		return touchesCorner;
	}

	// Swaps rows and columns so the piece looks the same on the board as in the
	// panel
	public static boolean[][] transpose(boolean[][] pieceData) {
		int rows = pieceData.length;
		int cols = pieceData[0].length;
		boolean[][] result = new boolean[cols][rows];
		for (int r = 0; r < rows; r++) {
			for (int c = 0; c < cols; c++) {
				result[c][r] = pieceData[r][c];
			}
		}
		return result;
	}

	// true if the cell (x, y) is on the board and belongs to the given team
	private static boolean isTeam(Team[][] board, int x, int y, Team team) {
		if (x < 0 || y < 0 || x >= board.length || y >= board.length)
			return false;
		return board[x][y] == team;
	}

	// starting corner of each team

	// startX gives the x of the corner
	// 0 (left) for blue and red, last column for green and yellow
	private static int startX(Team team, int size) {
		return (team == Team.BLUE || team == Team.RED) ? 0 : size - 1;
	}

	// startY gives the y of the corner
	// 0 (top) for blue and green, last row for red and yellow
	private static int startY(Team team, int size) {
		return (team == Team.BLUE || team == Team.GREEN) ? 0 : size - 1;
	}
}