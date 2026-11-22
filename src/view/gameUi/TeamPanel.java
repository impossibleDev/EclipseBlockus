package view.gameUi;

import shared.GameConstants;
import shared.Team;

import javax.swing.*;

import model.Player;

import static view.ViewConstants.cardImages;

import java.util.EnumMap;

//Aiden
public class TeamPanel extends JLabel {
	
	//IMAGE RESOURCES
	private static final ImageIcon img = new ImageIcon("resources/ui/TeamPanelFrame.png");
	public static final EnumMap<Team, ImageIcon> deckImages = new EnumMap<>(Team.class);
	static {
//		teamColors.put(Team.NULL, Color.GRAY);
		deckImages.put(Team.YELLOW, new ImageIcon("resources/ui/DeckYellow.png"));
		deckImages.put(Team.RED, new ImageIcon("resources/ui/DeckRed.png"));
		deckImages.put(Team.GREEN, new ImageIcon("resources/ui/DeckGreen.png"));
		deckImages.put(Team.BLUE, new ImageIcon("resources/ui/DeckBlue.png"));
	}
	
    //CONSTANTS
    private static final int deckIconSize = 40;
    private static final int deckIconPadding = 10;
    private static final int playerCardDistance = 110;

    //UI ELEMNTS
    private TimerIcon timerIcon = new TimerIcon(GameConstants.maxTime);
    private PlayerCard[] playerCard = new PlayerCard[4];
    private JLabel[] currentDeck = new JLabel[GameConstants.deckSize];
    private JLabel deckContainer = new JLabel();

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
            currentDeck[i] = new JLabel();
            currentDeck[i].setBounds(30+(int)(deckIconSize+deckIconPadding)*i,90,deckIconSize,deckIconSize);
            deckContainer.add(currentDeck[i]);
        }
        
        //Init deckContainer
        deckContainer.setBounds(0,440,250,220);
        add(deckContainer);
        
        //Init timerIcon
        timerIcon.setLocation(150, 500);
        add(timerIcon);
        update(new Player(Team.RED)); //test, remove later
    }
    
	public void update(Player player) {
		deckContainer.setIcon(deckImages.get(player.getTeam()));
		
		for (int i = 0; i < currentDeck.length; i++) {
			if (player.getDeck()[i] != null) {
				currentDeck[i].setIcon(cardImages.get(player.getDeck()[i]));
			}
		}
    }
}
