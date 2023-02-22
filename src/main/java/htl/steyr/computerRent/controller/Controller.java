package htl.steyr.computerRent.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;
import org.springframework.stereotype.Component;

@Component
public class Controller extends AbstractController {
    @FXML
    private AnchorPane mainPane;

    public void manageCustomers(ActionEvent event) {
        loadFxmlFile("customers.fxml", "Customers", mainPane.getScene().getWindow());
    }

    public void manageBrands(ActionEvent actionEvent) {
        loadFxmlFile("brands.fxml", "Brands", mainPane.getScene().getWindow());
    }

    public void manageDevices(ActionEvent actionEvent) {
        loadFxmlFile("devices.fxml", "Devices", mainPane.getScene().getWindow());
    }

    public void rentClicked(ActionEvent actionEvent) {
        loadFxmlFile("rent.fxml", "Rental", mainPane.getScene().getWindow());
    }

    public void returnClicked(ActionEvent actionEvent) {
        loadFxmlFile("return.fxml", "Return", mainPane.getScene().getWindow());
    }
}
