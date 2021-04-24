1
2
3
4
5
6
7
8
9
10
11
12
13
14
15
16
17
18
19
20
21
22
23
24
25
26
27
28
29
30
31
32
33
34
35
36
37
38
39
40
41
42
43
44
45
46
47
48
49
50
51
# Project Estimation  
Authors:
Date:
Version:
# Contents
- [Estimate by product decomposition]
- [Estimate by activity decomposition ]
# Estimation approach
<Consider the EZGas  project as described in YOUR requirement document, assume that you are going to develop the project INDEPENDENT of the deadlines of the course>
# Estimate by product decomposition
### 
|             | Estimate                        |             
| ----------- | ------------------------------- |  
| NC =  Estimated number of classes to be developed   |                             |             
|  A = Estimated average size per class, in LOC       |                            | 
| S = Estimated size of project, in LOC (= NC * A) | |
| E = Estimated effort, in person hours (here use productivity 10 LOC per person hour)  |                                      |   
| C = Estimated cost, in euro (here use 1 person hour cost = 30 euro) | | 
| Estimated calendar time, in calendar weeks (Assume team of 4 people, 8 hours per day, 5 days per week ) |                    |               
# Estimate by activity decomposition
### 
|         Activity name    | Estimated effort (person hours)   |             
| ----------- | ------------------------------- | 
| | |
###
Insert here Gantt chart with above activities
```plantuml
@startuml
printscale daily
Project starts the 1th of April 2021
[Requirements Planning] as [TASK1] lasts 12 days
[TASK1] is colored in Red

[Perform workflow analysis] lasts 1 day


[Model process] lasts 1 days
[Perform workflow analysis]->[Model process]

[Identify user requirements] lasts 1 days
[Model process]->[Identify user requirements]

[Identify performance requirements] lasts 1 days
[Identify user requirements]->[Identify performance requirements]

[Identify interface requirements] lasts 1 days
[Identify performance requirements]->[Identify interface requirements]

[Design GUI] lasts 2 days
[Identify interface requirements]->[Design GUI]

[Prepare software requirements specification] lasts 5 days
[Design GUI]->[Prepare software requirements specification]





[Requirements V&V] as [TASK1.1] lasts 1 days
[TASK1.1] is colored in LightBlue
[TASK1.1] starts at [TASK1]'s end

[Requirements inspection] lasts 1 days
[TASK1]->[Requirements inspection]








[Design document] as [TASK2] lasts 3 days
[TASK2] is colored in Red
[TASK2] starts at [TASK1]'s end

[Analysis] lasts 1 days
[TASK1]->[Analysis]

[Formalization] lasts 1 days
[Analysis]->[Formalization]

[Verification] lasts 1 days
[Formalization]->[Verification]

[Design document V&V] as [TASK2.1] lasts 1 days
[TASK2.1] is colored in LightBlue
[TASK2.1] starts at [TASK2]'s end

[Design document inspection] lasts 1 days
[TASK2]->[Design document inspection]



[Coding] as [TASK3] lasts 15 days
[TASK3] is colored in Red
[TASK3] starts at [TASK2.1]'s end

[Implement the code] lasts 15 days
[TASK2.1]->[Implement the code]

[Documentation] as [TASK3.1] lasts 1 days
[TASK3.1] is colored in LightBlue
[TASK3.1] starts at [TASK2.1]'s end

[Explain design document and code] lasts 1 days
[TASK2.1]->[Explain design document and code]



[System test] as [TASK4] lasts 3 days
[TASK4] is colored in Red
[TASK4] starts 2 days after [TASK3]'s end

[Test all units of application] lasts 7 days
[Test all units of application] starts 2 days after [TASK3]'s end



[Test plan] as [TASK1.2] lasts 1 days
[TASK1.2] is colored in LightBlue

[Prepare testing process] lasts 1 days


[Unit test] lasts 7 days
[Unit test] starts 15 days after [TASK1.2]'s end 
[Unit test] is colored in LightBlue

[Test each unit of the software] lasts 7 days
[Test each unit of the software] starts 15 days after [TASK1.2]'s end 

[Integration test] lasts 3 days
[Integration test] starts 2 days after [Unit test]'s start 
[Integration test] is colored in LightBlue
[Combine individual units and test them as a group] lasts 3 days
[Combine individual units and test them as a group] starts 2 days after [Unit test]'s start
@enduml
```
