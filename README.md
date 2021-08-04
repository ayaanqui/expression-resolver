# Expression Resolver

[![](https://jitpack.io/v/ayaanqui/expression-resolver.svg)](https://jitpack.io/#ayaanqui/expression-resolver) [![License: MIT](https://img.shields.io/badge/License-MIT-yellow.svg)](https://opensource.org/licenses/MIT) [![HitCount](http://hits.dwyl.com/{username}/{project-name}.svg)](http://hits.dwyl.com/ayaanqui/expression-resolver)

The Expression Resolver for Java provides a very easy way to solve any valid mathematical expression. The string based expression is parsed, and then reduced to a single numeric value. If the experssion was unable to reduce completely, the program tries to give clear error messages, such that the user is notified. _(Note: The program escapes all whitespaces and `$` signs)_

## Features

### Built-in math operators

-   Addition: `+`
-   Subtraction: `-`
-   Multiplication: `*`
-   Division: `/`
-   Exponent: `^`
-   Parentheses: `(` and `)`

**_\*Note:_** Numbers/Variables followed directly by `(` sign do not get identified as multiplication. Therefore, they must be shown explicitly (Ex. use `2*(1+1)` instead of `2(1+1)`). However, this is not the case if a `-` sign is followed by `(`, `-(2*1)` is equivalent to `-1*(2*1)`.

### Built-in functions

| Function | Description                | Inverse  | Parameter(s)   |
| -------- | -------------------------- | -------- | -------------- |
| `sin`    | Sine (radians)             | `arcsin` | `n`            |
| `cos`    | Cosine (radians)           | `arccos` | `n`            |
| `tan`    | Tangent (radians)          | `arctan` | `n`            |
| `sqrt`   | Square root                | `N/A`    | `n`            |
| `ln`     | Natural Log (log base `e`) | `exp`    | `n`            |
| `log`    | Log                        | `N/A`    | `n, base`      |
| `deg`    | Convert radians to degrees | `N/A`    | `n` (radians)  |
| `rad`    | Convert degrees to radians | `N/A`    | `n` (degrees)  |
| `abs`    | Absolute value             | `N/A`    | `n`            |
| `fact`   | Factorial (!)              | `N/A`    | `n` (`n >= 0`) |
| `avg`    | Average                    | `N/A`    | `n1, ..., nk`  |
| `sum`    | Summation                  | `N/A`    | `n1, ..., nk`  |

### Built-in mathematical constants

-   PI (π): `pi` (`3.141592653589793`)
-   Euler's number (e): `e` (`2.718281828459045`)
-   Tau (τ or 2\*π): `tau` (`6.283185307179586`)

## Set up

### Apache Maven

```xml
<dependencies>
    ...

    <dependency>
        <groupId>com.github.ayaanqui</groupId>
        <artifactId>expression-resolver</artifactId>
        <version>2.0</version>
    </dependency>
</dependencies>
```

### Gradle

```gradle
allprojects {
    repositories {
        ...

        maven { url "https://jitpack.io" }
    }
}
```

```gradle
dependencies {
    ...

    implementation 'com.github.ayaanqui:expression-resolver:master-SNAPSHOT'
}
```

## Usage

To set up ExpressionResolver, first make sure to import all necessary packages.

```java
import com.github.ayaanqui.expressionresolver.Resolver;
import com.github.ayaanqui.expressionresolver.objects.Response;
```

Once these packages have been imported you can start using `Resolver`

```java
// Create ExpressionResolver object
Resolver calculator = new Resolver();
```

A `Resolver` object gives access to methods:

-   `setExpression` Takes in a string expression
-   `setFunction` Define function
-   `expressionList`
-   `getExpression` Returns expression set using `setExpression`
-   `getLastResult` Returns last successfully solved expression
-   `solveExpression` Solves the expression set using `setExpression` or `expressionList`. _Returns [Response object](#response-object)_

### Setting expressions

```java
Resolver res = new Resolver();

// First value
double value1 = res
    .setExpression("473+5711-sin(20)"); // Returns Resolver object
    .solveExpression() // Returns Response object...
    .result; // Holds double value computed by solveExpression()

// Second value
double value2 = res
    .setExpression("sum(53, 577, 19493, 374)"); // Returns Resolver object
    .solveExpression() // Returns Response object...
    .result; // Holds double value computed by solveExpression()
```

### `Response` object

This object is returned by `solveExpression` which holds all the information about the solved expression:

-   `success` Returns a `boolean` value indicating whether the expression was reduced without an error
    -   `true` when the expression was reduced with no error
    -   `false` when there was an error reducing the expression
-   `result` If `success == true` then `result` holds the double value of the reduced expression
-   `errors` If `success == false` then `errors` holds an String array (`String[]`) describing each error

## Examples

### Basic use case

```java
Resolver calculator = new Resolver();

calculator.setExpression("2+2");
double result = calculator.solveExpression().result; // 4

calculator.setExpression("95-10+2^(3+3)*10");
result = calculator.solveExpression().result; // 725.0

calculator.setExpression("sin(20) + pi * 2");
result = calculator.solveExpression().result; // 7.196130557907214
```

### Accessing last result

Using the `<` operator allows access to the last successfull result

```java
Resolver calculator = new Resolver();

calculator.setExpression("-pi^2");
Response res = calculator.solveExpression();
res.result // 9.869604401089358

calculator.setExpression("2+<");
calculator.solveExpression().result; // 11.869604401089358
```

### Nested parentheses

Detects mismatched, or empty parentheses

```java
Resolver solver = new Resolver();

solver.setExpression("1+((((((((((((1-1))))+2+2))))))))");
double value = solver.solveExpression().result; // 5

solver.setExpression("ln(((((((sin(tau/2))))))))-(((1+1)))");
double v2 = solver.solveExpression().result; // -38.63870901270898


// Mismatch parentheses error:
solver.setExpression("(1-2)/sin((3*2)/2");
Response res = solver.solveExpression();
// Check for errors
if (!res.success)
    System.out.println("Error: " + res.errors[0]); // Error: Parentheses mismatch
```

### Variables

Assigned using the `=` operator. (_Note: once a variable is assigned, the value cannot be changed_)

```java
Resolver solver = new Resolver();

// Declaring a new variable
double v1 = solver.setExpression("force = 10*16.46")
                .solveExpression()
                .result; // 164.60000000000002

// Using variable "force"
// force = 164.60000000000002
// pi = pre-defined π constant
double v2 = solver.setExpression("force + pi")
                .solveExpression()
                .result; // 167.7415926535898

// Results in an error (res.success = false)
Response res = solver.setExpression("1 = 2").solveExpression();
if (res.success == false)
    System.out.println("Error:\n" + res.errors[0] + "\n" + res.errors[1]);

// Results in an error (res.success = false)
// All variables are immutable (constant or unchangeable)
Response res = solver.setExpression("pi = 3.142").solveExpression();
if (res.success == false)
    System.out.println("Error:\n" + res.errors[0] + "\n" + res.errors[1]);
```

### Defining functions

Functions can be defined by using `setFunction` method which takes two parameters: `String` function name, and `Function<Double[], Double>` function definition.

```java
Resolver res = new Resolver();

// Defining min function
double minVal = res
    .setExpression("min(45, 9, 22, pi, 644, 004, 192)")
    //           Name   Function Definition
    .setFunction("min", params -> {
        double min = params[0];
        for (double val : params)
            if (val < min)
                min = val;
        return min;
    })
    .solveExpression().result; // pi

// Defining force function
double val = res
    .setFunction("force", params -> {
        return params[0] * params[1];
    })
    .setExpression("force(27, 10)")
    .solveExpression()
    .result;

// Redfining built-in function arcsin
res.setFunction("arcsin", params -> 1 / Math.sin(params[0]));
```
