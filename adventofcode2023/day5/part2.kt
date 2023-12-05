import java.io.File
import java.io.BufferedReader

class R(val start: Long, val length: Long, val delta: Long?=null) {
    val end get() = start + length - 1
    override fun toString(): String = "R(start=$start, length=$length" + (delta?.let {", delta=$delta"} ?: "") + ")"
    fun contains(x: Long): Boolean = start <= x && x <= end
}

fun main(args: Array<String>) {
    val reader = File(args[0]).bufferedReader()
    // first line
    val nums: List<Long> = reader.readLine().split(": ")[1].split(" ").map { it.toLong() }
    val seeds = (0..<nums.size step 2).map { R(nums[it], nums[it + 1]) }
    // println("seeds=$seeds")
    val maps = parseMaps(reader)
    val locations = mapSeeds(seeds, maps)
    println(locations.map { it.start }.min())
}

fun parseMaps(reader: BufferedReader): List<List<R>> {
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
        list.sortBy { it.start }
        maps.add(list)
    }
    // maps.forEach{ println(it.joinToString()) }
    return maps
}

fun mapSeeds(seeds: List<R>, maps: List<List<R>> ): List<R> {
    var prev = seeds
    var next: MutableList<R> = mutableListOf<R>()
    for (ranges in maps) {
        next = mutableListOf<R>()
        val split = prev.flatMap { splitRange(it, ranges) }
        // println("split = $split")
        // take split seed ranges and map them to the next level
        split.forEach {
            // each split seed range should only be contained in one map range at most
            val y = ranges.filter { r -> r.contains(it.start) }.firstOrNull()
            next.add(R(it.start + (y?.delta ?: 0), it.length))
        }
        // println("next = $next")
        prev = next
    }
    return next
}

/* split seed ranges into map ranges */
fun splitRange(r: R, ranges: List<R>): List<R> {
    // All points on ranges that may be contained on the seed range
    val points = mutableListOf<Long>(r.start, r.end)
    ranges.flatMap { y -> listOf(y.start, y.end) }
        .filter { p -> r.contains(p) }
        .forEach { p -> points.add(p) }
    points.sort()
    // println("points=$points")
    val split = (0..<points.size - 1).map { i ->
        R(start = points[i], length = points[i + 1] - points[i] + 1)
    }
    return split
}
