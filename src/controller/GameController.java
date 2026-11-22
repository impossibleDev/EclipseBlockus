package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import model.Game;
import shared.PieceData;
import view.*;
import view.gameUi.Piece;


//Aiden
public class GameController implements ActionListener, MouseListener, MouseMotionListener {
    //controls the GameScreen input and stuff
	private AppController appController;
    private Game model = new Game();
    private GameScreen gameScreen;
    
    private boolean[][] previewPiece;
    public GameController(AppController appController, GameScreen gameScreen){
    	this.appController = appController;
        this.gameScreen = gameScreen;
        gameScreen.update(model.getCurrentPlayer(), model.getPlayers(), model.getBoard());
        //Adding listeners
        gameScreen.getSettingsButton().addActionListener(this);;
        for (Piece piece : gameScreen.getPiecePanel().getPieces()){

            piece.addActionListener(this);
            piece.addMouseListener(this);
        };
//        gamescreen.boardPanel.addMouseListener(this);
        gameScreen.getBoardPanel().addMouseMotionListener(this);
        gameScreen.getBoardPanel().addMouseListener(this);
        
        //TEST CODE
        previewPiece = PieceData.pieces[0]; //remove ts later
    }

    @Override
    public void actionPerformed(ActionEvent e) {
    	if (e.getSource() == gameScreen.getSettingsButton()) {
    		//open up settings
    		//BUT FOR NOW, OPEN UP HELP OVERLAY (change later when settings is done)
    		appController.getView().setOverlayHelpPanel();
    		
    	}
    	else if (e.getSource() instanceof Piece) {
    		Piece piece = (Piece) e.getSource();
    		previewPiece = piece.getData();
            gameScreen.selectPiece(piece);
            // handle piece click, selected piece is this piece
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        
    }

    @Override
    public void mousePressed(MouseEvent e) {
    	//if mouse is inside boardpanel, add snapped pos to board, play ur turn!
    	if (e.getSource()== gameScreen.getBoardPanel()) {
    		//add piece if is valid
    		
    		//TODO piece validation
    		if (true) {//add piecevalidation method here
    			//place piece code goes here
    			model.playTurn(gameScreen.getBoardPanel().getPreviewX(), gameScreen.getBoardPanel().getPreviewY(), previewPiece);
    			gameScreen.update(model.getCurrentPlayer(), model.getPlayers(), model.getBoard());
    		}
    	}
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

	@Override
	public void mouseDragged(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		// TODO Auto-generated method stub
		//if inside boardPanel and selected piece, preview it
		
		gameScreen.getBoardPanel().previewPiece(e.getX(), e.getY(), previewPiece);
		
	}
}
