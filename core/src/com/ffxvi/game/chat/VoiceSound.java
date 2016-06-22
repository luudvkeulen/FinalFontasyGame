/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ffxvi.game.chat;

import java.io.Serializable;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;

/**
 *
 * @author Acer
 */
public class VoiceSound implements Serializable {

	/**
	 * The data which has the user's voicechat in it.
	 */
	private byte[] data;

	/**
	 * The constructor of this class. Sets the value's of the fields.
	 *
	 * @param data The data which has the user's voicechat in it.
	 */
	public VoiceSound(byte[] data) {
		this.data = data;
	}

	/**
	 * Returns the data.
	 * @return data.
	 */
	public byte[] getData() {
		return this.data;
	}

	/**
	 * Play the input via the main speaker.
	 */
	public void playInput() {
		AudioFormat format = new AudioFormat(8000.0f, 16, 1, true, true);
		SourceDataLine speakers;
		System.out.println("GONNA TRY TO PLAY INPUT");
		try {
			DataLine.Info dataLineInfo = new DataLine.Info(SourceDataLine.class, format);
			speakers = (SourceDataLine) AudioSystem.getLine(dataLineInfo);
			speakers.open(format);
			speakers.start();
			System.out.println("data length of received packet: " + data.length);
			speakers.write(data, 0, data.length);
			speakers.drain();
			speakers.close();

			System.out.println("Should have played input");

		} catch (LineUnavailableException e) {
			e.printStackTrace();
		}
	}
}
