package view.gameUi;

import shared.Team;

import javax.swing.*;
import java.awt.*;

import static view.ViewConstants.teamColors;
import static view.ViewConstants.blockSize;
import static view.ViewConstants.blockPadding;

//Aiden
public class Piece extends JButton {
    private Team team;
    private boolean[][] data;
    private boolean altSkin = false;
    
    public Piece(boolean[][] data, Team team){
        setData(data);
        System.out.print(team.name());
        setTeam(team);
    }

    public boolean[][] getData(){
        return data;
    }
    
    public void setData(boolean[][] data){
        this.data = data;
        setSize(blockSize*data.length, blockSize*data[0].length);
    }
    public void setTeam(Team team){
        this.team = team;
        //color change code needed or not here idk?? - aiden
    }
    
    //custom paint
    @Override
    protected void paintComponent(Graphics g) {

    	//draw outlines if altSkin==true
    	if (altSkin) {
    		g.setColor(Color.WHITE);
            for (int x = 0; x< data.length; x++){
                for (int y=0; y<data[0].length; y++){
                    //draw one tile
                    if (data[x][y]) {
                        g.fillRect(x*blockSize, y*blockSize, blockSize, blockSize);
                    }
                }
            }
            
    	}
    	
    	//draw tiles
    	g.setColor(teamColors.get(team));
        for (int x = 0; x< data.length; x++){
            for (int y=0; y<data[0].length; y++){
                //draw one tile
                if (data[x][y]) {
                    g.fillRoundRect(x*blockSize + blockPadding/2, y*blockSize  + blockPadding/2, blockSize-blockPadding, blockSize-blockPadding, 2,2);
                }
            }
        }
        
    }
    
    //aka appearance when selected, if there is outline or not
    public void altSkinOn(boolean altSkin) {
    	this.altSkin = altSkin;
    	repaint();
    }
    
}
