package htl.steyr.computerRent.controller;

import htl.steyr.computerRent.JavaFxApplication;
import javafx.fxml.FXMLLoader;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.Window;

import java.io.IOException;
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
}
