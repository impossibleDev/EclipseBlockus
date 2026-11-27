package view.overlay;

import java.awt.Color;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;

import view.GuiUtil;

// melinda
public class NewGameOverlay extends Overlay {

	// ImageIcon resources
	private static final ImageIcon frameImg = new ImageIcon("resources/ui/NewGameOverlayFrame.png");
	private static final ImageIcon playButtonImg = new ImageIcon("resources/ui/NewGameOverlayPlayButton.png");
	private static final ImageIcon playButtonHoverImg = new ImageIcon("resources/ui/NewGameOverlayPlayButtonHover.png");

	// UI elements (supports both integers and "Unlimited")
	private final JComboBox<Object> cpuSelector = new JComboBox<>(new Object[] { 0, 1, 2, 3, 4 });
	private final JComboBox<Object> flipSelector = new JComboBox<>(new Object[] { "Unlimited", 10, 25, 50 });
	private final JComboBox<Object> timeLimitSelector = new JComboBox<>(new Object[] { "Unlimited", 10, 30, 60 });
	private final JButton startButton = new JButton(playButtonImg);

	// Constructor
	public NewGameOverlay() {
		super(frameImg);

		// Positioning close button inherited from Overlay
		closeButton.setLocation(525, 75);

		// Setup combo boxes cleanly using the helper method
		setupSelector(cpuSelector, 150);
		setupSelector(flipSelector, 200);
		setupSelector(timeLimitSelector, 250);

		// Style and position start button
		startButton.setRolloverIcon(playButtonHoverImg);
		startButton.setRolloverEnabled(true);
		startButton.setBorderPainted(false);
		startButton.setContentAreaFilled(false);
		startButton.setFocusPainted(false);
		startButton.setBounds(255, 300, 189, 90);
		add(startButton);
	}

	private void setupSelector(JComboBox<Object> box, int y) {
		styleComboBox(box);
		box.setBounds(350, y, 130, 40);
		add(box);
	}

	private void styleComboBox(JComboBox<Object> box) {
		box.setFont(GuiUtil.PIXELFONTSMALL);
		box.setBackground(Color.WHITE);
		box.setForeground(Color.BLACK);
	}

	// Getters for UI elements
	public JButton getStartButton() {
		return startButton;
	}

	public JComboBox<Object> getCpuSelector() {
		return cpuSelector;
	}

	public JComboBox<Object> getFlipSelector() {
		return flipSelector;
	}

	public JComboBox<Object> getTimeLimitSelector() {
		return timeLimitSelector;
	}
}