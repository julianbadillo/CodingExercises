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
    //gates.forEach{ println(it) }
    // println("inputs = ${map["inv"]?.inputs}")
    // println("inputs = ${map["con"]?.inputs}")
    var totalHigh = 0
    var totalLow = 0
    for (i in 1..n) {
        //println("-- Push button $i --")
        val (high, low) = pushButton(map)
        totalHigh += high
        totalLow += low
    }
    println("Total = ${totalHigh} x ${totalLow} = ${totalHigh * totalLow}")
}

fun parseGate(line: String): Gate {
    val parts = line.split(" -> ")
    return if (parts[0] == "broadcaster") Gate("broadcast", "B", parts[1].split(", "))
            else Gate(parts[0].drop(1), parts[0].take(1), parts[1].split(", "))
}

data class GP(val origin: String, val dest: String, val pulse: Boolean)
data class R(var high: Int, var low: Int)

/* Simulate when pushing a button */
fun pushButton(map: Map<String, Gate>): R {
    // push a button
    var queue = ArrayDeque<GP>()
    queue.addLast(GP("button", "broadcast", false)) // send a low pulse to broadcast
    var result = R(high=0, low=1) // count the button pulse
    while (queue.size > 0) {
        val (origin, dest, pulse) = queue.removeFirst()
        // println("GP($origin, $dest, $pulse)")
        val gate = map[dest]
        if (gate == null) continue
        val res = gate.receivePulse(pulse, origin)
        if (res == null) continue
        // count pulses
        for (dest2 in gate.dest) {
            if (res) result.high++ else result.low++
            queue.addLast(GP(dest, dest2, res))
            // println("$dest -${if (res) "high" else "low"}-> $dest2")
        }    
    }
    // println("Low pulses=$low, Highpulses=$high")
    return result
}
