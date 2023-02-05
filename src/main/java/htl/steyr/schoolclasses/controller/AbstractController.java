package htl.steyr.schoolclasses.controller;

import htl.steyr.schoolclasses.JavaFxApplication;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.Window;

import java.io.IOException;
import java.util.ArrayList;

public class AbstractController implements PublisherInterface<Boolean> {

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
