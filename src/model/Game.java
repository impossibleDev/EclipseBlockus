package model;

import shared.GameConstants;
import shared.PiecePlacer;
import shared.Team;

public class Game {

    private Player currentPlayer;
    private int currentPlayerIndex = 0;
    private Player[] players = new Player[GameConstants.playerSize];
    private Team[][] board;
    private static final Team[] order = {Team.BLUE, Team.GREEN, Team.YELLOW, Team.RED};

    //CONSTRUCTOR
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
                board[x][y] = Team.NULL;
            }
        }
        //test board blocks
        //board[2][4] = Team.RED;
        //board[6][6] = Team.YELLOW;
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