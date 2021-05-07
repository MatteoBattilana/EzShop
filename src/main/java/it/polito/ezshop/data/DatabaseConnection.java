/*
 * AA 2016-2017
 * Introduction to Web Programming
 * Common - DAO
 * UniTN
 */
package it.polito.ezshop.data;

import java.io.File;
import java.io.FileNotFoundException;
import java.sql.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class DatabaseConnection {
    private final String databaseFileName = "database.db";
    private final String schemaFileName = "schema.sql";
    private final transient Connection CON;


    public DatabaseConnection() {
        try {
            CON = DriverManager.getConnection("jdbc:sqlite:" + databaseFileName);
            if (CON != null) {
                DatabaseMetaData meta = CON.getMetaData();
                executeSqlScript(schemaFileName);

            }
        } catch (SQLException cnfe) {
            throw new RuntimeException(cnfe.getMessage(), cnfe.getCause());
        }
    }

    private void executeSqlScript(String filename) {
        // Delimiter
        String delimiter = ";";

        // Create scanner
        Scanner scanner;
        try {
            scanner = new Scanner(new File(filename)).useDelimiter(delimiter);
        } catch (FileNotFoundException e1) {
            e1.printStackTrace();
            return;
        }

        // Loop through the SQL file statements
        Statement currentStatement = null;
        while(scanner.hasNext()) {

            // Get statement
            String rawStatement = scanner.next() + delimiter;
            try {
                // Execute statement
                currentStatement = CON.createStatement();
                currentStatement.execute(rawStatement);
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                // Release resources
                if (currentStatement != null) {
                    try {
                        currentStatement.close();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
                currentStatement = null;
            }
        }
        scanner.close();
    }

    public boolean createUser(User user) {
        try {
            PreparedStatement ps = CON.prepareStatement("INSERT INTO users(id, username, password, role) VALUES(?,?,?,?)");
            ps.setInt(1, user.getId());
            ps.setString(2, user.getUsername());
            ps.setString(3, user.getPassword());
            ps.setString(4, user.getRole());
            return ps.executeUpdate()>0;
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
        return false;
    }

    public boolean updateUser(User user) {
        try {
            PreparedStatement ps = CON.prepareStatement("UPDATE users SET username = ?, password = ?, role = ?) WHERE id = ?");
            ps.setString(1, user.getUsername());
            ps.setString(2, user.getPassword());
            ps.setString(3, user.getRole());
            ps.setInt(4, user.getId());
            return ps.executeUpdate()>0;
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
        return false;
    }

    public boolean deleteUser(User user) {
        try {
            PreparedStatement ps = CON.prepareStatement("DELETE FROM users WHERE id = ?");
            ps.setInt(0, user.getId());
            return ps.executeUpdate()>0;
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
        return false;
    }

    public Map<Integer, User> getAllUsers() {
        Map<Integer, User> all = new HashMap<>();
        try {
            PreparedStatement ps = CON.prepareStatement("SELECT * FROM users");

            ResultSet resultSet = ps.executeQuery();
            while (resultSet.next()) {
                all.put(
                        resultSet.getInt("id"),
                        new UserImpl(
                                resultSet.getInt("id"),
                                resultSet.getString("username"),
                                resultSet.getString("password"),
                                resultSet.getString("role")
                        )
                );
            }
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
        return all;
    }
}
