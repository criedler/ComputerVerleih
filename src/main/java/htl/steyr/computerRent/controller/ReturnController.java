package htl.steyr.computerRent.controller;

import htl.steyr.computerRent.model.Customer;
import htl.steyr.computerRent.model.Device;
import htl.steyr.computerRent.repo.CustomerRepository;
import javafx.event.ActionEvent;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class ReturnController extends AbstractController{
    public ListView<Customer> customerView;
    public ListView<Device> rentedDevicesView;
    public AnchorPane mainPane;
    public TextField chargeCycleField;
    public Label totalPrice;

    private Customer customerSelected;

    private Device deviceSelected;

    @Autowired
    CustomerRepository customerRepo;

    public void initialize(){
        customerView.getItems().setAll(customerRepo.findAll());
    }

    public void customerViewClicked(MouseEvent mouseEvent) {
        customerSelected =customerView.getSelectionModel().getSelectedItem();
        rentedDevicesView.getItems().setAll(customerRepo.findOpenRentals(customerSelected.getCustomerID()));
    }

    public void rentedDevicesViewClicked(MouseEvent mouseEvent) {
        deviceSelected = rentedDevicesView.getSelectionModel().getSelectedItem();
        try {
            RentController c = loadFxmlFile("returnDialog.fxml", "Last Steps", mainPane.getScene().getWindow(), RentController.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void cancelClicked(ActionEvent actionEvent) {

    }

    public void returnClicked(ActionEvent actionEvent) {

    }

    public void calculatePriceClicked(ActionEvent actionEvent) {

    }
}
