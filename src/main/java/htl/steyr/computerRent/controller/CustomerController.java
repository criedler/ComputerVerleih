package htl.steyr.computerRent.controller;

import htl.steyr.computerRent.model.Customer;
import htl.steyr.computerRent.repo.CustomerRepository;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CustomerController extends AbstractController {

    @FXML
    private Label errorLabel;
    @FXML
    private ListView<Customer> customerView;
    @FXML
    private TextField firstnameField;
    @FXML
    private TextField lastnameField;
    @FXML
    private TextField zipcodeField;
    @FXML
    private TextField villageField;
    @FXML
    private TextField streetField;
    @FXML
    private TextField streetnumberField;
    @FXML
    private TextField emailField;
    @Autowired
    private CustomerRepository customerRepo;
    private Customer customerSelected = null;


    public void initialize() {
        errorLabel.setVisible(false);
        customerView.getItems().setAll(customerRepo.findAll());
    }

    public void saveClicked() {
        errorLabel.setVisible(false);
        if (customerSelected == null) {
            customerSelected = new Customer();
        }
        customerSelected.setFirstname(firstnameField.getText());
        customerSelected.setLastname(lastnameField.getText());
        customerSelected.setZipcode(zipcodeField.getText());
        customerSelected.setVillage(villageField.getText());
        customerSelected.setStreet(streetField.getText());
        customerSelected.setStreetnumber(streetnumberField.getText());
        customerSelected.setEmail(emailField.getText());

        try {
            customerRepo.save(customerSelected);
        } catch (RuntimeException e) {
            errorLabel.setVisible(true);
        }
        initialize();
    }

    public void createClicked() {
        customerSelected = null;
        firstnameField.clear();
        lastnameField.clear();
        zipcodeField.clear();
        villageField.clear();
        streetField.clear();
        streetnumberField.clear();
        emailField.clear();
    }

    public void deleteClicked() {
        if (customerSelected != null) {
            customerRepo.delete(customerSelected);
            customerView.getItems().setAll(customerRepo.findAll());
        }
        createClicked();
        initialize();
    }

    public void customerViewClicked() {
        customerSelected = customerView.getSelectionModel().getSelectedItem();

        if (customerSelected != null) {
            firstnameField.setText(customerSelected.getFirstname());
            lastnameField.setText(customerSelected.getLastname());
            zipcodeField.setText(customerSelected.getZipcode());
            villageField.setText(customerSelected.getVillage());
            streetField.setText(customerSelected.getStreet());
            streetnumberField.setText(String.valueOf(customerSelected.getStreetnumber()));
            emailField.setText(customerSelected.getEmail());
        }
    }
}
