/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package listenopolys;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.stage.Stage;

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
        
        menu.setStyle("-fx-background-color: #FF00FF;");
        media.setStyle("-fx-background-color: #FF0000;");
        player.setStyle("-fx-background-color: #0000FF;");
        
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
