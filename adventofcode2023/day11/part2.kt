import java.io.File
import java.io.BufferedReader


class G(val r: Int, val c: Int) {
    override fun toString() = "G($r, $c)"
    // Manhattan distance between galaxies
    operator fun minus(g2: G): Long = Math.abs(r - g2.r).toLong() + Math.abs(c - g2.c).toLong()
}

fun main(args: Array<String>) {
    val reader = File(args[0]).bufferedReader()
    val map = reader.readLines()
    sumShortestPath(map)
}

fun sumShortestPath(map: List<String>) {
    val R = map.size
    val C = map.first().length
    val galaxies = (0..<R).flatMap { r ->
        (0..<C).filter { c -> map[r][c] == '#'}.map { c -> G(r, c) }
    }
    val blankRows = (0..<R)
        .filter { r -> (0..<C).all{ c -> map[r][c] == '.'}}
        .toSet()
    val blankCols = (0..<C)
        .filter { c -> (0..<R).all{ r -> map[r][c] == '.'}}
        .toSet()

    // println("galaxies = $galaxies")
    // println("blankRows = $blankRows")
    // println("blankCols = $blankCols")
    var total = 0L
    for (i in 0..<galaxies.size-1) {
        for (j in i + 1..<galaxies.size) {
            val g1 = galaxies[i]
            val g2 = galaxies[j]
            var d = g1 - g2
            val r1 = Math.min(g1.r, g2.r)
            val r2 = Math.max(g1.r, g2.r)
            val c1 = Math.min(g1.c, g2.c)
            val c2 = Math.max(g1.c, g2.c)
            // account for expansion
            d += ((1000000L - 1L) * blankRows.filter { r1 < it && it < r2 }.size)
            d += ((1000000L - 1L) * blankCols.filter { c1 < it && it < c2 }.size)
            // println("D $g1 - $g2 = $d")
            total += d
        }
    }
    println(total)
}
