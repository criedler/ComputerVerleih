package htl.steyr.computerRent.controller;

import htl.steyr.computerRent.model.Customer;
import htl.steyr.computerRent.model.Device;
import htl.steyr.computerRent.repo.CustomerRepository;
import htl.steyr.computerRent.repo.DeviceRepository;
import htl.steyr.computerRent.repo.RentalRepository;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ReturnController extends AbstractController {
    @FXML
    private AnchorPane mainPane;

    @FXML
    private ListView<Customer> customerView;

    @FXML
    private ListView<Device> rentedDevicesView;

    private Customer customerSelected;

    private Device deviceSelected;



    @Autowired
    CustomerRepository customerRepo;

    @Autowired
    DeviceRepository deviceRepo;

    @Autowired
    RentalRepository rentalRepo;

    public void initialize() {
        customerView.getItems().setAll(customerRepo.findAll());
    }


    @FXML
    void customerViewClicked(MouseEvent mouseEvent) {
        customerSelected = customerView.getSelectionModel().getSelectedItem();
        if (customerSelected != null) {
            rentedDevicesView.getItems().setAll(deviceRepo.findOpenRentals(customerSelected.getCustomerId()));
        }
    }

    @FXML
    void rentedDevicesViewClicked(MouseEvent mouseEvent) {
        deviceSelected = rentedDevicesView.getSelectionModel().getSelectedItem();
        if (deviceSelected != null) {
            loadSelectRental();
        }
    }

    @FXML
    void backClicked(ActionEvent actionEvent) {
        loadFxmlFile("scene.fxml", "Menu", mainPane.getScene().getWindow());
    }

    @FXML
    void loadSelectRental() {
        ReturnSelectRentalController controller = loadFxmlFile("selectRental.fxml","select Rental", mainPane.getScene().getWindow());
        controller.setData(customerSelected, deviceSelected);
    }
}
