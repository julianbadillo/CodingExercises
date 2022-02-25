
fun sqInRect(lng:Int, wdth:Int):List<Int>? {
    if(lng == wdth)
        return null
    var list = mutableListOf<Int>()
    var l = lng
    var w = wdth
    while(l != w){
        if(l > w){
            list.add(w)
            l -= w
        }
        else{
            list.add(l)
            w -= l
        }
    }
    list.add(l)
    return list
}
