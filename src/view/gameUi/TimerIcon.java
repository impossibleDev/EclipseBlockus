/*
 * File: TimerIcon.java
 * Project: SDP #1 - BLOKUS
 * Course: [Course code, section, and instructor]
 * Group: 5 (Aiden, Yanjun, Melinda)
 * Contributors: Melinda, Aiden
 * Date: November 27, 2026
 * Description: Displays and redraws the remaining turn time.
 */

package view.gameUi;

import javax.swing.*;
import java.awt.*;

// melinda, aiden
public class TimerIcon extends JLabel {

	// CONSTANTS
	private final int warningTime = 3;
	private final Color fillColor = Color.PINK;
	private final Color warningFillColor = Color.YELLOW;

	private static final ImageIcon baseImg = new ImageIcon("resources/ui/TimerIcon.png");
	private static final ImageIcon borderImg = new ImageIcon("resources/ui/TimerIconBorder.png");

	private int time, maxTime;

	// Constructor
	public TimerIcon() {
		super(baseImg);
		setSize(80, 80);
	}

	public void update(int time, int maxTime) {
		this.time = time;
		this.maxTime = maxTime;
		repaint();
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.setColor(maxTime - time > warningTime ? fillColor : warningFillColor);
		g.fillArc(11, 21, 56, 56, 90, -(int) ((double) time * 360 / maxTime));
		borderImg.paintIcon(this, g, 0, 0);
	}
}