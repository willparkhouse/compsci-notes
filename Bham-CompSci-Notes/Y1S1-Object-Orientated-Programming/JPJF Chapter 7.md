**Strings**
Strings are not a primitive data type in Java, and so there is some differences when working with them. If we were to `string1 == string2`, this compares the memory address of each string rather than the contents. 

There are 6 ways to declare strings in Java, we have seen the literal way:
`String myString = "Hello World"`
We can also use the `StringBuffer` and `StringBuilder` classes.

**`Character` Methods**
`char` is a primitive data type in Java, and `Character` is an inbuilt class, with several useful methods for working with `char`, such as:
	isUpperCase()
	isLowerCase()
	toUpperCase()
	toLowerCase()
	isDigit()
	isLetter()
	isLetterOrDigit()
	isWhitespace()

**Strings**
A **literal string**/**string literal** is a sequence of characters enclosed within double quotes, e.g. “This is a string! :^)” Literal strings are ‘**anonymous objects**’ (unnamed) of the String class

String aGreeting = “Hey!”
In the above example, aGreeting is the variable name that stores a reference (**memory address**) to the string object. aGreeting does **NOT** hold the characters that make up the string, but rather the memory address where the characters are stored. If you assign a new value to a string variable, the memory address held by the variable name changes, though the original string still exists in memory (it will eventualy be discarded by the system). Therefore, strings are ‘immutable’ (never actually changed, variable just contains a new reference)

You can only ever compare if two strings are exactly the same because if so, their value is only stored once in memory so they will share an address (the comparison will be true because the ADDRESS is the same, not the actual string), they are stored in the string pool.

The **.equals()** methods tests if two strings contents are the same:
The method **.equalsIgnoreCase()** is the same as the .equals() method, but is NOT case sensitive.
The .**compareTo()** method compares strings and returns an integer based on the comparison (0 if equal +n if calling > argument and –n if calling < argument). The **.compareTo()** comparison is based on Unicode values, so being ‘more than’ = alphabetically ‘more’ = lower in an alphabetical list

Some useful String methods include:
**.toUpperCase()** / **.toLowerCase()**
**.length()**
**.indexOf()**
**.charAt()**
**.endsWith()** / **.startsWith()** (returns a boolean value)
**.replace()** allows you to replace all occurrences of a chosen character in a String
**.contains()** allows you to check if one string contains another string (case sensitive) and returns a boolean
**.toString()** converts objects of other classes to strings
**.subString()** can be used to extract part of a string, taking one or two arguments (begin OR begin and end index)
**.regionMatches()** can test if two string ‘regions’ are the same, either by taking four arguments (1.start of region in first string, 2.other string name, 3.start of region in second string, 4.length of comparison), or by taking a boolean as the first argument (true = not case-sensitive, false = case-sensitive) followed by the four other arguments
To convert a string to an integer: **Integer.parseInt**(“123”);
To convert a string to a double: **Double.parseDouble**(“123”);

**Using the StringBuilder and StringBuffer classes**

StringBuilder and StringBuffer are classes that can be used as an alternative to the String class, as they allow strings to be modified. **StringBuilder** is **more efficient**, but **StringBuffer** is ‘thread safe’ (if an application runs multiple threads of execution to create multiple paths of control)

To create a new StringBuilder object:
`StringBuilder message = new StringBuilder(“Hello world!”);`
If taking in user input in the form of strings that you will want to edit, convert the input from string to a StringBuilder:
`String stringName = keyboard.nextLine();`
`StringBuilder name = new StringBuilder(stringName);`

StringBuilders are not allocated a specific amount of memory to accommodate their string, but rather a memory block called a ‘buffer’
**.setLength()** changes length of string in StringBuilder object
If the length of a StringBuilder is not full, the unoccupied characters contain ‘\u0000’
If you use .setLength() to set the length to shorter than the string, the string is ‘truncated’
**.capacity()** is a method that returns the capacity of a StringBuilder object

· When you create a StringBuilder object with a String argument, its capacity is the (length of the String + 16) (because 16 = 4 bytes)

· You can assign a capacity when you create a StringBuilder object:

E.g. capacity = 300

StringBuilder prettyBigString = new StringBuilder(300);

· Your programmes will be more efficient if you allow for adequate capacity in the first place, rather than having to repeated increase capacity

· CAREFUL! .equals() will compare references if used on a StringBuilder object – if you want to compare these objects you must convert them to strings first using .toString()

· .append() allows you to add characters to the end of a StringBuilder object

· Use .append() on an empty StringBuilder object to convert a string to a StringBuilder:

String name = "Raj";

StringBuilder convertedName = new StringBuilder();

convertedName.append(name);

· .insert() allows you to add characters to a specific locations within a StringBuilder object: phrase.insert(n, “string”) n = starting index

· Use .setCharAt() to alter just one character in the StringBuilder object

· Use .charAt() to extract a character from a StringBuilder object, taking its index as an argument

· Index must always be greater than zero and less than the last position in the object

Summary:

· If you know you will be changing a string frequently in a programme, use StringBuilder

*