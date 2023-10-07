removeLast :: [a] -> [a]
removeLast xs = reverse (tail (reverse xs))

removeElem :: Int -> [a] -> [a]
removeElem n xs = removeLast (take n xs) ++ drop n xs

abs' :: Integer -> Integer
abs' n = if n >= 0 then n else -n

howMuchDoYouLikeHaskell :: Int -> String
howMuchDoYouLikeHaskell x = if x < 3 then "I dislike it!" else
                               if x < 7 then "It's ok!" else
                                 "It's fun!"

abs :: Int -> Int
abs n | n >= 0    = n
      | otherwise = -n

howMuchDoYouLikeHaskell2 :: Int -> String
howMuchDoYouLikeHaskell2 x | x < 3       = "I dislike it!"
                           | x < 7       = "It's ok!"
                           | otherwise   = "It's fun!"

notB :: Bool -> Bool
notB False = True
notB True = False

swap :: (a, b) -> (b, a)
swap (x,y) = (y,x)

isEmpty :: [a] -> Bool
isEmpty []     = True
isEmpty (x:xs) = False

notB' :: Bool -> Bool
notB' False = True
notB' True = False

andB :: Bool -> Bool -> Bool
andB True True = True
andB True False = False
andB False True = False
andB False False = False

andB' :: Bool -> Bool -> Bool
andB' True True = True
andB' _ _      = False

andB'' :: Bool -> Bool -> Bool
andB'' True b  = b
andB'' False _ = False

isTrue :: Bool -> Bool
isTrue True = True

isTrue' :: Bool -> Bool
isTrue' True = True
isTrue' False = error "not True"

fst :: (a,b) -> a
fst (x,y) = x

snd :: (a,b) -> b
snd (_,y) = y

third :: (a, b, c) -> c
third (_, _, z) = z

addVectors :: (Num a) => (a, a) -> (a, a) -> (a, a)
addVectors (x1, y1) (x2, y2) = (x1 + x2, y1 + y2)

isEmpty' :: [a] -> Bool
isEmpty' [] = True
isEmpty' (x:xs) = False

isEmpty'' :: [a] -> Bool
isEmpty'' [] = True
isEmpty'' (_:_) = False

sndElem :: [a] -> a
sndElem (_:x:_) = x

isEmpty2 :: [a] -> Bool
isEmpty2 x = case x of [] -> True
                       (_:_) -> False

double :: Int -> Int
double x = 2 * x

double' :: Int -> Int
double' = \x -> 2 * x

mult :: Int -> Int -> Int
mult x y = x * y

mult' :: Int -> Int -> Int
mult' = \x y -> x * y

mult'' :: Int -> (Int -> Int)
mult'' = \x -> (\y -> x * y)

alwaysZero :: Bool -> Int
alwaysZero = \_ -> 0

apply :: (a -> b) -> a -> b
apply f x = f x

square :: Int -> Int
square = (^2)

reci :: Fractional a => a -> a
reci = (1 /)

