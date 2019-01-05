package listenopolys.models;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

public class SaverFile implements Saver {

    @Override
    public void save(PlaylistService playlistService) {
        try {
            FileOutputStream fileOutputStream = new FileOutputStream("savefile");
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
            objectOutputStream.writeObject(playlistService.getSerializable());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
