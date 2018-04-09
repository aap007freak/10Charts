package com.antonleagre.tencharts;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {


    public static void main(String[] args) {
//        pdfPath = args[0];
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("ui/main.fxml"));
        primaryStage.setOnCloseRequest((we) -> Platform.exit());


        primaryStage.setTitle("JavaFX PDF Viewer Demo");
        primaryStage.setScene(new Scene(root, 1025,600));
        primaryStage.sizeToScene();
        primaryStage.centerOnScreen();
        primaryStage.show();

    }
}
