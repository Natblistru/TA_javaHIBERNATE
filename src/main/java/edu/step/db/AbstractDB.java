package edu.step.db;

import java.sql.*;

public abstract class AbstractDB {

    public abstract String getTableName();

    public Connection getConnection() throws SQLException {
        String url = "jdbc:mysql://localhost:3306/test";
        String username = "root";
        String password = "root";
        return DriverManager.getConnection(url, username, password);
    }


    protected ResultSet selectAll(Connection connection) throws SQLException {
        String sql = "SELECT * FROM " + getTableName();
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(sql);
        return resultSet;
    }

    public void delete(int id){
        try{
            Connection connection = getConnection();
            String sql = "DELETE FROM ? where id = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, getTableName());
            statement.setInt(2, id);
            int rows = statement.executeUpdate();
            System.out.println("S-au sters " + rows  + " randuri");
            connection.close();
        } catch (SQLException ex) {
            System.out.println("Nu am putut face delete-ul: " + ex.getMessage());
        }
    }
}
