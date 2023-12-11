import java.io.File
import java.io.BufferedReader


fun main(args: Array<String>) {
    val reader = File(args[0]).bufferedReader()
    val maze = reader.readLines()
    tilesEnclosed(maze)
}

data class P(val r: Int, val c: Int, var dir: P? = null, var out: String? = null) {
    override fun toString(): String = if (dir != null) "P($r, $c, $dir, $out)" else when (this) {
            S -> "S"
            N -> "N"
            W -> "W"
            E -> "E"
            else -> "#"
        }

    operator fun plus(b: P): P = P(r + b.r, c + b.c)
    operator fun unaryMinus(): P = P(-r, -c)
    override fun equals(other: Any?): Boolean = (other is P && r == other.r && c == other.c)
    override fun hashCode(): Int = r.hashCode() + c.hashCode()
    fun inBounds(R: Int, C: Int): Boolean = (0 <= r && r < R && 0 <= c && c <= C)
    fun nextDir(nextChar: Char, lastOut: String): String {
        return when (nextChar) {
            '|' -> if (lastOut.contains("E")) "E" else "W"
            '-' -> if (lastOut.contains("N")) "N" else "S"
            'L' -> if (dir == N && lastOut.contains("W")) "SW"
                    else if (dir == N && lastOut.contains("E")) "NE"
                    else if (dir == E && lastOut.contains("N")) "NE"
                    else "SW"
            'J' -> if (dir == N && lastOut.contains("W")) "NW"
                    else if (dir == N && lastOut.contains("E")) "SE"
                    else if (dir == W && lastOut.contains("N")) "NW"
                    else "SE"
            '7' -> if (dir == S && lastOut.contains("W")) "SW"
                    else if (dir == S && lastOut.contains("E")) "NE"
                    else if (dir == W && lastOut.contains("N")) "NE"
                    else "SW"
            'F' -> if (dir == S && lastOut.contains("W")) "NW"
                    else if (dir == S && lastOut.contains("E")) "SE"
                    else if (dir == E && lastOut.contains("N")) "NW"
                    else "SE"
            else -> ""
        }
    }
}
val S = P(1, 0)
val N = P(-1, 0)
val E = P(0, 1)
val W = P(0, -1)
val SE = S + E
val SW = S + W
val NE = N + E
val NW = N + W
val MOVES = mapOf<Char, List<P>>(
    '|' to listOf<P>(N, S),
    '-' to listOf<P>(E, W),
    'L' to listOf<P>(N, E),
    'J' to listOf<P>(N, W),
    '7' to listOf<P>(S, W),
    'F' to listOf<P>(S, E),
)

fun tilesEnclosed(maze: List<String>) {
    val map: List<MutableList<Char>> = maze.map{ it.map { it }.toMutableList() }
    val R = maze.size
    val C = maze.first().length
    // find start
    var start: P = P(0, 0)
    for (r in 0..<R) {
        val c = maze[r].indexOf('S')
        if (c != -1) {
            start = P(r, c)
            break
        }
    }
    // find a second pipe
    val path = mutableListOf<P>()
    var next: P
    for (dir in listOf(S, E, N, W)) {
        next = start + dir
        if (!next.inBounds(R, C)) {
            continue
        }
        var char = maze[next.r][next.c]
        // opposite direction
        if (MOVES[char]?.contains(-dir) ?: false) {
            next.dir = -dir
            // assume one direction
            next.out = when (char) {
                '|' -> "E"
                '-' -> "N"
                'L' -> "NE"
                'J' -> "NW"
                '7' -> "SW"
                'F' -> "SE"
                else -> null
            }
            path.add(next)
            break
        }
    }
    // follow the thread
    val pipes = mutableSetOf<P>(path.last())
    while (path.last() != start) {
        var last = path.last()
        var char = maze[last.r][last.c]
        // opposite direction
        for (dir in MOVES[char] ?: emptyList()) {
            if (dir != last.dir) {
                next = last + dir
                val nextChar = maze[next.r][next.c]
                next.dir = -dir
                path.add(next)
                pipes.add(next)
                next.out = next.nextDir(nextChar, last.out ?: "")
                break
            }
        }
    }

    // find the start pipe shape
    start = path.last()
    val end = path.first()
    map[start.r][start.c] =  if (end.dir == N && start.dir == N) '|'
        else if (end.dir == S && start.dir == S) '|'
        else if (end.dir == E && start.dir == E) '-'
        else if (end.dir == W && start.dir == W) '-'
        else if (end.dir == N && start.dir == W) '7'
        else if (end.dir == N && start.dir == E) 'F'
        else if (end.dir == S && start.dir == W) 'J'
        else if (end.dir == S && start.dir == E) 'L'
        else '*'
    var tilesIn = 0
    for (r in 0..<R) {
        var isIn = false
        var lastCross: Char? = null
        for (c in 0..<C) {
            if (P(r, c) in pipes ) {
                // crossing a vertical pipe
                if (map[r][c] == '|') {
                    isIn = !isIn    
                    map[r][c] = '│'
                } else if (map[r][c] == '-') {
                    map[r][c] = '─'
                } else if (map[r][c] == 'L') {
                    lastCross = map[r][c]
                    map[r][c] = '└'
                } else if (map[r][c] == 'F') {
                    lastCross = map[r][c]
                    map[r][c] = '┌'
                } else if (map[r][c] == 'J') {
                    if (lastCross == 'L') {
                        lastCross = null
                        // keep same side
                    } else if(lastCross == 'F') {
                        lastCross = null
                        isIn = !isIn    
                    }
                    map[r][c] = '┘'
                } else if (map[r][c] == '7') {
                    if (lastCross == 'F') {
                        lastCross = null
                        // keep same side
                    } else if(lastCross == 'L') {
                        lastCross = null
                        isIn = !isIn    
                    }
                    map[r][c] = '┐'
                }                
            } else if (!(P(r, c) in pipes)) {
                map[r][c] = if (isIn) 'I' else 'O'
                if (isIn) { tilesIn++ }
            }
        }
    }

    printMaze(map)
    println(tilesIn)
}

fun printMaze(map: List<List<Char>>) {
    val R = map.size
    val C = map.first().size
    for (r in 0..<R) {
        for (c in 0..<C) {
            print(map[r][c])
        }
        println()
    }
}