Classes contain methods. You can call a private or public class from within the class, but only a public method from outside the class.
Method are constructed of:
1. an optional access specifier, **public, private or protected** (package by default which cannot be called outside the package in which it is declared)
2. an **optional `static` modifier**, which means that the code inside the method runs without instantiating an object using that method.
3. a **return type**, `void, int, string` ETC, the datatype that the method returns.
4. the method's name (**identifier**)
5. **parentheses** containing the data to be sent to the method, can be empty

OOP uses **implementation hiding**, meaning that the "user" of a method only passes the method variable and receives a return, but does not know what happens within the method.

Statements that are put after a return statement are **unreachable** as they will never execute, also called **dead code**.

Curly braces within a method block create a **nested block**. Variables declared in the nested block are only accessible within this block.

**Overloading** a method is the act of defining multiple methods with the same name that require different arguments. When calling an overloaded method, **the datatype of the arguments changes which method runs**. 
![[chrome_uLkVn53X8T.png]]

This is very useful, but can lead to some ambiguity. For example, if we had these two methods:
`public static void calculateinterest(int bal, double rate)`
`public static void calculateinterest(double bal, int rate)
These methods have different types, BUT we can imagine a situation where we try to pass two `int` types to this method. There is no exact match for the method call and so it wouldn't compile.
