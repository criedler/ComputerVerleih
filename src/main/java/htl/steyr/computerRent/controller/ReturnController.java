package htl.steyr.computerRent.controller;

import htl.steyr.computerRent.model.Customer;
import htl.steyr.computerRent.model.Device;
import htl.steyr.computerRent.repo.BrandRepository;
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
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;

@Component
public class ReturnController extends AbstractController implements RepositoryAwareController {
    private static final double chargeCycle = 0.05;
    @FXML
    private ListView<Customer> customerView;
    @FXML
    private ListView<Device> rentedDevicesView;
    @FXML
    private AnchorPane mainPane;
    @FXML
    private TextField chargeCycleField;
    @FXML
    private double priceForPeriod;
    @FXML
    private double totalPrice;
    @FXML
    private Button returnBtn;
    @FXML
    private Label totalPriceLbl;

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


    public void customerViewClicked(MouseEvent mouseEvent) {
        customerSelected = customerView.getSelectionModel().getSelectedItem();
        rentedDevicesView.getItems().setAll(deviceRepo.findOpenRentals(customerSelected.getCustomerId()));
    }

    public void rentedDevicesViewClicked(MouseEvent mouseEvent) {
        deviceSelected = rentedDevicesView.getSelectionModel().getSelectedItem();
        loadDialog();
        setPriceForPeriod();
        disableBtnOnNullInput();
        onlyAllowNumericInput();
        checkChargeCycleFieldInput();
    }

    private void setPriceForPeriod() {
        priceForPeriod = deviceRepo.getTotalPrice(deviceSelected.getDeviceId());
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
        rentalRepo.insertFinalPrice(deviceSelected.getDeviceId(), totalPrice);
    }

    @Override
    public <T> void setRepository(List<T> repository) {
        for (Object item : repository) {
            if (item instanceof CustomerRepository) {
                customerRepo = (CustomerRepository) item;
            } else if (item instanceof DeviceRepository) {
                deviceRepo = (DeviceRepository) item;
            } else {
                rentalRepo = (RentalRepository) item;
            }
        }
    }
}
