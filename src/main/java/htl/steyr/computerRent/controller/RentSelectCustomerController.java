package htl.steyr.computerRent.controller;

import htl.steyr.computerRent.model.Customer;
import htl.steyr.computerRent.model.Device;
import htl.steyr.computerRent.model.Rental;
import htl.steyr.computerRent.repo.CustomerRepository;
import htl.steyr.computerRent.repo.RentalRepository;
import javafx.beans.binding.BooleanBinding;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class RentSelectCustomerController extends AbstractController{
    @FXML
    private Label OverviewLbl;

    @FXML
    private AnchorPane mainPane;
    @FXML
    private Button finishBtn;

    @FXML
    private Button rentBtn;

    @FXML
    private ListView<Customer> selectCustomerView;

    @FXML
    private Button undoBtn;

    private Customer customerSelected;

    @Autowired
    private RentalRepository rentalRepo;

    @Autowired
    private CustomerRepository customerRepo;

    private LocalDate startDate;
    private LocalDate endDate;
    private Device device;


    public void initialize(){
        selectCustomerView.getItems().setAll(customerRepo.findAll());
            }


    @FXML
    void backClicked(ActionEvent event) {
        loadFxmlFile("rent.fxml", "Rental", mainPane.getScene().getWindow());

    }

    @FXML
    void rentNowClicked(ActionEvent event) {
        Rental rental = new Rental(startDate, endDate, customerSelected, device);
        rentalRepo.save(rental);
        rentBtn.setDisable(true);
        undoBtn.setDisable(false);
    }

    @FXML
    void selectCustomerViewClicked(MouseEvent event) {
        customerSelected = selectCustomerView.getSelectionModel().getSelectedItem();
        if (customerSelected != null) {
            rentBtn.setDisable(false);
            undoBtn.setDisable(true);
            disableBtnOnNullInput();

        }
    }

    @FXML
    void undoClicked(ActionEvent event) {
        rentalRepo.deleteById(
                rentalRepo.findLastInsert()
        );
        rentBtn.setDisable(false);
        undoBtn.setDisable(true);
    }

    @FXML
    void finishbtnClicked(ActionEvent event) {
        loadDialog("finishedRent.fxml", "finished", mainPane.getScene().getWindow());
        OverviewLbl.setText("Rental entry made for Device "+device + " on the customer " + customerSelected
                + " from " + startDate + " till "+ endDate );

    }


    private void disableBtnOnNullInput() {
        BooleanBinding bb = new BooleanBinding() {
            {
                super.bind(rentBtn.disabledProperty(), undoBtn.disabledProperty());
            }

            @Override
            protected boolean computeValue() {
                return (!rentBtn.disabledProperty().getValue() && undoBtn.disabledProperty().getValue());
            }
        };
        finishBtn.disableProperty().bind(bb);
    }

    public void setData(LocalDate startDate, LocalDate endDate, Device deviceSelected) {
        this.startDate=startDate;
        this.endDate=endDate;
        this.device=deviceSelected;
    }
}
