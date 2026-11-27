package view.gameUi;

import java.awt.Color;
import java.util.EnumMap;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;

import model.Player;
import shared.GameConstants;
import shared.Team;
import view.GuiUtil;
import view.ViewConstants;

//Aiden
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
	private JLabel flipCounterLabel = new JLabel("Flips: INF"); // melinda
	private TimerIcon timerIcon = new TimerIcon();
	
	//Constructor
	public DeckPanel() {
		setLayout(null);

		for (int i = 0; i < currentDeck.length; i++) {
			currentDeck[i] = new JButton();
			currentDeck[i].setBounds(30 + (deckIconSize + deckIconPadding) * i, 120, deckIconSize, deckIconSize);
			add(currentDeck[i]);
		}

		timerIcon.setLocation(150, 15);
		add(timerIcon);

		flipCounterLabel.setFont(GuiUtil.PIXELFONTSMALL);
		flipCounterLabel.setForeground(Color.BLACK);
		flipCounterLabel.setBounds(30, 60, 180, 30);
		add(flipCounterLabel);
	}

	
	//UPDATE METHODS:
	
	//updates timer based on elapsed and max time, a "wrapper" toward timerIcon
	public void updateTimer(int time, int maxTime) {
		timerIcon.update(time, maxTime);
	}
	
	public void updateFlipCounter(int remainingFlips) {
		if (remainingFlips < 0) {
			flipCounterLabel.setText("Flips: INF");
		} else {
			flipCounterLabel.setText("Flips: " + remainingFlips);
		}
	}
	
	//updates visuals based off data of players
	public void update(Player currentPlayer) {
		setIcon(deckImages.get(currentPlayer.getTeam()));

		for (int i = 0; i < currentDeck.length; i++) {
			currentDeck[i].setIcon(ViewConstants.cardImages.get(currentPlayer.getDeck()[i]));
		}

		updateFlipCounter(currentPlayer.getRemainingFlips());
	}
	
	
	//set if the card/ability buttons is enabled
	public void setDeckButtonsEnabled(boolean isEnabled) {
		for (JButton cardButton : currentDeck) {
			cardButton.setEnabled(isEnabled);
		}
	}

	public JButton[] getCurrentDeck() {
		return currentDeck;
	}

	
	//Ui hover/selection logic
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

}