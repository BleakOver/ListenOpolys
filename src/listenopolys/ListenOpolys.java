/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package listenopolys;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.stage.Stage;
import listenopolys.models.Time;
import listenopolys.models.Track;
import listenopolys.models.TrackReader;

/**
 *
 * @author husoeur
 */
public class ListenOpolys extends Application {
    
    @Override
    public void start(Stage primaryStage) {
        GridPane structure = new GridPane();
        Scene scene = new Scene(structure, 300, 250);
        GridPane menu = new GridPane();
        GridPane media = new GridPane();
        GridPane player = new GridPane();
        
        structure.add(menu, 0, 0);
        structure.add(media, 1, 0);
        structure.add(player, 0, 1, 2, 1);
        
        menu.setStyle("-fx-background-color: ##7EB2A0;");
        media.setStyle("-fx-background-color: #2F3136;");
        player.setStyle("-fx-background-color: #6EBDC2;");
        
        ColumnConstraints col1 = new ColumnConstraints();
        col1.setPercentWidth(35);
        ColumnConstraints col2 = new ColumnConstraints();
        col2.setPercentWidth(65);
        structure.getColumnConstraints().addAll(col1, col2);
        
        RowConstraints row1 = new RowConstraints();
        row1.setPercentHeight(80);
        RowConstraints row2 = new RowConstraints();
        row2.setPercentHeight(20);
        structure.getRowConstraints().addAll(row1, row2);
        TrackReader reader = new TrackReader(new Track("titre", "/home/etud/enmora/Téléchargements/13632.wav" , "chopin", 1992, new Time(0, 0, 0)));
        Button playButton = new Button("play");
        playButton.setOnAction(new EventHandler<ActionEvent>(){
            public void handle(ActionEvent e){
                reader.play();
            }
        });
        
        Button pauseButton = new Button("pause");
        playButton.setOnAction(new EventHandler<ActionEvent>(){
            public void handle(ActionEvent e){
                reader.pause();
            }
        });
        
        Button stopButton = new Button("stop");
        playButton.setOnAction(new EventHandler<ActionEvent>(){
            public void handle(ActionEvent e){
                reader.stop();
            }
        });
        
        player.add(playButton, 0, 1);
        player.add(pauseButton, 1,0);
        player.add(stopButton, 0, 0);
        
        primaryStage.setTitle("Hello World!");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
