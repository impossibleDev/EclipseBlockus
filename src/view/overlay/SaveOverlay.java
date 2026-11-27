// Description: Prompts save and load controls for the game
// Contributors: Melinda

package view.overlay;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import view.GuiUtil;

// melinda
public class SaveOverlay extends Overlay {

	// ImageIcon resources
	private static final ImageIcon frameImg = new ImageIcon("resources/ui/SaveOverlayFrame.png");
	private static final ImageIcon yesButtonImg = new ImageIcon("resources/ui/SaveOverlayYesButton.png");
//	private static final ImageIcon yesButtonHoverImg = new ImageIcon("resources/ui/SaveOverlayYesButtonHover.png");
	private static final ImageIcon noButtonImg = new ImageIcon("resources/ui/SaveOverlayNoButton.png");
//	private static final ImageIcon noButtonHoverImg = new ImageIcon("resources/ui/SaveOverlayNoButtonHover.png");

	// UI elements
	private final JButton saveButton = GuiUtil.createButton(yesButtonImg);
	private final JButton dontSaveButton = GuiUtil.createButton(noButtonImg);

	public SaveOverlay() {
		super(frameImg);

		// Positioning close button inherited from Overlay
		closeButton.setLocation(577, 146);

		// Style and position save button
		saveButton.setBounds(220, 270, 112, 68);
		add(saveButton);

		dontSaveButton.setBounds(370, 270, 112, 68);
		add(dontSaveButton);
	}

	// Getters
	public JButton getSaveButton() {
		return saveButton;
	}

	public JButton getDontSaveButton() {
		return dontSaveButton;
	}

	public JButton getCloseButton() {
		return closeButton;
	}
}