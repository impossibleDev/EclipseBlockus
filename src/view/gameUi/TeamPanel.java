package view.gameUi;

import shared.GameConstants;

import javax.swing.*;
import java.awt.*;

//Aiden
public class TeamPanel extends JLabel {
	
	private static final ImageIcon img = new ImageIcon("resources/ui/TeamPanelFrame.png");
	
    //CONSTANTS
    private static final int deckIconSize = 75;
    private static final int deckIconPadding = 10;
    private static final int playerCardDistance = 110;

    //UI ELEMNTS
    private TimerIcon timerIcon = new TimerIcon(GameConstants.maxTime);
    private PlayerCard[] playerCard = new PlayerCard[4];
    private JLabel[] currentDeck = new JLabel[GameConstants.deckSize];


    public TeamPanel(){
    	super(img);

        //Init PlayerCards
        for (int i=0; i<4; i++){
            playerCard[i] = new PlayerCard();
            playerCard[i].setLocation(0, i*playerCardDistance);
            add(playerCard[i]);
        }

        //Init currentDeck icons
        for (int i=0; i<currentDeck.length; i++){
            currentDeck[i] = new JLabel("🃏");
            currentDeck[i].setBounds(10+(int)(deckIconSize+deckIconPadding)*i,520,deckIconSize,deckIconSize);
            add(currentDeck[i]);
        }

        //Init timerIcon
        timerIcon.setLocation(150, 500);
        add(timerIcon);
    }
}
