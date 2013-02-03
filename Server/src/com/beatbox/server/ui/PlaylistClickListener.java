package com.beatbox.server.ui;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JList;

import com.beatbox.lib.song.Song;
import com.beatbox.server.ServerController;

public class PlaylistClickListener extends MouseAdapter {

	JList<Song> list;
	ServerController controller;

	public PlaylistClickListener(JList<Song> list, ServerController controller) {
		this.list = list;
		this.controller = controller;
	}

	public void mouseClicked(MouseEvent e) {
		if (e.getClickCount() == 2) {
			controller.setPlayingIndex(list.getSelectedIndex());
			controller.stopPlaying();
			controller.startPlaying();
		}
	}

}
