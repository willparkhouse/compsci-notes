-- Some polymorphic functions

-- length

length' :: [a] -> Int
length' [] = 0 -- "Base case"
length' (x : xs) = 1 + length xs -- "Recursion step"

{-

length' [2,3,4] = length' (2 : [3,4])
                = 1 + length' [3,4]
                = 1 + length' (3:[4])
                = 1 + (1 + length' [4])
                = 1 + (1 + length' (4 : []))
                = 1 + (1 + (1 + length' []))
                = 1 + (1 + (1 + 0))
                = 3

-}

-- append two lists

-- appendBool :: [Bool] -> [Bool] -> [Bool]
-- appendInt :: [Int] -> [Int] -> [Int]

append :: [a] -> [a] -> [a]
append = undefined

-- reverse a list

reverse' :: [a] -> [a]
reverse' = undefined

-- delete an element from a list

example :: [Int -> Int]
example = [(+1),(*2)]

delete :: Eq a => a -> [a] -> [a]
delete x xs = undefined

-- sort a list

sort' :: Ord a => [a] -> [a]
sort' = undefined

-- Add up the elements of a list

sum' :: Num a => [a] -> a
sum' = undefined

-- Show

show :: Show a => a -> String
show = undefined

exercise :: Int -> Int -> Bool
exercise x y | x <= y     = False
             | x >= 2 * y = False
             | otherwise  = True

exercise' :: Int -> Int -> Bool
exercise' x y | x > y && x < 2 * y = True
              | otherwise = False

exercise'' :: Int -> Int -> Bool
exercise'' x y = x > y && x < 2 * y

-- Operator sections

-- +