# java-console-calculator

This program takes in a user input as a string, parses, simplifies, and finally evaluates the input.

## Syntax
```
<< 2+2
>> 2+2 = 4.0

<< 95-10+2^(3+3)*10
>> 95-10+2^(3+3)*10 = 725.0

<< sin(20)+pi*2
>> sin(20)+pi*2 = 7.196130557907214
```
You can access the previous answer by using ```<``` inside the expression

```
<< pi^2
>> pi^2 = 9.869604401089358

<< 2+<
>> 2+9.869604401089358 = 11.869604401089358
```

## How to run the program:
First, compile:
```
javac Run.java
```

Next, execute the program:
```
java Run
```

## Known Issues
- Spaces not supported `2 + 2 - e`
