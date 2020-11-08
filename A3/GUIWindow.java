import javax.swing.*;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Dimension;
import java.awt.Color;
import java.io.*;
import java.lang.*;
public class GUIWindow {
	private JFrame mainWindow;
	private JPanel mainPanel;
	private JLabel applicationLabel;
	private JLabel numberOfCandidatesInputLabel;
	private JSpinner numberOfCandidatesInput;
	private JLabel pathToOldGrepInputLabel;
	private JTextField pathToOldGrepInput;
	private JButton pathToOldGrepInputFileChooserButton;
	private JLabel pathToNewGrepInputLabel;
	private JTextField pathToNewGrepInput;
	private JButton pathToNewGrepInputFileChooserButton;
	private JLabel pathToTextFileInputLabel;
	private JTextField pathToTextFileInput;
	private JButton pathToTextFileInputFileChooserButton;
	private JLabel numberOfRunsInputLabel;
	private JSpinner numberOfRunsInput;
	private JLabel runOptionLabel;
	private JCheckBox artRunCheckbox;
	private JCheckBox rtRunCheckbox;
	private JLabel outputTextAreaLabel;
	private JScrollPane outputTextAreaScrollPane;
	private JTextArea outputTextArea;
	private JButton runTestButton;

	public GUIWindow(){
		this.mainWindow = new JFrame();
		this.mainPanel = new JPanel();
		this.mainPanel.setBorder(BorderFactory.createEmptyBorder(5, 15, 5, 15));
		this.mainWindow.setContentPane(this.mainPanel);
		this.applicationLabel = new JLabel();

		// Number of Candidates
		this.numberOfCandidatesInputLabel = new JLabel();
		this.numberOfCandidatesInput = new JSpinner();
		this.numberOfCandidatesInputLabel.setLabelFor(this.numberOfCandidatesInput);
		
		// Path to old GREP
		this.pathToOldGrepInputLabel = new JLabel();
		this.pathToOldGrepInput = new JTextField();
		this.pathToOldGrepInputFileChooserButton = new JButton();
		this.pathToOldGrepInputLabel.setLabelFor(this.pathToOldGrepInput);

		// Path to new GREP
		this.pathToNewGrepInputLabel = new JLabel();
		this.pathToNewGrepInput = new JTextField();
		this.pathToNewGrepInputFileChooserButton = new JButton();
		this.pathToNewGrepInputLabel.setLabelFor(this.pathToOldGrepInput);

		// Path to text file
		this.pathToTextFileInputLabel = new JLabel();
		this.pathToTextFileInput = new JTextField();
		this.pathToTextFileInputFileChooserButton = new JButton();
		this.pathToTextFileInputLabel.setLabelFor(this.pathToTextFileInput);

		// Number of Runs
		this.numberOfRunsInputLabel = new JLabel();
		this.numberOfRunsInput = new JSpinner();
		this.numberOfRunsInputLabel.setLabelFor(this.numberOfRunsInput);

		// Checkboxes for select ART or RT
		this.runOptionLabel = new JLabel();
		this.artRunCheckbox = new JCheckBox();
		this.rtRunCheckbox = new JCheckBox();

		// Output
		this.outputTextAreaLabel = new JLabel();
		this.outputTextArea = new JTextArea();
		this.outputTextAreaScrollPane = new JScrollPane(this.outputTextArea);
		this.outputTextAreaLabel.setLabelFor(this.outputTextAreaScrollPane);


		// Button to run test
		this.runTestButton = new JButton();
		

		// Set the default options when closing to exit_on_close
		this.mainWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.mainWindow.setResizable(true);


		// Get the current content pane which is running with the borderlayout by default
		this.mainPanel.setLayout(new GridBagLayout());
		// Create GridBagConstraints for the gridbaglayout inside of the input panel
		GridBagConstraints constraints = new GridBagConstraints();

		constraints.anchor = GridBagConstraints.BASELINE_LEADING;
		constraints.ipady = 14;

		// Application Label
		constraints.gridx = 0;
		constraints.gridy = 0;
		constraints.weightx = 1;
		constraints.fill = GridBagConstraints.HORIZONTAL;
		constraints.gridwidth = GridBagConstraints.REMAINDER;
		this.mainPanel.add(this.applicationLabel, constraints);


		// Number of Candidates
		constraints.gridx = 0;
		constraints.gridy = 1;
		constraints.weightx = 0;
		constraints.fill = GridBagConstraints.NONE;
		constraints.gridwidth = 1;
		this.mainPanel.add(this.numberOfCandidatesInputLabel, constraints);
		constraints.gridx = 1;
		constraints.weightx = 1;
		constraints.fill = GridBagConstraints.HORIZONTAL;
		constraints.gridwidth = 3;
		this.mainPanel.add(this.numberOfCandidatesInput, constraints);

		// Path to Older GREP Executable
		constraints.gridx = 0;
		constraints.gridy = 2;
		constraints.weightx = 0;
		constraints.fill = GridBagConstraints.NONE;
		constraints.gridwidth = 1;
		this.mainPanel.add(this.pathToOldGrepInputLabel, constraints);
		constraints.gridx = 1;
		constraints.weightx = 1;
		constraints.fill = GridBagConstraints.HORIZONTAL;
		constraints.gridwidth = 2;
		this.mainPanel.add(this.pathToOldGrepInput, constraints);
		constraints.gridx = 3;
		constraints.weightx = 0;
		constraints.fill = GridBagConstraints.NONE;
		constraints.gridwidth = GridBagConstraints.REMAINDER;
		this.mainPanel.add(this.pathToOldGrepInputFileChooserButton, constraints);

		// Path to Older GREP Executable
		constraints.gridx = 0;
		constraints.gridy = 3;
		constraints.weightx = 0;
		constraints.fill = GridBagConstraints.NONE;
		constraints.gridwidth = 1;
		this.mainPanel.add(this.pathToNewGrepInputLabel, constraints);
		constraints.gridx = 1;
		constraints.weightx = 1;
		constraints.fill = GridBagConstraints.HORIZONTAL;
		constraints.gridwidth = 2;
		this.mainPanel.add(this.pathToNewGrepInput, constraints);
		constraints.gridx = 3;
		constraints.weightx = 0;
		constraints.fill = GridBagConstraints.NONE;
		constraints.gridwidth = GridBagConstraints.REMAINDER;
		this.mainPanel.add(this.pathToNewGrepInputFileChooserButton, constraints);

		// Path to Text file for testing
		constraints.gridx = 0;
		constraints.gridy = 4;
		constraints.weightx = 0;
		constraints.fill = GridBagConstraints.NONE;
		constraints.gridwidth = 1;
		this.mainPanel.add(this.pathToTextFileInputLabel, constraints);
		constraints.gridx = 1;
		constraints.weightx = 1;
		constraints.fill = GridBagConstraints.HORIZONTAL;
		constraints.gridwidth = 2;
		this.mainPanel.add(this.pathToTextFileInput, constraints);
		constraints.gridx = 3;
		constraints.weightx = 0;
		constraints.fill = GridBagConstraints.NONE;
		constraints.gridwidth = GridBagConstraints.REMAINDER;
		this.mainPanel.add(this.pathToTextFileInputFileChooserButton, constraints);

		// Number of Runs
		constraints.gridx = 0;
		constraints.gridy = 5;
		constraints.weightx = 0;
		constraints.fill = GridBagConstraints.NONE;
		constraints.gridwidth = 1;
		this.mainPanel.add(this.numberOfRunsInputLabel, constraints);
		constraints.gridx = 1;
		constraints.weightx = 1;
		constraints.fill = GridBagConstraints.HORIZONTAL;
		constraints.gridwidth = GridBagConstraints.REMAINDER;
		this.mainPanel.add(this.numberOfRunsInput, constraints);

		// Checkboxes and button to run
		constraints.gridx = 0;
		constraints.gridy = 6;
		constraints.weightx = 0;
		constraints.fill = GridBagConstraints.NONE;
		constraints.gridwidth = 1;
		this.mainPanel.add(this.runOptionLabel, constraints);
		constraints.gridx = 1;
		this.mainPanel.add(this.artRunCheckbox, constraints);
		constraints.gridx = 2;
		this.mainPanel.add(this.rtRunCheckbox, constraints);
		constraints.gridx = 3;
		constraints.fill = GridBagConstraints.HORIZONTAL;
		constraints.gridwidth = GridBagConstraints.REMAINDER;
		this.mainPanel.add(this.runTestButton, constraints);

		// Output area
		constraints.gridx = 0;
		constraints.gridy = 7;
		constraints.weightx = 1;
		constraints.fill = GridBagConstraints.HORIZONTAL;
		constraints.gridwidth = GridBagConstraints.REMAINDER;
		this.mainPanel.add(this.outputTextAreaLabel, constraints);
		constraints.gridx = 0;
		constraints.gridy = 8;
		constraints.weightx = 1;
		constraints.weighty = 1;
		constraints.fill = GridBagConstraints.BOTH;
		constraints.gridwidth = GridBagConstraints.REMAINDER;
		constraints.gridheight = GridBagConstraints.REMAINDER;
		this.mainPanel.add(this.outputTextAreaScrollPane, constraints);

		this.setupFileChooser();
		this.setupData();
		this.retranslateUI();
		this.styleUI();
		
		this.mainWindow.pack();
		this.mainWindow.setMinimumSize(new Dimension(600, 800));
		this.mainWindow.setVisible(true);
	}

	private void setupFileChooser(){
		this.pathToOldGrepInputFileChooserButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				JFileChooser pathToOldGrepInputFileChooser = new JFileChooser();
				pathToOldGrepInputFileChooser.setCurrentDirectory(new File(System.getProperty("user.home")));
				int result = pathToOldGrepInputFileChooser.showOpenDialog(null);
				if (result == JFileChooser.APPROVE_OPTION) {
				    File selectedFile = pathToOldGrepInputFileChooser.getSelectedFile();
				    pathToOldGrepInput.setText(selectedFile.getAbsolutePath());
				}
			}
		});
		this.pathToNewGrepInputFileChooserButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				JFileChooser pathToNewGrepInputFileChooser = new JFileChooser();
				pathToNewGrepInputFileChooser.setCurrentDirectory(new File(System.getProperty("user.home")));
				int result = pathToNewGrepInputFileChooser.showOpenDialog(null);
				if (result == JFileChooser.APPROVE_OPTION) {
				    File selectedFile = pathToNewGrepInputFileChooser.getSelectedFile();
				    pathToNewGrepInput.setText(selectedFile.getAbsolutePath());
				}
			}
		});
		this.pathToTextFileInputFileChooserButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				JFileChooser pathToTextFileInputFileChooser = new JFileChooser();
				pathToTextFileInputFileChooser.setCurrentDirectory(new File(System.getProperty("user.home")));
				int result = pathToTextFileInputFileChooser.showOpenDialog(null);
				if (result == JFileChooser.APPROVE_OPTION) {
				    File selectedFile = pathToTextFileInputFileChooser.getSelectedFile();
				    pathToTextFileInput.setText(selectedFile.getAbsolutePath());
				}
			}
		});
	}

	private void setupData(){
		this.numberOfCandidatesInput.setModel(new SpinnerNumberModel(5, 0, Integer.MAX_VALUE, 1));
		this.numberOfRunsInput.setModel(new SpinnerNumberModel(5, 0, Integer.MAX_VALUE, 1));
		this.outputTextArea.setText("Test");
	}

	private void retranslateUI(){
		this.mainWindow.setTitle("GREP(A)RT");
		this.applicationLabel.setText("GREP(A)RT - A tool to test GREP with ART and RT");
		this.numberOfCandidatesInputLabel.setText("Number of Candidates: ");
		this.pathToOldGrepInputLabel.setText("Path to Older GREP executable: ");
		this.pathToNewGrepInputLabel.setText("Path to New GREP executable: ");
		this.pathToTextFileInputLabel.setText("Path to text file for testing: ");
		this.numberOfRunsInputLabel.setText("Maximum number of runs: ");
		this.runOptionLabel.setText("Run options: ");
		this.artRunCheckbox.setText("Run ART");
		this.rtRunCheckbox.setText("Run RT");
		this.outputTextAreaLabel.setText("Output: ");
		this.pathToOldGrepInputFileChooserButton.setText("Open");
		this.pathToNewGrepInputFileChooserButton.setText("Open");
		this.pathToTextFileInputFileChooserButton.setText("Open");
		this.runTestButton.setText("Run Test");
	}

	private void styleUI(){
		Font inputOutputLabelFont = new Font(Font.SANS_SERIF, Font.BOLD, 14);
		Font inputOutputFieldFont = new Font(Font.MONOSPACED, Font.PLAIN, 14);
		this.applicationLabel.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 20));
		
		// Set input labels and output labels font
		this.numberOfCandidatesInputLabel.setFont(inputOutputLabelFont);
		this.pathToOldGrepInputLabel.setFont(inputOutputLabelFont);
		this.pathToNewGrepInputLabel.setFont(inputOutputLabelFont);
		this.pathToTextFileInputLabel.setFont(inputOutputLabelFont);
		this.numberOfRunsInputLabel.setFont(inputOutputLabelFont);
		this.runOptionLabel.setFont(inputOutputLabelFont);
		this.artRunCheckbox.setFont(inputOutputLabelFont);
		this.rtRunCheckbox.setFont(inputOutputLabelFont);
		this.outputTextAreaLabel.setFont(inputOutputLabelFont);

		// Set input fields and output field font
		this.numberOfCandidatesInput.setFont(inputOutputFieldFont);
		this.pathToOldGrepInput.setFont(inputOutputFieldFont);
		this.pathToNewGrepInput.setFont(inputOutputFieldFont);
		this.pathToTextFileInput.setFont(inputOutputFieldFont);
		this.numberOfRunsInput.setFont(inputOutputFieldFont);

		// Set spinner fields left align text
		JSpinner.DefaultEditor numberOfCandidatesInputEditor = (JSpinner.DefaultEditor)this.numberOfCandidatesInput.getEditor();
		JSpinner.DefaultEditor numberOfRunsInputEditor = (JSpinner.DefaultEditor)this.numberOfRunsInput.getEditor();
		numberOfCandidatesInputEditor.getTextField().setHorizontalAlignment(JTextField.LEFT);
		numberOfRunsInputEditor.getTextField().setHorizontalAlignment(JTextField.LEFT);

		// Set output text area to false for editable
		this.outputTextArea.setEditable(false);
	}

}