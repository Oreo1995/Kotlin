import com.sun.deploy.util.SyncAccess
import com.sun.org.apache.xpath.internal.operations.Bool
import java.io.BufferedReader
import java.io.FileReader
import java.util.concurrent.locks.Lock
import kotlin.text.StringBuilder

//把lambda表达式保存在局部变量中，编译器推到出这两个变量具有函数类型
//x,y被省略的原因：因为他们的类型已经在函数的变量声明部分指定了，
// 不需要再lambda本身的定义当中再重复声明
val sum = { x: Int, y: Int -> x + y }
val action = { println(42) }

//上面变量的显示声明如下
//参数类型    返回类型
val sum1: (Int, Int) -> Int = { x, y -> x + y }    //有两个Int型参数和Int型返回值的函数
val action1: () -> Unit = { println(42) }       //没有参数和返回值的函数

//var canReturnNull: (Int,Int) -> Int? = {null}     //函数类型的返回值也可以标记为可空类型
var funOrNull: ((Int, Int) -> Int)? = null          //函数类型可空

//可以为函数类型声明中的参数指定名字
fun performRequest(
        url: String,
        callBack: (code: Int, content: String) -> Unit  //函数类型的参数现在有了名字
) {
    /*...*/
}

fun twoAndThree(operation: (Int, Int) -> Int) {     //定义一个函数类型的参数
    val result = operation(2, 3)                     //调用函数类型的参数
    p("The result is $result")
}

fun String.filter(predicate: (Char) -> (Boolean)): String {
    val sb = StringBuilder()
    for (index in 0 until length) {
        val element = get(index)
        if (predicate(element))      //调用作为参数传递给"predicate"的函数
            sb.append(element)
    }
    return sb.toString()
}

fun processTheAnswer(f: (Int) -> Int) {
    println(f(42))
}

//默认的lambda参数
fun <T> Collection<T>.joinToString(
        separator: String = "， ",
        prefix: String = "",
        postfix: String = "",
        transform: (T) -> String = { it.toString() }
): String {
    val result = StringBuilder(prefix)
    for ((index, element) in this.withIndex()) {
        if (index > 0) result.append(separator)
        result.append(transform(element))
    }
    result.append(postfix)
    return result.toString()
}

//使用函数类型的可空参数
fun <T> Collection<T>.joinToString1(
        separator: String = "， ",
        prefix: String = "",
        postfix: String = "",
        transform: ((T) -> String)? = null          //声明一个函数类型的可空参数
): String {
    val result = StringBuilder(prefix)
    for ((index, element) in this.withIndex()) {
        if (index > 0) result.append(separator)
        val str = transform?.invoke(element)        //使用安全调用语法的调用函数
                ?: element.toString()               //使用Elvis运算符处理回调没有被指定的情况
        result.append(str)
    }
    result.append(postfix)
    return result.toString()
}

enum class Delivery { STANDARD, EXPEDITED }

class Order(val itemCount: Int)

//定义一个返回函数类型的函数
fun getShippingCostCalculator(
        delivery: Delivery): (Order) -> Double {            //声明一个返回函数类型的函数
    if (delivery == Delivery.EXPEDITED) {
        return { order -> 6 + 2.1 * order.itemCount }       //返回lambda
    }
    return { order -> 1.2 * order.itemCount }               //返回lambda
}

data class Person18(
        val firstName: String,
        val lastName: String,
        val phoneNumber: String?
)

//显示以特定字符开头的联系人
class ContactListFilters {
    var prefix: String = ""
    var onlyWithPhoneNumber: Boolean = false

    fun getPredicate(): (Person18) -> Boolean {     //声明一个返回函数的函数
        val startsWithPrefix = { p: Person18 ->
            p.firstName.startsWith(prefix) || p.lastName.startsWith(prefix)
        }

        if (!onlyWithPhoneNumber) {
            return startsWithPrefix             //返回一个函数类型的变量
        }
        return {
            startsWithPrefix(it) && it.phoneNumber != null          //从这个函数返回一个lambda
        }
    }
}

data class SiteVisit(
        val path: String,
        val duration: Double,
        val os: OS
)

enum class OS { WINDOWS, LINUX, MAC, IOS, ANDROID }

val log = listOf(
        SiteVisit("/", 34.0, OS.WINDOWS),
        SiteVisit("/", 22.0, OS.MAC),
        SiteVisit("/login", 12.0, OS.WINDOWS),
        SiteVisit("/signup", 8.0, OS.IOS),
        SiteVisit("/", 16.3, OS.ANDROID)
)

//使用硬编码的过滤器分析站点访问数据
val averageWindowsDuration = log
        .filter { it.os == OS.WINDOWS }
        .map(SiteVisit::duration)
        .average()

//用一个普通方法去除重复代码
fun List<SiteVisit>.averageDurationFor(os: OS) =
        filter { it.os == os }.map(SiteVisit::duration).average()

//用一个复杂的硬编码函数分析站点访问数据
val averageMobileDuration = log
        .filter { it.os in setOf(OS.IOS, OS.ANDROID) }
        .map(SiteVisit::duration)
        .average()

//用一个高阶函数去除重复代码(条件查询)
fun List<SiteVisit>.averageDurationFor(predicate: (SiteVisit) -> Boolean) =
        filter(predicate).map(SiteVisit::duration).average()

inline fun <T> synchronized(lock: Lock, action: () -> T): T {
    lock.lock()
    try {
        return action()
    } finally {
        lock.unlock()
    }
}

fun foo(l: Lock) {
    p("Before sync")
    synchronized(l) {
        p("Action")
    }
    p("After sync")
}

//Lock.withLock等同于java中的资源管理synchronized  //(try/finally)

//使用use函数管理资源
//use函数等同于java中的try-with-resource
//use函数是一个扩展函数，别用来操作可关闭的资源，无论是否抛出异常，资源都会被关闭，而且是内联的
fun readFirstLineFromFile(path: String): String {
    BufferedReader(FileReader(path)).use { br ->
        return br.readLine()        //使用了非局部return从readFirstLineFromFile函数中返回了一个值
    }
}

data class Person19(val name: String, val age: Int)

val people = listOf(Person19("Alice", 29), Person19("Bob", 31))

fun lookForAlice(people: List<Person19>) {
    for (person in people) {
        if (person.name == "Alice") {
            p("Found")
            return
        }
    }
    p("Alice is not found")
}

//lambda中的返回语句，从一个封闭的函数返回
fun lookForALice1(people: List<Person19>) {
    people.forEach {
        if (it.name == "Alice") {
            p("Found")
            return      //和上面的结果一样  //返回到lookForAlice1
        }
    }
    p("Alice is not found")
}

//从lambda返回：使用标签返回，一个lambda表达式的标签不能多于一个
fun lookForAlice2(people: List<Person19>) {
    people.forEach lable@{
        //lambda标签
        if (it.name == "Alice") return@lable        //返回表达式标签
    }
    p("Alice might be somewhere")               //这一行总是被打印出来
}


//用函数名做return标签
fun lookForAlice4(people: List<Person19>) {
    people.forEach {
        if (it.name == "Alice") return@forEach       //return@forEach从lambda表达式中返回
    }
    p("Alice might be somewhere")
}

//匿名函数：默认使用局部返回
//局部返回的语法相当冗长，如果一个lambda包含多个返回语句会变得更加笨重。解决方案是，
//可以使用另一种可选的语法来传递代码块：匿名函数
fun lookForAlice5(people: List<Person19>) {
    people.forEach(fun(person) {        //使用匿名函数取代lambda表达式
        if (person.name == "Alice") return      //return语句指向最近的函数：一个匿名函数        //返回到fun(person)
        p("${person.name} is not Alice")
    })
}

fun main(args: Array<String>) {
    //在filter中使用匿名函数
    people.filter(fun(person): Boolean {
        return person.age < 30
    })

    //使用表达式体匿名函数
    people.filter(fun(person) = person.age < 30)

//    lookForAlice5(people)

//    p(StringBuilder().apply sb@{        //这个lambda的隐式接收者可以通过this@sb访问
//        listOf(1,2,3).apply {           //this只想作用域内最近的隐式接收者
//            this@sb.append(this.toString())     //所用隐式接收者都可以被访问，外层的接收者通过显示的标签访问
//        }
//    })

//    lookForAlice2(people)

//    val l = Lock()
//    foo(l)

//    p(log.averageDurationFor {
//        it.os in setOf(OS.ANDROID, OS.IOS)
//    })
//    p(log.averageDurationFor {
//        it.os == OS.IOS && it.path == "/signup"
//    })

//    p(averageMobileDuration)
//    p(log.averageDurationFor(OS.WINDOWS))
//    p(log.averageDurationFor(OS.MAC))
//    p(averageWindowsDuration)

//    val contacts = listOf(Person18("Dmitry", "Jemerov", "123-4567"),
//            Person18("Svetlana", "Isakova", null))
//    val contactListFilters = ContactListFilters()
//    with(contactListFilters) {
//        prefix = "Dm"
//        onlyWithPhoneNumber = true
//    }
//    p(contacts.filter(
//            contactListFilters.getPredicate()       //将getPredicate返回的函数作为参数传递给filter函数
//    ))

//    val calculator =                //将返回的函数保存在变量中
//            getShippingCostCalculator(Delivery.EXPEDITED)
//    p(calculator(Order(3)))     //调用返回的函数

//    val letters = listOf("Alpha", "Beta")
//    p(letters.joinToString())               //使用默认的转换函数
//    p(letters.joinToString { it.toLowerCase() })                //传递一个lambda作为参数
//    p(letters.joinToString(separator = "! ", postfix = "! ",     //使用命名参数语法传递几个参数，包括一个lambda
//            transform = { it.toUpperCase() }))

//    twoAndThree(sum)
//    twoAndThree { a, b -> a + b }
//    twoAndThree { a, b -> a * b }

//    p("ab1c".filter { it in 'a'..'z' })     //传递一个lambda作为"predicate"参数

//    val url = "http://kotl.in"
//    performRequest(url) { code, content -> /*...*/ }    //可以使用API中提供的参数名字作为lambda参数的名字。。。
//    performRequest(url) { code, page -> /*...*/ }       //。。。或者你可以改变参数的名字
}

