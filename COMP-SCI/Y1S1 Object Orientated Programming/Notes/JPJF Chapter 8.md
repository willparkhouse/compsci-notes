**Chapter 8 – Arrays**

8.1 Declaring an Array

·         Array = named list of items all of the same type (primitive or complex objects)

·         To declare an array variable use format: dataType [] arrayName (e.g. String [] someStrings)

·         Array naming conventions are the same as variable naming conventions, but are often plural

Syntax:

Double [] salesFigures = new double[20];

(20 = number of memory slots for values

·         Each item in an array can be distinguished based on its ‘subscript’ - an integer contained between [] to specify an element

·         Array elements are numbered with subscripts/indexes starting from 0 (in an array of 20 values, the subscripts will go from 0-19)

·         In Java, the array size can be variables, arithmetic expressions, and method return values

·         The array ‘length’ field contains the number of elements that the array contains

8.2 Initialising an Array

·         Int[] someNums; and int[] someNums; are both null arrays

·         Declaring an array != reserving memory space for array

·         To reserve memory space for an array use keyword ‘new’, without this the declared array variable name has the value null

·         To assign a value to an array:

arrayName[subscript] = value;

·         To initialise multiple values (in this case, do not declare size or use the keyword ‘new’):

int[] multsOfTen = {10, 20, 30};

·         You cannot only initialise part of an array – if the array has been declared to hold 10 elements you must initialise all of them or none of them

8.3 Using Variable Subscripts with an Array

·         Arrays are powerful when used to hold subscripts of variables rather than constants

·         For example, you could have an array of quiz scores and you wish to increment them all by 3 for some reason, this could be achieved using a for loop to iterate through the array and perform an arithmetic expression:

int sub; // loop control variable, represents subscript

final int INCREASE = 3;

for(sub = 0; sub < 5; ++sub)

scoreArray[sub] += INCREASE;

·         Arrays are often used in conjunction with loops that iterate through each element in the list (e.g. to find matches or sort list [usually based on a particular object field])

·         If the loop tries to execute for more times than there are array elements, an out of bounds error will be thrown

·         It is a good idea to create a constant variable equal to the array length to use as the limiting variable (in case this changes in future)

·         For arrays, length is a FIELD, not a method (as it is for strings)

·         You can use an enhanced for loop (foreach loop) to iterate through each element in an array (e.g. for each val in scoreArray, print val):

for(int val : scoreArray)

System.out.println(val);

*val must be same type as an array

Using Part of an Array

·         Below example exits if user enters “999” even if the entire array has not been filled:

![](file:///C:/Users/Music/AppData/Local/Packages/oice_16_974fa576_32c1d314_2971/AC/Temp/msohtmlclip1/01/clip_image002.png)

8.4 Declaring and Using Arrays of Objects

·         Arrays can hold objects as well as primitive data types

E.g. instead of:

Employee painter, electrician, plumber;

Employee firstEmployee, secondEmployee, thirdEmployee;

You can create an array:

Employee[] emps = new Employee[7];

·         Though this will not actually construct the employee objects, this must be done by calling individual constructors or creating a loop (if object does not use default constructor)

·         To use a method of an array: object[n].someMethod(); (n = index)

8.5 Searching an Array Using Parallel Arrays

·         ‘flag’ = variable holding a value that indicates whether some condition has been met

·         To compare a variable with a series of values, compare it to the values when they are put in an array:

int[] validValues = {101, 108, 201, 213, 266, 304, 311, 409, 411, 412};

for(int x = 0; x < validValues.length; ++x)

{

if(itemOrdered == validValues[x])

isValidItem = true;

}

Using Parallel Arrays

·         Additional information can be accessed from an array if you set up a ‘parallel array’ with the same number of elements as the array you are comparing to, with the values in each corresponding index being related

![](file:///C:/Users/Music/AppData/Local/Packages/oice_16_974fa576_32c1d314_2971/AC/Temp/msohtmlclip1/01/clip_image004.png)

·         It is most efficient to break out of the loop as soon as a valid value is found, rather than keep iterating through the rest of the elements

·         You can do this by setting the loop control variable to the limiting value:

![Title: Inserting image...](file:///C:/Users/Music/AppData/Local/Packages/oice_16_974fa576_32c1d314_2971/AC/Temp/msohtmlclip1/01/clip_image006.png)

·         Alternatively, including a condition in the for loop conditions that will only continue looping if the control variable does not equal the value you are searching for:

![](file:///C:/Users/Music/AppData/Local/Packages/oice_16_974fa576_32c1d314_2971/AC/Temp/msohtmlclip1/01/clip_image008.png)

Searching an Array for a Range Match

·         To perform a ‘range match’ create two corresponding arrays and compare values to the endpoints of numerical ranges to find the category in which a value belongs:

![Title: Inserting image...](file:///C:/Users/Music/AppData/Local/Packages/oice_16_974fa576_32c1d314_2971/AC/Temp/msohtmlclip1/01/clip_image010.png)

·         The inclusion of the sub value comparison is to check that the input does not fall below zero and cause a runtime error

8.6 Passing Array Passing Arrays _to_ and Returning Arrays _from_ Methods

Passing an Array to a Method

·         Individual array elements can be treated like variables, including being passed into methods

·         Array elements are passed ‘by value’ meaning a copy of the value they hold is entered, and the original element is unchanged (this will be the case for all Arrays containing primitive data types)

·         If a complex object is passed into a method, it is actually passing a memory address, thus the method has the ability to alter the original values in the array

Returning an Array from a Method

·         Methods can return array references (in method declaration put the return type as dataType[] and use the return keyword with the array name at the end of the method body)

8.7 Sorting Array Elements

Bubble Sort

·         Compare pairs and swap if not in correct order, so smaller/larger (depending on order) values ‘bubble’ to the top of the list

·         After one ‘pass’ of list the largest number will be at one end and the smallest at the other, but the list will not yet be fully sorted

![](file:///C:/Users/Music/AppData/Local/Packages/oice_16_974fa576_32c1d314_2971/AC/Temp/msohtmlclip1/01/clip_image011.png)

·         Maximum passes required to sort a list = list length – 1

·         To improve bubble sort efficiency, after each pass you no longer have to check the pair at the right end of the list indexes (as they are sorted into the correct position), so decrement the number of comparisons with each pass:

![](file:///C:/Users/Music/AppData/Local/Packages/oice_16_974fa576_32c1d314_2971/AC/Temp/msohtmlclip1/01/clip_image013.png)

Sorting Arrays of Objects

·         Sorting objects must be done on the basis of a particular object field, so use a getter method to retrieve the value you are going to compare

Insertion Sort

·         If an item is out of order relative to all items earlier in list, all earlier items shift down (to right), and the current item is inserted at the first position (far left)

·         In below example, inner loop will only execute and swap the values if someNums[b] (left of comparison pair) is greater than the temp value (right of comparison pair)

![](file:///C:/Users/Music/AppData/Local/Packages/oice_16_974fa576_32c1d314_2971/AC/Temp/msohtmlclip1/01/clip_image014.png)

8.8 Using 2D and Other Multidimensional Arrays

·         One dimensional array = accessed using single subscript

·         Two-dimensional array (/matrix/table) = requires two subscripts to access, has both ‘rows’ and ‘columns’, essentially an array of arrays

·         To declare a two-dimensional array:

dataType [] [] arrayName = new dataType [x] [y]

![](file:///C:/Users/Music/AppData/Local/Packages/oice_16_974fa576_32c1d314_2971/AC/Temp/msohtmlclip1/01/clip_image016.png)

·         If you do not assign any values, they will default to zero and can then be assigned later

·         To assign values to a 2D array:

int[][] someNumbers = { {8, 9, 10, 11},

{1, 3, 12, 15},

{5, 9, 44, 99} };

·         To pass a 2D array to a method:

![](file:///C:/Users/Music/AppData/Local/Packages/oice_16_974fa576_32c1d314_2971/AC/Temp/msohtmlclip1/01/clip_image018.png)

·         Use the .length() method to find the length of a row in a 2D array

·         ‘Jagged array’ = each row has a different length, this is achieved by only defining the number of rows when creating an array, then individually defining the length of each column

·         Java also supports arrays with more than 2 dimensions, e.g. for a 3D array use three sets of square brackets

8.9 Using the Arrays Class

·         The in-built java Arrays class contains methods for manipulating arrays (search, compare, fill, sort, etc.)

![](file:///C:/Users/Music/AppData/Local/Packages/oice_16_974fa576_32c1d314_2971/AC/Temp/msohtmlclip1/01/clip_image020.png)

·         To access Arrays class, use java.util.* import

·         To use binarySearch, the list must first be in order, and it will return a negative value if the search does not find a valid value

![](file:///C:/Users/Music/AppData/Local/Packages/oice_16_974fa576_32c1d314_2971/AC/Temp/msohtmlclip1/01/clip_image022.png)

8.10 Creating Enumerations

·         Enumeration = data type with a fixed set of values

·         To create an enumerated data type use keyword ‘enum’ (type identifier) followed by a pair of curly braces that contain list of enum constants, which are the allowed values for the type:

enum Month {JAN, FEB, MAR, APR, MAY, JUN,

JUL, AUG, SEP, OCT, NOV, DEC};

·         Enum identifier begins with a capital letter, all elements in its arrays are in block caps and are NOT strings

·         Enum types can be declared in their own file (if their name is the same as name.java), or within a class

·         Enum types CANNOT be declared within a method

·         To declare a variable and set equal to an enum value:

Month mon;

mon = Month.MAY;

Enum Methods

![](file:///C:/Users/Music/AppData/Local/Packages/oice_16_974fa576_32c1d314_2971/AC/Temp/msohtmlclip1/01/clip_image024.png)

![](file:///C:/Users/Music/AppData/Local/Packages/oice_16_974fa576_32c1d314_2971/AC/Temp/msohtmlclip1/01/clip_image026.png)

Advantages of Declaring an Enumeration Type

·         Allows only specific values to be assigned to a variable

·         Type-safe – only appropriate behaviours are allowed to be applied to the values

·         Self-documentation = better readability

·         You can add unique methods/fields to the enum type