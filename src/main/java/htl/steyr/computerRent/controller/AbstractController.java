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

public abstract class AbstractController implements PublisherInterface<Boolean> {

    private ArrayList<SubscriberInterface<Boolean>> list = new ArrayList<>();

    public <T> T loadFxmlFile(String path, String title, Window owner, Class<T> classOfController) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(AbstractController.class.getResource(path));
        fxmlLoader.setControllerFactory(JavaFxApplication.getSpringContext()::getBean);
        Parent scene = fxmlLoader.load();

        T controller = fxmlLoader.getController();

        Stage stage = new Stage();
        stage.setScene(new Scene(scene));
        stage.setTitle(title);

        if (owner != null) {
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.initOwner(owner);
        }

        stage.show();

        return controller;
    }

    public  <T> void loadIntoWindow(String fileName, Window owner, List<T> repository) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource(fileName));
            fxmlLoader.setBuilderFactory(new JavaFXBuilderFactory());
            fxmlLoader.setControllerFactory(JavaFxApplication.getSpringContext()::getBean);
            Parent root = fxmlLoader.load();

            SetRepositoryInterface controller = fxmlLoader.getController();

            controller.setRepository(repository);

            Scene scene = new Scene(root);
            Stage currentStage = (Stage) owner;
            currentStage.setScene(scene);
            currentStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void loadMainMenu(String fxmlFileName, Window owner) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource(fxmlFileName));
            fxmlLoader.setBuilderFactory(new JavaFXBuilderFactory());
            fxmlLoader.setControllerFactory(JavaFxApplication.getSpringContext()::getBean);

            Parent root = fxmlLoader.load();

            Scene scene = new Scene(root);
            Stage currentStage = (Stage) owner;
            currentStage.setScene(scene);
            currentStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



    @Override
    public void addSubscriber(SubscriberInterface<Boolean> sub) {
        this.list.add(sub);
    }

    @Override
    public void removeSubscriber(SubscriberInterface<Boolean> sub) {
        this.list.remove(sub);
    }

    @Override
    public void notifyAllSubscriber(Boolean what) {
        for (SubscriberInterface<Boolean> sub : list) {
            sub.notify(what);
        }
    }
}
