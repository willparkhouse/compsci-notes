module Interpreter where

import AbstractSyntax

type Storage = Identifier -> Integer

emptyStorage :: Storage
emptyStorage i = error ("Uninitialized variable " ++ i)

update :: Identifier -> Integer -> Storage -> Storage
update i x m = m'
 where
   m' :: Storage
   m' j | i == j    = x
        | otherwise = m j

number :: Bool -> Integer
number False = 0
number True  = 1

boolean :: Integer -> Bool
boolean 0 = False
boolean _ = True

eval :: Storage -> Expr -> Integer
eval m (Constant x) = x
eval m (Var i)      = m i
eval m (Op o es)    = opEval o [eval m e | e <- es]

opEval :: OpName -> [Integer] -> Integer
opEval Add     [x, y] = x + y
opEval Sub     [x, y] = x - y
opEval Mul     [x, y] = x * y
opEval Div     [x, y] = x `div` y
opEval Mod     [x, y] = x `mod` y
opEval Eq      [x, y] = number(x == y)
opEval Leq     [x, y] = number(x <= y)
opEval Less    [x, y] = number(x <  y)
opEval Geq     [x, y] = number(x >= y)
opEval Greater [x, y] = number(x >  y)
opEval And     [x, y] = number(boolean x && boolean y)
opEval Or      [x, y] = number(boolean x || boolean y)
opEval Not     [x]    = number(not(boolean x))
opEval op      xs     = error ("Interpreter bug. "
                            ++ "Please contact the software maintainer. "
                            ++ "Tried to apply " ++ show op
                            ++ " to " ++ show xs)

run :: Program -> Storage -> Storage

run (i := e) m = update i (eval m e) m

run (IfElse e p q) m
    | boolean(eval m e) = run p m
    | otherwise         = run q m

run (If e p) m
    | boolean(eval m e) = run p m
    | otherwise         = m

run (While e p) m
    | boolean(eval m e) = m''
    | otherwise         = m
    where
      m'  = run p m
      m'' = run (While e p) m'

run (Block []) m = m

run (Block (p : ps)) m = m''
    where
      m'  = run p m
      m'' = run (Block ps) m'

assign :: Identifier -> Expr -> Storage -> Storage
assign i e = \m -> update i (eval m e) m

ifElse :: (s -> Bool) -> (s -> s) -> (s -> s) -> (s -> s)
ifElse p f g = h
 where
   h m = if p m then f m else g m

while :: (s -> Bool) -> (s -> s) -> (s -> s)
while p f = g
 where
   g m = if p m then g(f m) else m

block :: [s -> s] -> s -> s
block []     m = m
block (f:fs) m = block fs (f m)

booleanValue :: Expr -> (Storage -> Bool)
booleanValue e = \m -> boolean(eval m e)

run' :: Program -> Storage -> Storage
run' (i := e)       = assign i e
run' (IfElse e p q) = ifElse (booleanValue e) (run' p) (run' q)
run' (If e p)       = ifElse (booleanValue e) (run' p) id
run' (While e p)    = while  (booleanValue e) (run' p)
run' (Block ps)     = block  (map run' ps)

