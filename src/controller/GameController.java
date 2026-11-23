package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import model.Game;
import shared.PieceData;
import shared.PiecePlacer;
import view.*;
import view.gameUi.Piece;


//Aiden
public class GameController implements ActionListener, MouseListener, MouseMotionListener {
    //controls the GameScreen input and stuff
	private AppController appController;
    private Game model = new Game();
    private GameScreen gameScreen;
    
    private int previewPieceNum;
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
    		
    		//If selected, deselect
    		if (gameScreen.getPiecePanel().getSelectedPiece() == piece) {
    			gameScreen.getPiecePanel().deselectPiece();
    			previewPiece = null;
    			return;
    		}
    		
    		//Select
    		previewPieceNum = gameScreen.getPiecePanel().getPieceNum(piece);
    		previewPiece = piece.getData().clone();
    		
            gameScreen.selectPiece(piece);
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
    		
    		//TODO piece validation at PiecePlacer validMove()
    		int x = gameScreen.getBoardPanel().getPreviewX();
    		int y= gameScreen.getBoardPanel().getPreviewY();
    		if ( previewPiece != null && PiecePlacer.validMove(model.getBoard(), x, y, previewPiece) ) {
    			//place piece code goes here
    			model.playTurn(x, y, previewPiece);
    			gameScreen.getPiecePanel().deselectPiece();
    			previewPiece = null;
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
