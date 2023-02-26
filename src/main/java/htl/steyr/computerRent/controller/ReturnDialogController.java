package htl.steyr.computerRent.controller;

import htl.steyr.computerRent.model.Customer;
import htl.steyr.computerRent.model.Device;
import htl.steyr.computerRent.model.Rental;
import htl.steyr.computerRent.repo.DeviceRepository;
import htl.steyr.computerRent.repo.RentalRepository;
import javafx.beans.binding.BooleanBinding;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ReturnDialogController extends AbstractController {
    @FXML
    AnchorPane mainPane;
    @FXML
    private Button returnBtn;
    @FXML
    private Label totalPriceLbl;
    @FXML
    private Label OverviewLbl;
    @FXML
    private TextField chargeCycleField;

    @Autowired
    DeviceRepository deviceRepo;

    @Autowired
    RentalRepository rentalRepo;

    private static final int chargeCycle = 5;

    private int priceForPeriod;

    private int totalPrice;

    private Rental rentalSelected;

    private Device deviceSelected;


    @FXML
    void cancelClicked(ActionEvent actionEvent) {
        loadFxmlFile("selectRental.fxml", "select Rental", mainPane.getScene().getWindow());
    }

    @FXML
    void returnClicked(ActionEvent actionEvent) {
        rentalRepo.insertFinalPrice(deviceSelected.getDeviceId(), totalPrice);
        loadDialog("finishedReturn.fxml", "finished Return", mainPane.getScene().getWindow());
        OverviewLbl.setText("Returned device " + deviceSelected +
                " with the rent period " + rentalSelected.getDateOfIssue() + " - " + rentalSelected.getReturnDate() +
                " for the Customer " + rentalSelected.getCustomer());
    }

    private void setPriceForPeriod() {
        priceForPeriod = deviceRepo.getTotalPrice(rentalSelected.getRentalId());
    }

    private void onlyAllowNumericInput() {
        chargeCycleField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")) {
                chargeCycleField.setText(newValue.replaceAll("[^\\d]", ""));
            }
        });
    }

    private void checkChargeCycleFieldInput() {
        chargeCycleField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.equals("")) {
                totalPriceLbl.setText("");
            } else {
                updateTotalPrice(Integer.parseInt(newValue));
            }
        });
    }

    private void updateTotalPrice(int newValue) {
        totalPrice = priceForPeriod + newValue * chargeCycle;
        totalPriceLbl.setText(totalPrice + " (" + (double) totalPrice / 100 + "€)");

    }

    private void disableBtnOnNullInput() {
        BooleanBinding bb = new BooleanBinding() {
            {
                super.bind(chargeCycleField.textProperty());
            }

            @Override
            protected boolean computeValue() {
                return (chargeCycleField.getText().isEmpty());
            }
        };
        returnBtn.disableProperty().bind(bb);
    }

    public void setData(Rental rentalSelected) {
        this.rentalSelected = rentalSelected;
        this.deviceSelected = rentalSelected.getDevice();
        setUp();
    }

    private void setUp() {
        setPriceForPeriod();
        disableBtnOnNullInput();
        onlyAllowNumericInput();
        checkChargeCycleFieldInput();
    }
}
