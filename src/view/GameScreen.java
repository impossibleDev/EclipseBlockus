package view;

import shared.Team;
import view.gameUi.BoardPanel;
import view.gameUi.Piece;
import view.gameUi.PiecesPanel;
import view.gameUi.TeamPanel;

import java.util.EnumMap;

import javax.swing.*;

import model.Player;

//Aiden
public class GameScreen extends Screen{
	
	//IMAGES
	public static final ImageIcon bgImageIcon = new ImageIcon("resources/bg/gameBg.png");
	public static final EnumMap<Team, ImageIcon> cornerImages = new EnumMap<>(Team.class);
	static {
		//CORNER IMAGES
        cornerImages.put(Team.YELLOW, new ImageIcon("resources/bg/CornerYellow.png"));
        cornerImages.put(Team.RED, new ImageIcon("resources/bg/CornerRed.png"));
        cornerImages.put(Team.GREEN, new ImageIcon("resources/bg/CornerGreen.png"));
        cornerImages.put(Team.BLUE, new ImageIcon("resources/bg/CornerBlue.png"));
	}
    //Ui elements
    private BoardPanel boardPanel;
    private TeamPanel teamPanel;
    private PiecesPanel piecesPanel;
    private JButton settingsButton;
    private JLabel currentCornerImage;
    private JLabel bgLabel = new JLabel(bgImageIcon);
    private EnumMap<Team, JLabel> cornerLabels = new EnumMap<>(Team.class);

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
        
        //corner images
        JLabel yellowCorner = new JLabel(cornerImages.get(Team.YELLOW));
        JLabel redCorner = new JLabel(cornerImages.get(Team.RED));
        JLabel blueCorner = new JLabel(cornerImages.get(Team.BLUE));
        JLabel greenCorner = new JLabel(cornerImages.get(Team.GREEN));

        cornerLabels.put(Team.YELLOW, yellowCorner);
        cornerLabels.put(Team.RED, redCorner);
        cornerLabels.put(Team.BLUE, blueCorner);
        cornerLabels.put(Team.GREEN, greenCorner);
        
        yellowCorner.setVisible(false);
        redCorner.setVisible(false);
        blueCorner.setVisible(false);
        greenCorner.setVisible(false);
        add(yellowCorner);
        add(redCorner);
        add(blueCorner);
        add(greenCorner);
        
        //bg image
        add(bgLabel);

    }

    public void update(Player currentPlayer, Player[] players, Team[][] board){
    	this.teamPanel.update(currentPlayer, players);
    	this.piecesPanel.update(currentPlayer);
        this.boardPanel.updateBoard(board);
        
        if (currentCornerImage!=null) {
        	currentCornerImage.setVisible(false);
        }
        currentCornerImage = cornerLabels.get(currentPlayer.getTeam());
        currentCornerImage.setVisible(true);
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
        boardPanel.setBounds((int)(getWidth()/2-250),(int)(getHeight()/2-250),500,500);
        teamPanel.setBounds(50,50,250,660);;
        piecesPanel.setBounds(getWidth()-300,50,250,660);
        settingsButton.setBounds(getWidth()-70,10,60,60);
        
        cornerLabels.get(Team.BLUE).setBounds(0,0,600,700);
        cornerLabels.get(Team.GREEN).setBounds(getWidth()-600,0,600,700);
        cornerLabels.get(Team.RED).setBounds(0,getHeight()-700,600,700);
        cornerLabels.get(Team.YELLOW).setBounds(getWidth()-600,getHeight()-700,600,700);
        bgLabel.setSize(getSize());
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
