# Project Estimation  
Authors: Battilana Matteo, Huang Chunbiao, Mondal Subhajit, Sabatini Claudia

Date: 22/04/2021

Version: 1.0
# Contents
- [Project Estimation](#project-estimation)
- [Contents](#contents)
- [Estimation approach](#estimation-approach)
- [Estimate by product decomposition](#estimate-by-product-decomposition)
- [Estimate by activity decomposition](#estimate-by-activity-decomposition)

# Estimation approach
<Consider the EZGas  project as described in YOUR requirement document, assume that you are going to develop the project INDEPENDENT of the deadlines of the course>
# Estimate by product decomposition
###
|             | Estimate                        |             
| ----------- | ------------------------------- |  
| NC =  Estimated number of classes to be developed   |               21          |             
|  A = Estimated average size per class, in LOC       |           180                 |
| S = Estimated size of project, in LOC (= NC * A) | 3780 |   
| E = Estimated effort, in person hours (here use productivity 10 LOC per person hour)  |                378                     |   
| C = Estimated cost, in euro (here use 1 person hour cost = 30 euro) | 11340 |
| Estimated calendar time, in calendar weeks (Assume team of 4 people, 8 hours per day, 5 days per week ) |               2.36     |               
# Estimate by activity decomposition
###
|         Activity name    | Estimated effort (person hours)   |             
| ----------- | ------------------------------- |
| Requirements planning | **112**|
| &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Review existing systems | 8 |
| &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Perform work analysis | 8 |
| &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Model process | 4 |
| &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Identify user requirements | 16 |
| &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Identify performance requirements | 14 |
| &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Evaluate hardware requirements  | 14 |
| &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Redact requirements document  | 8 |
| &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Design GUI prototype  | 32 |
| &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;V&V of the requirements document  | 8 |
| Design | **50** |
| &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Analysis of the design | 10 |
| &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Formalization of the design | 24 |
| &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;V&V of the design | 16 |
| Implementation | **150** |
| &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Code | 150 |
| &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Documentation | 16 |
| Testing | **74** |
| &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Unit testing | 27  |
| &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Integration test | 15 |
| &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;On site test (at the shop) | 8 |
| &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;GUI testing | 24 |
| Configuration and Installation | **16** |
| &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Database Set up |4 |
| Management | **416** |
| &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Human Resource Management | 416 |
| &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Project Management| 416 |




###
```plantuml
@startuml
printscale daily
[Requirements Planning] as [TASK1] lasts 14 days
[TASK1] is colored in #FF7700

[Review existing systems] lasts 1 day

[Perform work analysis] lasts 1 days
[Review existing systems]->[Perform work analysis]

[Model Process] lasts 1 days
[Perform work analysis]->[Model Process]

[Identify user requirements] lasts 2 days
[Model Process]->[Identify user requirements]

[Identify performance requirements] lasts 2 days
[Identify user requirements]->[Identify performance requirements]

[Evaluate hardware requirements] lasts 2 days
[Identify performance requirements]->[Evaluate hardware requirements]

[Redact Requirements document] lasts 1 days
[Evaluate hardware requirements]->[Redact Requirements document]

[Design GUI prototype] lasts 4 days
[Redact Requirements document]->[Design GUI prototype]

[Requirements V&V] as [TASK1.1] lasts 1 days
[TASK1.1] is colored in orchid
[TASK1.1] starts at [TASK1]'s end


[Design] as [TASK2] lasts 5 days
[TASK2] is colored in #FF7700
[TASK2] starts at [TASK1]'s end

[Analysis] lasts 2 days
[TASK1]->[Analysis]

[Formalization] lasts 3 days
[Analysis]->[Formalization]

[Design V&V] as [TASK2.1] lasts 2 days
[TASK2.1] is colored in orchid
[TASK2.1] starts at [TASK2]'s end


[Implementation] as [TASK3] lasts 19 days
[TASK3] is colored in #FF7700
[TASK3] starts at [TASK2.1]'s end

[Code] lasts 19 days
[TASK2.1]->[Code]

[Documentation] as [TASK3.1] lasts 1 days
[TASK3.1] is colored in orchid
[TASK3.1] starts at [TASK2.1]'s end


[Testing] as [TASK4] lasts 10 days
[TASK4] is colored in #FF7700
[TASK4] starts at [TASK3]'s end

[Unit Test] lasts 4 days
[Unit Test] starts at [TASK3]'s end

[GUI Test] lasts 3 days
[Unit Test]-> [GUI Test]

[Integration test] lasts 2 days
[GUI Test]-> [Integration test]
[Integration test] is colored in orchid

[On site test] lasts 1 days
[Integration test]-> [On site test]

[Configuration and Installation] as [TASK5] lasts 2 days
[TASK5] is colored in #FF7700
[TASK5] starts at [TASK4]'s end

[Database Set up] lasts 1 days
[Database Set up] starts at [TASK4]'s end

[Management] as [TASK1.2] lasts 52 days
[TASK1.2] is colored in #FF7700

[Human Resource Management] lasts 52 days
[Human Resource Management] is colored in orchid

[Project Management] lasts 52 days
@enduml
```
