# Integration and API Test Documentation

Authors: Battilana Matteo, Huang Chunbiao, Mondal Subhajit, Sabatini Claudia

Date: 18/05/2021

Version: 1.0

# Contents

- [Dependency graph](#dependency-graph)

- [Integration and API Test Documentation](#integration-and-api-test-documentation)
- [Contents](#contents)
- [Dependency graph](#dependency-graph)
- [Integration approach](#integration-approach)
- [Tests](#tests)
  - [Step 1](#step-1)
  - [Step 2](#step-2)
  - [Step 3](#step-3)
  - [Step 4](#step-4)
  - [Step 5](#step-5)
  - [Step 6 - API testing](#step-6---api-testing)
- [Scenarios](#scenarios)
        - [Scenario 1-4](#scenario-1-4)
        - [Scenario 2-4](#scenario-2-4)
        - [Scenario 3-4](#scenario-3-4)
        - [Scenario 6-7](#scenario-6-7)
        - [Scenario 8-3](#scenario-8-3)
- [Coverage of Scenarios and FR](#coverage-of-scenarios-and-fr)
- [Coverage of Non Functional Requirements](#coverage-of-non-functional-requirements)

- [Tests](#tests)

- [Scenarios](#scenarios)

- [Coverage of scenarios and FR](#scenario-coverage)
- [Coverage of non-functional requirements](#nfr-coverage)



# Dependency graph

     <report the here the dependency graph of the classes in EzShop, using plantuml>


```plantuml

         class DatabaseConnection {}

       Shop -[hidden]-> DatabaseConnection

         Shop -- CreditCardCircuit
         Shop -[hidden]-> CreditCardCircuit


                DatabaseConnection --> AccountBook
                DatabaseConnection <-- Shop
                DatabaseConnection <-- SaleTransactionImpl

     AccountBook --> BalanceOperationImpl

     SaleTransactionImpl --> ReturnTransactionImpl
     SaleTransactionImpl --> TransactionProduct
     TransactionProduct --> ProductTypeImpl
     CustomerCard <-- CustomerImpl

     ReturnTransactionImpl -> TransactionProduct


       Shop -> UserImpl
       Shop -> SaleTransactionImpl
       Shop -> ProductTypeImpl
       Shop -> OrderImpl
       Shop -> CustomerImpl
       Shop -> CustomerCard
       Shop -> AccountBook
```

# Integration approach

    <Write here the integration sequence you adopted, in general terms (top down, bottom up, mixed) and as sequence
    (ex: step1: class A, step 2: class A+B, step 3: class A+B+C, etc)>
    <Some steps may  correspond to unit testing (ex step1 in ex above), presented in other document UnitTestReport.md>
    <One step will  correspond to API testing>


We adopted a bottom up approach divided in the following steps:

|Step  |Tested classes |
|:-----:|:----|
|1                 |BalanceOperationImpl                    |
|                  |CreditCardCircuit                 |
|                  |CustomerCardImpl                    |
|                  |ProductTypeImpl                |
|                  |UserImpl                |
|                  |OrderImpl                |
|2                 |TransactionProduct + ProductTypeImpl                    |
||OrderImplAdapter + OrderImpl|
||AccountBook + BalanceOperationImpl|
|3|ReturnTransaction + TransactionProduct |
|4|SaleTransactionImpl + ProductTypeImpl + TransactionProduct + ReturnTransaction |
|5|DatabaseConnection + UserImpl + OrderImpl + ProductTypeImpl + SaleTransactionImpl + ReturnTransactionImpl + CustomerCardImpl + CustomerImpl |
|6|EZShop + DatabaseConnection + UserImpl + OrderImpl + ProductTypeImpl + SaleTransactionImpl + ReturnTransactionImpl + CustomerCardImpl + CustomerImpl |


#  Tests

   <define below a table for each integration step. For each integration step report the group of classes under test, and the names of
     JUnit test cases applied to them> JUnit test classes should be here src/test/java/it/polito/ezshop

## Step 1
**This step is about Unit Testing, the documented can be found [here](./UnitTestReport.md).**

## Step 2
| Classes  | JUnit test cases |
|--|--|
| TransactionProduct + ProductTypeImpl |TransactionProductTest.testProductType() |
||TransactionProductTest.testPositiveSetBarCode() |
||TransactionProductTest.testNullSetBarCode() |
||TransactionProductTest.testInvalidSetBarCode() |
||TransactionProductTest.testInvalidSetProductDescription() |
||TransactionProductTest.testNullSetProductDescription() |
||TransactionProductTest.testPositiveSetProductDescription() |
||TransactionProductTest.testNegativeSetAmount() |
||TransactionProductTest.testPositiveSetAmount() |
||TransactionProductTest.testPricePerUnit() |
||TransactionProductTest.testDiscountRate() |
||TransactionProductTest.testApplyDiscountRateToProduct() |
| OrderImplAdapter + OrderImpl |OrderImplAdapterTest.testPositiveBalanceId() |
||OrderImplAdapterTest.testNegativeBalanceId() |
||OrderImplAdapterTest.testSetProductCode() |
||OrderImplAdapterTest.testSetPricePerUnit() |
||OrderImplAdapterTest.testSetQuantity() |
||OrderImplAdapterTest.testSetStatus() |
||OrderImplAdapterTest.testNegativeSetOrderId() |
||OrderImplAdapterTest.testPositiveSetOrderId() |
|AccountBook + BalanceOperationImpl |AccountBookTest.testUnpaidBalanceOperation() |
||AccountBookTest.testPaidBalanceOperation() |
||AccountBookTest.testNullBalanceOperation() |
||AccountBookTest.testUpdatedBalance() |
||AccountBookTest.testNotUpdatedBalance() |
||AccountBookTest.testComputeBalance() |
||AccountBookTest.testOpListReset() |
||AccountBookTest.testNullOpListReset() |

## Step 3

| Classes  | JUnit test cases |
|--|--|
|ReturnTransaction + TransactionProduct|ReturnTransactionTest.testAddProduct() |
||ReturnTransactionTest.testGetMoney() |
||ReturnTransactionTest.testComputeTotal() |
||ReturnTransactionTest.testGetReturns() |

## Step 4

| Classes  | JUnit test cases |
|--|--|
|SaleTransactionImpl + ProductTypeImpl + TransactionProduct + ReturnTransaction|ReturnTransactionTest.testAddProduct() |SaleTransactionImplTest.testSecondConstructor() |
||SaleTransactionImplTest.testStartReturnTransaction() |
||SaleTransactionImplTest.testGetReturnTransactions() |
||SaleTransactionImplTest.testAddProductToSale() |
||SaleTransactionImplTest.testSetReturnProduct() |
||SaleTransactionImplTest.testSetTransactionStatus() |
||SaleTransactionImplTest.testSetTicketNumber() |
||SaleTransactionImplTest.testSetDiscountRate() |
||SaleTransactionImplTest.testGetMoney() |
||SaleTransactionImplTest.testGetPrice() |
||SaleTransactionImplTest.testComputeTotal() |
||SaleTransactionImplTest.testDeleteProductFromSale() |
||SaleTransactionImplTest.testComputePointsForSale() |
||SaleTransactionImplTest.testGetReturnTransaction() |
||SaleTransactionImplTest.testApplyDiscountRateToProduct() |
||SaleTransactionImplTest.testDeleteReturnTransaction() |
||SaleTransactionImplTest.testGetReturnTransactionTotal() |
||SaleTransactionImplTest.testEndReturnTransaction() |
||SaleTransactionImplTest.testSetPaidReturnTransaction() |
||SaleTransactionImplTest.testReset() |
||SaleTransactionImplTest.testEndSaleTransaction() |
||SaleTransactionImplTest.testGetSoldQuantity() |
||SaleTransactionImplTest.testGetTicketEntries() |
||SaleTransactionImplTest.testDummySetters() |
||SaleTransactionImplTest.testGetEntries() |


## Step 5

| Classes  | JUnit test cases |
|--|--|
|	DatabaseConnection + UserImpl + OrderImpl + ProductTypeImpl + SaleTransactionImpl + ReturnTransactionImpl + CustomerCardImpl + CustomerImpl | DatabaseConnectionTest.testExecuteStartUpMultipleTimes() |
||DatabaseConnectionTest.testExecuteStartUpInvalidFile() |
||DatabaseConnectionTest.testCreateUser() |
||DatabaseConnectionTest.testSetUserRole() |
||DatabaseConnectionTest.testSetWrongRole() |
||DatabaseConnectionTest.testDeleteUser() |
||DatabaseConnectionTest.testGetAllUsers() |
||DatabaseConnectionTest.testCreateOrder() |
||DatabaseConnectionTest.testGetAllProducts() |
||DatabaseConnectionTest.testCreateProductType() |
||DatabaseConnectionTest.testUpdateProductType() |
||DatabaseConnectionTest.testDeleteProductType() |
||DatabaseConnectionTest.testSaveSaleTransaction() |
||DatabaseConnectionTest.testGetAllSaleTransaction() |
||DatabaseConnectionTest.testDeleteSaleTransaction() |
||DatabaseConnectionTest.testUpdateSaleTransactionWithoutProducts() |
||DatabaseConnectionTest.testUpdateSaleTransaction() |
||DatabaseConnectionTest.testSaveBalanceOperation() |
||DatabaseConnectionTest.testGetAllBalanceOperations() |
||DatabaseConnectionTest.testGetAllReturnTransaction() |
||DatabaseConnectionTest.testDeleteBalanceOperation() |
||DatabaseConnectionTest.testUpdateOrder() |
||DatabaseConnectionTest.testGetAllOrders() |
||DatabaseConnectionTest.testDeleteOrder() |
||DatabaseConnectionTest.testCreateCustomer() |
||DatabaseConnectionTest.testUpdateCustomer() |
||DatabaseConnectionTest.testGetAllCustomers() |
||DatabaseConnectionTest.testGetAllCustomerCards() |
||DatabaseConnectionTest.testDeleteCustomer() |
||DatabaseConnectionTest.testCreateCustomerCard() |
||DatabaseConnectionTest.testUpdateCustomerCard() |
||DatabaseConnectionTest.testBalance() |
||DatabaseConnectionTest.testAddProductToSale() |


## Step 6 - API testing

| Classes  | JUnit test cases |
|--|--|
| EZShop + DatabaseConnection + UserImpl + OrderImpl + ProductTypeImpl + SaleTransactionImpl + ReturnTransactionImpl + CustomerCardImpl + CustomerImpl |EZShopTest.testCreateProduct() |
||EZShopTest.testNotLoggedCreateProduct() |
||EZShopTest.testWrongParametersCreateProduct() |
||EZShopTest.testUpdateLocation() |
||EZShopTest.testWrongLoginUpdateLocation() |
||EZShopTest.testWrongParametersUpdateLocation() |
||EZShopTest.testCreateUser() |
||EZShopTest.testWrongParametersCreateUser() |
||EZShopTest.testDeleteUser() |
||EZShopTest.testWrongLoginDeleteUser() |
||EZShopTest.testWrongParametersDeleteUser() |
||EZShopTest.testGetAllUsers() |
||EZShopTest.testWrongLoginGetAllUsers() |
||EZShopTest.testGetUser() |
||EZShopTest.testWrongLoginGetUser() |
||EZShopTest.testUpdateUserRights() |
||EZShopTest.testWrongLoginUpdateUserRights() |
||EZShopTest.testWrongParametersUpdateUserRights() |
||EZShopTest.testWrongParametersGetUser() |
||EZShopTest.testLoginUsernameNull() |
||EZShopTest.testLoginUsernameEmpty() |
||EZShopTest.testLoginPasswordNull() |
||EZShopTest.testLoginPasswordEmpty() |
||EZShopTest.testLoginWrongPassword() |
||EZShopTest.testLogin() |
||EZShopTest.testLogout() |
||EZShopTest.testUpdateProduct() |
||EZShopTest.testWrongLoginUpdateProduct() |
||EZShopTest.testWrongParamentersUpdateProduct() |
||EZShopTest.testDeleteProductType() |
||EZShopTest.testWrongLoginDeleteProductType() |
||EZShopTest.testWrongParametersDeleteProductType() |
||EZShopTest.testGetAllProductTypes() |
||EZShopTest.testGetProductTypeByBarCode() |
||EZShopTest.testWrongLoginGetProductTypeByBarCode() |
||EZShopTest.testWrongParameterGetProductTypeByBarCode() |
||EZShopTest.testGetProductTypesByDescription() |
||EZShopTest.testUpdateQuantity() |
||EZShopTest.testWrongLoginUpdateQuantity() |
||EZShopTest.testWrongParametersUpdateQuantity() |
||EZShopTest.testWrongParametersIssueOrder() |
||EZShopTest.testWrongLoginIssueOrder() |
||EZShopTest.testIssueOrder() |
||EZShopTest.testPayOrderFor() |
||EZShopTest.testWrongParametersPayOrderFor() |
||EZShopTest.testWrongLoginPayOrderFor() |
||EZShopTest.testNegativeBalancePayOrderFor() |
||EZShopTest.testPayOrder() |
||EZShopTest.testWrongLoginPayOrder() |
||EZShopTest.testNegativeBalancePayOrder() |
||EZShopTest.testWrongLogicPayOrder() |
||EZShopTest.testAlreadyPayedPayOrder() |
||EZShopTest.testWrongParametersPayOrder() |
||EZShopTest.testRecordOrderArrival() |
||EZShopTest.testLocationNotSetRecordOrderArrival() |
||EZShopTest.testOrderNotPayedRecordOrderArrival() |
||EZShopTest.testWrongLoginRecordOrderArrival() |
||EZShopTest.testGetAllOrders() |
||EZShopTest.testWrongLoginGetAllOrders() |
||EZShopTest.testDefineCustomer() |
||EZShopTest.testWrongLoginDefineCustomer() |
||EZShopTest.testWrongParametersDefineCustomer() |
||EZShopTest.testModifyCustomer() |
||EZShopTest.testCardNotPresentModifyCustomer() |
||EZShopTest.testDeleteCardModifyCustomer() |
||EZShopTest.testDeleteCardModifyCustomer2() |
||EZShopTest.testWrongLoginModifyCustomer() |
||EZShopTest.testWrongParametersModifyCustomer() |
||EZShopTest.testDeleteCustomer() |
||EZShopTest.testWrongLoginDeleteCustomer() |
||EZShopTest.testWrongParametersDeleteCustomer() |
||EZShopTest.testGetCustomer() |
||EZShopTest.testWrongLoginGetCustomer() |
||EZShopTest.testWrongParametersGetCustomer() |
||EZShopTest.testGetAllCustomers() |
||EZShopTest.testWrongLoginGetAllCustomers() |
||EZShopTest.testStartSaleTransaction() |
||EZShopTest.testWrongLoginStartSaleTransaction() |
||EZShopTest.testAddProductToSale() |
||EZShopTest.testWrongLoginAddProductToSale() |
||EZShopTest.testWrongParametersAddProductToSale() |
||EZShopTest.testDeleteProductToSale() |
||EZShopTest.testWrongLoginDeleteProductToSale() |
||EZShopTest.testWrongParametersDeleteProductToSale() |
||EZShopTest.testApplyDiscountRateToProduct() |
||EZShopTest.testWrongLoginApplyDiscountRateToProduct() |
||EZShopTest.testWrongParametersApplyDiscountRateToProduct() |
||EZShopTest.testApplyDiscountRateToSale() |
||EZShopTest.testWrongLoginApplyDiscountRateToSale() |
||EZShopTest.testWrongParameterApplyDiscountRateToSale() |
||EZShopTest.testComputePointsForSale() |
||EZShopTest.testWrongLoginComputePointsForSale() |
||EZShopTest.testWrongParametersComputePointsForSale() |
||EZShopTest.testEndSaleTransaction() |
||EZShopTest.testWrongLoginEndSaleTransaction() |
||EZShopTest.testWrongParametersEndSaleTransaction() |
||EZShopTest.testDeleteSaleTransaction() |
||EZShopTest.testWrongLoginDeleteSaleTransaction() |
||EZShopTest.testWrongParametersDeleteSaleTransaction() |
||EZShopTest.testNormalSale2() |
||EZShopTest.testGetSaleTransaction() |
||EZShopTest.testWrongLoginGetSaleTransaction() |
||EZShopTest.testWrongParametersGetSaleTransaction() |
||EZShopTest.testStartReturnTransaction() |
||EZShopTest.testWrongLoginStartReturnTransaction() |
||EZShopTest.testWrongParametersStartReturnTransaction() |
||EZShopTest.testWrongLoginReturnProduct() |
||EZShopTest.testWrongParametersReturnProduct() |
||EZShopTest.testReturnTransactionDeleteBeforePay() |
||EZShopTest.testReturnTransactionCommitFalse() |
||EZShopTest.testWrongLoginEndReturnTransaction() |
||EZShopTest.testCreateCard() |
||EZShopTest.testNotLoggedCreateCard() |
||EZShopTest.testModifyPointsOnCard() |
||EZShopTest.testWrongLoginModifyPointsOnCard() |
||EZShopTest.testWrongParametersModifyPointsOnCard() |
||EZShopTest.testAttachCardToCustomer() |
||EZShopTest.testWrongLoginAttachCardToCustomer() |
||EZShopTest.testWrongParametersAttachCardToCustomer() |
||EZShopTest.testWrongParametersEndReturnTransaction() |
||EZShopTest.testWrongLoginDeleteReturnTransaction() |
||EZShopTest.testWrongParametersDeleteReturnTransaction() |
||EZShopTest.testWrongLoginReceiveCashPayment() |
||EZShopTest.testWrongParametersReceiveCashPayment() |
||EZShopTest.testReceiveCreditCardPayment() |
||EZShopTest.testWrongLoginReceiveCreditCardPayment() |
||EZShopTest.testWrongParametersReceiveCreditCardPayment() |
||EZShopTest.testWrongLoginReturnCashPayment() |
||EZShopTest.testWrongParametersReturnCashPayment() |
||EZShopTest.testReturnCreditCardPayment() |
||EZShopTest.testWrongLoginReturnCreditCardPayment() |
||EZShopTest.testWrongParametersReturnCreditCardPayment() |
||EZShopTest.testRecordBalanceUpdate() |
||EZShopTest.testWrongLoginRecordBalanceUpdate() |
||EZShopTest.testWrongLoginGetCreditsAndDebits() |
||EZShopTest.testReset() |
||EZShopTest.testLoadFromDB() |


# Scenarios


<If needed, define here additional scenarios for the application. Scenarios should be named
 referring the UC in the OfficialRequirements that they detail>

##### Scenario 1-4

| Scenario |  Delete product type X |
| ------------- |:-------------:| 
|  Precondition     | User C exists and is logged in |
|  Post condition     | X is deleted   |
| Step#        | Description  |
|  1    |  C searches X via product id|  
|  2    |  C deletes X |


##### Scenario 2-4

| Scenario | Aborted operation of delete user |
| ------------- |:-------------:| 
|  Precondition     | Account  for User U existing  |
|     | U logged in  |
 | Account X exists |
|  Post condition     | U can't delete any account |
| Step#        | Description  |
|  1    |  A can't select  and delete account X because has no rights |
|  2    |  Operation is aborted |

##### Scenario 3-4

| Scenario |  Order and Pay of product type X |
| ------------- |:-------------:| 
|  Precondition     | ShopManager S exists and is logged in |
| | Product type X exists |
|  Post condition     | Order O is in PAYED state  |
| | Balance -= Order.units * Order.pricePerUnit |
| | X.units not changed |
| Step#        | Description  |
|  1    | S creates order O |
|  2    |  S fills  quantity of product to be ordered and the price per unit |  
|  3    |  S register payment done for O |
|  4   |  O's state is updated to PAYED |

##### Scenario 6-7

| Scenario | Delete product type X  from a Sale and complete the Sale |
| ------------- |:-------------:| 
|  Precondition     | Cashier C exists and is logged in |
| | Product type X exists and has enough units to complete the sale |
|  Post condition     | Balance +=( N-T)*X.unitPrice  |
| | X.quantity += T |
| Step#        | Description  |
|  1    |  C starts a new sale transaction |  
|  2    |  C reads bar code of X |
|  3    |  C adds N units of X to the sale |
|  4    |  X available quantity is decreased by N |
|  5    |  C deletes T units of X from the sale |
|  6    |  C closes the sale transaction |
|  7    |  Sytem ask payment type |
|  8    |  Manage credit card payment (go to scenario 3) |
|  9   |  Payment successful |
|  10   |  C confirms the sale and prints the sale Ticket |
|  11   |  Balance is updated |

##### Scenario 8-3

| Scenario |  Return transaction of product type X is closed as undone |
| ------------- |:-------------:| 
|  Precondition     | Cashier C exists and is logged in |
| | Product Type X exists |
| | Ticket T exists and has at least N units of X |
| | Ticket T was paid with credit card |
|  Post condition     | Balance not changed  |
| | X.quantity not changed|
| Step#        | Description  |
|  1    |  C inserts T.ticketNumber |
|  2    |  Return transaction starts |  
|  3    |  C reads bar code of X |
|  4    |  C adds N units of X to the return transaction |
|  5    |  X available quantity is increased by N |
|  6    |  C closes the return transaction with commit false |
|  7   |   X available quantity is deacreased by N|
|  8   |  The return is deleted|



# Coverage of Scenarios and FR


<Report in the following table the coverage of  scenarios (from official requirements and from above) vs FR.
Report also for each of the scenarios the (one or more) API JUnit tests that cover it. >




| Scenario ID | Functional Requirements covered | JUnit  Test(s) |
| ----------- | ------------------------------- | ----------- |
|  1-1         | FR3                             |   EZShopTest.testCreateProduct()|
|           |                              |   EZShopTest.testUpdateLocation()|    
|  1-2         | FR3                             | EZShopTest.testGetProductTypeByBarCode()  |
| | |EZShopTest.testUpdateLocation() |
|  1-3         | FR3|  EZShopTesttestGetProductTypeByBarCode()  |
| | |EZShopTest.testUpdateProduct() |
|  1-4        | FR3.2    |  EZShopTest.testDeleteProductType()   |
|  2-1         | FR1                             |  EZShopTest.testCreateUser() |
|  2-2         | FR1.2                             | EZShopTest.testDeleteUser()  |
|  2-3         | FR1.5                             |  EZShopTest.testUpdateUserRights() |
|  2-4        | FR1.2                             | EZShopTest.testWrongLoginDeleteUser()  |
|  3-1         |  FR4.3                         |   EZShopTest.testIssueOrder()|
|    3-2       |    FR4.5                        |   EZShopTest.testPayOrder()|  
|  3-3        |    FR4.6                       |   EZShopTest.testRecordOrderArrival()|
|  3-4        |   FR4.4                        |   EZShopTest.testPayOrder()|
|    4-1      |      FR5.1                      |   EZShopTest.testCreateCustomer()|  
|    4-2      |   FR5.6                         |   EZShopTest.testCreateCard())|  
| | |EZShopTest.testAttachCardToCustomer()  |
|    4-3     |        FR5.1                    |   EZShopTest.testModifyCustomer()|  
|    4-4      |         FR5.1                   |   EZShopTest.testModifyCustomer()|  
|  5-1         | FR1.5                             |   EZShopTest.testLogin()|
|    5-2       |  FR1.5                            |   EZShopTest.testLogout()|   
|  6-1        |      FR6                       |   EZShopTest.testStartSaleTransaction()|
| | |EZShopTest.testAddProductToSale()   |
| | |EZShopTest.testEndSaleTransaction()  |
| | | EZShopTest.testNormalSale2() |
| | |EZShopTest.testReceiveCreditCardPayment()  |
| 6-2       |    FR6                              |   EZShopTest.testStartSaleTransaction()|
| | |EZShopTest.testAddProductToSale()   |
| | |EZShopTest.testEndSaleTransaction()  |
| | | EZShopTest.testNormalSale2() |
| | | EZShopTest.testApplyDiscountRateToProduct() |
| | |EZShopTest.testReceiveCreditCardPayment()  |
| 6-3      |     FR6                             |   EZShopTest.testStartSaleTransaction()|
| | |EZShopTest.testAddProductToSale()   |
| | |EZShopTest.testEndSaleTransaction()  |
| | | EZShopTest.testNormalSale2() |
| | | EZShopTest.testApplyDiscountRateToSale() |
| | |EZShopTest.testReceiveCreditCardPayment()  |
| 6-4     |       FR6                           |   EZShopTest.testStartSaleTransaction()|
| | |EZShopTest.testAddProductToSale()   |
| | |EZShopTest.testEndSaleTransaction()  |
| | | EZShopTest.testNormalSale2() |
| | |EZShopTest.testReceiveCreditCardPayment()  |
| | |EZShopTest.testComputePointsForSale() |
| | |EZShopTest.testModifyPointsOnCard()  |
| 6-5     |       FR6                           |   EZShopTest.testStartSaleTransaction()|
| | |EZShopTest.testAddProductToSale()   |
| | |EZShopTest.testEndSaleTransaction()  |
| | |EZShopTest.testDeleteSaleTransaction()  |
| 6-6       |        FR6                        |   EZShopTest.testStartSaleTransaction()|
| | |EZShopTest.testAddProductToSale()   |
| | |EZShopTest.testEndSaleTransaction()  |
| | | EZShopTest.testNormalSale2() |
| 6-7       |        FR6                        |   EZShopTest.testStartSaleTransaction()|
| | |EZShopTest.testAddProductToSale()   |
| | |EZShopTest.testDeleteProductToSale() |
| | |EZShopTest.testEndSaleTransaction()  |
| | |EZShopTest.testReceiveCreditCardPayment()  |
| 7-1       |               FR7.2     |EZShopTest.testReceiveCreditCardPayment()|
| 7-2      |       FR7.2                           | EZShopTest.testReceiveCreditCardPayment()|
| | | EZShopTest.testWrongParametersReceiveCreditCardPayment()|
| 7-3      |     FR7.2                                         |EZShopTest.testReceiveCreditCardPayment()|
| 7-4       |   FR7.1                       |EZShopTest.testNormalSale2()|
| 8-1       |     FR6.12-FR6.14-FR6.15- FR7.4      |   EZShopTest.testStartReturnTransaction()|
| | |EZShopTest.testReturnCreditCardPayment() |
| 8-2      |   FR6.12-FR6.14-FR6.15- FR7.3    |   EZShopTest.testStartReturnTransaction()|
| 8-3      |  FR6.12-FR6.14-FR6.15     |   EZShopTest.testStartReturnTransaction()|
| | |EZShopTest.testReturnTransactionCommitFalse() |
| 9-1      |      FR8.3                       |   EZShopTest.testRecordBalanceUpdate()|
| 10-1      |    FR7.4                             |   EZShopTest.testReturnCreditCardPayment()|
| 10-2      |       FR7.3                      |   EZShopTest.testStartReturnTransaction()|








           



# Coverage of Non Functional Requirements


<Report in the following table the coverage of the Non Functional Requirements of the application - only those that can be tested with automated testing frameworks.>


###

| Non Functional Requirement | Test name |
| -------------------------- | --------- |
|NFR4| EZShopTest.testWrongParametersCreateProduct() |
|                NFR5        |  CreditCardCircuitTest.testPositiveValidateCreditCard()         |
|                NFR6        |  CustomerImpl.testPositiveSetCustomerImplCard()         |

The NFR2, that is the one about the Performance, has been checked by putting the EZShop test methods under timing profiling. All test methods, that includes complex cases with many API calls, are way below 500ms. These are the results:
| JUnit test cases | Time (ms) |
|--|--|
| EZShopTest.testNotLoggedCreateCard() | 0|
| EZShopTest.testReturnTransactionDeleteBeforePay() | 139|
| EZShopTest.testWrongParameterGetProductTypeByBarCode() | 6|
| EZShopTest.testWrongLoginStartSaleTransaction() | 0|
| EZShopTest.testWrongLoginPayOrderFor() | 29|
| EZShopTest.testWrongParametersReturnProduct() | 53|
| EZShopTest.testWrongParametersModifyCustomer() | 11|
| EZShopTest.testDeleteCardModifyCustomer2() | 27|
| EZShopTest.testGetProductTypeByBarCode() | 10|
| EZShopTest.testWrongLoginUpdateUserRights() | 13|
| EZShopTest.testWrongParametersPayOrderFor() | 22|
| EZShopTest.testWrongParametersReceiveCashPayment() | 6|
| EZShopTest.testUpdateQuantity() | 30|
| EZShopTest.testWrongLoginUpdateQuantity() | 30|
| EZShopTest.testWrongLoginGetCreditsAndDebits() | 5|
| EZShopTest.testReturnCreditCardPayment() | 94|
| EZShopTest.testLoginUsernameEmpty() | 0|
| EZShopTest.testAttachCardToCustomer() | 29|
| EZShopTest.testWrongLoginReturnProduct() | 47|
| EZShopTest.testWrongParametersCreateProduct() | 5|
| EZShopTest.testReceiveCreditCardPayment() | 76|
| EZShopTest.testLoadFromDB() | 198|
| EZShopTest.testWrongParametersReturnCashPayment() | 5|
| EZShopTest.testWrongParametersDeleteProductToSale() | 19|
| EZShopTest.testLogin() | 4|
| EZShopTest.testGetAllProductTypes() | 23|
| EZShopTest.testReset() | 116|
| EZShopTest.testStartReturnTransaction() | 174|
| EZShopTest.testGetAllUsers() | 51|
| EZShopTest.testLocationNotSetRecordOrderArrival() | 44|
| EZShopTest.testLoginPasswordNull() | 0|
| EZShopTest.testStartSaleTransaction() | 4|
| EZShopTest.testWrongLoginAddProductToSale() | 22|
| EZShopTest.testWrongLoginReceiveCashPayment() | 0|
| EZShopTest.testDeleteProductToSale() | 22|
| EZShopTest.testNormalSale2() | 82|
| EZShopTest.testWrongParametersGetSaleTransaction() | 23|
| EZShopTest.testDefineCustomer() | 14|
| EZShopTest.testWrongParametersUpdateUserRights() | 11|
| EZShopTest.testWrongLoginDefineCustomer() | 0|
| EZShopTest.testWrongParametersGetUser() | 4|
| EZShopTest.testUpdateUserRights() | 16|
| EZShopTest.testWrongLoginApplyDiscountRateToSale() | 19|
| EZShopTest.testWrongLoginDeleteProductToSale() | 18|
| EZShopTest.testWrongParametersPayOrder() | 18|
| EZShopTest.testLoginUsernameNull() | 0|
| EZShopTest.testReturnTransactionCommitFalse() | 79|
| EZShopTest.testGetUser() | 9|
| EZShopTest.testWrongLoginUpdateProduct() | 13|
| EZShopTest.testCreateProduct() | 10|
| EZShopTest.testPayOrderFor() | 73|
| EZShopTest.testModifyCustomer() | 20|
| EZShopTest.testWrongLoginRecordOrderArrival() | 33|
| EZShopTest.testWrongParametersCreateUser() | 0|
| EZShopTest.testWrongLoginModifyCustomer() | 13|
| EZShopTest.testWrongLoginGetSaleTransaction() | 0|
| EZShopTest.testWrongLoginGetProductTypeByBarCode() | 18|
| EZShopTest.testWrongParametersIssueOrder() | 6|
| EZShopTest.testGetAllCustomers() | 14|
| EZShopTest.testWrongParametersAddProductToSale() | 17|
| EZShopTest.testWrongLoginGetAllOrders() | 23|
| EZShopTest.testRecordOrderArrival() | 57|
| EZShopTest.testGetSaleTransaction() | 39|
| EZShopTest.testWrongLoginApplyDiscountRateToProduct() | 20|
| EZShopTest.testWrongParametersEndSaleTransaction() | 5|
| EZShopTest.testAddProductToSale() | 21|
| EZShopTest.testWrongParametersUpdateLocation() | 10|
| EZShopTest.testWrongLoginReturnCreditCardPayment() | 0|
| EZShopTest.testWrongParametersDeleteCustomer() | 9|
| EZShopTest.testUpdateProduct() | 15|
| EZShopTest.testWrongParametersModifyPointsOnCard() | 4|
| EZShopTest.testNotLoggedCreateProduct() | 1|
| EZShopTest.testWrongParametersDeleteUser() | 6|
| EZShopTest.testDeleteCardModifyCustomer() | 18|
| EZShopTest.testWrongLoginComputePointsForSale() | 19|
| EZShopTest.testWrongLoginDeleteReturnTransaction() | 0|
| EZShopTest.testWrongLoginIssueOrder() | 14|
| EZShopTest.testWrongParametersReceiveCreditCardPayment() | 6|
| EZShopTest.testWrongLoginGetCustomer() | 0|
| EZShopTest.testCardNotPresentModifyCustomer() | 9|
| EZShopTest.testOrderNotPayedRecordOrderArrival() | 30|
| EZShopTest.testWrongLogicPayOrder() | 34|
| EZShopTest.testRecordBalanceUpdate() | 34|
| EZShopTest.testGetAllOrders() | 21|
| EZShopTest.testWrongParamentersUpdateProduct() | 10|
| EZShopTest.testWrongParametersComputePointsForSale() | 5|
| EZShopTest.testLoginWrongPassword() | 6|
| EZShopTest.testWrongLoginDeleteUser() | 9|
| EZShopTest.testWrongLoginEndReturnTransaction() | 49|
| EZShopTest.testWrongParametersGetCustomer() | 15|
| EZShopTest.testApplyDiscountRateToProduct() | 19|
| EZShopTest.testDeleteProductType() | 14|
| EZShopTest.testCreateCard() | 14|
| EZShopTest.testCreateUser() | 9|
| EZShopTest.testWrongLoginEndSaleTransaction() | 1|
| EZShopTest.testWrongLoginDeleteSaleTransaction() | 0|
| EZShopTest.testWrongLoginRecordBalanceUpdate() | 6|
| EZShopTest.testIssueOrder() | 17|
| EZShopTest.testWrongLoginDeleteProductType() | 15|
| EZShopTest.testWrongParametersApplyDiscountRateToProduct() | 21|
| EZShopTest.testEndSaleTransaction() | 33|
| EZShopTest.testWrongLoginModifyPointsOnCard() | 13|
| EZShopTest.testWrongParametersEndReturnTransaction() | 55|
| EZShopTest.testModifyPointsOnCard() | 29|
| EZShopTest.testDeleteUser() | 16|
| EZShopTest.testWrongParametersUpdateQuantity() | 19|
| EZShopTest.testNegativeBalancePayOrderFor() | 24|
| EZShopTest.testWrongParametersReturnCreditCardPayment() | 5|
| EZShopTest.testComputePointsForSale() | 19|
| EZShopTest.testWrongParametersDeleteSaleTransaction() | 5|
| EZShopTest.testWrongLoginGetAllCustomers() | 0|
| EZShopTest.testAlreadyPayedPayOrder() | 27|
| EZShopTest.testWrongLoginReceiveCreditCardPayment() | 35|
| EZShopTest.testWrongParametersDeleteReturnTransaction() | 4|
| EZShopTest.testGetProductTypesByDescription() | 14|
| EZShopTest.testNegativeBalancePayOrder() | 36|
| EZShopTest.testWrongLoginGetUser() | 26|
| EZShopTest.testUpdateLocation() | 18|
| EZShopTest.testLogout() | 5|
| EZShopTest.testWrongLoginGetAllUsers() | 52|
| EZShopTest.testWrongLoginUpdateLocation() | 17|
| EZShopTest.testDeleteCustomer() | 14|
| EZShopTest.testWrongLoginDeleteCustomer() | 12|
| EZShopTest.testWrongParameterApplyDiscountRateToSale() | 19|
| EZShopTest.testWrongLoginAttachCardToCustomer() | 10|
| EZShopTest.testWrongLoginReturnCashPayment() | 0|
| EZShopTest.testWrongParametersDefineCustomer() | 5|
| EZShopTest.testWrongLoginStartReturnTransaction() | 0|
| EZShopTest.testLoginPasswordEmpty() | 0|
| EZShopTest.testApplyDiscountRateToSale() | 27|
| EZShopTest.testDeleteSaleTransaction() | 69|
| EZShopTest.testWrongParametersDeleteProductType() | 10|
| EZShopTest.testGetCustomer() | 9|
| EZShopTest.testWrongLoginPayOrder() | 36|
| EZShopTest.testWrongParametersStartReturnTransaction() | 6|
| EZShopTest.testPayOrder() | 47|
| EZShopTest.testWrongParametersAttachCardToCustomer() | 12|
