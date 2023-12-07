import java.io.File
import java.io.BufferedReader

enum class HandType {
    HIGH_CARD,
    ONE_PAIR,
    TWO_PAIR,
    THREE_OF_A_KIND,
    FULL_HOUSE,
    FOUR_OF_A_KIND,
    FIVE_OF_A_KIND,
}
// a map that decides depending on card counts what kind of hand is it
val HAND_TYPE_MAP = mapOf<String, HandType>(
    "11111" to HandType.HIGH_CARD,
    "1112" to HandType.ONE_PAIR,
    "122" to HandType.TWO_PAIR,
    "113" to HandType.THREE_OF_A_KIND,
    "23" to HandType.FULL_HOUSE,
    "14" to HandType.FOUR_OF_A_KIND,
    "5" to HandType.FIVE_OF_A_KIND,
 )
 val CARD_VALUE = mapOf<Char, Int> (
    'A' to 14,
    'T' to 10,
    'J' to 1, // weaker
    'Q' to 12,
    'K' to 13,
 )

class Hand(text: String): Comparable<Hand> {
    val hand: String
    val bet: Int
    var type: HandType
    val counts: String
    val cards: List<Int>
    init {
        var parts = text.split(" ")
        hand = parts[0]
        bet = parts[1].toInt()
        cards = hand.map { CARD_VALUE[it] ?: (it.code - '0'.code)}
        var cardCount = mutableMapOf<Char, Int>()
        hand.forEach {cardCount[it] = 1 + (cardCount[it] ?: 0)}
        counts = cardCount.values.toList().sorted().joinToString("")
        type = HAND_TYPE_MAP[counts] ?: HandType.HIGH_CARD
        // get the best possible bet
        type = if ('J' in hand) when(type) {
            HandType.HIGH_CARD -> HandType.ONE_PAIR
            HandType.ONE_PAIR -> HandType.THREE_OF_A_KIND
            HandType.TWO_PAIR -> if (cardCount['J'] == 1) HandType.FULL_HOUSE else HandType.FOUR_OF_A_KIND
            HandType.THREE_OF_A_KIND -> HandType.FOUR_OF_A_KIND
            HandType.FULL_HOUSE -> HandType.FIVE_OF_A_KIND
            HandType.FOUR_OF_A_KIND -> HandType.FIVE_OF_A_KIND
            else -> type
        } else type
    }
    override fun toString() = "H('$hand', $type, $cards, $counts, \$$bet)"
    override operator fun compareTo(other: Hand): Int {
        // compare type
        if (this.type != other.type) return this.type.compareTo(other.type)
        // compare cards
        for ((card1, card2) in (this.cards zip other.cards)) {
            if (card1 != card2) return card1 - card2
        }
        return 0
    }
}

fun main(args: Array<String>) {
    val reader = File(args[0]).bufferedReader()
    val hands = reader.readLines().map { Hand(it) }
    val total = hands
        .sorted()
        .mapIndexed { 
            index, it -> 
            (index + 1) * it.bet
        }
        .sum()
        // .forEachIndexed {
        //     index: Int, it: Hand -> 
        //     println("$index $it")
        // }
    println(total)
}