package htl.steyr.computerRent.controller;

import htl.steyr.computerRent.JavaFxApplication;
import htl.steyr.computerRent.repo.BrandRepository;
import htl.steyr.computerRent.repo.CustomerRepository;
import htl.steyr.computerRent.repo.DeviceRepository;
import htl.steyr.computerRent.repo.RentalRepository;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import org.springframework.stereotype.Component;

import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Component
public class Controller extends AbstractController {

    @FXML
    private AnchorPane mainPane;

    public void initialize(){
    }


    public void manageCustomers(ActionEvent event) {
        List<Object> repositories = new ArrayList<>();
        repositories.add(JavaFxApplication.getSpringContext().getBean(CustomerRepository.class));

        loadIntoWindow("customers.fxml", repositories);
    }

    public void manageBrands(ActionEvent actionEvent) {
        List<Object> repositories = new ArrayList<>();
        repositories.add(JavaFxApplication.getSpringContext().getBean(BrandRepository.class));

        loadIntoWindow("brands.fxml", repositories);

    }

    public void manageDevices(ActionEvent actionEvent) {
        List<Object> repositories = new ArrayList<>();
        repositories.add(JavaFxApplication.getSpringContext().getBean(DeviceRepository.class));
        repositories.add(JavaFxApplication.getSpringContext().getBean(BrandRepository.class));

        loadIntoWindow("devices.fxml", repositories);
    }


    public void rentClicked(ActionEvent actionEvent) {
        List<Object> repositories = new ArrayList<>();
        repositories.add(JavaFxApplication.getSpringContext().getBean(CustomerRepository.class));
        repositories.add(JavaFxApplication.getSpringContext().getBean(BrandRepository.class));
        repositories.add(JavaFxApplication.getSpringContext().getBean(DeviceRepository.class));
        repositories.add(JavaFxApplication.getSpringContext().getBean(RentalRepository.class));

        loadIntoWindow("rent.fxml", repositories);
    }

    public void returnClicked(ActionEvent actionEvent) {
        List<Object> repositories = new ArrayList<>();
        repositories.add(JavaFxApplication.getSpringContext().getBean(CustomerRepository.class));
        repositories.add(JavaFxApplication.getSpringContext().getBean(DeviceRepository.class));
        repositories.add(JavaFxApplication.getSpringContext().getBean(RentalRepository.class));

        loadIntoWindow("return.fxml", repositories);
    }

    private <T> void loadIntoWindow(String fileName, List<T> repository) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource(fileName));
            fxmlLoader.setBuilderFactory(new JavaFXBuilderFactory());
            fxmlLoader.setControllerFactory(JavaFxApplication.getSpringContext()::getBean);
            Parent root = fxmlLoader.load();

            RepositoryAwareController controller = fxmlLoader.getController();

            controller.setRepository(repository);

            Scene scene = new Scene(root);
            Stage currentStage = (Stage) mainPane.getScene().getWindow();
            currentStage.setScene(scene);
            currentStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
