# Requirements Document

Authors: Battilana Matteo, Huang Chunbiao, Mondal Subhajit, Sabatini Claudia

Date: 15/04/2021

Version: 1.1

# Contents

- [Essential description](#essential-description)
- [Stakeholders](#stakeholders)
- [Context Diagram and interfaces](#context-diagram-and-interfaces)
	+ [Context Diagram](#context-diagram)
	+ [Interfaces](#interfaces)

- [Requirements Document](#requirements-document)
- [Contents](#contents)
- [Essential description](#essential-description)
- [Stakeholders](#stakeholders)
- [Context Diagram and interfaces](#context-diagram-and-interfaces)
	- [Context Diagram](#context-diagram)
	- [Interfaces](#interfaces)
- [Stories and personas](#stories-and-personas)
- [Functional and non functional requirements](#functional-and-non-functional-requirements)
	- [Functional Requirements](#functional-requirements)
- [Access rights, actor vs function](#access-right-actor-vs-function)
	- [Non Functional Requirements](#non-functional-requirements)
- [Use case diagram and use cases](#use-case-diagram-and-use-cases)
	- [Use case diagram](#use-case-diagram)
		- [Use case 1, UC1](#use-case-1-uc1)
				- [Scenario 1.1](#scenario-11)
				- [Scenario 1.2](#scenario-12)
				- [Scenario 1.x](#scenario-1x)
		- [Use case 2, UC2](#use-case-2-uc2)
		- [Use case x, UCx](#use-case-x-ucx)
- [Glossary](#glossary)
- [System Design](#system-design)
- [Deployment Diagram](#deployment-diagram)

# Essential description

Small shops require a simple application to support the owner or manager. A small shop (ex a food shop) occupies 50-200 square meters, sells 500-2000 different item types, has one or a few cash registers
EZShop is a software application to:
* manage sales
* manage inventory
* manage customers
* support accounting


# Stakeholders


| Stakeholder name  | Description |
| ----------------- |:-----------:|
| OWNER | Own the shop. want to use the application in order to make more efficient the shop's administration and to check  the performance of the shop and the employees   |
| MANAGER| Manages the application, can insert or delete products from the inventory, order products to the suppliers, supervise the shop and the others employees (cashier)|
| CASHIER | Handle the sales and handle the costumers|
| CUSTOMERS | Person that want to buy products in the shop |
| DB ADMINISTRATOR | The database administrator configure the database of the system |
| SOFTWARE DEVELOPER | Writes the code by which the system is built, that installs also the application |
| SYSTEM DEVELOPER | Define the hardware that must be include in the final system in order to handle the payment and the product scan |
| CREDIT CARD SYSTEM | Service provided by merchant services and used by the application perform payment with credit/debit card |
| PRODUCT |  Product that has to be sold to the customers |
| SUPPLIER | Who supplies or delivers goods to the shop |
| EMAIL GATEWAY | This is the service used to send the email notifications |


# Context Diagram and interfaces

## Context Diagram
```plantuml
@startuml
left to right direction

rectangle System {
  usecase "EZShop" AS S
}

CASHIER -up- S
OWNER -up- S
MANAGER -- S
PRODUCT -- S
:CREDIT CARD SYSTEM: -down- S
:EMAIL GATEWAY: -up- S
@enduml
```
## Interfaces

| Actor | Logical Interface | Physical Interface  |
| ------------- |:-------------:| -----:|
|MANAGER| GUI | Screen Keyboard on PC, mouse|
|OWNER |GUI| Screen Keyboard on PC, mouse|
|CASHIER |GUI| Touchscreen, Keyboard on PC, mouse|
| CREDIT CARD SYSTEM | Web services (data exchange, soap + XML) | Internet connection |
| PRODUCT | Bar code reader laser | Bar code |
| EMAIL GATEWAY | IMAP (Internet Message Access Protocol) | Internet connection |

# Stories and personas

1. **Sharon**, 37, single mother of two girls aged 3 and 6 years, owner of a small
 grocery store is committed to providing hers customers seasonal and quality fruit and vegetables but admits that often reconcile management an activity and life as a mother is not so easy.
 This affects the organization of products in the warehouse that ends up rotting and must be thrown. For this reason,she would like to know, week by week which are the  goods that have been sold less than the other, so that the next week she will order a bit less, according to the numbers.<hr>

2. **Fabio**, 30, sporty, busy and dynamic man,is a owner of a several small sports supplement stores.
  He has made fitness and wellness his purpose of life and given the great demand
  of the market for these products his revenue is increasing considerably. For this reason he
  is thinking of hiring a manager to help him manage one of his shops.
  His main interest is to continue to manage every aspect of the shop at his best even if will not be always present.<hr>


3. **Alicia**, 44, a small businesswoman from Turin, owns a lovely shoes boutique.
  With the pandemic and with the economic crisis she is facing hard times but she does not give up to close permanently.
  Every day she searches in the list of sold product, written in the receipts, which one have been sold in order to compute manually the remaining inventory. She would    like to  have a software that helps her to keep track of all products, so that her shop will be never out of stock and never excess.<hr>

4. **Tom**, 22, an economics student to pay for his studies, works in a small hardware store,
 thanks to his skills with numbers and bureaucracy, he was recently promoted to manager of the activity.
 So now he has to take care of monitoring all aspects of the store, from inventory, to sales, to relationships with suppliers.
 He is very happy with his new role but he is aware of the responsibilities it entails
 and he would like to be able to combine work and studies well. So he is looking for an application that  allow him to satisfy the same tasks in less time and in a more efficient way.<hr>

5. **Jonathan**, 32, is the owner of a small book shop in an town; he has a tight budget and in order to cut the costs he has one stable cashier and some students that work on call, when they are free. He would like to have a software that is simply able to record his employees work shifts.


# Functional and non functional requirements

## Functional Requirements

| ID        | Description  |
| ------------- |-------------|
|  FR1 |Manage rights. Authorize access to functions to specific actors according to axcesss rights|
|FR2|	Manage employees|
|&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;FR2.1| Modify or define a new employee (manager or cashier)|
|&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;FR2.2 |Delete employee|
|&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;FR2.3 |List of all the employees|
|&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;FR2.4| Statistics of Employee, (for cashier daily earnings)|
|&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;FR2.5 |Search Employee|
|&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;FR2.6 |Disable Employee|
|FR3|Handle inventory|
|&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;FR3.1 |Search product|
|&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;FR3.2 |Order product to the suppliers|
|&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;FR3.3 |Add product|
|&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;FR3.4 |Remove product (automatically + manually)|
|&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;FR3.5 |List of products + prices + number of products orderd by some criterion (list of multiple choices)|
|&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;FR3.6| Email notification when product is out of stock|
|FR4|Manage customers|
|&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;FR4.1 |Create a new fidelity card (with an ID)|
|&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;FR4.5 |Add new customer + unique id |
|&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;FR4.2 |List of all the customers|
|&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;FR4.3 |Mark points every tot of shop (ex every 50 spent give them 1 point after 10 point 20% discount)|
|&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;FR4.4| Give a discount |
|FR5|Manage sales|
|&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;FR5.1| List of best selling products|
|&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;FR5.2 |List of daily sales|
|&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;FR5.3 |List of discounts (ex with products in expiration)|
|&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;FR5.4 |Create a discount|
|&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;FR5.5 |Delete discount|
|&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;FR5.7 |Create a gift card|
|&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;FR5.8 |Delete gift card|
|FR6|Access to the system|
|&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;FR6.1 |Log in|
|&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;FR6.2 |Log out|
|FR7|Monitor incomes|
|&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;FR7.1| Daily income|
|&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;FR7.2 |Monthly income|
|&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;FR7.3 |Year income|
|FR8|Register a sale payment |
|&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;FR8.1| Scan product|
|&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;FR8.2 |Apply possible discount|
|&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;FR8.3 |Use possible gift card|
|&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;FR8.4 | Get the receipt|
|&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;FR8.5 | Compute the sum|
|&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;FR8.6 |Get paid (credit card, cash)|
|&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;FR8.7 |Store the information about the transaction (sale, cashier and customer) |
|&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;FR8.8 |Update inventory|

## Access rights, actor vs function

| Function        | Owner | Manager | Cashier|
| ------------- |:-------------:| ------------- |:-------------:|
|FR1| &check;|  ||
|FR2.1| &check; | | |
|FR2.2| &check;|  ||
|FR2.3|&check;| &check;||  
|FR2.4|&check;| &check;||
|FR3.1|&check;| &check;| &check;|
|FR3.2|&check;| &check;||  
|FR3.3| &check;| &check;||
|FR3.4| &check;| &check;||
|FR3.5| &check;| &check;||
|FR4| &check;| &check; ||
|FR4.1| &check;| &check; |&check;|
|FR4.4| &check;| &check; |&check;|
|FR5 |&check;| &check; ||
|FR6 |&check;| &check; |&check;|
|FR7 |&check;| &check;||
|FR8 |&check;| &check; |&check;|


## Non Functional Requirements

| ID        | Type (efficiency, reliability, ..)           | Description  | Refers to |
| ------------- |:-------------:| :-----:| -----:|
|NFR1| USABILITY | Every customer with at least 3+ years experience in using a PC must be able to use all functions with no training in less than 30 minutes| All FR |
|NFR2| PERFORMANCE| All function should respond in <0.5 sec| All FR, excluded FR8.5, FR8.6, FR8.4 |
|NFR3| PRIVACY| The system must must store data in a safe way, in order to avoid information disclosure | FR2, FR3, FR4.5, FR7, FR8.7 |           
|NFR4| AVAILABILITY | The system must be available for least 99% of the time|All FR|
|NFR5| PORTABILITY| The application should be working in a Windows 10 operating system, build version 1809|All FR|
|NFR6| SECURITY| The data of one cashier can be visible and modifiable only by the owner and the manager. The data should be disclosed to other cashiers at the same or lower level. |FR2|
|NFR7| SECURITY| The system must be protected from an unauthorized access by using id and password|FR6|    
|NFR8| SECURITY | The credit card information should never be recorded into the system | FR8.6 |    
|NFR9| LOCALISATION | The prices are managed and displayed with the local currency with two decimals. This includes also the | FR8.5 |
|NFR10| LOCALISATION | VAT application on the final price depends on the local national law | FR8.5 |
|NFR11| LOCALISATION | Data protection must be in accordance with the local law (GDPR for Europe)| FR2, FR3, FR4.5, FR7, FR8.7 |

# Use case diagram and use cases
## Use case diagram

```plantuml
@startuml
rectangle System {
	left to right direction
	usecase "Manage Employee" as UC2
	usecase "Delete Employee" as UC2.1
	usecase "Add Employee" as UC2.2
	usecase "List Employees" as UC2.3
	usecase "Statistics of Employee" as UC2.4
	usecase "Search Employee" as UC2.5
	UC2.1 <-up- UC2: <<include>>
	UC2.2 <-up- UC2: <<include>>
	UC2.3 <-up- UC2: <<include>>
	UC2.4 <-up- UC2: <<include>>
	UC2.5 <-up- UC2: <<include>>

	usecase "Handle inventory" as UC3
	usecase "Search product" as UC3.1
  usecase "Add product" as UC3.2
  usecase "Remove product" as UC3.3
  usecase "Order by some criterion" as UC3.4
	UC3.4 <-up- UC3: <<extend>>
	UC3.3 <-up- UC3: <<include>>
	UC3.2 <-up- UC3: <<include>>
	UC3.1 <-up- UC3: <<include>>

	usecase "Access to the system" as UC4
	usecase "Log in" as UC4.1
  usecase "Log out" as UC4.2
	UC4.1 <-up- UC4: <<include>>
	UC4.2 <-up- UC4: <<include>>

	usecase "Monitor incomes" as UC5

	usecase "Manage customers" as UC6

	usecase "Register a sale payment" as UC7

	usecase "Manage sales" as UC8

}
actor Product
actor :Credit Card System:
Cashier <|-up- Manager
Manager <|-up- Owner

@enduml
```

### Use case 1, UC1 - Create a new employee
| Actors Involved        | Owner |
| ------------- |:-------------:|
|  Precondition     | The owner is logged in |  
|  Post condition     | A new employee is added into the system |
|  Nominal Scenario     | The owner select with the appropriate button the employee section, then he click over a button that prompt a windows that must be filled with the new employee's information |
|  Variants     | Owner provide person's information that already match with one employee in the system The application will notify this information in the same window |


##### Scenario 1.1 - Nominal

| Scenario 1.1 | |
| ------------- |:-------------:|
| Description | Owner wants to add a new employee to the ones already in the system for the EZShop|
| Precondition |  Owner is logged in|
| Post condition |  A new employee is added to into the system |
|  Step#     | Description |  
|  1     | Owner click on the employees section |  
|  2     | Owner click on the create new employee button |
|  3     | A window is opened with all necessary fields to be filled |
|  4     | Owner enters the new employee information and select if it is a manager or a cashier |
|  5     | Owner confirm the operation by clicking on the appropriate button |

##### Scenario 1.2
| Scenario 1.2 | |
| ------------- |:-------------:|
| Description | Owner tries to add a new employee that is already in the system for the EZShop|
| Precondition |  Owner is logged in|
| Post condition |  The entered employee is already in the system, the new employee is not added |
|  Step#     | Description |  
|  1     | Owner click on the employees section |  
|  2     | Owner click on the create new employee button |
|  3     | A window is opened with all necessary fields to be filled |
|  4     | Owner enters the new employee information and select if it is a manager or a cashier |
|  5     | Owner confirm the operation by clicking on the appropriate button |
|  6     | An alert notify that the employee is already in the system |
|  5     | Owner cancel the operation by clicking on the appropriate button |

### Use case 2, UC2 - Modify Employee
| Actors Involved        | Owner |
| ------------- |:-------------:|
|  Precondition     | The owner must be logger to the EZShop Application and the account that requires modifications must exists |
| Post condition | The existing account has been modified |
|  Nominal Scenario     | The owner select with the appropriate button the employee section, then he click over a button that prompt a windows that allows to change the employee information |


##### Scenario 2.1 - Nominal
|  Scenario 2.1  | |
| ------------- |:-------------:|
| Description | The owner wants modify the employee's information due to a typo error. The employee is already in the system for the EZShop (e.g. password, Access Level, etc.)|
| Precondition |  Owner is logged in|
| Post condition | The existing account has been modified |
|  Step#     | Description |  
|  1     | Owner click on the employees section |  
|  2     | Owner search the user that wants to modify |  
| 3 | Owner selects the modify command for that employee|
|4 | System presents the employee details. The screen opens in edit mode|
|5 | Owner modifies the employee account information|
|6 | Owner saves the changes by clicking the confirm button |

### Use case 3, UC3 - Disable Employee
| Actors Involved        | Owner |
| ------------- |:-------------:|
|  Precondition     | Owner is logged |
| Post condition | An existing employee account has been disable from access the EZShop Application |
|  Nominal Scenario     | The owner select with the appropriate button the employee section, then he click over a button that prompt a windows that must be filled with the new employee's information |

##### Scenario 3.1 - Nominal
|  Scenario 3.1  | |
| ------------- |:-------------:|
| Description | Owner wants disable an employee that is already in the system for the EZShop |
| Precondition |  Owner is logged in |
| Post condition | The existing employee account has been disable from access the EZShop Application |
|  Step#     | Description |  
|  1     | Owner click on the employees section |  
|  2     | Owner search the user that wants to modify |  
| 3 | Owner selects the modify command for that employee|
|4 | System presents the employee details. The screen opens in edit mode|
|5|Owner disable the employee account|
|6 |Owner saves the changes|


### Use case 4, UC4 - Create a discount
| Actors Involved        | Manager |
| ------------- |:-------------:|
|  Precondition | Manager is logged in |  
|  Post condition | The discount is successfully created a product and the EZShop inventory is updated |
|  Nominal Scenario     | The manger search a product from the list, clicks over it and create a discount |
|  Variants     | For that product another discount is still valid, a confirmation alert is promoted to the manager |

##### Scenario 4.1 - Nominal
|  Scenario 4.1  | |
| ------------- |:-------------:|
|  Description | Manager wants to create a discount on a products in the inventory   |
| Precondition |  Manager is logged in |
|  Post condition | The discount is successfully created a product and the EZShop inventory is updated |
|  Step#     | Description |  
| 1 | Manager searches the product from the list in the inventory section|
| 2 | Select products |
| 3 | Create a discount |
| 4 | Manager saves the changes |
| 5 | The system re-calculate the product price |
| 6 | The system updates the product inventory with the discount|

##### Scenario 4.2
|  Scenario 4.1  | |
| ------------- |:-------------:|
|  Description | Manager wants to create a discount on a products in the inventory, but the product has already a valid discount  |
| Precondition |  Manager is logged in |
|  Post condition | The old discount is replace with the new one |
|  Step#     | Description |  
| 1 | Manager searches the product from the list in the inventory section|
| 2 | Select products |
| 3 | Create a discount |
| 4 | System prompt an alert because there is a valid discount on the product and the manager confirmation  |
| 4 | Manager click of the confirmation button |
| 5 | The system re-calculate the product price |
| 6 | The system updates the product inventory with the discount|


### Use case 5, UC5 - Create a new fidelity card
| Actors Involved        | Manager |
| ------------- |:-------------:|
|  Precondition     | The cashier wants to create a new fidelity card for a customer |  
|  Post condition     | A new customer and the related fidelity card is added to the system |
|  Nominal Scenario     | The cashier select with the appropriate button the custom section, then he click over a button that prompt a windows that must be filled with the new customer information |
|  Variants     | Owner insert customer's information that already match with one customer in the system The application will notify this information in the same window |

##### Scenario 5.1 - Nominal
|  Scenario 5.1  | |
| ------------- |:-------------:|
| Description | The cashier wants to add a new customer and its fidelity card|
|  Precondition     | The cashier wants to create a new fidelity card for a customer |  
|  Post condition     | A new customer and the related fidelity card is added to the system |
|  Step#     | Description |  
|  1     | Cashier clicks on the customer section |  
|  2     | Cashier clicks on the create new customer button |
|  3     | A window is opened with all necessary fields to be filled |
|  4     | Cashier enters the new customer information |
|  5     | Owner confirm the operation by clicking on the appropriate button |

##### Scenario 5.2
|  Scenario 5.2  | |
| ------------- |:-------------:|
| Description | Owner wants to add a new employee that is already in the system for the EZShop|
|  Precondition     | The cashier wants to create a new fidelity card for a customer |  
|  Post condition     | The customer is not added to the system because already present |
|  Step#     | Description |  
|  1     | Cashier clicks on the customer section |  
|  2     | Cashier clicks on the create new customer button |
|  3     | A window is opened with all necessary fields to be filled |
|  4     | Cashier enters the new customer information |
|  5     | Owner confirm the operation by clicking on the appropriate button |


### Use case 6, UC6 - Manage a sale transaction
| Actors Involved        | Cashier |
| ------------- |:-------------:|
|  Precondition | Cashier is logged in the system |  
|  Post condition | The sales record is stored in the system and the inventory is updated |
|  Nominal Scenario     | The cashier starts a new transaction, cashier scans the product and the system computes the total. At the end the cashier get paid  |
|  Variants 1    | The customer wants to pay with the credit card |
|  Variants 2    | The scanner doesn't read correctly the product code |
|  Variants 3    | The customer wants to use a discount code |

##### Scenario 6.1 - Nominal
|  Scenario 6.1  | |
| ------------- |:-------------:|
|  Description | The cashier want to perform a sale transaction for a customer that wants to buy some products with cash  |
|  Precondition | Cashier is logged in the system |  
|  Post condition | The sales record is stored in the system and the inventory is updated |
|  Step#     | Description |  
| 1 | Cashier scans all products with the bar code scanner |
| 2 | Once all products have been scanned, the cashier ends the transaction |
| 3 | The system computes the total and apply the VAT |
| 4 | Cashier get paid and the transaction is recorded into the system|

##### Scenario 6.2
|  Scenario 6.2  | |
| ------------- |:-------------:|
|  Description | The cashier want to perform a sale transaction for a customer that wants to buy some products with credit card  |
|  Precondition | Cashier is logged in the system |  
|  Post condition | The sales record is stored in the system and the inventory is updated |
|  Step#     | Description |  
| 1 | Cashier scans all products with the bar code scanner |
| 2 | Once all products have been scanned, the cashier ends the transaction |
| 3 | The system computes the total and apply the VAT |
| 4 | Cashier get paid via the credit card system
|5|The transaction is recorded into the system|
| 6 | The system prints the ticket|

##### Scenario 6.3
|  Scenario 6.3  | |
| ------------- |:-------------:|
| Description | The cashier isn't able to scan a product, the product code in inserted directly from the keyboard |
|  Precondition | Cashier is logged in the system |  
|  Post condition | The sales record is stored in the system and the inventory is updated |
|  Step#     | Description |  
| 1 | Cashier scans all products with the bar code scanner except one |
| 2 | The cashier insert the product code directly in to the transaction via the keyboard |
| 3 | Once all products have been scanned, the cashier ends the transaction |
| 4 | The system computes the total and apply the VAT |
| 5 | Cashier get paid via the credit card system
| 6 | The transaction is recorded into the system|
| 7 | The system prints the ticket|

##### Scenario 6.4
|  Scenario 6.4  | |
| ------------- |:-------------:|
|  Description | The cashier want to perform a sale transaction for a customer that wants to buy some products and has a coupon |
|  Precondition | Cashier is logged in the system |  
|  Post condition | The sales record is stored in the system and the inventory is updated |
|  Step#     | Description |  
| 1 | Cashier scans all products with the bar code scanner |
| 2 | Once all products have been scanned, the cashier ends the transaction |
| 3 | Cashiers insert the coupons given by the customer |
| 4 | The system computes the total and apply the VAT |
| 5 | Cashier get paid and the transaction is recorded into the system|
| 6 | The system prints the ticket|

##### Scenario 6.5
|  Scenario 6.5  | |
| ------------- |:-------------:|
|  Description | The cashier want to perform a sale transaction for a customer that wants to buy some products, using the fidelity card |
|  Precondition | Cashier is logged in the system |  
|  Post condition | The sales record is stored in the system and the inventory is updated |
|  Step#     | Description |  
| 1 | Cashier scans the fidelity card |
| 2 | Cashier scans all products with the bar code scanner |
| 3 | Once all products have been scanned, the cashier ends the transaction |
| 3 | The system computes the points for the current sale transaction |
| 3 | The points are added to the current points |
| 4 | The system computes the total and apply the VAT |
| 5 | Cashier get paid and the transaction is recorded into the system|
| 6 | The system prints the ticket with the number of points|


### Use case 7, UC7 - Log in
| Actors Involved        | Cashier |
| ------------- |:-------------:|
|  Precondition     | The employee is not logged in |  
|  Post condition     | The employee is logged into the system, all functions based on the access rights are available  |
|  Nominal Scenario     | The employee enters his id and password in the login form and then clicks on the confirmation button |
|  Variants     | The employee enters wrong a wrong id or password, the system notifies a login error |


##### Scenario 7.1 - Nominal

| Scenario 7.1 | |
| ------------- |:-------------:|
| Description | The employee wants to log in to the system in order to use its functions  |
| Precondition |  The employee is not logged in the system and the application is already opened and running in the computer |
| Postcondition |  The employee is logged in |
|  Step#     | Description |  
|  1    | The employee click on the login button and a form is opened |  
|  2    | The employee enter his credential |
|  3    | The employee confirm with the appropriate button |

##### Scenario 7.2

| Scenario 11.2 | |
| ------------- |:-------------:|
| Description | The employee wants to log in to the system in order to use its functions  |
| Precondition |  The employee is not logged in the system and the application is already opened and running in the computer |
| Postcondition |  The employee is not logged in |
|  Step#     | Description |  
|  1     | The employee click on the login button and a form is opened |  
|  2     | The employee enter wrong credential |
|  3     | The employee can enter again the credential |




### Use case 8, UC8 - Modify price of an item
| Actors Involved        | Owner |
| ------------- |:-------------:|
|  Precondition | Owner is logged in |  
|  Post condition | Successfully modify price of the product in the inventory|
|  Nominal Scenario     | The owner select with the appropriate button the inventory section, then he select the product and insert the new price |

##### Scenario 8.1 - Nominal
|  Scenario 8.1  | |
| ------------- |:-------------:|
|  Description | The owner wants to modify the price of a product in the inventory  |
| Precondition |  The owner is logged in the system and the application is already opened and running in the computer |
| Postcondition |  The price of the product is updated with the new one |
| 1 | Owner searches the product from the list in the inventory section|
| 2 | Owner select the product |
| 3 | Owner enter the new price |
| 4 | Owner save the changes |


### Use case 9, UC9 - Employee statistics
| Actors Involved        | Owner |
| ------------- |:-------------:|
|  Precondition     | The owner is logged in |  
|  Post condition     | The statistics of the employee are shown to the owner |
|  Nominal Scenario     | The owner select with the appropriate button the employee section, then he click over a button that prompt the employee's statistics |


##### Scenario 9.1 - Nominal

| Scenario 9.1 | |
| ------------- |:-------------:|
| Description | Owner to get the number of sales in this week for a specific cashier|
| Precondition |  Owner is logged in|
|  Post condition     | The statistics of the employee are shown to the owner |
|  Step#     | Description |  
|  1     | Owner click on the statistics section |  
|  2     | Owner search and select a cashier |
|  3     | A window is opened and the owner can select the week he want to check|
|  4     | The system generates the statistics for that cashier for the selected week |


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
}

class "Fidelity card" {
	+ id
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
note top of Right : Different rights are\nassociated depending\non the employee type.
note bottom of Statistic : For each cashier a list\nof possible statistics\nis available in order to\nevaluate the performance.

@enduml
```

# System Design

```plantuml
@startuml
"EZShop System" o-- Computer: is composed
"EZShop System" o-- Server: is composed

Computer o-- "Bar code scanner"
Computer o-- "Keyboard"
Computer o-- "Touchscreen"
Computer o-- "Mouse"
Computer o-- "Software"
Computer o-- "Ticket machine"

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
The system is based on a client-server pattern. Since the software can be used both for generating statistics about the employee and mages the inventory, it can work without the Ticket machine and the Bar code scanner.
