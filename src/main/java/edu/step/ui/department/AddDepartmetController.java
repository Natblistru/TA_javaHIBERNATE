package edu.step.ui.department;

import edu.step.db.Company;
import edu.step.db.Department;
import edu.step.db.Employee;
import edu.step.db.hibernate.CompanyDao;
import edu.step.db.hibernate.DepartmentDao;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.net.URL;
import java.time.LocalDate;
import java.util.List;
import java.util.ResourceBundle;

public class AddDepartmetController implements Initializable {


    public TextField nameTextField;

    public TextField descriptionTextField;

    public ChoiceBox<Company> companyChoiceBox;

    public Button Save;

    private Department resultDepartment;

    public Department getResultDepartment() {
        return resultDepartment;
    }
    public void onSaveDepartment(ActionEvent event)  {
        if(!nameTextField.getText().isEmpty() || !descriptionTextField.getText().isEmpty()) {
            resultDepartment = new Department(nameTextField.getText(), descriptionTextField.getText(),
                    companyChoiceBox.getValue());
        }
        Node button = (Node)event.getSource();
        Stage stage = (Stage) button.getScene().getWindow();
        stage.close();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        CompanyDao dao = new CompanyDao();
        List<Company> selectAll = dao.findAll();
        ObservableList<Company> companies = FXCollections.observableArrayList(selectAll);
        companyChoiceBox.setItems(companies);
    }
}
