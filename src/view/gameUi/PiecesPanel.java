package view.gameUi;

import javax.swing.*;
import shared.PieceData;
import shared.Team;

//Aiden

//TODO someone work on autolayout/positioning the pieces[] !!!
public class PiecesPanel extends JLabel {
	
	private static final ImageIcon img = new ImageIcon("resources/ui/PiecePanelFrame.png");
    private Piece[] pieces;
    private Piece hoveredPiece;
    private Piece selectedPiece;

    public PiecesPanel(){
        super(img);
        setOpaque(false);
//        setSize(200,600);
        //load pieces from PieceData
        pieces = new Piece[PieceData.pieces.length];
        for (int i=0; i< pieces.length; i++){
            pieces[i] = new Piece(PieceData.pieces[i], Team.RED); //red for testing, dosent matter
            add(pieces[i]);
            pieces[i].setLocation(20,i*80);
        }

    }

    public Piece[] getPieces() {
        return pieces;
    }

    
	public void hoverPiece(Piece piece) {
		if (piece == null) {
			return;
		}
		if (hoveredPiece == piece) {
			dehoverPiece();
			return;
		}
		dehoverPiece();
		hoveredPiece = piece;
		hoveredPiece.setLocation(hoveredPiece.getX(), hoveredPiece.getY() - 5);
	}
    
    public void  dehoverPiece() {
    	if (hoveredPiece!=null) {
    		hoveredPiece.setLocation(hoveredPiece.getX(), hoveredPiece.getY()+5);
    		hoveredPiece = null;
    	}
		
    }
    
    public void selectPiece(Piece piece) {
    	if (piece == null) {
			return;
		}
		if (selectedPiece == piece) {
			deselectPiece();
			return;
		}
		deselectPiece();
		selectedPiece = piece;
		selectedPiece.altSkinOn(true);
    }
    
    public void deselectPiece() {
    	if (selectedPiece!=null) {
    		selectedPiece.altSkinOn(false);
    		selectedPiece = null;
    	}
    }
    
}
