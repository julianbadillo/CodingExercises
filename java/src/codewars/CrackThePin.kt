//package algos
/*
Given is a md5 hash of a five digits long PIN. It is given as string.
Md5 is a function to hash your password: "password123" ===> "482c811da5d5b4bc6d497ffa98491e38"
Why is this useful? Hash functions like md5 can create a hash from string in a short time and it is impossible to find
out the password, if you only got the hash.
The only way is cracking it, means try every combination, hash it and compare it with the hash you want to crack.
 (There are also other ways of attacking md5 but that's another story) Every Website and OS is storing their passwords
 as hashes, so if a hacker gets access to the database, he can do nothing, as long the password is safe enough.

What is a hash: https://en.wikipedia.org/wiki/Hash_function#:~:text=A%20hash%20function%20is%20any,table%20called%20a%20hash%20table.
What is md5: https://en.wikipedia.org/wiki/MD5

Note: Many languages have build in tools to hash md5. If not, you can write your own md5 function or google for an example.
Here is another kata on generating md5 hashes: https://www.codewars.com/kata/password-hashes
Your task is to return the cracked PIN as string.

This is a little fun kata, to show you, how weak PINs are and how important a bruteforce protection is, if you create your own login.
 * */

fun crack(hash: String): String {
    // C0d3 g03s h3r3

    var md = java.security.MessageDigest.getInstance("MD5")

    for (i in 0..99999) {
        val pass = "%05d".format(i)
        val q = md.digest(pass.toByteArray())
        val hash1 = q.joinToString(separator = "") { b -> "%02x".format(b) }
        if (hash == hash1)
            return pass
    }
    return ""
}

/*
You might already be familiar with many smartphones that allow you to use a geometric pattern as a security measure.
To unlock the device, you need to connect a sequence of dots/points in a grid by swiping your finger without lifting it
as you trace the pattern through the screen.
A B C
D E F
G H I

Rules

In a pattern, the dots/points cannot be repeated: they can only be used once, at most.
In a pattern, any two subsequent dots/points can only be connected with direct straight lines in either of these ways:
    Horizontally: like (A -> B) in the example pattern image.
    Vertically: like (D -> G) in the example pattern image.
    Diagonally: like (I -> E), as well as (B -> I), in the example pattern image.
    Passing over a point between them that has already been 'used': like (G -> C) passing over E, in the example pattern
     image. This is the trickiest rule. Normally, you wouldn't be able to connect G to C, because E is between them,
     however when E has already been used as part the pattern you are tracing, you can connect G to C passing over E,
     because E is ignored, as it was already used once.
* */
fun countPatternsFrom(firstPoint: String, length: Int): Int {
    if (length > 9 || length == 0) return 0
    var i = (firstPoint[0] - 'A') / 3
    var j = (firstPoint[0] - 'A') % 3
    var b = NumPad()
    return b.countFrom(i, j, length)
}

class NumPad {
    val N: Int = 3
    var M: Array<Array<Boolean>> = arrayOf(arrayOf(false, false, false),
            arrayOf(false, false, false),
            arrayOf(false, false, false))

    fun countFrom(i: Int, j: Int, n: Int): Int {
        if (n == 1)
            return 1
        M[i][j] = true
        var count = 0
        for (i2 in 0 until N) {
            for (j2 in 0 until N) {
                // not used, not same
                if (!M[i2][j2] && !(i == i2 && j == j2)) {
                    // adjacent
                    if (Math.abs(i - i2) <= 1 && Math.abs(j - j2) <= 1)
                        count += countFrom(i2, j2, n - 1)
                    // L
                    else if(Math.abs(i - i2) + Math.abs(j - j2) == 3)
                        count += countFrom(i2, j2, n - 1)
                    // one used dot in the middle
                    else if (Math.abs(i - i2) % 2 == 0
                            && Math.abs(j - j2) % 2 == 0
                            && M[(i + i2) / 2][(j + j2) / 2])
                        count += countFrom(i2, j2, n - 1)

                }
            }
        }
        M[i][j] = false
        //println("$n : $count ")
        return count
    }
}


