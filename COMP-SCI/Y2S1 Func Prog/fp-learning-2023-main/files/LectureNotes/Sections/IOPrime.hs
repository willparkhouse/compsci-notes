module IOPrime (IO', putChar', getChar', putStr', putStrLn', getLine',
                getContents', interact', pipe, pipeR, translate) where

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

data IO' a = Return a
           | Input (Char -> IO' a)
           | Output Char (IO' a)

instance Monad IO' where
  return = Return

  -- (>>=) :: IO' a -> (a -> IO' b) -> IO' b
  (Return x)      >>= f = f x
  (Input reader)  >>= f = Input (\c -> reader c >>= f)
  (Output c i)    >>= f = Output c (i >>= f)

instance Functor IO' where
  fmap f xm = xm >>= pure . f

instance Applicative IO' where
  pure = return
  fm <*> xm = fm >>= \f -> xm >>= pure . f

putChar' :: Char -> IO' ()
putChar' c = Output c (return ())

getChar' :: IO' Char
getChar' = Input return

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

input :: (Char -> IO a) -> IO a
input reader = getChar >>= reader

output :: Char -> IO a -> IO a
output c i = putChar c >> i

translate :: IO' a -> IO a
translate (Return a)      = return a
translate (Input reader)  = input (\c -> translate(reader c))
translate (Output c i)    = output c (translate i)

pipe :: IO' () -> String -> String
pipe (Return ())    _      = ""
pipe (Input reader) []     = error "end of input stream"
pipe (Input reader) (c:cs) = pipe (reader c) cs
pipe (Output c i)   cs     = c : pipe i cs

pipeR :: IO' a -> String -> (String , a)
pipeR (Return x)     _      = ("", x)
pipeR (Input reader) []     = error "end of input stream"
pipeR (Input reader) (c:cs) = pipeR (reader c) cs
pipeR (Output c i)   cs     = case pipeR i cs of
                                (ds , x) -> (c : ds, x)

getContents' :: IO' String
getContents' = do
  c <- getChar'
  cs <- getContents'
  return (c:cs)

interact' :: (String -> String) -> IO' ()
interact' f = do
  s <- getContents'
  putStr' (f s)

reverseInteraction3_ :: String -> String
reverseInteraction3_ xs = f(lines xs)
  where
    f :: [String] -> String
    f ls = "Enter a word please\n"
        ++ if force(head ls) == ""
           then "Goodbye"
           else "The reversed word is\n" ++ reverse(head ls) ++ "\n" ++ f(tail ls)

force :: String -> String
force xs = if all (\x -> x == x) xs then xs else error "the else branch should be unreachable"

reverseInteraction3 :: IO()
reverseInteraction3 = interact reverseInteraction3_

