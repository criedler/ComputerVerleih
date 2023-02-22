package htl.steyr.computerRent.controller;

import htl.steyr.computerRent.model.Brand;
import htl.steyr.computerRent.model.Device;
import htl.steyr.computerRent.repo.BrandRepository;
import htl.steyr.computerRent.repo.DeviceRepository;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;


@Component
public class RentController extends AbstractController {
    @FXML
    private TextField modelNameField;
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

    @Autowired
    private DeviceRepository deviceRepo;

    @Autowired
    private BrandRepository brandRepo;


    private Device deviceSelected;



    public void initialize(){
        deviceView.getItems().clear();
        brandChoiceBox.getItems().setAll(brandRepo.findAll());
    }

    @FXML
    void startDateSelected(ActionEvent actionEvent) {
        if (startDate.getValue() != null) {
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

    @FXML
    void endDateSelected(ActionEvent actionEvent) {
        if (endDate.getValue() != null) {
            loadDeviceView();

        }
    }

    @FXML
    void filter(ActionEvent actionEvent) {
        loadDeviceView();
    }

    @FXML
    void deviceViewClicked(MouseEvent mouseEvent) {
        if (mouseEvent.getClickCount() == 2) {
            deviceSelected = deviceView.getSelectionModel().getSelectedItem();
            if (deviceSelected != null) {
                loadCustomerFxml();
            }
        }
    }

    @FXML
    void backClicked(ActionEvent actionEvent) {
        loadFxmlFile("scene.fxml","Menu", mainPane.getScene().getWindow());
    }


    private void loadDeviceView() {
        deviceView.getItems().setAll(
                deviceRepo.findAvaiableDevices(
                        startDate.getValue(),
                        endDate.getValue(),
                        brandChoiceBox.getValue(),
                        modelNameField.getText()
                )
        );
    }

    private void loadCustomerFxml(){
        RentSelectCustomerController controller = loadFxmlFile("selectCustomer.fxml","select Customer", mainPane.getScene().getWindow());
        controller.setData(startDate.getValue(),endDate.getValue(),deviceSelected);
     }

}
