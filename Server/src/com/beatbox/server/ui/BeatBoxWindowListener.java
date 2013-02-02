package com.beatbox.server.ui;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import com.beatbox.server.ServerController;

public class BeatBoxWindowListener extends WindowAdapter {
	private ServerController controller;

	public BeatBoxWindowListener(ServerController controller) {
		this.controller = controller;
	}

	public void windowClosing(WindowEvent e) {
		controller.saveConfig();
	}
}