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

import java.util.List;

@Component
public class DeviceController extends AbstractController{
    @FXML
    private Label errorLabel;
    @FXML
    private ListView<Device> deviceView;
    @FXML
    private TextField modelnameField;
    @FXML
    private TextField priceField;
    @FXML
    private ChoiceBox<Brand> brandChoiceBox;

    @Autowired
    private DeviceRepository deviceRepo;

    @Autowired
    BrandRepository brandRepo;

    private Device deviceSelected = null;

    public void initialize() {
        errorLabel.setVisible(false);

        deviceView.getItems().setAll(deviceRepo.findAll());
        brandChoiceBox.getItems().setAll(brandRepo.findAll());
    }

    @FXML
    void saveClicked(ActionEvent actionEvent) {
        errorLabel.setVisible(false);

        if (deviceSelected == null) {
            deviceSelected = new Device();
        }
        deviceSelected.setModelName(modelnameField.getText());
        deviceSelected.setPrice(Integer.parseInt(priceField.getText()));
        deviceSelected.setBrand(brandChoiceBox.getSelectionModel().getSelectedItem());

        try {
            deviceRepo.save(deviceSelected);
            initialize();
        } catch (RuntimeException e) {
            errorLabel.setVisible(true);
        }

    }

    @FXML
    void createClicked() {
        deviceSelected = null;
        modelnameField.clear();
        priceField.clear();
        brandChoiceBox.setValue(null);
    }

    @FXML
    void deleteClicked(ActionEvent actionEvent) {
        if (deviceSelected != null) {
            deviceRepo.delete(deviceSelected);
            deviceView.getItems().setAll(deviceRepo.findAll());
        }
        createClicked();
        initialize();
    }

    @FXML
    void deviceViewClicked(MouseEvent mouseEvent) {
        deviceSelected = deviceView.getSelectionModel().getSelectedItem();
        if (deviceSelected != null) {
            modelnameField.setText(deviceSelected.getModelName());
            priceField.setText(String.valueOf(deviceSelected.getPrice()));
            brandChoiceBox.getSelectionModel().select(deviceSelected.getBrand());
        }
    }

    @FXML
    void backClicked(ActionEvent actionEvent) {
        loadFxmlFile("scene.fxml","Menu", deviceView.getScene().getWindow());
    }
}
