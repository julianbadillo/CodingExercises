import java.io.File
import java.io.BufferedReader

fun main(args: Array<String>) {
    val reader = File(args[0]).bufferedReader()
    val instructions = reader.readLine().split(",")
    val total = fitLenses(instructions)
    println(total)
}

fun hash(line: String) : Int {
    var total = 0
    line.forEach { 
        total += it.code  
        total *= 17
        total %= 256
    }
    // println("'$line'  hash: $total")
    return total
}

data class L(val label: String, val hash: Int, val fl: Int) {
    override fun equals(other: Any?): Boolean = (other is L) && other.label == label
    override fun toString() = "$label $fl"
}

fun fitLenses(instructions: List<String>): Int {
    val boxes = Array<MutableList<L>>(256) { mutableListOf<L>() }
    for (inst in instructions) {
        if ('-' in inst) {
            boxes[hash].remove(L(inst.substring(0, inst.length - 1), 0, 0))
        } else if ('=' in inst) {
            val parts = inst.split("=")
            val lens = L(parts[0], hash(parts[0]), parts[1].toInt())
            val i = boxes[hash].indexOf(lens)
            if (i == -1) {
                boxes[hash].add(lens)
            } else {
                boxes[hash][i] = lens
            }
        }
    }
    // printBoxes(boxes)
    return boxes.mapIndexed { i, it -> it.mapIndexed{ j, it2 -> (i + 1) * (j + 1) * it2.fl }.sum() }.sum()
}

fun printBoxes(boxes: Array<MutableList<L>>) {
    boxes.forEachIndexed{ i, it ->
        if(it.size > 0) {
            println("${i} $it")
        }
    }
}