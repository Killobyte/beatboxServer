package com.beatbox.server.ui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import com.beatbox.server.ServerController;

public class PlayButtonListener implements ActionListener {

	ServerController controller;

	public PlayButtonListener(ServerController controller) {
		this.controller = controller;
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		if (controller.isPaused()) {
			controller.resumePlayback();
			controller.getUI().setPlayButtonText("Pause");
		} else {
			if (controller.isMediaStarted()) {
				controller.pausePlayback();
				controller.getUI().setPlayButtonText("Play");
			} else if (controller.hasSongs()) {
				controller.startPlaying();
				controller.getUI().setPlayButtonText("Pause");
			}
		}
	}
}
