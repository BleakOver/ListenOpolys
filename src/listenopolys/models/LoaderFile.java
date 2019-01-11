package listenopolys.models;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;

public class LoaderFile implements Loader {

    @Override
    public PlaylistService load() {
        PlaylistService playlistService = null;
        try {
            FileInputStream fileInputStream = new FileInputStream("savefile");
            ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
            playlistService = (PlaylistService)objectInputStream.readObject();
            playlistService.setNotSerializable();
        } catch (FileNotFoundException e) {
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        finally {
            return playlistService;
        }
    }
}
