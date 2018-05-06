package com.antonleagre.tencharts;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {


    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("ui/main.fxml"));
        primaryStage.setOnCloseRequest((we) -> Platform.exit());

        primaryStage.setTitle("10Charts ALPHA");
        primaryStage.setScene(new Scene(root, 1025,600));
        primaryStage.sizeToScene();
        primaryStage.centerOnScreen();
        primaryStage.show();

        // TODO: 5/6/2018 all FAA airports update per airac so we can do that too maybe no? 

    }
}
