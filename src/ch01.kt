import java.lang.IllegalArgumentException
import Color.*

fun p(T:Any?){
    println(T)
}

fun add(a: Int, b: Int) {
    val aslist = arrayListOf<String>("a", "b", "c")
    val c: String = "2"
    for (i in 0 until aslist.size)
        println(aslist.get(i))

    val d = "2"
}

fun o2o() {
    val a = "h" + 5
    print(a)
}

fun sum(a: Int, b: Int) = a + b

fun vars(vararg v: Int): Int {
    for (vc in v) {
        print(vc)
    }
    return v.size
}

fun varible() {
    val a: Int = 10
    val b = 1
    val c: Int
    c = 1

    var d = 1
    d = 2
    d = 3
}

fun strTemple() {
    var a = 1
    val s1 = "a is $a"
    println(s1)
    a = 2
    val s2 = "${s1.replace("is", "was")},but now is $a"
    println(s2)
}

fun checkNull() {
    var age: String? = "23"
    val ages = age!!.toInt()
    age = null
    val ages1 = age?.toInt()
    val ages2 = age?.toInt() ?: -1
    print("" + ages + "\n" + ages1 + "\n" + ages2)
}

//类型自动转换，有三种写法
fun getStringLength(obj: Any): Int? {
//    if (obj is String) {
//        return obj.length
//    }
//    return null

//    if (obj !is String) {
//        return null
//    }
//    return obj.length

    if (obj is String && obj.length > 0) {
        return obj.length
    }

    return null
}

//loop, as same as rang to
fun range() {
    for (i in 1..10) print(i)      //1 to 10     <=
    println()
    for (i in 4..1) println(i)      //nothing

    var j = 5
    if (j in 1..10) {       //j>=1&&j<=10
        println(j)      //j
    }

    for (i in 1..4 step 2) print(i)    //1,2
    println()
    for (i in 4 downTo 1 step 2) print(i)  //4,3
    println()
    for (i in 1 until 10) print(i)    //1 to 10
}

fun compartNum() {
    val a = 1000
    println(a === a)    //compare the address of object
    val boxedA: Int? = a    //box a
    val anotherBoxA: Int? = a    //box another a
    println(boxedA === anotherBoxA)
    println(boxedA == anotherBoxA)
}

fun testDataType() {
    val oneMillion = 1_000_000
    println(oneMillion)
    val creditCardNumber = 6210_8135_2000_7252_441L
    println(creditCardNumber)
    val socialSecurityNumber = 999_99_9999L
    println(socialSecurityNumber)
    val hexBytes = 0xFF_EC_DE_5E
    println(hexBytes)
    val bytes = 0b11010010_01101001_10010100_10010010
    println(bytes)
    val l = 1L + 3
    println(l)
}

fun decimalDigitValue(c: Char): Int {
    if (c !in '0'..'9') {
        throw IllegalArgumentException("Out of range")
    }
    return c.toInt() - '0'.toInt()
}

class Person(val name: String, var isMarried: Boolean)

fun testPerson() {
    val person = Person("Bob", false)
    println(person.name)
    println(person.isMarried)
}

class Rectangle(val height: Int, val width: Int) {
    val isSquare: Boolean
        get() {
            return height == width
        }
}

fun square() {
    val rectangle = Rectangle(4, 4)
    println(rectangle.isSquare)
}

fun square2() = println(Rectangle(4, 3).isSquare)

enum class Color(
        val r: Int, val g: Int, val b: Int
) {
    RED(255, 0, 0), ORANGE(255, 165, 0),
    YELLOW(255, 255, 0), GREEN(0, 255, 0),
    BLUE(0, 0, 255), INDIGO(75, 0, 130),
    VIOLET(238, 130, 238);

    fun rgb() = (r * 256 + g) * 256 + b
}

enum class ColorSimple {
    RED, GREEN, YELLOW, ORANGE, BLUE, INDIGO, VIOLET
}

fun getMnemonic(color: Color) = when (color) {
    Color.RED -> "Richard"
    Color.ORANGE -> "of"
    Color.YELLOW -> "York"
    Color.GREEN -> "Gave"
    Color.BLUE -> "Battle"
    Color.INDIGO -> "In"
    Color.VIOLET -> "Vain"
}

fun getWarmth(color: Color) = when (color) {
//    Color.RED, Color.ORANGE, Color.YELLOW -> "warm"
//    Color.GREEN -> "neutral"
//    Color.BLUE,Color.INDIGO,Color.VIOLET -> "cold"

    //import Color.*
    RED, ORANGE, YELLOW -> "warm"
    GREEN -> "neutral"
    BLUE, INDIGO, VIOLET -> "cold"
}

fun mix(c1: Color, c2: Color) = when (setOf(c1, c2)) {
    setOf(RED, YELLOW) -> ORANGE
    setOf(YELLOW, BLUE) -> GREEN
    setOf(BLUE, VIOLET) -> INDIGO
    else -> throw Exception("Dirty color")
}

fun mixOptimized(c1: Color, c2: Color) = when {
    (c1 == RED && c2 == YELLOW) || (c1 == YELLOW && c2 == RED) -> ORANGE
    (c1 == YELLOW && c2 == BLUE) || (c1 == BLUE && c2 == YELLOW) -> GREEN
    (c1 == BLUE && c2 == VIOLET) || (c1 == VIOLET && c2 == BLUE) -> INDIGO
    else -> throw Exception("Dirty color")
}

fun main(args: Array<String>) {
    p(mixOptimized(Color.YELLOW,BLUE))
//    println(mix(BLUE, BLUE))
//    println(getWarmth(Color.ORANGE))
//    println(getMnemonic(Color.GREEN))
//    println(ColorSimple.RED)
//    println(Color.VIOLET.rgb())
//    square2()
//    square()
//    testPerson()
//    println(decimalDigitValue('a'))
//    testDataType()
//    compartNum()
//    range()
//    print(getStringLength("1"))
//    println("Hello World")
//    add(3, 5)
//    print(sum(10, 5))
//    o2o()

//    print(vars(1,2,3,4,5))

//    val sumLambda:(Int,Int) -> Int = {x,y -> x+y}
//    print(sumLambda(3,5))
//    strTemple()
//    checkNull()
}