// Description: Displays the active player deck, flip count, and turn timer.
// Contributors: Melinda

package view.gameUi;

import java.awt.Color;
import java.util.EnumMap;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;

import model.Player;
import shared.CardTypes;
import shared.GameConstants;
import shared.Team;
import view.GuiUtil;
import view.ViewConstants;

public class DeckPanel extends JLabel {

	// Constants
	private static final int deckIconSize = 40;
	private static final int deckIconPadding = 10;

	// ImageIcon resources
	private static final EnumMap<Team, ImageIcon> deckImages = new EnumMap<>(Team.class);
	static {
		deckImages.put(Team.YELLOW, new ImageIcon("resources/ui/DeckYellow.png"));
		deckImages.put(Team.RED, new ImageIcon("resources/ui/DeckRed.png"));
		deckImages.put(Team.GREEN, new ImageIcon("resources/ui/DeckGreen.png"));
		deckImages.put(Team.BLUE, new ImageIcon("resources/ui/DeckBlue.png"));
	}

	// UI elements
	private JButton[] currentDeck = new JButton[GameConstants.deckSize];
	private JButton hoveredCard;
	private JButton selectedCard;
	private JLabel flipCounterLabel = new JLabel("Flips: Unlimited"); // melinda
	private TimerIcon timerIcon;

	public DeckPanel() {
		setLayout(null);

		for (int i = 0; i < currentDeck.length; i++) {
			currentDeck[i] = new JButton();
			currentDeck[i].setBounds(30 + (deckIconSize + deckIconPadding) * i, 70, deckIconSize, deckIconSize);
			add(currentDeck[i]);
		}

		timerIcon = new TimerIcon();
		timerIcon.setLocation(150, 120);
		add(timerIcon);

		flipCounterLabel.setFont(GuiUtil.PIXELFONTSMALL);
		flipCounterLabel.setForeground(Color.BLACK);
		flipCounterLabel.setBounds(40, 40, 180, 30);
		add(flipCounterLabel);
	}

	public void update(Player currentPlayer) {
		setIcon(deckImages.get(currentPlayer.getTeam()));

		CardTypes[] playerDeck = currentPlayer.getDeck();
		int activeDeckSize = (playerDeck != null) ? playerDeck.length : 0;
		boolean canUseCard = currentPlayer.canUseCard() && !currentPlayer.hasUsedCardThisTurn();

		for (int i = 0; i < currentDeck.length; i++) {
			if (i < activeDeckSize && playerDeck[i] != null) {
				currentDeck[i].setIcon(ViewConstants.cardImages.get(playerDeck[i]));
				currentDeck[i].setVisible(true);
				currentDeck[i].setEnabled(canUseCard);
			} else {
				currentDeck[i].setIcon(null);
				currentDeck[i].setVisible(false); // Hide empty/unused slots
				currentDeck[i].setEnabled(false);
			}
		}

		updateFlipCounter(currentPlayer.getRemainingFlips());
	}

	public void setDeckButtonsEnabled(boolean isEnabled) {
		for (JButton cardButton : currentDeck) {
			cardButton.setEnabled(isEnabled);
		}
	}

	public JButton[] getCurrentDeck() {
		return currentDeck;
	}

	public void hoverCard(JButton card) {
		if (hoveredCard == card) {
			dehoverCard();
			return;
		}
		dehoverCard();
		hoveredCard = card;
		hoveredCard.setLocation(hoveredCard.getX(), hoveredCard.getY() - 5);
	}

	public void dehoverCard() {
		if (hoveredCard != null) {
			hoveredCard.setLocation(hoveredCard.getX(), hoveredCard.getY() + 5);
			hoveredCard = null;
		}
	}

	public void selectCard(JButton card) {
		deselectCard();
		selectedCard = card;
		selectedCard.setLocation(selectedCard.getX(), selectedCard.getY() - 10);
	}

	public void deselectCard() {
		if (selectedCard != null) {
			selectedCard.setLocation(selectedCard.getX(), selectedCard.getY() + 10);
			selectedCard = null;
		}
	}

	public JButton getSelectedCard() {
		return selectedCard;
	}

	public void updateFlipCounter(int remainingFlips) {
		
		
		if (remainingFlips < 0) {
			flipCounterLabel.setText("Flips: INF");
		} else {
			flipCounterLabel.setText("Flips: " + remainingFlips);
		}
	}

	public void updateTimer(int secElapsed, int secLimit) {
		if (timerIcon != null) {
			timerIcon.update(secElapsed, secLimit);
		}
	}
	
	public void setTimerIcon(boolean isVisible) {
		timerIcon.setVisible(isVisible);
	}
	
}