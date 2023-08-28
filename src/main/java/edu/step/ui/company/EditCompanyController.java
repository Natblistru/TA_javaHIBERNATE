package edu.step.ui.company;

import edu.step.db.Company;
import edu.step.db.Department;
import edu.step.db.hibernate.CompanyDao;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class EditCompanyController implements Initializable {

    public TextField nameTextField;


    public TextField descriptionTextField;


    private Company editedCompany;

    private Company result;

    public Company getResult() {
        return editedCompany;
    }

    public EditCompanyController() {
//        DepartmentDB db= new DepartmentDB();
//        List<Department> selectAll = db.selectAll();
    }

    public void onSaveCompany(ActionEvent event) {
        if(editedCompany != null && (!nameTextField.getText().isEmpty() || !descriptionTextField.getText().isEmpty())) {
            editedCompany.setName(nameTextField.getText());
            editedCompany.setDescription(descriptionTextField.getText());
        }
        Node button = (Node)event.getSource();
        Stage stage = (Stage) button.getScene().getWindow();
        stage.close();
    }

    public void setEditedCompany(Company company) {
        editedCompany = company;
        if (editedCompany != null) {
            nameTextField.setText(editedCompany.getName());
            descriptionTextField.setText(editedCompany.getDescription());
//        for(Department d: departments){
//            if(d.getId() == editedEmployee.getDepartment().getId()){
//                departmentChoiceBox.setValue(d);
//            }
//        }
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}
