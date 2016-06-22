package com.ffxvi.game.chat;

import com.ffxvi.game.client.Client;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sound.sampled.*;

public class VoiceChat implements Runnable {

	private Client clientSender;
	public volatile boolean recording;
	public volatile boolean programActive;

	public VoiceChat(Client clientSender) {
		this.clientSender = clientSender;
		this.recording = false;
		this.programActive = true;
	}

	@Override
	public void run() {
		try {
			this.recordInput();
		} catch (Exception ex) {
			Logger.getLogger(VoiceChat.class.getName()).log(Level.SEVERE, null, ex);
		}
	}

	public void recordInput() throws Exception {
		AudioFormat format = new AudioFormat(8000.0f, 16, 1, true, true);
		TargetDataLine line;
		DataLine.Info info = new DataLine.Info(TargetDataLine.class, format);
		try {
			line = (TargetDataLine) AudioSystem.getLine(info);
			line.open(format);
		} catch (LineUnavailableException ex) {
			System.out.println("Mic unavailable");
			return;
		}

		while (programActive) {
			while (recording) {
				byte[] data = new byte[800];
				System.out.println("data length: " + data.length);
				line.start();
				line.read(data, 0, data.length);
				clientSender.sendVoiceSoud(new VoiceSound(data));
				line.close();
			}
		}

	}

	public void sendToServer(VoiceSound data) throws Exception {
		this.clientSender.sendVoiceSoud(data);
	}
}
