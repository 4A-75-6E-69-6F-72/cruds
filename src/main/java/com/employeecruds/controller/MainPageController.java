package com.employeecruds.controller;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import com.employeecruds.model.Employee;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

public class MainPageController implements Initializable {

    @FXML
    private Button clearButton;

    private int employeeId = 0;

    @FXML
    private TextField employeeAge;

    @FXML
    private TextField employeeName;

    @FXML
    private TextField employeePhone;

    @FXML
    private StackPane employeesPane;

    @FXML
    private Button saveButton;

    @FXML
    private TextField searchAge;

    @FXML
    private Button searchButton;

    @FXML
    private TextField searchName;

    @FXML
    private TextField searchPhone;

    private Employee searchedEmployee = new Employee();

    @FXML
    void clear(ActionEvent event) {
        employeeId = 0;
        this.employeeName.setText("");
        this.employeeAge.setText("");
        this.employeePhone.setText("");
    }

    @FXML
    void save(ActionEvent event) {
        if (employeeAge.getText().length() == 0 || employeeName.getText().length() == 0 || employeePhone.getText().length() == 0) {
            Alert a = new Alert(AlertType.INFORMATION);
            a.setHeaderText("Please fill all the fields before saving");
            a.showAndWait();
        }
        else{
            try {
                Employee employee = new Employee(employeeName.getText(), Integer.parseInt(employeeAge.getText()), Integer.parseInt(employeePhone.getText()));
                EmployeeApi employeeApi = new EmployeeApi();
                if(employeeId > 0){
                    employee.setId(employeeId);
                    employeeApi.updateEmployee(employee);
                    refreshEmployeesPane();
                    return;
                }
                employeeApi.addEmployee(employee);
                refreshEmployeesPane();
            } catch (Exception e) {
                Alert a = new Alert(AlertType.INFORMATION);
                a.setHeaderText("Wrong inputs for employee's information");
                a.showAndWait();
            }
            
        }
    }

    @FXML
    void search(ActionEvent event) {
        if (searchName.getText().length() == 0 && searchPhone.getText().length() == 0 && searchAge.getText().length() == 0) {
            searchedEmployee = new Employee();
            refreshEmployeesPane();
        }
        else{
            String name = "";
            try {
                if(searchName.getText() != null){
                    name = searchName.getText();
                }

                int age;
                try {
                    age = Integer.parseInt(searchAge.getText());
                } catch (Exception e) {
                    age = 0;
                }

                int phone;
                try {
                    phone = Integer.parseInt(searchPhone.getText());
                } catch (Exception e) {
                    phone = 0;
                }
                Employee employee = new Employee(name, age, phone);
                searchedEmployee = employee;
                refreshEmployeesPane();
            } catch (Exception e) {
                Alert a = new Alert(AlertType.INFORMATION);
                a.setHeaderText("Wrong inputs for searching employee's information");
                a.showAndWait();
            }
            
        }
    }

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        refreshEmployeesPane();
    }

    public void refreshEmployeesPane() {
        clear(null);
        EmployeeApi employeeApi = new EmployeeApi();
        ArrayList<Employee> employees = employeeApi.searchEmployee(searchedEmployee);

        employeesPane.getChildren().clear();

        if (employees != null && employees.size() > 0) {
            employeesPane.getChildren().add(getEmployeesListPane(employees));
        }

    }

    VBox getEmployeesListPane(ArrayList<Employee> employees) {
        VBox employeesListPane = new VBox();
        employeesListPane.setSpacing(20);
        int i = 1;
        for (Employee employee : employees) {
            employeesListPane.getChildren().add(getEmployeeListElement(employee, i));
            i++;
        }
        return employeesListPane;
    }

    Pane getEmployeeListElement(Employee employee, int index) {
        try {
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource("/com/employeecruds/view/EmployeeComponent.fxml"));
            EmployeeComponentController controller = new EmployeeComponentController(index, employee, this);
            loader.setController(controller);
            return loader.load();
        } catch (Exception e) {
            System.out.println("could not load employee list element");
        }
        return null;
    }

    void loadEmployee(Employee employee){
        System.out.println(employee.getId());
        this.employeeId = employee.getId();
        this.employeeName.setText(employee.getName());
        this.employeeAge.setText(employee.getAge() + "");
        this.employeePhone.setText(employee.getPhone() + "");
    }

}
