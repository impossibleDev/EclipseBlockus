package model;

import shared.CardTypes;
import shared.GameConstants;
import shared.PiecePlacer;
import shared.Team;

//Aiden
public class Game {
	
	//FIELDS
	private static final Team[] order = {Team.BLUE, Team.GREEN, Team.YELLOW, Team.RED};
	
    private Player currentPlayer;
    private Player[] players = new Player[GameConstants.playerSize];
    
    private Team[][] board;
    
    private int currentPlayerIndex = 0;
    private boolean currentPlayerUsedCard;

    //Constructor
    public Game(){
    	//INIT PLAYERS
    	for (int i=0; i<players.length; i++) {
    		players[i] = new Player(order[i]);
    	}
    	currentPlayer = players[0];
    	
    	
    	//INIT BOARD
        board = new Team[GameConstants.boardSize][GameConstants.boardSize];
        for (int x = 0; x < GameConstants.boardSize; x++){
            for (int y = 0; y < GameConstants.boardSize; y++){
                board[x][y] = Team.RED;
            }
        }
        
    }

    public void startGame(){

    }
    public void playTurn(int posX, int posY, boolean[][]pieceData){
    	
    	if (!PiecePlacer.validMove(board, posX, posY, pieceData, currentPlayer.getTeam())) {
    		throw new IllegalArgumentException("Invalid Move");
    	}

        //place shape
    	for (int x=0; x<pieceData.length; x++) {
    		for (int y=0; y<pieceData[0].length; y++) {
    			if (pieceData[x][y]) {
    				board[x+posX][y+posY] = currentPlayer.getTeam();
    			}
        	}
    	}
        //next turn
    	if (currentPlayerIndex == order.length-1)
    		currentPlayerIndex=0;
    	else
    		currentPlayerIndex++;
    	currentPlayer = players[currentPlayerIndex];
        currentPlayerUsedCard = false;
    }

    public void playCard(CardTypes cardType, int x, int y) {
        if (currentPlayerUsedCard) {
            throw new IllegalStateException("Current player has already used a card this turn.");
        }
        CardTypes.useCard(cardType, board, x, y);
        currentPlayerUsedCard = true;
    }

    public boolean isGameOver(){
        return false;
    }

    public Team[][] getBoard(){
        return board;
    }
    
    public Player getCurrentPlayer() {
    	return currentPlayer;
    }

    public Player[] getPlayers() {
    	return players;
    }





}