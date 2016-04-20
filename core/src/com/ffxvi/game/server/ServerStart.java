package com.ffxvi.game.server;

import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class ServerStart implements Runnable {
	ArrayList clientOutputStreams;
	ArrayList<String> users;
	
	@Override
	public void run() {
		clientOutputStreams = new ArrayList();
		users = new ArrayList();

		try {
			ServerSocket serverSock = new ServerSocket(2222);

			while (true) {
				Socket clientSock = serverSock.accept();
				PrintWriter writer = new PrintWriter(clientSock.getOutputStream());
				clientOutputStreams.add(writer);

				Thread listener = new Thread(new ClientHandler(clientSock, writer));
				listener.start();
			}
		} catch (Exception ex) {}
	}
}
