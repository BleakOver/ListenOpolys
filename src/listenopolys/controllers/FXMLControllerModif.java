package listenopolys.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import listenopolys.models.Playlist;

import java.net.URL;
import java.util.ResourceBundle;

public class FXMLControllerModif implements Initializable {

    @FXML
    TextField textFieldTitle;

    private String initialValue;

    FXMLController controller = null;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    /**
     * @param value initial value of the playlist to update
     */
    public void setInitialValue(String value){
        textFieldTitle.setText(value);
        initialValue = value;
    }

    /**
     * update the playlist title
     */
    public void buttonModifyPlaylistClicked() {
        if(controller!=null && textFieldTitle.getText()!=""){
            controller.updatePlaylistTitle(initialValue, textFieldTitle.getText());
        }
        Stage stage = (Stage) textFieldTitle.getScene().getWindow();
        stage.close();
    }

    /**
     * set the controller to use for adding the playlist
     * @param controller controller to use for adding the playlist
     */
    public void setController(FXMLController controller){
        this.controller = controller;
    }

}
