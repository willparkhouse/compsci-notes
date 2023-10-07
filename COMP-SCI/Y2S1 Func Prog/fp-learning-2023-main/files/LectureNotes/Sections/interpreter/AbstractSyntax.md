# Abstract syntax

A video on this section can be found [here](https://bham.cloud.panopto.eu/Panopto/Pages/Viewer.aspx?id=78c83470-4c37-4279-bef8-ac85012e8be3).

The operations are listed in the order of precedence as in the
languages C and C++ (from low to high), where all the operations in
the same line have the same precedence, but this is important for
[parsing](Parser.md) only:

```haskell
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
```
Notice that we are using a constructor `:=` in the `Program` type, written in infix notation.
We use [monadic parsing](/files/LectureNotes/Sections/monads.md#monadic-parsing) to convert from [concrete syntax](ConcreteSyntax.md) to abstract syntax.

#### Next: [Parser](Parser.md)
