package htl.steyr.computerRent.controller;

import htl.steyr.computerRent.model.Brand;
import htl.steyr.computerRent.model.Device;
import htl.steyr.computerRent.repo.DeviceRepository;
import javafx.event.ActionEvent;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DateCell;
import javafx.scene.control.DatePicker;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.LocalDate;

@Component
public class RentController extends AbstractController {
    public AnchorPane mainPane;
    public ListView<Device> deviceView;
    public DatePicker startDate;
    public DatePicker endDate;
    public ChoiceBox<Brand> brandChoiceBox;

    @Autowired
    private DeviceRepository deviceRepo;

    public void initialize(){
    }

    public void deviceViewClicked(MouseEvent mouseEvent) {
        if(mouseEvent.getClickCount() == 2){
            try {
                SelectCustomerController c = loadFxmlFile("selectCustomer.fxml", "Select Customer", mainPane.getScene().getWindow(), SelectCustomerController.class);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void startDateSelected(ActionEvent actionEvent) {
        endDate.setDayCellFactory(datePicker -> new DateCell(){
            public void updateItem(LocalDate date, boolean empty){
                super.updateItem(date,empty);
                setDisable(empty || date.compareTo(startDate.getValue())<0);
            }
        });
        endDate.setDisable(false);
    }

    public void endDateSelected(ActionEvent actionEvent) {
        loadDeviceView();
    }

    private void loadDeviceView() {
        deviceView.getItems().setAll(deviceRepo.findAll());
    }
}
