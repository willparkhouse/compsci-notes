module Main where

import System.Environment

import AbstractSyntax
import Parser
import Interpreter

initialStorage :: Integer -> Storage
initialStorage x = update "x" x emptyStorage

runxy :: Program -> Integer -> Integer
runxy p x = m' "y"
  where
    m  = initialStorage x
    m' = run p m

main :: IO()
main =
  do
    args <- getArgs
    if length args == 2
       then
         do
           concreteProgram <- readFile (args !! 0)
           let abstractProgram = parseProgram concreteProgram
           let x = read(args !! 1)
           let y = runxy abstractProgram x
           putStrLn (show y)
       else
           putStrLn "Usage: runhaskell Runxy.hs <filename> <Integer>"

