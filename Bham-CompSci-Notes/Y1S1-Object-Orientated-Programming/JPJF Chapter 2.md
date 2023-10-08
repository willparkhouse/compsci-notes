**Using Data**
A data item is a constant when its value cannot be changed while the program is running. For example: `System.out.println(459)`. 459 will be printed irrespective of other code. This is referred to as a literal constant (bc it's value is taken literally), a numeric constant and an unnamed constant. 

Java has 8 primitive data types:
byte - byte-length integer
short - short integer
int - integer
long - long integer
float - single-precision floating point
double - double-precision floating point
char - a single character
boolean - true / false

To declare a named constant, use the `final` keyword, eg `final int numOfPotatoes = 12`. You can declare a constant without assigned a variable to it, a variable can be assigned to it later, but still only once. 

Named constants are conventionally given an all uppercase identified, seperated with underscores like : `NUMBER_OF_DEPTS` or `TAX`.

**Integer Data Types** 
TYPE | MINIMUM VALUE | MAXIMUM VALUE | SIZE IN BYTES
-----|---------------|---------------|---------------
byte | -128 | 127 | 1 | 
short | - 32,768 | 32,767 | 2 
int | - 2,147,483,648 | 2,147,483,647 | 4 
long | -9,223,372,036,854,775,808 | 9,223,372,036,854,775,807 | 8
The above table shows the 4 primitive integer types in Java, each can be used dependant on the required maximum value required. EG for 'numOfDependants' you'd likely use the `byte` type, for national GDP calculations you'd likely use the `long` type.

**Boolean Data Type**

The `boolean` data type holds either `true` or `false`. This can be directly assigned or the results of a comparison. A **relational operator** (aka a **comparison operator**) compares two items. See the below table for the default java relational operators.

OPERATOR | DESCRIPTION | TRUE EXAMPLE | FALSE EXAMPLE
-|-|-|-
< | Less than | 3 < 8 | 8 < 3
\> | Greater than | 4 > 2 | 2 > 4 
\== | Equal to | 7 == 7 | 3 == 9 
<= | Less than or equal to | 5 <= 5 | 8 <= 6 
\>= | Greater than or equal to | 7 >= 3 | 1 >= 2
 != | Not equal to | 5 != 6 | 3 != 3
An expression that contains a relational operator has a Boolean value.

**Floating Point Data Types**
There are two primitive floating point data types in Java, float and double. Float has accuracy up to 6/7 significant figures, whereas double can hold 14 or 15 significant figures.
EG float can be given the value 0.151252132 (9dp) but will store 0.151252 (6dp). 

TYPE | MINIMUM | MAXIMUM | SIZE IN BYTES
-|-|-|-
float | - 3.4 * 10^38 | 3.4 * 10^38 | 4
double | -1.7 * 10^308  | 1.7 * 10^308  |   8

By default a floating-point constant is assigned to a double data-type. So `float pocketChange = 4.87` would be assigned to a double by default, but can be assigned to a float by appending an 'F' like `float pocketChange = 4.87F`.

**`Char` data type**
The `char` data type holds a single character, this can be any uppercase or lowercase letter, a digit or punctuation. 
The below representations of '9' print the same but are stored differently in memory.
`char aCharvalue = '9'; 
`int aNumValue = 9;
WHEREAS
`char aCharvalue = 9; 
`int aNumValue = '9';
will print nothing and 53 respectively. This is because the aCharValue has not be passed a string but a numerical value so it corresponds to ht^I in the unicode table, and aNumValue corresponds to the character 9, which is 53 in the unicode table.

Below you can see the Unicode values from 0 through 127 and their character equivalents.
![[chrome_HicrnDgJ0i.png]]
You can also assign an "escape sequence" to a char data type, such as backspace, tab, etc
![[chrome_N8W1GXnthX.png]]
When you want to produce a console output on multiple links you can either use the println() method multiple times, use text blocks OR print a newline escape sequence (`\n`) within a println() call such as:
	`system. out . println ( "Hello\nthere " ) ;`

**Scanner Class**

To accept keyboard input you can use the `scanner` class. This uses the 'standard input device' (usually the keyboard) to accept input from the user. 
The `system.in` object is designed to read only bytes, we use the `scanner` class to make this more flexible. To create a `Scanner` object and connect it to the `system.in` we can:
``Scanner inputDevice = new Scanner(System.in);
Once we have imported the scanner class and instantiated an object of it connected to System.in, we can then use some of the classes methods to retrieve user data:
![[msedge_B8dsHLQOFB 1.jpg]]
See below some example usage of the scanner class:
![[msedge_OOSBOrpXHk.jpg]]
***NOTE***
Java is DUMB and this class does not clear the keyboard buffer, so if you want to accept numerical input THEN any other input, you must have a `inputDevice.nextLine();` between the lines to clear the 'Enter' key from the buffer. SO after any `next()`, `nextInt()`, or `nextDouble()` call, then add a `inputDevice.nextLine();`.

***JOptionPane Class to accept GUI input***

There are two dialog boxes that can be used to accept user input:
	-InputDialog - prompts user for text input
	-ConfirmDialog - prompts user with question, and buttons to respond (often yes, no, cancel)

`JOptionPane.showInputDialog` can be passed from 1 to 6 arguments, with one argument we would type:
```java
import javax.swing.JOptionPane
public class HelloNameDialog
{
	public static void main(String[] args)
	{
		String result;
		result = JOptionPane.showInputDialog(null, "What is your name?");
		JOptionPane.showMessageDialog(null, "Hello " + result + "!");
	}
}
```
We are parsing the method with `null` and `"What is your name?"`. We can also path the method more arguments like:
```java
JOptionPane.showInputDialog(null,
	"What is your name?",
	"Area code information",
	JOptionPane.QUESTION_MESSAGE);
```
This second argument names the dialog box "Area Code Information".
