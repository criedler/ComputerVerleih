package htl.steyr.schoolclasses.controller;

import htl.steyr.schoolclasses.model.Brand;
import htl.steyr.schoolclasses.model.Device;
import htl.steyr.schoolclasses.repo.DeviceRepository;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DeviceController {

    @FXML
    private ListView<Device> deviceView;

    @FXML
    private TextField modelnameField;

    @FXML
    private TextField priceField;

    @FXML
    private ChoiceBox<Brand> brandChoiceBox;

    private Device deviceSelected=null;

    @Autowired
    private DeviceRepository deviceRepo;

    public void initialize(){
        deviceView.getItems().setAll(deviceRepo.findAll());
    }

    public void saveClicked(ActionEvent actionEvent) {
        if (deviceSelected==null){
            deviceSelected=new Device();
            deviceRepo.save(deviceSelected);
        }else {
            deviceSelected.setModelName(modelnameField.getText());
            deviceSelected.setPrice(Float.parseFloat(priceField.getText()));
            deviceSelected.setBrand(brandChoiceBox.getSelectionModel().getSelectedItem());
            deviceRepo.save(deviceSelected);
        }
        initialize();
    }

    public void createClicked(ActionEvent actionEvent) {
        deviceSelected=null;
        modelnameField.clear();
        priceField.clear();
        brandChoiceBox.setValue(null);
    }

    public void deleteClicked(ActionEvent actionEvent) {
        if (deviceSelected!=null){
            deviceRepo.delete(deviceSelected);
            deviceView.getItems().setAll(deviceRepo.findAll());
        }
        initialize();
    }

    public void deviceViewClicked(MouseEvent mouseEvent) {
        deviceSelected=deviceView.getSelectionModel().getSelectedItem();
        if (deviceSelected!=null){
            modelnameField.setText(deviceSelected.getModelName());
            priceField.setText(String.valueOf(deviceSelected.getPrice()));
            brandChoiceBox.getSelectionModel().select(deviceSelected.getBrand());
        }
    }
}
