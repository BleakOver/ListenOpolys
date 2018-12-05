/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package listenopolys.controllers;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import listenopolys.models.Playlist;
import listenopolys.models.Track;

/**
 *
 * @author husoeur
 */
public class PlaylistServices {
    private List<Playlist> playlistList;
    
    public PlaylistServices(){
       playlistList=new ArrayList<>();
    }
    
    
    public void addPlaylist(Playlist p){
        if(!playlistList.contains(p)){
            playlistList.add(p);
        }
        
    }
    
    public void removePlaylist(String playlistTitle){
        for (Playlist p : playlistList) {
            if(p.getTitle().equals(playlistTitle)){
                playlistList.remove(p);
                return;
            }
        }
    }
    
    public List<Track> getTracksFromPlaylist(String playlistTitle){
        for (Playlist p : playlistList) {
            if(p.getTitle().equals(playlistTitle)){
                return p.getTracks();
            }
        }
        return null;
    }
    
    public List<Playlist> getPlaylistList(){
        return playlistList;
    }
}
