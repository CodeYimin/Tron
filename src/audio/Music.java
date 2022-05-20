package audio;

import java.io.File;
// possible exceptions
import java.io.IOException;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

public class Music {
    private Clip music;

    public Music(String musicFilePath) {
        try {
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(new File(musicFilePath));
            this.music = AudioSystem.getClip();
            this.music.open(audioStream);
        } catch (IOException ex) {
            System.out.println("File not found!");
        } catch (UnsupportedAudioFileException ex) {
            System.out.println("Unsupported file!");
        } catch (LineUnavailableException ex) {
            System.out.println("Audio feed already in use!");
        }
    }

    public void start() {
        if (this.music == null) {
            return;
        }

        this.music.start();
    }

    public void loop() {
        if (this.music == null) {
            return;
        }

        this.music.loop(Clip.LOOP_CONTINUOUSLY);
    }

    public void loop(int count) {
        if (this.music == null) {
            return;
        }

        this.music.loop(count);
    }
}