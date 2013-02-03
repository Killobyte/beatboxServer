package com.beatbox.server;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class RequestHandler extends Thread {

	ServerController controller;
	Socket socket;

	public RequestHandler(ServerController controller, Socket socket) {
		this.controller = controller;
		this.socket = socket;
	}

	public void run() {
		BufferedReader inFromClient = null;
		DataOutputStream outToClient = null;
		try {
			inFromClient = new BufferedReader(new InputStreamReader(
					socket.getInputStream()));
			outToClient = new DataOutputStream(socket.getOutputStream());
			String input = inFromClient.readLine();
			BBRequest request = new BBRequest(input);
			if (request.getIsErrored()) {
				outToClient.writeBytes("Error - malformed request");
				socket.close();
				return;
			}
			switch (request.getCommand()) {
			case GETLIBRARY:
				outToClient.writeBytes(controller.getLibrary().toJSONObject()
						.toString());
				break;
			case ADDSONG:
				addSong(request);
				break;
			default:
				outToClient.writeBytes("Error - invalid command");
				socket.close();
				return;
			}
		} catch (IOException e) {
			System.err.println("Error talking to client");
			e.printStackTrace();
		}
	}

	private void addSong(BBRequest request) {
		controller.addSongToPlaylist(request.getArtist(), request.getTitle());
	}
}
