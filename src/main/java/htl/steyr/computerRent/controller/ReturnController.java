package htl.steyr.computerRent.controller;

import htl.steyr.computerRent.model.Customer;
import htl.steyr.computerRent.model.Device;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import org.springframework.stereotype.Component;

@Component
public class ReturnController extends AbstractController{
    public ListView<Customer> customerView;
    public ListView<Device> rentedDevicesView;

    public void customerViewClicked(MouseEvent mouseEvent) {
    }

    public void rentedDevicesViewClicked(MouseEvent mouseEvent) {
    }
}
