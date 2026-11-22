package view.gameUi;

import model.Player;
import shared.CardTypes;
import shared.GameConstants;
import shared.Team;

import javax.swing.*;
import java.awt.*;

import static view.ViewConstants.cardImages;

public class PlayerCard extends JLabel {

    //CONSTANTS
    private static final int iconSize = 40;
    private static final int iconPadding = 5;

    //UI ELEMENTS
    JLabel[] deck = new JLabel[GameConstants.deckSize];

    public PlayerCard(){
        setSize(250,110);

        //init deck icons
        for (int i=0; i<deck.length; i++){
            deck[i] = new JLabel();
            deck[i].setBounds(30+(int)(iconSize+iconPadding)*i,40,iconSize,iconSize);
            add(deck[i]);
        }
        update(new Player(Team.RED)); //TEST, REMOVE LATER!!!
    }

    public void update(Player player){
        for (int i=0; i<deck.length; i++){
            if (player.getDeck()[i]!=null){
                deck[i].setIcon(cardImages.get(player.getDeck()[i]));
            }

        }
    }
}
