/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package listenopolys.models;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

/**
 *
 * @author husoeur
 */
public class Playlist {
    private String title;
    private Time duration;
    private Map<Integer,Track> playlist;
    
    public Playlist(String title){
        this.title=title;
        this.duration=new Time(0,0,0);
        playlist=new TreeMap();
    }
    
    
    public void addTrack(Track t){
        if(playlist.isEmpty()){
            playlist.put(1, t);
        }
        else{
            playlist.put((playlist.lastKey()+1, t);
        }
    }
    
    public void removeTrack(int id){
        playlist.remove(id);
    }
}
