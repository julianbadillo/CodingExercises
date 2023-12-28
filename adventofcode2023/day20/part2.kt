import java.io.File
import java.io.BufferedReader
import java.util.PriorityQueue



data class Gate(val origin: String, val op: String, val dest: List<String>) {
    var on: Boolean = false
    var inputs: MutableMap<String, Boolean> = mutableMapOf<String, Boolean>()
    /* Receive a pulse (true=high, false=low), and calculate result 
        to be passed to destinations (true=high, false=low, null=none) */
    fun receivePulse(pulse: Boolean, from: String) : Boolean? {
        // broadcast
        when (op) {
            "B" -> return pulse
            "%" -> 
                if (!pulse) {
                    on = !on
                    return on
                }
            "&" -> {
                inputs[from] = pulse
                //println("$inputs -> ${inputs.values}}")
                return !inputs.values.all{it}
            }
        }
        return null
    }
    override fun toString() = "($origin, $op, $dest [on: $on, $inputs])"
}



fun main(args: Array<String>) {
    val reader = File(args[0]).bufferedReader()
    val n = args[1].toInt()
    val gates = reader.readLines().map { parseGate(it) }
    // count inputs for conjunctions
    gates.filter { it.op == "&" }
        .forEach { 
            gates.filter{ g -> g.dest.contains(it.origin) }
                .forEach{ g -> it.inputs[g.origin] = false }
        }

    // map them all
    val map = gates.map{ it.origin to it }.toMap()
    printRecursive(map, 0, "broadcast")
    //gates.forEach{ println(it) }
    // println("inputs = ${map["inv"]?.inputs}")
    // println("inputs = ${map["con"]?.inputs}")
    
    // for (i in (1..n)) {
    //     //println("-- Push button $i --")
    //     if (pushButton(map)) {
    //         println("Total = ${i}")
    //         break
    //     } else if (i % 1000 == 0 ) {
    //         println("$i")
    //     }
    // }
}

fun parseGate(line: String): Gate {
    val parts = line.split(" -> ")
    return if (parts[0] == "broadcaster") Gate("broadcast", "B", parts[1].split(", "))
            else Gate(parts[0].drop(1), parts[0].take(1), parts[1].split(", "))
}

fun printRecursive(map: Map<String, Gate>, level: Int, node: String) {
    if (level > 4) return
    print(Array<Char>(level){' '}.joinToString(""))
    val gate = map[node]
    if (gate == null) {
        println(node)
        return
    }
    println(gate)
    for (d in gate.dest){
        printRecursive(map, level+1, d)
    }
}

data class GP(val origin: String, val dest: String, val pulse: Boolean)
fun pushButton(map: Map<String, Gate>): Boolean {
    // push a button

    var queue = ArrayDeque<GP>()
    queue.addLast(GP("button", "broadcast", false)) // send a low pulse to broadcast
    while (queue.size > 0) {
        val (origin, dest, pulse) = queue.removeFirst()
        // println("GP($origin, $dest, $pulse)")
        val gate = map[dest]
        if (gate == null) continue
        val res = gate.receivePulse(pulse, origin)
        if (res == null) continue    
        for (dest2 in gate.dest) {
            if (dest2 == "rx") {
                // println("$dest -${if (res) "high" else "low"}-> $dest2") 
                if (!res) return true
            }
            queue.addLast(GP(dest, dest2, res))
            // println("$dest -${if (res) "high" else "low"}-> $dest2")
        }
    }
    // println("Low pulses=$low, Highpulses=$high")
    return false
}
