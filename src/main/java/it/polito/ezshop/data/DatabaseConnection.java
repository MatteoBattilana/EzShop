package it.polito.ezshop.data;

import java.io.File;
import java.io.FileNotFoundException;
import java.sql.*;
import java.sql.Date;
import java.util.*;

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

    public boolean setUserRole(User user, String role) {
        try {
            PreparedStatement ps = CON.prepareStatement("UPDATE users SET role = ? WHERE id = ?");
            ps.setString(1, role);
            ps.setInt(2, user.getId());
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

    public boolean createOrder(OrderImpl o) {
        try {
            PreparedStatement ps = CON.prepareStatement("INSERT INTO order_operation(id, date_op, status, quantity, product_code, order_status, price_per_unit) VALUES(?,?,?,?,?,?,?)");
            ps.setInt(1, o.getBalanceId());
            ps.setDate(2, Date.valueOf(o.getDate()));
            ps.setString(3, o.getStatus());
            ps.setInt(4, o.getQuantity());
            ps.setString(5, o.getProductCode());
            ps.setString(6, o.getOrderStatus());
            ps.setDouble(7, o.getPricePerUnit());
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
                                this,
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
            CON.setAutoCommit(false);
            PreparedStatement ps = CON.prepareStatement("DELETE FROM sale_transaction WHERE id = ?");
            ps.setInt(1, transaction.getTicketNumber());
            if(ps.executeUpdate()>0){
                // Rollback product quantities
                for (TransactionProduct tp: transaction.getTicketEntries()) {
                    ProductTypeImpl product = tp.getProductType();
                    product.setQuantity(product.getQuantity() + tp.getAmount());
                    if(!updateProductType(tp.getProductType())){
                        product.setQuantity(product.getQuantity() - tp.getAmount());
                    }
                }
            }
            CON.commit();
            CON.setAutoCommit(true);
            return true;
        }
        catch (Exception ex) {
            try {
                CON.rollback();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
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
            PreparedStatement ps = CON.prepareStatement("INSERT INTO balance_operation(id, date_op, money, type, status) VALUES(?,?,?,?,?)");
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

    public List<BalanceOperationImpl> getAllBalanceOperations() {
        List<BalanceOperationImpl> all = new ArrayList<>();
        try {
            PreparedStatement ps = CON.prepareStatement("SELECT * FROM balance_operation");

            ResultSet resultSet = ps.executeQuery();
            while (resultSet.next()) {
                all.add(
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
            PreparedStatement ps = CON.prepareStatement("DELETE FROM balance_operation WHERE id = ?");
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
            CON.setAutoCommit(false);
            PreparedStatement ps = CON.prepareStatement("INSERT INTO return_transaction(id, date_op, money, type, status, id_product, amount,  id_sale) VALUES(?,?,?,?,?,?,?,?)");
            ps.setInt(1, returnTransaction.getBalanceId());
            ps.setDate(2, Date.valueOf(returnTransaction.getDate()));
            ps.setDouble(3, returnTransaction.getMoney());
            ps.setString(4, returnTransaction.getType());
            ps.setString(5, returnTransaction.getStatus());
            ps.setInt(6, returnTransaction.getProduct().getId());
            ps.setInt(7, returnTransaction.getAmount());
            ps.setInt(8, saleId);
            if(ps.executeUpdate() > 0) {
                ProductTypeImpl prod = returnTransaction.getProduct();
                PreparedStatement ps2 = CON.prepareStatement("UPDATE product_type SET location = ?, quantity = ?, note = ?, description = ?, barcode = ?, price = ? WHERE id = ?");
                ps2.setString(1, prod.getLocation());
                ps2.setInt(2, prod.getQuantity());
                ps2.setString(3, prod.getNote());
                ps2.setString(4, prod.getProductDescription());
                ps2.setString(5, prod.getBarCode());
                ps2.setDouble(6, prod.getPricePerUnit());
                ps2.setInt(7, prod.getId());
            }
            CON.commit();
            CON.setAutoCommit(true);
            return true;
        }
        catch (Exception ex) {
            try {
                CON.rollback();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
            ex.printStackTrace();
        }

        return false;
    }

    public boolean updateReturnTransaction(ReturnTransaction returnT, Integer saleId) {
        try {
            PreparedStatement ps = CON.prepareStatement("UPDATE return_transaction SET date_op = ?, money = ?, type = ?, status = ?, id_product = ?, amount = ?, id_sale = ? WHERE id = ?");
            ps.setDate(1, Date.valueOf(returnT.getDate()));
            ps.setDouble(2, returnT.getMoney());
            ps.setString(3, returnT.getType());
            ps.setString(4, returnT.getStatus());
            ps.setInt(5, returnT.getProduct().getId());
            ps.setInt(6, returnT.getAmount());
            ps.setInt(7, saleId);
            ps.setInt(8, returnT.getBalanceId());
            return ps.executeUpdate()>0;
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
        return false;
    }

    public boolean updateOrder(OrderImpl o) {
        try {
            PreparedStatement ps = CON.prepareStatement("UPDATE order_operation SET date_op = ?, status = ?, quantity = ?, product_code = ?, order_status = ?, price_per_unit = ? WHERE id = ?");
            ps.setDate(1, Date.valueOf(o.getDate()));
            ps.setString(2, o.getStatus());
            ps.setInt(3, o.getQuantity());
            ps.setString(4, o.getProductCode());
            ps.setString(5, o.getOrderStatus());
            ps.setDouble(6, o.getPricePerUnit());
            ps.setInt(7, o.getBalanceId());
            return ps.executeUpdate()>0;
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
        return false;
    }

    public Map<Integer, OrderImpl> getAllOrders() {
        Map<Integer, OrderImpl> all = new HashMap<>();
        try {
            PreparedStatement ps = CON.prepareStatement("SELECT * FROM order_operation");

            ResultSet resultSet = ps.executeQuery();
            while (resultSet.next()) {
                all.put(
                        resultSet.getInt("id"),
                        new OrderImpl(
                                resultSet.getInt("id"),
                                new Date( resultSet.getDate("date_op").getTime() ).toLocalDate(),
                                resultSet.getString("status"),
                                resultSet.getInt("quantity"),
                                resultSet.getString("product_code"),
                                resultSet.getString("order_status"),
                                resultSet.getDouble("price_per_unit")
                        )
                );
            }
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
        return all;
    }

    public boolean deleteOrder(OrderImpl op) {
        try {
            PreparedStatement ps = CON.prepareStatement("DELETE FROM order_operation WHERE id = ?");
            ps.setInt(1, op.getBalanceId());

            return ps.executeUpdate() > 0;
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
        return false;
    }

    public boolean createCustomer(CustomerImpl customer) {
        try {
            PreparedStatement ps = CON.prepareStatement("INSERT INTO customer(id, name, card) VALUES(?,?,?)");
            ps.setInt(1, customer.getId());
            ps.setString(2, customer.getCustomerName());
            ps.setString(3, customer.getCustomerCard());
            return ps.executeUpdate()>0;
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
        return false;
    }

    public boolean updateCustomer(CustomerImpl customer) {
        try {
            PreparedStatement ps = CON.prepareStatement("UPDATE customer SET name = ?, card = ? WHERE id = ?");
            ps.setString(1, customer.getCustomerName());
            ps.setString(2, customer.getCustomerCard());
            ps.setInt(3, customer.getId());
            return ps.executeUpdate()>0;
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
        return false;
    }

    public Map<Integer, CustomerImpl> getAllCustomers(Map<String, CustomerCardImpl> mCustomerCards) {
        Map<Integer, CustomerImpl> all = new HashMap<>();
        try {
            PreparedStatement ps = CON.prepareStatement("SELECT * FROM customer");

            ResultSet resultSet = ps.executeQuery();

            while (resultSet.next()) {
                CustomerImpl customer = new CustomerImpl(
                        resultSet.getInt("id"),
                        resultSet.getString("name")
                );
                customer.setCustomerCard(mCustomerCards.get(resultSet.getString("card")));
                all.put(
                        customer.getId(),
                        customer
                );
            }
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
        return all;
    }

    public Map<String, CustomerCardImpl> getAllCustomerCards() {
        Map<String, CustomerCardImpl> all = new HashMap<>();
        try {
            PreparedStatement ps = CON.prepareStatement("SELECT * FROM customer_card");

            ResultSet resultSet = ps.executeQuery();
            while (resultSet.next()) {
                all.put(
                        resultSet.getString("id"),
                        new CustomerCardImpl(
                                resultSet.getString("id"),
                                resultSet.getInt("points")
                        )
                );
            }
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
        return all;
    }

    public boolean deleteCustomer(CustomerImpl customer) {
        try {
            PreparedStatement ps = CON.prepareStatement("DELETE FROM customer WHERE id = ?");
            ps.setInt(1, customer.getId());

            return ps.executeUpdate() > 0;
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
        return false;
    }

    public boolean createCustomerCard(CustomerCardImpl customerCard) {
        try {
            PreparedStatement ps = CON.prepareStatement("INSERT INTO customer_card(id, points) VALUES(?,?)");
            ps.setString(1, customerCard.getCardId());
            ps.setInt(2, customerCard.getCardPoints());
            return ps.executeUpdate()>0;
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
        return false;
    }

    public boolean updateCustomerCard(CustomerCardImpl card) {
        try {
            PreparedStatement ps = CON.prepareStatement("UPDATE customer_card SET points = ? WHERE id = ?");
            ps.setInt(1, card.getCardPoints());
            ps.setString(2, card.getCardId());
            return ps.executeUpdate()>0;
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
        return false;
    }

    public boolean deleteReturnTransaction(ReturnTransaction ret, int saleId) {
            try {
                PreparedStatement ps = CON.prepareStatement("DELETE FROM return_transaction WHERE id = ?");
                ps.setInt(1, ret.getBalanceId());
                return ps.executeUpdate()>0;
            }
            catch (Exception ex) {
                ex.printStackTrace();
            }
            return false;
        }

    public boolean deleteAllTransactionProducts(int saleId) {
        try {
            PreparedStatement ps = CON.prepareStatement("DELETE FROM transaction_product WHERE id_sale = ?");
            ps.setInt(1, saleId);
            return ps.executeUpdate()>0;
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
        return false;
    }
}
