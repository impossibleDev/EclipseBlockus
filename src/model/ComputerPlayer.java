// Description: Determines and performs automated player moves.
// Contributors: Yanjun

package model;

import java.util.Random;
import shared.PieceData;
import shared.PiecePlacer;
import shared.Team;

// yanjun
public class ComputerPlayer {

	// Constants
	// random to make sure the computer players don't place in a similar way every
	private static final Random rand = new Random();

	// one possible move
	// We keep the best one found so far and replace it
	// whenever we find a move with a higher score
	public static class Move {
		// Fields
		// x, y (board position of the piece)
		// pieceNum (which of the 21 pieces this is)
		// size (how many squares in that piece)
		// score (how good the move is), data (the piece's shape, including orientation)
		public int x, y, pieceNum, size, score;
		public boolean[][] data;
	}


	// returns the best move found, or null if the player has no legal move
	public static Move chooseMove(Team[][] board, Player player) {
		// start from piece 0 with no best move yet (null)
		return searchPieces(board, player, 0, null);
	}

	// RECURSION : check pieces one by one
	// Each call looks at one piece, then calls itself for the next piece number
	private static Move searchPieces(Team[][] board, Player player, int pieceNum, Move best) {
		// base case: every piece has been checked
		// so return the best move we found
		if (pieceNum >= PieceData.pieces.length) {
			return best;
		}
		// skip pieces this player already placed (disappeared)
		if (!player.isUsed(pieceNum)) {
			// same orientation the controller uses (transposed once)
			boolean[][] base = PiecePlacer.transpose(PieceData.pieces[pieceNum]);
			// try all 8 orientations of this piece and keep the best move so far
			best = searchOrientations(board, player.getTeam(), base, pieceNum, 0, best);
		}
		// recursive case: move on to the next piece
		return searchPieces(board, player, pieceNum + 1, best);
	}

	// RECURSION: try the 8 orientations of one piece
	private static Move searchOrientations(Team[][] board, Team team, boolean[][] data, int pieceNum, int step,
			Move best) {
		// base case: all 8 orientations have been tried
		// return the best move
		if (step >= 8) {
			return best;
		}
		// try this orientation on every position of the board
		best = searchPositions(board, team, data, pieceNum, best);
		// get the next orientation
		boolean[][] next = (step == 3) ? PiecePlacer.flip(data) : PiecePlacer.rotate(data);
		// recursive case: do the same for the next orientation
		return searchOrientations(board, team, next, pieceNum, step + 1, best);
	}

	// tries every board position for the current orientation and keeps the highest
	// scoring move
	private static Move searchPositions(Team[][] board, Team team, boolean[][] data, int pieceNum, Move best) {
		int size = countSquares(data);

		for (int x = 0; x < board.length; x++) {
			for (int y = 0; y < board.length; y++) {
				// ask the rules if the piece can go here, if not try the next position
				if (!PiecePlacer.validMove(board, x, y, data, team))
					continue;

				// legal so work out how good it is
				int s = score(board, x, y, size, team);
				// keep it only if it is the first legal move or beats the best
				if (best == null || s > best.score) {
					best = new Move(); // new object to store
					best.x = x;
					best.y = y;
					best.pieceNum = pieceNum;
					best.data = data;
					best.size = size;
					best.score = s;
				}
			}
		}
		return best;
	}

	// Bigger pieces score higher and each color has a different position preference
	private static int score(Team[][] board, int x, int y, int size, Team team) {
		// base score: bigger -> higher
		int score = size * 10;
		// distance from the middle of the board
		int centre = board.length / 2;
		int dist = Math.abs(x - centre) + Math.abs(y - centre);
		// color preference
		switch (team) {
		case GREEN:
			score -= dist;
			break; // center (higher score when close to center)
		case YELLOW:
			score += dist;
			break; // edges (higher score when away from center)
		default:
			break; // only piece size ("no preference")
		}
		return score + rand.nextInt(15); // random noise, bigger = more random
	}

	// counts how many squares a piece has
	private static int countSquares(boolean[][] data) {
		int count = 0;
		for (boolean[] row : data)
			for (boolean b : row)
				if (b)
					count++;
		return count;
	}
}