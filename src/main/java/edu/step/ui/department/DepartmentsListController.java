package edu.step.ui.department;

import edu.step.db.Company;
import edu.step.db.Department;
import edu.step.db.Employee;
import edu.step.db.hibernate.DepartmentDao;
import edu.step.ui.department.EditDepartmentController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

public class DepartmentsListController implements Initializable {

    public TableView<Department> departmentTable;
    public TableColumn<Department, String> nameColumn;

    public TableColumn<Department, String> descriptionColumn;

    public TableColumn<Department, Company> companyColumn;

    private DepartmentDao departmentDao = new DepartmentDao();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        List<Department> departments = departmentDao.findAll();
        ObservableList<Department> observableArrayList = FXCollections.observableArrayList(departments);
        departmentTable.setItems(observableArrayList);
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        descriptionColumn.setCellValueFactory(new PropertyValueFactory<>("description"));
        companyColumn.setCellValueFactory(new PropertyValueFactory<>("company"));
    }

    public void onAddDepartment() throws IOException {

       FXMLLoader fxmlLoaderDep = new FXMLLoader(getClass().getResource("/fxml/department/add.fxml"));
        AnchorPane pane = fxmlLoaderDep.load();
        Scene scene = new Scene(pane);
        Stage addDialog = new Stage();
        addDialog.setScene(scene);
        addDialog.setTitle("Add department");
        addDialog.initModality(Modality.APPLICATION_MODAL);

        addDialog.showAndWait();
        AddDepartmetController controllerDep = fxmlLoaderDep.getController();
       Department dep = controllerDep.getResultDepartment();
        if(dep != null) {
            departmentDao.create(dep); // todo: DE TRATAT ERORILE
            departmentTable.getItems().add(dep);
       }

    }
    public void onDeleteDepartment(ActionEvent event) {
        int index = departmentTable.getSelectionModel().getSelectedIndex();
        if(index != -1) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setHeaderText("Are you sure you want to delete the selected department?");
            Department department = departmentTable.getItems().get(index);
            alert.setContentText(department.getId() + " " + department.getName() + " " + department.getDescription());
            alert.setTitle("Please confirm");
            Optional<ButtonType> result = alert.showAndWait();
            if(result.isPresent()) {
                if(result.get() == ButtonType.OK) {
                    departmentDao.delete(departmentTable.getItems().get(index).getId());
                    departmentTable.getItems().remove(index);
                }
            }
        }
    }

    public void onEditDepartment(ActionEvent event) throws IOException{
        Department selectedDepartment = departmentTable.getSelectionModel().getSelectedItem();

        if (selectedDepartment != null) {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/department/edit.fxml"));
            AnchorPane pane = fxmlLoader.load();
            Scene scene = new Scene(pane);
            Stage editDialog = new Stage();
            editDialog.setScene(scene);
            editDialog.setTitle("Edit department");
            editDialog.initModality(Modality.APPLICATION_MODAL);

            EditDepartmentController controller = fxmlLoader.getController();
            //int index = employeeTable.getSelectionModel().getSelectedIndex();
            // afisam fereastra de confirmare
            //if(index != -1) {
            //controller.setEditedEmployee(employeeTable.getItems().get(index));
            controller.setEditedDepartment(selectedDepartment);
            editDialog.showAndWait();

            Department dep = controller.getResult();

            if (dep != null) {
                departmentDao.update(dep); // todo: DE TRATAT ERORILE
                departmentTable.refresh();
            }
            // }
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("No Employee Selected");
            alert.setHeaderText(null);
            alert.setContentText("Please select an employee to edit.");

            alert.showAndWait();
        }
    }
}
