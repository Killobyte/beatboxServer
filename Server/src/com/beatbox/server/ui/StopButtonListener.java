package com.beatbox.server.ui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import com.beatbox.server.ServerController;

public class StopButtonListener implements ActionListener {

	ServerController controller;

	public StopButtonListener(ServerController controller) {
		this.controller = controller;
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		if (controller.isMediaStarted()) {
			controller.stopPlaying();
			controller.getUI().setPlayButtonText("Play");
		}
	}
}
