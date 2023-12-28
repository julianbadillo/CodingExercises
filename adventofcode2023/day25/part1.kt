
import java.io.File
import java.io.BufferedReader

fun main(args: Array<String>) {
    val reader = File(args[0]).bufferedReader()

    val graph = mutableMapOf<String, MutableSet<String>>()
    reader.readLines().forEach {
        val parts = it.split(": ")
        val node = parts[0]
        val adjs = parts[1].split(" ")
        if (graph[node] == null) {
            graph[node] = mutableSetOf()
        }
        graph[node]?.addAll(adjs)
        for (node2 in adjs) {
            if (graph[node2] == null) {
                graph[node2] = mutableSetOf()
            }
            graph[node2]?.add(node)
        }
    }
    // val edges = graph.flatMap { (n1, ed) -> ed.map { n2 -> Edge(n1, n2)} }.toSet()
    // edges.forEach{ println(it) }
    // for ((k, v) in graph) {
    //     println("$k -> $v")
    // }
    partitionGraph(graph)

}

data class Edge(val n1: String, val n2: String) {
    override fun equals(other: Any?) = (other is Edge) && ((other.n1 == n1 && other.n2 == n2) || (other.n1 == n2 && other.n2 == n1))
    override fun hashCode() = n1.hashCode() + n2.hashCode()
    override fun toString() = "$n1 $n2"
}

fun partitionGraph(graph: MutableMap<String, MutableSet<String>>) {

    // we can eliminate at most 3 edges.
    val nodes = graph.keys.toList()
    val distance = calculateDistances(graph)
    // printDistances(distance, nodes)
    
    val cluster1 = mutableSetOf<String>()
    val cluster2 = mutableSetOf<String>()

    // pick 2 centroids based on the two nodes with most edges
    val sortedNodes = graph.map { (n, edges) -> edges.size to n }.sortedBy { -it.component1() }
    // println("Sorted nodes by edge size")
    // println(": $sortedNodes")
    var center1 = sortedNodes[0].component2()
    var center2 = sortedNodes[1].component2()
    println("Centers: $center1 $center2")
    // split according to lowest distance - initial partition
    nodes.forEach { 
        val d1 = distance[Edge(center1, it)] ?: -1
        val d2 = distance[Edge(center2, it)] ?: -1
        if ( d1 < d2 ) cluster1.add(it)
        else cluster2.add(it)
    }
    
    // Move nodes from one partition to the other until it converges
    var go = true
    while (go) {
        go = false
        for (n1 in nodes) {
            // try wigglint it from one partition to the other - where it has lower crossing edges
            val c1 = graph[n1]?.filter{ cluster1.contains(it) }?.count() ?: throw IllegalStateException("!!! $n1")
            val c2 = graph[n1]?.filter{ cluster2.contains(it) }?.count() ?: throw IllegalStateException("!!! $n1")
            // println("Cluster1: ${cluster1.sorted()}")
            // println("Cluster2: ${cluster2.sorted()}")
            // println("$n1 = $c1 $c2")
            if (c1 < c2 && cluster1.contains(n1)) {
                cluster2.add(n1)
                cluster1.remove(n1)
                go = true
            } else if (c2 < c1 && cluster2.contains(n1)) {
                cluster1.add(n1)
                cluster2.remove(n1)
                go = true
            }
            val score = scorePartition(graph, cluster1, cluster2)
            if (score <= 3) {
                go = false
                break
            }
        }
    }
    
    // println("Cluster1: ${cluster1.sorted()}")
    println("Cluster1: ${cluster1.size}")
    // println("Cluster2: ${cluster2.sorted()}")
    println("Cluster2: ${cluster2.size}")
    println("Total = ${cluster1.size * cluster2.size}")
                
    // brute force -> try all possible partitions
    // cluster1.addAll(nodes.drop(1))
    // cluster2.addAll(nodes.take(1))
    // while (true) {
    //     val score = scorePartition(graph, cluster1, cluster2)
    //     if (score == 3) {
    //         println("Cluster1: ${cluster1.sorted()}")
    //         println("Cluster2: ${cluster2.sorted()}")
    //         println("Total = ${cluster1.size * cluster2.size}")
    //         break
    //     }
    //     // next
    //     var i = 0
    //     while (i < nodes.size) {
    //         if (cluster1.contains(nodes[i])) {
    //             cluster1.remove(nodes[i])
    //             cluster2.add(nodes[i])
    //             break
    //         } else {
    //             cluster2.remove(nodes[i])
    //             cluster1.add(nodes[i])
    //             i++
    //         }
    //     }
    //     if (i == nodes.size) {
    //         println("$i reached!~")
    //         break
    //     }
    // }
}

/* Count how many edges go from one cluster to the other */
fun scorePartition(graph: Map<String, Set<String>>, cluster1: Set<String>, cluster2: Set<String>): Int {
    return cluster1.map { node1 -> 
        graph[node1]?.filter { node2 -> cluster2.contains(node2) }
            ?.count() 
            ?: 0
    }.sum()
}

/** Calulates distance between all Nodes to all other Nodes */
fun calculateDistances(graph: Map<String, Set<String>>): Map<Edge, Int> {
    val dist = mutableMapOf<Edge, Int>()
    // from all nodes to all nodes
    for (n1 in graph.keys) {
        bfsDistance(graph, dist, n1)
    }
    return dist
}

/** BFS distance. */
fun bfsDistance(graph: Map<String, Set<String>>, dist: MutableMap<Edge, Int>, start: String) {
    val queue = ArrayDeque<String>()
    val inQueue = mutableSetOf<String>()
    val visited = mutableSetOf<String>()
    queue.add(start)
    inQueue.add(start)
    dist[Edge(start, start)] = 0
    while (queue.size > 0) {
        val node = queue.removeFirst()
        inQueue.remove(node)
        val edge = Edge(start, node)
        val d = dist[edge] ?: throw IllegalStateException("!!! $edge")
        visited.add(node)
        for (node2 in graph[node] ?: listOf<String>()) {
            if (visited.contains(node2)) continue
            if (inQueue.contains(node2)) continue
            val edge2 = Edge(start, node2)
            if (dist[edge2]?.let { d + 1 > it } ?: false) continue
            dist[edge2] = d + 1
            queue.addLast(node2)
            inQueue.add(node2)
        }
    }
}

/* Gets a cluster centroid, i.e. a node with the lowest distance sum to all other nodes */
fun centroid(distance: Map<Edge, Int>, subGraph: Set<String>): String {
    var dists = subGraph.map { n1 ->
        n1 to
        subGraph.map {n2 -> distance[Edge(n1, n2)] ?: throw IllegalStateException("!!! $n1 $n2")}.sum()
    }
    // println(dists)
    return dists.minBy { it.component2() }.component1()
}

fun printDistances(distance: Map<Edge, Int>, nodes: List<String>) {
    nodes.forEach{ print("\t$it")}
    println()
    for (n1 in nodes) {
        print(n1)
        for (n2 in nodes) {
            print("\t${distance[Edge(n1, n2)]}")
        }
        println()
    }
}
