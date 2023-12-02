package day2
import java.io.File
import java.io.BufferedReader

fun main(args: Array<String>) {
    val reader: BufferedReader = File(args[0]).bufferedReader()
    val lines = reader.readLines()
    // map-reduce
    val testGame = Game(id=0, r=12, g=13, b=14)
    val total = lines.map { parseGame(it) }.
                        filter{ isGamePossible(it, testGame) }.
                        map{ it[0].id }.
                        sum()
    println(total)
}

fun parseGame(line: String) : List<Game> {
    // filter - then map
    val gameData = line.split(": ")
    val id = gameData[0].split(" ")[1].toInt()
    //println(gameData)
    return gameData[1].split("; ").map{ Game(id, it) }
}

fun isGamePossible(games: List<Game>, testGame: Game) : Boolean {
    // true if all games are possible
    // println("---")
    return games
            //.apply{ println(this) }
            .all { it.r <= testGame.r && it.g <= testGame.g && it.b <= testGame.b }
}

