module Data1 where

import System.Random

type Lst a = [a]

conj :: Bool -> Bool -> Bool
conj False False = False
conj False True  = False
conj True  False = False
conj True  True  = True

data BW = Black | White

bw2bool :: BW -> Bool
bw2bool Black = False
bw2bool White = True

bool2bw :: Bool -> BW
bool2bw False = Black
bool2bw True  = White

bw2bool' :: BW -> Bool
bw2bool' Black = True
bw2bool' White = False

bool2bw' :: Bool -> BW
bool2bw' False = White
bool2bw' True  = Black

data Bit = Zero | One

bit2Bool :: Bit  -> Bool
bool2Bit :: Bool -> Bit

bit2Bool Zero  = False
bit2Bool One   = True

bool2Bit False = Zero
bool2Bit True  = One


bit2Bool' :: Bit  -> Bool
bool2Bit' :: Bool -> Bit

bit2Bool' Zero  = True
bit2Bool' One   = False

bool2Bit' False = One
bool2Bit' True  = Zero

data WeekDay = Mon | Tue | Wed | Thu | Fri | Sat | Sun
               deriving (Show, Read, Eq, Ord, Enum)

data WeekDay' = Monday | Tuesday | Wednesday | Thursday | Friday | Saturday | Sunday

dive :: Int -> Int -> Maybe Int
x `dive` y = if y == 0 then Nothing else Just (x `div` y)

adde :: Maybe Int -> Maybe Int -> Maybe Int
adde Nothing  Nothing  = Nothing
adde Nothing  (Just y) = Nothing
adde (Just x) Nothing  = Nothing
adde (Just x) (Just y) = Just (x + y)

adde' :: Maybe Int -> Maybe Int -> Maybe Int
adde' (Just x) (Just y) = Just (x+y)
adde' _       _         = Nothing

adde'' :: Maybe Int -> Maybe Int -> Maybe Int
adde'' xm ym = case xm of
                Nothing -> Nothing
                Just x  -> case ym of
                            Nothing -> Nothing
                            Just y  -> Just (x+y)

firstPosition :: Eq a => a -> [a] -> Maybe Int
firstPosition x []     = Nothing
firstPosition x (y:ys)
           | x == y    = Just 0
           | otherwise = case firstPosition x ys of
                           Nothing -> Nothing
                           Just n  -> Just (n+1)

testFirstPosition :: Eq a => a -> [a] -> Bool
testFirstPosition x ys =  case firstPosition x ys of
                           Nothing -> and [ ys !! i /= x | i <- [0 .. length ys - 1]]
                           Just n  -> ys !! n == x

bool2Int :: Bool -> Int
bool2Int False = 0
bool2Int True  = 1

int2Bool :: Int -> Bool
int2Bool n | n == 0    = False
           | otherwise = True

data WorkingWeekDay = Mon' | Tue' | Wed' | Thu' | Fri'

data And a b = Both a b

data MainDish = Chicken | Pasta | Vegetarian
data Dessert = Cake | IceCream | Fruit
data Drink = Tea | Coffee | Beer

type SaverMenu = Either (And MainDish Dessert) (And MainDish Drink)

type SaverMenu' = And MainDish (Either Dessert Drink)

prime :: SaverMenu -> SaverMenu'
prime (Left (Both m d)) = Both m (Left  d)
prime (Right(Both m d)) = Both m (Right d)

unprime :: SaverMenu' -> SaverMenu
unprime (Both m (Left  d)) = Left (Both m d)
unprime (Both m (Right d)) = Right(Both m d)

and2pair :: And a b -> (a,b)
and2pair (Both x y) = (x,y)

pair2and :: (a,b) -> And a b
pair2and (x,y) = Both x y

type SaverMenu''  = Either (MainDish, Dessert) (MainDish, Drink)
type SaverMenu''' = (MainDish, Either Dessert Drink)

data List a = Nil | Cons a (List a)

nativelist2ourlist :: [a] -> List a
nativelist2ourlist []     = Nil
nativelist2ourlist (x:xs) = Cons x (nativelist2ourlist xs)

ourlist2nativelist :: List a -> [a]
ourlist2nativelist Nil         = []
ourlist2nativelist (Cons x xs) = x:ourlist2nativelist xs

append :: List a -> List a -> List a
append Nil         ys = ys
append (Cons x xs) ys = Cons x (append xs ys)

rev :: List a -> List a
rev Nil         = Nil
rev (Cons x xs) = rev xs `append` (Cons x Nil)

fastrev :: List a -> List a
fastrev xs = revapp xs Nil
  where
    revapp :: List a -> List a -> List a
    revapp (Cons x xs) ys = revapp xs (Cons x ys)
    revapp Nil         ys = ys

fib 0 = 0
fib 1 = 1
fib n = fib (n-1) + fib (n-2)

fastfib n = fibAcc n 0 1
  where
    fibAcc 0 x y = x
    fibAcc 1 x y = y
    fibAcc n x y = fibAcc (n-1) y (x+y)

btexample = Fork 8 (Fork 4 (Fork 2 Empty Empty) Empty) (Fork 16 Empty (Fork 20 Empty Empty))

data BT a = Empty
          | Fork a (BT a) (BT a) deriving (Show, Read, Eq, Ord)

mirror :: BT a -> BT a
mirror Empty = Empty
mirror (Fork x l r) = Fork x (mirror r) (mirror l)

size :: BT a -> Integer
size Empty        = 0
size (Fork x l r) = 1 + size l + size r

leaves :: BT a -> Integer
leaves Empty        = 1
leaves (Fork x l r) = leaves l + leaves r

height :: BT a -> Integer
height Empty        = 0
height (Fork x l r) = 1 + max (height l) (height r)

btleft = Fork 20 (Fork 16 (Fork 8 (Fork 4 (Fork 2 Empty Empty) Empty) Empty) Empty) Empty

data Direction = L | R deriving (Show)
type Address   = [Direction]

subtree :: Address -> BT a -> Maybe(BT a)
subtree []     t            = Just t
subtree (_:_)  Empty        = Nothing
subtree (L:ds) (Fork _ l _) = subtree ds l
subtree (R:ds) (Fork _ _ r) = subtree ds r

isValid :: Address -> BT a -> Bool
isValid []     _            = True
isValid (_:_)  Empty        = False
isValid (L:ds) (Fork _ l _) = isValid ds l
isValid (R:ds) (Fork _ _ r) = isValid ds r

validAddresses :: BT a -> [Address]
validAddresses Empty        = [[]]
validAddresses (Fork _ l r) = [[]]
                           ++ [L:ds | ds <- validAddresses l]
                           ++ [R:ds | ds <- validAddresses r]

validAddresses' :: BT a -> [Address]
validAddresses' Empty        = [[]]
validAddresses' (Fork _ l r) = [[]]
                            ++ (map (L:) (validAddresses' l))
                            ++ (map (R:) (validAddresses' r))

btpaths :: BT a -> [[a]]
btpaths Empty        = [[]]
btpaths (Fork x l r) = [x:xs | xs <- btpaths l]
                    ++ [x:xs | xs <- btpaths r]

treeInOrder :: BT a -> [a]
treeInOrder Empty = []
treeInOrder (Fork x l r) = treeInOrder l ++ [x] ++ treeInOrder r

treePreOrder :: BT a -> [a]
treePreOrder Empty = []
treePreOrder (Fork x l r) = [x] ++ treePreOrder l ++ treePreOrder r

levels :: BT a -> [[a]]
levels Empty        = []
levels (Fork x l r) = [[x]] ++ zipappend (levels l) (levels r)
  where
    zipappend []       yss      = yss
    zipappend xss      []       = xss
    zipappend (xs:xss) (ys:yss) = (xs ++ ys) : zipappend xss yss

treeBreadthFirst :: BT a -> [a]
treeBreadthFirst = concat . levels

balancedTree :: [a] -> BT a
balancedTree [] = Empty
balancedTree xs = let (ys, x:zs) = splitAt (length xs `div` 2) xs in
                  Fork x (balancedTree ys) (balancedTree zs)

balance :: BT a -> BT a
balance = balancedTree . treeInOrder

inOrderTree :: [a] -> [BT a]
inOrderTree [] = [Empty]
inOrderTree xs = [Fork x l r | i <- [0..length xs-1],
                               let (ys, x:zs) = splitAt i xs,
                               l <- inOrderTree ys, r <- inOrderTree zs]

