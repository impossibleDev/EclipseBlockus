package view.gameUi;

import javax.swing.*;
import java.awt.*;

public class TimerIcon extends JLabel {//switch back to JPanel later
	private static final ImageIcon img = new ImageIcon("resources/ui/TimerIcon.png");
    private int time;
    private int maxTime;

    public TimerIcon(int maxTime){
        super(img);
        setOpaque(false);
        setSize(80,80);
        this.maxTime = maxTime;
        
        this.time = 75;
        this.maxTime = 360;
    }

    public void update(int time){
        this.time = time;
        repaint();//needed??
    }
    @Override
    protected void paintComponent(Graphics g) {
        int angleExtent = (int) (-360.0 * time / maxTime);
        g.setColor(Color.YELLOW);
        g.fillArc(11, 21, 56, 56, 90, angleExtent);
        super.paintComponent(g);
        //Custom paint for the timer (pie-like thing)
        //the ratio is time/maxTime % of the pie
    }
}
