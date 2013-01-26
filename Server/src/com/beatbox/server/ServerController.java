package com.beatbox.server;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.util.Scanner;

import org.json.JSONException;
import org.json.JSONObject;

import com.beatbox.lib.Library;
import com.beatbox.lib.song.Song;
import com.beatbox.server.ui.ServerUI;

public class ServerController {
	private String configPath = "config.json";
	private Library library;
	private ServerUI ui;
	private String musicPath;
	private JSONObject config;

	// private ArrayList<Song> playlist;

	public ServerController() {

		// playlist = new ArrayList<Song>();

		readConfigFile();

		if (musicPath != null) {
			library = new Library();

			library.buildFromFilepath(new File(musicPath));
		}

		ui = new ServerUI(this);
	}

	private void readConfigFile() {
		String jsonString = null;
		config = null;
		try {
			jsonString = new Scanner(new File(configPath)).useDelimiter("\\Z")
					.next();
			config = new JSONObject(jsonString);
		} catch (FileNotFoundException e) {
			System.err.println("Error - config file not found");
			e.printStackTrace();
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
			}
		}
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

	public void addSongToPlaylist(Song song) {
		// playlist.add(song);
		ui.addSongToPlaylist(song);
	}

	public void removeSongFromPlaylistAt(int index) {
		// playlist.remove(index);
		ui.removeSongFromPlaylist(index);
	}

	public ServerUI getUI() {
		return ui;
	}

	public static void main(String[] argv) {
		@SuppressWarnings("unused")
		ServerController main = new ServerController();
	}
}
