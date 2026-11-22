package view.gameUi;

import shared.GameConstants;
import shared.Team;

import javax.swing.*;
import java.awt.*;

import static view.ViewConstants.teamColors;
import static view.ViewConstants.blockSize;
import static view.ViewConstants.blockPadding;

public class BoardPanel extends JPanel {

    private Team[][] board;
    private int previewX, previewY; //new
    private boolean[][] previewPieceData; //new
    public BoardPanel(){
        super();
        setSize(400,400);
    }

    public void updateBoard(Team[][] board){
        this.board = board;
        repaint();
    }
    
    public void previewPiece(int mouseX, int mouseY, boolean[][] pieceData) {
    	previewX = mouseX/blockSize;
    	previewY = mouseY/blockSize;
    	previewPieceData = pieceData;
        repaint();
    }
    
    public int getPreviewX() {return previewX; }
    public int getPreviewY() {return previewY; }
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (board == null) return;
        for (int x = 0; x< GameConstants.boardSize; x++){
            for (int y=0; y<GameConstants.boardSize; y++){
                //draw tile
                g.setColor(teamColors.get(board[x][y]));
                if (board[x][y]== Team.NULL)
                    g.fillRect(x*blockSize, y*blockSize, blockSize-blockPadding, blockSize-blockPadding);
                else
                    g.fillRoundRect(x*blockSize, y*blockSize, blockSize-blockPadding, blockSize-blockPadding, 2,2);
            }
        }
        if (previewPieceData == null) return;

        g.setColor(Color.CYAN); // Temporary preview color.
        for (int x = 0; x < previewPieceData.length; x++) {
            for (int y = 0; y < previewPieceData[x].length; y++) {
                if (!previewPieceData[x][y]) continue;

                int boardX = previewX + x;
                int boardY = previewY + y;
                if (boardX >= 0 && boardX < GameConstants.boardSize
                        && boardY >= 0 && boardY < GameConstants.boardSize) {
                    g.fillRoundRect(boardX * blockSize, boardY * blockSize,
                            blockSize - blockPadding, blockSize - blockPadding, 2, 2);
                }
            }
        }
    }
}
