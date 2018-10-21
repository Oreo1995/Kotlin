import java.lang.IllegalArgumentException
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
        if (index > 0)
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
        if (index > 0)
            result.append(separator)
        result.append(element)
    }

    result.append(postfix)
    return result.toString()
}

//public static final String UNIX_LINE_SEPARATOR = "\n"
//const val UNIX_LINE_SEPARATOR = "\n"

fun String.lastChar(): Char = this[this.length - 1]
fun String.lastChar1(): Char = this.get(this.length - 1)
fun String.lastChar2(): Char = get(length - 1)

fun <T> Collection<T>.joinToString2(
        separator: String = ",",
        prefix: String = "",
        postfix: String = ""
): String {
    val result = StringBuilder(prefix)
    for ((index, element) in this.withIndex()) {
        if (index > 0) result.append(separator)
        result.append(element)
    }
    result.append(postfix)
    return result.toString()
}

fun Collection<String>.join(
        separator: String = ",",
        prefix: String = "",
        postfix: String = ""
) = joinToString2(separator, prefix, postfix)

        open class View {
    open fun click() = println("View clicked")
}

class Button1 : View() {
    override fun click() = println("Button clicked")
}

fun View.showOff() = println("I'm a view!")
fun Button1.showOff() = println("I'm a button!")     //can not override the extension function

val String.lastCharElement: Char
    get() = get(length - 1)     //getter

var StringBuilder.lastChar: Char
    get() = get(length - 1)     //getter
    set(value: Char) {          //setter
        this.setCharAt(length - 1, value)
    }

fun compare() {
    val strings: List<String> = listOf("first", "second", "fourteenth")
    p(strings.last())

    val numbers: Collection<Int> = listOf(1, 14, 2)
    p(numbers.max())
}

//infix fun Any.to(other:Any) = Pair(this,other)

class User(val id: Int, val name: String, val address: String)

//局部函数
fun saveUser(user: User) {
    fun validate(value: String, fieldName: String) {
        if (value.isEmpty()) {
            throw IllegalArgumentException(
                    "Can't save user ${user.id}: " +
                            "empty $fieldName"
            )
        }
    }

    validate(user.name, "Name")
    validate(user.address, "Address")
}

//扩展函数+内部函数验证
fun User.validateBeforeSave() {
    fun validate(value: String, fieldName: String) {
        if (value.isEmpty()) {
            throw IllegalArgumentException(
                    "Can's save user $id: empty $fieldName"
            )
        }
    }
    validate(name, "Name")
    validate(address, "Address")
}

fun saveUser2(user: User) {
    user.validateBeforeSave()
}

fun main(args: Array<String>) {
//    saveUser2(User(3,"",""))
//    saveUser(User(2, "3", ""))

//    val (number,name) = 1 to "one"

//    val sb = StringBuilder("Kotlin?")
//    sb.lastChar = '!'
//    p(sb)

//    p("Kot".lastCharElement)

//    val view:View = Button()
//    view.showOff()      //I'm a view!

//    val view:View = Button()
//    view.click()

//    p(listOf("one", "two", "three").join(" "))
//    p("Kotlin".lastChar())

//    val list = listOf(1, 2, 3)
//    p(list.joinToString2(separator = ";",prefix = "(",postfix = ")"))
//    p(joinToString1(list, ","))
//    p(joinToString1(list, prefix = "*", postfix = "&"))

//    p(joinToString(list,/*seprator*/";",/*prefix*/"(",")"))
//    p(joinToString(list,separator = ",",prefix = "(",postfix = ")"))

//    getIndexOfCollection()
//    testCollectionInit()
}