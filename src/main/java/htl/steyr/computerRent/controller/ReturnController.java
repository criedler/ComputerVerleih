package htl.steyr.computerRent.controller;

import htl.steyr.computerRent.model.Customer;
import htl.steyr.computerRent.model.Device;
import htl.steyr.computerRent.model.Rental;
import htl.steyr.computerRent.repo.CustomerRepository;
import htl.steyr.computerRent.repo.DeviceRepository;
import htl.steyr.computerRent.repo.RentalRepository;
import javafx.beans.binding.BooleanBinding;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;

@Component
public class ReturnController extends AbstractController implements SetRepositoryInterface {


    @FXML
    private AnchorPane mainPane;

    private static final int chargeCycle = 5;
    public ListView<Rental> rentalView;
    @FXML
    private ListView<Customer> customerView;
    @FXML
    private ListView<Device> rentedDevicesView;

    @FXML
    private TextField chargeCycleField;

    private int priceForPeriod;

    private int totalPrice;
    @FXML
    private Button returnBtn;
    @FXML
    private Label totalPriceLbl;

    private Customer customerSelected;

    private Device deviceSelected;

    private Rental rentalSelected;

    @Autowired
    CustomerRepository customerRepo;

    @Autowired
    DeviceRepository deviceRepo;

    @Autowired
    RentalRepository rentalRepo;

    public void initialize() {
        customerView.getItems().setAll(customerRepo.findAll());
    }


    public void customerViewClicked(MouseEvent mouseEvent) {
        customerSelected = customerView.getSelectionModel().getSelectedItem();
        if (customerSelected!=null) {
            rentedDevicesView.getItems().setAll(deviceRepo.findOpenRentals(customerSelected.getCustomerId()));
        }
    }

    public void rentedDevicesViewClicked(MouseEvent mouseEvent) {
        deviceSelected = rentedDevicesView.getSelectionModel().getSelectedItem();
        if (deviceSelected!=null) {
            loadSelectRentalDialog();
        }

    }

    private void loadSelectRentalDialog() {
        try {
            loadFxmlFile("selectRental.fxml", "Select Rental ", mainPane.getScene().getWindow(), ReturnController.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        rentalView.getItems().setAll(rentalRepo.findRentalsForDevice(deviceSelected.getDeviceId()));
    }

    public void rentalViewClicked(MouseEvent mouseEvent) {
        rentalSelected = rentalView.getSelectionModel().getSelectedItem();
        if (rentalSelected!=null){
            loadDialog();
        }
    }

    private void setPriceForPeriod() {
        priceForPeriod = deviceRepo.getTotalPrice(rentalSelected.getRentalId());
    }

    private void loadDialog() {
        try {
            ReturnController c = loadFxmlFile("returnDialog.fxml", "Last Steps", mainPane.getScene().getWindow(), ReturnController.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        setPriceForPeriod();
        disableBtnOnNullInput();
        onlyAllowNumericInput();
        checkChargeCycleFieldInput();
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
        totalPriceLbl.setText(totalPrice + " (" + (double) totalPrice /100 + "€)");

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
        rentalRepo.insertFinalPrice(deviceSelected.getDeviceId(), totalPrice);
    }

    @Override
    public <T> void setRepository(List<T> repository) {
        for (Object item : repository) {
            if (item instanceof CustomerRepository) {
                customerRepo = (CustomerRepository) item;
            } else if (item instanceof DeviceRepository) {
                deviceRepo = (DeviceRepository) item;
            } else if (item instanceof RentalRepository){
                rentalRepo = (RentalRepository) item;
            }
        }
    }

    public void backClicked(ActionEvent actionEvent) {
        loadMainMenu("scene.fxml",mainPane.getScene().getWindow());
    }


}
