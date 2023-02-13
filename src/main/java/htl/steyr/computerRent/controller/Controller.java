package htl.steyr.computerRent.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class Controller extends AbstractController {

    @FXML
    private AnchorPane mainPane;

    public void manageCustomers(ActionEvent actionEvent) {
        try {
            CustomerController c = loadFxmlFile("customers.fxml", "Manage Customers", mainPane.getScene().getWindow(), CustomerController.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void manageBrands(ActionEvent actionEvent) {
        try {
            BrandController c = loadFxmlFile("brands.fxml", "Manage Brands", mainPane.getScene().getWindow(), BrandController.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void manageDevices(ActionEvent actionEvent) {
        try {
            DeviceController c = loadFxmlFile("devices.fxml", "Manage Devices", mainPane.getScene().getWindow(), DeviceController.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void rentClicked(ActionEvent actionEvent) {
        try {
            RentController c = loadFxmlFile("rent.fxml", "Rent Device", mainPane.getScene().getWindow(), RentController.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void returnClicked(ActionEvent actionEvent) {
        try {
            ReturnController c = loadFxmlFile("return.fxml", "Return Device", mainPane.getScene().getWindow(), ReturnController.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
