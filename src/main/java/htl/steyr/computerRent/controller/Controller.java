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
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.Window;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Component
public class Controller extends AbstractController {

    @FXML
    private AnchorPane mainPane;

    private static List<Object> repositories;




    public void initialize() {
        if (repositories == null) {
            repositories = new ArrayList<>();
            repositories.add(JavaFxApplication.getSpringContext().getBean(CustomerRepository.class));
            repositories.add(JavaFxApplication.getSpringContext().getBean(BrandRepository.class));
            repositories.add(JavaFxApplication.getSpringContext().getBean(DeviceRepository.class));
            repositories.add(JavaFxApplication.getSpringContext().getBean(RentalRepository.class));
        }
    }


    public void manageCustomers(ActionEvent event) {
        loadIntoWindow("customers.fxml", mainPane.getScene().getWindow(), repositories);
    }

    public void manageBrands(ActionEvent actionEvent) {
        loadIntoWindow("brands.fxml", mainPane.getScene().getWindow(), repositories);
    }

    public void manageDevices(ActionEvent actionEvent) {
        loadIntoWindow("devices.fxml", mainPane.getScene().getWindow(), repositories);
    }

    public void rentClicked(ActionEvent actionEvent) {
        loadIntoWindow("rent.fxml", mainPane.getScene().getWindow(), repositories);
    }

    public void returnClicked(ActionEvent actionEvent) {
        loadIntoWindow("return.fxml", mainPane.getScene().getWindow(), repositories);
    }


}
