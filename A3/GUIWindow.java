import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class GUIWindow implements ActionListener {

	Font appLableFont = new Font("Helvetica Neue", Font.PLAIN, 20);
	Font appTitleFont = new Font("Helvetica Neue", Font.PLAIN, 30);

	JTextField candidatesCountTextBox = newInputField();
	JTextField grepV1NameTextBox = newInputField();
	JTextField grepV2NameTextBox = newInputField();
	JTextField filePathTextBox = newInputField();
	JTextField maxRunsTextBox = newInputField();

	JLabel candidatesCountTextBoxLabel = newLabel("Number of candidates:");
	JLabel grepV1NameTextBoxLabel = newLabel("File name of older grep version:");
	JLabel grepV2NameTextBoxLabel = newLabel("File name of newer grep version:");
	JLabel filePathTextBoxLabel = newLabel("File path to grep executables:");
	JLabel maxRunsTextBoxLabel = newLabel("Maximum number of runs:");

	JPanel candidatesCountPanel = new JPanel();
	JPanel grepV1NamePanel = new JPanel();
	JPanel grepV2NamePanel = new JPanel();
	JPanel filePathPanel = new JPanel();
	JPanel maxRunsPanel = new JPanel();

	JLabel outputLabel = newLabel("Testing output:");
	JTextArea outputTextArea = new JTextArea(15, 15);
	JScrollPane outputScrollPane = new JScrollPane(outputTextArea);

	JPanel leftPanel = new JPanel();
	JPanel rightPanel = new JPanel();
	JPanel centrePanel = new JPanel();

	JFrame window = new JFrame();
	JPanel mainPanel = new JPanel();

	JButton button = new JButton("mega lol");
	JLabel titleLabel = new JLabel("ART/RT GREP TESTING TOOL");
	int count = 0;

	public GUIWindow() {
		titleLabel.setFont(appTitleFont);

		buildLeftInputPanel();
		buildOutputPanel();

		centrePanel.add(leftPanel);
		centrePanel.add(rightPanel);

		mainPanel.add(titleLabel);
		mainPanel.add(centrePanel);
		mainPanel.add(button);
		button.addActionListener(this);

		// mainPanel.setBorder(BorderFactory.createEmptyBorder(500, 500, 500, 500));
		mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.PAGE_AXIS));

		window.add(mainPanel, BorderLayout.CENTER);

		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setTitle("ART/RT GREP TESTING TOOL");
		window.pack();
		window.setVisible(true);

	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		// bla bla bla does nothing useful here
		count++;
		titleLabel.setText("lol: " + count);
	}

	public void buildOutputPanel() {
		outputTextArea.setFont(appLableFont);
		rightPanel.add(outputLabel);
		rightPanel.add(outputScrollPane);
		rightPanel.setLayout(new BoxLayout(rightPanel, BoxLayout.PAGE_AXIS));
	}

	public void buildLeftInputPanel() {
		candidatesCountPanel.add(candidatesCountTextBoxLabel);
		candidatesCountPanel.add(candidatesCountTextBox);
		grepV1NamePanel.add(grepV1NameTextBoxLabel);
		grepV1NamePanel.add(grepV1NameTextBox);
		grepV2NamePanel.add(grepV2NameTextBoxLabel);
		grepV2NamePanel.add(grepV2NameTextBox);
		filePathPanel.add(filePathTextBoxLabel);
		filePathPanel.add(filePathTextBox);
		maxRunsPanel.add(maxRunsTextBoxLabel);
		maxRunsPanel.add(maxRunsTextBox);

		leftPanel.add(candidatesCountPanel);
		leftPanel.add(grepV1NamePanel);
		leftPanel.add(grepV2NamePanel);
		leftPanel.add(filePathPanel);
		leftPanel.add(maxRunsPanel);
		leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.PAGE_AXIS));
	}

	public JTextField newInputField() {
		JTextField field = new JTextField();
		field.setFont(appLableFont);
		field.setColumns(30);
		return field;
	}

	public JLabel newLabel(String text) {
		JLabel label = new JLabel(text);
		label.setFont(appLableFont);
		return label;
	}

}
