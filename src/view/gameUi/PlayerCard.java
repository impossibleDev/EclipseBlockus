/*
 * File: PlayerCard.java
 * Project: SDP #1 - BLOKUS
 * Course: [Course code, section, and instructor]
 * Group: 5 (Aiden, Yanjun, Melinda)
 * Contributors: Not explicitly documented in source
 * Date: November 27, 2026
 * Description: Displays a player card and that player's remaining card icons.
 */

package view.gameUi;

import model.Player;
import shared.CardTypes;
import shared.GameConstants;
import shared.Team;

import javax.swing.*;
import java.awt.*;

import static view.ViewConstants.cardImages;

public class PlayerCard extends JLabel {

	// CONSTANTS
	private static final int iconSize = 40;
	private static final int iconPadding = 5;

	// UI ELEMENTS
	private JLabel[] deckLabels = new JLabel[GameConstants.deckSize];

	public PlayerCard() {
		setSize(250, 110);
		setLayout(null);

		// init deck icons
		for (int i = 0; i < deckLabels.length; i++) {
			deckLabels[i] = new JLabel();
			deckLabels[i].setBounds(30 + (int) (iconSize + iconPadding) * i, 40, iconSize, iconSize);
			add(deckLabels[i]);
		}
	}

	public void update(Player currentPlayer) {
		// Update team background/frame icon if needed
		// setIcon(deckImages.get(currentPlayer.getTeam())); // Uncomment if deckImages applies to PlayerCard

		CardTypes[] playerDeck = currentPlayer.getDeck();
		for (int i = 0; i < deckLabels.length; i++) {
			// Safely check if the index exists in the player's deck and is not null
			if (playerDeck != null && i < playerDeck.length && playerDeck[i] != null) {
				deckLabels[i].setIcon(cardImages.get(playerDeck[i]));
				deckLabels[i].setVisible(true);
			} else {
				deckLabels[i].setIcon(null);
				deckLabels[i].setVisible(false); // Hide empty/cooldown slots
			}
		}
	}
}