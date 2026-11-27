/*
 * File: GuiUtil.java
 * Project: SDP #1 - BLOKUS
 * Course: [Course code, section, and instructor]
 * Group: 5 (Aiden, Yanjun, Melinda)
 * Contributors: Aiden
 * Date: November 27, 2026
 * Description: Provides reusable GUI helpers, fonts, and button styles.
 */

package view;

import javax.swing.*;
import java.awt.*;
import java.io.File;

//Aiden

//font code copied form my g11 project
public class GuiUtil {

	// Font constants
	public static final Font PIXELFONT = loadCustomFont("resources/fonts/PressStart2P-Regular.ttf");
	public static Font PIXELFONTBIG = PIXELFONT.deriveFont(30f);
	public static Font PIXELFONTMEDIUM = PIXELFONT.deriveFont(20f);
	public static Font PIXELFONTSMALL = PIXELFONT.deriveFont(10f);

	public static Font loadCustomFont(String path) {
		try {
			// Create the font object from the file
			Font customFont = Font.createFont(Font.TRUETYPE_FONT, new File(path));

			// Register it so the JVM can handle it
			GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
			ge.registerFont(customFont);

			// Return the font at the specific size you want
			return customFont;
		} catch (Exception e) {
			e.printStackTrace();
			// Fallback font if the file is missing
			return new Font("Arial", Font.BOLD, (int) 10);
		}
	}

	public static JButton createButton(String text) { // mainly for menu screen
		JButton button = new JButton(text);
		button.setFont(PIXELFONTMEDIUM);
		button.setAlignmentX(Component.CENTER_ALIGNMENT);
		button.setMaximumSize(new Dimension(200, 55));
		button.setOpaque(true);
		button.setFocusable(false);
		button.setBackground(Color.WHITE);
		button.setForeground(Color.BLACK);
		button.setBorder(BorderFactory.createLineBorder(Color.BLACK, 3));
		return button;
	}

	public static JButton createButton(ImageIcon img) { // creates button from imageIcon
		JButton button = new JButton(img);
		button.setBorderPainted(false);
		button.setContentAreaFilled(false);
		button.setFocusPainted(false);
		button.setOpaque(false);
		return button;
	}

	// creates a button with hover image
	public static JButton createButton(ImageIcon normalImg, ImageIcon hoverImg) {
		JButton button = new JButton(normalImg);
		button.setRolloverIcon(hoverImg);
		button.setRolloverEnabled(true);
		button.setBorderPainted(false);
		button.setContentAreaFilled(false);
		button.setFocusPainted(false);
		button.setOpaque(false);
		return button;
	}

}