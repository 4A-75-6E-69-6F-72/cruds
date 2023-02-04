package com.employeecruds.controller;

import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

import com.employeecruds.model.Employee;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.text.Text;

public class EmployeeComponentController implements Initializable{

    private int viewId;
    private Employee employee;
    private MainPageController parentController;

    EmployeeComponentController(int index, Employee employee, MainPageController parentController){
        this.employee = employee;
        this.viewId = index;
        this.parentController = parentController;
    }

    @FXML
    private Text age;

    @FXML
    private Text index;

    @FXML
    private Text name;

    @FXML
    private Text phone;

    @FXML
    void delete(ActionEvent event) {
        Alert a = new Alert(AlertType.NONE);
        a.setHeaderText("Are you sure you want to delete this employee?");
        a.setAlertType(AlertType.CONFIRMATION);
        Optional<ButtonType> choice = a.showAndWait();
        if(choice.isPresent() && choice.get() == ButtonType.OK){
            EmployeeApi employeeApi = new EmployeeApi();
            employeeApi.deleteEmployee(employee.getId());
            parentController.refreshEmployeesPane();
        }
    }

    @FXML
    void update(ActionEvent event) {
        parentController.loadEmployee(employee);
    }

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        age.setText(employee.getAge() + "");
        index.setText(viewId + "");
        name.setText(employee.getName());
        phone.setText(employee.getPhone() + "");
    }
}
