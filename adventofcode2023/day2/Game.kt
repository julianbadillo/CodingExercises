package day2

// constructor-initializer
public class Game(
    val id: Int,
    var r: Int = 0,
    var g: Int = 0,
    var b: Int = 0,
) {
    constructor(id: Int, line: String) : this(id) {
        //println("="+line)
        line.split(", ").forEach{
            val m = it.split(" ")
            //println("=="+m)
            // switch equivalent
            when (m[1]) {
                "red" -> this.r = m[0].toInt()
                "green" -> this.g = m[0].toInt()
                "blue" -> this.b = m[0].toInt()
            }
        }
    }
    override fun toString(): String = "Game(r=$r, g=$g, b=$b)"
}
