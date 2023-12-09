import java.io.File
import java.io.BufferedReader

fun main(args: Array<String>) {
    val reader = File(args[0]).bufferedReader()
    val total = reader.readLines()
        .map{ it.split(" ").map{ it.toInt() }.toMutableList() }
        .map{ extrapolateBefore(it) }
        .sum()
    println(total)
}

fun extrapolateBefore(arr: MutableList<Int>): Int {
    var degree = 0
    val points = mutableListOf<MutableList<Int>>(arr)
    // Reduce to zero
    do {
        val last = points.last()
        val next = (0..<last.size - 1).map{ i -> last[i+1] - last[i] }.toMutableList()
        val notZeros = next.any{ it != 0 }
        points.add(next)
        degree++
    } while (notZeros)
    println("degree = ${degree - 1}")
    // points.forEach { println(it) }
    // calculate backwoards
    (points.size-1 downTo 1).forEach {
        i ->
        val bottom = points[i]
        val top = points[i - 1]
        top.add(0, top.first() - bottom.first())
    }
    // points.forEach { println(it) }
    return points.first().first()
}