## Ill-typed Expressions
1. Write five ill-typed expressions in Haskell.
2. Check their types in `ghci` - what does `ghci` say?
3. What happens when you try to evaluate that expression?
## Polymorphism
1. (Requires Section [Polymorphism](../LectureNotes/Sections/polymorphism.md))
   Find out the types of the following functions. Decide if they are polymorphic.
   1. `fst` **Polymorphic, takes (a, b) and returns a**
   2. `(++)` **polymorphic**
   3. `not` **Not Polymorphic**
   4. `head` **Polymorphic, takes [a] and returns a**
   5. `tail` **Polymorphic**
   6. `id` **Polymorphic**
2. Explain, in your own words, what the function `zip` does. In the expression `zip ['x', 'y'] [False]`, what are the type variables `a` and `b` of `zip :: [a] -> [b] -> [(a, b)]` instantiated by?
		**1. Zip is a function that takes two lis ts and makes as many pairs of the elements in the form (x1, y1) as possible, up to the length of the smallest list. 
		2. `A` is of the type `char` and `B` is of the type `bool`.** 
3. Find a polymorphic function in the GHC [standard library](https://hackage.haskell.org/package/base-4.17.0.0/docs/Prelude.html) whose type contains 3 type variables or more.
	1. **[mzipWith](https://hackage.haskell.org/package/base-4.17.0.0/docs/Prelude.html#v:mzipWith)**
4. Read Section 3.7 of [Programming in Haskell](https://rl.talis.com/3/bham/lists/D8A4E97C-76C1-121A-7100-E513B9C6B342.html?lang=en). Compare the types of the examples given there with the types `ghci` indicates. (Note: some of the types that `ghci` shows use "type classes" - you will learn about these in the next lesson.)
## Standard Library Functions and Hoogle
Look up the following functions for manipulating lists on [Hoogle](https://hoogle.haskell.org/) and write down their types.  For each function, read the description and try the function on a sample list.
1. length **takes a datatype and returns its length, whether that be the length of a text, or length of a list etc.**
1. reverse **reverses a list, array, strings, text, and combination of data in a row**
1. tail **returns the list without the 'head', which is usually the first element** (1, 2, 3) -> (2, 3)
1. head **returns the first element of the list**
1. take **takes an int, n, and a list, when applied returns the first n elements of the list, works on empty lists, or lists of length < nm**
1. drop **opposite of `take`, given an int, n, and a list, returns everything after the first n elements**
1. takeWhile * **takes a predicate and a list, and takes all the elements up until the predicate returns false for that element** 
1. dropWhile *
1. filter *
1. all *
1. any *
1. map **
The functions marked (*) additionally take a **predicate** `p :: a -> Bool`.  You can think of such a function `p` as a "yes or no question" about elements of the type `a`.  Examples are the following:
```
odd :: Int -> Bool
even :: Int -> Bool
isUpper :: Char -> Bool
isLower :: Char -> Bool
```
Try combining some of the starred functions with these predicates to get an idea for what each function does.
**Note**.  You will need to first type `import Data.Char` in `ghci` or add this line to the top of your source file in order to have access to the last two.
The `map` function (marked \*\*) additionally takes an arbitrary function `f :: a -> b`.  This function is extremely useful for modifying the contents of a list.
## Functions in Haskell
Here are some example functions which you can try to implement to get started using both the concepts from the lecture as well as the library functions you found above.
1. Write a function `orB :: Bool -> Bool -> Bool` that returns `True` if at least one argument is `True`.
1. Write a function `swap :: (a, b) -> (b, a)` that swaps the elements of a pair.
1. Write a function that removes both the first and the last element of a list.
1. Write a function which returns the reverse of a list if its length is greater than 7.  Now modify the function so that the cutoff length is a parameter.
1. Write a function which doubles all the elements of a list `l :: [Int]` and then keeps only those which are greater than 10.
1. Write a function to return the reverse of a string with all its alphabetic elements capitalized. (The function `toUpper :: Char -> Char` in the `Data.Char` library may be useful here.)
# Homework for Week 2
This homework will not be assessed.  You must complete it, however, in order to prepare for the tests.
## Writing More Functions
1. Write a function to pair each element of a list with its index.
1. Using guarded equations, write a function of type `Int -> Int -> Bool` that returns `True` if the first argument is greater than the second and less than twice the second.
1. (Adapted and expanded from the book "Programming in Haskell)
   Define three variants of a function `third :: [a] -> a` that returns the third element in any list that contains at least this many elements, using
   1. `head` and `tail`
   1. list indexing `!!`
   1. pattern matching
1. (Adapted and expanded from the book "Programming in Haskell)
   Define a function `safetail :: [a] -> [a]` that behaves like tail except that it maps `[]` to `[]` (instead of throwing an error). Using `tail` and `isEmpty :: [a] -> Bool`,
   define `safetail` using
   1. a conditional expression
   1. guarded equations
   1. pattern matching
## Type Classes and Instances
1. Run, and understand, the following examples:
    1. `False == 'c'`
    2. `False == True`
    3. `False == not`
    4. `False == not True`
    5. `not == id`
    6. `[not] == [ (id :: Bool -> Bool) ]`
1. Find all the basic instances of the type class `Bounded` that are defined in the GHC Prelude (the libraries that are loaded when starting `ghci`, without importing any additional libraries). Find out what `minBound` and `maxBound` are for each of the instances.
1. What type classes do the type classes `Fractional`, `Floating`, `Integral` extend? What functions do they provide? Which type class would you choose to implement a trigonometric calculus?