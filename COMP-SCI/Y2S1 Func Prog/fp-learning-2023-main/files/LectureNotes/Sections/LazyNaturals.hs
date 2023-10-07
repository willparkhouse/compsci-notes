checkLengthBiggerThan :: [a] -> Int -> Bool
checkLengthBiggerThan xs n = length xs > n

checkLengthBiggerThan' :: [a] -> Int -> Bool
checkLengthBiggerThan' []     0 = False
checkLengthBiggerThan' xs     0 = True
checkLengthBiggerThan' []     n = False
checkLengthBiggerThan' (x:xs) n = checkLengthBiggerThan' xs (n-1)

data Nat = Zero | Succ Nat deriving (Eq,Ord)

one, two, three :: Nat
one   = Succ Zero
two   = Succ one
three = Succ two

toNat :: Int -> Nat
toNat 0 = Zero
toNat n = Succ (toNat (n-1))

infty = Succ infty

length' :: [a] -> Nat
length' []     = Zero
length' (x:xs) = Succ (length' xs)

biggerThan :: Nat -> Nat -> Bool
Zero     `biggerThan` y        = False
(Succ x) `biggerThan` Zero     = True
(Succ x) `biggerThan` (Succ y) = x `biggerThan` y

checkLengthBiggerThan'' :: [a] -> Int -> Bool
checkLengthBiggerThan'' xs n = (length' xs) `biggerThan` (toNat n)

checkLengthBiggerThan''' :: [a] -> Int -> Bool
checkLengthBiggerThan''' xs n = length' xs > toNat n

