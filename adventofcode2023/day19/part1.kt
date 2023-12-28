import java.io.File
import java.io.BufferedReader
import java.util.PriorityQueue

fun main(args: Array<String>) {
    val reader = File(args[0]).bufferedReader()
    processParts(reader.readLines())
}

data class Rule(val dest: String, val prop: String? = null, val op: String? = null, val v: Int? = null) {
    fun eval(p: Map<String, Int>): String? {
        // println("$this $p")
        if (prop == null) return dest
        val pv = p[prop] ?: -1
        if (op == "<" && pv < v ?: -1) return dest
        if (op == ">" && pv > v ?: -1) return dest
        return null
    }
}

fun processParts(lines: List<String>) {
    
    val ite = lines.iterator()
    var line = ite.next()
    val workflows = mutableMapOf<String, List<Rule>>()
    val parts = mutableListOf<Map<String, Int>>()
    // parse workflows
    while (line != "") {
        parseWorkflow(line, workflows)
        line = ite.next()
    }
    // for (w in workflows) {
    //     println(w)
    // }
    // parse parts
    while (ite.hasNext()) {
        parts.add(parsePart(ite.next()))
    }
    val accepted = parts.filter { processPart(workflows, it) }
    println(accepted)
    println(accepted.size)
    println(accepted.map{ it.values.sum() }.sum())
}

val pat1 = Regex("""(\w+)\{(.+)\}""")
val pat2 = Regex("""(\w+)|((\w+)([><])(\d+):(\w+))""")
fun parseWorkflow(line: String, workflows: MutableMap<String, List<Rule>>) {
    val mat = pat1.matchEntire(line)
    val name = mat?.groups?.get(1)?.value ?: ""
    val ruleText = mat?.groups?.get(2)?.value ?: ""
    val rules = mutableListOf<Rule>()
    for (rule in ruleText.split(",") ) {
        pat2.matchEntire(rule)?.groups?.let {
            // only destination
            if (it.get(1) != null) {
                rules.add(Rule(it.get(1)?.value ?: ""))
            } else {
                // logic rule
                rules.add(Rule(
                    dest = it.get(6)?.value ?: "",
                    prop = it.get(3)?.value,
                    op = it.get(4)?.value,
                    v =  it.get(5)?.value?.toInt(),
                ))
            }
        } ?: throw IllegalStateException("!!! NO MATCH")
    }
    workflows[name] = rules
}

val pat3 = Regex("""\{x=(\d+),m=(\d+),a=(\d+),s=(\d+)\}""")

fun parsePart(line: String): Map<String, Int> {
    val mat = pat3.matchEntire(line)
    return mat?.groups?.let { mapOf<String, Int>(
        "x" to (it.get(1)?.value?.toInt() ?: -1),
        "m" to (it.get(2)?.value?.toInt() ?: -1),
        "a" to (it.get(3)?.value?.toInt() ?: -1),
        "s" to (it.get(4)?.value?.toInt() ?: -1),
    )} ?: throw IllegalStateException("!!!")
}

fun processPart(workflows: Map<String, List<Rule>>, part: Map<String, Int>): Boolean {
    var step: String = "in"
    // print("$step")
    while (step != "A" && step != "R") {
        val workflow = workflows[step] ?: throw IllegalStateException("!!! '$step' not found")
        var dest: String? = null
        var ite = workflow.iterator()
        while (dest == null) {
            val rule = ite.next()
            dest = rule.eval(part)
        }
        step = dest
        // print("> $step")
    }
    // println()
    return step == "A"
}