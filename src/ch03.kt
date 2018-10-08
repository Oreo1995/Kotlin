import java.lang.StringBuilder
import javax.swing.plaf.basic.BasicPopupMenuSeparatorUI

fun testCollectionInit() {
    val set = hashSetOf(1, 7, 53)
    val list = arrayListOf(1, 7, 53)
    val map = hashMapOf(1 to "one", 7 to "seven", 53 to "fifty-three")

    p(set.javaClass)
    p(list.javaClass)
    p(map.javaClass)

    p(set)
    p(list)
    p(map)
}

fun getIndexOfCollection() {
    val strings = listOf("first", "second", "fourteenth")
    println(strings.last())
    val numbers = listOf(1, 14, 2)
    println(numbers.max())
}

fun <T> joinToString(
        collection: Collection<T>,
        separator: String,
        prefix: String,
        postfix: String
    ): String {

    val result = StringBuilder(prefix)

    for ((index, element) in collection.withIndex()) {
        if(index > 0)
            result.append(separator)
        result.append(element)
    }

    result.append(postfix)
    return result.toString()
}

fun <T> joinToString1(
        collection: Collection<T>,
        separator: String = ",",
        prefix: String = "",
        postfix: String = ""
): String {

    val result = StringBuilder(prefix)

    for ((index, element) in collection.withIndex()) {
        if(index > 0)
            result.append(separator)
        result.append(element)
    }

    result.append(postfix)
    return result.toString()
}


fun main(args: Array<String>) {
    val list = listOf(1,2,3)
    p(joinToString1(list,","))
    p(joinToString1(list,prefix = "*",postfix = "&"))

//    p(joinToString(list,/*seprator*/";",/*prefix*/"(",")"))
//    p(joinToString(list,separator = ",",prefix = "(",postfix = ")"))

//    getIndexOfCollection()
//    testCollectionInit()
}