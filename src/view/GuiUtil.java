// Description: Provides reusable GUI helpers, fonts, and button styles.
// Contributors: Aiden

package view;

import javax.swing.*;
import java.awt.*;
import java.io.File;

//Aiden
public class GuiUtil {

	// Font constants
	public static final Font PIXELFONT = loadCustomFont("resources/fonts/PressStart2P-Regular.ttf");
	public static Font PIXELFONTBIG = PIXELFONT.deriveFont(30f);
	public static Font PIXELFONTMEDIUM = PIXELFONT.deriveFont(20f);
	public static Font PIXELFONTSMALL = PIXELFONT.deriveFont(10f);

	//method for loading fonts
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
	// creates a button with hover image
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
		JButton button = createButton(normalImg);
		button.setRolloverIcon(hoverImg);
		button.setRolloverEnabled(true);
		return button;
	}

}