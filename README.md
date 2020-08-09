# Number Processor
The application accepts numbers and does multiple computation on the numbers in **parallel** or in **sequential**.

Table of Contents
=================

   * [Computations](#computations)
   * [Examples](#examples)
   * [Run Command](#run-command)
   * [Download Jar Build](#download-jar-build)
   * [Docker Build](#docker-build)

Created by [Alok Singh](https://github.com/alokkusingh)

## Computations
- `Addition` - add the numbers 
- `Multiplication` - multiply the numbers
- `Subtraction` - subtract the numbers

## Examples
- Example 1:

    Sample Input:
    1 1 1 1 1 SEQUENTIAL

    Sample Output:
    ERROR: "At least 2 numbers must be different"

    Explanation: All five numbers are with the same value "1".

- Example 2:

    Sample Input:
    3 4 1 5 6 SEQUENTIAL

    Sample Output:
    Add: 19
    Multi: 360
    Sub: -7
    Time: 10s
    Type: SEQUENTIAL
    Numbers: [6, 5, 4, 3, 1]

    Explanation: in this case, Add operation executed first and took 2s. Then Mult operation executed and took 5s. Finally Sub operation took 3s, giving a total of 10s.

- Example 3:

    Sample Input: 
    5 4 5 5 5 PARALLEL

    Sample Output:
    Mult: 20
    Sub: 1
    Add: 9
    Time: 5s
    Type: PARALLEL
    Numbers: [5, 4]

    Explanation: in this case, Add (5s), Mult (1s) and Sub (4s) operations started at the same time and the longest one defined the final execution time result of 5s.


## Run Command
Run the application by executing following command
```
cd number-processor
java -jar target/number-processor-0.0.1-SNAPSHOT.jar 1 2 6 4 3 PARALLEL
```
Or
```
cd number-processor
java -jar target/number-processor-0.0.1-SNAPSHOT.jar 1 2 6 4 3 SEQUENTIAL
``` 

## Download Jar Build
Get the latest build from [number-processor](https://github.com/alokkusingh/number-processor/packages/349251)

## Docker Build
Build Docker Image
```
cd number-processor
docker build -t alokkusingh/number-processor:1.0.0 --build-arg JAR_FILE=target/number-processor-1.0.0.jar .
``` 
Pull/Run Docker Container
```
docker run --name number-processor alokkusingh/number-processor:1.0.0 1 2 6 4 3 SEQUENTIALL
```
