import java.lang.Appendable
import java.lang.IllegalArgumentException

val authors = listOf("Dmitry", "Svetlana")      //类型推导
val readers: MutableList<String> = mutableListOf()
val readers1 = mutableListOf<String>()      //遇上等价

//类型参数声明
//fun <T> List<T>.slice(indices:IntRange):List<T>
//接收者使用的类型形参                //返回类型使用了类型形参

val <T> List<T>.penultimate: T
    get() = this[size - 2]

//泛型类的声明
//class StringList : List<String> {
//    override fun get(index :Int):String = ...
//}

//class ArrayList<T> : List<T> {
//    override fun get(index: Int): T = ...             //现在ArrayList的泛型类型形参就是List的类型实参
//}

//T类型参数，Number上界
//fun <T:Number> List<T>.sum():T{}

fun <T : Number> oneHalf(value: T): Double {            //指定Number为类型参数的上界
    return value.toDouble() / 2.0                      //调用Number类中的方法
}

//声明带类型参数约束的函数
fun <T : Comparable<T>> max(first: T, second: T): T {       //这个函数的实参必须是可比较的函数
    return if (first > second) first else second
    //first>second的简写形式会根据kotlin的运算符约定被编译成first.compareTo(Second)>0
}

//为一个类型参数指定多个约束
//声明作为类型实参的类型必须实现CharSequence和Appendable连个接口，这意味着该类型的值可以使用
// 访问数据(endsWith)和修改数据(append)两种操作
fun <T> ensureTrailingPeriod(seq: T)
        where T : CharSequence, T : Appendable {
    if (!seq.endsWith('.')) {           //调用为CharSequence接口定义的扩展函数
        seq.append('.')                      //调用Appendable接口的方法
    }
}

//让类型形参可空
class Processor<T> {
    fun process(value: T) {
        value?.hashCode()           //value是可空的，所以要用安全调用
    }
}

//让类型参数非空
class Processor2<T : Any> {          //指定非"空"上界
    fun process(value: T) {
        value.hashCode()            //类型T的值现在是非"空"的
    }
}

//对泛型类型做类型转换
fun printSum(c: Collection<*>) {
    val intList = c as? List<Int>       //这里会有肩高。Unchecked cast：Collection<*> to List<Int>
            ?: throw IllegalArgumentException("List is expected")
    p(intList.sum())
}

//对已知类型实参做类型转换
fun printSum1(c: Collection<Int>) {
    if (c is List<Int>) {           //这次检查是合法的
        p(c.sum())
    }
}

fun main(args: Array<String>) {
    p(printSum1(listOf(1,2,3)))
//    printSum(listOf(1, 2, 3))         //一切都符合预期
//    printSum(setOf(1,2,3))          //Set不是List，所以抛出了异常
//    printSum(listOf("a", "b", "c"))  //类型转换成功，但后面抛出了java.lang.String cannot be cast to java.lang.Number异常

//    val value: List<String> = listOf("a", "b", "c")
//    val value1 = listOf(1, "2")
//    //运行时类型擦除
////    if (value1 is List<String>) {/**/ }   //Cannot check for instance of erased type: List<String>
//
//    if (value1 is List<*>) {  /**/
//    } //检查一个值是否是列表，而不是set或者其他对象。可以使用特殊的星号投射语法来做这种检查

//    val nullableStringProcessor1 = Processor2<String?>()      //编译不通过，因为String?不是Any的子类型(它是Any?的子类型)

//    val nullableStringProcessor = Processor<String?>()      //可空类型String?被用来替代T
//    nullableStringProcessor.process(null)               //使用null作为value实参的代码可以编译

//    val helloWorld = StringBuffer("Hello World")
//    ensureTrailingPeriod(helloWorld)
//    p(helloWorld)

//    p(max("kotlin","java"))      //String类继承了Comparable<String>，使得String变成了max的有效类型实参
////    p(max("kotlin",42))         //不能编译，因为对不能比较的条目调用max。T的上界是泛型类型<Comparable<T>

//    p(listOf(1,2,3).sum())
//    p(listOf(1,2,3,4).penultimate)

//    val letters = ('a'..'z').toList()
//    p(letters.slice<Char>(0..2))        //显式地指定类型实参
//    p(letters.slice(10..13))        //编译器推导出这里的T是Char（与上作用一样）

//    val list = listOf(1,2,3,4)
//    p(list.slice(2..3))
}