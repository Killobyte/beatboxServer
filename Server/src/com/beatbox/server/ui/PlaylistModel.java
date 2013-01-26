package com.beatbox.server.ui;

import java.util.ArrayList;

import javax.swing.AbstractListModel;

import com.beatbox.lib.song.Song;

public class PlaylistModel extends AbstractListModel<Song> {

	private static final long serialVersionUID = 4225730778450953578L;

	ArrayList<Song> playlist = new ArrayList<Song>();

	@Override
	public Song getElementAt(int i) {
		return playlist.get(i);
	}

	@Override
	public int getSize() {
		return playlist.size();
	}

	public void addSong(Song song) {
		playlist.add(song);
		fireIntervalAdded(this, playlist.size() - 1, playlist.size() - 1);
	}

	public void removeSong(int index) {
		playlist.remove(index);
		fireIntervalRemoved(this, index, index);
	}

}
