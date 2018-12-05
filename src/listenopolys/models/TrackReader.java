/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package listenopolys.models;

import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

/**
 *
 * @author enmora
 */
public class TrackReader {
    
    private AudioInputStream audioStream;
    long currentFrame;
    String status;
    Clip clip;
    
    public TrackReader(Track track){
        currentFrame=0;
        status="pause";
        try {
            audioStream=AudioSystem.getAudioInputStream(new File(track.getFilePath()).getAbsoluteFile());
        } catch (UnsupportedAudioFileException ex) {
            Logger.getLogger(TrackReader.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
            
        try {
            clip=AudioSystem.getClip();
            clip.open(audioStream);
        } catch (LineUnavailableException ex) {
            Logger.getLogger(TrackReader.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(TrackReader.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    public void pause(){
        if(status=="pause")
            return;
        currentFrame=clip.getMicrosecondPosition();
        clip.stop();
        status="pause";
        System.out.println("pause");
    }
           
    public void play(){
        if(status=="play")
            return;
        clip.setMicrosecondPosition(currentFrame);
        clip.start();
        status="play";
        System.out.println("play");
    }
    
    public void stop(){
        if(status=="pause" && currentFrame==0)
            return;
        clip.stop();
        currentFrame=0;
        status="pause";
        System.out.println("stop");
    }
    
    public String getStatus(){
        return status;
    }
    
}
