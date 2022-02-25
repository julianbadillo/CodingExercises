package adventofcode

import java.io.File
import kotlin.math.min

/**
 *
Advent of Code

[About][Events][Shop][Settings][Log Out]

julianbadillo 10*
sub y{2019}

[Calendar][AoC++][Sponsors][Leaderboard][Stats]

Our sponsors help make Advent of Code possible:
TwilioQuest - Play Advent of Code and earn rad loot in TwilioQuest, a developer RPG for Mac, Windows, and Linux. Learn JavaScript, Python, git, APIs for SMS, VoIP, or WhatsApp, and much more.
--- Day 6: Universal Orbit Map ---

You've landed at the Universal Orbit Map facility on Mercury. Because navigation in space often involves transferring between orbits, the orbit maps here are useful for finding efficient routes between, for example, you and Santa. You download a map of the local orbits (your puzzle input).

Except for the universal Center of Mass (COM), every object in space is in orbit around exactly one other object. An orbit looks roughly like this:

\
\
|
|
AAA--> o            o <--BBB
|
|
/
/

In this diagram, the object BBB is in orbit around AAA. The path that BBB takes around AAA (drawn with lines) is only partly shown. In the map data, this orbital relationship is written AAA)BBB, which means "BBB is in orbit around AAA".

Before you use your map data to plot a course, you need to make sure it wasn't corrupted during the download. To verify maps, the Universal Orbit Map facility uses orbit count checksums - the total number of direct orbits (like the one shown above) and indirect orbits.

Whenever A orbits B and B orbits C, then A indirectly orbits C. This chain can be any number of objects long: if A orbits B, B orbits C, and C orbits D, then A indirectly orbits D.

For example, suppose you have the following map:

COM)B
B)C
C)D
D)E
E)F
B)G
G)H
D)I
E)J
J)K
K)L

Visually, the above map of orbits looks like this:

G - H       J - K - L
/           /
COM - B - C - D - E - F
\
I

In this visual representation, when two objects are connected by a line, the one on the right directly orbits the one on the left.

Here, we can count the total number of orbits as follows:

D directly orbits C and indirectly orbits B and COM, a total of 3 orbits.
L directly orbits K and indirectly orbits J, E, D, C, B, and COM, a total of 7 orbits.
COM orbits nothing.

The total number of direct and indirect orbits in this example is 42.

What is the total number of direct and indirect orbits in your map data?

To begin, get your puzzle input.

Answer:

You can also [Shareon Twitter Mastodon] this puzzle.

 * */

val lines = File("../data/adventofcode2019/orbits.txt").readLines()
//val lines = listOf<String>("COM)B", "B)C", "C)D", "D)E", "E)F", "B)G", "G)H", "D)I", "E)J", "J)K", "K)L", "K)YOU", "I)SAN")


class Orbits {

    var orbits: MutableMap<String, MutableList<String>> = mutableMapOf<String, MutableList<String>>()
    var parent: MutableMap<String, String> = mutableMapOf<String, String>()

    constructor(lines: Collection<String>) {
        // build map
        for (line in lines) {
            val parts = line.split(")")
            if (parts[0] !in orbits)
                this.orbits[parts[0]] = mutableListOf<String>()
            this.orbits[parts[0]]!!.add(parts[1])
            this.parent[parts[1]] = parts[0]
        }
    }

    fun pathToCOM(from: String): List<String> {
        var path = mutableListOf<String>()
        var node = from
        while (!node.equals("COM") && node in this.parent) {
            node = this.parent[node]!!
            path.add(node)
        }
        return path.reversed()
    }

    // sum of heights - total orbits
    fun sumHeights(): Int{
        var height = mutableMapOf<String, Int>()
        var queue = mutableListOf<String>("COM")
        height["COM"] = 0
        while (!queue.isEmpty()){
            var node = queue.removeAt(0)
            var h = height[node]!!
            if(node in orbits){
                for(child in orbits[node]!!){
                    height[child] = h + 1
                    queue.add(child)
                }
            }

        }
        return height.values.sum()
    }
}

val o = Orbits(lines)

var path1 = o.pathToCOM("YOU")
var path2 = o.pathToCOM("SAN")

println(path1)
println(path2)

var n = (0 until min(path1.size, path2.size)).count { path1[it] == path2[it] }
// path to com
println("${path1.size + path2.size - 2*n}")





