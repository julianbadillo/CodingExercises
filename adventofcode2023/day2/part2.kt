package day2
import java.io.File
import java.io.BufferedReader

fun main(args: Array<String>) {
    val reader: BufferedReader = File(args[0]).bufferedReader()
    val lines = reader.readLines()
    val total = lines.map{ parseGame(it) }
        .map{ minSetGame(it) }
        .map{ it.r * it.g * it.b }
        .sum()
    println(total)
}

fun parseGame(line: String): List<Game> {
    // filter - then map
    val gameData = line.split(": ")
    val id = gameData[0].split(" ")[1].toInt()
    // println(gameData)
    return gameData[1].split("; ").map{ Game(id, it) }
}

fun minSetGame(games: List<Game>): Game =
    Game(
        -1, 
        r=games.map {it.r}.max(),
        g=games.map {it.g}.max(),
        b=games.map {it.b}.max()
    )

