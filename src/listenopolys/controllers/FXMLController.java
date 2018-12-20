/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package listenopolys.controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.Node;


import listenopolys.views.*;


/**
 *
 * @author enmora
 */
public class FXMLController implements Initializable {

    @FXML
    private void handleButtonAction(ActionEvent event) throws Exception {


        Parent root = FXMLLoader.load(getClass().getResource("views/FXMLAddPlaylistView.fxml"));
        Scene playlist_add_view_scene = new Scene(root);
        Stage playlistAdd_stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        playlistAdd_stage.setScene(playlist_add_view_scene);
        playlistAdd_stage.show();
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }



}
