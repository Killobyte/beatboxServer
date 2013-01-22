package com.beatbox.server.ui;

import javax.swing.JFrame;

import com.beatbox.server.ServerController;

public class ServerUI {

	ServerController controller;

	JFrame mainFrame;

	public ServerUI(ServerController controller) {
		this.controller = controller;

		mainFrame = new JFrame();
		mainFrame.setTitle("BeatBox Home Jukebox");

		mainFrame.setSize(300, 200);
		mainFrame.setLocationRelativeTo(null);
		mainFrame.addWindowListener(new BeatBoxWindowListener(controller));
		mainFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

		mainFrame.setVisible(true);
	}

}
