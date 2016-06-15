/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server;

import java.io.IOException;
import java.net.Inet4Address;
import java.net.InetSocketAddress;
import java.net.UnknownHostException;
import java.rmi.RemoteException;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import server.chat.ChatServer;

/**
 *
 * @author Robin
 */
public class FinalFontasyServer {

	private static ServerSubscriber serverSubscriber;
	private static Server server;
	private static ChatServer chatServer;
	/**
	 * @param args the command line arguments
	 * @throws java.rmi.RemoteException
	 * @throws java.net.UnknownHostException
	 */
	public static void main(String[] args) throws RemoteException, UnknownHostException {
		// Clear the console window
		System.out.println("\033[H\033[2J");
		// Write a welcome message
		System.out.println("\n---Welcome to the Final Fontasy XVI server---");
		
		switch (args.length) {
			case 0:
				startAndSubscribe("", 0);
				System.out.println("0 args");
				break;
			case 1:
				startAndSubscribe(args[0], 0);
				System.out.println("1 args:" + args[0]);
				break;
			case 2:
				startAndSubscribe(args[0], Integer.getInteger(args[1]));
				System.out.println("2 args");
				break;
			default:
				break;
		}
		
		Scanner sc = new Scanner(System.in);
		String line = "";
		Scanner lineSc = new Scanner(line);
		String input = "";
		String name = "";
		System.out.println("");
		while (name == "") {
			System.out.print("Please enter a name for the server: ");
			line = sc.nextLine();
			lineSc = new Scanner(line);
			try {
				input = lineSc.next();
				name = input;
			} catch (NoSuchElementException nsee) {
				System.out.println("Invalid name");
			}
		}
		serverSubscriber.renameServer(name);
		System.out.println(String.format("\nServer '%1$s' is now online. Type 'help' for a list of commands", name));

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
					serverSubscriber.removeServer(Inet4Address.getLocalHost().getHostAddress() + ":" + 1338);
					chatServer.stopChat();
					System.out.println("---Shutting down the Final Fontasy XVI server---");
					break;
				// Display the port that the server is listening on
				case "port":
					System.out.println(String.format("Current port: %1$s", server.getListeningPort()));
					break;
				// Display the currently connected clients in the form of IP Addresses
				case "players":
					System.out.println(String.format(""
							+ "--------------------------------------------------\n"
							+ "Current players: [%1$s/%2$s]", server.getPlayerCount(), server.getPlayerLimit()));
					for (String s : server.getPlayerInfo()) {
						System.out.println(s);
					}
					System.out.println(""
							+ "--------------------------------------------------");
					break;
				// Display the amount of spectators that are connected
				case "spectators":
					System.out.println(String.format(""
							+ "-------------------------------\n"
							+ "Current spectators: [%1$s/%2$s]\n"
							+ "-------------------------------", server.getSpectatorCount(), server.getSpectatorLimit()));
					break;
				case "name":
					try {
						name = lineSc.next();
					} catch (NoSuchElementException nsee) {
						System.out.println(String.format("This server is known as: %1$s", name));
						break;
					}
					serverSubscriber.renameServer(name);
					System.out.println("renamed to: " + name);
					break;
				// Display all available commands
				case "help":
					System.out.println(""
							+ "---------------------------------------------------------------------------\n"
							+ "help\t\t\tShows all available commands.\n"
							+ "stop\t\t\tStops the server.\n"
							+ "port\t\t\tShows the port that the server is listening on.\n"
							+ "players\t\t\tShows the addresses of all the connected players.\n"
							+ "spectators\t\tShows the amount of spectators that are connected\n"
							+ "name [name]\t\tSets the name of the server.\n"
							+ "---------------------------------------------------------------------------");
					break;
			}
			lineSc.close();
		}
		sc.close();
	}
	
	private static void startAndSubscribe(String address, int port) throws RemoteException {
		if(address.equals("") && port == 0) {
			server = new Server(1338);
			try {
				serverSubscriber = new ServerSubscriber(server);
			} catch (IOException ex) {
				System.out.println(ex.getMessage());
			}
			startChatServer(port);
		} else if (port == 0 && !address.equals("")) {
			server = new Server(1338);
			try {
				serverSubscriber = new ServerSubscriber(server, address);
			} catch (IOException ex) {
				System.out.println(ex.getMessage());
			}
			startChatServer(port);
		} else {
			server = new Server(port);
			try {
				serverSubscriber = new ServerSubscriber(server, address, port);
			} catch (IOException ex) {
				System.out.println(ex.getMessage());
			}
			startChatServer(port);
		}
	}
        
	private static void startChatServer(int port) {
            ChatServer chat = new ChatServer(FinalFontasyServer.server);
            Thread t = new Thread(chat);
            t.start();
        }
        
//	private static void startChatServer(int port) {
//		chatListener = null;
//		try {
//			//Start the chatserver
//			if(port == 0) {
//				chatListener = new ChatListener(server, 1338);
//			} else {
//				chatListener = new ChatListener(server, port);
//			}
//			new Thread(chatListener).start();
//		} catch (IOException ex) {
//			Logger.getLogger(FinalFontasyServer.class.getName()).log(Level.SEVERE, null, ex);
//		}
//	}
}
