package view.gameUi;

import shared.Team;

import javax.swing.*;

import model.Player;

//Aiden
public class TeamPanel extends JLabel {
	
	//IMAGE RESOURCES
	private static final ImageIcon img = new ImageIcon("resources/ui/TeamPanelFrame.png");
	
    private static final int playerCardDistance = 110;

    //UI ELEMNTS
    private PlayerCard[] playerCards = new PlayerCard[4];

    public TeamPanel(){
    	super(img);

        //Init PlayerCards
        for (int i=0; i<4; i++){
            playerCards[i] = new PlayerCard();
            playerCards[i].setLocation(0, i*playerCardDistance);
            add(playerCards[i]);
        }
    }
    
	public void update(Player[] players) {
		for (int i=0; i<players.length; i++) {
			playerCards[i].update(players[i]);
		}
    }
}
