package model;

import shared.GameConstants;
import shared.PiecePlacer;
import shared.Team;

public class Game {

    private Team currentTeam;
    private Team[][] board;

    //CONSTRUCTOR
    public Game(){
        board = new Team[GameConstants.boardSize][GameConstants.boardSize];
        for (int x = 0; x < GameConstants.boardSize; x++){
            for (int y = 0; y < GameConstants.boardSize; y++){
                board[x][y] = Team.NULL;
            }
        }
        //test board blocks
        board[2][4] = Team.RED;
        board[6][6] = Team.YELLOW;
    }

    public void startGame(){

    }
    public void playTurn(int posX, int posY, boolean[][]pieceData){
    	if (!PiecePlacer.validMove(board, posX, posY, pieceData)) {
    		throw new IllegalArgumentException("Invalid Move");
    	}
        //place shape
    	for (int x=0; x<pieceData.length; x++) {
    		for (int y=0; y<pieceData[0].length; y++) {
    			if (pieceData[x][y]) {
    				board[x+posX][y+posY] = Team.RED; //TODO place piece with proper team enum
    			}
        	}
    	}
        //next turn
    }

    public boolean isGameOver(){
        return false;
    }

    public Team[][] getBoard(){
        return board;
    }







}
