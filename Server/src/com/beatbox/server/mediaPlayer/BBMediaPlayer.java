package com.beatbox.server.mediaPlayer;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;

import javax.media.Format;
import javax.media.Manager;
import javax.media.MediaLocator;
import javax.media.NoPlayerException;
import javax.media.Player;
import javax.media.PlugInManager;
import javax.media.Time;
import javax.media.format.AudioFormat;

import com.beatbox.lib.song.Song;
import com.beatbox.server.ServerController;

public class BBMediaPlayer extends Thread {
	ServerController controller;
	Player player;
	Time resumeTime;
	boolean paused;

	public BBMediaPlayer(ServerController controller) {
		this.controller = controller;
	}

	public void run() {
		playNextSong();
	}

	public void playNextSong() {
		Song nextSong = controller.getNextSong();
		File songFile = new File(nextSong.getPath());
		MediaLocator ml;
		Format input1 = new AudioFormat(AudioFormat.MPEGLAYER3);
		Format input2 = new AudioFormat(AudioFormat.MPEG);
		Format output = new AudioFormat(AudioFormat.LINEAR);
		PlugInManager.addPlugIn("com.sun.media.codec.audio.mp3.JavaDecoder",
				new Format[] { input1, input2 }, new Format[] { output },
				PlugInManager.CODEC);
		try {
			ml = new MediaLocator(songFile.toURI().toURL());
			player = Manager.createPlayer(ml);
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoPlayerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		player.start();
		controller.setPlaying(true);
		paused = false;
	}

	public void stopMedia() {
		player.stop();
		controller.setPlaying(false);
	}

	public void pauseAndResumeMedia() {
		if (paused) {
			player.setMediaTime(resumeTime);
			player.start();
		} else {
			resumeTime = player.getMediaTime();
			player.stop();
		}
		paused = !paused;
	}
}
