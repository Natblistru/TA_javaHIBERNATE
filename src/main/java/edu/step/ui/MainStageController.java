package edu.step.ui;

import edu.step.db.Company;
import edu.step.db.Department;
import edu.step.db.Employee;
import edu.step.db.hibernate.CompanyDao;
import edu.step.db.hibernate.DepartmentDao;
import edu.step.db.hibernate.EmployeeDao;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.*;

// TODO: implement views
public class MainStageController implements Initializable {

    public TableColumn<Employee, String> nameColumn;
    public TableColumn<Employee, String> surnameColumn;
    public TableColumn<Employee, String> emailColumn;
    public TableColumn<Employee, String> birthdateColumn; // TODO: de inlocuit cu alt tip corespunzator
    public TableColumn<Employee, Department> departmentColumn; // TODO: de inlocuit cu alt tip corespunzator

    public TableView<Employee> employeeTable; // TODO: stergere multipla

    public MenuItem DepartmentsMenuItem;

    public MenuItem CompaniesMenuItem;

 //   private EmployeeDB employeeDB = new EmployeeDB();
    private EmployeeDao employeeDao = new EmployeeDao();
    private DepartmentDao departmentDao = new DepartmentDao();
    public CompanyDao companyDao = new CompanyDao();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // List<Employee> employees = employeeDB.selectAll();
        List<Employee> employees = employeeDao.findAll();
        ObservableList<Employee> observableArrayList = FXCollections.observableArrayList(employees);
        employeeTable.setItems(observableArrayList);
        employeeTable.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        surnameColumn.setCellValueFactory(new PropertyValueFactory<>("surname"));
        emailColumn.setCellValueFactory(new PropertyValueFactory<>("email"));
        departmentColumn.setCellValueFactory(new PropertyValueFactory<>("department"));
        birthdateColumn.setCellValueFactory(new PropertyValueFactory<>("birthdate"));
    }

    public void onAdd() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/add.fxml"));
        AnchorPane pane = fxmlLoader.load();
        Scene scene = new Scene(pane);
        Stage addDialog = new Stage();
        addDialog.setScene(scene);
        addDialog.setTitle("Add employee");
        addDialog.initModality(Modality.APPLICATION_MODAL);
        addDialog.showAndWait();
        AddDialogController controller = fxmlLoader.getController();
        Employee emp = controller.getResult();
        if(emp != null) {
            employeeDao.create(emp); // todo: DE TRATAT ERORILE
            employeeTable.getItems().add(emp);
        }

    }

    public void onDelete() {
        ObservableList<Integer> selectedIndices = employeeTable.getSelectionModel().getSelectedIndices();
        List<Integer> idxCopy = new ArrayList<>(selectedIndices);
        // sortam invers sa ne asiguram ca obiectul cu indexul cel mai mare e sters primul
        idxCopy.sort(Comparator.reverseOrder());


        // citim care rand a fost selectat
        int index = employeeTable.getSelectionModel().getSelectedIndex();
        // afisam fereastra de confirmare
        if(!selectedIndices.isEmpty()){
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setHeaderText("Are you sure you want to delete the selected employee?");
             alert.setContentText("You have selected " + selectedIndices.size() + " employee(s)");
            alert.setTitle("Please confirm");
            Optional<ButtonType> result = alert.showAndWait();
            if(result.isPresent()) {
                if(result.get() == ButtonType.OK) {
                    for(int idx: idxCopy) {
                        employeeDao.delete( employeeTable.getItems().get(idx).getId());
                        employeeTable.getItems().remove(idx);
                    }
                }
            }
        }
        // daca yes - stergem
        // daca no - nu facem nimic
    }

    public void onEdit() throws IOException{
        Employee selectedEmployee = employeeTable.getSelectionModel().getSelectedItem();

        if (selectedEmployee != null) {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/edit.fxml"));
            AnchorPane pane = fxmlLoader.load();
            Scene scene = new Scene(pane);
            Stage editDialog = new Stage();
            editDialog.setScene(scene);
            editDialog.setTitle("Edit employee");
            editDialog.initModality(Modality.APPLICATION_MODAL);

            EditDialogController controller = fxmlLoader.getController();
            //int index = employeeTable.getSelectionModel().getSelectedIndex();
            // afisam fereastra de confirmare
            //if(index != -1) {
                //controller.setEditedEmployee(employeeTable.getItems().get(index));
            controller.setEditedEmployee(selectedEmployee);
            editDialog.showAndWait();

                Employee emp = controller.getResult();

                if (emp != null) {
                    employeeDao.update(emp); // todo: DE TRATAT ERORILE
                    employeeTable.refresh();
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


    public void onOpenDepartments (ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/department/listDepartments.fxml"));
        AnchorPane pane = fxmlLoader.load();
        Scene scene = new Scene(pane);
        Stage addDialog = new Stage();
        addDialog.setScene(scene);
        addDialog.setTitle("List of departments");
        addDialog.initModality(Modality.APPLICATION_MODAL);
        addDialog.show();
    }

    public void onOpenCompanies (ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/company/ListCompanies.fxml"));
        AnchorPane pane = fxmlLoader.load();
        Scene scene = new Scene(pane);
        Stage addDialog = new Stage();
        addDialog.setScene(scene);
        addDialog.setTitle("List of companies");
        addDialog.initModality(Modality.APPLICATION_MODAL);
        addDialog.show();
    }


    public void onExport(ActionEvent event) {
        try {
            List<Company> companies = companyDao.findAll();
            String filePath = "D:\\Documents\\JAVA\\write1.yaml";
            companyDao.onExport(companies, filePath);

        } catch (IOException e) {
            System.err.println("A apărut o eroare la exportul datelor: " + e.getMessage());
        }

    }

    public void onImport(ActionEvent event) {
        try {
            String filePath = "D:\\Documents\\JAVA\\write1.yaml";
            companyDao.onImport(filePath,companyDao,departmentDao,employeeDao);
            initialize(null,null);
        } catch (IOException e) {
            System.err.println("A apărut o eroare la exportul datelor: " + e.getMessage());
        }
    }
}
