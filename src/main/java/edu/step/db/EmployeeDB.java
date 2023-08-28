package edu.step.db;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EmployeeDB extends AbstractDB {

    public String getTableName() {
        return "employee";
    }

    public List<Employee> selectAll(){
        try{
            Connection connection = getConnection();
            ResultSet resultSet = selectAll(connection);
            List<Employee> list = new ArrayList<>();
            while(resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                String surname = resultSet.getString("surname");
                String email = resultSet.getString("email");
                int departmentId = resultSet.getInt("department_id");
                DepartmentDB departmentDB = new DepartmentDB();
                Department department = departmentDB.selectOne(departmentId);
                list.add(new Employee(id, name, surname, email, department));
            }
            connection.close();
            return list;
        } catch (SQLException ex) {
            System.out.println("Nu am putut face selectul: " + ex.getMessage());
            return new ArrayList<>(); // TODO: de inlocuit acest return cu throw new Exception()
        }
    }

    public void update(Employee employee){
        try{
            Connection connection = getConnection();
            String sql = "UPDATE employee SET name=?, surname=?, email=? WHERE id = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, employee.getName());
            statement.setString(2, employee.getSurname());
            statement.setString(3, employee.getEmail());
            statement.setInt(4, employee.getId());
            int rows = statement.executeUpdate();
            System.out.println("S-au modificat " + rows  + " randuri");
            connection.close();
        } catch (SQLException ex) {
            System.out.println("Nu am putut face update-ul: " + ex.getMessage());
        }
    }

    public void create(Employee employee){
        try{
            Connection connection = getConnection();
            String sql = "INSERT INTO employee(name, surname, email, department_id, birthdate) values(?, ?, ?, ?)";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, employee.getName());
            statement.setString(2, employee.getSurname());
            statement.setString(3, employee.getEmail());
            statement.setInt(4, employee.getDepartment().getId());
            statement.setString(5, employee.getBirthdate().toString());
            int rows = statement.executeUpdate();
            System.out.println("S-au modificat " + rows  + " randuri");
            connection.close();
        } catch (SQLException ex) {
            System.out.println("Nu am putut face insertul: " + ex.getMessage());
        }
    }
}
