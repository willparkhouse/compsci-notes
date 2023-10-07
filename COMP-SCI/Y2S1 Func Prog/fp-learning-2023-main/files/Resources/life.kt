/*
   This is a Kotlin version of the Haskell "Game of Life" program.

     https://kotlinlang.org/

   Kotkin is Google's preferred language for developing Android
   applications.

     https://developer.android.com/kotlin
     https://techcrunch.com/2019/05/07/kotlin-is-now-googles-preferred-language-for-android-app-development/

   Kotlin supports functional programming, as illustrated in this
   direct translation of the Haskell program
   https://git.cs.bham.ac.uk/mhe/fp-learning-2021-2022/blob/master/LectureNotes/Life.md

   Try to run it from the command line using these instructions:

     https://kotlinlang.org/docs/tutorials/command-line.html

     $ kotlinc life.kt -include-runtime -d life.jar
     $ java -jar life.jar

 */

data class Cell (val x: Int, val y: Int)
typealias Grid = List<Cell>

/*

  * A grid with all cells dead is represented by an empty list.

  * We only list the cells that are alive.

*/

fun isLive (c: Cell, g: Grid) = c in g
fun isDead (c: Cell, g: Grid) = ! isLive (c, g)

fun minHeight (g: Grid) = g.map { c -> c.y } . min()
fun maxHeight (g: Grid) = g.map { c -> c.y } . max()
fun minWidth  (g: Grid) = g.map { c -> c.x } . min()
fun maxWidth  (g: Grid) = g.map { c -> c.x } . max()

val blinker       = listOf(Cell(1,1), Cell(2,1), Cell(3,1))
val glider        = listOf(Cell(1,3), Cell(2,1), Cell(2,3), Cell(3,2), Cell(3,3))
val pentagenarian = listOf(Cell(1,2), Cell(2,2), Cell(2,3), Cell(4,1), Cell(4,3))
val nicolas       = listOf(Cell(0,2), Cell(1,2), Cell(1,3), Cell(2,3), Cell(0,4), Cell(1,4))

/* Escape character: */

val esc : String = 27.toChar().toString()

/* Hardcoded terminal dimensions: */

val terminalWidth = 70
val terminalHeight = 22

/* Move cursor in terminal: */

fun goto(c: Cell) {
 print (esc + "[" + (terminalHeight-c.y).toString() + ";" + (c.x+1).toString() + "H")
}

/* Print a cell as a character in the terminal: */

fun printCell(c: Cell) {
 if (c.x >= 0 && c.x < terminalWidth && c.y >= 0 && c.y < terminalHeight) {
    goto (c)
    print("O")
 }
}

/* Clear the terminal screen: */

fun cls () {
 print (esc + "[2J")
}

/* Render a grid in the terminal: */

fun render (g: Grid) {
 cls()
 g.forEach { c -> printCell(c) }
 goto (Cell(0,terminalHeight))
}

/* The cartesian product of two lists: */

fun cartesianProduct(xs: List<Int>, ys: List<Int>) : List<Cell> =
 xs.map {x -> ys.map {y -> Cell(x , y)}} . flatten()

/* Some special grids: */

val unitGrid = cartesianProduct(listOf(-1, 0, 1),listOf(-1, 0, 1))
val unitGridWithoutCenter = unitGrid.filter { c -> !(c.x == 0 && c.y == 0) }

/* The list of neighbours of a cell in a grid: */

fun neighbours(c: Cell) = unitGridWithoutCenter.map { d -> Cell(c.x + d.x, c.y + d.y) }

/* How many live neighbourhoods a cell in a grid has: */

fun ngbLiveCount(c: Cell, g: Grid) = neighbours(c) . filter { d -> isLive(d , g) } . count()

/* Apply the rules of the Game of Life to a grid to get a new grid: */

fun step(g: Grid) : Grid {
 if (g.isEmpty()) return g

 val minX = minWidth (g) !!
 val maxX = maxWidth (g) !!
 val minY = minHeight(g) !!
 val maxY = maxHeight(g) !!

 val t = cartesianProduct((minX-1..maxX+1).toList(), (minY-1..maxY+1).toList())
 val newg = t.filter { c -> (isLive(c, g) && ngbLiveCount(c, g) in 2..3)
                         || (isDead(c, g) && ngbLiveCount(c, g) == 3) }
 return newg
}

fun life1 (g: Grid) {
  render(g)
  Thread.sleep(100)
  life1(step(g))
}

fun main() {
 life1 (glider)
}

/* Alternative: */

/* Print successive iterations: */

fun printGrid(g: Grid, c1: Cell, c2: Cell) {
 for (y in c2.y downTo c1.y) {
  for (x in c1.x .. c2.x) {
    print(if (isLive (Cell(x,y), g)) "O" else ".")
  }
  println()
 }
}

fun <T> iterate(f: (T) -> T, x: T, n: Int): List<T> {
 if (n==0) return listOf()
 else return listOf(x) + iterate(f, f(x), n-1)
}

fun life2 (seed: Grid, n: Int) {
 val gs: List<Grid> = iterate({g -> step(g)}, seed, n+1)
 val minX = gs.map {g -> g.map { c -> c.x }} . flatten() . min() !!
 val maxX = gs.map {g -> g.map { c -> c.x }} . flatten() . max() !!
 val minY = gs.map {g -> g.map { c -> c.y }} . flatten() . min() !!
 val maxY = gs.map {g -> g.map { c -> c.y }} . flatten() . max() !!

 for (i in 0..n) {
   println("GENERATION " + i)
   printGrid(gs[i], Cell(minX-1,minY-1), Cell(maxX+1,maxY+1))
 }
}

fun main2() {
 life2 (glider, 7)
}
