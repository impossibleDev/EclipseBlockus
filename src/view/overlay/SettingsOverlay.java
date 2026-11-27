// Description: Displays and handles game settings
// Contributors: Melinda

package view.overlay;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JToggleButton;

import view.GuiUtil;

//melinda
public class SettingsOverlay extends Overlay {

	// ImageIcon resources
	private static final ImageIcon frameImg = new ImageIcon("resources/ui/SettingsOverlayFrame.png");
	private static final ImageIcon saveButtonImg = new ImageIcon("resources/ui/SettingsOverlaySaveButton.png");
	private static final ImageIcon saveButtonHoverImg = new ImageIcon(
			"resources/ui/SettingsOverlaySaveButtonHover.png");
//	private static final ImageIcon leaveButtonImg = new ImageIcon("resources/ui/SettingsOverlayLeaveButton.png");
//	private static final ImageIcon leaveButtonHoverImg = new ImageIcon("resources/ui/SettingsOverlayLeaveButtonHover.png");

	// UI elements
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