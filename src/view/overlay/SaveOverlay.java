package view.overlay;

import java.awt.*;
import javax.swing.*;
import view.GuiUtil;

// melinda
public class SaveOverlay extends Overlay {

	// imageIcon Resources
	private static final ImageIcon frameImg = new ImageIcon("resources/ui/SaveOverlayFrame.png");
	private static final ImageIcon yesButtonimg = new ImageIcon("resources/ui/SaveOverlayYesButton.png");
	private static final ImageIcon noButtonimg = new ImageIcon("resources/ui/SaveOverlayNoButton.png");
	private JButton saveButton = GuiUtil.createButton(yesButtonimg);
	private JButton dontSaveButton = GuiUtil.createButton(noButtonimg);;

	public SaveOverlay() {
		super(frameImg);
//		setSize(440, 210);
//		setLayout(new BorderLayout());
//		setOpaque(true);
//		setBackground(Color.WHITE);
//		setBorder(BorderFactory.createLineBorder(Color.BLACK, 3));
//		setPreferredSize(new Dimension(440, 210));

		// top panel
//		JLabel title = new JLabel("SAVE GAME", SwingConstants.CENTER);
//		title.setFont(GuiUtil.PIXELFONTMEDIUM);

//		closeButton = new JButton("X");
//		closeButton.setFont(GuiUtil.PIXELFONTSMALL);
//		closeButton.setBackground(Color.WHITE);
//		closeButton.setForeground(Color.BLACK);
//		closeButton.setFocusPainted(false);
//		closeButton.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
//		closeButton.setPreferredSize(new Dimension(35, 30));

//		JPanel top = new JPanel(new BorderLayout());
//		top.setOpaque(false);
//		top.setBorder(BorderFactory.createEmptyBorder(8, 12, 5, 12));
//		top.add(title, BorderLayout.CENTER);
//		top.add(closeButton, BorderLayout.EAST);
//		add(top, BorderLayout.NORTH);

//		// save game message
//		JLabel message = new JLabel(
//				"<html><div style='text-align: center;'>Do you want to save your current game?</div></html>",
//				SwingConstants.CENTER);
//		message.setFont(GuiUtil.PIXELFONTSMALL);
//		add(message, BorderLayout.CENTER);
//
//		// button panel for choices
//		JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 12));
//		buttonPanel.setOpaque(false);
//
//		saveButton = new JButton("Save");
//		saveButton.setFont(GuiUtil.PIXELFONTMEDIUM);
//		saveButton.setBackground(Color.WHITE);
//		saveButton.setForeground(Color.BLACK);
//
//		dontSaveButton = new JButton("Don't Save");
//		dontSaveButton.setFont(GuiUtil.PIXELFONTMEDIUM);
//		dontSaveButton.setBackground(Color.WHITE);
//		dontSaveButton.setForeground(Color.BLACK);

//		buttonPanel.add(saveButton);
//		buttonPanel.add(dontSaveButton);
//		add(buttonPanel, BorderLayout.SOUTH);
		saveButton.setBounds(195,270,112,68);
		add(saveButton);
		
		dontSaveButton.setBounds(370,270,112,68);
		add(dontSaveButton);
	}

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