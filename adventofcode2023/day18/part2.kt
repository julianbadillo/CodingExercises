import java.io.File
import java.io.BufferedReader
import java.util.PriorityQueue

fun main(args: Array<String>) {
    val reader = File(args[0]).bufferedReader()
    val instr = reader.readLines()
    val total = poolSize(instr)
    println(total)
    // val total = polygonArea(listOf(P(0, 0), P(0, 3), P(4, 3), P(4, 1), P(3, 1), P(3, 0)))
    // val total = polygonArea(listOf(P(0, 0), P(0, 4), P(3, 4), P(3, 3), P(2, 3), P(2, 1), P(3, 1), P(3, 0)))
    // println(total)
}


data class P(val r: Int, val c: Int) {
    override fun equals(other: Any?) = other is P && r == other.r && c == other.c
    override fun hashCode() = r.hashCode() + 17 * c.hashCode()
}

data class Dir(val dr: Int, val dc: Int) {
    override fun toString() = when (this) {
        U -> "U"
        L -> "L"
        D -> "D"
        else -> "R"
    }
}

val U = Dir(-1, 0)
val L = Dir(0, -1)
val D = Dir(1, 0)
val R = Dir(0, 1)
val ALL_DIRS = listOf(U, L, D, R)
fun poolSize(lines: List<String>) : Long {
    
    var pos = P(0, 0)
    val pool = mutableListOf<P>(pos)
    var lTotal = 0L
    for (line in lines) {
        // println("$dir $l")
        val (dir, l) = parseInstruction(line)
        lTotal += l.toLong()
        pos = P(pos.r + l * dir.dr, pos.c + l * dir.dc)
        if (pos != P(0, 0)) pool.add(pos)
    }
    // println(pool)
    val a = polygonArea(pool)
    println("Area = $a, lTotal = $lTotal , adjusted = ${a + lTotal / 2 + 1L}")
    return a + lTotal / 2 + 1L
}

data class Res(var dir: Dir, var l: Int)

// fun parseInstruction(line: String): Res {
//     // parse instructions
//     val parts = line.split(" ")
//     return Res(when(parts[0]) {
//             "R" -> R
//             "D" -> D
//             "U" -> U
//             else -> L
//         },
//         parts[1].toInt()
//     )
// }

fun parseInstruction(line: String): Res {
    // parse instructions
    val parts = line.split(" ")
    val hexCode = parts[2].substring(2, parts[2].length - 2)
    val dirCode = parts[2].substring(parts[2].length - 2, parts[2].length - 1)
    // println("hexCode=$hexCode dirCode=$dirCode")
    return Res(
        when(dirCode) {
            "0" -> R
            "1" -> D
            "2" -> L
            else -> U
        },
        hexCode.toInt(16)
    )
}

fun polygonArea(points: List<P>): Long {
    var A = 0L
    val n = points.size
    for (i in 0..<n) {
        A += (points[i].r + points[(i+1) % n].r).toLong() * (points[i].c - points[(i+1) % n].c).toLong()
    }
    return A / 2L
}
