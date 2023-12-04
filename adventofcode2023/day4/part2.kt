package day2
import java.io.File
import java.io.BufferedReader

fun main(args: Array<String>) {
    val reader = File(args[0]).bufferedReader()
    val lines = reader.readLines()
    val cards = lines.map { parseCard(it) }.toTypedArray()
    //println(cards)
    // initial scores
    for (i in 0..<cards.size) {
        cards[i].run {
            scoreCard()
            for (j in 1..score) {
                cards[i + j].copies += copies
            }
        }
    }
    //cards.forEach { println(it) }
    // now all scores
    val total = cards.map { it.copies }.sum()
    println(total)
}

class Card(
    val winners: MutableSet<Int> = mutableSetOf<Int>(),
    val numbers: MutableSet<Int> = mutableSetOf<Int>(),
    var copies: Int = 1,
    var score: Int = 0,
) {
    fun scoreCard() {
        val match = this.numbers.intersect(this.winners)
        // println("match=$match")
        score = match.size
    }
    override fun toString() = "${winners} | ${numbers} x ${copies}"
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