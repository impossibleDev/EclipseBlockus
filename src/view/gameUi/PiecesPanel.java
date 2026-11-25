package view.gameUi;

import java.awt.GridLayout;
import javax.swing.*;

import model.Player;
import shared.PieceData;
import shared.Team;

//Aiden
public class PiecesPanel extends JLabel {

	private static final ImageIcon img = new ImageIcon("resources/ui/PiecePanelFrame.png");
	private Piece[] pieces;
	private Piece hoveredPiece;
	private Piece selectedPiece;
	private JPanel piecesContentPanel;

	public PiecesPanel() {
		super(img);
		setLayout(null);
		setOpaque(false);

		piecesContentPanel = new JPanel();
		piecesContentPanel.setLayout(new GridLayout(0, 2, 0, 14));
		piecesContentPanel.setOpaque(false);

		if (PieceData.pieces != null) {
			pieces = new Piece[PieceData.pieces.length];

			for (int i = 0; i < pieces.length; i++) {
				pieces[i] = new Piece(PieceData.pieces[i], Team.RED);
				piecesContentPanel.add(pieces[i]);
			}
		}

		JScrollPane scrollPane = new JScrollPane(piecesContentPanel);
		scrollPane.setOpaque(false);
		scrollPane.getViewport().setOpaque(false);
		scrollPane.setBorder(null);

		scrollPane.setBounds(40, 80, 250, 560);
		add(scrollPane);
		revalidate();
		repaint();
	}

	public Piece[] getPieces() {
		return pieces;
	}

	public int getPieceNum(Piece piece) {
		for (int i = 0; i < pieces.length; i++) {
			if (pieces[i] == piece) {
				return i;
			}
		}
		throw new IllegalArgumentException("Piece does not belong to PiecePanel");
	}

	public Piece getSelectedPiece() {
		return selectedPiece;
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

	public void dehoverPiece() {
		if (hoveredPiece != null) {
			hoveredPiece.setLocation(hoveredPiece.getX(), hoveredPiece.getY() + 5);
			hoveredPiece = null;
		}
	}

	public void selectPiece(Piece piece) {
		if (piece == null) {
			return;
		}
		deselectPiece();
		selectedPiece = piece;
		selectedPiece.altSkinOn(true);
	}

	public void deselectPiece() {
		if (selectedPiece != null) {
			selectedPiece.altSkinOn(false);
			selectedPiece = null;
		}
	}

	public void update(Player currentPlayer) {
		if (currentPlayer == null)
			return;
		for (Piece piece : pieces) {
			if (piece != null) {
				piece.setTeam(currentPlayer.getTeam());
			}
		}
	}
}