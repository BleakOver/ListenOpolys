/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package listenopolys;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import listenopolys.controllers.FXMLController;

/**
 *
 * @author husoeur
 */
public class ListenOpolys extends Application {
    
    @Override
    public void start(Stage primaryStage) throws Exception{
        FXMLLoader loader = new FXMLLoader();
        Parent root = loader.load(getClass().getResource("views/FXMLView.fxml").openStream());
        
        Scene scene = new Scene(root);
        
        primaryStage.setMinHeight(200);
        primaryStage.setMinWidth(350);
        primaryStage.setScene(scene);
        primaryStage.show();

        FXMLController controller = loader.getController();

        primaryStage.setOnCloseRequest(windowEvent -> {
            controller.close();
            Platform.exit();
            System.exit(0);
        });
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
