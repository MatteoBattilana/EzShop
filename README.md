# EzShop
Final project for the 'Software Engineering' course at Politecnico di Torino.

# Requirements Document

Authors: Battilana Matteo, Huang Chunbiao, Mondal Subhajit, Sabatini Claudia

Date: 21/04/2021

Version: 1.2

# Contents
- [Essential description](#essential-description)
- [Stakeholders](#stakeholders)
- [Context Diagram and interfaces](#context-diagram-and-interfaces)
  - [Context Diagram](#context-diagram)
  - [Interfaces](#interfaces)
- [Stories and personas](#stories-and-personas)
- [Functional and non functional requirements](#functional-and-non-functional-requirements)
  - [Functional Requirements](#functional-requirements)
  - [Access rights, actor vs function](#access-rights-actor-vs-function)
  - [Non Functional Requirements](#non-functional-requirements)
- [Use case diagram and use cases](#use-case-diagram-and-use-cases)
  - [Use case diagram](#use-case-diagram)
    - [Use case 1, UC1 - Create a new Employee](#use-case-1-uc1-create-a-new-employee)
    - [Use case 2, UC2 - Modify Employee](#use-case-2-uc2-modify-employee)
    - [Use case 3, UC3 - Disable Employee](#use-case-3-uc3-disable-employee)
    - [Use case 4, UC4 - Create a discount](#use-case-4-uc4-create-a-discount)
    - [Use case 5, UC5 - Create a new fidelity card](#use-case-5-uc5-create-a-new-fidelity-card)
    - [Use case 6, UC6 - Manage a sale transaction](#use-case-6-uc6-manage-a-sale-transaction)
    - [Use case 7, UC7 - Log in](#use-case-7-uc7-log-in)
    - [Use case 8, UC8 - Modify price of an item](#use-case-8-uc8-modify-price-of-an-item)
    - [Use case 9, UC9 - Employee statistics](#use-case-9-uc9-employee-statistics)
    - [Use case 10, UC10 - Manage Employee Work-Shift](#use-case-10-uc10-manage-employee-work-shift)
- [Glossary](#glossary)
- [System Design](#system-design)
- [Deployment Diagram](#deployment-diagram)
    - [System configuration](#system-configuration)


# Essential description

Small shops require a simple application to support the Owner or Manager. A small shop (ex a food shop) occupies 50-200 square meters, sells 500-2000 different item types, has one or a few cash registers
EZShop is a software application to:
* manage sales
* manage inventory
* manage customers
* support accounting


# Stakeholders


| Stakeholder name  | Description |
| ----------------- |:-----------:|
| OWNER | Owns the shop and wants to use the application in order to make more efficient the shop's administration and to check the performance of the shop and the Employees |
| MANAGER| Manages the application, can insert or delete products from the inventory,  supervise the shop and the others Employee (Cashiers)|
| CASHIER | Handles the sale transactions and handles the costumers |
| CUSTOMER | Person that wants to buy products in the shop |
| DB ADMINISTRATOR | The database administrator configures the database of the system that will be running for the EZShop application |
| SOFTWARE DEVELOPER | Writes the code for the EZShop application and installs also the application |
| SYSTEM DEVELOPER | Defines the hardware that must be include in the final system in order to handle the payment and the product scan |
| CREDIT CARD SYSTEM | Service provided by merchant services and used by the application to perform payment with credit/debit card |
| PRODUCT |  Product that has to be sold to the customers, that is scanned by the barcode reader |
| SUPPLIER | Who supplies or delivers goods to the shop |
| EMAIL GATEWAY | TService used to send the email notifications |


# Context Diagram and interfaces

## Context Diagram
```plantuml
@startuml
left to right direction

rectangle System {
  usecase "EZShop" AS S
}

Cashier -up- S
Owner -up- S
Manager -- S
Product -- S
:Credit Card System: -down- S
:Email Gateway: -up- S
@enduml
```
## Interfaces

| Actor | Logical Interface | Physical Interface  |
| ------------- |:-------------:| -----:|
|Manager| GUI | Screen Keyboard on PC, mouse|
|Owner |GUI| Screen Keyboard on PC, mouse|
|Cashier |GUI| Touchscreen, Keyboard on PC, mouse|
| Credit Card System | Web services (data exchange, soap + XML) | Internet connection |
| Product | Barcode reader laser | Barcode |
| Email Gateway | IMAP (Internet Message Access Protocol) | Internet connection |

# Stories and personas

1. **Sharon**, 37, single mother of two girls aged 3 and 6 years, owner of a small
 grocery store is committed to providing her customers seasonal and quality fruit and vegetables but admits that often combine management an activity and life as a mother is not so easy.
 This affects the organization of products in the warehouse that may rot and shoukd be thrown. For this reason she would like to have a weekly report showing which goods have been less sold, in order to reduce their amount on the next order.<hr>

2. **Fabio**, 30, sporty, busy and dynamic man,is the owner of  several small sport supplement stores.
  He has made fitness and wellness his purpose of life and given the great market demand for these products his revenue is considerably increasing.
  For this reason he is thinking of hiring a manager to help him manage one of his shops.
  His main goal is to keep on managing every aspect of his shop even if he won't be always present.<hr>


3. **Alicia**, 44, a businesswoman from Turin, owns a lovely shoes boutique.
  With the pandemic and with the economic crisis she is facing hard times but she doesn't want to give up and to close permanently.
  Every day she checks on the list of sold product, which one have been sold in order to compute manually the remaining inventory. She would like to  have a software that helps her to keep track of all products, so that her shop will be never go out of stock and never become redundant.<hr>

4. **Tom**, 22, an economics student works in a small hardware store,to pay for his studies.
 Thanks to his skills with numbers and bureaucracy, he was recently promoted to Manager of the activity.
 So now he has to deal of monitoring all aspects of the store, from the inventory, to the sales, to relationships with suppliers.
 He is very happy with his new role but he is aware of the responsibilities it entails
 and he would like to be able to combine work and studies well. Therefore he is looking for an application that allows him to accomplish the same tasks in less time and in a more efficient way.<hr>

5. **Jonathan**, 32, is the Owner of a small book shop in a town; he has a tight budget and in order to cut the costs he has one stable cashier and some on call students. He would like to have a software that is simply able to record his Employees work shifts.


# Functional and non functional requirements

## Functional Requirements

| ID        | Description  |
| ------------- |-------------|
|FR1|	Manage Employees|
|&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;FR1.1| Modify or create a new Employee (Manager or Cashier)|
|&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;FR1.2 |Delete Employee|
|&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;FR1.3 |List of all the Employees|
|&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;FR1.4| Statistics of Employee, (for Cashier daily earnings)|
|&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;FR1.5 |Search Employee|
|&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;FR1.6 |Disable Employee|
|&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;FR1.7  |Manage rights. Authorize access to functions to specific actors according to access rights|
|&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;FR1.8 | Manage work-shift timetable common to all Employees|
|&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;FR1.8.1 |Add a work-shift into the work-shift timetable common to all Employees|
|&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;FR1.8.2 |Modify a work-shift that is already into the work-shift timetable common to all Employees|
|&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;FR1.8.2 |Delete a work-shift that is already into the work-shift timetable common to all Employees|
|FR2|Handle inventory|
|&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;FR2.1 |Search product|
|&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;FR2.2 |Add product|
|&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;FR2.3 |Remove product (automatically + manually)|
|&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;FR2.4 |List of products + prices + number of products ordered by some criterion (list of multiple choices)|
|&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;FR2.5| Email notification if a product is out of stock|
|FR3|Manage customers|
|&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;FR3.1 |Create a new fidelity card (with an ID)|
|&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;FR3.2 |Add new customer with unique id |
|&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;FR3.3 |List of all the customers|
|&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;FR3.4 |Manage customer points|
|&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;FR3.4.1 |Mark points every tot of shop (e.g. every 15 spent give to the customer 1 point; after 50 point 10% discount for the entire sale transaction)|
|&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;FR3.4.2| Give a discount |
|FR4|Manage sales|
|&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;FR4.1| List of best selling products|
|&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;FR4.2 |List of daily sales|
|&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;FR4.3 |List of discounts (ex with products in expiration)|
|&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;FR4.4 |Create a discount, the name of the products that are *on discount* are written in a green color, the products *out of stock* in red |
|&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;FR4.5 |Delete discount|
|&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;FR4.7 |Create a gift card|
|&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;FR4.8 |Delete gift card|
|FR5|Access to the system|
|&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;FR5.1 |Log in|
|&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;FR5.2 |Log out|
|FR6|Monitor incomes|
|&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;FR6.1| Daily income|
|&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;FR6.2 |Monthly income|
|&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;FR6.3 |Year income|
|FR7|Register a sale payment |
|&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;FR7.1| Scan product barcode and play a feedback sound|
|&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;FR7.2 |Apply possible discount|
|&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;FR7.3 |Apply possible gift card|
|&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;FR7.4 | Get the receipt|
|&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;FR7.5 | Compute the sum|
|&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;FR7.6 |Get paid (credit card, cash)|
|&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;FR7.7 |Store the information about the transaction (sale, Cashier and customer) |
|&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;FR7.8 |Update inventory|
|&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;FR7.9 |Remove product from the transaction list|

## Access rights, actor vs function

| Function        | Owner | Manager | Cashier|
| ------------- |:-------------:| ------------- |:-------------:|
|FR1| &check;|  ||
|FR1.1| &check; | | |
|FR1.2| &check;|  ||
|FR1.3|&check;| &check;||  
|FR1.4|&check;| &check;||
|FR1.7|&check;| ||  
|FR1.8|&check;| &check;||
|FR2.1|&check;| &check;| &check;|
|FR2.2| &check;| &check;||
|FR2.3| &check;| &check;||
|FR2.4| &check;| &check;||
|FR3| &check;| &check; ||
|FR3.1| &check;| &check; |&check;|
|FR3.4| &check;| &check; |&check;|
|FR4 |&check;| &check; ||
|FR4.7 |&check;| &check; |&check;|
|FR4.8 |&check;| &check; |&check;|
|FR5 |&check;| &check; |&check;|
|FR6 |&check;| &check;||
|FR7 |&check;| &check; |&check;|


## Non Functional Requirements

| ID        | Type (efficiency, reliability, ..)           | Description  | Refers to |
| ------------- |:-------------:| :-----:| -----:|
|NFR1| USABILITY | Every person with at least 3+ years experience in using a PC must be able to use the core functions with no training in less than 30 minutes| All FR |
|NFR2| PERFORMANCE| All functions should respond in < 0.5 sec| All FR, excluded FR7.5, FR7.6, FR7.4 |
|NFR3| PRIVACY| The system must store data in a safe way, in order to avoid information disclosure | FR1, FR2, FR3.5, FR6, FR7.7 |           
|NFR4| AVAILABILITY | The system must be available for least 99% of the time|All FR|
|NFR5| PORTABILITY| The application works on a Windows 10 operating system, build version 1809|All FR|
|NFR6| SECURITY| The data of one Cashier can be visible and modifiable only by the Owner and the Manager. The data should be disclosed to other Cashiers. |FR1|
|NFR7| SECURITY| The system must be protected from an unauthorized access by using id and password|FR5|    
|NFR8| SECURITY | The credit card information should never be recorded into the system but only used during the payment process | FR7.6 |    
|NFR9| LOCALISATION | The prices are managed and displayed with the local currency with two decimals. This also applies to the date format | FR7.5 |
|NFR10| LOCALISATION | VAT application on the final price depends on the local national law | FR7.5 |
|NFR11| LOCALISATION | Data protection must be in accordance with the local law (GDPR for Europe)| FR1, FR2, FR3.5, FR6, FR7.7 |

# Use case diagram and use cases
## Use case diagram
```plantuml
@startuml
	usecase "FR1 Manage Employees" as UC2
	usecase "FR1.3 List Employees" as UC2.3
	usecase "FR1.4 Statistics of Employee" as UC2.4
	usecase "FR1.8 Manage Work-shift timetable" as UC2.7
	UC2.3 <.. UC2: <<include>>
	UC2.4 <.. UC2: <<include>>
	UC2.7 <.. UC2: <<include>>

	usecase "FR2 Handle inventory" as UC3
	usecase "FR2.1 Search product" as UC3.1
	usecase "FR2.2 Send email if product\nout of stock" as UC3.2
	UC3.2 <.. UC3: <<include>>
	UC3.1 <.. UC3: <<include>>

	usecase "FR5 Access to the system" as UC4

	usecase "FR3 Manage customers" as UC5
	usecase "FR6 Monitor incomes" as UC6

	usecase "FR4 Manage sales" as UC8
  usecase "FR4.7 Create gift card" as UC8.4
  usecase "FR4.8 Delete gift card" as UC8.5
	UC8.4 <.. UC8: <<include>>
	UC8.5 <.. UC8: <<include>>

  usecase "FR7 Register a sale payment" as UC7
  usecase "FR7.6 Get paid" as UC7.8
  usecase "Get paid via\ncredit card" as UC7.8.1
  usecase "FR7.1 Scan product\nbarcode" as UC7.1
  UC7.8.1 <.up. UC7.8: <<extends>>
  UC7.8 <.up. UC7: <<include>>
  UC7.1 <.up. UC7: <<include>>


Cashier <|-down- Manager
Manager <|-down- Owner
Product <-up- UC7.1
:Credit Card System: <-up- UC7.8.1

Owner -right-> UC2

Manager --> UC2.3
Manager --> UC2.4
Manager -up-> UC2.7
Manager -up-> UC3
Manager --> UC6
Manager --> UC8

Cashier --> UC3.1
Cashier -up-> UC4
Cashier -up-> UC8.4
Cashier --> UC8.5
Cashier -right-> UC7
Cashier -up-> UC5


:Email Gateway: <-up- UC3.2
@enduml
```

### Use case complete decomposition

```plantuml
@startuml

  usecase "FR7 Register a sale payment" as UC7
  usecase "FR7.2 Apply discount" as UC7.2
  usecase "FR7.5 Compute sum" as UC7.3
  usecase "FR7.7 Store transaction" as UC7.4
  usecase "FR7.8 Update inventory" as UC7.5
  usecase "FR7.9 Remove product" as UC7.9
  usecase "FR7.4 Print the receipt" as UC7.6
  usecase "FR7.3 Apply gift card" as UC7.7
  usecase "FR7.6 Get paid" as UC7.8
  usecase "Get paid via credit card" as UC7.8.1
  usecase "FR7.1 Scan product barcode" as UC7.1
  UC7.8.1 <.up. UC7.8: <<extends>>
  UC7.8 <.up. UC7: <<include>>
  UC7.7 <.up. UC7: <<extends>>
  UC7.6 <.up. UC7: <<include>>
  UC7.5 <.up. UC7: <<include>>
  UC7.4 <.up. UC7: <<include>>
  UC7.3 <.up. UC7: <<extend>>
  UC7.2 <.up. UC7: <<extend>>
  UC7.1 <.up. UC7: <<include>>
  UC7.9 <.up. UC7: <<extend>>
@enduml
```


```plantuml
@startuml

	usecase "FR4 Manage sales" as UC8
  usecase "FR4.4 Add discount" as UC8.1
  usecase "FR4.5 Modify/Delete discount" as UC8.2
  usecase "FR4.1 List best selling" as UC8.3
  usecase "FR4.7 Create gift card" as UC8.4
  usecase "FR4.8 Delete gift card" as UC8.5
	UC8.1 <.up. UC8: <<include>>
	UC8.2 <.up. UC8: <<include>>
	UC8.3 <.up. UC8: <<include>>
	UC8.4 <.up. UC8: <<include>>
	UC8.5 <.up. UC8: <<include>>


		usecase "FR6 Monitor incomes" as UC6
		usecase "FR6.1 Filter by day/month/year" as UC6.1
		UC6.1 <.up. UC6: <<extend>>

		usecase "FR5 Access to the system" as UC4
		usecase "FR5.1 Log in" as UC4.1
		usecase "FR5.2 Log out" as UC4.2
		UC4.1 <.up. UC4: <<include>>
		UC4.2 <.up. UC4: <<include>>

@enduml
```

```plantuml
@startuml

usecase "FR1 Manage Employees" as UC2
usecase "FR1.1 Modify/Delete/Disable Employee" as UC2.1
usecase "FR1.3 List Employees" as UC2.3
usecase "FR1.4 Statistics of Employee" as UC2.4
usecase "FR1.5 Search Employee" as UC2.5
usecase "FR1.7 Manage Rights" as UC2.6
usecase "FR1.8 Manage Work-shift timetable" as UC2.7
UC2.1 <.up. UC2: <<include>>
UC2.3 <.up. UC2: <<include>>
UC2.4 <.up. UC2: <<include>>
UC2.5 <.up. UC2: <<include>>
UC2.6 <.up. UC2: <<include>>
UC2.7 <.up. UC2: <<include>>

@enduml
```

```plantuml
@startuml


	usecase "FR2 Handle inventory" as UC3
	usecase "FR2.1 Search product" as UC3.1
  usecase "FR2.2 Add product" as UC3.2
  usecase "FR2.4 List by some criterion" as UC3.4
  usecase "FR2.3 Modify/Delete product" as UC3.5
  usecase "FR2.5 Email notification" as UC3.6
	usecase "FR2.2 Send email if product\nout of stock" as UC3.2
	UC3.2 <.. UC3: <<include>>
	UC3.4 <.up. UC3.1: <<extend>>
	UC3.2 <.up. UC3: <<include>>
	UC3.1 <.up. UC3: <<include>>
	UC3.5 <.up. UC3: <<include>>
	UC3.6 <.up. UC3: <<include>>


	usecase "FR3 Manage customers" as UC5
	usecase "FR3.1 Create fidelity card" as UC5.1
	usecase "FR3.2 Create customer" as UC5.1.1
	UC5.1 <.up. UC5: <<include>>
	UC5.1.1 <.up. UC5.1: <<extend>>
	usecase "FR3.3 List customers" as UC5.2
	usecase "FR3.4 Manage customer points" as UC5.2
	usecase "FR3.4.1 Add points" as UC5.2.1
	usecase "FR3.4.2 Generate discount" as UC5.2.2
	UC5.2.2 <.up. UC5.2: <<extend>>
	UC5.2.1 <.up. UC5.2: <<include>>
	UC5.2 <.up. UC5: <<include>>



@enduml
```

[Go GUI Prototype](./GUIPrototype.md)

### Use case 1, UC1 - Create a new Employee
| Actors Involved        | Owner |
| ------------- |:-------------:|
|  Precondition     | The EZShop application is running, the Owner is logged in |  
|  Post condition     | A new Employee is added into the system |
|  Nominal Scenario     | The Owner select with the appropriate button the Employee section, then he click over a button that shows a windows that must be filled with the new Employee's information |
|  Variants     | Owner provide Employee's information that already match with one Employee in the system The application will notify this information in the same window with an alert |


##### Scenario 1.1 - Nominal

| Scenario 1.1 | |
| ------------- |:-------------:|
| Description | Owner wants to add a new Employee to the ones already in the system for the EZShop|
| Precondition |  Owner is logged in|
| Post condition |  A new Employee is added to into the system |
|  Step#     | Description |  
|  1     | Owner clicks on the Employees section |  
|  2     | Owner clicks on the create new Employee button |
|  3     | A window is opened with all necessary fields to be filled |
|  4     | Owner enters the new Employee information and selects if it is a Manager or a Cashier |
|  5     | Owner confirms the operation by clicking on the appropriate button |
|  6     | The system will give a positive feedback about the creation |

##### Scenario 1.2
| Scenario 1.2 | |
| ------------- |:-------------:|
| Description | Owner tries to add a new Employee that is already in the system for the EZShop|
| Precondition |  Owner is logged in|
| Post condition |  The entered Employee is already in the system, the new Employee is not added |
|  Step#     | Description |  
|  1     | Owner clicks on the Employees section |  
|  2     | Owner clicks on the create new Employee button |
|  3     | A window is opened with all necessary fields to be filled |
|  4     | Owner enters the new Employee information and selects if it is a Manager or a Cashier |
|  5     | Owner confirms the operation by clicking on the appropriate button |
|  6     | An alert notifies that the Employee is already in the system |
|  5     | Owner cancels the operation by clicking on the appropriate button |

### Use case 2, UC2 - Modify Employee
| Actors Involved        | Owner |
| ------------- |:-------------:|
|  Precondition     | The EZShop application is running, the Owner must be logged into the EZShop Application and the account that requires modifications must exists |
| Post condition | The existing account is modified |
|  Nominal Scenario     | The Owner selects with the appropriate button the Employee section, then he clicks the name of the Employee that shows  windows that allow to change the Employee information |


##### Scenario 2.1 - Nominal
|  Scenario 2.1  | |
| ------------- |:-------------:|
| Description | The Owner wants to modify the Employee's information due to a typo error. The Employee is already in the system for the EZShop|
| Precondition | The EZShop application is running and the Owner is logged in|
| Post condition | The existing account is modified |
|  Step#     | Description |  
|  1     | Owner clicks on the Employees section |    
| 2 | Owner searches the user that wants to modify and selects it|
|3 | System shows the Employee details. The Employee information are opened in edit mode|
|4 | Owner modifies the Employee account information|
|5 | Owner saves the changes by clicking the confirm button |

### Use case 3, UC3 - Disable Employee
| Actors Involved        | Owner |
| ------------- |:-------------:|
|  Precondition     | The EZShop application is running and the Owner is logged |
| Post condition | An existing Employee account is disabled from access the EZShop Application, but not deleted |
|  Nominal Scenario     | The Owner selects with the appropriate button the Employee section, then he clicks the name of the Employee that shows windows that allow to change the Employee information |

##### Scenario 3.1 - Nominal
|  Scenario 3.1  | |
| ------------- |:-------------:|
| Description | Owner wants to disable an Employee that is already in the system for the EZShop |
| Precondition |  The EZShop application is running and the Owner is logged in |
| Post condition | The existing Employee account is disabled from access the EZShop Application |
|  Step#     | Description |  
|  1     | Owner clicks on the Employees section |  
| 2 | Owner searches the user that wants to modify and selects it|
|3 | System presents the Employee details. The screen opens in edit mode|
|4|Owner disables the Employee account|
|5 |Owner saves the changes|


### Use case 4, UC4 - Create a discount
| Actors Involved        | Manager |
| ------------- |:-------------:|
|  Precondition | The EZShop application is running and the Manager is logged in |  
|  Post condition | The discount is successfully created for a product and the EZShop inventory is updated |
|  Nominal Scenario     | The Manager searches a product from the list, clicks over it and creates a discount |
|  Variants     | For that product another discount is still valid, a confirmation alert is promoted to the Manager in order to replace it with the new one |

##### Scenario 4.1 - Nominal
|  Scenario 4.1  | |
| ------------- |:-------------:|
|  Description | Manager wants to create a discount on a product in the inventory   |
| Precondition | The EZShop application is running and the Manager is logged in |
|  Post condition | The discount is successfully created for a product and the EZShop inventory is updated |
|  Step#     | Description |  
| 1 | The Manager searches the product from the list in the inventory section  |
| 2 | The Manager selects the product |
| 3 | The Manager inserts the discount amount in percentage |
| 4 | The Manager saves the changes |
| 5 | The system re-calculates the product price |
| 6 | The system updates the product inventory with the discount|

##### Scenario 4.2
|  Scenario 4.2  | |
| ------------- |:-------------:|
|  Description | Manager wants to create a discount on a products in the inventory, but the product has already a valid discount  |
| Precondition | The EZShop application is running and the Manager is logged in |
|  Post condition | The old discount is replaced with the new one |
|  Step#     | Description |  
| 1 | The Manager searches the product from the list in the inventory section  |
| 2 | The Manager selects the product |
| 3 | The Manager inserts the discount amount in percentage |
| 4 | System prompts an alert because there is a valid discount on the product and ask if the system needs to replace it |
| 4 | Manager clicks of the confirmation button |
| 5 | The system re-calculates the product price |
| 6 | The system updates the product inventory with the discount|


### Use case 5, UC5 - Create a new fidelity card
| Actors Involved        | Cashier |
| ------------- |:-------------:|
|  Precondition     | The EZShop application is running and the Cashier is logged in |  
|  Post condition     | A new customer and the related fidelity card is added to the system |
|  Nominal Scenario     | The Cashier wants to create a new fidelity card for a customer. The Cashier selects with the appropriate button for the customer section, then he clicks over a button that prompts  windows that must be filled with the new customer information |
|  Variants     | The Cashier inserts customer's information that already match with another customer in the system. The application will notify this information in the same window and will create a new fidelity card replacing the previous one |

##### Scenario 5.1 - Nominal
|  Scenario 5.1  | |
| ------------- |:-------------:|
| Description | The Cashier wants to add a new customer and its fidelity card|
|  Precondition     | The EZShop application is running and the Cashier is logged in |  
|  Post condition     | A new customer and the related fidelity card is added to the system |
|  Step#     | Description |  
|  1     | Cashier clicks on the customer section |  
|  2     | Cashier clicks on the create new customer button |
|  3     | A window is opened with all necessary fields to be filled |
|  4     | Cashier enters the new customer's information |
|  5     | Cashier confirms the operation by clicking on the appropriate button |

##### Scenario 5.2
|  Scenario 5.2  | |
| ------------- |:-------------:|
| Description | The Cashier wants to add a new customer and its fidelity card. The customer and the fidelity card is already in the system |
|  Precondition     | The EZShop application is running and the Cashier is logged in |  
|  Post condition     | The customer is not added to the system because already present, but a new fidelity card is created and stored in the system |
|  Step#     | Description |  
|  1     | Cashier clicks on the customer section |  
|  2     | Cashier clicks on the create new customer button |
|  3     | A window is opened with all necessary fields to be filled |
|  4     | Cashier enters the new customer's information |
|  6     | The system will prompt an information message, since the customer is already in the system |
|  7     | The Cashier confirm the creation of a new fidelity card, associated to the customer, invalidating the previous one |


### Use case 6, UC6 - Manage a sale transaction
| Actors Involved        | Cashier |
| ------------- |:-------------:|
|  Precondition | The EZShop application is running and the Cashier is logged in the system |  
|  Post condition | The sales record is stored in the system and the inventory is updated |
|  Nominal Scenario     | The Cashier starts a new transaction, Cashier scans the product and the system computes the total. At the end the Cashier get paid  |
|  Variant 1    | The customer wants to pay with the credit card |
|  Variant 2    | The scanner doesn't read correctly the product code |
|  Variant 3    | The customer wants to use a gift card |
|  Variant 5    | The customer wants to insert the fidelity card, in order have points |

##### Scenario 6.1 - Nominal
|  Scenario 6.1  | |
| ------------- |:-------------:|
|  Description | The Cashier wants to manage a sale transaction for a customer that wants to buy some products with cash  |
|  Precondition | The EZShop application is running and the Cashier is logged in the system |  
|  Post condition | The sales record is stored in the system and the inventory is updated |
|  Step#     | Description |  
| 1 | Cashier scans all products with the barcode scanner |
| 2 | Once all products have been scanned, the Cashier ends the transaction |
| 3 | The system computes the total and applies the VAT |
| 4 | Cashier gets paid and the transaction is recorded into the system|
| 5 | The system prints the receipt|

##### Scenario 6.2
|  Scenario 6.2  | |
| ------------- |:-------------:|
|  Description | The Cashier wants to manage a sale transaction for a customer that wants to buy some products with credit card  |
|  Precondition | The EZShop application is running and the Cashier is logged in the system |  
|  Post condition | The sales record is stored in the system and the inventory is updated |
|  Step#     | Description |  
| 1 | Cashier scans all products with the barcode scanner |
| 2 | Once all products have been scanned, the Cashier ends the transaction |
| 3 | The system computes the total and apply the VAT |
| 4 | Cashier gets paid via the credit card system
|5|The transaction is recorded into the system|
| 6 | The system prints the receipt|

##### Scenario 6.3
|  Scenario 6.3  | |
| ------------- |:-------------:|
| Description | The Cashier isn't able to scan a product, the product code in inserted directly from the keyboard |
|  Precondition | The EZShop application is running and the Cashier is logged in the system |  
|  Post condition | The sales record is stored in the system and the inventory is updated |
|  Step#     | Description |  
| 1 | Cashier scans all products with the barcode scanner except one |
| 2 | The Cashier inserts the product code directly in to the transaction via the keyboard |
| 3 | Once all products have been scanned, the Cashier ends the transaction |
| 4 | The system computes the total and apply the VAT |
| 5 | Cashier gets paid via the credit card system
| 6 | The transaction is recorded into the system|
| 7 | The system prints the receipt|

##### Scenario 6.4
|  Scenario 6.4  | |
| ------------- |:-------------:|
|  Description | The Cashier wants to manage a sale transaction for a customer that wants to buy some products and has a gift card |
|  Precondition | The EZShop application is running and the Cashier is logged in the system |  
|  Post condition | The sales record is stored in the system and the inventory is updated |
|  Step#     | Description |  
| 1 | Cashier scans all products with the barcode scanner |
| 2 | Once all products have been scanned, the Cashier ends the transaction |
| 3 | Cashiers inserts the gift card given by the customer |
| 4 | The system computes the total and apply the VAT |
| 5 | Cashier gets paid and the transaction is recorded into the system|
| 6 | The system prints the receipt|

##### Scenario 6.5
|  Scenario 6.5  | |
| ------------- |:-------------:|
|  Description | The Cashier wants to manage a sale transaction for a customer that wants to buy some products, using the fidelity card |
|  Precondition | The EZShop application is running and the Cashier is logged in the system |  
|  Post condition | The sales record is stored in the system and the inventory is updated |
|  Step#     | Description |  
| 1 | Cashier scans the fidelity card |
| 2 | Cashier scans all products with the barcode scanner |
| 3 | Once all products have been scanned, the Cashier ends the transaction |
| 4 | The system computes the points for the current sale transaction |
| 5 | The points are added to the current points |
| 6 | The system applies 10% discount of the sale transaction if the number of points are >= 50 |
| 7 | The system computes the total and apply the VAT |
| 8 | Cashier gets paid and the transaction is recorded into the system|
| 9 | The system prints the receipt with the number of points|


### Use case 7, UC7 - Log in
| Actors Involved        | Cashier |
| ------------- |:-------------:|
|  Precondition     | The EZShop application is running, the Employee is not logged in and the login form is displayed |  
|  Post condition     | The Employee is logged into the system, all functions based on the access rights are available  |
|  Nominal Scenario     | The Employee enters his user id and password in the login form and then clicks on the confirmation button |
|  Variants     | The Employee enters a wrong user id or password, the system notifies a login error |


##### Scenario 7.1 - Nominal

| Scenario 7.1 | |
| ------------- |:-------------:|
| Description | The Employee wants to log in to the system in order to use its functions  |
| Precondition |   The EZShop application is running, the Employee is not logged in the system and the application is already opened and running in the computer |
| Post condition |  The Employee is logged in |
|  Step#     | Description |   
|  1    | The Employee enters his credential, user id and password |
|  2    | The Employee confirms with the appropriate button |

##### Scenario 7.2

| Scenario 7.2 | |
| ------------- |:-------------:|
| Description | The Employee wants to log in to the system in order to use its functions  |
| Precondition |  The Employee is not logged in the system and the application is already opened and running in the computer |
| Post condition |  The Employee is not logged in |
|  Step#     | Description |  
| 1     | The Employee enters wrong credential |
|  2     | The Employee can enter again the credential |




### Use case 8, UC8 - Modify price of an item
| Actors Involved        | Owner |
| ------------- |:-------------:|
|  Precondition | The EZShop application is running and the Owner is logged in |  
|  Post condition |  The price is successfully updated for the product in the inventory|
|  Nominal Scenario     | The Owner selects with the appropriate button the inventory section, then he selects the product and inserts the new price |

##### Scenario 8.1 - Nominal
|  Scenario 8.1  | |
| ------------- |:-------------:|
|  Description | The Owner wants to modify the price of a product in the inventory  |
| Precondition |  The Owner is logged in the system and the application is already opened and running in the computer |
| Post condition |  The price of the product is updated with the new one |
| 1 | Owner searches the product from the list in the inventory section|
| 2 | Owner selects the product |
| 3 | Owner enters the new price |
| 4 | Owner saves the changes |


### Use case 9, UC9 - Employee statistics
| Actors Involved        | Owner |
| ------------- |:-------------:|
|  Precondition     | The EZShop application is running and the Owner is logged in |  
|  Post condition     | The statistics of the Employee are shown to the Owner |
|  Nominal Scenario     | The Owner selects with the appropriate button the Employee section, then he clicks over a button that prompts the Employee's statistics |


##### Scenario 9.1 - Nominal

| Scenario 9.1 | |
| ------------- |:-------------:|
| Description | Owner wants to get the number of sales in a week for a specific Cashier|
|  Precondition     | The EZShop application is running and the Owner is logged in |  
|  Post condition     | The statistics of the Employee are shown to the Owner |
|  Step#     | Description |
|  1     | Owner clicks on the accounting section|
|  2     | Owner clicks on the statistics section |  
|  3     | Owner searches and selects a Cashier |
|  4     | A window is opened and the Owner can select the week he wants to check|
|  5     | The system generates the statistics for that Cashier for the selected week |


### Use case 10, UC10 - Manage Employee Work-Shift
| Actors Involved        | Manager |
| ------------- |:-------------:|
|  Precondition  | The EZShop application is running and the Manager is logged in  |
| Post condition | The Manager successfully creates new timetable or modifies the pre-existing timetable for work-shift of all Employee|
|  Nominal Scenario |The Manager selects with the appropriate button on the system, then he adds a new work-shift into the work-shift timetable of all Employees or modifies the pre-existing work-shift timetable |

##### Scenario 10.1 - Nominal
|  Scenario 10.1  | |
| ------------- |:-------------:|
| Description | The Manager wants to add a work-shift in the work-shift timetable for a better management of the EZShop|
| Precondition |  The system is running and the Manager is logged in |
| Post condition | Successfully added the work-shift in the work-shift timetable|
|  Step#     | Description |   
| 1 | Manager opens the work-shift section |  
| 2| Manager clicks on the appropriate add button|
| 3| The system will highlight the free slots|
| 4| Manager creates a new work-shift in the work-shift timetable|
| 5 | Manager inserts details in the work-shift timetable |
| 6 | Manager saves the details by clicking the confirm button |

##### Scenario 10.2
|  Scenario 10.2  | |
| ------------- |:-------------:|
| Description | The Manager wants to modify work-shift timetable according to the needs|
| Precondition |  Manager is logged in, the work-shift timetable has at least one record |
| Post condition | Successfully modified the pre-existing work-shift timetable |
|  Step#     | Description |  
| 1 | Manager opens the work-shift section |  
| 2 | Manager selects a slot in the pre-existing work-shift timetable|
| 3 | Manager modifies the slot |
| 4 | Manager saves the modifications by clicking the confirm button |

# Glossary

```plantuml
@startuml
class Cashier {
	+ name
	+ surname
	+ id
}
class Right {
	+ type
}
class Discount {
	+ start date
	+ end date
	+ percentage
}
class "Product" {
	+ id
	+ availableStock
	+ description
}
class Notification {
	+ id
	+ type
	+ text
}
class "Gift card" {
	+ id
	+ value
}

class Customer {
	+ name
	+ surname
	+ user id
	+ password
}

class "Fidelity card" {
	+ points
}

class Sale {
	+ id
	+ data
	+ total
}

class Supplier {
	+ name
	+ email
	+ telephone
}

EZShop -- "Work-shift\ntimetable"
"Work-shift\ntimetable" -up- "*" Cashier
EZShop -- "*" Cashier
Manager -up-|> Cashier
Owner -up-|> Manager
Cashier "*"-- "*" Right
Cashier -down- "*" Statistic
EZShop -right- "*" "Gift card"

EZShop -- Inventory
Inventory -up- "*" Notification
Inventory -right- "*" Product
Product "*" -right- Discount
Supplier "*" -up- "*" Product
EZShop -left- "*" Customer
Customer -left- "Fidelity card"
Manager -- Inventory

Cashier -- "*" Sale
Sale "*" -- "1..*" Product
"Fidelity card" "0..1" -- "*" Sale

note left of "Fidelity card" : A customer can have only\none fidelity card that contains\nall the points.
note right of "Gift card" : The shop can sell gift cards\nwith different values.
note top of Notification : An email notification contains \ninformation about the products that\nare going to be out of stock
note bottom of Right : Different rights are\nassociated depending\non the Employee type.
note bottom of Statistic : For each Cashier a list\nof possible statistics\nis available in order to\nevaluate the performance.

@enduml
```

# System Design

```plantuml
@startuml
"EZShop System" o-- Computer: is composed
"EZShop System" o-- Server: is composed

Computer o-- "Barcode scanner"
Computer o-- "Keyboard"
Computer o-- "Touchscreen"
Computer o-- "Mouse"
Computer o-- "Software"
Computer o-right- "Receipt machine"
Computer o-left- "Speaker"

Server o-- "DBMS"

@enduml
```


# Deployment Diagram
```plantuml
@startuml

node "Server" as server
node "Computer" as computer2

server -- computer2
artifact "DBMS" as dbms
artifact "EZShop Application" as application
dbms .right.> server
application ..> computer2
@enduml
```

### System configuration
The system is based on the client-server pattern. Since the software can be used both for generating statistics about the Employee and manage the inventory, it can work with or without the Receipt machine and the Barcode scanner.
