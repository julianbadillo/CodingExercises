import java.io.File
import java.io.BufferedReader
import java.util.PriorityQueue

fun main(args: Array<String>) {
    val reader = File(args[0]).bufferedReader()
    val steps = args[1].toInt()
    countPlots(reader.readLines(), steps)
}

data class P(val r: Int, val c: Int) {
    override fun equals(other: Any?) = other is P && r == other.r && c == other.c
    override fun hashCode() = r.hashCode() + 17 * c.hashCode()
    fun inBounds(R: Int, C: Int) = 0 <= r && r < R && 0 <= c && c < C
}

data class D(val dr: Int, val dc: Int)

val N = D(-1, 0)
val W = D(0, -1)
val S = D(1, 0)
val E = D(0, 1)
val DIRECTIONS = listOf(N, W, S, E)

fun countPlots(map: List<String>, steps: Int) {
    val R = map.size
    val C = map.first().length
    // 3-d array
    val marked = map.map { it.map {  
        if (it == '#') Array<Boolean>(0) {false}
        else Array<Boolean>(steps + 1) { false }
      }   
    }

    // find start
    var start: P = P(0, 0)
    for (r in 0..<R) {
        for (c in 0..<C) {
            if (map[r][c] == 'S') {
                start = P(r, c)
            }
        }
    }
    marked[start.r][start.c][0] = true
    // dynamic prog.
    for (d in 0..<steps) {
        for (r in 0..<R) {
            for (c in 0..<C) {
                if (map[r][c] == '#') continue // rock
                // println("$r $c $d = ${marked[r][c][d]}")
                if (!marked[r][c][d]) continue // not reached for this number of steps
                for (dir in DIRECTIONS) {
                    val p2 = P(r + dir.dr, c + dir.dc)
                    if (!p2.inBounds(R, C)) continue
                    if (map[p2.r][p2.c] == '#') continue
                    marked[p2.r][p2.c][d + 1] = true
                    // println("-->$p2")
                }
            }
        }
    }


    printMap(map, marked, steps)
    var total = 0
    for (r in 0..<R) {
        for (c in 0..<C) {
            if (map[r][c] != '#' && marked[r][c][steps]) total++
        }
    }
    println(total)
}

fun printMap(map: List<String>, marked: List<List<Array<Boolean>>>, steps: Int) {
    val R = map.size
    val C = map.first().length
    for (r in 0..<R) {
        for (c in 0..<C) {
            if (map[r][c] != '#' && marked[r][c][steps])
                print('O')
            else
                print(map[r][c])
        }
        println()
    }
}