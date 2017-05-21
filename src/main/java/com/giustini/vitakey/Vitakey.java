package com.giustini.vitakey;

import javafx.application.Application;
import javafx.stage.Stage;

/**
 * @author Andrés Giustini
 */

public class Vitakey extends Application {

    @Override
    public void start(Stage primaryStage) {
        MainController controller = new MainController();
        controller.init(primaryStage);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
