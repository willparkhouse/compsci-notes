{-# LANGUAGE MonadComprehensions #-}

import Control.Monad.Writer
import Control.Monad.State

import Control.Applicative
import Data.Char

fib :: Integer -> Integer
fib 0 = 0
fib 1 = 1
fib n = fib (n-2) + fib (n-1)

fibm :: Monad m => Integer -> m Integer
fibm 0 = pure 0
fibm 1 = pure 1
fibm n = do
          x <- fibm (n-2)
          y <- fibm (n-1)
          pure (x+y)

fib_maybe :: Integer -> Maybe Integer
fib_maybe = fibm

fib_list :: Integer -> [Integer]
fib_list = fibm

fib_list' :: Integer -> [Integer]
fib_list' 0 = pure 0 -- equivalent to [0]
fib_list' 1 = pure 1 -- equivalent to [1]
fib_list' n = [ x+y | x <- fib_list' (n-2), y <- fib_list' (n-1)]

fibm' :: Monad m => Integer -> m Integer
fibm' 0 = pure 0
fibm' 1 = pure 1
fibm' n = [ x+y | x <- fibm' (n-2), y <- fibm' (n-1)]

fib1 :: Integer -> Maybe Integer
fib1 n | n <  0  = Nothing
       | n == 0  = Just 0
       | n == 1  = Just 1
       | n >= 2  = case fib1 (n-2) of
                     Nothing -> Nothing
                     Just x  -> case fib1 (n-1) of
                                  Nothing -> Nothing
                                  Just y  -> Just (x+y)

fib1' :: Integer -> Maybe Integer
fib1' n | n <  0 = Nothing
        | n == 0 = pure 0
        | n == 1 = pure 1
        | n >= 2 = do
                     x <- fib1' (n-2)
                     y <- fib1' (n-1)
                     pure (x+y)

fib2 :: Integer -> [Integer]
fib2 n | n <  0 = []
       | n == 0 = pure 0
       | n == 1 = pure 1
       | n >= 2 = do
                    x <- fib2 (n-2)
                    y <- fib2 (n-1)
                    pure (x+y)

fib3 :: Integer -> IO Integer
fib3 n | n <  0 = error ("invalid input " ++ show n)
       | n == 0 = pure 0
       | n == 1 = pure 1
       | n >= 2 = do
                    putStrLn ("call with n = " ++ show n)
                    x <- fib3 (n-2)
                    y <- fib3 (n-1)
                    pure (x+y)

fibs :: [Integer]
fibs = 0 : 1 : zipWith (+) fibs (tail fibs)

fib4 :: Integer -> Writer [Integer] Integer
fib4 n | n <  0 = error ("invalid input " ++ show n)
       | n == 0 = pure 0
       | n == 1 = pure 1
       | n >= 2 = do
                    tell [n]
                    x <- fib4 (n-2)
                    y <- fib4 (n-1)
                    pure (x+y)

fib5 :: Integer -> State Int Integer
fib5 n | n <  0 = error ("invalid input " ++ show n)
       | n == 0 = pure 0
       | n == 1 = pure 1
       | n >= 2 = do
                    modify (+1)
                    x <- fib5 (n-2)
                    y <- fib5 (n-1)
                    pure (x+y)

fib' :: Integer -> Integer
fib' n = x
 where
  f :: Integer -> State (Integer, Integer) ()
  f 0 = pure ()
  f n = do
         modify (\(x,y) -> (y, x+y))
         f (n-1)

  ((),(x,y)) = runState (f n) (0,1)

data Maybe' a = Nothing' | Just' a

instance Functor Maybe' where
 fmap g Nothing' = Nothing'
 fmap g (Just' x) = Just' (g x)

instance Applicative Maybe' where
  pure = Just'
  Nothing'  <*> Nothing'  = Nothing'
  (Just' g) <*> Nothing'  = Nothing'
  Nothing'  <*> (Just' x) = Nothing'
  (Just' g) <*> (Just' x) = Just' (g x)

instance Monad Maybe' where
  Nothing' >>= f = Nothing'
  Just' x  >>= f = f x

addm :: Maybe Int -> Maybe Int -> Maybe Int
addm xm ym = do
  x <- xm
  y <- ym
  pure (x+y)

addm' :: Maybe Int -> Maybe Int -> Maybe Int
addm' xm ym = xm >>= \x -> ym >>= \y -> pure (x+y)



data Writer' a = Result a String
                deriving Show



instance Monad Writer' where
  return x = Result x ""
  xm >>= f = case xm of
               Result x s -> case f x of
                               Result y t -> Result y (s ++ t)

-- Boiler plate:

instance Functor Writer' where
  fmap f xm = do x <- xm
                 return (f x)

instance Applicative Writer' where
      pure = return
      fm <*> xm = do f <- fm
                     x <- xm
                     return (f x)

data State' s a = T (s -> (a,s))

runState' :: State' s a -> (s -> (a, s))
runState' (T p) = p

instance Monad (State' s) where
  return x = T (\u -> (x,u))
  -- (>>=) :: State' s a -> (a -> State' s b) -> State' s b
  xm >>= f = case xm of
               T p -> T (\u -> case p u of
                                (x, v) -> case f x of
                                            T q -> q v)

instance Functor (State' s) where
  fmap f xm = do x <- xm
                 return (f x)

instance Applicative (State' s) where
      pure = return
      fm <*> xm = do f <- fm
                     x <- xm
                     return (f x)

get' :: State' s s
get' = T (\s -> (s,s))

put' :: s -> State' s ()
put' s = T (\_ -> ((), s))

modify' :: (s -> s) -> State' s ()
modify' f = T(\s -> ((), f s))

fibm'' :: Monad m => Integer -> m Integer
fibm'' 0 = pure 0
fibm'' 1 = pure 1
fibm'' n = fibm'' (n-2) >>= (\x ->
           fibm'' (n-1) >>= (\y ->
           pure (x+y)))

newtype Parser a = P (String -> [(a, String)])

parse :: Parser a -> String -> [(a, String)]
parse (P p) inp = p inp

item :: Parser Char
item = P (\inp -> case inp of
                     []     -> []
                     (x:xs) -> [(x,xs)])

instance Functor Parser where
  -- fmap :: (a -> b) -> Parser a -> Parser b
  fmap g p = P (\inp -> case parse p inp of
                           []        -> []
                           [(v,out)] -> [(g v, out)])

instance Applicative Parser where
  -- pure :: a -> Parser a
  pure v = P (\inp -> [(v,inp)])

  -- <*> :: Parser (a -> b) -> Parser a -> Parser b
  pg <*> px = P (\inp -> case parse pg inp of
                   []         -> []
                   [(g, out)] -> parse (fmap g px) out)


instance Monad Parser where
  -- (>>=) :: Parser a -> (a -> Parser b) -> Parser b
  p >>= f = P (\inp -> case parse p inp of
                          []        -> []
                          [(v,out)] -> parse (f v) out)

instance Alternative Parser where
  -- empty :: Parser a
  empty = P (\inp -> [])

  -- (<|>) :: Parser a -> Parser a -> Parser a
  p <|> q = P (\inp -> case parse p inp of
                          []        -> parse q inp
                          [(v,out)] -> [(v,out)])

sat :: (Char -> Bool) -> Parser Char
sat p = do x <- item
           if p x then return x else empty

digit :: Parser Char
digit = sat isDigit

lower :: Parser Char
lower = sat isLower

upper :: Parser Char
upper = sat isUpper

letter :: Parser Char
letter = sat isAlpha

alphanum :: Parser Char
alphanum = sat isAlphaNum

char :: Char -> Parser Char
char x = sat (==x)

string :: String -> Parser String
string []         = return []
string (x:xs)     = do char x
                       string xs
                       return (x:xs)

ident :: Parser String
ident = do x <- lower
           xs <- many alphanum
           return (x:xs)

nat :: Parser Int
nat = do xs <- some digit
         return (read xs)

space :: Parser ()
space = do many (sat isSpace)
           return ()

int :: Parser Int
int = do char '-'
         n <- nat
         return (-n)
      <|> nat

token :: Parser a -> Parser a
token p = do space
             v <- p
             space
             return v

identifier :: Parser String
identifier = token ident

natural :: Parser Int
natural = token nat

integer :: Parser Int
integer = token int

symbol :: String -> Parser String
symbol xs = token (string xs)

-- a parser for a non-empty list of natural numbers that ignores spacing
nats :: Parser [Int]
nats = do symbol "["
          n <- natural
          ns <- many (do symbol ","
                         natural)
          symbol "]"
          return (n:ns)

expr :: Parser Int
expr = do t <- term
          do symbol "+"
             e <- expr
             return (t + e)
           <|> return t

term :: Parser Int
term = do f <- factor
          do symbol "*"
             t <- term
             return (f * t)
           <|> return f

factor :: Parser Int
factor = do symbol "("
            e <- expr
            symbol ")"
            return e
          <|> natural

eval :: String -> Int
eval xs = case parse expr xs of
             [(n,[])]  -> n
             [(_,out)] -> error ("Unused input " ++ out)
             []        -> error "Invalid input"

{-

three :: Parser (Char, Char)
three = do
         x <- item
         item
         z <- item
         return (x,z)

-}

sequence' :: Monad m => [m a] -> m [a]
sequence' [] = pure []
sequence' (xm : xms) = do
                        x <- xm
                        xs <- sequence' xms
                        pure (x:xs)
{-

f :: a -> b
mx : (a -> Int) -> Int
Need my : (b -> Int) -> Int

my g = ?
g :: b -> Int
g . f :: a -> Int
mx (g .f) :: Int

-}

--         f                    mx                     my
kmap :: (a -> b) -> ((a -> Int) -> Int) -> ((b -> Int) -> Int)
kmap f mx = my
 where
   my g = mx (g . f)

lmap :: (a -> b) -> ((a -> Int) -> Int) -> ((b -> Int) -> Int)
lmap f mx g = mx (g . f)


a -> (b -> (c -> d))
