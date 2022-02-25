import org.junit.Assert
import org.junit.Test

import org.junit.Assert.*
import kotlin.test.assertEquals

class CrackThePinKtTest {

    @Test
    fun simplePIN() {
        assertEquals("12345", crack("827ccb0eea8a706c4c34a16891f84e7b"))
    }
    @Test
    fun harderPIN() {
        assertEquals("00078", crack("86aa400b65433b608a9db30070ec60cd"))
    }

    @Test
    fun recursiveCount1() {
        var b = NumPad()
        var n = b.countFrom(1,1, 1)
        assertEquals(1, n)
        n = b.countFrom(1,1, 2)
        assertEquals(8, n)
        n = b.countFrom(0, 0, 2)
        assertEquals(5, n)
    }

    @Test
    fun test2Path(){
        assertEquals(5, countPatternsFrom("A", 2))
        assertEquals(7, countPatternsFrom("B", 2))
        assertEquals(5, countPatternsFrom("C", 2))
        assertEquals(7, countPatternsFrom("D", 2))
        assertEquals(8, countPatternsFrom("E", 2))
        assertEquals(7, countPatternsFrom("F", 2))
        assertEquals(5, countPatternsFrom("G", 2))
        assertEquals(7, countPatternsFrom("H", 2))
        assertEquals(5, countPatternsFrom("I", 2))
    }

    @Test
    fun test3Path(){
        assertEquals(31, countPatternsFrom("A", 3))
    }

    @Test
    fun `sample tests`() {
        assertEquals(0, countPatternsFrom("A", 10), "Should return 0 for path of length 10 with starting point A")
        assertEquals(0, countPatternsFrom("A", 0), "Should return 0 for path of length 0 with starting point A")
        assertEquals(0, countPatternsFrom("E", 14), "Should return 0 for path of length 14 with starting point E")
        assertEquals(1, countPatternsFrom("B", 1), "Should return 1 for path of length 1 with starting point B")
        assertEquals(5, countPatternsFrom("C", 2), "Should return 5 for path of length 2 with starting point C")
        assertEquals(8, countPatternsFrom("E", 2), "Should return 8 for path of length 2 with starting point E")
        assertEquals(256, countPatternsFrom("E", 4), "Should return 256 for path of length 4 with starting point E")
    }
}