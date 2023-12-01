import java.io.File
import java.io.BufferedReader

fun main(args: Array<String>) {
    val reader: BufferedReader = File(args[0]).bufferedReader()
    val lines = reader.readLines()
    // map-reduce
    var total = lines.map { getNumber(it) }.sum()
    println(total)
}

/* Get the 2-digit number from the first and last digit on the string */
fun getNumber(line: String): Int {
    // filter - then map
    val ints = line.filter{ it >= '0' && it <= '9' }.map{ it.code - '0'.code }
    return ints.first() * 10 + ints.last()
}