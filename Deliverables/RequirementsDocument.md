# Requirements Document 

Authors:

Date: 08/04/2021

Version:

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
- [access right, actor vs function](#access-right-actor-vs-function)
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
| OWNER |own the shop. want to use the application in order to make more efficient the shop's administration and to check  the performance of the shop and the employees   |
| MANAGER| manages the application, can insert or delete produtcs from the inventory, order products to the suppliers, supervise the shop and the others employees (cashier/salesmen)|
| CASHIER/SALESMAN| handle the sales and the costumers|
|CUSTOMER| //TO DO?//|


# Context Diagram and interfaces

## Context Diagram
\<Define here Context diagram using UML use case diagram>

\<actors are a subset of stakeholders>

## Interfaces
\<describe here each interface in the context diagram>

\<GUIs will be described graphically in a separate document>

| Actor | Logical Interface | Physical Interface  |
| ------------- |:-------------:| -----:|
|MANAGER| GUI |Screen Keyboard on Pc, mouse|
|OWNER |GUI| Screen Keyboard on Pc,mouse|
|CASHIER/SALESMAN |GUI| Screen Keyboard on Pc,mouse|

# Stories and personas
\<A Persona is a realistic impersonation of an actor. Define here a few personas and describe in plain text how a persona interacts with the system>

\<Persona is-an-instance-of actor>

\<stories will be formalized later as scenarios in use cases>
1.Sharon,37,single mother of two girls aged 3 and 6 years, owner of a small
 grocery store is committed to providing hers customers 
 seasonal and quality fruit and vegetables but admits that often reconcile management an activity and
 life as a mother is not so easy.
 This affects the organization of products in the warehouse that ends up rotting and must be thrown. For this reason,she would like to know, week by week which are the  goods that have been sold less than the other, so that the next week she will order a bit less, according to the numbers.

2.Fabio,30,sporty, busy and dynamic man,is a owner of a several small sports supplement stores.
  He has made fitness and wellness his purpose of life and given the great demand 
  of the market for these products his revenue is increasing considerably. For this reason he 
  is thinking of hiring a manager to help him manage one of his shops.
  His main interest is to continue to manage every aspect of the shop at his best even if will not be always present.
  
3.Alicia 44, a small businesswoman from Turin, owns a lovely shoes boutique.
  With the pandemic and with the economic crisis she is facing hard times but she does not give up to close permanently. 
  Every day she searchs in the list of sold product, written in the receipts, which one have been sold in order to compute manually the remaining inventory. She would    like to  have a software that helps her to keep track of all products, so that her shop will be never out of stock and never excess.

4.Tom 22, an economics student to pay for his studies, works in a small hardware store,
 thanks to his skills with numbers and bureaucracy, he was recently promoted to manager of the activity.
 So now he has to take care of monitoring all aspects of the store, from inventory, to sales, to relationships with suppliers.
 He is very happy with his new role but he is aware of the responsibilities it entails 
 and he would like to be able to combine work and studies well. So he is looking for an application that  allow him to satisfy the same tasks in less time and in a more 
efficient way.

5.Jonathan 32, is the owner of a small book shop in an town; he has a tight budget and in order to cut the costs he has one stable cashier and some students that work on call, when they are free. He would like to have a software that is simply able to record his employess work shifts.


# Functional and non functional requirements

## Functional Requirements

\<In the form DO SOMETHING, or VERB NOUN, describe high level capabilities of the system>

\<they match to high level use cases>

| ID        | Description  |
| ------------- |:-------------:| 
|  FR1 |Manage rights.Authorize access to functions to specific actors according to accesss rights|
|FR2|Manage employees|
|FR2.1| Define or modify a new employee (ex: promotion to manager, hiring)|
|FR2.2 |Delete employee (fire)|
|FR2.3 |List of all the employees|
|FR2.4| Statistics of Employee-> cashier(daily earnings)|
|FR2.5 |Search Employee|
|FR3|Handle inventory|
|FR3.1 |Search product|
|FR3.2 |Order product to the suppliers|
|FR3.3 |Add product|
|FR3.4 |Remove product (automatically+ manually)|
|FR3.5 |List of products + prices+ number of products orderd by some criterion(list of multiple choices)|
|FR3.6| Notification when product is out of stock|
|FR4|Manage customers|
|FR4.1 |Create a new fidelity card (with an ID)|
|FR4.5 |Add new customer + unique id |
|FR4.2 |List of all the customers|
|FR4.3 |Mark points every tot of shop (ex every 50 spent give them 1 point after 10 point 20% discount)|
|FR4.4| Give a discount |
|FR5|Manage sales|
|FR5.1| List of best selling products|
|FR5.2 |List of daily sales|
|FR5.3 |List of offers (ex with products in expiration)|
|FR5.4 |Create an offer|
|FR5.5 |Delete offer|
|FR5.7 |Create a gift card|
|FR5.8 |Delete gift card|
|FR6|Access to the system|
|FR6.1 |Log in|
|FR6.2 |Log out|
|FR7|Monitor incomes|
|FR7.1| Daily income|
|FR7.2 |Monthly income| 
|FR7.3 |Year income|
|FR8|Register a sale payment |
|FR8.1| scan product|
|FR8.2 |Compute the sum|
|FR8.3 |apply possible discount|
|FR8.4 |use possible gift card|
|FR8.5 | get the receipt|
|FR8.6 |get paid (credit card, cash)|
|FR8.7 |store the information about sales, cashier and customer|
|FR8.8 |update inventory|

# access right, actor vs function

| Function        | Owner | Manager | Salesman|
| ------------- |:-------------:| ------------- |:-------------:| 
|FR2.1| yes| no| no|
|FR2.2| yes| no |no| 
|FR2.3|yes| yes|no|  
|FR2.4|yes| yes|no| 
|FR1| yes| no |no| 
|FR3.2|yes| yes|no|  
|FR3.3| yes| yes|no| 
|FR4| yes| yes |yes| 
|FR5 |yes| yes |no| 
|FR6 |yes| yes |yes| 
|FR7 |yes| no/yes?|no| 


## Non Functional Requirements

\<Describe constraints on functional requirements>

| ID        | Type (efficiency, reliability, ..)           | Description  | Refers to |
| ------------- |:-------------:| :-----:| -----:| 
|NFR1| USABILITY | every customer with at least 3+ years experience in using a Pcs must be able to use all functions with no training in less than 30 minutes|??|
|NFR2| PERFORMANCE| All function should respond in <0.5 sec|??|
|NFR3| PRIVACY| the sistem must be standard to the current GDPR and must store data in a safe way|??|           
|NFR4| AVAILABILITY |at least 99%|??|
|NFR5| PORTABILITY| the application should be accessed from the operating system used in the shop.|??|
|NFR6| SECURITY| the data of one salesman can be visible and midifiable only by the owner and the manager.The data should be disclosed to other salesmen at the same or lower level.The system must be protected from an unauthorized access by using id and password|??|
# Use case diagram and use cases


## Use case diagram
\<define here UML Use case diagram UCD summarizing all use cases, and their relationships>

\<ideas of use cases>
| UC     | Description |
| ------------- |:-------------:|
|UC1.| Create account for new employee (hire employee and manager and owner)|
|UC2. |Modify account for employee (ex promotion)|
|UC3. |Delete account for employee (fire employee)|
|UC4. |Add product to the inventory|
|UC5. |Order product to the supplier|
|UC6. |Create an offer|
|UC7. |Delete an offer ?|
|UC8. |Add new fidelity card (Add new customer)|
|UC9. |Mark point to the fidelity card (modify customer)|
|UC10.|Give a discount|
|UC11.|Search item|
|UC12 |Log in|
|UC13 |Modify price of an item|
|UC14 |Show the inventory of all items|
|UC15 |Check daily sales|
|UC16 |Accounting (per day, week, month, year)|


\<next describe here each use case in the UCD>
### Use case 1, UC1
| Actors Involved        |  |
| ------------- |:-------------:| 
|  Precondition     | \<Boolean expression, must evaluate to true before the UC can start> |  
|  Post condition     | \<Boolean expression, must evaluate to true after UC is finished> |
|  Nominal Scenario     | \<Textual description of actions executed by the UC> |
|  Variants     | \<other executions, ex in case of errors> |

##### Scenario 1.1 

\<describe here scenarios instances of UC1>

\<a scenario is a sequence of steps that corresponds to a particular execution of one use case>

\<a scenario is a more formal description of a story>

\<only relevant scenarios should be described>

| Scenario 1.1 | |
| ------------- |:-------------:| 
|  Precondition     | \<Boolean expression, must evaluate to true before the scenario can start> |
|  Post condition     | \<Boolean expression, must evaluate to true after scenario is finished> |
| Step#        | Description  |
|  1     |  |  
|  2     |  |
|  ...     |  |

##### Scenario 1.2

##### Scenario 1.x

### Use case 2, UC2
..

### Use case x, UCx
..



# Glossary

\<use UML class diagram to define important terms, or concepts in the domain of the system, and their relationships> 

\<concepts are used consistently all over the document, ex in use cases, requirements etc>

# System Design
\<describe here system design>

\<must be consistent with Context diagram>

# Deployment Diagram 

\<describe here deployment diagram >

