import java.io.File
import java.io.BufferedReader


fun main(args: Array<String>) {
    val reader = File(args[0]).bufferedReader()
    val total = reader.readLines().map { countArrangements(it) }.sum()
    println(total)
}

// group class
data class G(val l: Int, var pos: Int = 0, var maxPos: Int = 0) {
    override fun toString() = "G($l, $pos, $maxPos)"
}

fun countArrangements(line: String): Int {
    val parts = line.split(" ")
    // expansion
    var springs = parts[0] //(1..5).map { parts[0] }.joinToString("?")
    val groups = parts[1].split(",").map { it.toInt() }.map { l -> G(l)} //(1..5).flatMap{ parts[1].split(",").map { x -> x.toInt() }.map { l -> G(l)} }// damaged springs
    var maxPos = springs.length + 1
    groups.reversed().forEach{
        maxPos -= (it.l + 1)
        it.maxPos = maxPos
    }
    println(springs)
    println(groups)
    var pos = 0
    groups.forEach {
        it.pos = pos
        pos += (it.l + 1) // at least one separation
    }
    var count = 0
    do {
        if (matches(springs, groups)) count++
    } while (nextPosition(groups))
    return count
}

/* Moves the array to the next position */
fun nextPosition(groups: List<G>): Boolean {
    var i = groups.size - 1
    while (i >= 0) {
        // calculate space needed to the right of it
        if (groups[i].pos < groups[i].maxPos) {
            groups[i].pos++ // move it
            // move all the ones to the right
            ((i + 1)..<groups.size).forEach { j ->
                groups[j].pos = groups[j - 1].pos + 1 + groups[j - 1].l
            }
            // println("positions += $positions")
            return true
        } else {
            i-- // previous position
        }
    }
    return false
}

/* Evaluates factibility */
fun matches(springs: String, groups: List<G>): Boolean {
    val arr = Array<Char>(springs.length) {'.'}
    groups.forEach {
        (it.pos..<it.pos + it.l).forEach {
            i ->
            arr[i] = '#'
        }
    }
    val solution = arr.joinToString("")
    val r = springs.zip(solution) {
        a, b -> 
        if (a == b) true
        else if (a == '?') true
        else false
    }.all { it }
    println("$solution vs $springs = $r")
    return r
}
