package edu.step.ui.company;

import edu.step.db.Company;
import edu.step.db.Department;
import edu.step.db.hibernate.CompanyDao;
import edu.step.ui.company.AddCompanytController;
import edu.step.ui.company.EditCompanyController;
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

public class CompaniesListController implements Initializable  {

    public TableView<Company> companyTable;

    public TableColumn<Company, String> nameColumn;

    public TableColumn<Company, String> descriptionColumn;

    private CompanyDao companyDao = new CompanyDao();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        List<Company> companies = companyDao.findAll();
        ObservableList<Company> observableArrayList = FXCollections.observableArrayList(companies);
        companyTable.setItems(observableArrayList);
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        descriptionColumn.setCellValueFactory(new PropertyValueFactory<>("description"));
    }
    public void onAddCompany() throws IOException {

        FXMLLoader fxmlLoaderDep = new FXMLLoader(getClass().getResource("/fxml/company/add.fxml"));
        AnchorPane pane = fxmlLoaderDep.load();
        Scene scene = new Scene(pane);
        Stage addDialog = new Stage();
        addDialog.setScene(scene);
        addDialog.setTitle("Add company");
        addDialog.initModality(Modality.APPLICATION_MODAL);

        addDialog.showAndWait();
        AddCompanytController controllerDep = fxmlLoaderDep.getController();
        Company comp = controllerDep.getResultCompany();
        if(comp != null) {
            companyDao.create(comp); // todo: DE TRATAT ERORILE
            companyTable.getItems().add(comp);
        }

    }
    public void onDeleteCompany(ActionEvent event) {
        int index = companyTable.getSelectionModel().getSelectedIndex();
        if(index != -1) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setHeaderText("Are you sure you want to delete the selected company?");
            Company company = companyTable.getItems().get(index);
            alert.setContentText(company.getId() + " " + company.getName() + " " + company.getDescription());
            alert.setTitle("Please confirm");
            Optional<ButtonType> result = alert.showAndWait();
            if(result.isPresent()) {
                if(result.get() == ButtonType.OK) {
                    companyDao.delete(companyTable.getItems().get(index).getId());
                    companyTable.getItems().remove(index);
                }
            }
        }
    }

    public void onEditCompany(ActionEvent event) throws IOException{
        Company selectedCompany = companyTable.getSelectionModel().getSelectedItem();

        if (selectedCompany != null) {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/company/edit.fxml"));
            AnchorPane pane = fxmlLoader.load();
            Scene scene = new Scene(pane);
            Stage editDialog = new Stage();
            editDialog.setScene(scene);
            editDialog.setTitle("Edit company");
            editDialog.initModality(Modality.APPLICATION_MODAL);

            EditCompanyController controller = fxmlLoader.getController();
            //int index = employeeTable.getSelectionModel().getSelectedIndex();
            // afisam fereastra de confirmare
            //if(index != -1) {
            //controller.setEditedEmployee(employeeTable.getItems().get(index));
            controller.setEditedCompany(selectedCompany);
            editDialog.showAndWait();

            Company comp = controller.getResult();

            if (comp != null) {
                companyDao.update(comp); // todo: DE TRATAT ERORILE
                companyTable.refresh();
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
