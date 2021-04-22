# Project Estimation  
Authors: Battilana Matteo, Huang Chunbiao, Mondal Subhajit, Sabatini Claudia

Date: 22/04/2021

Version: 1.0
# Contents
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
| &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Integration testing | 15 |
| &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;On site test (at the shop) | 8 |
| &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;GUI testing | 24 |
###

```plantuml
@startgantt
[Requirements Planning] as [T1] lasts 27 days
[T1] is colored in Application
[Review existing systems] lasts 1 days
[Perform work analysis] lasts 2 days
[Model process] lasts 2 days
[Identify user requirements] lasts 5 days
[Identify performance requirements] lasts 4 days
[Evaluate hardware requirements] lasts 3 days
[Redact requirements document] lasts 4 days
[Design GUI prototype] lasts 6 days
[V&V of the requirements document] lasts 3 days

[Review existing systems] -> [Perform work analysis]
[Perform work analysis] -> [Model process]
[Model process] -> [Identify user requirements]
[Identify user requirements] -> [Identify performance requirements]
[Identify user requirements] -> [Evaluate hardware requirements]
[Evaluate hardware requirements]-> [Redact requirements document]
[Identify performance requirements]-> [Redact requirements document]
[Redact requirements document]-> [Design GUI prototype]
[Design GUI prototype]-> [V&V of the requirements document]



[Design] as [T2] lasts 9 days
[T2] is colored in Ivory
[Analysis of the design] lasts 4 days
[Formalization of the design] lasts 4 days
[V&V of the design] lasts 3 days

[V&V of the requirements document] -> [T2]
[T1] -> [T2]
[V&V of the requirements document] -> [Analysis of the design]
[Analysis of the design] -> [Formalization of the design]
[V&V of the design] starts 2 days after [Formalization of the design]'s start


[Implementation & Unit Testing] as [T3] lasts 20 days
[T3] is colored in Orange
[Code] lasts 17 days
[Documentation] lasts 20 days
[Unit testing] lasts 15 days

[V&V of the design] -> [T3]
[T2] -> [T3]
[V&V of the design] -> [Code]
[V&V of the design] -> [Documentation]
[Documentation] starts 1 days after [Code]'s start
[Unit testing] starts 5 days after [Code]'s start



[Integration test & GUI test] as [T4] lasts 7 days
[T4] is colored in DarkCyan
[Integration testing] lasts 7 days
[GUI testing] lasts 7 days

[Documentation] -> [T4]
[T3] -> [T4]
[Documentation] -> [GUI testing]
[Documentation] -> [Integration testing]

@endgantt

```
