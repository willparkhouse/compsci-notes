import Control.Concurrent

type Cell = (Int,Int)
type Grid = [Cell]

pentagenarian :: Grid
pentagenarian = [(1,2),(2,2),(2,3),(4,1),(4,3)]

glider :: Grid
glider = [(1,3),(2,1),(2,3),(3,2),(3,3)]

isLive, isDead :: Cell -> Grid -> Bool
isLive c g = c `elem` g
isDead c g = not (isLive c g)

neighbours :: Cell -> [Cell]
neighbours (x,y) = [ (x+i,y+j) | i <- [-1..1], j <- [-1..1], not (i==0 && j==0) ]

liveNeighbours :: Grid -> Cell -> [Cell]
liveNeighbours g c = [c' | c' <- neighbours c, isLive c' g]

step :: Grid -> Grid
step [] = []
step g =
  [(x,y) | x <- [minX-1 .. maxX+1],
           y <- [minY-1 .. maxY+1],
              (isDead (x,y) g && length (liveNeighbours g (x,y)) == 3)
           || (isLive (x,y) g && length (liveNeighbours g (x,y)) `elem` [2,3])
         ]
  where
    minX = minimum [ x | (x,y) <- g ]
    maxX = maximum [ x | (x,y) <- g ]
    minY = minimum [ y | (x,y) <- g ]
    maxY = maximum [ y | (x,y) <- g ]

terminalWidth  = 70
terminalHeight = 22

cls :: IO ()
cls = putStr "\ESC[2J"

goto :: Cell -> IO ()
goto (x,y) = putStr ("\ESC[" ++ show (terminalHeight-y) ++ ";" ++ show (x+1) ++ "H")

printCell :: Cell -> IO ()
printCell (x,y) | x >= 0 && x < terminalWidth
               && y >= 0 && y < terminalHeight = do
                                                  goto (x,y)
                                                  putChar 'O'

                | otherwise                    = return ()

terminalRender :: Grid -> IO ()
terminalRender g = do
                    cls
                    sequence [ printCell c | c <- g ]
                    goto (0,terminalHeight)

delayTenthSec :: Int -> IO ()
delayTenthSec n = threadDelay (n * 10^5)

life :: Grid -> IO ()
life seed = f 0 seed
 where
  f n g = do
           terminalRender g
           putStrLn (show n)
           delayTenthSec 1
           f (n+1) (step g)

main :: IO ()
main = life glider

block3 :: Grid
block3 = [(x,y) | x <- [1..3], y <- [1..3]]

pulsar :: Grid
pulsar = [(x+i,y) | x <- [2,8], y <- [0,5,7,12], i <- [0..2]] ++ [(x,y+i) | x <- [0,5,7,12], y <- [2,8], i <- [0..2]]

