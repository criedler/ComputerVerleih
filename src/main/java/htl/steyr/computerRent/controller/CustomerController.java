package htl.steyr.computerRent.controller;

import htl.steyr.computerRent.model.Customer;
import htl.steyr.computerRent.repo.CustomerRepository;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CustomerController extends AbstractController implements SetRepositoryInterface {
    @FXML
    private AnchorPane mainPane;
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
        customerSelected.setFirstName(firstnameField.getText());
        customerSelected.setLastName(lastnameField.getText());
        customerSelected.setZipcode(zipcodeField.getText());
        customerSelected.setVillage(villageField.getText());
        customerSelected.setStreet(streetField.getText());
        customerSelected.setStreetNumber(streetnumberField.getText());
        customerSelected.setEmail(emailField.getText());

        try {
            customerRepo.save(customerSelected);
            initialize();
        } catch (RuntimeException e) {
            errorLabel.setVisible(true);
        }

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
            firstnameField.setText(customerSelected.getFirstName());
            lastnameField.setText(customerSelected.getLastName());
            zipcodeField.setText(customerSelected.getZipcode());
            villageField.setText(customerSelected.getVillage());
            streetField.setText(customerSelected.getStreet());
            streetnumberField.setText(String.valueOf(customerSelected.getStreetNumber()));
            emailField.setText(customerSelected.getEmail());
        }
    }

    @Override
    public <T> void setRepository(List<T> repository) {
        for (Object item: repository) {
            if (item instanceof CustomerRepository) {
                customerRepo= (CustomerRepository) item;
            }
        }
    }

    public void backClicked(ActionEvent actionEvent) {
        loadMainMenu("scene.fxml", customerView.getScene().getWindow());
    }
}
