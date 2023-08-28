package edu.step.ui.company;

import edu.step.db.Company;
import edu.step.db.Department;
import edu.step.db.hibernate.CompanyDao;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class AddCompanytController implements Initializable {


    public TextField nameTextField;

    public TextField descriptionTextField;


    public Button Save;

    private Company resultCompany;

    public Company getResultCompany() {
        return resultCompany;
    }
    public void onSaveCompany(ActionEvent event)  {
        if(!nameTextField.getText().isEmpty() || !descriptionTextField.getText().isEmpty()) {
            resultCompany = new Company(nameTextField.getText(), descriptionTextField.getText());
        }
        Node button = (Node)event.getSource();
        Stage stage = (Stage) button.getScene().getWindow();
        stage.close();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
     }
}
