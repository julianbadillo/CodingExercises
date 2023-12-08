import java.io.File
import java.io.BufferedReader


class Node(val name: String) {
    var left: Node? = null
    var right: Node? = null
    override fun toString(): String = "N($name, ${left?.name}, ${right?.name})"
}

fun main(args: Array<String>) {
    val reader = File(args[0]).bufferedReader()
    val instr = reader.readLine()
    reader.readLine()
    val nodes = parseNodes(reader.readLines())
    //println(nodes)
    val total = followPath(instr, nodes)
    println(total)
}

fun parseNodes(lines: List<String>): Map<String, Node> {
    val nodes = mutableMapOf<String, Node>()
    for (line in lines) {
        val parts = line.split(" = ")
        val node: Node = nodes[parts[0]] ?: Node(parts[0])
        nodes[node.name] = node
        val children = parts[1].replace("(", "").replace(")", "").split(", ")
        node.left = nodes[children[0]] ?: Node(children[0])
        nodes[(node.left as Node).name] = node.left as Node
        node.right = nodes[children[1]] ?: Node(children[1])
        nodes[(node.right as Node).name] = node.right as Node
    }
    return nodes
}

fun followPath(instr: String, nodes: Map<String, Node>): Long {
    var steps = 0
    var nodes = nodes.values.filter { it.name[2] == 'A' }
    var dists = nodes.map { findClosestEndNode(it, instr).toLong() }
    println(dists)
    var total = dists.reduce { acc, element -> lcm(acc, element) }
    return total
}

fun findClosestEndNode(start: Node, instr: String): Int {
    var steps = 0
    var node = start
    while (node.name[2] != 'Z') {
        node = (if (instr[steps % instr.length] == 'L') node.left else node.right) as Node
        steps++
    }
    return steps
}

fun gcd(a: Long, b: Long): Long {
    if (a >= b && a % b == 0L) { return b }
    if (a <= b && b % a == 0L) { return a }
    if (a > b) { return gcd(b, a % b) }
    else return gcd(a, b % a)
}

fun lcm(a: Long, b: Long): Long = a * b / gcd(a, b)

