import java.io.File
import java.io.BufferedReader


class G(val r: Int, val c: Int) {
    override fun toString() = "G($r, $c)"
}

fun main(args: Array<String>) {
    val reader = File(args[0]).bufferedReader()
    val map = reader.readLines()
    sumShortestPath(map)
}

fun sumShortestPath(map: List<String>) {
    val R = map.size
    val C = map.first().length
    val galaxies = mutableListOf<G>()
    val blankRows = mutableSetOf<Int>()
    val blankCols = mutableSetOf<Int>()
    for (r in 0..<R) {
        for (c in 0..<C) {
            if (map[r][c] == '#') galaxies.add(G(r, c))
        }
    }

    for (r in 0..<R) {
        if ((0..<C).all{ c -> map[r][c] == '.'}){
            blankRows.add(r)
        }
    }
    for (c in 0..<C) {
        if ((0..<R).all{ r -> map[r][c] == '.'}){
            blankCols.add(c)
        }
    }

    // println("galaxies = $galaxies")
    // println("blankRows = $blankRows")
    // println("blankCols = $blankCols")
    var total = 0
    for (i in 0..<galaxies.size-1) {
        for (j in i+1..<galaxies.size) {
            val g1 = galaxies[i]
            val g2 = galaxies[j]
            var d = Math.abs(g1.r - g2.r) + Math.abs(g1.c - g2.c)
            val r1 = Math.min(g1.r, g2.r)
            val r2 = Math.max(g1.r, g2.r)
            val c1 = Math.min(g1.c, g2.c)
            val c2 = Math.max(g1.c, g2.c)
            d += blankRows.filter { r1 < it && it < r2 }.size
            d += blankCols.filter { c1 < it && it < c2 }.size
            // println("D $g1 - $g2 = $d")
            total += d
        }
    }
    println(total)
}
