package com.beatbox.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ConnectionHandler extends Thread {

	ServerController controller;
	int port;
	boolean runServer;

	public ConnectionHandler(ServerController controller) {
		this.controller = controller;
		port = 55555;
		runServer = true;
	}

	public void run() {
		ServerSocket socket = null;
		try {
			socket = new ServerSocket(port);
		} catch (IOException e) {
			System.err.println("Couldn't start server");
			e.printStackTrace();
		}

		if (socket != null) {
			while (runServer) {
				try {
					Socket connectionSocket = socket.accept();
					RequestHandler handler = new RequestHandler(controller,
							connectionSocket);
					handler.start();
				} catch (IOException e) {
					System.err.println("IOException in client socket");
					e.printStackTrace();
				}

			}
			try {
				socket.close();
			} catch (IOException e) {
				System.err.println("Error closing socket");
				e.printStackTrace();
			}
		}
	}

	public int getPort() {
		return port;
	}
}
