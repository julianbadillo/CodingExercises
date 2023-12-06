import java.io.File
import java.io.BufferedReader

// h = time holding the button
// d = total distance traveled
// t = duration of the race
// v = boat speed = h
// d = h * (t - h)
// d = h * t - h^2
// h^2 - t * h + d = 0
// h = (t +- sqrt(t^2 - 4*d)) / 2
// hd = sqrt(t^2 - 4*d)

fun main(args: Array<String>) {
    val reader = File(args[0]).bufferedReader()
    // first line
    val t: Long = reader.readLine().split(":")[1].replace(" ", "").toLong()
    val d: Long = reader.readLine().split(":")[1].replace(" ", "").toLong()
    println("$t, $d, ${options(t, d)}")
    
}

fun getHDelta(t: Double, d: Double): Double {
    return Math.sqrt(t*t - 4*d)
}

fun options(t: Long, d: Long): Long {
    // both quadratic equation solutions
    val minh = ((t.toDouble()) - Math.sqrt(t.toDouble()*t.toDouble() - 4.0*d.toDouble())) / 2.0
    val maxh = ((t.toDouble()) + Math.sqrt(t.toDouble()*t.toDouble() - 4.0*d.toDouble())) / 2.0
    // round
    val minhR = Math.ceil(minh)
    val maxhR = Math.floor(maxh)
    // println("minh=$minh maxh=$maxh minhR=$minhR maxhR=$maxhR")
    // integer solutions above d
    var opt = maxhR.toLong() - minhR.toLong() + 1
    // discount if strict match
    if (minh == minhR) { opt-- }
    if (maxh == maxhR) { opt-- }
    return opt
}