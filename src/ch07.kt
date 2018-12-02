import org.omg.CORBA.portable.Delegate
import java.beans.PropertyChangeListener
import java.beans.PropertyChangeSupport
import java.lang.reflect.Type
import java.math.BigDecimal
import java.security.cert.PolicyNode
import java.time.LocalDate
import kotlin.properties.Delegates
import kotlin.reflect.KProperty

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
    return p.x in upperLeft.x until lowerRight.x &&     //not contains lowerRight.x     <
            p.y in upperLeft.y until lowerRight.y       //not contains lowerRight.y     <
}

operator fun ClosedRange<LocalDate>.iterator(): Iterator<LocalDate> =
        object : Iterator<LocalDate> {
            var current = start
            override fun hasNext(): Boolean =
                    current <= endInclusive

            override fun next() = current.apply {
                current = plusDays(1)   //current date plus 1
            }

//            override fun next(): LocalDate {
//                return current.apply {
//                    current.plusDays(2)
//                }
//            }
        }

//手动为非数据类生成组件函数（解析声明）
class Point2(val x: Int, val y: Int) {
    operator fun component1() = x
    operator fun component2() = y
}

data class NameComponents(val name: String,
                          val extension: String)

fun splitFileName(fullName: String): NameComponents {
    val result = fullName.split('.', limit = 2)
    return NameComponents(result[0], result[1])
}

fun splitFileName1(fullName: String): NameComponents {
    val (name, extension) = fullName.split('.', limit = 2)
    return NameComponents(name, extension)
}

fun printEntries(map: Map<String, String>) {
//    kt给map增加了一个扩展的iterator函数
//    Map.Entry上的扩展函数component1和component2，分别返回它的键和值
    for ((key, value) in map) {
        p("$key -> $value")
    }

    //前面的循环被转换成了下面的代码。
//    for (entry in map.entries) {
//        val key = entry.component1()
//        val value = entry.component2()
//    }
}

//编译器创建一个隐藏的辅助属性，并使用委托对象的实列进行初始化，初始属性p会委托给该实列。
//class Foo{
//    private val delegate = Delegate()
//    val p:Type            //p的访问都会调用对应的delegate的getValue和setValue方法
//        set(value:Type) = delegate.setValue(...,value)
//        get() = delegate.getValue(...)
//}

//class Delegate{
//    operator fun getValue(){...}                      //getter
//    operator fun setValue(...,value:Type){...}        //setter
//}

//委托属性的基本语法
//class Foo{
//    val p:Type by Delegate()      //关键字"by"把属性关联上委托对象
//}

class Email {/*...*/ }

fun loadEmails(person: Person11): List<Email> {
    p("Load emails for ${person.name}")
    return listOf(/*...*/)
}

fun loadEmails(person: Person12): List<Email> {
    p("Load emails for ${person.name}")
    return listOf(/*...*/)
}

//线程不安全
class Person11(val name: String) {
    private var _emails: List<Email>? = null         //_emails属性用来保存数据，关联委托
    val emails: List<Email>
        get() {
            if (_emails == null) {
                _emails = loadEmails(this)
            }
            return _emails!!        //如果已经加载，直接返回
        }
}

//默认情况下，lazy是线程安全的
class Person12(val name: String) {
    val emails by lazy { loadEmails(this) }         //等同于11中那么复杂的初始化方法
}

open class PropertyChangeAware {
    protected val changeSupport = PropertyChangeSupport(this)

    fun addPropertyChangeListener(listener: PropertyChangeListener) {
        changeSupport.addPropertyChangeListener(listener)
    }

    fun removePropertyChangeListener(listener: PropertyChangeListener) {
        changeSupport.removePropertyChangeListener(listener)
    }
}

//监听器监听属性变化并通知
class Person13(
        val name: String, age: Int, salary: Int
) : PropertyChangeAware() {
    var age: Int = age
        set(newValue) {
            val oldValue = field
            field = newValue
            changeSupport.firePropertyChange("age", oldValue, newValue)
        }

    var salary: Int = salary
        set(newValue) {
            val oldValue = field                    //field标识符允许你访问属性背后的支持字段
            field = newValue
            changeSupport.firePropertyChange(       //当属性变化时，通知监听器
                    "salary", oldValue, newValue)
        }
}

//提取13中的setter代码，上面有很多重复的
class ObservableProperty(
        val propName: String, var propValue: Int,
        val changeSupport: PropertyChangeSupport
) {
    fun getValue(): Int = propValue
    fun setValue(newValue: Int) {
        val oldValue = propValue
        propValue = newValue
        changeSupport.firePropertyChange(propName, oldValue, newValue)
    }
}

class Person14(
        val name: String, age: Int, salary: Int
) : PropertyChangeAware() {
    val _age = ObservableProperty("age", age, changeSupport)
    var age: Int
        get() = _age.getValue()
        set(value) {
            _age.setValue(value)
        }

    val _salary = ObservableProperty("salary", salary, changeSupport)
    var salary: Int
        get() = _salary.getValue()
        set(value) {
            _salary.setValue(value)
        }
}

class ObservableProperty1(
        var propValue: Int, val changeSupport: PropertyChangeSupport
) {
    operator fun getValue(p: Person15, prop: KProperty<*>): Int = propValue
    operator fun setValue(p: Person15, prop: KProperty<*>, newValue: Int) {
        val oldValue = propValue
        propValue = newValue
        changeSupport.firePropertyChange(prop.name, oldValue, newValue)
    }
}

class Person15(
        val name: String, age: Int, salary: Int
) : PropertyChangeAware() {

    var age: Int by ObservableProperty1(age, changeSupport)
    var salary: Int by ObservableProperty1(salary, changeSupport)
}

class Person16(
        val name: String, age: Int, salary: Int
) : PropertyChangeAware() {

    private val observer = { prop: KProperty<*>, oldValue: Int, newValue: Int ->
        changeSupport.firePropertyChange(prop.name, oldValue, newValue)
    }

    var age: Int by Delegates.observable(age, observer)     //使用Delegates.observable来实现属性修改的通知
    var salary: Int by Delegates.observable(salary, observer)
}

class Person17 {
    private val _attributes = hashMapOf<String, String>()
    fun setAttribute(attrName: String, value: String) {
        _attributes[attrName] = value
    }

//    val name: String
//        get() = _attributes["name"]!!

    val name: String by _attributes      //把map作为委托属性
}

fun main(args: Array<String>) {
    val p = Person17()
    val data = mapOf<String, String>("name" to "Dmitry", "company" to "JetBrains")
    for ((attrName, value) in data) {
        p.setAttribute(attrName, value)
    }
    p(p.name)

//    val p = Person16("Sun", 23, 8000)
//    p.addPropertyChangeListener(        //关联监听器，用于监听属性修改
//            PropertyChangeListener { event ->
//                p("Property ${event.propertyName} changed " +
//                        "from ${event.oldValue} to ${event.newValue}")
//            }
//    )
//    p.age = 18
//    p.salary = 15000

//    val p = Person12("Bob")
//    p.emails
//    p.emails

//    val p = Person11("Alice")
//    p.emails        //此方法只执行一次，因此下面的方法不会执行...第一次访问会加载邮件
//    p.emails

//    val foo = Foo()
//    val oldValue = foo.p        //通过调用delegate.getValue(...)来实现属性的更改
//    foo.p = newValue            //通过调用delegate.setValue(...,newValue)来实现属性的修改

//    val map = mapOf("Oracle" to "Java", "JetBrains" to "Kotlin")
//    printEntries(map)

//    val (name, ext) = splitFileName("example.kt")
//    p(name)
//    p(ext)

    //解析声明
//    val p = Point(10, 20)
//    val (x, y) = p      //可以转换成下面这种
//    val a = p.component1()
//    val b = p.component2()
//    p(x)
//    p(y)

//    val newYear = LocalDate.ofYearDay(2017, 1)
//    val daysOff = newYear.minusDays(1)..newYear
//    for(dayOff in daysOff){
//        println(dayOff)
//    }

//    val a = "abc"
//    for(c in a)         //operator fun CharSequence.iterator():CharIterator
//        print(c)

//    val n = 9
//    p(0..n + 1)
//    p(0..(n + 1))     //rangeTo运算符的优先级低于算术运算符，但是最好把参数括起来以免混淆
//    (0..n).forEach { print(it) }        //0..n.forEach()不会被编译，因为必须把区间表达式括起来才能调用它的方法

//    val now = LocalDate.now()
//    val vacation = now..now.plusDays(10)    //create a close vacation from today to after 10 days,range to
//    p(now.plusWeeks(1) in vacation)     //check a date is belong to the vacation or not

//    val rect = Rectangle1(Point(10, 20), Point(50, 50))
//    p(Point(20, 30) in rect)     //right object will invoke the contains function,and left object is a parameter
//    p(Point(5, 5) in rect)

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
