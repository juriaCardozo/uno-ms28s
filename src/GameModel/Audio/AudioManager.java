package GameModel.Audio;

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
        try {
            if (backgroundMusicClip != null && isPlaying) {
                backgroundMusicClip.stop();
            }

            File audioFile = new File(filePath);
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(audioFile);
            AudioFormat format = audioStream.getFormat();
            DataLine.Info info = new DataLine.Info(Clip.class, format);

            if (!AudioSystem.isLineSupported(info)) {
                System.out.println("Line not supported.");
                return;
            }

            backgroundMusicClip = (Clip) AudioSystem.getLine(info);
            backgroundMusicClip.open(audioStream);
            backgroundMusicClip.loop(Clip.LOOP_CONTINUOUSLY);
            volumeControl = (FloatControl) backgroundMusicClip.getControl(FloatControl.Type.MASTER_GAIN);
            isPlaying = true;

        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            e.printStackTrace();
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
        try {
            File audioFile = new File(filePath);
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(audioFile);
            AudioFormat format = audioStream.getFormat();
            DataLine.Info info = new DataLine.Info(Clip.class, format);

            if (!AudioSystem.isLineSupported(info)) {
                System.out.println("Line not supported.");
                return;
            }

            Clip soundEffectClip = (Clip) AudioSystem.getLine(info);
            soundEffectClip.open(audioStream);
            soundEffectClip.start();
            
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            e.printStackTrace();
        }
    }
}
