package adventofcode

/**
 * Computer that executes intcode
 */
class IntCodeComputer {
    fun executeCode(program: IntArray, input: MutableList<Int>, pos: Int = 0) : List<Int> {
        var p = pos
        var output = mutableListOf<Int>()
        while (true) {
            val op = program[p] % 100;
            var modebits = program[p] / 100;
            val m1 = (modebits % 10) == 1
            modebits /= 10
            val m2 = (modebits % 10) == 1
            modebits /= 10
            when(op){
            // sum
                1 -> {
                    val val1 = if(m1) program[p + 1] else program[program[p + 1]]
                    val val2 = if(m2)program[p + 2] else program[program[p + 2]]
                    program[program[p + 3]] = val1 + val2
                    p += 4
                }
            // mult
                2 -> {
                    val val1 = if(m1) program[p + 1] else program[program[p + 1]]
                    val val2 = if(m2)program[p + 2] else program[program[p + 2]]
                    program[program[p + 3]] = val1 * val2
                    p += 4
                }
            // input
                3 ->{
                    var val1 = input.removeAt(0)
                    program[program[p + 1]] = val1
                    p += 2
                }
            // output
                4 ->{
                    val val1 = if(m1) program[p + 1] else program[program[p + 1]]
                    output.add(val1)
                    p += 2
                }
            // jump if true
                5 -> {
                    val val1 = if(m1) program[p + 1] else program[program[p + 1]]
                    val val2 = if(m2)program[p + 2] else program[program[p + 2]]
                    p = if(val1 > 0) val2 else p + 3
                }
            // jump if false
                6 -> {
                    val val1 = if(m1) program[p + 1] else program[program[p + 1]]
                    val val2 = if(m2)program[p + 2] else program[program[p + 2]]
                    p = if(val1 == 0) val2 else p + 3
                }
            // less than
                7 -> {
                    val val1 = if(m1) program[p + 1] else program[program[p + 1]]
                    val val2 = if(m2)program[p + 2] else program[program[p + 2]]
                    program[program[p + 3]] = if(val1 < val2) 1 else 0
                    p += 4
                }
            // equals
                8 -> {
                    val val1 = if(m1) program[p + 1] else program[program[p + 1]]
                    val val2 = if(m2)program[p + 2] else program[program[p + 2]]
                    program[program[p + 3]] = if(val1 == val2) 1 else 0
                    p += 4
                }
                99 ->{
                    return output
                }
            }
        }
    }
}