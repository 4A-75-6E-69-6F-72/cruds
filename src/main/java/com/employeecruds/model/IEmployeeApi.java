package com.employeecruds.model;

import java.util.ArrayList;

// interface for the API class which will query the database
public interface IEmployeeApi {
    abstract ArrayList<Employee> getEmployees();
    abstract void addEmployee(Employee employee);
    abstract void deleteEmployee(int employeeId);
    abstract void updateEmployee(Employee employee);
    abstract ArrayList<Employee> searchEmployee(Employee employee);
}
