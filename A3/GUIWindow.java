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

	JTextField candidatesCountTextBox = new JTextField();
	JTextField grepV1NameTextBox = new JTextField();
	JTextField grepV2NameTextBox = new JTextField();
	JTextField filePathTextBox = new JTextField();
	JTextField maxRunsTextBox = new JTextField();

	JLabel candidatesCountTextBoxLabel = new JLabel("Number of candidates:");
	JLabel grepV1NameTextBoxLabel = new JLabel("File name of older grep version:");
	JLabel grepV2NameTextBoxLabel = new JLabel("File name of newer grep version:");
	JLabel filePathTextBoxLabel = new JLabel("File path to grep executables:");
	JLabel maxRunsTextBoxLabel = new JLabel("Maximum number of runs:");

	JPanel candidatesCountPanel = new JPanel();
	JPanel grepV1NamePanel = new JPanel();
	JPanel grepV2NamePanel = new JPanel();
	JPanel filePathPanel = new JPanel();
	JPanel maxRunsPanel = new JPanel();

	JLabel outputLabel = new JLabel("Testing output:");
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

		candidatesCountTextBox.setFont(appLableFont);
		grepV1NameTextBox.setFont(appLableFont);
		grepV2NameTextBox.setFont(appLableFont);
		filePathTextBox.setFont(appLableFont);
		maxRunsTextBox.setFont(appLableFont);

		candidatesCountTextBoxLabel.setFont(appLableFont);
		grepV1NameTextBoxLabel.setFont(appLableFont);
		grepV2NameTextBoxLabel.setFont(appLableFont);
		filePathTextBoxLabel.setFont(appLableFont);
		maxRunsTextBoxLabel.setFont(appLableFont);

		candidatesCountTextBox.setColumns(30);
		grepV1NameTextBox.setColumns(30);
		grepV2NameTextBox.setColumns(30);
		filePathTextBox.setColumns(30);
		maxRunsTextBox.setColumns(30);

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

		outputLabel.setFont(appLableFont);
		outputTextArea.setFont(appLableFont);
		rightPanel.add(outputLabel);
		rightPanel.add(outputScrollPane);
		rightPanel.setLayout(new BoxLayout(rightPanel, BoxLayout.PAGE_AXIS));

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

}
