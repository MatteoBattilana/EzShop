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

    public boolean createOrder(Order o) {
        try {
            PreparedStatement ps = CON.prepareStatement("INSERT INTO orders(id, quantity, balance_id, product_code, status, price_per_unit) VALUES(?,?,?,?,?,?)");
            ps.setInt(1, o.getOrderId());
            ps.setInt(2, o.getQuantity());
            ps.setInt(3, o.getBalanceId());
            ps.setString(4, o.getProductCode());
            ps.setString(5, o.getStatus());
            ps.setDouble(6, o.getPricePerUnit());
            return ps.executeUpdate()>0;
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
        return false;
    }

    public boolean createBalanceOperation(BalanceOperationImpl op) {
        try {
            PreparedStatement ps = CON.prepareStatement("INSERT INTO balance_operations(id, date_op, money, type, status) VALUES(?,?,?,?,?)");
            ps.setInt(1, op.getBalanceId());
            ps.setDate(2, Date.valueOf(op.getDate()));
            ps.setDouble(3, op.getMoney());
            ps.setString(4, op.getType());
            ps.setString(5, op.getStatus());
            return ps.executeUpdate()>0;
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
        return false;
    }

    public boolean updateBalance(double v) {
        deleteBalance();
        try {
            PreparedStatement ps = CON.prepareStatement("INSERT INTO account_book(balance) VALUES(?)");
            ps.setDouble(1, v);
            return ps.executeUpdate()>0;
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
        return false;
    }

    private boolean deleteBalance() {
        try {
            PreparedStatement ps = CON.prepareStatement("DELETE FROM account_book");
            return ps.executeUpdate()>0;
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
        return false;
    }

    public boolean deleteBalanceOperation(BalanceOperationImpl operation) {
        try {
            PreparedStatement ps = CON.prepareStatement("DELETE FROM balance_operations WHERE id = ?");
            ps.setInt(0, operation.getBalanceId());
            return ps.executeUpdate()>0;
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
        return false;
    }

    public boolean deleteOrder(OrderImpl order) {
        try {
            PreparedStatement ps = CON.prepareStatement("DELETE FROM orders WHERE id = ?");
            ps.setInt(0, order.getBalanceId());
            return ps.executeUpdate()>0;
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
        return false;
    }

    private boolean updateBalanceOperation(BalanceOperationImpl balanceOperation) {
        try {
            PreparedStatement ps = CON.prepareStatement("UPDATE balance_operations SET date_op = ?, money = ?, type = ?, status = ?) WHERE id = ?");
            ps.setDate(1, Date.valueOf(balanceOperation.getDate()));
            ps.setDouble(2, balanceOperation.getMoney());
            ps.setString(3, balanceOperation.getType());
            ps.setString(4, balanceOperation.getStatus());
            ps.setInt(5, balanceOperation.getBalanceId());
            return ps.executeUpdate()>0;
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
        return false;
    }

    public Map<Integer, ProductTypeImpl> getAllProducts() {
        Map<Integer, ProductTypeImpl> all = new HashMap<>();
        try {
            PreparedStatement ps = CON.prepareStatement("SELECT * FROM product_type");

            ResultSet resultSet = ps.executeQuery();
            while (resultSet.next()) {
                all.put(
                        resultSet.getInt("id"),
                        new ProductTypeImpl(
                                resultSet.getInt("quantity"),
                                resultSet.getString("location"),
                                resultSet.getString("note"),
                                resultSet.getString("description"),
                                resultSet.getString("barcode"),
                                resultSet.getDouble("price"),
                                resultSet.getInt("id")
                        )
                );
            }
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
        return all;
    }

    public boolean updateProductType(ProductTypeImpl prod) {
        try {
            PreparedStatement ps = CON.prepareStatement("UPDATE product_type SET location = ?, quantity = ?, note = ?, description = ?, barcode = ?, price = ? WHERE id = ?");
            ps.setString(1, prod.getLocation());
            ps.setInt(2, prod.getQuantity());
            ps.setString(3, prod.getNote());
            ps.setString(4, prod.getProductDescription());
            ps.setString(5, prod.getBarCode());
            ps.setDouble(6, prod.getPricePerUnit());
            ps.setInt(7, prod.getId());
            return ps.executeUpdate()>0;
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
        return false;
    }

    public boolean createProductType(ProductTypeImpl prod) {
        try {
            PreparedStatement ps = CON.prepareStatement("INSERT INTO product_type (id, location, quantity, note, description, barcode, price) VALUES(?,?,?,?,?,?,?)");
            ps.setInt(1, prod.getId());
            ps.setString(2, prod.getLocation());
            ps.setInt(3, prod.getQuantity());
            ps.setString(4, prod.getNote());
            ps.setString(5, prod.getProductDescription());
            ps.setString(6, prod.getBarCode());
            ps.setDouble(7, prod.getPricePerUnit());
            return ps.executeUpdate()>0;
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
        return false;
    }

    public boolean deleteProductType(ProductTypeImpl productType) {
        try {
            PreparedStatement ps = CON.prepareStatement("DELETE FROM product_type WHERE id = ?");
            ps.setInt(0, productType.getId());
            return ps.executeUpdate()>0;
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
        return false;
    }
}
