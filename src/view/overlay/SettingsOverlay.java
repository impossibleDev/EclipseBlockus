package view.overlay;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JToggleButton;
import javax.swing.SwingConstants;

import view.GuiUtil;

//yanjun
// The settings window shown on top of the Overlay background.
// It has a title bar with a close button, music/sfx switches and a save button.
public class SettingsOverlay extends Overlay {

    private final JToggleButton musicButton = new JToggleButton("ON", true);
    private final JToggleButton sfxButton = new JToggleButton("ON", true);
    private final JButton saveButton = new JButton("Save!");
    private final JButton closeButton = new JButton("X");

    public SettingsOverlay() {
        Color darkBrown = new Color(74, 48, 32);

        // Panel appearance: layout, solid background, border, fixed size
        setLayout(new BorderLayout());
        setOpaque(true);
        setBackground(Color.WHITE);
        setBorder(BorderFactory.createLineBorder(Color.BLACK, 3));
        setPreferredSize(new Dimension(640, 440));

        //title
        JLabel title = new JLabel("SETTINGS", SwingConstants.CENTER);
        title.setFont(GuiUtil.PIXELFONTBIG);

        closeButton.setFont(GuiUtil.PIXELFONTMEDIUM);
        styleToggle(musicButton);
        styleToggle(sfxButton);

        JPanel top = new JPanel(new BorderLayout());
        top.setOpaque(false);
        top.setBorder(BorderFactory.createEmptyBorder(15, 10, 10, 10));
        top.add(title, BorderLayout.CENTER);
        top.add(closeButton, BorderLayout.EAST);
        add(top, BorderLayout.NORTH);

        //content
        // 3 rows x 2 columns: labels on the left, buttons on the right, so everything lines up
        JPanel content = new JPanel(new GridLayout(3, 2, 30, 30));
        content.setOpaque(false);
        content.setBorder(BorderFactory.createEmptyBorder(20, 40, 40, 40));

        // Row 0: Music
        content.add(makeLabel("Music", darkBrown));
        content.add(wrap(musicButton));

        // Row 1: SFX
        content.add(makeLabel("SFX", darkBrown));
        content.add(wrap(sfxButton));

        // Row 2: Save Game
        content.add(makeLabel("Save Game", darkBrown));
        saveButton.setFont(GuiUtil.PIXELFONTMEDIUM);
        content.add(wrap(saveButton));

        add(content, BorderLayout.CENTER);
    }

    // Puts a button in a small panel so it keeps a fixed size
    // and sits in the vertical middle of its grid cell (same height as the label)
    private JPanel wrap(JComponent button) {
        button.setPreferredSize(new Dimension(150, 50));   // same size for all buttons
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setOpaque(false);
        GridBagConstraints c = new GridBagConstraints();
        c.anchor = GridBagConstraints.WEST;   // left aligned, vertically centered
        c.weightx = 1;
        panel.add(button, c);
        return panel;
    }

    // Creates a label with the game font and the given color
    private JLabel makeLabel(String text, Color color) {
        JLabel label = new JLabel(text);
        label.setFont(GuiUtil.PIXELFONTMEDIUM);
        label.setForeground(color);
        return label;
    }

    private void styleToggle(JToggleButton button) {
        button.setFont(GuiUtil.PIXELFONTMEDIUM);
    }

    // Accessors let the controller connect behavior to the controls.
    public JToggleButton getMusicButton() { return musicButton; }
    public JToggleButton getSfxButton() { return sfxButton; }
    public JButton getSaveButton() { return saveButton; }
    public JButton getCloseButton() { return closeButton; }
}