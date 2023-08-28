package edu.step.ui;

import edu.step.db.Department;
import edu.step.db.DepartmentDB;
import edu.step.db.Employee;
import edu.step.db.hibernate.DepartmentDao;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.net.URL;
import java.time.LocalDate;
import java.util.List;
import java.util.ResourceBundle;

// TODO: de schimbat validarea datelor (de colorat campurile obligatorii in rosu)
public class AddDialogController implements Initializable {

    public TextField nameTextField;

    public TextField surnameTextField;

    public TextField emailTextField;

    public DatePicker birthdateDatePicker;

    public ChoiceBox<Department> departmentChoiceBox;

    private Employee result;

    public Employee getResult() {
        return result;
    }

    public void onSave(ActionEvent event) {
        if(!nameTextField.getText().isEmpty() || !surnameTextField.getText().isEmpty()) {
            LocalDate birth = birthdateDatePicker.getValue();
            // TODO: de adaugat birthdate aici
            result = new Employee(nameTextField.getText(), surnameTextField.getText(), emailTextField.getText(),
                    departmentChoiceBox.getValue(), birth);
        }
        Node button = (Node)event.getSource();
        Stage stage = (Stage) button.getScene().getWindow();
        stage.close();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
//        DepartmentDB db= new DepartmentDB();
//        List<Department> selectAll = db.selectAll();
        DepartmentDao dao = new DepartmentDao();
        List<Department> selectAll = dao.findAll();
        ObservableList<Department> departments = FXCollections.observableArrayList(selectAll);
        departmentChoiceBox.setItems(departments);
//        for(Department d: departments) {
//            if(d.getId() == employee.getDepartment().getId()) {
//                departmentChoiceBox.setValue(d);
//                break;
//            }
//        }
    }
}
