package htl.steyr.computerRent.controller;

import htl.steyr.computerRent.model.Brand;
import htl.steyr.computerRent.model.Customer;
import htl.steyr.computerRent.model.Device;
import htl.steyr.computerRent.model.Rental;
import htl.steyr.computerRent.repo.BrandRepository;
import htl.steyr.computerRent.repo.CustomerRepository;
import htl.steyr.computerRent.repo.DeviceRepository;
import htl.steyr.computerRent.repo.RentalRepository;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

@Component
public class RentController extends AbstractController implements SetRepositoryInterface {
    @FXML
    private AnchorPane mainPane;
    @FXML
    private ListView<Device> deviceView;
    @FXML
    private DatePicker startDate;
    @FXML
    private DatePicker endDate;
    @FXML
    private ChoiceBox<Brand> brandChoiceBox;
    @FXML
    private Button rentBtn;

    @Autowired
    private DeviceRepository deviceRepo;
    @Autowired
    private RentalRepository rentalRepo;
    @Autowired
    private CustomerRepository customerRepo;
    @Autowired
    private BrandRepository brandRepo;

    @FXML
    private ListView<Customer> selectCustomerView;

    private Customer customerSelected;

    private Device deviceSelected;


    public void initialize() {
        brandChoiceBox.getItems().setAll(brandRepo.findAll());

    }

    public void deviceViewClicked(MouseEvent mouseEvent) {
        if (mouseEvent.getClickCount() == 2) {
            deviceSelected = deviceView.getSelectionModel().getSelectedItem();
            if (deviceSelected!=null) {
                try {
                    RentController c = loadFxmlFile("selectCustomer.fxml", "Select Customer", mainPane.getScene().getWindow(), RentController.class);
                    selectCustomerView.getItems().setAll(customerRepo.findAll());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }

    public void startDateSelected(ActionEvent actionEvent) {
        if (startDate.getValue()!=null) {
            endDate.setDisable(false);
            deviceView.getItems().clear();
            endDate.setValue(null);
            endDate.setDayCellFactory(datePicker -> new DateCell() {
                public void updateItem(LocalDate date, boolean empty) {
                    super.updateItem(date, empty);
                    setDisable(empty || date.compareTo(startDate.getValue()) < 0);
                }
            });
        }
    }

    public void endDateSelected(ActionEvent actionEvent) {
        if (endDate.getValue() != null) {
            loadDeviceView();
        }
    }

    private void loadDeviceView() {
        System.out.println(startDate.getValue());
        System.out.println(endDate.getValue());
        deviceView.getItems().setAll(deviceRepo.findAvaiableDevices(startDate.getValue(), endDate.getValue()));
    }

    public void filterByBrand(ActionEvent actionEvent) {
        Brand selected = brandChoiceBox.getSelectionModel().getSelectedItem();
            deviceView.getItems().setAll(deviceRepo.filterByBrand(selected.getName(),startDate.getValue(), endDate.getValue()));

    }

    public void rentNowClicked(ActionEvent actionEvent) {
        LocalDate startPeriod = startDate.getValue();
        LocalDate endPeriod = endDate.getValue();
        Rental rental = new Rental(startPeriod, endPeriod, customerSelected, deviceSelected);
        rentalRepo.save(rental);
        deviceView.getItems().clear();


        Stage currentStage= (Stage) rentBtn.getScene().getWindow();
        currentStage.close();

    }

    public void selectCustomerViewClicked(MouseEvent mouseEvent) {
        customerSelected = selectCustomerView.getSelectionModel().getSelectedItem();
        if (customerSelected != null){
            rentBtn.setDisable(false);
        }
    }

    public void clearFilter(ActionEvent actionEvent) {
        brandChoiceBox.getSelectionModel().select(null);
        loadDeviceView();
    }

    @Override
    public <T> void setRepository(List<T> repository) {
        for (Object item: repository) {
            if (item instanceof CustomerRepository) {
                customerRepo= (CustomerRepository) item;
            } else if ( item instanceof BrandRepository){
                brandRepo= (BrandRepository) item;
            } else if ( item instanceof DeviceRepository){
                deviceRepo = (DeviceRepository) item;
            } else {
                rentalRepo = (RentalRepository) item;
            }
        }
    }

    public void backClicked(ActionEvent actionEvent) {
        loadMainMenu("scene.fxml",mainPane.getScene().getWindow());
    }
}
