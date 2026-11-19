package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import model.Game;
import view.*;
import view.gameUi.Piece;


//Aiden
public class GameController implements ActionListener, MouseListener {
    //controls the GameScreen input and stuff
	private AppController appController;
    private Game model = new Game();
    private GameScreen gameScreen;

    public GameController(AppController appController, GameScreen gamescreen){
    	this.appController = appController;
        this.gameScreen = gamescreen;
        gameScreen.update(model.getBoard());
        //Adding listeners
        for (Piece piece : gamescreen.getPiecePanel().getPieces()){

            piece.addActionListener(this);
            piece.addMouseListener(this);
        };
        gamescreen.boardPanel.addMouseListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
    	System.out.print("click piece");
    	appController.toMenuScreen();
        if (e.getSource() instanceof Piece) {
            Piece clickedPiece = (Piece) e.getSource();
            // handle piece click, selected piece is this piece
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        //if mouse is inside boardpanel, add snapped pos to board, play ur turn!
    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {
    	System.out.print("selcaaz");
        if (e.getSource() instanceof Piece) {
//            Piece clickedPiece = (Piece) e.getSource();
            // handle piece hover animation
        	System.out.print("selct piece");
        	gameScreen.selectPiece((Piece) e.getSource());
        }
    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
}
