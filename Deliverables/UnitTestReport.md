# Unit Testing Documentation

Authors:

Date:

Version:

# Contents

- [Black Box Unit Tests](#black-box-unit-tests)




- [White Box Unit Tests](#white-box-unit-tests)


# Black Box Unit Tests

    <Define here criteria, predicates and the combination of predicates for each function of each class.
    Define test cases to cover all equivalence classes and boundary conditions.
    In the table, report the description of the black box test case and (traceability) the correspondence with the JUnit test case writing the 
    class and method name that contains the test case>
    <JUnit test classes must be in src/test/java/it/polito/ezshop   You find here, and you can use,  class TestEzShops.java that is executed  
    to start tests
    >

 ### **Class *BalanceOperationImpl***



**Criteria for method *SetBalanceId*:**
	
 - Validity of the id

**Predicates for method *SetBalanceId*:**

| Criteria | Predicate |
| -------- | --------- |
| Validity of the id         |  (minint, 1)   |
|                             | [1, maxint)  ) |


**Boundaries**:

| Criteria | Boundary values |
| -------- | --------------- |
| Validity of the id           |   0,1              |
|          |                 |



**Combination of predicates**:


| Validity of the id | Valid / Invalid | Description of the test case | JUnit test case |
|-------|-------|-------|-------|
| (minint, 1)| Invalid|setId(-5) -> new id no setted<br />setId(0) -> new value no setted | BalanceOperationImplTest.java-> NegativeSetBalanceId()|
|[1, maxint)|Valid|setId(15)->new id setted|BalanceOperationImplTest.java-> PositiveSetBalanceId()|

**Criteria for method *SetDate*:**
	
 - Validity of the Date
  
**Predicates for method *SetDate*:**

| Criteria | Predicate |
| -------- | --------- |
|Validity of the Date         |  Valid   |
|                             | NULL |

**Combination of predicates**:


| Validity of the Date  | Valid / Invalid | Description of the test case | JUnit test case |
|-------|-------|-------|-------|
| NULL| Invalid|setDate(null)->new date no setted| BalanceOperationImplTest.java-> NullSetDate()|
| Valid| Valid|Date=LocalDate.of(2022,10,12)<br />setDate(Date)->new date setted| BalanceOperationImplTest.java-> PositiveSetDate()|


**Criteria for method *SetType*:**
	
 - Validity of the Type
 - Validity of the Parameters Type
  


**Predicates for method *SetType*:**

| Criteria | Predicate |
| -------- | --------- |
|Validity of the Type        |  Valid   |
|                             | NULL |
|Validity of the Parameters Type        |  Valid ("SALE","ORDER","RETURN","DEBIT","CREDIT")  |
|                             | Invalid |



**Combination of predicates**:


| Validity of the Type  | Validity of the Parameters Type  | Valid / Invalid | Description of the test case | JUnit test case |
|-------|-------|-------|-------|-------|
| NULL| *|Invalid|setType(null)->new type no setted| BalanceOperationImplTest.java-> NullSetType()|
|*|Invalid| Invalid|setType(" ")->new type no setted| BalanceOperationImplTest.java-> InvalidSetType()|
|Valid|Valid("SALE","ORDER","RETURN","DEBIT","CREDIT")| Valid |setType("SALE ")->new type setted </br> setType("ORDER")->new type setted </br>setType("RETURN")->new type setted </br>setType("DEBIT")->new type setted </br> setType("CREDIT")->new type setted </br>   | BalanceOperationImplTest.java-> PositiveSetType()|

**Criteria for method *SetStatus*:**
	
 - Validity of the Status
 - Validity of the Parameters Status
  


**Predicates for method *SetStatus*:**

| Criteria | Predicate |
| -------- | --------- |
|Validity of the Status      |  Valid   |
|                             | NULL |
|Validity of the Parameters Status       |  Valid ("PAID","UNPAID") |
|                             | Invalid |



**Combination of predicates**:


| Validity of the Status  | Validity of the Parameters Status  | Valid / Invalid | Description of the test case | JUnit test case |
|-------|-------|-------|-------|-------|
| NULL| *|Invalid|setStatus(null)->new status no setted| BalanceOperationImplTest.java-> NullSetStaus()|
|*|Invalid| Invalid|setStatus(" ")->new status no setted| BalanceOperationImplTest.java-> InvalidSetStaus()|
|Valid|Valid("PAID","UNPAID")| Valid |setType("PAID ")->new status setted </br> setType("UNPAID")->new type setted| BalanceOperationImplTest.java-> PositiveSetStaus()|


### **Class *CustomerImpl***

**Criteria for method *SetCustomerId*:**
	
 - Validity of the id

**Predicates for method *SetCustomerId*:**

| Criteria | Predicate |
| -------- | --------- |
| Validity of the id         |  (minint, 1)   |
|                             | [1, maxint)  ) |


**Boundaries**:

| Criteria | Boundary values |
| -------- | --------------- |
| Validity of the id           |   0,1              |
|          |                 |



**Combination of predicates**:


| Validity of the id | Valid / Invalid | Description of the test case | JUnit test case |
|-------|-------|-------|-------|
| (minint, 1)| Invalid|setId(-5) -> new id no setted<br />setId(0) -> new value no setted | CustomerImplTest.java-> NegativeSetCustomerId()|
|[1, maxint)|Valid|setId(15)->new id setted|CustomerImplTest.java-> PositiveSetCustomerId()|


**Criteria for method *SetCustomerName*:**
	
 - Validity of the String Name
 - Lenght of the String Name
  


**Predicates for method *SetCustomerName*:**

| Criteria | Predicate |
| -------- | --------- |
|Validity of the String Name  |  Valid   |
|                             | NULL |
|Lenght of the String Name        | >0 |
|                             | =0 ("") |



**Combination of predicates**:


| Validity of the String Name | Lenght of the String Name   | Valid / Invalid | Description of the test case | JUnit test case |
|-------|-------|-------|-------|-------|
| NULL| *|Invalid|setCustomerName(null)->new name no setted| CustomerImplTest.java->NullSetCustomerName()|
|*|=0| Invalid|setCustomerName("")->new name no setted| CustomerImplTest.java->InvalidSetCustomerName()|
|Valid|>0| Valid|setCustomerName("Chiara")->new name setted| CustomerImplTest.java->PositiveSetCustomerName()|

### **Class *Usermpl***

**Criteria for method *SetId*:**
	
 - Validity of the id

**Predicates for method *SetId*:**

| Criteria | Predicate |
| -------- | --------- |
| Validity of the id         |  (minint, 0)   |
|                             | [0, maxint)  ) |


**Boundaries**:

| Criteria | Boundary values |
| -------- | --------------- |
| Validity of the id           |   -1,0 |
|          |                 |



**Combination of predicates**:


| Validity of the id | Valid / Invalid | Description of the test case | JUnit test case |
|-------|-------|-------|-------|
| (minint, 0)| Invalid|setId(-5) -> new id no setted<br />setId(1) -> new value no setted | UserImplTest.java-> NegativeSetId()|
|[0, maxint)|Valid|setId(5)->new id setted|UserImplTest.java-> PositiveSetId()|


**Criteria for method *SetUsername*:**
	
 - Validity of the String Username
 - Lenght of the String Userame
  


**Predicates for method *SetUsername*:**

| Criteria | Predicate |
| -------- | --------- |
|Validity of the String Username  |  Valid   |
|                             | NULL |
|Lenght of the String Username      | >0 |
|                             | =0 ("") |



**Combination of predicates**:


| Validity of the String Username | Lenght of the String Username   | Valid / Invalid | Description of the test case | JUnit test case |
|-------|-------|-------|-------|-------|
| NULL| *|Invalid|setUserame(null)->new username no setted| UserImplTest.java->NullSetUsername()|
|*|=0| Invalid|setUsername("")->new username no setted| UserImplTest.java->InvalidSetUsername()|
|Valid|>0| Valid|setUsername("SaraR")->new username setted| UserImplTest.java->PositiveSetUsername()|

**Criteria for method *SetPassword*:**
	
 - Validity of the String Password
 - Lenght of the String Password
  


**Predicates for method *SetPassword*:**

| Criteria | Predicate |
| -------- | --------- |
|Validity of the String Password  |  Valid   |
|                             | NULL |
|Lenght of the String Password     | >0 |
|                             | =0 ("") |



**Combination of predicates**:


| Validity of the String Password | Lenght of the String Password  | Valid / Invalid | Description of the test case | JUnit test case |
|-------|-------|-------|-------|-------|
| NULL| *|Invalid|setPassword(null)->new password no setted| UserImplTest.java->NullSetPassword()|
|*|=0| Invalid|setPassword("")->new password no setted| UserImplTest.java->InvalidSetPassword()|
|Valid|>0| Valid|setPassword("passWORD")->new password setted| UserImplTest.java->PositiveSetPassword()|

**Criteria for method *SetRole*:**
	
 - Validity of the String Role
 - Lenght of the String Role
 - Validity of the Parameters Role 
  


**Predicates for method *SetRole*:**

| Criteria | Predicate |
| -------- | --------- |
|Validity of the String Role |  Valid   |
|                             | NULL |
|Lenght of the String Role    | >0 |
|                             | =0 ("") |
|Validity of the Parameters Role       |  Valid ("Administrator","Cashier","ShopManager") |
|                             | Invalid |



**Combination of predicates**:


| Validity of the String Role| Lenght of the StringRole  |Validity of the Parameters Role| Valid / Invalid | Description of the test case | JUnit test case |
|-------|-------|-------|-------|-------|-------|
| NULL| *| *|Invalid|setRole(null)->new role no setted| UserImplTest.java->NullSetRole()|
|*|=0| *| Invalid|setRole("")->new role no setted| UserImplTest.java->InvalidExistenceSetRole()|
|*|>0| Invalid| Invalid|setRole("Manager")->new role no setted| UserImplTest.java->InvalidSetRole()|
|Valid|>0| Valid("Administrator","Cashier","ShopManager")|Valid|setRole("ShopManager")->new password setted </br>setRole("Cashier")->new password setted </br>setRole("Administrator")->new password setted </br>| UserImplTest.java->PositiveSetRole()|

### **Class *ProductTypeImpl***

**Criteria for method *SetId*:**
 - Size of the Parameter of the id 	
 - Validity of the id

**Predicates for method *SetId*:**

| Criteria | Predicate |
| -------- | --------- |
| Size of the Parameter of the id         |  (minint, 1)   |
|                             | [1, maxint)  |
| Validity of the id | Valid |
|                             | NULL |



**Boundaries**:

| Criteria | Boundary values |
| -------- | --------------- |
| Size of the Parameter of the id          |   0,1 |
|          |                 |



**Combination of predicates**:


|Size of the Parameter of the id  |   Validity of the id | Valid / Invalid | Description of the test case | JUnit test case |
|-------|-------|-------|-------|-------|
| (minint, 1)|*| Invalid|setId(-5) -> new id no setted<br />setId(0) -> new value no setted | ProductTypeImplTest.java-> NegativeSetId()|
| * |NULL| Invalid|setId(null) -> new id no setted setted| ProductTypeImplTest.java-> NullSetId()|
|[1, maxint)|Valid|Valid|setId(5)->new id setted|ProductTypeImplTest.java-> PositiveSetId()|

**Criteria for method *SetQuantity*:**
 - Size of the Parameter of the quantity	
 - Validity of the quantity

**Predicates for method *SetQuantity*:**

| Criteria | Predicate |
| -------- | --------- |
| Size of the Parameter of the quantity	      |  (minint, 0)   |
|                             | [0, maxint)  |
| Validity of the quantity| Valid |
|                             | NULL |



**Boundaries**:

| Criteria | Boundary values |
| -------- | --------------- |
|Size of the Parameter of the quantity	 |   -1,0 |
|          |                 |



**Combination of predicates**:


|Size of the Parameter of the quantity	 |   Validity of the quantity | Valid / Invalid | Description of the test case | JUnit test case |
|-------|-------|-------|-------|-------|
| (minint, 0)|*| Invalid|setQuantity(-5) -> new quantity no setted<br />setQuantity(-1) -> new quantity no setted | ProductTypeImplTest.java-> NegativeSetQuantity()|
| * |NULL| Invalid|setQuantity(null) -> new quantity no setted setted| ProductTypeImplTest.java-> InvalidSetIQuantity()|
|[0, maxint)|Valid|Valid|setQuantity(5)->new quantitysetted|ProductTypeImplTest.java-> PositiveSetQuantity()

**Criteria for method *SetPricePerUnit*:**
 - Size of the Parameter of the PricePerUnit	
 - Validity of the PricePerUnit

**Predicates for method *SetQuantity*:**

| Criteria | Predicate |
| -------- | --------- |
| Size of the Parameter of the PricePerUnit	  |  (-maxdouble, 0)   |
|                             | [0, maxdouble)  |
| Validity of the PricePerUnit| Valid |
|                             | NULL |



**Boundaries**:

| Criteria | Boundary values |
| -------- | --------------- |
| Size of the Parameter of the PricePerUnit |   -0.0001,0,0.0001 |
|          |                 |



**Combination of predicates**:


|Size of the Parameter of the PricePerUnit	 |   Validity of the PricePerUnit| Valid / Invalid | Description of the test case | JUnit test case |
|-------|-------|-------|-------|-------|
| (-maxdouble, 0)|*| Invalid|setPricePerUnit(-0.50) -> new PricePerUnit no setted<br />setQuantity(-0.0001) -> new PricePerUnit no setted | ProductTypeImplTest.java-> NegativeSetPricePerUnit()|
| * |NULL| Invalid|setPricePerUnit(null) -> new PricePerUnit no setted setted| ProductTypeImplTest.java-> InvalidSetIPricePerUnit()|
|[0, maxdouble)|Valid|Valid|setPricePerUnit(0.60)->new PricePerUnit setted<br />setPricePerUnit(0.0001) -> new PricePerUnit setted|ProductTypeImplTest.java-> PositiveSetPricePerUnit()

**Criteria for method *SetBarCode*:**
	
 - Validity of the String BarCode
 - Lenght of the String BarCode
  


**Predicates for method *SetBarCode*:**

| Criteria | Predicate |
| -------- | --------- |
|Validity of the String BarCode |  Valid   |
|                             | NULL |
|Lenght of the String BarCode      | >0 |
|                             | =0 ("") |



**Combination of predicates**:


| Validity of the String BarCode| Lenght of the String BarCode  | Valid / Invalid | Description of the test case | JUnit test case |
|-------|-------|-------|-------|-------|
| NULL| *|Invalid|setBarCode(null)->new BarCode no setted| ProductTypeImplTest.java->NullSetBarCode()|
|*|=0| Invalid|setBarCode("")->new BarCode no setted| ProductTypeImplTest.java->InvalidSetBarCode()|
|Valid|>0| Valid|setBarCode("78515420")->new BarCode setted| ProductTypeImplTest.java.java->PositiveSetBarCode()|

**Criteria for method *SetDescription*:**
	
 - Validity of the String Description
 - Lenght of the String Description
  


**Predicates for method *SetDescription*:**

| Criteria | Predicate |
| -------- | --------- |
|Validity of the String Description |  Valid   |
|                             | NULL |
|Lenght of the String Description    | >0 |
|                             | =0 ("") |



**Combination of predicates**:


| Validity of the String Description| Lenght of the String Description  | Valid / Invalid | Description of the test case | JUnit test case |
|-------|-------|-------|-------|-------|
| NULL| *|Invalid|setDescription(null)->new Description no setted| ProductTypeImplTest.java->NullSetDescription()|
|*|=0| Invalid|setDescription("")->new Description no setted| ProductTypeImplTest.java->InvalidSetDescription()|
|Valid|>0| Valid|setDescription("yellow")->new Description setted| ProductTypeImplTest.java->PositiveSetDescription()|

**Criteria for method *SetNote*:**
	
 - Validity of the String Note
 - Lenght of the String Note
  


**Predicates for method *SetNote*:**

| Criteria | Predicate |
| -------- | --------- |
|Validity of the String Note |  Valid   |
|                             | NULL |
|Lenght of the String Note   | >0 |
|                             | =0 ("") |



**Combination of predicates**:


| Validity of the String Note| Lenght of the String Note  | Valid / Invalid | Description of the test case | JUnit test case |
|-------|-------|-------|-------|-------|
| NULL| *|Invalid|setNote(null)->new Note no setted| ProductTypeImplTest.java->NullSetNote()|
|*|=0| Invalid|setNote("")->new Note no setted| ProductTypeImplTest.java->InvalidSetNote()|
|Valid|>0| Valid|setNote("bad")->new Note setted| ProductTypeImplTest.java->PositiveSetNote()|


















# White Box Unit Tests

### Test cases definition
    
    <JUnit test classes must be in src/test/java/it/polito/ezshop>
    <Report here all the created JUnit test cases, and the units/classes under test >
    <For traceability write the class and method name that contains the test case>


| Unit name | JUnit test case |
|--|--|
|||
|||
||||

### Code coverage report

    <Add here the screenshot report of the statement and branch coverage obtained using
    the Eclemma tool. >


### Loop coverage analysis

    <Identify significant loops in the units and reports the test cases
    developed to cover zero, one or multiple iterations >

|Unit name | Loop rows | Number of iterations | JUnit test case |
|---|---|---|---|
|||||
|||||
||||||

