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
            PreparedStatement ps = CON.prepareStatement("UPDATE users SET username = ?, password = ?, role = ? WHERE id = ?");
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
            ps.setInt(1, user.getId());
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

    public boolean deleteOrder(OrderImpl order) {
        try {
            PreparedStatement ps = CON.prepareStatement("DELETE FROM orders WHERE id = ?");
            ps.setInt(1, order.getBalanceId());
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
            ps.setInt(1, productType.getId());
            return ps.executeUpdate()>0;
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
        return false;
    }

    public boolean createSaleTransaction(SaleTransactionImpl saleT) {
        try {
            PreparedStatement ps = CON.prepareStatement("INSERT INTO sale_transaction(id, discount, transaction_status, date_op, money, type, status) VALUES(?,?,?,?,?,?,?)");
            ps.setInt(1, saleT.getTicketNumber());
            ps.setDouble(2, saleT.getDiscountRate());
            ps.setString(3, saleT.getTransactionStatus());
            ps.setDate(4, Date.valueOf(saleT.getDate()));
            ps.setDouble(5, saleT.getMoney());
            ps.setString(6, saleT.getType());
            ps.setString(7, saleT.getStatus());
            return ps.executeUpdate()>0;
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
        return false;
    }

    public Map<Integer, SaleTransactionImpl> getAllSaleTransaction(Map<Integer, ProductTypeImpl> mProducts) {
        Map<Integer, SaleTransactionImpl> all = new HashMap<>();
        try {
            PreparedStatement ps = CON.prepareStatement("SELECT * FROM sale_transaction");

            ResultSet resultSet = ps.executeQuery();
            while (resultSet.next()) {
                int saleId = resultSet.getInt("id");
                all.put(
                        resultSet.getInt("id"),
                        new SaleTransactionImpl(
                                resultSet.getInt("id"),
                                new Date( resultSet.getDate("date_op").getTime() ).toLocalDate(),
                                resultSet.getString("type"),
                                resultSet.getString("status"),
                                getAllBySaleId(saleId, mProducts),
                                getAllReturnTransaction(saleId, mProducts),
                                resultSet.getDouble("discount"),
                                resultSet.getString("transaction_status")
                        )
                );
            }
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
        return all;
    }

    private Map<ProductTypeImpl, TransactionProduct> getAllBySaleId(int saleTransactionId, Map<Integer, ProductTypeImpl> mProducts) {
        Map<ProductTypeImpl, TransactionProduct> all = new HashMap<>();
        try {
            PreparedStatement ps = CON.prepareStatement("SELECT * FROM transaction_product WHERE id_sale = ?");
            ps.setInt(1, saleTransactionId);

            ResultSet resultSet = ps.executeQuery();
            while (resultSet.next()) {
                ProductTypeImpl product = mProducts.get(resultSet.getInt("id_product"));
                all.put(
                        product,
                        new TransactionProduct(
                                product,
                                resultSet.getDouble("discount"),
                                resultSet.getInt("quantity")
                        )
                );
            }
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
        return all;
    }

    private Map<Integer, ReturnTransaction> getAllReturnTransaction(int saleTransactionId, Map<Integer, ProductTypeImpl> mProducts) {
        Map<Integer, ReturnTransaction> all = new HashMap<>();
        try {
            PreparedStatement ps = CON.prepareStatement("SELECT * FROM return_transaction WHERE id_sale = ?");
            ps.setInt(1, saleTransactionId);

            ResultSet resultSet = ps.executeQuery();
            while (resultSet.next()) {
                ProductTypeImpl product = mProducts.get(resultSet.getInt("id_product"));
                all.put(
                        resultSet.getInt("id"),
                        new ReturnTransaction(
                                resultSet.getInt("id"),
                                new Date( resultSet.getDate("date_op").getTime() ).toLocalDate(),
                                resultSet.getString("type"),
                                resultSet.getString("status"),
                                resultSet.getBoolean("committed"),
                                resultSet.getInt("amount"),
                                product
                        )
                );
            }
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
        return all;
    }

    public boolean addProductToSale(SaleTransactionImpl transaction, TicketEntry ticket, int productId) {
        try {
            PreparedStatement ps = CON.prepareStatement("INSERT INTO transaction_product(id_sale, id_product, discount, quantity) VALUES(?,?,?,?)");
            ps.setInt(1, transaction.getTicketNumber());
            ps.setInt(2, productId);
            ps.setDouble(3, ticket.getDiscountRate());
            ps.setInt(4, ticket.getAmount());
            return ps.executeUpdate()>0;
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
        return false;
    }

    public boolean saveSaleTransaction(SaleTransactionImpl saleT) {
        if(createSaleTransaction(saleT)) {
            for(TransactionProduct ticket: saleT.getTicketEntries()) {
                addProductToSale(saleT, ticket, ticket.getProductType().getId());
                updateProductType(ticket.getProductType());
            }
        }
        return true;
    }

    public boolean deleteSaleTransaction(SaleTransactionImpl transaction) {
        try {
            PreparedStatement ps = CON.prepareStatement("DELETE FROM sale_transaction WHERE id = ?");
            ps.setInt(1, transaction.getTicketNumber());
            if(ps.executeUpdate()>0){
                // Rollback product quantities
                for (TransactionProduct tp: transaction.getTicketEntries()) {
                    updateProductType(tp.getProductType());
                }
            }
            return true;
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
        return false;
    }

    public boolean updateSaleTransaction(SaleTransactionImpl transaction) {
        try {
            PreparedStatement ps = CON.prepareStatement("UPDATE sale_transaction SET discount = ?, transaction_status = ?, date_op = ?, money = ?, type = ?, status = ? WHERE id = ?");
            ps.setDouble(1, transaction.getDiscountRate());
            ps.setString(2, transaction.getTransactionStatus());
            ps.setDate(3, Date.valueOf(transaction.getDate()));
            ps.setDouble(4, transaction.getMoney());
            ps.setString(5, transaction.getType());
            ps.setString(6, transaction.getStatus());
            ps.setInt(7, transaction.getTicketNumber());
            return ps.executeUpdate()>0;
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
        return false;
    }

    public boolean saveBalanceOperation(BalanceOperationImpl operation) {
        try {
            PreparedStatement ps = CON.prepareStatement("INSERT INTO balance_operations(id, date_op, money, type, status) VALUES(?,?,?,?,?)");
            ps.setInt(1, operation.getBalanceId());
            ps.setDate(2, Date.valueOf(operation.getDate()));
            ps.setDouble(3, operation.getMoney());
            ps.setString(4, operation.getType());
            ps.setString(5, operation.getStatus());
            return ps.executeUpdate()>0;
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
        return false;
    }

    public double getBalance() {
        try {
            PreparedStatement ps = CON.prepareStatement("SELECT balance FROM account_book");
            ResultSet resultSet = ps.executeQuery();
            if(resultSet.next()) return resultSet.getDouble("balance");
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
        return 0;
    }

    public Map<Integer, BalanceOperationImpl> getAllBalanceOperations() {
        Map<Integer, BalanceOperationImpl> all = new HashMap<>();
        try {
            PreparedStatement ps = CON.prepareStatement("SELECT * FROM balance_operations");

            ResultSet resultSet = ps.executeQuery();
            while (resultSet.next()) {
                all.put(
                        resultSet.getInt("id"),
                        new BalanceOperationImpl(
                                resultSet.getInt("id"),
                                new Date( resultSet.getDate("date_op").getTime() ).toLocalDate(),
                                resultSet.getDouble("money"),
                                resultSet.getString("type"),
                                resultSet.getString("status")
                        )
                );
            }
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
        return all;
    }

    public boolean deleteBalanceOperation(BalanceOperation op) {
        try {
            PreparedStatement ps = CON.prepareStatement("DELETE FROM balance_operations WHERE id = ?");
            ps.setInt(1, op.getBalanceId());
            return ps.executeUpdate()>0;
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
        return false;
    }


    public boolean saveReturnTransaction(ReturnTransaction returnTransaction, Integer saleId) {
        try {
            PreparedStatement ps = CON.prepareStatement("INSERT INTO return_transaction(id, date_op, money, type, status, id_product, amount, committed, id_sale) VALUES(?,?,?,?,?,?,?,?,?)");
            ps.setInt(1, returnTransaction.getBalanceId());
            ps.setDate(2, Date.valueOf(returnTransaction.getDate()));
            ps.setDouble(3, returnTransaction.getMoney());
            ps.setString(4, returnTransaction.getType());
            ps.setString(5, returnTransaction.getStatus());
            ps.setInt(6, returnTransaction.getProduct().getId());
            ps.setInt(7, returnTransaction.getAmount());
            ps.setBoolean(8, returnTransaction.isCommited());
            ps.setInt(9, saleId);
            if(ps.executeUpdate() > 0) {
                return updateProductType(returnTransaction.getProduct());
            }
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
        return false;
    }

    public boolean deleteReturnTransaction(ReturnTransaction transaction) {
        try {
            PreparedStatement ps = CON.prepareStatement("DELETE FROM return_transaction WHERE id = ?");
            ps.setInt(1, transaction.getBalanceId());
            if(ps.executeUpdate()>0){
                // Rollback product quantities
                updateProductType(transaction.getProduct());
            }
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
        return false;
    }

    public boolean updateReturnTransaction(ReturnTransaction returnT, Integer saleId) {
        try {
            PreparedStatement ps = CON.prepareStatement("UPDATE return_transaction SET date_op = ?, money = ?, type = ?, status = ?, id_product = ?, amount = ?, committed = ?, id_sale = ? WHERE id = ?");
            ps.setDate(1, Date.valueOf(returnT.getDate()));
            ps.setDouble(2, returnT.getMoney());
            ps.setString(3, returnT.getType());
            ps.setString(4, returnT.getStatus());
            ps.setInt(5, returnT.getProduct().getId());
            ps.setInt(6, returnT.getAmount());
            ps.setBoolean(7, returnT.isCommited());
            ps.setInt(8, saleId);
            ps.setInt(9, returnT.getBalanceId());
            return ps.executeUpdate()>0;
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
        return false;
    }
}
