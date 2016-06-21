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
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Robin
 */
public class PingRunnable extends Observable implements Runnable {
	
	private InetSocketAddress pingTarget;
	private long startTime;
	
	public InetSocketAddress getTarget() {
		return pingTarget;
	}

	public PingRunnable(InetSocketAddress pingTarget) {
		this.pingTarget = pingTarget;
		this.startTime = System.nanoTime();
	}
	
	@Override
	public void run() {
		try {
			pingTarget.getAddress().isReachable(200);
		} catch (IOException ex) {
			Logger.getLogger(PingRunnable.class.getName()).log(Level.SEVERE, null, ex);
		}
		int latency = (int)(System.nanoTime() - startTime);
		int sleepFor = 1000000000 - latency;
		try {
			sleep(sleepFor/1000000);
		} catch (InterruptedException ex) {
			Logger.getLogger(PingRunnable.class.getName()).log(Level.SEVERE, null, ex);
		}
		setChanged();
		notifyObservers(latency);
	}
}
