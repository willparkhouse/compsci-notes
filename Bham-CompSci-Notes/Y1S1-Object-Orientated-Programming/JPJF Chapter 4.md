	An **object** can be a member of **multiple classes**.

An application is a class that contains the PSVM method.

Objects are **abstract data types**, often called **composite data types**.

A class client / user is a class that instantiates objects of another class.

A class header is made of 3 parts:
	-Optional access specifier
	-The keyword `class`
	-Any legal identifier (usually in Camel Case)

**Public vs Private**
When creating a class, often we make the attributes (variables / data) **private**, but make methods for accessors / mutators (set/get) **public**.

A field may be made **public**, for example, when you need to access it directly, but often we like to adhere to **implementation hiding** as a matter of convention.

```java
public class Employee
{
	public static final int COMPANY_TAX_NUMBER = 248584;
	private int empNum;
	private String empName;
	
	public int getEmpNum(){
		return empNum;
	}
	
	public void setEmpNum(empNum){
		this.empNum = empNum;
	}

	public String getEmpName(){
		return empName;
	}

	public void setEmpName(empName){
		this.empName = empName;
	}
}
```

***Static vs Non-Static***
When you make a data field static, then it's value is shared across all objects, if you change the value on one object, that value changes across all other objects. If 100 different objects were instantiated from this class, the `static` variables are only stored once in memory.

Imagine an `employee` class, you might need a variable for the companies tax number, and say I had a piece of code that needed this tax number, you might not want to call  `john.COMPANY_TAX_NUMBER` but just `Employee.COMPANY_TAX_NUMBER`. This would be made a `static` variable, and would want to be homogenous company-wide. 

Variables that are non-static are called **instance variables**. Each object gets a copy of the variables and methods at instantiation. Static variables / methods are called **class variables / methods.**

**Declaring Objects**
To declare a new object there is a two stage process. First we give the object an identifier like: 
`Employee johnSmith;`
This notifies the compliler that we are creating a new object of the class `Employee` called `johnSmith`, BUT it does not allocate the memory for that new object. For that we have to:
`johnSmith = new Employee();`
The `new` keyword allocates a space in memory for this object, we can put these two steps in one line like:
`Employee johnSmith = new Employee();`

**Constructors**
When using the above line of code, we employ the Java compilers default **constructor**. This instantiates the object `johnSmith` with:
	-numeric fields set to `0
	-character fields set to `\\u0000`
	-booleans fields set to `false`
	-fields that are object references, for example String, are set to `null`
	
If we do not want each field in an object to hold these default values, we can write our own constructor. Any constructor we write:
	-must have the same name as the class it constructs
	-cannot have a return type - not even `void`
	
Constructors are placed **within** our class.

```java
public class Employee
{
	private int empSalary;
	private int empNum;
	public Employee(int num)
	{
		empSalary = 800.00;
		empNum = num;
	}
}
```
The above constructor sets the default `empSalary` to `800.00`, and `empNum` is set to the number we provide when we instantiate an Employee object. Now we have a custom constructor for this class, we must pass it an `int` for `empNum` like: 
`Employee johnSmith = new Employee(676);
`
We cannot leave this blank anymore, but say we still wish to create an employee object but have not decided on an `empNum` yet, for this we would have two constructors like:
```java
public class Employee
{
	private int empSalary;
	private int empNum;
	public Employee(int num)
	{
		empSalary = 800.00;
		empNum = num;
	}
	public Employee()
	{
		empSalary = 800.00;
		empNum = 999;
	}
}
```

This means that Employee objects are instantiated with 999 by default, unless we provide an `int` at creation. 

**`this` Keyword**
You might notice that `num` is not a particularly distinct variable name, we might want to instead name the constructor's attribute `empNum`. BUT we then run into an issue of `empNum = empNum` within the constructor, which assigns an empty value to itself. 

To get around this we can use the `this` keyword, which allows us to reference the specific objects value for `empNum`.
```java
public class Employee
{
	int empNum;
	public Employee(int empNum)
	{
		this.empNum = empNum;
	}
}
```

The `this` keyword means that `this.empNum` refers to the field in the `Employee` class and the `empNum` refers to the locally declared variable that we pass the constructor when we instantiate an object.

The `this` keyword can also be used to overload constructors. In the example below, we could write 4 constructors as in 4-24, but if we want to add code to restrict `stuNum` to between 0 and 999, or GPA to below 4, we would need to edit each constructor. Instead, we can use the `this` keyword to call the two-parameter constructor. Because each of these constructors calls one **master constructor**, we would only need to edit the code in this master constructor.![[Pasted image 20221014225538.png]]

**More On `static` Methods & Variables**
`static` variables are homogenous amongst all objects within the class. You can use them, for example, to track the number of objects of that type. With our employee class we would:
```java
public class Employee
{
	private int empNum;
	private static int numOfEmployees;
	public Employee(int empNum)
	{
		this.empNum = empNum;
		numOfEmployees = numOfEmployees + 1;
	}
}
```

In the example used prior, `COMPANY_TAX_NUMBER`, we would declare like so:
`private static final int COMPANY_TAX_NUMBER = 258132;`
Note the `final` keyword makes the variable immutable. `final` variables must be set at declaration.

**Using Imported, Pre-written Constants & Methods**
You can import classes, like we have with `System` and `JOptionPane`, that have pre-written classes and features to write code faster. These are both **packages**, or a **library of classes**. `java.lang` is a package that is implicitly imported to every Java program, this contains the `System` class. We can import other packages like `java.time` or `javax.swing`, which contain the `LocalDate` and `JOptionPane` classes respectively.
The `Math` class is part of `java.lang`, and is imported implicitly. It contains Pi and e as `static double` values, and various methods as shown below:
![[Pasted image 20221015005410.png]]
To import custom classes you can either:
	1. Use the entire path with the class name
	2. Import the class
	3. Import the package that contains the class
Using 2 would look like `import java.time.Localdate`, and you would declare by:
`LocalDate myAnniversary;`
Using 3 would look like `import java.time.*` (with the * as a wildcard, importing all classes in that package), and you would declare by:
`java.time.LocalDate myAnniversary;` 

**Using the `LocalDate` Class**
A `LocalDate` object can be created using several approaches. For example:
	`LocalDate today = LocalDate.now()` returns the current date.
	`LocalDate graduation = LocalDate.of(2024, 5, 29) ` returns the date specified.
Notice that these objects are instantiated with `.of()` and `.now()`, instead of the `new` keyword. This is because the class's constructors are not `public`.
A `LocalDate` object can be displayed as a string, or can return `int` or `enum` type, depending on the method used to call it.

**Composition and Nested Classes**
Classes can contain primitive data types as fields, but also can contain other classes. For example say we have a `NameAndAddress` class that stores the postal address in the correct formatting, and then we have a customer class that contains the `NameAndAddress` class as a field. We must remember to supply values for the cotained object if it has no default constructor. We say that the contained object and the object with the object field inside it **has-a relationship**.

A **nested class** is a class that is defined within another class. The containing class is a **top-level class**. There are four types of nested class:
	-**`static` member classes** - has access to all static methods of the top-level class.
	-**Nonstatic member classes**, aka **inner classes** - requires and instance, and has access to all data and methods in the top-level class.
	-**Local classes** - local to a block of code
	-**Anonymous classes** - local classes with no identifier
We often nest classes that are only used by the top-level class. Packaging them together can make the code easier to understand and maintain.
