# Expression Resolver

[![](https://jitpack.io/v/ayaanqui/expression-resolver.svg)](https://jitpack.io/#ayaanqui/expression-resolver) [![License: MIT](https://img.shields.io/badge/License-MIT-yellow.svg)](https://opensource.org/licenses/MIT) [![HitCount](http://hits.dwyl.com/{username}/{project-name}.svg)](http://hits.dwyl.com/ayaanqui/expression-resolver)

The Expression Resolver for Java provides a very easy way to solve any valid mathematical expression. The string based expression is parsed, and then reduced to a single numeric value. If the experssion was unable to reduce completely, the program tries to give clear error messages, such that the user is notified.  _(Note: The program escapes all whitespaces and `$` signs)_

## Usage (as a Maven package)
```java
import com.github.ayaanqui.ExpressionResolver.Expression;

public class MyClass {
    public static void main(String args[]) {
        // Create ExpressionResolver object
        Expression calculator = new Expression();

        calculator.setExpression("1+1 - sin( pi*exp(2) )");
        double result = calculator.solveExpression().result; // 2.9398721563036108

        calculator.setExpression("a = ln(10*45+35)/(2^7)");
        result = calculator.solveExpression().result; // 0.04831366321044909

        calculator.setExpression("a+1");
        result = calculator.solveExpression().result; // 1.048313663210449
    }
}
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


## Running as a calculator

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

## Compile

```
mvn package
```

## Run
```
mvn exec:java
```