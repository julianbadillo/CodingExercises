import java.io.File
import java.util.*

val image = File("../data/adventofcode2019/image.txt").readText().map{it - '0'}.toIntArray()

val width = 25
val height = 6
var result = 0
val layers =  image.size / width / height


var decoded = Array<IntArray>(height, {IntArray(width)})
// fill with '2'
for(i in 0 until height)
    for (j in 0 until width)
        decoded[i][j] = 2


for(l in 0 until layers) {
    for(i in 0 until height)
        for (j in 0 until width){
            var pixel = image[l*width*height+i*width+j]
            // if transparent, set color
            if(decoded[i][j] == 2)
                decoded[i][j] = pixel
            // else keep it
        }
}

for(i in 0 until height)
    println(decoded[i].joinToString(separator = ""){ if(it == 0) " " else "X"})