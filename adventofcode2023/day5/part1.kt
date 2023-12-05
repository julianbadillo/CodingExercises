import java.io.File
import java.io.BufferedReader

class R(val start: Long, val length: Long, val delta: Long) {
    override fun toString(): String = "R(start=$start, length=$length, delta=$delta)"
}

fun main(args: Array<String>) {
    val reader = File(args[0]).bufferedReader()
    // first line
    val seeds: List<Long> = reader.readLine().split(": ")[1].split(" ").map { it.toLong() }
    val maps = parseInput(reader)
    val locations = mapSeeds(seeds, maps)
    println(locations.min())
}

fun parseInput(reader: BufferedReader): List<List<R>> {
    val maps = mutableListOf<List<R>>()
    reader.readLine()
    while(reader.ready()) {
        // title
        reader.readLine()
        val list = mutableListOf<R>()
        while (reader.ready()) {
            val line = reader.readLine()
            if (line == "") {
                break
            }
            val nums = line.split(" ").map { it.toLong() }
            list.add(R(nums[1], nums[2], nums[0] - nums[1]))
        }
        maps.add(list)
    }
    // maps.forEach{ println(it.joinToString()) }
    return maps
}

fun mapSeeds(seeds: List<Long>, maps: List<List<R>> ): List<Long> {
    var prev = seeds
    var next: MutableList<Long> = mutableListOf<Long>()
    for (ranges in maps) {
        next = mutableListOf<Long>()
        for (x in prev) {
            val r = ranges.filter { it.start <= x && x < it.start + it.length }.firstOrNull()
            next.add(x + (r?.delta ?: 0))
        }
        // println("next = $next")
        prev = next
    }
    return next
}