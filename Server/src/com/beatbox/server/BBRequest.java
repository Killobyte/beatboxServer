package com.beatbox.server;

import org.json.JSONException;
import org.json.JSONObject;

public class BBRequest {

	public static enum Command {
		GETLIBRARY, ADDSONG, INVALID, QUIT
	};

	JSONObject request;
	boolean isErrored;
	Command command;
	String artist;
	String title;

	public BBRequest(String input) {
		try {
			request = new JSONObject(input);
			String inCommand = request.getString("command");
			if (inCommand.equals("GETLIBRARY")) {
				command = Command.GETLIBRARY;
			} else if (inCommand.equals("ADDSONG")) {
				command = Command.ADDSONG;
				artist = request.getString("artist");
				title = request.getString("title");
			} else if (inCommand.equals("QUIT")) {
				command = Command.QUIT;
			} else {
				command = Command.INVALID;
			}
			isErrored = false;
		} catch (JSONException e) {
			isErrored = true;
			System.err.println("Error parsing request");
		}
	}

	public boolean getIsErrored() {
		return isErrored;
	}

	public Command getCommand() {
		return command;
	}

	public String getArtist() {
		return artist;
	}

	public String getTitle() {
		return title;
	}
}
