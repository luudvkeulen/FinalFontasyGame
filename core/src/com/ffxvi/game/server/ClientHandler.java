package com.ffxvi.game.server;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
public class ClientHandler implements Runnable {
	BufferedReader reader;
	Socket sock;
	PrintWriter client;

	public ClientHandler(Socket clientSocket, PrintWriter user) {
		client = user;
		try {
			sock = clientSocket;
			InputStreamReader isReader = new InputStreamReader(sock.getInputStream());
			reader = new BufferedReader(isReader);
		} catch (Exception ex) {}
	}

	@Override
	public void run() {
		String message, connect = "Connect", disconnect = "Disconnect", chat = "Chat";
		String[] data;

		try {
			while ((message = reader.readLine()) != null) {
				data = message.split(":");

				for (String token : data) {
				}

				if (data[2].equals(connect)) {
				} else if (data[2].equals(disconnect)) {
				} else if (data[2].equals(chat)) {
				} else {
				}
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
}
