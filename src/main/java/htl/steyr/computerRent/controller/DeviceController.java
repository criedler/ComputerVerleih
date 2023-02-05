package htl.steyr.computerRent.controller;

import htl.steyr.computerRent.model.Brand;
import htl.steyr.computerRent.model.Device;
import htl.steyr.computerRent.repo.BrandRepository;
import htl.steyr.computerRent.repo.DeviceRepository;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DeviceController {

    public Label errorLabel;
    @FXML
    private ListView<Device> deviceView;

    @FXML
    private TextField modelnameField;

    @FXML
    private TextField priceField;

    @FXML
    private ChoiceBox<Brand> brandChoiceBox;

    private Device deviceSelected = null;

    @Autowired
    private DeviceRepository deviceRepo;

    @Autowired
    BrandRepository brandRepo;

    public void initialize() {
        errorLabel.setVisible(false);

        deviceView.getItems().setAll(deviceRepo.findAll());
        brandChoiceBox.getItems().setAll(brandRepo.findAll());
    }

    public void saveClicked(ActionEvent actionEvent) {
        errorLabel.setVisible(false);

        if (deviceSelected == null) {
            deviceSelected = new Device();
        }
        deviceSelected.setModelName(modelnameField.getText());
        deviceSelected.setPrice(Float.parseFloat(priceField.getText()));
        deviceSelected.setBrand(brandChoiceBox.getSelectionModel().getSelectedItem());

        try {
            deviceRepo.save(deviceSelected);
        } catch (RuntimeException e) {
            errorLabel.setVisible(true);
        }

        initialize();
    }

    public void createClicked() {
        deviceSelected = null;
        modelnameField.clear();
        priceField.clear();
        brandChoiceBox.setValue(null);
    }

    public void deleteClicked(ActionEvent actionEvent) {
        if (deviceSelected != null) {
            deviceRepo.delete(deviceSelected);
            deviceView.getItems().setAll(deviceRepo.findAll());
        }
        createClicked();
        initialize();
    }

    public void deviceViewClicked(MouseEvent mouseEvent) {
        deviceSelected = deviceView.getSelectionModel().getSelectedItem();
        if (deviceSelected != null) {
            modelnameField.setText(deviceSelected.getModelName());
            priceField.setText(String.valueOf(deviceSelected.getPrice()));
            brandChoiceBox.getSelectionModel().select(deviceSelected.getBrand());
        }
    }
}
