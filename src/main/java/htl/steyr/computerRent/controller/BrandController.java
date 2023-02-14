package htl.steyr.computerRent.controller;

import htl.steyr.computerRent.model.Brand;
import htl.steyr.computerRent.repo.BrandRepository;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class BrandController extends AbstractController implements SetRepositoryInterface {
    @FXML
    private Label errorLabel;
    @FXML
    private ListView<Brand> brandView;
    @FXML
    private TextField brandnameField;
    @Autowired
    private BrandRepository brandRepo;
    private Brand brandSelected = null;

    public void initialize() {
        errorLabel.setVisible(false);

        brandView.getItems().setAll(brandRepo.findAll());
    }

    public void saveClicked(ActionEvent actionEvent) {
        errorLabel.setVisible(false);

        if (brandSelected == null) {
            brandSelected = new Brand();
        }
        brandSelected.setName(brandnameField.getText());

        try {
            brandRepo.save(brandSelected);
            initialize();
        } catch (RuntimeException e) {
            errorLabel.setVisible(true);
        }
    }

    public void createClicked() {
        brandSelected = null;
        brandnameField.clear();
    }

    public void deleteClicked(ActionEvent actionEvent) {
        if (brandSelected != null) {
            brandRepo.delete(brandSelected);
            brandView.getItems().setAll(brandRepo.findAll());
        }
        createClicked();
        initialize();
    }

    public void brandViewClicked(MouseEvent mouseEvent) {
        brandSelected = brandView.getSelectionModel().getSelectedItem();
        if (brandSelected != null) {
            brandnameField.setText(brandSelected.getName());
        }
    }

    @Override
    public <T> void setRepository(List<T> repository) {
        for (Object item : repository) {
            if (item instanceof BrandRepository) {
                brandRepo = (BrandRepository) item;

            }
        }
    }

    public void backClicked(ActionEvent actionEvent) {
        loadMainMenu("scene.fxml",brandView.getScene().getWindow());
    }
}
