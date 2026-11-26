package view.overlay;


import javax.swing.JComboBox;
import javax.swing.JButton;
import javax.swing.ImageIcon;

//melinda
public class NewGameOverlay extends Overlay {

	//imageIcon Resources
	private static final ImageIcon frameImg = new ImageIcon("resources/ui/NewGameOverlayFrame.png");
	private static final ImageIcon playButtonImg = new ImageIcon("resources/ui/NewGameOverlayPlayButton.png");
	private static final ImageIcon playButtonHoverImg = new ImageIcon("resources/ui/NewGameOverlayPlayButtonHover.png");

	//UI Elements
	private final JComboBox<Integer> cpuSelector = new JComboBox<>(new Integer[] { 1, 2, 3 });
	private final JComboBox<Integer> flipSelector = new JComboBox<>(new Integer[] { 10, 25, 50 });
	private final JComboBox<Integer> timeLimitSelector = new JComboBox<>(new Integer[] { 10, 30, 60 });
	private final JButton startButton = new JButton(playButtonImg);

	//Constructor
	public NewGameOverlay() {
		super(frameImg);
		
		//positioning and adding elements
		closeButton.setLocation(525, 75);

		cpuSelector.setBounds(310, 150, 70, 40);
		add(cpuSelector);

		flipSelector.setBounds(310, 200, 70, 40);
		add(flipSelector);

		timeLimitSelector.setBounds(350, 250, 70, 40);
		add(timeLimitSelector);

		startButton.setRolloverIcon(playButtonHoverImg);
		startButton.setRolloverEnabled(true);
		startButton.setBounds(255, 300, 189, 90);
		add(startButton);
	}

	//Getters for UI elements
	public JButton getStartButton() {
		return startButton;
	}


	public JComboBox<Integer> getCpuSelector() {
		return cpuSelector;
	}

	public JComboBox<Integer> getFlipSelector() {
		return flipSelector;
	}

	public JComboBox<Integer> getTimeLimitSelector() {
		return timeLimitSelector;
	}
}
