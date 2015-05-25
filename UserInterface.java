package com.github.cnguyen.texteditor;

import javax.swing.*;
import javax.swing.text.DefaultEditorKit;

import java.awt.*;
import java.awt.event.*;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class UserInterface extends JFrame {
	
	private final int WIDTH = 400;
	private final int HEIGHT = 400;
	
	private boolean isSaved = false;
	private String fileName;
	
	private JTextArea textArea;
	private JFileChooser fileChooser;
	
	public UserInterface() {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) { }
		
		fileChooser = new JFileChooser();
		setTitle("Text Editor");
		setSize(WIDTH, HEIGHT);
		
		JMenuBar menu = new JMenuBar();
		setJMenuBar(menu);
		
		JMenu file = new JMenu("File");
		JMenu edit = new JMenu("Edit");
		JMenu format = new JMenu("Format");
		
		file.setMnemonic('F');
		edit.setMnemonic('E');
		
		menu.add(file);
		menu.add(edit);
		menu.add(format);
		
		JMenuItem newFile = new JMenuItem("New");
		JMenuItem open = new JMenuItem("Open");
		JMenuItem save = new JMenuItem("Save");
		JMenuItem saveAs = new JMenuItem("Save As");
		JMenuItem exit = new JMenuItem("Exit");
		JMenuItem cut = new JMenuItem(new DefaultEditorKit.CutAction());
		JMenuItem copy = new JMenuItem(new DefaultEditorKit.CopyAction());
		JMenuItem paste = new JMenuItem(new DefaultEditorKit.PasteAction());
		JMenuItem backgroundColor = new JMenuItem("Background Color");
		JCheckBoxMenuItem wordWrap = new JCheckBoxMenuItem("Word Wrap", true);
		
		newFile.addActionListener(new File());
		newFile.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, ActionEvent.CTRL_MASK));
		
		open.addActionListener(new File());
		open.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, ActionEvent.CTRL_MASK));
		
		save.addActionListener(new File());
		save.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, ActionEvent.CTRL_MASK));
		
		saveAs.addActionListener(new File());
		
		exit.addActionListener(new File());
		exit.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_E, ActionEvent.CTRL_MASK));
		
		cut.setText("Cut");
		
		copy.setText("Copy");
		copy.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_C, ActionEvent.CTRL_MASK));
		
		paste.setText("Paste");
		paste.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_P, ActionEvent.CTRL_MASK));
		
		backgroundColor.addActionListener(new Edit());
		
		wordWrap.addItemListener(new ItemListener() {

			@Override
			public void itemStateChanged(ItemEvent e) {
				// Item is selected
				if (e.getStateChange() == 1)
					textArea.setLineWrap(true);
				else
					textArea.setLineWrap(false);
			}
			
		});
		
		file.add(newFile);
		file.add(open);
		file.add(save);
		file.add(saveAs);
		file.addSeparator();
		file.add(exit);
		edit.add(cut);
		edit.add(copy);
		edit.add(paste);
		edit.addSeparator();
		edit.add(backgroundColor);
		format.add(wordWrap);
		
		textArea = new JTextArea(10, 10);
		textArea.setLineWrap(true);
		textArea.setWrapStyleWord(true);
		
		JScrollPane scrollTextArea = new JScrollPane(textArea);
		
		add(scrollTextArea);
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
	}
	
	private void open() {
		fileChooser.setDialogTitle("Open");
		if (fileChooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
			String fileName = fileChooser.getSelectedFile().getAbsolutePath();
			try {
				FileReader reader = new FileReader(fileName);
				textArea.read(reader, null);
				reader.close();
				isSaved = false;
				this.fileName = fileName;
				setTitle(fileName);
			} catch (IOException e) {
				// ADD CATCH LINES
			}
		}
	}
	
	private void save() {
		try {
			FileWriter writer = new FileWriter(fileName + ".txt");
			textArea.write(writer);
			writer.close();
			isSaved = true;
			setTitle(fileName);
		} catch (IOException e) {
			// ADD CATCH LINES
		}
	}
	
	private void saveAs() {
		fileChooser.setDialogTitle("Save As");
		if (fileChooser.showSaveDialog(null) == JFileChooser.APPROVE_OPTION) {
			String fileName = fileChooser.getSelectedFile().getAbsolutePath();
			try {
				FileWriter writer = new FileWriter(fileName + ".txt");
				textArea.write(writer);
				writer.close();
				isSaved = true;
				this.fileName = fileName;
				setTitle(fileName);
			} catch (IOException e) {
				// ADD CATCH LINES
			}
		}
	}
	
	private class File implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			String command = e.getActionCommand();
			
			if (command.equals("New")) {
				if (textArea.getText().equals("")) {
					dispose();
					new UserInterface();
				} else {
					int userInput = JOptionPane.showConfirmDialog(
							UserInterface.this, "Would you like to save first?",
							"Save", JOptionPane.YES_NO_OPTION);
					if (userInput == JOptionPane.YES_OPTION) {
						if (isSaved)
							save();
						else
							saveAs();
					} else {
						dispose();
						new UserInterface();
					}
				}
			} else if (command.equals("Open"))
				open();
			else if (command.equals("Save"))
				save();
			else if (command.equals("Save As"))
				saveAs();
			else if (command.equals("Exit")) {
				System.exit(0);
			}
		}
		
	}
	
	private class Edit implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			String command = e.getActionCommand();
			
			if (command.equals("Background Color")) {
				Color selectedColor = JColorChooser.showDialog(null, "Select a Background Color", Color.WHITE);
				//set panel bg to selectedColor
			}
		}
		
	}
	
}
