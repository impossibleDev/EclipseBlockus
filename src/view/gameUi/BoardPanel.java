package view.gameUi;

import shared.GameConstants;
import shared.Team;

import javax.swing.*;
import java.awt.*;

import static view.ViewConstants.teamColors;
import static view.ViewConstants.blockSize;
import static view.ViewConstants.blockPadding;

public class BoardPanel extends JPanel {
	
	//IMAGE RESOURCES
	private static final ImageIcon img = new ImageIcon("resources/ui/BoardPanelFrame.png");
	
	private JLabel borderFrameLabel = new JLabel(img);
    private Team[][] board;
    private int previewX, previewY;
    private boolean[][] previewPieceData;
    public BoardPanel(){
        super(null);
        borderFrameLabel.setBounds(0,0,500,500);
        add(borderFrameLabel);
        setOpaque(false);
    }

    public void updateBoard(Team[][] board){
        this.board = board;
        repaint();
    }
    
    public void previewPiece(int mouseX, int mouseY, boolean[][] pieceData) {
    	previewX = (mouseX-50)/blockSize;
    	previewY = (mouseY-50)/blockSize;
    	previewPieceData = pieceData;
        repaint();
    }
    
    public int getPreviewX() {return previewX; }
    public int getPreviewY() {return previewY; }
    @Override
    protected void paintComponent(Graphics g) {
    	g.setColor(Color.WHITE);
    	g.fillRect(50,50,400,400);
    	
        if (board == null) return;
        for (int x = 0; x< GameConstants.boardSize; x++){
            for (int y=0; y<GameConstants.boardSize; y++){
                //draw tile
                g.setColor(teamColors.get(board[x][y]));
                if (board[x][y]== Team.NULL)
                    g.fillRect(50+ x*blockSize, 50+ y*blockSize, blockSize-blockPadding, blockSize-blockPadding);
                else
                    g.fillRoundRect(50+ x*blockSize, 50+ y*blockSize, blockSize-blockPadding, blockSize-blockPadding, 2,2);
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
                    g.fillRoundRect(50+ boardX * blockSize, 50+ boardY * blockSize,
                            blockSize - blockPadding, blockSize - blockPadding, 2, 2);
                }
            }
        }
        
        //frame
    }
}
