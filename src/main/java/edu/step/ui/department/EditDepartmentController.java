package edu.step.ui.department;

import edu.step.db.Company;
import edu.step.db.Department;
import edu.step.db.Employee;
import edu.step.db.hibernate.CompanyDao;
import edu.step.db.hibernate.DepartmentDao;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

public class EditDepartmentController implements Initializable {

    public TextField nameTextField;


    public TextField descriptionTextField;


    public ChoiceBox<Company> companyChoiceBox;

    private Department editedDepartment;

    private Department result;

    private ObservableList<Company> companies;

    public Department getResult() {
        return editedDepartment;
    }

    public EditDepartmentController() {
//        DepartmentDB db= new DepartmentDB();
//        List<Department> selectAll = db.selectAll();
        CompanyDao dao = new CompanyDao();
        List<Company> selectAll = dao.findAll();
        companies = FXCollections.observableArrayList(selectAll);
    }

    public void onSaveDepartment(ActionEvent event) {
        if(editedDepartment != null && (!nameTextField.getText().isEmpty() || !descriptionTextField.getText().isEmpty())) {
            editedDepartment.setName(nameTextField.getText());
            editedDepartment.setDescription(descriptionTextField.getText());
            editedDepartment.setCompany(companyChoiceBox.getValue());
        }
        Node button = (Node)event.getSource();
        Stage stage = (Stage) button.getScene().getWindow();
        stage.close();
    }

    public void setEditedDepartment(Department department) {
        editedDepartment = department;
        if (editedDepartment != null) {
            nameTextField.setText(editedDepartment.getName());
            descriptionTextField.setText(editedDepartment.getDescription());
            companyChoiceBox.setValue(editedDepartment.getCompany());
//        for(Department d: departments){
//            if(d.getId() == editedEmployee.getDepartment().getId()){
//                departmentChoiceBox.setValue(d);
//            }
//        }
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        companyChoiceBox.setItems(companies);
    }
}
