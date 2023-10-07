# A model of the IO monad

This is an optional handout.

An application of the material here is to facilitate automatic marking of interactive programs, by making IO not to perform any actual IO, if we work with the `IO'` monad defined here instead.

The `IO` monad allows us to write [interactive programs](https://en.wikipedia.org/wiki/Interactive_computing) that alternate between repeatedly inputing, computing and outputting, like in the function `reverseInteraction1` defined below, as you would do in imperative languages such as `Java` and `C`.

The `IO` monad is built-in in the language. In order to try to make sense of it, we construct a model `IO'` of `IO`.
In this simplified model, we only implement the following functions:
```haskell
module IOPrime (IO', putChar', getChar', putStr', putStrLn', getLine',
                getContents', interact', pipe, pipeR, translate) where
```
  * `putChar' :: Char -> IO' ()` prints a character.
  * `getChar' :: IO' Char` inputs a character.
  * `putStr' :: String -> IO' ()` prints a string.
  * `putStrLn' :: String -> IO' ()` prints a string followed by a newline.
  * `getLine' :: IO' String` inputs a string terminated by a newline.
  * `getContents' :: IO' String` gets the whole input.
  * `interact' :: (String -> String) -> IO' ()` converts a function into an interaction, where the input is the argument of the function and the output is the value of a function.
  * `pipe :: IO' () -> String -> String` converts `IO'` interaction into a function `String -> String`. The functions `interact'` and `pipe` are mutually inverse. The function `PipeR :: IO' () -> String -> (String, a)` additionally gives a result.
  * `translate :: IO' a -> IO a` is demonstrated and explained below.

The point is that these functions, without the trailing primes, are predefined in Haskell, with an external implementation in [C](https://en.wikipedia.org/wiki/C_(programming_language)) in Haskell's [runtime system](https://en.wikipeadia.org/wiki/Runtime_system), and what we are doing here is to try to make sense of them by making a model of them within Haskell.

## Example

Here is an example of an interactive program in the native `IO` monad:
```haskell
reverseInteraction1 :: IO ()
reverseInteraction1 = do
  putStrLn "Enter a word please"
  word <- getLine
  if word == ""
  then
    putStrLn "Goodbye"
  else do
    putStrLn "The reversed word is"
    putStrLn (reverse word)
    reverseInteraction1
```
Here we define our own monad `IO'` from scratch, and a translation
```
  translate :: IO' a -> IO a
```
so that, for example, the following function `reverseInteraction2` is equivalent to the above function:
```haskell
reverseInteraction' :: IO' ()
reverseInteraction' = do
  putStrLn' "Enter a word please"
  word <- getLine'
  if word == ""
  then
    putStrLn' "Goodbye"
  else do
    putStrLn' "The reversed word is"
    putStrLn' (reverse word)
    reverseInteraction'

reverseInteraction2 :: IO ()
reverseInteraction2 = translate reverseInteraction'
```

## The `IO'` data type

The idea of the following definition is that an element of type `IO' a` is an interaction that can
  * return a value of type `a` and end, or
  * input a character, and then continue to interact, or
  * output a character, and then continue to interact.

```haskell
data IO' a = Return a
           | Input (Char -> IO' a)
           | Output Char (IO' a)
```
Notice that the argument of the constructor `Input` is a function, which says how to proceed when a character is given. On the other hand, the constructor `Output` has two arguments, a given character, followed by an interaction.

## The `IO'` monad

This is a monad as follows:
```haskell
instance Monad IO' where
  return = Return

  -- (>>=) :: IO' a -> (a -> IO' b) -> IO' b
  (Return x)      >>= f = f x
  (Input reader)  >>= f = Input (\c -> reader c >>= f)
  (Output c i)    >>= f = Output c (i >>= f)
```
with the following boiler-plate code:
```haskell
instance Functor IO' where
  fmap f xm = xm >>= pure . f

instance Applicative IO' where
  pure = return
  fm <*> xm = fm >>= \f -> xm >>= pure . f
```

## Primitive effects

The primitive effects corresponding to the Haskell prelude functions with the same names, without the trailing primes, are implemented as follows:
```haskell
putChar' :: Char -> IO' ()
putChar' c = Output c (return ())

getChar' :: IO' Char
getChar' = Input return
```

## Derived effects

We also have the following derived effects:
```haskell
putStr' :: String -> IO' ()
putStr' []     = return ()
putStr' (c:cs) = putChar' c >> putStr' cs

putStrLn' :: String -> IO' ()
putStrLn' cs = putStr' cs >> putChar' '\n' >> return ()

getLine' :: IO' String
getLine' = do
  c <- getChar'
  if c == '\n'
    then
      return ""
    else do
      cs <- getLine'
      return (c:cs)
```

## `input` and `output` for the native `IO'` monad

Although the `IO` monad doesn't have predefined functions `Input :: (Char -> IO' a) -> IO' a` and `Output :: Char -> IO' a -> IO' a` as our `IO'` monad, they can be defined as follows:
```haskell
input :: (Char -> IO a) -> IO a
input reader = getChar >>= reader

output :: Char -> IO a -> IO a
output c i = putChar c >> i
```

## Translation of `IO'` into `IO`

We can drop the prime as follows, using `input` and `output` defined above:
```haskell
translate :: IO' a -> IO a
translate (Return a)      = return a
translate (Input reader)  = input (\c -> translate(reader c))
translate (Output c i)    = output c (translate i)
```
What this does is to translate our (so-called "initial") model into the model provided by the runtime system.

It has to be emphasized that there is no way back, however: there is no inverse function `IO a -> IO' a`.

## `pipe` and `interact'`

Our `IO'` monad can also be understood without reference to actual IO as follows:

```haskell
pipe :: IO' () -> String -> String
pipe (Return ())    _      = ""
pipe (Input reader) []     = error "end of input stream"
pipe (Input reader) (c:cs) = pipe (reader c) cs
pipe (Output c i)   cs     = c : pipe i cs
```
This converts an `IO'` interaction into a function from input strings to output strings. More generally, we have:
```haskell
pipeR :: IO' a -> String -> (String , a)
pipeR (Return x)     _      = ("", x)
pipeR (Input reader) []     = error "end of input stream"
pipeR (Input reader) (c:cs) = pipeR (reader c) cs
pipeR (Output c i)   cs     = case pipeR i cs of
                                (ds , x) -> (c : ds, x)
```
Finally, we get the following:
```haskell
getContents' :: IO' String
getContents' = do
  c <- getChar'
  cs <- getContents'
  return (c:cs)

interact' :: (String -> String) -> IO' ()
interact' f = do
  s <- getContents'
  putStr' (f s)
```

## Third implementation of `reverseInteraction`

We exploit Haskell's laziness and the prelude function `interact
:: (String -> String) -> IO()` so that we can implement `reverseInteraction without mentioning monads.
```haskell
reverseInteraction3_ :: String -> String
reverseInteraction3_ xs = f(lines xs)
  where
    f :: [String] -> String
    f ls = "Enter a word please\n"
        ++ if force(head ls) == ""
           then "Goodbye"
           else "The reversed word is\n" ++ reverse(head ls) ++ "\n" ++ f(tail ls)
```
Because of laziness, the function produces the string `"Enter a word please"` before tring to evaluate its argument `ls`. This is what allows the following function to behave exactly like `reverseInteraction1` and `reverseInteraction2`. It is important to *not* define `f` by pattern matching to achieve the desired behaviour. It is also important to use the function `force` to force the complete evaluation of `xs`, as otherwise we see the message "The reverse word is\n" before we finish typing the input, after we input the first character. We could use `seq` instead of `force` for that purpose:
```haskell
force :: String -> String
force xs = if all (\x -> x == x) xs then xs else error "the else branch should be unreachable"
```
In order to check that all elements of `xs` are equal to themselves, which is of course `True`, we need to fully evaluate `xs`.

In the last step we do need to invoke the `IO` monad via the function interact:
```haskell
reverseInteraction3 :: IO()
reverseInteraction3 = interact reverseInteraction3_
```
Notice that our version `interact'` for the `IO'` monad also exploits laziness.

The final remark is that although it is possible in principle to define interactions as functions `String -> String`, it is difficult in practice, at least without enough experience, as illustrated by the fact that in the above example we had to be careful to avoid pattern mathing and force the evaluation of part of the input at certain places. It is because of this that people prefer to work with the `IO` monad, as it makes clear the order of input and output, which are only implicit in the lazy behaviour of the corresponding functions `String -> String`.
