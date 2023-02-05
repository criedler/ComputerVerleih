package htl.steyr.schoolclasses.controller;

import htl.steyr.schoolclasses.model.Brand;
import htl.steyr.schoolclasses.repo.BrandRepository;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class BrandController {
    @FXML
    private ListView<Brand> brandView;
    @FXML
    private TextField brandnameField;

    @Autowired
    private BrandRepository brandRepo;

    private Brand brandSelected=null;

    public void initialize(){
        brandView.getItems().setAll(brandRepo.findAll());
    }

    public void saveClicked(ActionEvent actionEvent) {
        if (brandSelected==null){
            brandSelected=new Brand();
        }else {
            brandSelected.setName(brandnameField.getText());
        }
        try{
            brandRepo.save(brandSelected);
        }catch (RuntimeException e){
            //errorLabel.setVisible(true);
        }
        initialize();
    }

    public void createClicked(ActionEvent actionEvent) {
        brandSelected=null;
        brandnameField.clear();
    }

    public void deleteClicked(ActionEvent actionEvent) {
        if (brandSelected!=null){
            brandRepo.delete(brandSelected);
            brandView.getItems().setAll(brandRepo.findAll());
        }
        initialize();
    }

    public void brandViewClicked(MouseEvent mouseEvent) {
        brandSelected= brandView.getSelectionModel().getSelectedItem();
        if (brandSelected!=null){
            brandnameField.setText(brandSelected.getName());
        }
    }
}
