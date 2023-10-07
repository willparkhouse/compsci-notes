**Chapter 9 – Inheritance and Interfaces**

9.1 Learning about the concept of Inheritance

·         Created classes can inherit the data and methods of existing classes, facilitated by inheritance

·         Therefore, you can create a new class simply indicating the ways it differs from an existing class

·         ‘base class’ / ‘superclass’ / ‘parent class’ = class used as a basis for inheritance

·         ‘derived class’ / ‘subclass’ / ‘child class’ = class that inherits from a base class (is-a/instanceof relationship)

·          

E.g.

Employee receptionist = new Employee();

Employee deliveryPerson = new Employee();

9.2 Extending Classes

·         The keyword ‘extends’ is used to achieve inheritance, creating a superclass-subclass relationship:

public class EmployeeWithTerritory extends Employee

·         You use the above code to inherit all data fields and methods of the superclass, and then you can add new fields and methods to the class

·         To instantiate a subclass object:

EmployeeWithTerritory northernRep = new EmployeeWithTerritory();

9.3 Overriding Superclass Methods

·         Overriding a field/method = using subclass version of an element instead of the superclass version

·         If you create a method in the subclass with the same name of a method in the superclass that serves a distinct (but related) purpose, object of the subclass will intuitively use the subclass’ method

·         When overriding a method, you MUST use the same name and parameter list

·         If you create a subclass method with the same name but different parameter list, you are OVERLOADING not overriding, so objects can access either the subclass or superclass method based on the parameters passed

·         A subclass can use an overridden superclass method using the keyword ‘super’ -> super(field1, field2, field3)

·         ‘@Override’ is an annotation you can prior to an overridden method, which indicates an intention to override (rather than create new) a method to the compiler

9.4 Calling Constructors During Inheritance

·         Instantiating a subclass object = calling subclass AND superclass constructor

·         Superclass constructor executes _first_

·         Usually, subclass constructor only has to initialise its specific data fields

Using Superclass Constructors that Require Arguments

·         If a superclass has a constructor that requires arguments, all of its subclasses MUST provide the correct arguments to the constructor

·         If a superclass uses....

·         The default constructor (empty), a subclass can be created with or without its own constructor, subclass will automatically use default if they do not have their own constructor

·         Only constructors that require arguments (= not default constructor), each subclass MUST call one of the superclass constructors within the first statement within its own constructor

·         ![](file:///C:/Users/Music/AppData/Local/Packages/oice_16_974fa576_32c1d314_2971/AC/Temp/msohtmlclip1/01/clip_image002.png)

·         If you must create a subclass constructor with parameters that the subclass does not have, use the keyword ‘super’:

public HourlyEmployee()

{

super('P', 22.35, 40);

// Other statements can go here

}

·         OR (if subclass constructor does require arguments/parameters)

public HourlyEmployee(char dept, double rate, int hours)

{

super(dept, rate, hours);

// Other statements can go here

}

·         super(data fields) must always be the first statement in a subclass constructor

9.5 Accessing Superclass Methods

·         If you want to access a method from superclass that has already been overridden in the subclass, use the keyword ‘super’:

Superclass – display method displays two fields:

![](file:///C:/Users/Music/AppData/Local/Packages/oice_16_974fa576_32c1d314_2971/AC/Temp/msohtmlclip1/01/clip_image003.png)

Subclass – user superclass display method then adds another field of its own to be displayed:

![](file:///C:/Users/Music/AppData/Local/Packages/oice_16_974fa576_32c1d314_2971/AC/Temp/msohtmlclip1/01/clip_image005.png)

Combining ‘this’ and ‘super’

·         ‘this’ and ‘super’ _can_ refer to the same method, but not always

·         If a subclass has overridden a superclass method

super.someMethod -> superclass

someMethod() & this.someMethod() -> subclass

·         If a subclass has NOT overridden a superclass method, all method calls using ‘this’, ‘super’, or no keyword will point to superclass method

9.6 Employing Information Hiding

·         Information hiding = using private fields that can only be edited using public methods

·         Subclasses can access all data fields and methods of the superclass HOWEVER subclasses cannot access private superclass data fields within their own methods

·         To allow subclasses to directly access these data fields in their methods (/if they cannot be accessed with getters), use the keyword ‘protected’ rather than private

·         ‘protected’ = can be used within superclass and associated subclasses, but not in any “outside” classes

·         ‘protected’ should not be used for convenience as it can increase class ‘fragility’, making them more prone to errors

9.7 Methods You Cannot Override

·         Static, final, and methods within final classes CANNOT be overridden

Static Methods

·         A subclass can hide (not the same as overriding) a static method in the superclass by declaring a static method in the subclass with the same signature as the static method in the superclass

·         Then you can either add another method or more code to achieve your intention or use superclassName.staticMethodName():

![](file:///C:/Users/Music/AppData/Local/Packages/oice_16_974fa576_32c1d314_2971/AC/Temp/msohtmlclip1/01/clip_image007.png)

·         ‘super’ cannot be used inside static methods of the subclass

Final Methods

·         Using ‘final’ indicates a method that you DON’T want to be overridden

Final Superclasses

·         In a final class, _all_ of its methods are final

·         Final classes cannot be superclasses

9.8 Creating and Using Abstract Classes

·         Abstract class = a class purely created to serve as a superclass, not a class from which you instantiate objects

·         Abstract class = opposite of final class

·         Use the keyword ‘abstract’ when defining an abstract class

Methods in Abstract Classes

·         Methods in abstract methods _can_ either be nonabstract or abstract:

·         Nonabstract method = implemented in the class and inherited by subclasses

·         Abstract method = no method body – this is implemented _in the subclasses_ (abstract class usually contain at least 1), use ‘abstract’ keyword for clarity

·         Inherited abstract methods must be overridden in the subclass – though it can either remain abstract or it can be implemented

![](file:///C:/Users/Music/AppData/Local/Packages/oice_16_974fa576_32c1d314_2971/AC/Temp/msohtmlclip1/01/clip_image009.png)

9.9 Using Dynamic Method Binding

·         Each object of each subclass is-a superclass object, so subclass objects can be converted to superclass objects

·         When creating an object from a subclass whose superclass is abstract, you can indirectly reference it as a superclass abstract object (still not an _actual_ instantiated object of the superclass)

·         To create a reference to the superclass object, use a variable name to hold the memory address of a concrete object

·         In the example below, Animal is an abstract class and the different subclasses Cow and Dog have already been created:

![](file:///C:/Users/Music/AppData/Local/Packages/oice_16_974fa576_32c1d314_2971/AC/Temp/msohtmlclip1/01/clip_image011.png)

Syntax -> variableReferenceToSuperclass = new subclassObject

·         In the above example, the method speak() is called on the most recently referenced object

·         ‘Dynamic/late method binding’ = selecting the correct subclass method depending on argument type (done _during execution_)

·         ‘Static/fixed method binding’ = opposite of dynamic method binding, faster but less flexible

Using a Superclass as a Method Parameter Type

·         Useful if you want to use a parameter that could be of several types – e.g. you could take any Animal as a parameter to encompass the objects that come from Animal’s subclasses

![](file:///C:/Users/Music/AppData/Local/Packages/oice_16_974fa576_32c1d314_2971/AC/Temp/msohtmlclip1/01/clip_image013.png)

9.10 Creating Arrays of Subclass Objects

·         An array contains object references, not actual objects

·         An array of superclass (whether abstract or concrete) references can hold subclass references

·         E.g. you could make a generic Animal array that contains objects of different Animal subclasses

Animal[] animalRef = new Animal[3]

·         Above code does NOT instantiate 3 objects (impossible because Animal is abstract), simply reserves space for three object references

![](file:///C:/Users/Music/AppData/Local/Packages/oice_16_974fa576_32c1d314_2971/AC/Temp/msohtmlclip1/01/clip_image015.png)

9.11 Using the Object Class and Its Methods

·         All classes you create in Java are technically subclasses of the Object class (technically every class would have ‘extends Object’ in its declaration)

·         The Object class contains methods that all descendent class can use/overload/override:

![](file:///C:/Users/Music/AppData/Local/Packages/oice_16_974fa576_32c1d314_2971/AC/Temp/msohtmlclip1/01/clip_image017.png)

toString()

·         toString() -> converts object to a string, method of the Object class that you can create for yourself or just use this superclass version

·         The Object toString() method will output a hash code (a calculated number that uniquely identifies an object)

·         You can overload toString() so that it produces are more readable output, which can help to debug programmes by illustrating class behaviour/contents during execution

equals()

·         The Object method equals() returns true when it compares two objects with identical hash codes (and no two objects of a class will have the same hash code unless you deliberately change one)

·         You could overload/override equals() so that it compares objects based on contents rather than reference:

![](file:///C:/Users/Music/AppData/Local/Packages/oice_16_974fa576_32c1d314_2971/AC/Temp/msohtmlclip1/01/clip_image019.png)

·         The above example overloads the Object methods by providing a different parameter lists

·         To override the method, use the same signature as the superclass method, and cast Object parameter to the appropriate class parameter (e.g. BankAccount)

Recommendations for Overriding Equals (to prevent errors in more complex programmes):

1.  Determine if the equals() argument is the same object as the calling object by using a comparison such as obj == this and return true if it is.

2.  Return false if the Object argument is null.

3.  Return false if the calling and argument objects are not the same class.

4.  Cast the Object argument to the same type as the calling object only if they are the same class.

5.  You should override the hashCode() method as well, because equal objects should have equal hash codes.

9.12 Creating and Using Interfaces

·         ‘multiple inheritance’ = a subclass that inherits from more than one superclass – PROHIBITED in Java due to issues with polymorphism

·         In Java, subclasses CAN still inherit from other subclasses

·         ‘interface’ = similar to a class, contains only public, abstract method headers (without a method body) and only public, static, final fields

·         ‘interface’ describes what a class DOES, not how it is done and allows unrelated objects to interact

·         To create a class that uses an interface, use the keyword ‘implements’, and the class MUST include its own code for EVERY method the interface implements

![](file:///C:/Users/Music/AppData/Local/Packages/oice_16_974fa576_32c1d314_2971/AC/Temp/msohtmlclip1/01/clip_image020.png)

![](file:///C:/Users/Music/AppData/Local/Packages/oice_16_974fa576_32c1d314_2971/AC/Temp/msohtmlclip1/01/clip_image021.png)

·         Thus a workingDog object can....

![](file:///C:/Users/Music/AppData/Local/Packages/oice_16_974fa576_32c1d314_2971/AC/Temp/msohtmlclip1/01/clip_image023.png)

·         You CANNOT instantiate concrete objects from interfaces OR abstract classes

·         Abstract classes can contain nonabstract methods, but interfaces can contain ONLY abstract methods

·         Create an abstract class when you want to provide data/methods for a subclass to inherit but override if required

·         Create an interface when you know what actions you want classes to include, but each of them must separately define the behaviour of the action (method)

·         E.g. a MusicalInstrument interface where each instrument object’s outputSound() method is unique

·         Create an interface if you want a class to implement behaviour from multiple superclasses

![](file:///C:/Users/Music/AppData/Local/Packages/oice_16_974fa576_32c1d314_2971/AC/Temp/msohtmlclip1/01/clip_image025.png)

Creating Interfaces to Store Related Constants

Why are interface data fields...?

·         Static -> because you cannot create objects from interfaces

·         Public -> because without public methods, you cannot access private fields

·         Final -> without public methods, there is no way to edit values

·         Interfaces provide a set of constant data that classes can use, rather than having to redeclare values themselves

![](file:///C:/Users/Music/AppData/Local/Packages/oice_16_974fa576_32c1d314_2971/AC/Temp/msohtmlclip1/01/clip_image027.png)

9.13 Using records, Anonymous Inner Classes, and Lambda Expressions

Using ‘records’

·         The ‘record’ keyword can be used to declare a class that serves purely to hold final data with simple get/set requirements

·         A ‘record’ only contains field definitions, constructors, and getters, the code for each is then automatically generated

E.g. Instead of....

![](file:///C:/Users/Music/AppData/Local/Packages/oice_16_974fa576_32c1d314_2971/AC/Temp/msohtmlclip1/01/clip_image029.png)

Use....

![](file:///C:/Users/Music/AppData/Local/Packages/oice_16_974fa576_32c1d314_2971/AC/Temp/msohtmlclip1/01/clip_image031.png)

·         With each ‘record’, you generate a private final field for each element, a public constructor, a public accessor method for each component and implementations of equals(), hashCode() and toString()

·         Do NOT use a record if you need to implement setters or more complicated getters

·         Do NOT use a record if you need to extend a class or need an abstract class

Using Anonymous Inner Classes

·         ‘Anonymous inner class’ = a nameless class defined within another class, useful if you only need to instantiate a class once in a programme

·         AICs can either extend superclasses or implement an interface, but without the ‘extends’ or ‘implements’ keywords

·         The below example declares a washingMachine object without naming a WashingMachine class, which then overrides the work() method:

![](file:///C:/Users/Music/AppData/Local/Packages/oice_16_974fa576_32c1d314_2971/AC/Temp/msohtmlclip1/01/clip_image033.png)

·         CAREFUL! Be sure to include a semicolon after implementing the anonymous inner class (see above washingMachine.work();)

Using Lambda Expressions

·         ‘Interface’ = a description of _what_ a class does, but NOT _how_ it is done (declares methods headers but not method body)

Example:

![](file:///C:/Users/Music/AppData/Local/Packages/oice_16_974fa576_32c1d314_2971/AC/Temp/msohtmlclip1/01/clip_image035.png)

·         Lambda expression = Worker washingMachine = () -> {method}

·         Lambda expressions create an object that implements a ‘functional interface’, which is an interface containing one ‘abstract method’

·         In the above example, washingMachine is an object implementing the Worker interface, because the Worker interface contains only one method, the lambda expression knows how to use it

·         The () indicates that the method takes no parameters, otherwise it would contain comma separated arguments

·         Simple Lambda Expressions:

parameter -> expression

OR

(parameter1, parameter2) -> expression

OR

(parameter1, parameter2) -> {code block}

·         Expressions CANNOT contain variables, assignments, or statements such as if or for UNLESS curly braces are used (making a code block), otherwise the must immediately return a value

·         If a lambda expressions needs to return a value, it must have a return statement

·         Lambda expressions themselves are often passed as parameters to a function

·         Lambda expressions can be stored in a variable IF...

▫   The variables type is an interface with only one method

▫   The lambda expression has the same number of parameters as the that method

▫   The lambda expression has the same return type as the that method

·         Many suitable interfaces are built into Java (java.util, e.g. the Consumer interface that is used by lists)

![](file:///C:/Users/Music/AppData/Local/Packages/oice_16_974fa576_32c1d314_2971/AC/Temp/msohtmlclip1/01/clip_image037.png)

*****