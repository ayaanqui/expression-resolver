# Java Console Calculator

This program takes in a user input as a string, parses the expression, and reduces the expression to a single numeric value. If the experssion was unable to reduce completely, the program tries to give clear error messages, such that the user is notified. _(Note: The program escapes all whitespaces and `$` signs)_

## Usage

Example with mixed operations
```
>> 2+2
4.0

>> 95-10+2^(3+3)*10
725.0

>> sin(20)+pi*2
7.196130557907214
```

Access last result by using `<`

```
>> -pi^2
9.869604401089358

>> 2+<
11.869604401089358
```

Nested parentheses, with support for detecting mismatching pairs

```
>> 1+((((((((((((1-1))))+2+2))))))))
5

>> ln(((((((sin(tau/2))))))))-(((1+1)))
-38.63870901270898

>> (1-2)/sin((3*2)/2
 *Parentheses mismatch
```

Variables assigned using the `=` operator. (_Note: once a variable is assigned, the value cannot be changed_)

```
>> force = 10*16.46
164.60000000000002

>> force + pi
167.7415926535898

>> 1 = 2
 *Variable names cannot start with a number
 *Variables cannot be reassigned

>> pi = 3.142
 *Variable names cannot start with a number
 *Variables cannot be reassigned
```

### Supported math operators

1. Addition: `+`
2. Subtraction: `-`
3. Multiplication: `*`
4. Division: `/`
5. Exponent: `^`
6. Parentheses: `(` and `)`

**_\*Note:_** Numbers/Variables followed directly by `(` sign do not get identified as multiplication. Therefore, they must be shown explicitly (Ex. use `2*(1+1)` instead of `2(1+1)`). However, this is not the case if a `-` sign is followed by `(`, `-(2*1)` is equivalent to `-1*(2*1)`.

### Supported math functions

_All functions listed, require a single parameter followed by opening and closing parentheses._

1. Sine: `sin`, Inverse sine: `arcsin`
2. Cosine: `cos`, Inverse cosine: `arccos`
3. Tangent: `tan`, Inverse tangent: `arctan`
4. Natural Log (log base `e`): `ln`
5. Square root: `sqrt`
6. Absolute value: `abs`
7. Factorial (!): `fact`
8. `e^x`: `exp`

### Supported mathematical constants

_All constants can be used directly, without the need of any parameters_

1. PI (π): `pi` (`3.141592653589793`)
2. Euler's number (e): `e` (`2.718281828459045`)
3. Tau (τ or 2\*π): `tau` (`6.283185307179586`)

## Build/Compile (_with Maven_)

```
mvn package
```

## Run
```
mvn exec:java
```

## Create executable jar file

```
mvn clean compile assembly:single
```
```
java -jar ./target/Calculator-1.0-SNAPSHOT-jar-with-dependencies.jar
```