/*
 * File: SettingsOverlay.java
 * Project: SDP #1 - BLOKUS
 * Course: [Course code, section, and instructor]
 * Group: 5 (Aiden, Yanjun, Melinda)
 * Contributors: Melinda
 * Date: November 27, 2026
 * Description: Displays and handles game settings controls.
 */

package view.overlay;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JToggleButton;

import view.GuiUtil;

//melinda
public class SettingsOverlay extends Overlay {

	// imageIcon Resources
	private static final ImageIcon frameImg = new ImageIcon("resources/ui/SettingsOverlayFrame.png");
	private static final ImageIcon saveButtonImg = new ImageIcon("resources/ui/SettingsOverlaySaveButton.png");
	private static final ImageIcon saveButtonHoverImg = new ImageIcon(
			"resources/ui/SettingsOverlaySaveButtonHover.png");
//	private static final ImageIcon leaveButtonImg = new ImageIcon("resources/ui/SettingsOverlayLeaveButton.png");
//	private static final ImageIcon leaveButtonHoverImg = new ImageIcon("resources/ui/SettingsOverlayLeaveButtonHover.png");

	// UI Elements
	private final JToggleButton musicButton = new JToggleButton("ON", true);
	private final JToggleButton sfxButton = new JToggleButton("ON", true);
	private final JButton saveButton = GuiUtil.createButton(saveButtonImg, saveButtonHoverImg);

	// Constructor
	public SettingsOverlay() {
		super(frameImg);

		closeButton.setLocation(540, 38);

		musicButton.setBounds(310, 120, 100, 40);
		musicButton.setFont(GuiUtil.PIXELFONTMEDIUM);
		add(musicButton);

		sfxButton.setBounds(310, 190, 100, 40);
		sfxButton.setFont(GuiUtil.PIXELFONTMEDIUM);
		add(sfxButton);

		saveButton.setBounds(247, 280, 146, 71);
		add(saveButton);

	}

	// Getters for UI elements
	public JToggleButton getMusicButton() {
		return musicButton;
	}

	public JToggleButton getSfxButton() {
		return sfxButton;
	}

	public JButton getSaveButton() {
		return saveButton;
	}
}