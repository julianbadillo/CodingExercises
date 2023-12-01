import java.io.File
import java.io.BufferedReader

fun main(args: Array<String>) {
    val reader: BufferedReader = File(args[0]).bufferedReader()
    val lines = mutableListOf<String>()
    reader.forEachLine { lines.add(it) }
    var total = 0
    for(line in lines){
        total += getNumber(line)
    }
    print(total)
}

fun getNumber(line: String): Int {
    val ints = mutableListOf<Int>()
    for (i in 0..line.length-1) {
        if(line[i] >= '0' && line[i] <= '9'){
            ints.add(line[i].code - '0'.code)
        }
    }
    return ints.first() * 10 + ints.last()
}