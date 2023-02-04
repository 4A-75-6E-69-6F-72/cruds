package com.employeecruds.business;

import com.employeecruds.controller.MainPageController;

import javafx.application.Application;
import javafx.stage.Stage;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;

public class App extends Application {

    @Override
    public void start(Stage primaryStage) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/employeecruds/view/MainPage.fxml"));
            MainPageController controller = new MainPageController();
            loader.setController(controller);
            Pane mainPage = loader.load();
            Scene sc = new Scene(mainPage);
            primaryStage.setMaximized(true);
            primaryStage.setScene(sc);
            primaryStage.show();
        } catch (Exception e) {
            System.out.println(e);
        }
    }
    // public static void main(String[] args) {
    // EmployeeApi myEmployeeApi = new EmployeeApi();
    // Employee someEmployee = new Employee("j", 30, 0);
    // // someEmployee.setId(1);
    // // myEmployeeApi.addEmployee(someEmployee);
    // // myEmployeeApi.deleteEmployee(1);
    // ArrayList<Employee> employees = myEmployeeApi.searchEmployee(someEmployee);
    // if (employees != null && employees.size() > 0) {
    // for (Employee employee : employees) {
    // System.out.println("id: " + employee.getId() + " name: " + employee.getName()
    // + " age: "
    // + employee.getAge() + " phone: " + employee.getPhone());
    // }
    // }
    // else{
    // System.out.println("Empty employees");
    // }

    // System.out.println("Hello World!");
    // }
}
