package cz.borec.demo.sound;

import java.io.IOException;
import java.net.URL;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

// To play sound using Clip, the process need to be alive.
// Hence, we use a Swing application.
public class SoundPlayer /* extends JFrame */ {

	private static Clip clip;

	static {
		/*
		 * this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		 * this.setTitle("Test Sound Clip"); this.setSize(300, 200);
		 * this.setVisible(true);
		 */
		try {
			// Open an audio input stream.
			URL url = new SoundPlayer().getClass().getClassLoader().getResource("sound/DeskBell.wav");
			AudioInputStream audioIn = AudioSystem.getAudioInputStream(url);
			// Get a sound clip resource.
			clip = AudioSystem.getClip();
			// Open audio clip and load samples from the audio input stream.
			clip.open(audioIn);
			audioIn.close();
		} catch (UnsupportedAudioFileException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (LineUnavailableException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		playSound();
		try {
			Thread.sleep(10000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public static void playSound() {
		Runnable r = new Runnable() {

			@Override
			public void run() {
				clip.stop();
				clip.setFramePosition(0);
				clip.start();
				
			}
		};
		Thread t = new Thread(r);
		
		t.start();
	}


	
	
}
