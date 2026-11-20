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
//        Piece testPiece = gamescreen.getPiecePanel().getTestPiece();
//        testPiece.addActionListener(this);
//        testPiece.addMouseListener(this);
        gamescreen.boardPanel.addMouseListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() instanceof Piece) {
            gameScreen.selectPiece((Piece) e.getSource());
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
        if (e.getSource() instanceof Piece) {
            // handle piece hover animation
        	gameScreen.hoverPiece((Piece)e.getSource());
        }
    }

    @Override
    public void mouseExited(MouseEvent e) {
    	if (e.getSource() instanceof Piece) {
    		// handle piece hover animation
    		gameScreen.dehoverPiece();
      }
    }
}
