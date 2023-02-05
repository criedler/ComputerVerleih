package htl.steyr.schoolclasses.controller;

import htl.steyr.schoolclasses.model.Brand;
import javafx.event.ActionEvent;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class Controller extends AbstractController {
    public void manageClasses(ActionEvent actionEvent) {
    }

    public void manageStudents(ActionEvent actionEvent) {

    }

    public void manageCustomers(ActionEvent actionEvent) {
        try {
            CustomerController c = loadFxmlFile("customers.fxml", "Manage Customers", null, CustomerController.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void manageBrands(ActionEvent actionEvent) {
        try {
            BrandController c = loadFxmlFile("brands.fxml", "Manage Brands", null, BrandController.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void manageDevices(ActionEvent actionEvent) {
        try {
            DeviceController c = loadFxmlFile("devices.fxml", "Manage Devices", null, DeviceController.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
