package com.beatbox.server.ui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JList;

import com.beatbox.server.ServerController;

public class RemoveButtonListener implements ActionListener {

	ServerController controller;

	public RemoveButtonListener(ServerController controller) {
		this.controller = controller;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		JList playlist = controller.getUI().getPlaylist();
		int[] indicies = playlist.getSelectedIndices();
		indicies = reverse(indicies);
		for (int index : indicies) {
			controller.removeSongFromPlaylistAt(index);
		}
	}

	private int[] reverse(int[] inArray) {
		int length = inArray.length;
		int[] outArray = new int[length];
		for (int i = 0; i < length; i++) {
			outArray[i] = inArray[length - i - 1];
		}
		return outArray;
	}
}
