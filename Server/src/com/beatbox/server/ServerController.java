package com.beatbox.server;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Scanner;

import org.json.JSONException;
import org.json.JSONObject;

import com.beatbox.lib.Library;
import com.beatbox.lib.song.Song;
import com.beatbox.server.mediaPlayer.BBMediaPlayer;
import com.beatbox.server.music.ui.pathPrompt.MusicPathPrompt;
import com.beatbox.server.ui.ServerUI;

public class ServerController {
	private String configPath = "config.json";
	private Library library;
	private ServerUI ui;
	private String musicPath;
	private JSONObject config;
	boolean isPlaying = false;
	private ArrayList<Song> playlist;
	BBMediaPlayer player;
	private int playingIndex;

	public ServerController() {

		playlist = new ArrayList<Song>();
		playingIndex = 0;

		if (readConfigFile()) {
			buildLibrary();
			startUI();
		} else {
			@SuppressWarnings("unused")
			MusicPathPrompt pathPrompt = new MusicPathPrompt(this);
		}

	}

	private boolean readConfigFile() {
		String jsonString = null;
		config = null;
		try {
			jsonString = new Scanner(new File(configPath)).useDelimiter("\\Z")
					.next();
			config = new JSONObject(jsonString);
		} catch (FileNotFoundException e) {
			System.err.println("Error - config file not found");
		} catch (JSONException e) {
			System.err.println("Error reading config file");
			e.printStackTrace();
		}
		if (config != null) {
			try {
				musicPath = config.getString("musicPath");
				System.out.println("Read musicPath as " + musicPath);
			} catch (JSONException e) {
				System.err
						.println("Error - musicPath missing from config file");
				e.printStackTrace();
				return false;
			}
			return true;
		} else {
			// Prompt for music path
			return false;
		}
	}

	public void buildLibrary() {
		if (musicPath != null) {
			library = new Library();

			library.buildFromFilepath(new File(musicPath));
		}
	}

	public void startUI() {
		ui = new ServerUI(this);
	}

	public void setFilepathFromPrompt(String path) {
		musicPath = path;
		config = new JSONObject();
		try {
			config.put("musicPath", musicPath);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		buildLibrary();
		startUI();
	}

	public Library getLibrary() {
		return library;
	}

	public void saveConfig() {
		String jsonString = config.toString();
		try {
			FileWriter fstream = new FileWriter(configPath);
			BufferedWriter out = new BufferedWriter(fstream);
			out.write(jsonString);
			out.close();
		} catch (Exception e) {
			System.err.println("Error writing config");
			e.printStackTrace();
		}

	}

	public synchronized void addSongToPlaylist(Song song) {
		playlist.add(song);
		ui.addSongToPlaylist(song);
	}

	public synchronized void removeSongFromPlaylistAt(int index) {
		playlist.remove(index);
		ui.removeSongFromPlaylist(index);
	}

	public ServerUI getUI() {
		return ui;
	}

	public void setPlaying(boolean in) {
		isPlaying = in;
	}

	public boolean isPlaying() {
		return isPlaying;
	}

	public synchronized Song getNextSong() {
		if (playlist.size() == 0 || playingIndex >= playlist.size()) {
			return null;
		} else {
			return playlist.get(playingIndex++);
		}
	}

	public void startPlaying() {
		player = new BBMediaPlayer(this);
		player.start();
	}

	public void stopPlaying() {
		player.stopMedia();
	}

	public static void main(String[] argv) {
		@SuppressWarnings("unused")
		ServerController main = new ServerController();
	}
}
