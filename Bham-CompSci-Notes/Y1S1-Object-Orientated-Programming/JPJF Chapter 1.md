[[JPJF Chapter 1]]
__Java Overview pdf__

Java is a high level language. Programs are written in Java Source Files (plaintext), these are parsed by the Java Compiler to byte-code and executed by the Java Virtual Machine (JVM)

This allows Java code to be ran on many different architectures 
	-(write anywhere, run everywhere)
	
Two types of errors are __syntax errors__ and __logic errors__. Code with syntax errors won't run, whereas code with logic errors will run but won't function as expected.

Java is an OOP, converting the problem space we want to solve into **classes** and **objects**.

Classes describe a blueprint of executions for problems we want to solve. Each class has attributes (information about the class) and methods (actions the class can perform). 

We can generate concrete versions of these classes into objects which have their own unique attributes and actions.

EG Say we have a class for information about cats, with attributes such as age, name and breed. We can then instantiate Objects corresponding to each cat. 
		Cat1 → Salem, 4, Domestic Short Hair  
		Cat2 → Ginger, 2, Persian  
		Cat3 → Whiskers, 6, Maine Coon

All Java programs require a java file with a main class, with boilerplate as shown below:

```java
public class Main {  
	public static void main(String args []) {  
			System.out.println("Hello World");  
	}  
}
```

The capitalised `Main` is the class name, and this **must** match the title of the file containing the main object. As a rule all java files require a class with the same name as the file.

(The `static` keyword means the class works without being instantiated.)

This boilerplate is required for the program to compile correctly.

![[chrome_YvpOkpwr06.png]]
	This shows us the anatomy of a Java statement. 

Java can output to command line or via a GUI. We run tests in the command line usually.

To run a program from command line we type:
`javac Main.java
`java Main`

Use // for single line comments in java, or /* and \*\/ for multi line comments

**NAMING CONVENTION

Use Camel Case
	-Start variables with a lowercase letter, then every next word with a Uppercase letter
		eg dogNames, speakerBrands, myMedication
	-Start class names with an uppercase letter
		eg TwosComplementer, WowCoolClass, PotatoMasher
Java identifies cannot use the Java reserved keywords, or true, false and null
![[chrome_1n3b2x9zpi.jpg]]
	(Java reserved keywords)

**Scope

When a variable is declared the scope of it is within the class / object it is called within. Variables called in the mainclass (and no deeper) are global variables. To always use globals variables is bad practice, as it uses lots of memory unnecessarily and can make keeping track of variables difficult.1rr

**Primitive Data Types**
By 'primitive' we mean that the data type is not a class. 
Some examples of primitive data types are:
	byte
	short, int, long
	float, double
	boolean
	char
NOTE: string is not a primitive type, and is in fact a built-in class.

**Encapsulisation**
In object-oriented classes, attributes and methods are encapsulated into objects. Encapsulation refers to two closely related object-oriented notions:
	❯Encapsulation is the **enclosure of data and methods within an object**. Encapsulation allows you to treat all of an object’s methods and data as a single entity. Just as an actual dog contains all of its attributes and abilities, so would a program’s Dog object.
	❯ Encapsulation also refers to the **concealment of an object’s data and methods** from outside sources. Concealing data is sometimes called information hiding, and concealing how methods work is **implementation hiding**. Encapsulation lets you hide specific object attributes and methods from outside sources and provides the security that keeps data and methods safe from inadvertent changes.

**Polymorphism**
Polymorphism allows methods to have different results based on context. EG say we are programming a GUI - the method change_height() is useful for buttons, sliders, windows etc, and this can be shared amongst various different objects.

**Basic GUI Program**

```
import javax.swing.JOptionPane;  
public class Main {  
    public static void main(String[] args)  
    {  
        JOptionPane.showMessageDialog(null, "Hello world!");  
    }  
}
```
This code imports the package javax.swing.JOptionPane. Packages to be imported are declared outside classes.