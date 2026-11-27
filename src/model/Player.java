/*
 * File: Player.java
 * Project: SDP #1 - BLOKUS
 * Course: [Course code, section, and instructor]
 * Group: 5 (Aiden, Yanjun, Melinda)
 * Contributors: Yanjun, Melinda
 * Date: November 27, 2026
 * Description: Stores player state, pieces, cards, and game options.
 */

package model;

import shared.CardTypes;
import shared.GameConstants;
import shared.PieceData;
import shared.Team;
import java.util.ArrayList;
import java.util.List;

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

	// Turn-based card progression tracking fields
	private int turnsTaken = 0;
	private int turnsSinceFirstCard = 0;
	private boolean hasReceivedFirstCard = false;

	// DETACH ability
	private boolean detachActive = false;

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
		// Can use a card if they have at least one card in their hand
		return deck != null && deck.length > 0 && cardCooldownTurns <= 0;
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

	// Melinda -- detach ability logic
	public boolean isDetachActive() {
		return detachActive;
	}

	public void setDetachActive(boolean detachActive) {
		this.detachActive = detachActive;
	}

	public void beginTurn() {
		tickCardCooldown(); // Decrements cardCooldownTurns each time their turn starts
		turnsTaken++;

		// Reset detach flag and turn card usage flags at the start of a new turn
		detachActive = false;
		this.usedCardThisTurn = false;
		
		// Give the first random card exactly on their 3rd turn (optional progression logic)
		if (!hasReceivedFirstCard && turnsTaken >= 3) {
			hasReceivedFirstCard = true;
			addRandomCard();
		}
		// After the first card, add another random card every 4 turns (capped at 4)
		else if (hasReceivedFirstCard) {
			turnsSinceFirstCard++;
			if (turnsSinceFirstCard >= 4) {
				turnsSinceFirstCard = 0;
				addRandomCard();
			}
		}
	}

	// Adds a new random card to the player's hand without overwriting, capped at 4
	public void addRandomCard() {
		CardTypes[] allCards = { CardTypes.SKIP, CardTypes.EXPLODE, CardTypes.REGENERATE, CardTypes.DETACH };
		CardTypes randomCard = allCards[(int) (Math.random() * allCards.length)];

		List<CardTypes> cardList = new ArrayList<>();
		if (deck != null) {
			for (CardTypes c : deck) {
				if (c != null) {
					cardList.add(c);
				}
			}
		}

		// Only add a new card if under the maximum cap of 4
		if (cardList.size() < 4) {
			cardList.add(randomCard);
			deck = cardList.toArray(new CardTypes[0]);
		}
	}

	// Removes a specific card from the player's hand once used
	public void useCardAtIndex(int index) {
		if (deck == null || index < 0 || index >= deck.length) {
			return;
		}
		List<CardTypes> cardList = new ArrayList<>();
		for (int i = 0; i < deck.length; i++) {
			if (i != index && deck[i] != null) {
				cardList.add(deck[i]);
			}
		}
		deck = cardList.toArray(new CardTypes[0]);
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
		cloned.turnsTaken = this.turnsTaken;
		cloned.turnsSinceFirstCard = this.turnsSinceFirstCard;
		cloned.hasReceivedFirstCard = this.hasReceivedFirstCard;

		if (this.deck != null) {
			cloned.deck = this.deck.clone();
		}

		return cloned;
	}
	
	public void setCardCooldownTurns(int cardCooldownTurns) {
		this.cardCooldownTurns = Math.max(0, cardCooldownTurns);
	}
}