# Design Document


Authors:

Date:

Version:


# Contents

- [Design Document](#design-document)
- [Contents](#contents)
- [Instructions](#instructions)
- [High level design](#high-level-design)
- [Low level design](#low-level-design)
- [Verification traceability matrix](#verification-traceability-matrix)
- [Verification sequence diagrams](#verification-sequence-diagrams)
  - [Sequence diagram for scenario "RECORD ORDER PRODUCT"](#sequence-diagram-for-scenario-record-order-product)
  - [Sequence diagram for scenario "6.4"](#sequence-diagram-for-scenario-64)
  - [Sequence diagram for scenario "6.3"](#sequence-diagram-for-scenario-63)

# Instructions

The design must satisfy the Official Requirements document, notably functional and non functional requirements

# High level design

<discuss architectural styles used, if any>
<report package diagram>






# Low level design

- User(username, password, id)
  - Administrator, Cashier, ShopManager
- ProductType(id, description, barcode, pricePerUnit, note, Position position(optional))
- Product(id, quantity, temporaryQuantity, ProductType)
- Position(aisleNumber, rackAlphabeticIdentifier, levelNumber)
- Order(id, String productCode, int quantity, double pricePerUnit, supplier(?), status, arrival)
- Customer(id, name, CustomerCard cc)
- CustomerCard(id, points)
- SaleTransaction(id, list<TransactionProduct>, CustomerCard, points, Ticket)
- TransactionProduct(id, quantity, discount)
- Ticket(id, ReturnTransaction, Payment)
- AccountBook(List<FinancialTransaction>)
- FinancialTransaction
- Credit
- Debit
- ReturnTransaction(Ticket, list<Product, amount>, committed: true;false, Payment)
- Payment(id, )
  - CashPayment(cash, return)
  - CreditCardPayment(Credit card)
- Credit card ?
- BalanceOperation (Credit, Debit, Order, Sale, Return)
- EZShop(User logged, )



```plantuml
package Controller
package Model
package Data
package Exception
package GUI

Controller --> Model
Controller --> Data
Controller --> Exception
```

```plantuml

package it.polito.ezshop.logic {
  left to right direction
  class Shop {
    + loggedUser: User
    + reset()
+ Integer createUser(String username, String password, String role)
+ boolean deleteUser(Integer id)
+ List<User> getAllUsers()
+ User getUser(Integer id)
+ boolean updateUserRights(Integer id, String role)
+ User login(String username, String password)
+ boolean logout()
+ Integer createProductType(String description, String productCode, double pricePerUnit, String note)
+ boolean updateProduct(Integer id, String newDescription, String newCode, double newPrice, String newNote)
+ boolean deleteProductType(Integer id)
+ List<ProductType> getAllProductTypes()
+ ProductType getProductTypeByBarCode(String barCode)
+ List<ProductType> getProductTypesByDescription(String description)
+ boolean updateQuantity(Integer productId, int toBeAdded)
+ boolean updatePosition(Integer productId, String newPos)
+ Integer issueReorder(String productCode, int quantity, double pricePerUnit)
+ Integer payOrderFor(String productCode, int quantity, double pricePerUnit)
+ boolean payOrder(Integer orderId)
+ boolean recordOrderArrival(Integer orderId)
+ List<Order> getAllOrders()
+ Integer defineCustomer(String customerName)
+ boolean modifyCustomer(Integer id, String newCustomerName, String newCustomerCard)
+ boolean deleteCustomer(Integer id)
+ Customer getCustomer(Integer id)
+ List<Customer> getAllCustomers()
+ String createCard()
+ boolean attachCardToCustomer(String customerCard, Integer customerId)
+ boolean modifyPointsOnCard(String customerCard, int pointsToBeAdded)
+ Integer startSaleTransaction()
+ boolean addProductToSale(Integer transactionId, String productCode, int amount)
+ boolean deleteProductFromSale(Integer transactionId, String productCode, int amount)
+ boolean applyDiscountRateToProduct(Integer transactionId, String productCode, double discountRate)
+ boolean applyDiscountRateToSale(Integer transactionId, double discountRate)
+ int computePointsForSale(Integer transactionId)
+ boolean closeSaleTransaction(Integer transactionId)
+ boolean deleteSaleTicket(Integer ticketNumber)
+ Ticket getSaleTicket(Integer transactionId)
+ Ticket getTicketByNumber(Integer ticketNumber)
+ Integer startReturnTransaction(Integer ticketNumber)
+ boolean returnProduct(Integer returnId, String productCode, int amount)
+ boolean endReturnTransaction(Integer returnId, boolean commit)
+ boolean deleteReturnTransaction(Integer returnId)
+ double receiveCashPayment(Integer ticketNumber, double cash)
+ boolean receiveCreditCardPayment(Integer ticketNumber, String creditCard)
+ double returnCashPayment(Integer returnId)
+ double returnCreditCardPayment(Integer returnId, String creditCard)
+ boolean recordBalanceUpdate(double toBeAdded)
+ List<BalanceOperation> getCreditsAndDebits(LocalDate from, LocalDate to)
+ double computeBalance()
  }
}

package it.polito.ezshop.model {
  interface FinancialTransaction
  interface Credit
  interface Debit
  interface Payment
  class User {
    + id
    + name
    + surname
    + username
    + password
    + role
    + updateUserRights(String role)
  }
  class Product {
    + id
    + quantity
    + temporaryQuantity
    + position
    + ProductType
    + boolean updateProduct(String newDescription, String newCode, double newPrice, String newNote)
    + boolean updateQuantity(int toBeAdded)
    + boolean updatePosition(String newPos)
  }
  class ProductType{
      + id
      + barcode
      + description
      + pricePerUnit
      + discountRate
      + note
  }
  class Order {
    + id
    + ProductType
    + supplier
    + pricePerUnit
    + quantity
    + status
    + arrival
  }
  class CustomerCard {
      + id
      + points
      + Customer
      + boolean modifyPointsOnCard(int pointsToBeAdded)
  }
  class Customer {
      + id
      + name
      + surname
      + boolean modifyCustomer(String newCustomerName, String newCustomerCard)
  }
  class SaleTransaction {
      + discount
      + pints
      + Optional<Ticket>
      + List<TransactionProduct>
      + boolean applyDiscountRateToSale(double discountRate)
  }
  class ReturnTransaction {
    + Ticket
    + List<TransactionProduct>
    + committed
    + Payment
  }
  class TransactionProduct {
    + Product
    + amount
    + discountRate
    + boolean applyDiscountRateToProduct(double discountRate)
  }
  class CashPayment {
    + cash
    + return
  }
  class CreditCardPayment {
    + CreditCard
  }
  class AccountBook{
    + balance
    + List<FinancialTransaction>
  }
  class Ticket{
    + id
    + Optional<ReturnTransaction>
    + Payment
  }

  Credit --|> FinancialTransaction
  Debit --|> FinancialTransaction
  SaleTransaction --|> Credit
  ReturnTransaction --|> Debit
  Order --|> Debit

  Payment <|-- CreditCardPayment
  Payment <|-- CashPayment

AccountBook --> FinancialTransaction

Ticket --> Payment
ReturnTransaction --> Payment
CreditCardPayment --> CreditCard
SaleTransaction --> TransactionProduct
TransactionProduct --> Product
CustomerCard --> Customer
SaleTransaction --> CustomerCard

ProductType <-- Product
Order --> ProductType
SaleTransaction --> Ticket
ReturnTransaction --> TransactionProduct
}

package it.polito.ezshop.controller {
  class UserController{
    + reset()
    + Integer createUser(String username, String password, String role)
    + boolean deleteUser(Integer id)
    + List<User> getAllUsers()
    + User getUser(Integer id)
    + boolean updateUserRights(Integer id, String role)
    + User login(String username, String password)
    + updateFromModel(Object)
  }
  class ProductController {
    + Integer createProductType(String description, String productCode, double pricePerUnit, String note)
    + boolean updateProduct(Integer id, String newDescription, String newCode, double newPrice, String newNote)
    + boolean deleteProductType(Integer id)
    + List<ProductType> getAllProductTypes()
    + ProductType getProductTypeByBarCode(String barCode)
    + List<ProductType> getProductTypesByDescription(String description)
    + boolean updateQuantity(Integer productId, int toBeAdded)
    + boolean updatePosition(Integer productId, String newPos)
    + updateFromModel(Object)
  }
  class OrderController {
    + Integer issueReorder(String productCode, int quantity, double pricePerUnit)
    + Integer payOrderFor(String productCode, int quantity, double pricePerUnit)
    + boolean payOrder(Integer orderId)
    + boolean recordOrderArrival(Integer orderId)
    + List<Order> getAllOrders()
    + updateFromModel(Object)
  }
  class CustomerController {
    + Integer defineCustomer(String customerName)
    + boolean modifyCustomer(Integer id, String newCustomerName, String newCustomerCard)
    + boolean deleteCustomer(Integer id)
    + Customer getCustomer(Integer id)
    + List<Customer> getAllCustomers()
    + updateFromModel(Object)
  }
  class SaleTransactionController {
    + Integer startSaleTransaction()
    + boolean addProductToSale(Integer transactionId, String productCode, int amount)
    + boolean deleteProductFromSale(Integer transactionId, String productCode, int amount)
    + boolean applyDiscountRateToProduct(Integer transactionId, String productCode, double discountRate)
    + boolean applyDiscountRateToSale(Integer transactionId, double discountRate)
    + int computePointsForSale(Integer transactionId)
    + boolean closeSaleTransaction(Integer transactionId)
    + boolean deleteSaleTicket(Integer ticketNumber)
    + Ticket getSaleTicket(Integer transactionId)
    + Ticket getTicketByNumber(Integer ticketNumber)
    + updateFromModel(Object)
  }
  class ReturnTransactionController {
    + Integer startReturnTransaction(Integer ticketNumber)
    + boolean returnProduct(Integer returnId, String productCode, int amount)
    + boolean endReturnTransaction(Integer returnId, boolean commit)
    + boolean deleteReturnTransaction(Integer returnId)
    + updateFromModel(Object)
  }
  class PaymentController {
    + double receiveCashPayment(Integer ticketNumber, double cash)
    + boolean receiveCreditCardPayment(Integer ticketNumber, String creditCard)
    + double returnCashPayment(Integer returnId)
    + double returnCreditCardPayment(Integer returnId, String creditCard)
    + updateFromModel(Object)
  }
  class BalanceController{
    + boolean recordBalanceUpdate(double toBeAdded)
    + List<BalanceOperation> getCreditsAndDebits(LocalDate from, LocalDate to)
    + double computeBalance()
    + updateFromModel(Object)
  }
  class ControllerFactory {
    + HashMap<Class, Object> controllers
    + getController(Class): Object
  }

  ControllerFactory -down-> BalanceController
  ControllerFactory -down-> UserController
  ControllerFactory -down-> ProductController
  ControllerFactory -down-> OrderController
  ControllerFactory -down-> CustomerController
  ControllerFactory -down-> SaleTransactionController
  ControllerFactory -down-> ReturnTransactionController
  ControllerFactory -down-> PaymentController
}

it.polito.ezshop.controller --> it.polito.ezshop.model





Shop -left-> User
Shop --> ControllerFactory


package it.polito.ezshop.exception {
  class InvalidRoleException
  class InvalidPasswordException
  class InvalidProductDescriptionException
  class InvalidPricePerUnitException
  class InvalidLocationException
  class InvalidCustomerIdException
  class InvalidCustomerCardException
  class InvalidDiscountRateException
  class InvalidProductCodeException
  class InvalidQuantityException
  class InvalidPaymentException
  class InvalidCreditCardException
  class UnauthorizedException
}

```


In order to make the Class Diagram more clear, the get and set methods have been committed from the diagram. For the same reason, the links from each class of the controller package to each classes of the model package have been omitted. The relation is 1:1 since the each calls of the controller internally works with the data representation given by the model.





# Verification traceability matrix

|  | Position | Product Type| Quantity | Sale Transaction | Customer | Loyalty card| Return Transaction | Order        | Shop | User | Financial Transaction | Credit        | Debit   | Sale| Account Book |Product|
| :---: |:--------------:| :-------------:      | :---------: |:-------------:    | :-----:        | :-------------:      |:-------------:| :-------------: |:-------------:| :-------------: |:-------------:| :-------------: |:------------------:|:---:|:---:|:----:|
| FR1   || | || || ||X |X| || || | |
| FR3|X| X| ||X |X| || X|X| || || | |
| FR4 |X|X |X || || |X| X|X|X|| || | |
| FR5 || | ||X|X| ||X |X| || || | |
| FR6 ||X |X |X| X|X|X ||X |X|X || || | |
| FR7   || | || || ||X || X|| ||| |
| FR8   | || | || || |X| X|X| || | | |
|  | || | || || || || || | | |
|  | || | || || || || || | | |
|  | || | || || || || || | | |
|  | || | || || || || || | | |
|  | || | || || || || || | | |
|  | || | || || || || || | | |













# Verification sequence diagrams
\<select key scenarios from the requirement document. For each of them define a sequence diagram showing that the scenario can be implemented by the classes and methods in the design>

## Sequence diagram for scenario "6.4"
```plantuml
@startuml
"Shop" -> Shop:1 : searchProducts()
Shop -> ProductType:2 : getBarcode()
activate ProductType
return
Shop-> SaleTransaction:3 : addProducts()
SaleTransaction->Quantity: 4: setquantity()
Shop -> Shop:5: searchCustomerCard()
Shop -> LotaltyCard:6:addPoint()
Shop -> SaleTransaction:7: setPaymentType()
Shop -> FinancialTransaction:8: setAmount()
Shop -> AccountBook:9: UpdateBalance()
@enduml
```

## Sequence diagram for scenario "6.3"
```plantuml
@startuml
"Shop" -> Shop:1 : searchProducts()
Shop -> ProductType:2 : getBarcode()
activate ProductType
return
Shop-> SaleTransaction:3 : addProducts()
SaleTransaction-> Quantity:4: SetQuantity()
activate Quantity
return
Shop -> SaleTranscation:5: getDiscountrate()
Shop -> SaleTransaction:6: setPaymentType()
Shop -> FinancialTransaction:7: setAmount()
Shop -> AccountBook:8: UpdateBalance()
@enduml
```
