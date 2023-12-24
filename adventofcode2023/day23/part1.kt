import java.io.File
import java.io.BufferedReader
import java.util.PriorityQueue

fun main(args: Array<String>) {
    val reader = File(args[0]).bufferedReader()
    val total = longestHike(reader.readLines())
    println(total)
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

fun longestHike(map: List<String>): Int {
    // backtracking
    return longestHikeR(map, P(0, 1), mutableSetOf<P>()) ?: -1
}

fun longestHikeR(map: List<String>, pos: P, points: MutableSet<P>): Int? {
    val R = map.size
    val C = map.first().length
    if (pos.r == R -1 && pos.c == C - 2) // final position
        return 0
    points.add(pos)
    val results = mutableListOf<Int>()
    for (dir in DIRECTIONS) {
        val pos2 = P(pos.r + dir.dr, pos.c + dir.dc)
        if (!pos2.inBounds(R, C)) continue
        if (map[pos2.r][pos2.c] == '#') continue
        if (map[pos2.r][pos2.c] == 'v' && dir != S) continue
        if (map[pos2.r][pos2.c] == '^' && dir != N) continue
        if (map[pos2.r][pos2.c] == '>' && dir != E) continue
        if (map[pos2.r][pos2.c] == '<' && dir != W) continue
        if (points.contains(pos2)) continue // never walk the same point twice
        val res = longestHikeR(map, pos2, points)
        if (res != null) results.add(res)
    }
    points.remove(pos)
    return if (results.size == 0) null else 1 + results.max()
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