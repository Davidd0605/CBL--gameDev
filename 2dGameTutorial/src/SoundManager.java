import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import java.net.URL;

public class SoundManager {
    Clip clip;
    URL[] soundURL = new URL[10];
    FloatControl fc;
    float volume;
    int volumeInd = 100;
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
            fc = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
            checkVolume();
        } catch (Exception e) {
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
    public void checkVolume() {
        if(volumeInd == 0) {
            volume = -80;
        } else {
            // Equation for volume
            // -30 ... 6
            // 1 . . . 100
            //y1 - y2 = m (x1 - x2)
            // 6 + 30 = m * 99
            // m = 36 / 99
            //volume(volumeInd) = 36/99 * volumeInd + const.
            //volume(1) = -30 = 36/99 + const.
            //const. = -30 - 36/99 = -30.36
            volume = ((float) volumeInd) * 36 / 99 - 30.36f;
        }
        if(volumeInd == 100) {
            volume = 6;
        }
        fc.setValue(volume);
    }
}
