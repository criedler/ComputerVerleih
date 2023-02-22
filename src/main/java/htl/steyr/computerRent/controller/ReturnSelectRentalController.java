package htl.steyr.computerRent.controller;

import htl.steyr.computerRent.JavaFxApplication;
import htl.steyr.computerRent.model.Customer;
import htl.steyr.computerRent.model.Device;
import htl.steyr.computerRent.model.Rental;
import htl.steyr.computerRent.repo.RentalRepository;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;


@Component
public class ReturnSelectRentalController extends AbstractController {
    @FXML
    private ListView<Rental> rentalView;

    @FXML
    private AnchorPane mainPane;

    @Autowired
    RentalRepository rentalRepository;

    private Rental rentalSelected;


    @FXML
    void rentalViewClicked(MouseEvent mouseEvent) {
        rentalSelected = rentalView.getSelectionModel().getSelectedItem();
        if (rentalSelected != null) {
            loadDialog();
        }
    }

    @FXML
    void backClicked(ActionEvent actionEvent) {
        loadFxmlFile("return.fxml", "Return", mainPane.getScene().getWindow());
    }

    public void setData(Customer customerSelected, Device deviceSelected) {
        rentalView.getItems().setAll(rentalRepository.findRentalsForDevice(customerSelected.getCustomerId(), deviceSelected.getDeviceId()));
    }

    private void loadDialog() {
        ReturnDialogController controller = loadFxmlFile("returnDialog.fxml", "Last Steps", mainPane.getScene().getWindow());
        controller.setData(rentalSelected);
    }

}
