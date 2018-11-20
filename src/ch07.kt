import java.math.BigDecimal
import java.security.cert.PolicyNode

data class Point(val x: Int, val y: Int) {
    //override +
//    operator fun plus(other: Point): Point {
//        return Point(x + other.x,y + other.y)
//
//    }
}

//same as above
operator fun Point.plus(other: Point): Point {
    return Point(x + other.x, y + other.y)
}

//* times;/ div;% mod;+ plus;- minus
operator fun Point.times(scale: Double): Point {
    return Point((x * scale).toInt(), (y * scale).toInt())
}

//运算符函数的返回类型也可以不同于任意运算数类型
operator fun Char.times(count: Int): String {
    return toString().repeat(count)
}

//override +=
operator fun <T> MutableCollection<T>.plusAssign(element: T) {      //(集合)这个可写可不写，kotlin默认实现的
    this.add(element)
}

//位运算，中缀调用
//sh1--带符号左移，shr--带符号右移，ushr--无符号右移，and--按位与，or--按位或，xor--按位异或，inv--按位取反
fun bitMove() {
    p(0x0F and 0xF0)        //0
    p(0x0F or 0xF0)         //255
    p(0x1 shl 4)            //signed move left
}

//重载一元运算符   (-a)
//+a unaryPlus;-a unaryMinus;!a not;++a,a++ inc;--a,a-- dec
operator fun Point.unaryMinus(): Point {            //一元运算符无参数
    return Point(-x, -y)     //坐标取反，然后返回
}

operator fun BigDecimal.inc() = this + BigDecimal.ONE

//因为数据类默认实现了equals方法,所以此处用普通类来重载比较运算符中的等号运算符equals
//equals方法不能实现为扩展函数
class Point1(val x: Int, val y: Int) {
    override fun equals(other: Any?): Boolean {
        if (other === this) return true
        if (other !is Point1) return false
        return other.x == x && other.y == y
    }
}

class People(val firstName: String, val lastName: String) : Comparable<People> {
    override fun compareTo(other: People): Int {
        return compareValuesBy(this, other, People::lastName, People::firstName)
    }
}

//as same as : val value = map[key],it's a convention
//x[a,b] -> x.get(a,b)
operator fun Point.get(index: Int): Int {
    return when (index) {
        0 -> x
        1 -> y
        else ->
            throw IndexOutOfBoundsException("Invalid coordinate $index")
    }
}

data class MutablePoint(var x: Int, var y: Int)

//like map[key] = value
//x[a,b] = c -> x.set(a,b,c)
operator fun MutablePoint.set(index: Int, value: Int) {
    when (index) {
        0 -> x = value
        1 -> y = value
        else ->
            throw IndexOutOfBoundsException("Invalid coordinate $index")
    }
}

data class Rectangle1(val upperLeft: Point, val lowerRight: Point)

operator fun Rectangle1.contains(p: Point): Boolean {
    return p.x in upperLeft.x until lowerRight.x &&     //not contains lowerRight.x
            p.y in upperLeft.y until lowerRight.y       //not contains lowerRight.y
}

fun main(args: Array<String>) {
    val rect = Rectangle1(Point(10, 20), Point(50, 50))
    p(Point(20, 30) in rect)     //right object will invoke contains function,left object is a parameter
    p(Point(5, 5) in rect)

//    val p = MutablePoint(10,20)
//    p[0] = 5
//    p[1] = 15
//    p(p)

//    val p = Point(10, 20)
//    p(p[1])

//    val p1 = People("Alice","Smith")
//    val p2 = People("Bob","Johnson")
//    p(p1<p2)

//    p(Point1(10,20) == Point1(10,20))
//    p(Point1(10,20) != Point1(5,5))
//    p(null == Point1(1,2))

//    var bd = BigDecimal.ZERO
//    p(bd++)
//    p(++bd)

//    val p = Point(10, 20)
//    p(-p)

//    val list = arrayListOf(1, 2)
//    list += 3                               //集合+=的运算数可以使用单个元素
//    val newList = list + listOf(4, 5)       //也可以是元素类型一致的其他集合
//    p(list)
//    p(newList)

//    val numbers = ArrayList<Int>()
//    numbers += 42
//    p(numbers)

//    bitMove()

//    var point = Point(1,2)
//    point += Point(3,4)
//    p(point)

//    p('a' * 3)        //invoke Char.times

//    val p1 = Point(10,20)
//    val p2 = Point(30,40)
//    p(p1.plus(p2))
//    println(p1+p2)
//    p(p1*1.5)
//    p(1.5*p1)         //Kotlin不会自动支持交换性，需定义Double.times(p:Point):Point
}
