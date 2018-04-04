package sample;

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
        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        primaryStage.setOnCloseRequest((we) -> Platform.exit());


        primaryStage.setTitle("JavaFX PDF Viewer Demo");
        primaryStage.setScene(new Scene(root, 1025,600));
        primaryStage.sizeToScene();
        primaryStage.centerOnScreen();
        primaryStage.show();


    }

     /*
    rivate void createViewer() {
        try {
            SwingUtilities.invokeAndWait(() -> {
                // create the viewer ri components.
                swingController = new SwingController();
                swingController.setIsEmbeddedComponent(true);

                PropertiesManager properties = new PropertiesManager(System.getProperties(), swingController.getMessageBundle());
                properties.setFloat(PropertiesManager.PROPERTY_DEFAULT_ZOOM_LEVEL, 1.25f);
                properties.setBoolean(PropertiesManager.PROPERTY_SHOW_UTILITY_OPEN, false);
                properties.setBoolean(PropertiesManager.PROPERTY_SHOW_UTILITY_SAVE, false);
                properties.setBoolean(PropertiesManager.PROPERTY_SHOW_UTILITY_PRINT, false);
                // hide the status bar
                properties.setBoolean(PropertiesManager.PROPERTY_SHOW_STATUSBAR, false);
                // hide a few toolbars, just to show how the prefered size of the viewer changes.
                properties.setBoolean(PropertiesManager.PROPERTY_SHOW_TOOLBAR_FIT, false);
                properties.setBoolean(PropertiesManager.PROPERTY_SHOW_TOOLBAR_ROTATE, false);
                properties.setBoolean(PropertiesManager.PROPERTY_SHOW_TOOLBAR_TOOL, false);
                properties.setBoolean(PropertiesManager.PROPERTY_SHOW_TOOLBAR_FORMS, false);

                swingController.getDocumentViewController().setAnnotationCallback(
                        new MyAnnotationCallback(swingController.getDocumentViewController()));

                SwingViewBuilder factory = new SwingViewBuilder(swingController, properties);

                viewerPanel = factory.buildViewerPanel();
                viewerPanel.revalidate();


            });

            SwingNode node = new SwingNode();
            node.setContent(viewerPanel);

        } catch (InterruptedException | InvocationTargetException e) {
            e.printStackTrace();
        }
    }
     */
}
