import java.io.File
import java.io.BufferedReader


fun main(args: Array<String>) {
    val reader = File(args[0]).bufferedReader()
    val maze = reader.readLines()
    val total = farthestPoint(maze)
    println(total)
}

data class P(val r: Int, val c: Int) {
    override fun toString() = "P($r, $c)"
    operator fun plus(b: P) = P(r + b.r, c + b.c)
    operator fun unaryMinus() = P(-r, -c)
}
val S = P(1, 0)
val N = P(-1, 0)
val E = P(0, 1)
val W = P(0, -1)

val MOVES = mapOf<Char, List<P>>(
    '|' to listOf<P>(N, S),
    '-' to listOf<P>(E, W),
    'L' to listOf<P>(N, E),
    'J' to listOf<P>(N, W),
    '7' to listOf<P>(S, W),
    'F' to listOf<P>(S, E),
)

fun farthestPoint(maze: List<String>): Int {
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
    val queue = ArrayDeque<P>()
    val dist = mutableMapOf<P, Int>(start to 0)

    // look around start
    listOf(N, S, E, W)
        .filter { 0 <= (start + it).r && (start + it).r < R && 0 <= (start + it).c && (start + it).c < C }
        .forEach {
            val next = start + it
            if (MOVES[maze[next.r][next.c]]?.contains(-it) ?: false) {
                queue.addLast(next)
                dist[next] = 1
            }
        }

    while (queue.size > 0) {
        val p = queue.removeFirst()
        val d = dist[p] ?: throw IllegalStateException("Missing distance $p")
        val moves = MOVES[maze[p.r][p.c]] ?: throw IllegalStateException("Missing moves ${maze[p.r][p.c]}")
        for (move in moves) {
            val p2 = p + move
            if (p2.r < 0 || R <= p2.r || p2.c < 0 || C <= p2.c) {
                continue
            }
            val d2 = dist[p2]
            if (d2 == null || d + 1 < d2) {
                queue.addLast(p2)
                dist[p2] = d + 1
            }
        }
    }
    // find the farthest point
    return dist.values.max()
}
