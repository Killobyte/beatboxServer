package com.beatbox.server.music.ui.pathPrompt;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import com.beatbox.server.ServerController;

public class MusicPathOKListener implements ActionListener {

	ServerController controller;
	MusicPathPrompt pathPrompt;

	public MusicPathOKListener(ServerController controller,
			MusicPathPrompt pathPrompt) {
		this.controller = controller;
		this.pathPrompt = pathPrompt;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		controller.setFilepathFromPrompt(pathPrompt.getFilepath());
		pathPrompt.setVisible(false);
	}
}
