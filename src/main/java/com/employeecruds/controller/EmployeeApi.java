package com.employeecruds.controller;

import java.util.ArrayList;

import com.employeecruds.model.Employee;
import com.employeecruds.model.IEmployeeApi;

import java.io.File;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class EmployeeApi implements IEmployeeApi {

    String pathToDB = "C:/sqlite/";
    String dbName = "employeesDB.db";

    // Initializes the database
    public EmployeeApi() {
        new File(pathToDB).mkdirs();
        createNewDatabase(dbName);
        createEmployeeTable();
    }

    // Creates the table employee in the database.
    public void createEmployeeTable() {
        Connection conn = connect();
        try {
            String sql = "CREATE TABLE IF NOT EXISTS employees (\n"
                    + " id integer PRIMARY KEY,\n"
                    + " name text NOT NULL,\n"
                    + " age int NOT NULL,\n"
                    + " phone int NOT NULL\n"
                    + ");";
            Statement stmt = conn.createStatement();
            stmt.execute(sql);
            disconnect(conn);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    // Establishes a connection with the database
    public Connection connect() {
        Connection conn = null;
        try {

            String url = "jdbc:sqlite:" + pathToDB + dbName;
            conn = DriverManager.getConnection(url);

            System.out.println("Connection to SQLite has been established.");

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return conn;
    }

    // diconnects the connection to the database
    public void disconnect(Connection conn) {
        try {
            if (conn != null) {
                conn.close();
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    // Creates the database
    public void createNewDatabase(String fileName) {

        String url = "jdbc:sqlite:" + pathToDB + fileName;

        try {
            Connection conn = DriverManager.getConnection(url);

            if (conn != null) {
                DatabaseMetaData meta = conn.getMetaData();
                System.out.println("The driver name is " + meta.getDriverName());
                System.out.println("A new database has been created.");
            }

            disconnect(conn);

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    // CRUDS methods
    @Override
    public void addEmployee(Employee employee) {
        Connection conn = connect();
        if (conn != null) {
            try {
                String sql = "INSERT INTO employees (name, age, phone) VALUES(?,?,?)";
                PreparedStatement pstmt = conn.prepareStatement(sql);
                pstmt.setString(1, employee.getName());
                pstmt.setInt(2, employee.getAge());
                pstmt.setInt(3, employee.getPhone());
                pstmt.executeUpdate();
                disconnect(conn);
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }

        } else {
            System.out.println("could not add employee");
        }
    }

    @Override
    public void deleteEmployee(int employeeId) {
        Connection conn = connect();
        if (conn != null) {
            try {
                String sql = "DELETE FROM employees WHERE id = " + employeeId + ";";
                PreparedStatement pstmt = conn.prepareStatement(sql);
                // pstmt.setString(1, employee.getName());
                // pstmt.setInt(2, employee.getAge());
                // pstmt.setInt(3, employee.getPhone());
                pstmt.executeUpdate();
                disconnect(conn);
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        } else {
            System.out.println("could not delete employee");
        }
    }

    @Override
    public ArrayList<Employee> getEmployees() {
        ArrayList<Employee> employees = new ArrayList<Employee>();
        Connection conn = connect();
        if (conn != null) {
            try {
                String sql = "SELECT * FROM employees";
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(sql);

                while (rs.next()) {
                    Employee employee = new Employee();
                    employee.setId(rs.getInt("id"));
                    employee.setName(rs.getString("name"));
                    employee.setAge(rs.getInt("age"));
                    employee.setPhone(rs.getInt("phone"));
                    employees.add(employee);
                }

            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        } else {
            System.out.println("could not get employees");
        }
        return employees;
    }

    @Override
    public ArrayList<Employee> searchEmployee(Employee employee) {
        System.out.println(employee.getName());
        ArrayList<Employee> employees = new ArrayList<Employee>();
        Connection conn = connect();
        if (conn != null) {
            try {
                String sql;
                if(employee.getName() == null){
                     sql = "SELECT * FROM employees;";
                }
                else if (employee.getName().length() < 1){
                    sql = "SELECT * FROM employees WHERE (CHARINDEX("+employee.getAge()+", age) > 0 OR "+employee.getAge()+" = 0) " +
                    "AND (CHARINDEX("+employee.getPhone()+", phone) > 0 OR "+employee.getPhone()+" = 0);";
                }
                else{
                     sql = "SELECT * FROM employees WHERE CHARINDEX('" + employee.getName() + "', name) > 0 "+
                    "AND (CHARINDEX("+employee.getAge()+", age) > 0 OR "+employee.getAge()+" = 0) " +
                    "AND (CHARINDEX("+employee.getPhone()+", phone) > 0 OR "+employee.getPhone()+" = 0);";
                }
                PreparedStatement pstmt = conn.prepareStatement(sql);

                ResultSet rs = pstmt.executeQuery();

                while (rs.next()) {
                    Employee em = new Employee();
                    em.setId(rs.getInt("id"));
                    em.setName(rs.getString("name"));
                    em.setAge(rs.getInt("age"));
                    em.setPhone(rs.getInt("phone"));
                    employees.add(em);
                }

            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        } else {
            System.out.println("could not search employee");
        }
        return employees;
    }

    @Override
    public void updateEmployee(Employee employee) {
        Connection conn = connect();
        if (conn != null) {
            try {
                String sql = "UPDATE employees " +
                "SET name = ?, age = ?, phone = ?" +
                " WHERE id = ?;";

                PreparedStatement pstmt = conn.prepareStatement(sql);

                pstmt.setString(1, employee.getName());
                pstmt.setInt(2, employee.getAge());
                pstmt.setInt(3, employee.getPhone());
                pstmt.setInt(4, employee.getId());
                pstmt.executeUpdate();

                disconnect(conn);

            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        } else {
            System.out.println("could not update employee");
        }

    }

}
