package edu.step.db;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DepartmentDB extends AbstractDB {

    @Override
    public String getTableName() {
        return "department";
    }


    public Department selectOne(int id){
        try{
            Connection connection = getConnection();
            String sql = "SELECT * FROM " + getTableName() + " where id = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            while(resultSet.next()) {
                int depId = resultSet.getInt("id");
                String name = resultSet.getString("name");
                String description = resultSet.getString("description");
                return new Department(id, name, description);
            }
            connection.close();
            return null;
        } catch (SQLException ex) {
            System.out.println("Nu am putut face selectul: " + ex.getMessage());
            return null; // TODO: de inlocuit acest return cu throw new Exception()
        }
    }

    public List<Department> selectAll(){
        try{
            Connection connection = getConnection();
            ResultSet resultSet = selectAll(connection);
            List<Department> list = new ArrayList<>();
            while(resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                String description = resultSet.getString("description");
                list.add(new Department(id, name, description));
            }
            connection.close();
            return list;
        } catch (SQLException ex) {
            System.out.println("Nu am putut face selectul: " + ex.getMessage());
            return new ArrayList<>(); // TODO: de inlocuit acest return cu throw new Exception()
        }
    }


    public void create(Department department){
        try{
            Connection connection = getConnection();
            String sql = "INSERT INTO " + getTableName() +" (name, description) values(?, ?)";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, department.getName());
            statement.setString(2, department.getDescription());
            int rows = statement.executeUpdate();
            System.out.println("S-au modificat " + rows  + " randuri");
            connection.close();
        } catch (SQLException ex) {
            System.out.println("Nu am putut face insertul: " + ex.getMessage());
        }
    }

    public void update(Department department){
        try{
            Connection connection = getConnection();
            String sql = "UPDATE department SET name=?, description=? WHERE id = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, department.getName());
            statement.setString(2, department.getDescription());
            statement.setInt(3, department.getId());
            int rows = statement.executeUpdate();
            System.out.println("S-au modificat " + rows  + " randuri");
            connection.close();
        } catch (SQLException ex) {
            System.out.println("Nu am putut face update-ul: " + ex.getMessage());
        }
    }


    // parametru




}
