package components;

import java.io.File;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

public class DJ {
    
    public void PlayMusic(){
        String bip = "src/res/Platformer2.mp3";
        Media hit = new Media(new File(bip).toURI().toString());
       
        MediaPlayer mediaPlayer = new MediaPlayer(hit);
        
        mediaPlayer.play();
    }
    
}
