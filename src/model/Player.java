package model;

import shared.CardTypes;
import shared.GameConstants;
import shared.PieceData;
import shared.Team;

public class Player {
	Team team;
	CardTypes[] deck = new CardTypes[GameConstants.deckSize];
	private boolean isCpu;
	private int remainingFlips;

	// true = piece is still available, false = already placed
	private boolean[] availablePieces;

	private int bonusOneByOneCount = 0;
	private boolean usedCardThisTurn = false;

	private int cardCooldownTurns = 0;

	public Player(Team team) {
		this(team, false);
	}

	public Player(Team team, boolean isCpu) {
		this.team = team;
		this.isCpu = isCpu;
		this.remainingFlips = -1; // default to unlimited

		deck[0] = CardTypes.SKIP;
		deck[1] = CardTypes.EXPLODE;
		deck[2] = CardTypes.REGENERATE;
		deck[3] = CardTypes.DETACH;

		initializeStartingPieces();
	}

	private void initializeStartingPieces() {
		if (PieceData.pieces != null) {
			availablePieces = new boolean[PieceData.pieces.length];
			for (int i = 0; i < availablePieces.length; i++) {
				availablePieces[i] = true;
			}
		} else {
			availablePieces = new boolean[0];
		}
	}

	public Team getTeam() {
		return team;
	}

	public CardTypes[] getDeck() {
		return deck;
	}

	public boolean isCpu() {
		return isCpu;
	}

	public void setCpu(boolean isCpu) {
		this.isCpu = isCpu;
	}

	public int getRemainingFlips() {
		return remainingFlips;
	}

	public void setRemainingFlips(int remainingFlips) {
		this.remainingFlips = remainingFlips;
	}

	public boolean useFlip() {
		if (remainingFlips == -1) {
			return true;
		}
		if (remainingFlips > 0) {
			remainingFlips--;
			return true;
		}
		return false;
	}

	public boolean[] getAvailablePieces() {
		return availablePieces;
	}

	// used when a game is restored from a save
	public void setAvailablePieces(boolean[] pieces) {
		this.availablePieces = pieces.clone();
	}

	// marks a piece as used
	public void usePiece(int pieceIndex) {
		if (pieceIndex >= 0 && pieceIndex < availablePieces.length) {
			availablePieces[pieceIndex] = false;
		}
	}

	// alias for ComputerPlayer / Game
	public void setUsed(int pieceNum) {
		usePiece(pieceNum);
	}

	public boolean hasPiece(int pieceIndex) {
		if (pieceIndex >= 0 && pieceIndex < availablePieces.length) {
			return availablePieces[pieceIndex];
		}
		return false;
	}

	public boolean isUsed(int pieceNum) {
		return !hasPiece(pieceNum);
	}

	// yanjun
	// number of squares left in the pieces that were not placed (lower is better)
	public int getRemainingSquares() {
		int total = 0;
		for (int i = 0; i < availablePieces.length; i++) {
			if (availablePieces[i]) {
				for (boolean[] row : PieceData.pieces[i]) {
					for (boolean b : row) {
						if (b)
							total++;
					}
				}
			}
		}
		return total;
	}

	// yanjun
	public int getBonusOneByOneCount() {
		return bonusOneByOneCount;
	}

	public void addBonusOneByOne() {
		bonusOneByOneCount++;
	}

	public void useBonusOneByOne() {
		if (bonusOneByOneCount > 0) {
			bonusOneByOneCount--;
		}
	}

	public void setBonusOneByOneCount(int count) {
		this.bonusOneByOneCount = Math.max(0, count);
	}

	public boolean hasUsedCardThisTurn() {
		return usedCardThisTurn;
	}

	public void setUsedCardThisTurn(boolean usedCardThisTurn) {
		this.usedCardThisTurn = usedCardThisTurn;
	}

	public int getCardCooldownTurns() {
		return cardCooldownTurns;
	}

	public boolean canUseCard() {
		return cardCooldownTurns <= 0;
	}

	// every 4 rounds
	public void triggerCardCooldown() {
		this.cardCooldownTurns = 4;
	}

	public void tickCardCooldown() {
		if (cardCooldownTurns > 0) {
			cardCooldownTurns--;
		}
	}

	public void beginTurn() {
		tickCardCooldown(); // Decrements cardCooldownTurns each time their turn starts
	}

	public Player copy() {
		Player cloned = new Player(this.team, this.isCpu);
		cloned.setRemainingFlips(this.remainingFlips);

		if (this.availablePieces != null) {
			cloned.setAvailablePieces(this.availablePieces);
		}

		cloned.setBonusOneByOneCount(this.bonusOneByOneCount);
		cloned.setUsedCardThisTurn(this.usedCardThisTurn);
		cloned.cardCooldownTurns = this.cardCooldownTurns;

		return cloned;
	}
	
	public void setCardCooldownTurns(int cardCooldownTurns) {
		this.cardCooldownTurns = Math.max(0, cardCooldownTurns);
	}

}