package GameModel.Managers;

import java.io.File;
import java.io.IOException;
import javax.sound.sampled.*;

public class AudioManager {

    private Clip backgroundMusicClip;
    private FloatControl volumeControl;
    private boolean isPlaying;

    public AudioManager() {
        isPlaying = false;
    }

    public void playBackgroundMusic(String filePath) {
        stopCurrentMusic();
        backgroundMusicClip = loadClip(filePath);
        if (backgroundMusicClip != null) {
            configureAndPlayClip(backgroundMusicClip, true);
        }
    }

    private void stopCurrentMusic() {
        if (backgroundMusicClip != null && isPlaying) {
            backgroundMusicClip.stop();
        }
    }

    private Clip loadClip(String filePath) {
        Clip clip = null;
        try {
            File audioFile = new File(filePath);
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(audioFile);
            AudioFormat format = audioStream.getFormat();
            DataLine.Info info = new DataLine.Info(Clip.class, format);

            if (!AudioSystem.isLineSupported(info)) {
                System.out.println("Line not supported for the format: " + format);
                return null;
            }

            clip = (Clip) AudioSystem.getLine(info);
            clip.open(audioStream);
            audioStream.close();
        } catch (UnsupportedAudioFileException e) {
            System.err.println("Unsupported audio file format: " + e.getMessage());
        } catch (IOException e) {
            System.err.println("IO error while loading audio file: " + e.getMessage());
        } catch (LineUnavailableException e) {
            System.err.println("Audio line unavailable: " + e.getMessage());
        }
        return clip;
    }

    private void configureAndPlayClip(Clip clip, boolean loop) {
        if (clip != null) {
            if (loop) {
                clip.loop(Clip.LOOP_CONTINUOUSLY);
            }
            volumeControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
            clip.start();
            isPlaying = true;
        }
    }

    public void stopBackgroundMusic() {
        if (backgroundMusicClip != null && isPlaying) {
            backgroundMusicClip.stop();
            isPlaying = false;
        }
    }

    public boolean isPlaying() {
        return isPlaying;
    }

    public boolean volumeUp() {
        if (volumeControl != null) {
            float currentVolume = volumeControl.getValue();
            float maxVolume = volumeControl.getMaximum();
            if (currentVolume < maxVolume) {
                volumeControl.setValue(currentVolume + 1.0f);
                return true;
            }
        }
        return false;
    }

    public boolean volumeDown() {
        if (volumeControl != null) {
            float currentVolume = volumeControl.getValue();
            float minVolume = volumeControl.getMinimum();
            if (currentVolume > minVolume) {
                volumeControl.setValue(currentVolume - 1.0f);
                return true;
            }
        }
        return false;
    }

    public void playSoundEffect(String filePath) {
        Clip soundEffectClip = loadClip(filePath);
        if (soundEffectClip != null) {
            soundEffectClip.start();
        }
    }
}
