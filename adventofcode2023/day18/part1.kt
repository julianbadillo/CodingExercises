import java.io.File
import java.io.BufferedReader
import java.util.PriorityQueue

fun main(args: Array<String>) {
    val reader = File(args[0]).bufferedReader()
    val instr = reader.readLines()
    val total = poolSize(instr)
    println(total)
}


data class P(val r: Int, val c: Int, val dir: Dir? = null) {
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
fun poolSize(lines: List<String>) : Int {
    
    var pos = P(0, 0, R)
    val pool = mutableSetOf<P>(pos)
    for (line in lines) {
        val (dir, l) = parseInstruction(line)
        for (i in 1..l) {
            pos = P(pos.r + dir.dr, pos.c + dir.dc, dir)
            pool.add(pos)
        }
    }
    
    var inside = mutableSetOf<P>(P(1, 1))
    var queue = ArrayDeque<P>()
    queue.addLast(P(1,1))
    // flood with BFS
    while (queue.size > 0) {
        pos = queue.removeFirst()
        for (dir in ALL_DIRS) {
            val pos2 = P(pos.r + dir.dr, pos.c + dir.dc)
            if (pool.contains(pos2)) continue
            if (inside.contains(pos2)) continue
            inside.add(pos2)
            queue.add(pos2)
        }
    }
    printPool(pool, inside)
    return pool.size + inside.size
}

data class Res(var dir: Dir, var l: Int)

fun parseInstruction(line: String): Res {
    // parse instructions
    val parts = line.split(" ")
    return Res(when(parts[0]) {
            "R" -> R
            "D" -> D
            "U" -> U
            else -> L
        },
        parts[1].toInt()
    )
}

fun printPool(pool: Set<P>, inside: Set<P>) {
    var R = pool.map{ it.r }.max()
    var C = pool.map{ it.c }.max()
    for (r in 0..R) {
        for (c in 0..C) {
            print(if(pool.contains(P(r, c))) "#" else if (inside.contains(P(r, c))) "O" else " ")
        }
        println()
    }
}
