fib :: Integer -> Integer
fib 0 = 1
fib 1 = 1
fib n = fib(n-2) + fib(n-1)

fix :: (a -> a) -> a
fix f = x
 where
   x = f x

fibstep :: (Integer -> Integer) -> (Integer -> Integer)
fibstep g = h
 where
  h :: Integer -> Integer
  h 0 = 1
  h 1 = 1
  h n = g(n-2) + g(n-1)

fib' :: Integer -> Integer
fib' = fix fibstep

store :: (Integer -> a) -> [a]
store f = [f i | i <- [0..]]

fetch :: [a] -> (Integer -> a)
fetch (x:xs) 0 = x
fetch (x:xs) n = fetch xs (n-1)

memoList :: (Integer -> a) -> (Integer -> a)
memoList= fetch . store

fibml :: Integer -> Integer
fibml = fix(memoList . fibstep)

fixml :: ((Integer -> a) -> (Integer -> a)) -> Integer -> a
fixml f = g
 where
   g = f(memoList g)

fibml' :: Integer -> Integer
fibml' = fixml fibstep

type Sequence a = Integer -> a

hd :: Sequence a -> a
hd s = s 0

tl :: Sequence a -> Sequence a
tl s = \n -> s(n+1)

cons :: a -> Sequence a -> Sequence a
cons x s n | n == 0    = x
           | otherwise = s(n-1)

memoSeq :: Sequence a -> Sequence a
memoSeq s = cons (hd s) (memoSeq(tl s))

fibms :: Integer -> Integer
fibms = fix(memoSeq . fibstep)


type Tree a = Integer -> a

root :: Tree a -> a
root t = t 0

left, right :: Tree a -> Tree a
left t  = \n -> t(1+2*n)
right t = \n -> t(2+2*n)

fork :: a -> Tree a -> Tree a -> Tree a
fork x l r n | n == 0 = x
             | odd n  = l((n-1) `div` 2)
             | even n = r((n-2) `div` 2)

memoTree :: Tree a -> Tree a
memoTree t = fork (root t) (memoTree(left t)) (memoTree(right t))

fixt :: ((Integer -> a) -> (Integer -> a)) -> Integer -> a
fixt f = g
 where
   g = f(memoTree g)

fibmt :: Integer -> Integer
fibmt = fix(memoTree . fibstep)

