package com.beatbox.server.mediaPlayer;

import com.beatbox.lib.song.Song;
import com.beatbox.server.ServerController;

public class BBMediaPlayer extends Thread {
	ServerController controller;
	boolean paused;
	Song currentSong;

	public BBMediaPlayer(ServerController controller) {
		this.controller = controller;
	}

	public void run() {
		playNextSong();
	}

	public void playNextSong() {
		currentSong = controller.getNextSong();
		if (currentSong != null) {
			currentSong.play(new Runnable() {
				@Override
				public void run() {
					playNextSong();
				}
			});
			controller.setMediaStarted(true);
		}
	}

	public void stopMedia() {
		currentSong.stop();
		controller.setMediaStarted(false);
	}

	public void pauseOrResumeMedia() {
		currentSong.pauseOrResume();
	}
}
