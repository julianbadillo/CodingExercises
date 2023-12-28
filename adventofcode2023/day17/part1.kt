import java.io.File
import java.io.BufferedReader
import java.util.PriorityQueue

fun main(args: Array<String>) {
    val reader = File(args[0]).bufferedReader()
    val map = reader.readLines()
    val total = minHeatLoss(map)
    println(total)
}


data class D(val dr: Int, val dc: Int) {
    override fun toString() = when (this) {
        N -> "N"
        S -> "S"
        E -> "E"
        else -> "W"
    }
}

val N = D(-1, 0)
val W = D(0, -1)
val S = D(1, 0)
val E = D(0, 1)
val DIRECTIONS = listOf(N, W, S, E)

data class P(val r: Int, val c: Int, var l: Int = 0, var dir: D? = null, var prev: P? = null) {
    override fun toString(): String = if (dir == null) "P($r, $c, $l)" else "P($r, $c, $l, ${dir})"
    fun inBounds(R: Int, C: Int) = 0 <= r && r < R && 0 <= c && c < C
    override fun equals(other: Any?) = other is P && other.r == r && other.c == c && other.dir == dir && other.l == l
    override fun hashCode() = r.hashCode() + 17 * c.hashCode() + (dir?.hashCode()?:0) + 35 * l.hashCode()
}

fun minHeatLoss(map: List<String>) : Int {
    val R = map.size
    val C = map.first().length
    val cost = map.map{ it.map{ c -> c.code - '0'.code}.toTypedArray<Int>() }
    val visited = mutableSetOf<P>()
    val distance = mutableMapOf<P, Int>()
    //val queue = ArrayDeque<P>()
    val queue = PriorityQueue<P>() { a,b -> (distance[a] ?: 0) - (distance[b] ?: 0)}
    val inQueue = mutableSetOf<P>()
    distance[P(0,0, 1, S)] = 0
    distance[P(0,0, 1, E)] = 0
    // queue.addLast(P(0, 0, 1, S))
    queue.add(P(0, 0, 1, S))
    inQueue.add(P(0, 0, 1, S))
    // queue.addLast(P(0, 0, 1, E))
    queue.add(P(0, 0, 1, E))
    inQueue.add(P(0, 0, 1, E))
    val last = mutableSetOf<P>()
    // good'ol Djisktra
    while (queue.size > 0) {
        //var pos = queue.removeFirst()
        var pos = queue.poll()
        inQueue.remove(pos)
        var dist = distance[pos] ?: throw IllegalStateException("Missing distance ${pos}")
        visited.add(pos)
        if(pos.r == R-1 && pos.c == C-1) last.add(pos)
        for (dir2 in DIRECTIONS) {
            // skip opposite directions
            if (pos.dir == S && dir2 == N) continue
            if (pos.dir == N && dir2 == S) continue
            if (pos.dir == E && dir2 == W) continue
            if (pos.dir == W && dir2 == E) continue
            
            val pos2 = P(r = pos.r + dir2.dr, c = pos.c + dir2.dc, l = if(pos.dir == dir2) pos.l + 1 else 1, dir = dir2, prev = pos)
            if (pos2.l > 3) continue
            if (!pos2.inBounds(R, C)) continue
            if (pos2 in visited) continue
            if (pos2 in inQueue) continue
            if (distance[pos2]?.let { it < dist + cost[pos2.r][pos2.c] } ?: false) continue
            // println("$pos -> $pos2")
            distance[pos2] = dist + cost[pos2.r][pos2.c]
            queue.add(pos2)
            inQueue.add(pos2)
            // println("pos2=${pos2} added to the queue")
            
        }
        if(visited.size % 1000 == 0) {
            println("visited = ${visited.size}")
        }
    }
    
    println("visited = ${visited.size}")
    for (pos in last) {
        println("$pos : ${distance[pos]}")
    }
    return distance[last.first()] ?: -1
}

