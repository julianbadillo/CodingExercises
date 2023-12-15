import java.io.File
import java.io.BufferedReader

fun main(args: Array<String>) {
    val reader = File(args[0]).bufferedReader()
    val total = reader.readLine().split(",").map { hash(it) }.sum()
    println(total)
}

fun hash(line: String) : Int {
    var total = 0
    line.forEach{ 
        total += it.code  
        total *= 17
        total %= 256
    }
    // println("'$line'  hash: $total")
    return total
}