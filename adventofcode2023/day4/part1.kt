package day2
import java.io.File
import java.io.BufferedReader

fun main(args: Array<String>) {
    val reader = File(args[0]).bufferedReader()
    val lines = reader.readLines()
    val total = lines.map { parseCard(it) }
                    .map{ it.scoreCard() }
                    .sum()
    println(total)
}

class Card(val winners: MutableSet<Int> = mutableSetOf<Int>(), val numbers: MutableSet<Int> = mutableSetOf<Int>()) {
    fun scoreCard(): Int {
        val match = this.numbers.intersect(this.winners)
        // println("exp=$exp")
        val n = match.size
        return if (n == 0) 0 else 1.shl(n - 1)
    }
    override fun toString() = "${winners} | ${numbers}\n"
}

fun parseCard(line: String): Card {
    val parts = line.split(": ")[1].split(" | ")
    val card = Card().apply {
        parts[0].split(" ")
                .filter { it != "" }
                .map { it.toInt() }
                .forEach { winners.add(it) }
        parts[1].split(" ")
                .filter { it != ""}
                .map { it.toInt() }
                .forEach { numbers.add(it) }
    }
    // println(card)
    return card
}