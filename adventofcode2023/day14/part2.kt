import java.io.File
import java.io.BufferedReader

class Rock(val fixed: Boolean = false) {
    override fun toString() = if(fixed) "#" else "O"
}


fun main(args: Array<String>) {
    val reader = File(args[0]).bufferedReader()
    val map = reader.readLines()
    val total = rockLoad(map)
    println(total)
}

fun rockLoad(map: List<String>): Int {

    val R = map.size
    val C = map.first().length
    val rocks = Array(R) { arrayOfNulls<Rock?>(C) }
    for (r in 0..<R) {
        for (c in 0..<C) {
            rocks[r][c] = if (map[r][c] == 'O') {
                Rock(fixed = false)
            } else if (map[r][c] == '#') {
                Rock(fixed = true)
            } else  {
                null
            }
        }
    }
    val cycle = listOf(N, W, S, E)
    val record = mutableMapOf<String, Int>()
    val loads = mutableListOf<Int>()
    var cycleLength: Int
    var i = 0
    while (true) {
        for (d in cycle) tilt(rocks, d)
        // println("Cycle $i")
        //printRocks(rocks)
        val line = rocks.map { it.map{ x -> x?.toString() ?: "."}.joinToString("") }.joinToString("")
        if (record[line] != null) {
            cycleLength = i - record[line] as Int
            println("Cycle found at $i, ${record[line]}")
            println("Load = ${northLoad(rocks)}")
            break
        } else {
            record[line] = i
            loads.add(northLoad(rocks))
            i++
        }
        //println(line)
    }
    val offset = loads.size - cycleLength
    println("cycleLength=$cycleLength loads=${loads.size} offset=${offset}")
    // println(loads)
    val index = (1000000000L - 1 - offset) % cycleLength + offset
    println("index=$index")
    return loads[index.toInt()]
}
data class P(val dr: Int, val dc: Int)

val N = P(-1, 0)
val W = P(0, -1)
val S = P(1, 0)
val E = P(0, 1)

fun tilt(rocks: Array<Array<Rock?>>, dir: P) {
    val R = rocks.size
    val C = rocks[0].size
    // cycles depend on direction
    if (dir == N) {
        for (r in 0..<R)
            for (c in 0..<C) 
                dropRock(rocks, dir, r, c)
    } else if (dir == S) {
        for (r in R-1 downTo 0)
            for (c in 0..<C) 
                dropRock(rocks, dir, r, c)
    } else if (dir == W) {
        for (c in 0..<C)
            for (r in 0..<R) 
                dropRock(rocks, dir, r, c)
    } else if (dir == E) {
        for (c in C-1 downTo 0)
            for (r in 0..<R) 
                dropRock(rocks, dir, r, c)
    }
}

fun dropRock(rocks: Array<Array<Rock?>>, dir: P, r: Int, c: Int) {
    val R = rocks.size
    val C = rocks[0].size
    
    if (!(rocks[r][c]?.fixed?:true)) {            
        // within bounds and doesn't find another obstacke
        var r2 = r
        var c2 = c
        while ( 0 <= r2 + dir.dr && r2 + dir.dr < R 
                && 0 <= c2 + dir.dc && c2 + dir.dc < C 
                && rocks[r2 + dir.dr][c2 + dir.dc] == null) {
                    r2 += dir.dr
                    c2 += dir.dc
                }
        if (r2 != r || c2 != c) {
            rocks[r2][c2] = rocks[r][c]
            rocks[r][c] = null
        }
    }
}


fun northLoad(rocks: Array<Array<Rock?>>): Int {
    val R = rocks.size
    val C = rocks[0].size
    var total = 0
    for (r in 0..<R) {
        for (c in 0..<C) {
            total += if (!(rocks[r][c]?.fixed?:true)) R - r else 0
        }
    }
    return total
}

fun printRocks(rocks: Array<Array<Rock?>>) {
    for (l in rocks) {
        for (r in l) {
            print(r ?: ".")
        }
        println()
    }
}
