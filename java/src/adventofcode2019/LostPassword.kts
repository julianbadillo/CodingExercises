package adventofcode

import java.util.*

/**
 * --- Day 4: Secure Container ---

You arrive at the Venus fuel depot only to discover it's protected by a password. The Elves had written the password on a sticky note, but someone threw it out.

However, they do remember a few key facts about the password:

It is a six-digit number.
The value is within the range given in your puzzle input.
Two adjacent digits are the same (like 22 in 122345).
Going from left to right, the digits never decrease; they only ever increase or stay the same (like 111123 or 135679).

Other than the range rule, the following are true:

111111 meets these criteria (double 11, never decreases).
223450 does not meet these criteria (decreasing pair of digits 50).
123789 does not meet these criteria (no double).

How many different passwords within the range given in your puzzle input meet these criteria?

Your puzzle input is 136760-595730.
 */
fun splitDigits(n: Int): ShortArray{
    var x = n
    var l = mutableListOf<Short>()
    while (x > 0){
        l.add((x % 10).toShort())
        x /= 10
    }
    l.reverse()
    return l.toShortArray()
}

fun ShortArray.next(){
    var i = this.size - 1;
    this[i]++
    if(this[i] == 10.toShort())
    {
        // prev digit that is not nine
        var digit = this.last { it < 9 } + 1
        var j = this.indexOfLast { it < 9 }
        for(k in j..i)
            this[k] = digit.toShort()
    }
}

// valid if at least two consecutive equal
fun ShortArray.valid() = (0 until this.size - 1).any { this[it] == this[it + 1] }

fun ShortArray.validSecond(): Boolean = (0 until this.size - 1).any(){
    return this[it] == this[it + 1]
        && (0 == it || this[it - 1] != this[it])
        && (it + 1 == size - 1 || this[it + 1] != this[it + 2])
}

//Your puzzle input is 136760-595730.
var l = splitDigits(136777)
var end = splitDigits(589999)
var n = 0;
while(!l.contentEquals(end)) {
    if(l.validSecond()){
        println("${Arrays.toString(l)}")
        n++;
    }
    l.next();
}
if(l.validSecond()){
    println("${Arrays.toString(l)}")
    n++;
}
println(n)