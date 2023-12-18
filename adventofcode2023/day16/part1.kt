import java.io.File
import java.io.BufferedReader

fun main(args: Array<String>) {
    val reader = File(args[0]).bufferedReader()
    val map = reader.readLines()
    val total = energize(map)
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

fun energize(map: List<String>) : Int {
    val queue = ArrayDeque<P>()
    val energized = mutableMapOf<P, MutableList<P>>()
    queue.addLast(P(0, 0))
    queue.addLast(E)

    while (queue.size > 0) {
        var pos = queue.removeFirst()
        var dir = queue.removeFirst()
        followPath(map, queue, energized, pos, dir)
    }
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