// Description: Displays the all player cards and current player's deck
// Contributors: Aiden

package view.gameUi;

import javax.swing.*;
import model.Player;

//Aiden
public class TeamPanel extends JLabel {

	// Constants
	private static final int playerCardDistance = 110;

	// ImageIcon resources
	private static final ImageIcon img = new ImageIcon("resources/ui/TeamPanelFrame.png");

	// UI elements
	private PlayerCard[] playerCards = new PlayerCard[4];

	public TeamPanel() {
		super(img);
		setLayout(null);

		// Init PlayerCards
		for (int i = 0; i < 4; i++) {
			playerCards[i] = new PlayerCard();
			playerCards[i].setLocation(0, i * playerCardDistance);
			add(playerCards[i]);
		}
	}

	public void update(Player[] players) {
		for (int i = 0; i < players.length; i++) {
			// Pass the player object down so PlayerCard can check their active card &
			// cooldown status
			playerCards[i].update(players[i]);
		}
	}
}