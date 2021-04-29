# Design Document


Authors: Battilana Matteo, Huang Chunbiao, Mondal Subhajit, Sabatini Claudia

Date: 26/04/2021

Version: 1.0


# Contents

- [Design Document](#design-document)
- [Contents](#contents)
- [Instructions](#instructions)
- [High level design](#high-level-design)
- [Low level design](#low-level-design)
- [Verification traceability matrix](#verification-traceability-matrix)
- [Verification sequence diagrams](#verification-sequence-diagrams)
  - [Sequence diagram for scenario 1.1](#sequence-diagram-for-scenario-11)
  - [Sequence diagram for scenario 2.1](#sequence-diagram-for-scenario-21)
  - [Sequence diagram for scenario 2.3](#sequence-diagram-for-scenario-23)
  - [Sequence diagram for scenario 3.1](#sequence-diagram-for-scenario-31)
  - [Sequence diagram for scenario 3.2](#sequence-diagram-for-scenario-32)
  - [Sequence diagram for scenario 3.3](#sequence-diagram-for-scenario-33)
  - [Sequence diagram for scenario 4.1](#sequence-diagram-for-scenario-41)
  - [Sequence diagram for scenario 4.3](#sequence-diagram-for-scenario-43)
  - [Sequence diagram for scenario 6.1 - scenario 7.4](#sequence-diagram-for-scenario-61-scenario-74)
  - [Sequence diagram for scenario 8.1 - scenario 7.4](#sequence-diagram-for-scenario-81-scenario-74)
  - [Sequence diagram for scenario 9.1](#sequence-diagram-for-scenario-91)


# Instructions

The design must satisfy the Official Requirements document, notably functional and non functional requirements

# High level design

<discuss architectural styles used, if any>
<report package diagram>






# Low level design


```plantuml
package it.polito.ezshop.model
package it.polito.ezshop.data
package it.polito.ezshop.exception
package it.polito.ezshop.gui

it.polito.ezshop.data --> it.polito.ezshop.model
it.polito.ezshop.data --> it.polito.ezshop.exception
```

```plantuml

  left to right direction
package it.polito.ezshop.data {

    class SingletonDatabaseConnection {
      - dbUrl: String
      + getInstance(): SingletonDatabaseConnection
      + getConnection(): Connection
    }
  class CreditCardManager {
    + validateCreditCart(creditCardId: String): Boolean
    + pay(creditCardId: String, amount: Double): Boolean
  }
  class Shop {
    - allUsers: Map<Integer, User>
    - allSales: Map<Integer, SaleTransaction>
    - products: Map<Integer, ProductType>
    - orders: Map<Integer, Order>
    - customers: Map<Integer, Customer>
    - customerCards: Map<String, CustomerCard>
    + reset(): Boolean
+ createUser(username: String, password: String, role: String): Integer
+ deleteUser(id: Integer): Boolean
+ getAllUsers(): List<User>
+ getUser(id: Integer): User
+ updateUserRights(id: Integer, role: String): Boolean
+ login(username: String, password: String): User
+ logout(): Boolean
+ createProductType(description: String, productCode: String, pricePerUnit: Double, note: String): Integer
+ updateProduct(id: Integer, newDescription: String, newCode: String, newPrice: Double, newNote: String): Boolean
+ deleteProductType(id: Integer): Boolean
+ getAllProductTypes(): List<ProductType>
+ getProductTypeByBarCode(barCode: String): ProductType
+ getProductTypesByDescription(description: String): List<ProductType>
+ updateQuantity(productId: Integer, toBeAdded: Integer): Boolean
+ updatePosition(productId: Integer, newPos: String): Boolean
+ issueOrder(productCode: String, quantity: Integer, pricePerUnit: Double): Integer
+ payOrderFor(productCode: String, quantity: Integer, pricePerUnit: Double): Integer
+ payOrder(orderId: Integer): Boolean
+ recordOrderArrival(orderId: Integer): Boolean
+ getAllOrders(): List<Order>
+ defineCustomer(customerName: String): Integer
+ modifyCustomer(id: Integer, newCustomerName: String, newCustomerCard: String): Boolean
+ deleteCustomer(id: Integer): Boolean
+ getCustomer(id: Integer): Customer
+ getAllCustomers(): List<Customer>
+ createCard(): String
+ attachCardToCustomer(customerCard: String, customerId: Integer): Boolean
+ modifyPointsOnCard(customerCard: String, pointsToBeAdded: Integer): Boolean
+ startSaleTransaction(): Integer
+ addProductToSale(transactionId: Integer, productCode: String, amount: Integer): Boolean
+ deleteProductFromSale(transactionId: Integer, productCode: String, amount: Integer): Boolean
+ applyDiscountRateToProduct(transactionId: Integer, productCode: String, discountRate: Double): Boolean
+ applyDiscountRateToSale(transactionId: Integer, discountRate: Double): Boolean
+ computePointsForSale(transactionId: Integer): Integer
+ endSaleTransaction(transactionId: Integer): Boolean
+ deleteSaleTransaction(transactionID: Integer): Boolean
+ getSaleTransaction(transactionId: Integer): Ticket
+ getTicketByNumber(transactionID: Integer): Ticket
+ startReturnTransaction(transactionID: Integer): Integer
+ returnProduct(returnId: Integer, productCode: String, amount: Integer): Boolean
+ endReturnTransaction(returnId: Integer, Boolean commit): Boolean
+ deleteReturnTransaction(returnId: Integer): Boolean
+ receiveCashPayment(transactionID: Integer, cash: Double): Double
+ receiveCreditCardPayment(transactionID: Integer, creditCard: String): Boolean
+ returnCashPayment(returnId: Integer): Double
+ returnCreditCardPayment(returnId: Integer, creditCard: String): Double
+ recordBalanceUpdate(toBeAdded: Double): Boolean
+ getCreditsAndDebits(LocalDate from, LocalDate to): List<BalanceOperation>
+ computeBalance(): Double
- getProductByBarcode(barcode: String): ProductType
- getSaleTransactionByReturnTransactionId(id: Integer): SaleTransaction
- loadFromDb(): Boolean
  }
  Shop -[hidden]-> SingletonDatabaseConnection

    Shop -- CreditCardManager
    Shop -[hidden]-> CreditCardManager
}

package it.polito.ezshop.model {

  note "All classes in the model package\nare persistent" as N1
  abstract class BalanceOperation {
    - amount: Dobule
    - description: String
    - date: LocalDate
    - type: String
  }
  class User {
    - id: Integer
    - name: String
    - surname: String
    - username: String
    - password: String
    - role: String
    + updateUserRights(role: String)
  }

  class ProductType{
      - id: Integer
      - barcode: String
      - description: String
      - pricePerUnit: Double
      - discountRate: Double
      - position: String
      - note: String
      - quantity: Integer
      - temporaryQuantity: Integer
      + updateProduct(newDescription: String, newCode: String, newPrice: Double, newNote: String): Boolean
      + updatePosition(newPos: String): Boolean
      + updateQuantity(toBeAdded: Integer): Boolean
      + updateTemporaryQuantity(toBeAdded: Integer): Boolean
      + commitTemporaryQuantity(): Boolean
  }
  class Order {
    - id: Integer
    - supplier: String
    - pricePerUnit: Double
    - quantity: Integer
    - status: String
    - arrival: LocalDate
    + recordOrderArrival(): Boolean
  }
  class CustomerCard {
      - id: String
      - points: Integer
      + setCustomer(customer: Customer)
      + removeCustomer()
      + modifyPointsOnCard(pointsToBeAdded: Integer): Boolean
  }
  class Customer {
      - id: Integer
      - name: String
      + modifyCustomer(newCustomerName: String, newCustomerCard: String): Boolean
  }
  class SaleTransaction {
      - id: Integer
      - discount: Dobule
      - points: Integer
      - returnTransactions: Map<Integer, ReturnTransaction>
      - prodList: Map<ProductType, TransactionProduct>
      - status: String
      - customerCard: CustomerCard
      + setCustomerCard(CustomerCard): Boolean
      + addProductToSale(product: ProductType, amount: Integer): Boolean
      + deleteProductFromSale(product: String, amount: Integer): Boolean
      + applyDiscountRateToProduct(product: ProductType, discountRate: Double): Boolean
      + applyDiscountRateToSale(discountRate: Double): Boolean
      + computePointsForSale(): Integer
      + endSaleTransaction(): Boolean
      + startReturnTransaction(): ReturnTransaction
      + deleteReturnTransaction(): Boolean
      + getSoldQuantity(product: ProductType): Integer
      + computeTotal(): Double
      + endReturnTransaction(id: Integer, Boolean commit): Boolean
      + deleteReturnTransaction(id: Integer): Boolean
      + setReturnProduct(returnId: Integer, product: ProductType, amount: Integer): Boolean
      + getReturnTransactionStatus(id: Integer): Boolean
      + getReturnTransactionTotal(id: Integer): Boolean
      + getReturnTransaction(id: Integer): ReturnTransaction
      + commitAllTemporaryQuantity()
  }
  class ReturnTransaction {
    - id: Integer
    - quantity: Integer
    - committed: String
    - status: Boolean
    + updateProductQuantity(): Boolean
    + computeTotal(): Double
  }
  class TransactionProduct {
    - quantity: Integer
    - discountRate: Double
    + applyDiscountRateToProduct(discountRate: Double): Boolean
  }

  class AccountBook{
    - balance: Double
    - opList: List<BalanceOperation>
    + recordBalanceUpdate(toBeAdded: Double): Boolean
    + add(BalanceOperation): Boolean
    + getCreditsAndDebits(LocalDate from, LocalDate to): List<BalanceOperation>
    + computeBalance(): Double
  }


  ReturnTransaction --|> BalanceOperation
  SaleTransaction --|> BalanceOperation
  Order -up-|> BalanceOperation

AccountBook --"*" BalanceOperation

SaleTransaction -right-"*" ReturnTransaction
SaleTransaction --"*" TransactionProduct
TransactionProduct "*" -- ProductType
CustomerCard "0..1"-- Customer
SaleTransaction -- "0..1" CustomerCard

Order -right- ProductType
ReturnTransaction -- ProductType
}

  Shop - "*" User
  Shop - "*" SaleTransaction
  Shop - "*" ProductType
  Shop - "*" Order
  Shop - "*" Customer
  Shop - "*" CustomerCard
  Shop - AccountBook



```
```plantuml
package it.polito.ezshop.exception {
class InvalidUsernameException
class InvalidPasswordException
class InvalidRoleException

class InvalidUserIdException
class UnauthorizedException

class InvalidProductDescriptionException
class InvalidProductCodeException
class InvalidPricePerUnitException
class InvalidProductIdException
class InvalidLocationException
class InvalidQuantityException

class InvalidOrderIdException
class InvalidCustomerNameException
class InvalidCustomerIdException
class InvalidCustomerCardException

class InvalidDiscountRateException
class InvalidTransactionIdException
class InvalidtransactionIDException

class InvalidCreditCardException
}


```

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


## Sequence diagram for scenario 1.1
```plantuml
@startuml
->Shop: 1: createProductType()
activate Shop
Shop -> ProductType: 2: <<create>>

activate ProductType

ProductType ->ProductType:   3: setBarcode()

ProductType ->ProductType:   4: setPricePerUnit()

ProductType ->ProductType:   5: setNote()
ProductType ->ProductType:   6: setDescription()
return instance
return
->Shop: 7: updatePosition()
activate Shop
Shop -> ProductType: 8: setPosition()
activate ProductType
return
return
@enduml
```


## Sequence diagram for scenario 2.1
```plantuml
@startuml
->Shop: 1: createUser()
activate Shop
Shop -> User: 2: <<create>>

activate User

User ->User:   3: setUsername()

User ->User:   4: setPassword()

User ->User:   5: setRole()
return instance
return
@enduml
```

## Sequence diagram for scenario 2.3
```plantuml
@startuml
->Shop: 1: updateUserRights()
activate Shop
  Shop -> User: 2: setRole()
  activate User
  return
return
@enduml
```

## Sequence diagram for scenario 3.1
```plantuml
@startuml
->Shop: 1: issueOrder()
activate Shop
  Shop -> Order: 2: <<create>>
  activate Order
    Order -> Order: 3: setQuantity()
    Order -> Order: 4: setPricePerUnit()
    Order -> Order: 5: setStatus()
  return instance
return
@enduml
```

## Sequence diagram for scenario 3.2
```plantuml
@startuml
->Shop: 1: payOrder()
activate Shop
    Shop -> Order: 2: setStatus()
    activate Order
    return
  Shop --> AccountBook: 3: recordBalanceUpdate()
  activate AccountBook
  return

  Shop --> AccountBook: 5: add()
  activate AccountBook
  return
return
@enduml
```

## Sequence diagram for scenario 3.3
```plantuml
@startuml
->Shop: 1: recordOrderArrival()
activate Shop
    Shop -> Order: 2: setStatus()
    activate Order
      Order -> ProductType: 3 updateQuantity()
      activate ProductType
      return
    return
return
@enduml
```


## Sequence diagram for scenario 4.1
```plantuml
@startuml
->Shop: 1: defineCustomer()
activate Shop
    Shop -> Customer: 2: <<create>>
    activate Customer
      Customer -> Customer: 3: setName()
    return: instance
return
@enduml
```

## Sequence diagram for scenario 4.3
```plantuml
@startuml
->Shop: 1: modifyCustomer()
activate Shop
    Shop -> CustomerCard: 2: <<create>>
    activate CustomerCard
      CustomerCard -> CustomerCard: 3: removeCustomer()
    return
return
@enduml
```


## Sequence diagram for scenario 6.1 - scenario 7.4
```plantuml
@startuml
  ->Shop: 1: startSaleTransaction()
  activate Shop
    Shop -> SaleTransaction: 2: <<create>>
    activate SaleTransaction
    return
  return

  ->Shop: 3: addProductToSale()
  activate Shop
    Shop -> Shop: 4: getProductByBarcode()
    Shop -> SaleTransaction: 5: addProductToSale()
    activate SaleTransaction
      SaleTransaction -> ProductType: 6: setTemporaryQuantity()
      activate ProductType
      return
    return
return

-> Shop: 7: endSaleTransaction()
activate Shop
  Shop -> SaleTransaction: 8: endSaleTransaction()
  activate SaleTransaction
  return
return

-> Shop: 9: receiveCashPayment()
activate Shop
  Shop -> SaleTransaction: 10: computeTotal()
  activate SaleTransaction
      loop prodList
          SaleTransaction -> TransactionProduct: 11: getDiscountRate()
          activate TransactionProduct
          return
          SaleTransaction -> TransactionProduct: 12: getQuantity()
          activate TransactionProduct
          return
      end
  return
  Shop -> AccountBook: 13: add()
  activate AccountBook
  return


Shop -> AccountBook: 14: updateBalance()
activate AccountBook
return

  Shop -> SaleTransaction: 15: commitAllTemporaryQuantity()
  activate SaleTransaction

    loop prodList
    SaleTransaction -> ProductType: 16: commitTemporaryQuantity()
    activate ProductType
    return

    end
  return

return
@enduml
```

## Sequence diagram for scenario 8.1 - scenario 7.4
```plantuml
@startuml
->Shop: 1: startReturnTransaction()
activate Shop
  Shop -> SaleTransaction: 2: startReturnTransaction()
  activate SaleTransaction
    SaleTransaction -> ReturnTransaction: 3: <<create>>
    activate ReturnTransaction
    return : instance
  return
return

->Shop: 4: returnProduct()
  activate Shop
    Shop -> Shop: 5: getProductByBarcode()
    Shop -> Shop: 6: getSaleTransactionByReturnTransactionId()
    Shop -> SaleTransaction: 7: getSoldQuantity()
    activate SaleTransaction
    return


    Shop -> SaleTransaction: 8: setReturnProduct()
    activate SaleTransaction
          SaleTransaction -> ReturnTransaction: 9: setProduct()
          activate ReturnTransaction
          return
          SaleTransaction -> ReturnTransaction: 10: quantity()
          activate ReturnTransaction
          return
    return
return





-> Shop: 11: returnCashPayment()
activate Shop
    Shop -> Shop: 12: getSaleTransactionByReturnTransactionId()

    Shop -> SaleTransaction: 13: getReturnTransactionStatus()
    activate SaleTransaction
    return

  Shop -> SaleTransaction: 14: getReturnTransactionTotal()
    activate SaleTransaction
     SaleTransaction -> ReturnTransaction: 15: computeTotal()
      activate ReturnTransaction
      return
    return

   Shop -> AddressBook: 16: recordBalanceUpdate()
   activate AddressBook
   return
   Shop -> SaleTransaction: 17: getReturnTransaction()
   activate SaleTransaction
   return
   Shop -> AddressBook: 18: add()
   activate AddressBook
   return
return
-> Shop: 19: endReturnTransaction()
activate Shop
    Shop -> Shop: 20: getSaleTransactionByReturnTransactionId()
          Shop -> SaleTransaction: 21: endReturnTransaction()
          activate SaleTransaction

                   SaleTransaction -> ReturnTransaction: 22: updateProductQuantity()
                   activate ReturnTransaction
                   ReturnTransaction -> ProductType: 23: setQuantity()
                   activate ProductType
                   return


                 return

          return
return

@enduml
```


## Sequence diagram for scenario 9.1
```plantuml
@startuml
->Shop: 1: getCreditsAndDebits()
activate Shop
    Shop -> AccountBook: 2: getCreditsAndDebits()
    activate AccountBook
    return
return
@enduml
```
