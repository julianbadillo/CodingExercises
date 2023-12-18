import java.io.File
import java.io.BufferedReader

fun main(args: Array<String>) {
    val reader = File(args[0]).bufferedReader()
    val map = reader.readLines()
    val total = maxEnergize(map)
    println(total)
}

data class P(val r: Int, val c: Int) {
    override fun toString() = "P($r, $c)"
    operator fun plus(other: P) = P(r + other.r, c + other.c)
    fun inBounds(R: Int, C: Int) = 0 <= r && r < R && 0 <= c && c < C
    override fun equals(other: Any?) = other is P && other.r == r && other.c == c
    fun toDir() = when (this) {
        N -> "N"
        S -> "S"
        E -> "E"
        W -> "W"
        else -> toString()
    }
}

val N = P(-1, 0)
val W = P(0, -1)
val S = P(1, 0)
val E = P(0, 1)


fun maxEnergize(map: List<String>): Int {
    val R = map.size
    val C = map.first().length
    val points = mutableListOf<P>()
    for(r in 0..<R) { 
        points.add(P(r, 0))
        points.add(P(r, C-1))
    }
    for(c in 1..<C-1) { 
        points.add(P(0, c))
        points.add(P(R - 1, c))
    }
    val dirs = listOf(
        E, W, S, N,
    )
    return points.map { p ->
        dirs.map { d -> energize(map, p, d) }.max()
    }.max()
}

fun energize(map: List<String>, startPos: P, startDir: P): Int {
    val queue = ArrayDeque<P>()
    val energized = mutableMapOf<P, MutableList<P>>()
    queue.addLast(startPos)
    queue.addLast(startDir)
    while (queue.size > 0) {
        var pos = queue.removeFirst()
        var dir = queue.removeFirst()
        followPath(map, queue, energized, pos, dir)
    }
    // println("$startPos -> ${startDir.toDir()} : ${energized.size}")
    return energized.size
}

fun followPath(map: List<String>, queue: ArrayDeque<P>, energized: MutableMap<P, MutableList<P>>, startPos: P, startDir: P) {
    var pos = startPos
    var dir = startDir
    val R = map.size
    val C = map.first().length
    //println("Start $pos -> ${dir.toDir()}")
    while (true) {
        //println("$pos, ${dir.toDir()}")
        if(!pos.inBounds(R, C)) break
        // detect loops, or previously passed beam tracks
        if(energized[pos]?.contains(dir) ?: false) break
        if(energized[pos] == null) energized[pos] = mutableListOf<P>()
        energized[pos]?.add(dir)

        if (map[pos.r][pos.c] == '/') {
            dir = when(dir) {
                S -> W
                E -> N
                N -> E
                else -> S
            }
        } else if (map[pos.r][pos.c] == '\\') {
            dir = when(dir) {
                S -> E
                E -> S
                N -> W
                else -> N
            }
        // split array
        } else if (map[pos.r][pos.c] == '|' && (dir == E || dir == W)) {
            dir = N
            queue.addLast(pos)
            queue.addLast(S)
        } else if (map[pos.r][pos.c] == '-' && (dir == S || dir == N)) {
            dir = E
            queue.addLast(pos)
            queue.addLast(W)
        }
        // next square
        pos = pos + dir
    }
}