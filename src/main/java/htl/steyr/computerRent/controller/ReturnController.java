package htl.steyr.computerRent.controller;

import htl.steyr.computerRent.model.Customer;
import htl.steyr.computerRent.model.Device;
import htl.steyr.computerRent.repo.CustomerRepository;
import htl.steyr.computerRent.repo.DeviceRepository;
import javafx.beans.binding.BooleanBinding;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class ReturnController extends AbstractController {
    public ListView<Customer> customerView;
    public ListView<Device> rentedDevicesView;
    public AnchorPane mainPane;
    public TextField chargeCycleField;
    public double priceForPeriod;
    public Button returnBtn;
    public static final double chargeCycle = 0.05;
    public Label totalPriceLbl;

    private Customer customerSelected;

    private Device deviceSelected;

    @Autowired
    CustomerRepository customerRepo;

    @Autowired
    DeviceRepository deviceRepo;

    public void initialize() {
        customerView.getItems().setAll(customerRepo.findAll());
    }


    public void customerViewClicked(MouseEvent mouseEvent) {
        customerSelected = customerView.getSelectionModel().getSelectedItem();
        rentedDevicesView.getItems().setAll(deviceRepo.findOpenRentals(customerSelected.getCustomerID()));
    }

    public void rentedDevicesViewClicked(MouseEvent mouseEvent) {
        deviceSelected = rentedDevicesView.getSelectionModel().getSelectedItem();
        loadDialog();
        setPriceForPeriod();
        disableBtnOnNullInput();
        onlyAllowNumericInput();
    }

    private void setPriceForPeriod() {
        priceForPeriod = deviceRepo.getTotalPrice(deviceSelected.getDeviceID());
    }

    private void loadDialog() {
        try {
            ReturnController c = loadFxmlFile("returnDialog.fxml", "Last Steps", mainPane.getScene().getWindow(), ReturnController.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void onlyAllowNumericInput() {
        chargeCycleField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")) {
                chargeCycleField.setText(newValue.replaceAll("[^\\d]", ""));
            } else {
                if (newValue.equals("")) {
                    totalPriceLbl.setText("");
                } else {
                    updateTotalPrice(Integer.parseInt(newValue));
                }
            }
        });
    }

    private void updateTotalPrice(int newValue) {
        double totalPrice = priceForPeriod + newValue * chargeCycle;
        totalPriceLbl.setText(String.valueOf(totalPrice));
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

    public void cancelClicked(ActionEvent actionEvent) {

    }

    public void returnClicked(ActionEvent actionEvent) {

    }
}
