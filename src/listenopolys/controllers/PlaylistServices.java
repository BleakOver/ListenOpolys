/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package listenopolys.controllers;
import java.util.ArrayList;
import java.util.List;
import listenopolys.models.Playlist;

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
        playlistList.add(p);
    }
    
    public void removePlaylist(Playlist p){
        playlistList.remove(p);
    }
}
