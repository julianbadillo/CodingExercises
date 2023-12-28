import java.io.File
import java.io.BufferedReader
import java.util.PriorityQueue

data class P(val x: Int, val y: Int, val z: Int)

data class Brick(var x1: Int, var y1: Int, var z1: Int, var x2: Int, var y2: Int, var z2: Int) {
    override fun toString() = "[$x1, $y1, $z1 ~ $x2, $y2, $z2]"
    constructor(s: String): this(0,0,0,0,0,0) {
        val parts = s.split("~")
        val p1 = parts[0].split(",").map {it.toInt()}
        val p2 = parts[1].split(",").map {it.toInt()}
        x1 = p1[0]
        y1 = p1[1]
        z1 = p1[2]
        x2 = p2[0]
        y2 = p2[1]
        z2 = p2[2]
        if (x1 > x2 || y2 > y2 || z1 > z2) println("! Errror coordinates: $this")
    }
    /* Set of space blocks occuppied by this brick */
    val blocks: Set<P> 
        get() = if (x1 != x2) (x1..x2).map { P(it, y1, z1) }.toSet()
            else if (y1 != y2) (y1..y2).map { P(x1, it, z1) }.toSet()
            else (z1..z2).map { P(x1, y1, it) }.toSet()
}

fun main(args: Array<String>) {
    val reader = File(args[0]).bufferedReader()
    val bricks = reader.readLines().map { Brick(it) }.toMutableList()
    // sort bricks by z1 coordinate
    bricks.sortBy { it.z1 }
    // bricks.forEach { println("$it: ${it.blocks}") }
    // 3d space
    var space = mutableMapOf<P, Brick>()
    for (brick in bricks) {
        // fall
        while (brick.z1 > 1 
            // directly below
            && brick.blocks.map { P(it.x, it.y, it.z - 1) }
                // empty space
                .all { space[it] == null } ) {
            brick.z1 = brick.z1 - 1  
            brick.z2 = brick.z2 - 1  
        }
        // fill the space
        // val below = brick.blocks.map { P(it.x, it.y, it.z - 1) }
        // println("brick = $brick")
        // println("below = $below")
        // println("occupied = ${below.all { space[it] == null }}")
        brick.blocks.forEach { space[it] = brick }
    }
    // bricks.forEach { println("$it: ${it.blocks}") }
    var total = 0
    val above = mutableMapOf<Brick,Set<Brick>>()
    val below = mutableMapOf<Brick,Set<Brick>>()
    bricks.forEach {
        above[it] = it.blocks
                            .map { bl -> P(bl.x, bl.y, bl.z + 1) }
                            .map{ bl -> space[bl] }
                            .filterNotNull()
                            .filter { bl -> bl != it }
                            .toSet()
        below[it] = it.blocks
                            .map { bl -> P(bl.x, bl.y, bl.z - 1) }
                            .map{ bl -> space[bl] }
                            .filterNotNull()
                            .filter { bl -> bl != it }
                            .toSet()
    }
    val supported = mutableMapOf<Brick, Set<Brick>>()
    for (brick in bricks) {
        val supported = above[brick] ?: throw IllegalStateException("! non above!")
        if (supported.size == 0) {
            // println("$brick can be disintegrated; does not support any other bricks.")
            total ++
        } else if ( supported.all{ (below[it]?.size?: throw IllegalStateException("!not below")) > 1  } ) {
            // println("$brick can be disintegrated; It's backed up by ${supported.map{ below[it] }.filterNotNull().flatMap{it}}")
            total ++
        } else {
            // println("$brick cannot be disintagrated, $supported will fall.")
        }
    }
    println("total = $total")
}
