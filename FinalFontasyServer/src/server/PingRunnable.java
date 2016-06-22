/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server;

import java.io.IOException;
import static java.lang.Thread.sleep;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Observable;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Robin
 */
public class PingRunnable extends Observable implements Runnable {
	
	private InetSocketAddress pingTarget;
	
	public InetSocketAddress getTarget() {
		return pingTarget;
	}

	public PingRunnable(InetSocketAddress pingTarget) {
		this.pingTarget = pingTarget;
	}
	
	@Override
	public void run() {
		boolean reachable = false;
		try {
//			String targetIP = pingTarget.getAddress().toString().substring(1);
//			String command = String.format("ping -n 1 %1$s", targetIP);
//			System.out.println(command);
//			Process p1 = java.lang.Runtime.getRuntime().exec(command);
//			reachable = p1.waitFor(200, TimeUnit.MILLISECONDS);
			reachable = pingTarget.getAddress().isReachable(200);
			sleep(800);
		} catch (IOException ex) {
			Logger.getLogger(PingRunnable.class.getName()).log(Level.SEVERE, null, ex);
		} catch (InterruptedException ex) {
			Logger.getLogger(PingRunnable.class.getName()).log(Level.SEVERE, null, ex);
		}
		setChanged();
		notifyObservers(reachable);
	}
}
