/*
 * File: TeamPanel.java
 * Project: SDP #1 - BLOKUS
 * Course: [Course code, section, and instructor]
 * Group: 5 (Aiden, Yanjun, Melinda)
 * Contributors: Aiden
 * Date: November 27, 2026
 * Description: Displays the team player cards and their status.
 */

package view.gameUi;

import javax.swing.*;
import model.Player;

//Aiden
public class TeamPanel extends JLabel {

	// IMAGE RESOURCES
	private static final ImageIcon img = new ImageIcon("resources/ui/TeamPanelFrame.png");

	// CONSTANTS
	private static final int playerCardDistance = 110;

	// UI ELEMENTS
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