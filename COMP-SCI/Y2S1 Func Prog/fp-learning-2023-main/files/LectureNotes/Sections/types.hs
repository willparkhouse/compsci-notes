
add :: (Integer, Integer) -> Integer
add (x,y) = x + y

add' :: Integer -> (Integer -> Integer)
add' x y = x + y

add3 :: Integer -> Integer -> Integer -> Integer
add3 x y z = x + y + z

