
import java.io.File
import java.io.BufferedReader

data class P(val x: Double, val y: Double) {
    fun inBounds(a: Double, b: Double) = (a <= x && x <= b && a <= y && y <= b)
}

/* Line equation AX + BY = C */
class Line (val x0: Double, val y0: Double, val vx: Double, val vy: Double) {
    val a: Double = vy
    val b: Double = -vx
    val c: Double = x0 * vy - y0 * vx

    override fun toString() = "$a*X + $b*Y = $c"
    /* Intersection with other line, or null if none */
    fun intersection(l2: Line): P? {
        // parallel lines
        if (a * l2.b == b * l2.a) return null
        // 2 x 2 solution
        val m = (a * l2.b - b * l2.a)
        val x = (c * l2.b - b * l2.c) / m
        val y = (a * l2.c - c * l2.a) / m
        // if in the past for any of the lines
        if (vx > 0 && x < x0) return null
        if (l2.vx > 0 && x < l2.x0) return null
        if (vx < 0 && x > x0) return null
        if (l2.vx < 0 && x > l2.x0) return null
        return P(x, y)
    }
}

fun main(args: Array<String>) {
    val reader = File(args[0]).bufferedReader()
    val lines = reader.readLines().map { parseLine(it) }
    var count = 0
    for (i in 0..<lines.size - 1) {
        for (j in i + 1 ..<lines.size ) {
            val inter = lines[i].intersection(lines[j])
            println("Line $i and $j: $inter")
            // if (inter?.inBounds(7.0, 27.0) ?: false) count++
            if (inter?.inBounds(2e14, 4e14) ?: false) count++
        }
    }
    println(count)
}

fun parseLine(line: String): Line {
    val parts = line.replace(" ", "").split("@")
    val coords = parts[0].split(",").map { it.toDouble() }
    val vels = parts[1].split(",").map { it.toDouble() }
    return Line(coords[0], coords[1], vels[0], vels[1])
}


