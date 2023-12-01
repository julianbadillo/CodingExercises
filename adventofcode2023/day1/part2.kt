import java.io.File
import java.io.BufferedReader

fun main(args: Array<String>) {
    val reader: BufferedReader = File(args[0]).bufferedReader()
    val lines = mutableListOf<String>()
    // javascript-like iterator
    reader.forEachLine { lines.add(it) }
    // map-reduce
    var total = lines.map { getNumber(it) }.sum()
    println(total)
}

/* Get the number from first and last digit in the line
 */
fun getNumber(line: String): Int {
    val numbers: Map<String, Int> = mapOf("one" to 1,
    "two" to 2,
    "three" to 3,
    "four" to 4,
    "five" to 5,
    "six" to 6,
    "seven" to 7,
    "eight" to 8,
    "nine" to 9,
    //"zero" to 0
    )
    val ints = mutableListOf<Int>()
    for (i in 0 until line.length) {
        // if a number
        if(line[i] >= '0' && line[i] <= '9'){
            ints.add(line[i].code - '0'.code)
        } else {
            // look for word digits
            for (size in 3..5) {
                // size fits
                if (i + size <= line.length && numbers.containsKey(line.substring(i, i + size))) {
                    val n = numbers[line.substring(i, i + size)]
                    if (n != null) {
                        ints.add(n)
                    }
                }
            }
        }
    }
    //println(ints)
    return ints.first() * 10 + ints.last()
}