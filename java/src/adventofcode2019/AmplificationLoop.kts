import adventofcode.IntCodeComputerSync
import java.io.File

fun permutations(n: Int): List<List<Int>>{

    var list = (0 until n).toList()
    return permutationsR(list)
}

fun permutationsR(list: List<Int>): List<List<Int>>{
    // base case
    if(list.size == 1)
        return listOf(listOf(list[0]))
    else
    {
        var result = mutableListOf<List<Int>>()
        for(first in list){
            for(perm in permutationsR(list.filter { it != first }))
                result.add(listOf(first) + perm)
        }
        return result
    }
}

//val program = intArrayOf(3,52,1001,52,-5,52,3,53,1,52,56,54,1007,54,5,55,1005,55,26,1001,54,
//        -5,54,1105,1,12,1,53,54,53,1008,54,0,55,1001,55,1,55,2,53,55,53,4,
//        53,1001,56,-1,56,1005,56,6,99,0,0,0,0,10)
val program = File("../data/amp_circuit.txt").readText().split(",").map{it.toInt()}.toIntArray()

var maxOutput = 0

for(perm in permutations(5)) {
    // computer array for permutation
    val computers = arrayOf(IntCodeComputerSync(program),
                                                    IntCodeComputerSync(program),
                                                    IntCodeComputerSync(program),
                                                    IntCodeComputerSync(program),
                                                    IntCodeComputerSync(program))
    // add phases as inputs
    for(i in 0 until perm.size)
        computers[i].addInput(perm[i] + 5)
    // initial input
    computers[0].addInput(0)
    while(true){
        for(i in 0 until perm.size){
            while(computers[i].executeNextLine());
            // output to next computer
            if(i < perm.size - 1)
                computers[i+1].addInput(computers[i].getOutput())
            else
                computers[0].addInput(computers[i].getOutput())
        }
        if(computers.last().isFinished())
            break
    }

    // last output
    if(computers.last().getOutput() > maxOutput){
        maxOutput = computers.last().getOutput()
        println(maxOutput)

    }
}
