import java.io.File
import java.io.BufferedReader
import java.util.PriorityQueue

fun main(args: Array<String>) {
    val reader = File(args[0]).bufferedReader()
    countRanges(reader.readLines())
}

data class Rule(val dest: String, val prop: String? = null, val op: String? = null, val v: Int? = null) {
    override fun toString() = if (prop == null || op == null || v == null) dest else "$prop $op $v : $dest"
    fun eval(p: Map<String, Int>): String? {
        // println("$this $p")
        if (prop == null || v == null) return dest
        val pv = p[prop] ?: -1
        if (op == "<" && pv < v) return dest
        if (op == ">" && pv > v) return dest
        return null
    }
    /* Split a range in two, first one when the rule is true, second when it wasn't */
    fun split(r: Range): List<Range> {
        if (prop == null || v == null) return listOf<Range>(r)
        val rTrue = r.copy()
        val rFalse = r.copy()
        val propTrue = rTrue[prop]
        val propFalse = rFalse[prop]
        if (propTrue == null || propFalse == null) return listOf<Range>(rTrue, rFalse)
        if (op == "<") {
            propTrue.max = Math.min(v - 1, propTrue.max)
            propFalse.min = Math.max(v, propFalse.min)
        } else if (op == ">") {
            propTrue.min = Math.max(v + 1, propTrue.min)
            propFalse.max = Math.min(v, propFalse.max) 
        }
        return listOf<Range>(rTrue, rFalse)
    }
}

class Range(m: Map<String, R>): HashMap<String, R> (m) {
    fun count(): Long = if(!empty()) this.values.map{(it.max - it.min + 1).toLong()}.reduce {a, b -> a * b} else 0L
    override fun toString() = this.map { (k, v) -> "$k$v"}.joinToString(",")
    fun empty(): Boolean = this.values.any{ it.min > it.max }
    fun copy(): Range {
        val r = Range(mapOf<String,R>())
        this.forEach {
            (k,v) -> r[k] = v.copy() 
        }
        return r
    }
}

data class R(var min: Int, var max: Int) {
    override fun toString() = "[$min-$max]"
}

fun countRanges(lines: List<String>) {
    
    val ite = lines.iterator()
    var line = ite.next()
    val workflows = mutableMapOf<String, List<Rule>>()
    // parse workflows
    while (line != "") {
        parseWorkflow(line, workflows)
        line = ite.next()
    }
    //println(workflows)

    val accepted = mutableListOf<Range>()
    val queueR = ArrayDeque<Range>()
    val queueW = ArrayDeque<String>()
    val start = Range(mapOf(
        "x" to R(1, 4000),
        "m" to R(1, 4000),
        "a" to R(1, 4000),
        "s" to R(1, 4000),
    ))
    queueR.addLast(start)
    queueW.addLast("in")

    while(queueR.size > 0) {
        var range = queueR.removeFirst()
        val wf = queueW.removeFirst()
        if (wf == "A") {
            accepted.add(range)
            continue
        }
        if (wf == "R") continue
    
        val workflow = workflows[wf] ?: listOf<Rule>()
        for (rule in workflow) {
            // println("Range = $range")
            // println("Rule = $rule")
            if (rule.prop == null || rule.v == null) {
                queueR.addLast(range)
                queueW.addLast(rule.dest)
                break
            }
            // split
            val (rTrue, rFalse) = rule.split(range)
            // println(" true -> $rTrue")
            // println(" false -> $rFalse")
            if (!rTrue.empty()) {
                queueR.addLast(rTrue)
                queueW.addLast(rule.dest)
            }
            // continue to all false until is empty
            if (rFalse.empty()) break
            range = rFalse
        }
    }
    //println(accepted)
    val total = accepted.map { it.count() }.sum()
    println(total)
}

val pat1 = Regex("""(\w+)\{(.+)\}""")
val pat2 = Regex("""(\w+)|((\w+)([><])(\d+):(\w+))""")
fun parseWorkflow(line: String, workflows: MutableMap<String, List<Rule>>) {
    val mat = pat1.matchEntire(line)
    val name = mat?.groups?.get(1)?.value ?: ""
    val ruleText = mat?.groups?.get(2)?.value ?: ""
    val rules = mutableListOf<Rule>()
    for (rule in ruleText.split(",") ) {
        val mat2 = pat2.matchEntire(rule)
        if (mat2?.groups?.get(1) != null) {
            rules.add(Rule(mat2.groups.get(1)?.value ?:""))
        } else {
            rules.add(Rule(
                dest = mat2?.groups?.get(6)?.value ?: "",
                prop = mat2?.groups?.get(3)?.value,
                op = mat2?.groups?.get(4)?.value,
                v =  mat2?.groups?.get(5)?.value?.toInt(),
            ))
        }
    }
    workflows[name] = rules
}
