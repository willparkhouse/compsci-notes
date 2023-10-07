module AbstractSyntax where

type Identifier = String

data OpName = Or                                 --  ||
            | And                                --  &&
            | Eq                                 --  ==
            | Leq | Less | Geq | Greater         --  <=  <  >=  >
            | Add | Sub                          --  +  -
            | Mul | Div | Mod                    --  *  /  %
            | Not                                --  !
            deriving (Show)

data Expr = Constant Integer
          | Var Identifier
          | Op OpName [Expr]
          deriving (Show)

data Program = Identifier := Expr
             | Block [Program]
             | While Expr Program
             | If Expr Program
             | IfElse Expr Program Program
             deriving (Show)

