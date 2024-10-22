import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.net.URL;

public class SoundManager {
    Clip clip;
    URL[] soundURL = new URL[10];

    public SoundManager() {
        soundURL[0] = getClass().getResource("Sound/click.wav");
        soundURL[1] = getClass().getResource("Sound/hitHurt.wav");
        soundURL[2] = getClass().getResource("Sound/hitLand.wav");
        soundURL[3] = getClass().getResource("Sound/GAME.wav");
        soundURL[4] = getClass().getResource("Sound/hitEnemy.wav");
    }
    public void setFile(int i) {
        try {
            AudioInputStream ais = AudioSystem.getAudioInputStream(soundURL[i]);
            clip = AudioSystem.getClip();
            clip.open(ais);

        }catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void play() {
        clip.start();
    }
    public void loop() {
        clip.loop(Clip.LOOP_CONTINUOUSLY);
    }
    public void stop() {
        clip.stop();
    }
}
