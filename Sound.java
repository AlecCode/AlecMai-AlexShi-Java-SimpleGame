package com;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;

/**
 * Created by Alec on 2018-02-18.
 */
class Sound { //each sound/song is conveted into a 'sound' object
    private Clip myClip;

    public Sound(String songName) {
        try {
            // Open an audio input stream
            File file = new File(songName);
            AudioInputStream ais = AudioSystem.getAudioInputStream(file);
            this.myClip = AudioSystem.getClip();
            this.myClip.open(ais);
        }
        catch(IOException e) {
            e.printStackTrace(); //displays the specific lines that the error has occured within the program
            System.out.println("Do you have the right file?");
        }
        catch(UnsupportedAudioFileException e) {
            e.printStackTrace();
            System.out.println("Unsupported audio file");
        }
        catch(LineUnavailableException e) {
            e.printStackTrace();
            System.out.println("Line is unavailable");
        }
    }

    public void loop() {
        this.myClip.loop(Clip.LOOP_CONTINUOUSLY); //plays continuously
    }

    public void play() {
        this.myClip.setFramePosition(0); //rewinding the sound/song
        this.myClip.start();
    }
}