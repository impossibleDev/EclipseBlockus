// Description: Displays a player card and includes relevant information about the player
// Aiden

package view.gameUi;

import model.Player;
import shared.CardTypes;
import shared.GameConstants;

import javax.swing.*;

import static view.ViewConstants.cardImages;

public class PlayerCard extends JLabel {

	// Constants
	private static final int iconSize = 40;
	private static final int iconPadding = 5;

	// UI elements
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
	
	//updates ui to given player info
	public void update(Player player) {
		// Update team background/frame icon if needed
		
		CardTypes[] playerDeck = player.getDeck();
		for (int i = 0; i < deckLabels.length; i++) {
			// Safely check if the index exists in the player's deck and is not null
			if (playerDeck != null && i < playerDeck.length && playerDeck[i] != null) {
				deckLabels[i].setIcon(cardImages.get(playerDeck[i]));
				deckLabels[i].setVisible(true);
			} else {
				// Hide empty/cooldown slots
				deckLabels[i].setIcon(null);
				deckLabels[i].setVisible(false);
			}
		}
	}
}