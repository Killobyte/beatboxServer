package com.beatbox.server.ui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JTree;
import javax.swing.tree.TreePath;

import com.beatbox.server.ServerController;

public class AddButtonListener implements ActionListener {

	ServerController controller;

	public AddButtonListener(ServerController controller) {
		this.controller = controller;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		JTree tree = controller.getUI().getLibraryTree();
		TreePath[] paths = tree.getSelectionPaths();
		for (TreePath path : paths) {
			SongTreeNode songNode;
			try {
				songNode = (SongTreeNode) path.getLastPathComponent();
			} catch (java.lang.ClassCastException exception) {
				// We got a node that is not a leaf
				return;
			}
			controller.addSongToPlaylist(songNode.getSong());
		}
	}
}
