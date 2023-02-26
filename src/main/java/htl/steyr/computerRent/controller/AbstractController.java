package htl.steyr.computerRent.controller;

import htl.steyr.computerRent.JavaFxApplication;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.Window;
import javafx.stage.WindowEvent;

import java.io.IOException;
import java.lang.management.PlatformLoggingMXBean;
import java.util.ArrayList;
import java.util.List;

public abstract class AbstractController {
    public <T> T loadFxmlFile(String filename, String title, Window owner) {
        FXMLLoader fxmlLoader = new FXMLLoader(AbstractController.class.getResource(filename));
        fxmlLoader.setControllerFactory(JavaFxApplication.getSpringContext()::getBean);
        T controller = null;
        try {
            Parent root = fxmlLoader.load();
            controller = fxmlLoader.getController();
            Stage currentStage = (Stage) owner;
            currentStage.setScene(new Scene(root));
            currentStage.setTitle(title);
            currentStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return controller;
    }

    public void loadDialog(String filename, String title, Window owner) {
        FXMLLoader fxmlLoader = new FXMLLoader(AbstractController.class.getResource(filename));
        fxmlLoader.setControllerFactory(JavaFxApplication.getSpringContext()::getBean);
        try {
            Parent root = fxmlLoader.load();
            Stage currentStage = new Stage();
            currentStage.setScene(new Scene(root));
            currentStage.setTitle(title);


            if (owner != null) {
                currentStage.initModality(Modality.APPLICATION_MODAL);
                currentStage.initOwner(owner);
            }

            currentStage.show();

            currentStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
                @Override
                public void handle(WindowEvent windowEvent) {
                    loadFxmlFile("scene.fxml", "Main Menu", owner);
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
