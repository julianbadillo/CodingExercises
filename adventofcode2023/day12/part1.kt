import java.io.File
import java.io.BufferedReader


fun main(args: Array<String>) {
    val reader = File(args[0]).bufferedReader()
    val total = reader.readLines().map { countArrangements(it) }.sum()
    println(total)
}

fun countArrangements(line: String): Int {
    val parts = line.split(" ")
    val springs = parts[0]
    val groups = parts[1].split(",").map { it.toInt() } // damaged springs
    val positions = mutableListOf<Int>()
    var pos = 0
    for (g in groups) { 
        positions.add(pos)
        pos += (g + 1) // at least one separation
    }
    var count = 0
    do {
        if (matches(springs, groups, positions)) count++
    } while (nextPosition(positions, groups, springs.length))
    return count
}

/* Moves the array to the next position */
fun nextPosition(positions: MutableList<Int>, groups: List<Int>, length: Int): Boolean {
    var i = groups.size - 1
    while (i >= 0) {
        // calculate space needed to the right of it
        val right = (i..<groups.size).map{j -> groups[j] + 1}.sum()
        if (positions[i] <= length - right) {
            positions[i]++ // move it
            // move all the ones to the right
            ((i + 1)..<groups.size).forEach { j ->
                positions[j] = positions[j - 1] + 1 + groups[j - 1]
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
fun matches(springs: String, groups: List<Int>, positions: List<Int>): Boolean {
    val arr = Array<Char>(springs.length) {'.'}
    positions.zip(groups) {
        pos: Int, g: Int ->
        (pos..<pos+g).forEach {
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
    // println("$solution vs $springs = $r")
    return r
}
