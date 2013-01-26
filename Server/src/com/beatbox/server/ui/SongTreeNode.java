package com.beatbox.server.ui;

import javax.swing.tree.DefaultMutableTreeNode;

import com.beatbox.lib.song.Song;

public class SongTreeNode extends DefaultMutableTreeNode {

	private static final long serialVersionUID = 4018429364799907722L;

	Song song;

	public SongTreeNode(Song song) {
		super(song.getTitle());
		this.song = song;
	}

	public Song getSong() {
		return song;
	}
}
