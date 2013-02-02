package com.beatbox.server.music.ui.pathPrompt;

import java.awt.BorderLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

import com.beatbox.server.ServerController;

public class MusicPathPrompt extends JFrame {

	private static final long serialVersionUID = -6245423121742308722L;
	ServerController controller;
	JTextField filepathField;

	public MusicPathPrompt(ServerController controller) {
		this.controller = controller;

		filepathField = new JTextField();

		JButton okButton = new JButton("OK");
		okButton.addActionListener(new MusicPathOKListener(controller, this));

		this.add(new JLabel("Path to music directory:"),
				BorderLayout.PAGE_START);
		this.add(filepathField, BorderLayout.CENTER);
		this.add(okButton, BorderLayout.PAGE_END);

		this.pack();
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		this.setVisible(true);
	}

	public String getFilepath() {
		return filepathField.getText();
	}
}
