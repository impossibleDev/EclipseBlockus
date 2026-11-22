package view;

import shared.Team;
import view.gameUi.BoardPanel;
import view.gameUi.Piece;
import view.gameUi.PiecesPanel;
import view.gameUi.TeamPanel;

import javax.swing.*;

import model.Player;

//Aiden
public class GameScreen extends Screen{

    //Ui elements
    private BoardPanel boardPanel;
    private TeamPanel teamPanel;
    private PiecesPanel piecesPanel;
    private JButton settingsButton;
    JLabel[] teamBgCorner;

    //Constructor
    public GameScreen(){
        super();
        //add ui elements
        boardPanel = new BoardPanel();
        teamPanel = new TeamPanel();
        piecesPanel = new PiecesPanel();
        settingsButton = GuiUtil.createButton(ViewConstants.settingsImage);
        add(boardPanel);
        add(teamPanel);
        add(piecesPanel);
        add(settingsButton);

        boardPanel.setVisible(true);

    }

    public void update(Player currentPlayer, Player[] players, Team[][] board){
    	this.teamPanel.update(currentPlayer, players);
    	this.piecesPanel.update(currentPlayer);
        this.boardPanel.updateBoard(board);
    }

    public void setCurrentTeam(){

    }

    //Getters for UI Elements
    public PiecesPanel getPiecePanel(){ return piecesPanel;}
    public BoardPanel getBoardPanel() { return boardPanel;}; 
    public JButton getSettingsButton() {return settingsButton;}
    
    @Override
    public void resize(){
        super.resize();
        boardPanel.setBounds((int)(getWidth()/2-200),50,400,400);
        teamPanel.setBounds(100,50,250,660);;
        piecesPanel.setBounds(getWidth()-250,50,221,480);
        settingsButton.setBounds(getWidth()-70,10,60,60);
    }

	public void hoverPiece(Piece piece) {
		piecesPanel.hoverPiece(piece);
		
	}
	
	public void dehoverPiece() {
		piecesPanel.dehoverPiece();
		
	}
	
	public void selectPiece(Piece piece) {
		piecesPanel.selectPiece(piece);
		
	}

}
