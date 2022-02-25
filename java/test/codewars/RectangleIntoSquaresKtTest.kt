import org.junit.Test

import org.junit.Assert.*

class RectangleIntoSquaresKtTest {

    @Test
    fun sqInRect() {
    }

    private fun dotest(lng:Int, wdth:Int, expect:List<Int>?) {
        println("lng: " + lng.toString() + " wdth: " + wdth.toString())
        val actual = sqInRect(lng, wdth)
        assertEquals(expect, actual)
    }

    @Test
    fun test1() {
        dotest(5, 3, listOf(3, 2, 1, 1))
        dotest(5, 5, null)
        dotest(20, 14, listOf(14, 6, 6, 2, 2, 2))

    }
}