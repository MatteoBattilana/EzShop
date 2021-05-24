# Integration and API Test Documentation

Authors: Battilana Matteo, Huang Chunbiao, Mondal Subhajit, Sabatini Claudia

Date: 18/05/2021

Version: 1.0

# Contents

- [Dependency graph](#dependency-graph)

- [Integration approach](#integration)

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
||DatabaseConnectionTest.testWrongSchemaSQL() |
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

## Scenario UCx.y

| Scenario |  name |
| ------------- |:-------------:|
|  Precondition     |  |
|  Post condition     |   |
| Step#        | Description  |
|  1     |  ... |  
|  2     |  ... |



# Coverage of Scenarios and FR


<Report in the following table the coverage of  scenarios (from official requirements and from above) vs FR.
Report also for each of the scenarios the (one or more) API JUnit tests that cover it. >




| Scenario ID | Functional Requirements covered | JUnit  Test(s) |
| ----------- | ------------------------------- | ----------- |
|  1-1         | FR3                             |   EZShopTest.testCreateProduct()|
|           |                              |   EZShopTest.testUpdateLocation()|    
|  1-2         | FR3                             | **MISSING**  |
|  1-3         | FR3                             |  **MISSING** |
|  2-1         | FR1                             |  EZShopTest.testCreateUser() |
|  2-2         | FR1.2                             | EZShopTest.testDeleteUser()  |
|  2-3         | FR1.5                             |  EZShopTest.testUpdateUserRights() |
|  5-1         | FR1.5                             |   EZShopTest.testLogin()|
|    5-2       |  FR1.5                            |   EZShopTest.testLogout()|          
| ...         |                                 |             |             
| ...         |                                 |             |             
| ...         |                                 |             |             
| ...         |                                 |             |             



# Coverage of Non Functional Requirements


<Report in the following table the coverage of the Non Functional Requirements of the application - only those that can be tested with automated testing frameworks.>


###

| Non Functional Requirement | Test name |
| -------------------------- | --------- |
|                            |           |
