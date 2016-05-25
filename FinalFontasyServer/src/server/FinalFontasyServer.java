/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import server.chat.ChatListener;

/**
 *
 * @author Robin
 */
public class FinalFontasyServer {

	/**
	 * @param args the command line arguments
	 */
	public static void main(String[] args) {
		// Clear the console window
		System.out.println("\033[H\033[2J");
		// Write a welcome message
		System.out.println("\n---Welcome to the Final Fontasy XVI server---");

		try {
			new ServerSubscriber();
		} catch (IOException ex) {
			Logger.getLogger(FinalFontasyServer.class.getName()).log(Level.SEVERE, null, ex);
		}

		Scanner sc = new Scanner(System.in);
		Scanner lineSc;
		String line = "";
		String input = "";

		// Start the server
		Server server = new Server(1338);

		ChatListener chatListener = null;
		try {
			//Start the chatserver
			chatListener = new ChatListener(server, 1338);
			new Thread(chatListener).start();
		} catch (IOException ex) {
			Logger.getLogger(FinalFontasyServer.class.getName()).log(Level.SEVERE, null, ex);
		}

		// A while loop with a boolean to be able to keep receiving commands from the terminal
		boolean stop = false;
		while (!stop) {
			// Read the next line written in the console and put it in the String line
			line = sc.nextLine();
			// Create a scanner for the current line, this makes it easier to only
			// read one part of the line at a time
			lineSc = new Scanner(line);
			input = lineSc.next();
			// Do something depending on the entered command
			switch (input.toLowerCase()) {
				// Stop the server
				case "stop":
					stop = true;
					server.stop();
					chatListener.stopListener();
					System.out.println("---Shutting down the Final Fontasy XVI server---");
					break;
				// Display the port that the server is listening on
				case "port":
					System.out.println(String.format("Current port: %1$s", server.getListeningPort()));
					break;
				// Display the currently connected clients in the form of IP Addresses
				case "players":
					System.out.println("Current players:");
					for (InetSocketAddress player : server.getPlayerAddresses()) {
						if (player != null) {
							System.out.println(player.toString());
						}
					}
					System.out.println("----------------");
					break;
				// Display all available commands
				case "help":
					System.out.println("----------------\n"
							+ "help - Shows all available commands.\n"
							+ "stop - Stops the server.\n"
							+ "port - Shows the port that the server is listening on.\n"
							+ "players - Shows the addresses of all the connected players.\n"
							+ "----------------");
					break;
			}
			lineSc.close();
		}
		sc.close();
	}
}
