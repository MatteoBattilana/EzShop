package it.polito.ezshop.data;

import java.io.File;
import java.io.FileNotFoundException;
import java.sql.*;
import java.sql.Date;
import java.util.*;

public class DatabaseConnection {
    private final static String databaseFileName = "src/main/java/it/polito/ezshop/utils/database.db";
    private final static String schemaFileName = "src/main/java/it/polito/ezshop/utils/schema.sql";
    private final transient Connection CON;

    public DatabaseConnection() {
        try {
            CON = DriverManager.getConnection("jdbc:sqlite:" + databaseFileName);
            if (CON != null) {
                executeSqlScript();
            }
        } catch (SQLException cnfe) {
            throw new RuntimeException(cnfe.getMessage(), cnfe.getCause());
        }
    }

    /**
     * Method use to create the database at the startup if not present
     */
    private void executeSqlScript() {
        // Delimiter
        String delimiter = ";";

        // Create scanner
        Scanner scanner;
        try {
            scanner = new Scanner(new File(schemaFileName)).useDelimiter(delimiter);
        } catch (FileNotFoundException e1) {
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
            } catch (SQLException ignore) { } finally {
                // Release resources
                if (currentStatement != null) {
                    try {
                        currentStatement.close();
                    } catch (SQLException ignore) { }
                }
                currentStatement = null;
            }
        }
        scanner.close();
    }

    /**
     * Method used to save the user into the DB
     *
     * @param user the user that need to be saved into the DB
     * @return true if the user has been saved into the DB
     */
    public boolean createUser(User user) {
        if(user != null) {
            try {
                PreparedStatement ps = CON.prepareStatement("INSERT INTO users(id, username, password, role) VALUES(?,?,?,?)");
                ps.setInt(1, user.getId());
                ps.setString(2, user.getUsername());
                ps.setString(3, user.getPassword());
                ps.setString(4, user.getRole());
                return ps.executeUpdate() > 0;
            } catch (Exception ignored) { }
        }
        return false;
    }

    /**
     * Method used to set the role of a user
     * @param user
     * @param role the new role, it can be Cashier, ShopManager or Administrator
     * @return true if everything went ok
     */
    public boolean setUserRole(User user, String role) {
        if(user != null && role != null && !role.isEmpty() && (role.equals("Cashier") || role.equals("ShopManager") || role.equals("Administrator"))) {
            try {
                PreparedStatement ps = CON.prepareStatement("UPDATE users SET role = ? WHERE id = ?");
                ps.setString(1, role);
                ps.setInt(2, user.getId());
                return ps.executeUpdate() > 0;
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        return false;
    }

    /**
     * Method used to delete a user from the DB
     *
     * @param user to delete
     * @return true if everything went ok
     */
    public boolean deleteUser(User user) {
        if(user != null) {
            try {
                PreparedStatement ps = CON.prepareStatement("DELETE FROM users WHERE id = ?");
                ps.setInt(1, user.getId());
                return ps.executeUpdate() > 0;
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        return false;
    }

    /**
     * Get all users from the DB
     * @return the map of users
     */
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

    /**
     * Save the order in the DB
     * @param o order
     * @return true if everything went ok
     */
    public boolean createOrder(OrderImpl o) {
        if(o != null) {
            try {
                PreparedStatement ps = CON.prepareStatement("INSERT INTO order_operation(id, date_op, status, quantity, product_code, order_status, price_per_unit) VALUES(?,?,?,?,?,?,?)");
                ps.setInt(1, o.getBalanceId());
                ps.setDate(2, Date.valueOf(o.getDate()));
                ps.setString(3, o.getStatus());
                ps.setInt(4, o.getQuantity());
                ps.setString(5, o.getProductCode());
                ps.setString(6, o.getOrderStatus());
                ps.setDouble(7, o.getPricePerUnit());
                return ps.executeUpdate() > 0;
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        return false;
    }

    /**
     * Get the map of the order in the DB
     * @return the map of the orders
     */
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

    /**
     * Update a given product
     * @param prod product to update
     * @return true if everything went ok
     */
    public boolean updateProductType(ProductTypeImpl prod) {
        if (prod != null) {
            try {
                PreparedStatement ps = CON.prepareStatement("UPDATE product_type SET location = ?, quantity = ?, note = ?, description = ?, barcode = ?, price = ? WHERE id = ?");
                ps.setString(1, prod.getLocation());
                ps.setInt(2, prod.getQuantity());
                ps.setString(3, prod.getNote());
                ps.setString(4, prod.getProductDescription());
                ps.setString(5, prod.getBarCode());
                ps.setDouble(6, prod.getPricePerUnit());
                ps.setInt(7, prod.getId());
                return ps.executeUpdate() > 0;
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        return false;
    }

    /**
     * Save into the DB a new ProductType
     * @param prod
     * @return true if everything went ok
     */
    public boolean createProductType(ProductTypeImpl prod) {
        if(prod != null) {
            try {
                PreparedStatement ps = CON.prepareStatement("INSERT INTO product_type (id, location, quantity, note, description, barcode, price) VALUES(?,?,?,?,?,?,?)");
                ps.setInt(1, prod.getId());
                ps.setString(2, prod.getLocation());
                ps.setInt(3, prod.getQuantity());
                ps.setString(4, prod.getNote());
                ps.setString(5, prod.getProductDescription());
                ps.setString(6, prod.getBarCode());
                ps.setDouble(7, prod.getPricePerUnit());
                return ps.executeUpdate() > 0;
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        return false;
    }

    /**
     * Delete a product
     * @param productType to delete
     * @return true if everything went ok
     */
    public boolean deleteProductType(ProductTypeImpl productType) {
        if(productType != null) {
            try {
                PreparedStatement ps = CON.prepareStatement("DELETE FROM product_type WHERE id = ?");
                ps.setInt(1, productType.getId());
                return ps.executeUpdate() > 0;
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        return false;
    }

    /**
     * Create a sale transaction into the DB
     * @param saleT the new sale transaction to save
     * @return true if everything went ok, false otherwise
     */
    public boolean createSaleTransaction(SaleTransactionImpl saleT) {
        if(saleT != null ) {
            try {
                PreparedStatement ps = CON.prepareStatement("INSERT INTO sale_transaction(id, discount, transaction_status, date_op, type, status) VALUES(?,?,?,?,?,?)");
                ps.setInt(1, saleT.getTicketNumber());
                ps.setDouble(2, saleT.getDiscountRate());
                ps.setString(3, saleT.getTransactionStatus());
                ps.setDate(4, Date.valueOf(saleT.getDate()));
                ps.setString(5, saleT.getType());
                ps.setString(6, saleT.getStatus());
                return ps.executeUpdate() > 0;
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        return false;
    }

    /**
     * Return all sale transactions from DB
     * @param mProducts
     * @return the map of all sale transaction into the DB
     */
    public Map<Integer, SaleTransactionImpl> getAllSaleTransaction(Map<Integer, ProductTypeImpl> mProducts) {
        Map<Integer, SaleTransactionImpl> all = new HashMap<>();
        try {
            PreparedStatement ps = CON.prepareStatement("SELECT * FROM sale_transaction");

            ResultSet resultSet = ps.executeQuery();
            while (resultSet.next()) {
                int saleId = resultSet.getInt("id");
                Map<ProductTypeImpl, TransactionProduct> soldProducts = getAllBySaleId(saleId, mProducts);
                all.put(
                        resultSet.getInt("id"),
                        new SaleTransactionImpl(
                                this,
                                resultSet.getInt("id"),
                                new Date( resultSet.getDate("date_op").getTime() ).toLocalDate(),
                                resultSet.getString("type"),
                                resultSet.getString("status"),
                                soldProducts,
                                getAllReturnTransaction(saleId, mProducts, resultSet.getDouble("discount"), soldProducts),
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

    /**
     * Return all TicketEntry from a given sale transaction id
     * @param saleTransactionId id of the sale transaction
     * @param mProducts list of products
     * @return the map of all ticket entry
     */
    private Map<ProductTypeImpl, TransactionProduct> getAllBySaleId(int saleTransactionId, Map<Integer, ProductTypeImpl> mProducts) {
        Map<ProductTypeImpl, TransactionProduct> all = new HashMap<>();
        if(saleTransactionId > 0) {
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
                                    resultSet.getInt("quantity"),
                                    resultSet.getDouble("price")
                            )
                    );
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        return all;
    }

    /**
     * Get all return transactions given a sale transaction
     * @param saleTransactionId sale transaction
     * @param mProducts
     * @param saleDiscount
     * @param soldProducts
     * @return map of the return transactions
     */
    private Map<Integer, ReturnTransaction> getAllReturnTransaction(int saleTransactionId, Map<Integer, ProductTypeImpl> mProducts, double saleDiscount, Map<ProductTypeImpl, TransactionProduct> soldProducts) {
        Map<Integer, ReturnTransaction> all = new HashMap<>();
        if(saleDiscount > 0 && saleDiscount >= 0 && saleDiscount <= 1) {
            try {
                PreparedStatement ps = CON.prepareStatement("SELECT * FROM return_transaction WHERE id_sale = ?");
                ps.setInt(1, saleTransactionId);

                ResultSet resultSet = ps.executeQuery();
                while (resultSet.next()) {
                    ReturnTransaction retT = all.get(resultSet.getInt("id"));
                    if (retT == null) {
                        retT = new ReturnTransaction(
                                resultSet.getInt("id"),
                                new Date(resultSet.getDate("date_op").getTime()).toLocalDate(),
                                resultSet.getString("type"),
                                resultSet.getString("status"),
                                saleDiscount
                        );
                        all.put(
                                resultSet.getInt("id"),
                                retT
                        );
                    }

                    // Add product returned used to decrement the one in the sale transaction
                    ProductTypeImpl product = mProducts.get(resultSet.getInt("id_product"));
                    retT.addProduct(soldProducts.get(product), resultSet.getInt("amount"));
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        return all;
    }

    private boolean addProductToSale(SaleTransactionImpl transaction, TicketEntry ticket, int productId) {
        if(transaction != null && ticket != null && productId > 0) {
            try {
                PreparedStatement ps = CON.prepareStatement("INSERT INTO transaction_product(id_sale, id_product, discount, quantity, price) VALUES(?,?,?,?,?)");
                ps.setInt(1, transaction.getTicketNumber());
                ps.setInt(2, productId);
                ps.setDouble(3, ticket.getDiscountRate());
                ps.setInt(4, ticket.getAmount());
                ps.setDouble(5, ticket.getPricePerUnit());
                return ps.executeUpdate() > 0;
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        return false;
    }

    public boolean saveSaleTransaction(SaleTransactionImpl saleT) {
        if(saleT != null &createSaleTransaction(saleT)) {
            for(TransactionProduct ticket: saleT.getTicketEntries()) {
                addProductToSale(saleT, ticket, ticket.getProductType().getId());
                updateProductType(ticket.getProductType());
            }
        }
        return true;
    }

    /**
     * Delete a sale transaction
     * @param transaction
     * @return true if everything went ok, false otherwise
     */
    public boolean deleteSaleTransaction(SaleTransactionImpl transaction) {
        if(transaction != null) {
            try {
                CON.setAutoCommit(false);
                PreparedStatement ps = CON.prepareStatement("DELETE FROM sale_transaction WHERE id = ?");
                ps.setInt(1, transaction.getTicketNumber());
                if (ps.executeUpdate() > 0) {
                    // Rollback product quantities
                    for (TransactionProduct tp : transaction.getTicketEntries()) {
                        if(tp != null) {
                            ProductTypeImpl product = tp.getProductType();
                            if(product != null) {
                                product.setQuantity(product.getQuantity() + tp.getAmount());
                                if (!updateProductType(tp.getProductType())) {
                                    product.setQuantity(product.getQuantity() - tp.getAmount());
                                }
                            }
                        }
                    }
                }
                CON.commit();
                CON.setAutoCommit(true);
                return true;
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        return false;
    }

    /**
     * Update a given transaction
     * @param transaction to be updated
     * @return true if everything went ok, false otherwise
     */
    public boolean updateSaleTransaction(SaleTransactionImpl transaction) {
        if(transaction != null) {
            try {
                CON.setAutoCommit(false);
                PreparedStatement ps = CON.prepareStatement("UPDATE sale_transaction SET discount = ?, transaction_status = ?, date_op = ?,  type = ?, status = ? WHERE id = ?");
                ps.setDouble(1, transaction.getDiscountRate());
                ps.setString(2, transaction.getTransactionStatus());
                ps.setDate(3, Date.valueOf(transaction.getDate()));
                ps.setString(4, transaction.getType());
                ps.setString(5, transaction.getStatus());
                ps.setInt(6, transaction.getTicketNumber());
                if (ps.executeUpdate() > 0) {
                    for (TransactionProduct tp : transaction.getTicketEntries()) {
                        if(tp != null) {
                            PreparedStatement ps1 = CON.prepareStatement("UPDATE transaction_product SET discount = ?, price = ?, quantity = ? WHERE id_sale = ? AND id_product = ?");
                            ps1.setDouble(1, tp.getDiscountRate());
                            ps1.setDouble(2, tp.getPricePerUnit());
                            ps1.setInt(3, tp.getAmount());
                            ps1.setInt(4, transaction.getBalanceId());
                            ps1.setInt(5, tp.getProductType().getId());
                            if (ps1.executeUpdate() <= 0) {
                                CON.rollback();
                                return false;
                            }
                        }
                    }
                }
                CON.commit();
                CON.setAutoCommit(true);
                return true;
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        return false;
    }

    /**
     * Save a balance operation into the DB
     * @param operation
     * @return true if everything went ok, false otherwise
     */
    public boolean saveBalanceOperation(BalanceOperationImpl operation) {
        if(operation != null) {
            try {
                PreparedStatement ps = CON.prepareStatement("INSERT INTO balance_operation(id, date_op, money, type, status) VALUES(?,?,?,?,?)");
                ps.setInt(1, operation.getBalanceId());
                ps.setDate(2, Date.valueOf(operation.getDate()));
                ps.setDouble(3, operation.getMoney());
                ps.setString(4, operation.getType());
                ps.setString(5, operation.getStatus());
                return ps.executeUpdate() > 0;
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        return false;
    }

    /**
     * Return the all balance operations that are in the DB
     * @return list of all balance operations
     */
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

    /**
     * Delete a given balance operation
     * @param op
     * @return true if everything went ok, false otherwise
     */
    public boolean deleteBalanceOperation(BalanceOperation op) {
        if (op != null) {
            try {
                PreparedStatement ps = CON.prepareStatement("DELETE FROM balance_operation WHERE id = ?");
                ps.setInt(1, op.getBalanceId());
                return ps.executeUpdate() > 0;
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        return false;
    }

    /**
     * Save a return transaction into the DB
     * @param returnTransaction
     * @param saleId the sale transaction id
     * @return true if everything went ok, false otherwise
     */
    public boolean saveReturnTransaction(ReturnTransaction returnTransaction, Integer saleId) {
        if(returnTransaction != null && saleId > 0) {
            try {
                CON.setAutoCommit(false);
                for (Map.Entry<TransactionProduct, Integer> entry : returnTransaction.getReturns().entrySet()) {
                    PreparedStatement ps = CON.prepareStatement("INSERT INTO return_transaction(id, date_op, type, status, id_product, amount,  id_sale) VALUES(?,?,?,?,?,?,?)");
                    ps.setInt(1, returnTransaction.getBalanceId());
                    ps.setDate(2, Date.valueOf(returnTransaction.getDate()));
                    ps.setString(3, returnTransaction.getType());
                    ps.setString(4, returnTransaction.getStatus());
                    ps.setInt(5, entry.getKey().getProductType().getId());
                    ps.setInt(6, entry.getValue());
                    ps.setInt(7, saleId);
                    ps.executeUpdate();
                }
                CON.commit();
                CON.setAutoCommit(true);
                return true;
            } catch (Exception ex) {
                try {
                    CON.rollback();
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
                ex.printStackTrace();
            }
        }
        return false;
    }

    /**
     * Change status of a return transaction
     * @param returnT
     * @return true if everything went ok, false otherwise
     */
    public boolean setStatusReturnTransaction(ReturnTransaction returnT) {
        if(returnT != null) {
            try {
                PreparedStatement ps = CON.prepareStatement("UPDATE return_transaction SET status = ? WHERE id = ?");
                ps.setString(1, returnT.getStatus());
                ps.setInt(2, returnT.getBalanceId());
                return ps.executeUpdate() > 0;
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        return false;
    }

    /**
     * Update a given order
     * @param o
     * @return true if everything went ok, false otherwise
     */
    public boolean updateOrder(OrderImpl o) {
        if (o != null) {
            try {
                PreparedStatement ps = CON.prepareStatement("UPDATE order_operation SET date_op = ?, status = ?, quantity = ?, product_code = ?, order_status = ?, price_per_unit = ? WHERE id = ?");
                ps.setDate(1, Date.valueOf(o.getDate()));
                ps.setString(2, o.getStatus());
                ps.setInt(3, o.getQuantity());
                ps.setString(4, o.getProductCode());
                ps.setString(5, o.getOrderStatus());
                ps.setDouble(6, o.getPricePerUnit());
                ps.setInt(7, o.getBalanceId());
                return ps.executeUpdate() > 0;
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        return false;
    }

    /**
     * Get all orders that are in the DB
     * @return map of all orders
     */
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
                                resultSet.getString("product_code"),
                                resultSet.getDouble("price_per_unit"),
                                resultSet.getInt("quantity"),
                                resultSet.getString("status"),
                                resultSet.getString("order_status")
                        )
                );
            }
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
        return all;
    }

    /**
     * Delete an order
     * @param op
     * @return true if everything went ok, false otherwise
     */
    public boolean deleteOrder(OrderImpl op) {
        if(op != null) {
            try {
                PreparedStatement ps = CON.prepareStatement("DELETE FROM order_operation WHERE id = ?");
                ps.setInt(1, op.getBalanceId());

                return ps.executeUpdate() > 0;
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        return false;
    }

    /**
     * Save a customer into the DB
     * @param customer
     * @return true if everything went ok, false otherwise
     */
    public boolean createCustomer(CustomerImpl customer) {
        if(customer != null) {
            try {
                PreparedStatement ps = CON.prepareStatement("INSERT INTO customer(id, name, card) VALUES(?,?,?)");
                ps.setInt(1, customer.getId());
                ps.setString(2, customer.getCustomerName());
                ps.setString(3, customer.getCustomerCard());
                return ps.executeUpdate() > 0;
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        return false;
    }

    /**
     * Update a customer passed as parameter
     * @param customer
     * @return true if everything went ok, false otherwise
     */
    public boolean updateCustomer(CustomerImpl customer) {
        if(customer != null) {
            try {
                PreparedStatement ps = CON.prepareStatement("UPDATE customer SET name = ?, card = ? WHERE id = ?");
                ps.setString(1, customer.getCustomerName());
                ps.setString(2, customer.getCustomerCard());
                ps.setInt(3, customer.getId());
                return ps.executeUpdate() > 0;
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        return false;
    }

    /**
     * Return all customers in the DB
     * @param mCustomerCards
     * @return map of all customers
     */
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

    /**
     * Return all customer card
     * @return map of all customer cards
     */
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

    /**
     * Delete a given customer
     * @param customer
     * @return true if everything went ok, false otherwise
     */
    public boolean deleteCustomer(CustomerImpl customer) {
        if(customer != null) {
            try {
                PreparedStatement ps = CON.prepareStatement("DELETE FROM customer WHERE id = ?");
                ps.setInt(1, customer.getId());

                return ps.executeUpdate() > 0;
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        return false;
    }

    /**
     * Save the customer card into the DB
     * @param customerCard
     * @return true if everything went ok, false otherwise
     */
    public boolean createCustomerCard(CustomerCardImpl customerCard) {
        if(customerCard != null) {
            try {
                PreparedStatement ps = CON.prepareStatement("INSERT INTO customer_card(id, points) VALUES(?,?)");
                ps.setString(1, customerCard.getCustomer());
                ps.setInt(2, customerCard.getPoints());
                return ps.executeUpdate() > 0;
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        return false;
    }

    /**
     * Update a given customer card
     * @param card
     * @return true if everything went ok, false otherwise
     */
    public boolean updateCustomerCard(CustomerCardImpl card) {
        if(card != null) {
            try {
                PreparedStatement ps = CON.prepareStatement("UPDATE customer_card SET points = ? WHERE id = ?");
                ps.setInt(1, card.getPoints());
                ps.setString(2, card.getCustomer());
                return ps.executeUpdate() > 0;
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        return false;
    }

    /**
     * Delete from the DB a given return transaction
     * @param ret
     * @return true if everything went ok, false otherwise
     */
    public boolean deleteReturnTransaction(ReturnTransaction ret) {
        if(ret != null) {
                try {
                    PreparedStatement ps = CON.prepareStatement("DELETE FROM return_transaction WHERE id = ?");
                    ps.setInt(1, ret.getBalanceId());
                    return ps.executeUpdate() > 0;
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
            return false;
        }

    /**
     * Delete all the Ticket Entry for a given sale
     * @param saleId of the sale transaction
     * @return true if everything went ok, false otherwise
     */
    public boolean deleteAllTransactionProducts(int saleId) {
        if(saleId > 0) {
            try {
                PreparedStatement ps = CON.prepareStatement("DELETE FROM transaction_product WHERE id_sale = ?");
                ps.setInt(1, saleId);
                return ps.executeUpdate() > 0;
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        return false;
    }

    /**
     * Update the insternal balance
     * @param newBalance the new balance
     * @return true if everything went ok, false otherwise
     */
    public boolean updateBalance(double newBalance) {
        deleteBalance();
        try {
            PreparedStatement ps = CON.prepareStatement("INSERT INTO balance(money) VALUES(?)");
            ps.setDouble(1, newBalance);
            return ps.executeUpdate()>0;
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
        return false;
    }

    private void deleteBalance(){
        try {
            PreparedStatement ps = CON.prepareStatement("DELETE FROM balance");
            ps.executeUpdate();
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Get the balance from the DB
     * @return the current saved balance in the DB
     */
    public double getBalance() {
        try {
            PreparedStatement ps = CON.prepareStatement("SELECT money FROM balance");
            ResultSet resultSet = ps.executeQuery();
            if(resultSet.next()){
                return resultSet.getDouble("money");
            }
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
        return 0.0;
    }
}
