import com.sun.org.apache.xpath.internal.operations.Bool
import java.lang.Appendable
import java.lang.IllegalArgumentException
import java.util.*
import kotlin.reflect.KClass

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

//fun <T> isA1(value:Any) = value is T        //Error: Cannot check for instance of erased type: T

//声明带实化类型参数的函数
inline fun <reified T> isA(value: Any) = value is T     //现在代码可以编译了

//filterIsInstance的简化实现
inline fun <reified T>
        Iterable<*>.filterIsInstance(): List<T> {     //reified声明了类型参数不会在运行时被擦除
    val destination = mutableListOf<T>()

    for (element in this) {
        if (element is T) {         //可以检查元素是不是指定为类型参数的类的实列
            destination.add(element)
        }
    }
    return destination

    //上面的代码生产的代码和下面这段是等价的，（对调用filterIsInstance<String>来说）
//    for (element in this) {
//        if (element is String) {        //引用具体类
//            destination.add(element)
//        }
//    }
}

//用带实化类型参数的函数重写loadService
inline fun <reified T> loadService() =      //类型参数标记成了reified
        ServiceLoader.load(T::class.java)       //把T::class当成类型形参访问

//简化Android上的startActivity函数
//inline fun <reified T:Activity>
//        Context.startActivity(){
//    val intent = Intent(this,T::class.java)
//    startActivity(intent)
//}
//
//startActivity<DetailActivity>()       //调用方法显示Activity

//变型：泛型和子类型化
fun printContents(list: List<Any>) {
    p(list.joinToString())
}

//协变：保留子类型化关系(在类型参数的名称前加上out关键字就可以声明协变)
interface Producer<out T> {
    fun produce(): T
}

//定义一个不变型的类似集合的类
open class Animal {
    fun feed() {}
}

class Herd<T : Animal> {             //类型参数没有声明成协变的
    val size: Int get() = 1
    operator fun get(i: Int): T {
        return Animal() as T
    }
}

fun fendAll(animals: Herd<Animal>) {
    for (i in 0 until animals.size) {
        animals[i].feed()
    }
}

//使用不变型的类似集合的类
class Cat : Animal() {                         //Cat是一个Animal
    fun cleanLitter() {}
}

//使用一个协变的类似集合的类
class Herd2<out T : Animal> {             //类型参数T现在是协变的
    val size: Int get() = 1
    operator fun get(i: Int): T {
        return Animal() as T
    }
}

fun fendAll2(animals: Herd2<Animal>) {
    for (i in 0 until animals.size) {
        animals[i].feed()
    }
}

fun takeCareOfCats(cats: Herd<Cat>) {
    for (i in 0 until cats.size) {
        cats[i].cleanLitter()
//        fendAll(cats)             //错误：推导的类型是Hear<Cat>,但期望的却是Herd<Animal>
    }
}

fun takeCareOfCats2(cats: Herd2<Cat>) {
    for (i in 0 until cats.size) {
        cats[i].cleanLitter()
        fendAll2(cats)              //不需要类型转换
    }
}

//逆变：反转子类型化关系
fun enumerateCats(f: (Cat) -> Number) {}

fun Animal.getIndex(): Int = 5

//使用点变型：在类型出现的地方指定类型
//带不变型类型参数的数据拷贝函数
fun <T> copyData0(source: MutableList<T>,
                  destination: MutableList<T>) {
    for (item in source) {
        destination.add(item)
    }
}

//带不变型类型参数的数据拷贝函数
fun <T : R, R> copyData1(source: MutableList<T>,        //来源的元素类型应该是目标元素类型的子类型
                         destination: MutableList<R>) {
    for (item in source) {
        destination.add(item)
    }
}

//带out投影类型参数的数据拷贝函数
fun <T> copyData2(source: MutableList<out T>,       //可以给类型的用法加上out关键字：没有使用那些T用在in位置的方法
                  destination: MutableList<T>) {
    for (item in source) {
        destination.add(item)
    }
}

//带in投影类型参数的数据拷贝函数
fun <T> copyData3(source: MutableList<T>,
                  destination: MutableList<in T>) {
    for (item in source) {
        destination.add(item)
    }
}

//星号投影：使用*代替类型参数
fun star() {
    val list: MutableList<Any?> = mutableListOf('a', 1, "qwe")
    val chars = mutableListOf('a', 'b', 'c')
    val unknownElements: MutableList<*> =                        //MutableList<*>和MutableList<Anu?>不一样
            if (Random().nextBoolean()) list else chars
//    unknownElements.add(42)         //编译器禁止调用这个方法
    p(unknownElements.first())        //读取元素是安全的：first()返回一个类型为Any？的元素
}

//当类型实参的信息并不重要的时候，可以使用星号投影的语法：不需要使用任何在签名中引用类型参数的方法，或者只是读取数据
//而不关心它的具体类型。例如：可以实现一个接收List<*>做参数的printFirst函数：
fun printFirst(list: List<*>) {         //每一种列表都是可能的实参
    if (list.isNotEmpty()) {            //isNotEmpty()没有使用泛型类型参数
        p(list.first())                 //first()现在返回的是Any?，但是这里足够了
    }
}

//输入验证的接口
interface FieldValidator<in T> {        //接口定义在T上的逆变
    fun validate(input: T): Boolean     //T只在in位置使用(这个方法只是消费T的值)
}

object DefaultStringValidator : FieldValidator<String> {
    override fun validate(input: String) = input.isNotEmpty()
}

object DefaultIntValidator : FieldValidator<Int> {
    override fun validate(input: Int) = input >= 0
}

fun testValidator() {
    //把所有的验证器都存储到同一个容器中，并根据输入的类型来选出正确的验证器
    val validators = mutableMapOf<KClass<*>, FieldValidator<*>>()
    validators[String::class] = DefaultStringValidator
    validators[Int::class] = DefaultIntValidator

    //错误：编译器不知道存储的是哪种类型，因为存储在map中的值的类型是FieldValidator<*>
//    validators[String::class]!!.validate("")
    //使用显示的转换获取验证器(不安全，不推荐，警告：未受检的转换)
    val stringValidator = validators[String::class] as FieldValidator<String>
    val stringValidator1 = validators[Int::class] as FieldValidator<String> //得到了一个错误的验证器，但代码可以编译
//    p(stringValidator.validate(""))
    p(stringValidator1.validate(""))                //直到使用验证器时才发现真正的错误

}

object Validators{
    private val validators = mutableMapOf<KClass<*>, FieldValidator<*>>()

    fun <T : Any> registerValidator(
            kClass: KClass<T>, fieldValidator: FieldValidator<T>) {
        validators[kClass] = fieldValidator
    }

    @Suppress("UNCHECKED_CAST")         //禁止关于未受检的转换到FieldValidator<T>的警告
    operator fun <T : Any> get(kClass: KClass<T>): FieldValidator<T> =
            validators[kClass] as? FieldValidator<T>
    ?:throw IllegalArgumentException(
            "No validator for ${kClass.simpleName}"
    )
}

fun main(args: Array<String>) {
    Validators.registerValidator(String::class,DefaultStringValidator)
    Validators.registerValidator(Int::class,DefaultIntValidator)
    p(Validators[String::class].validate("Kotlin"))
    p(Validators[Int::class].validate(42))
//    p(Validators[String::class].validate(42))

//    enumerateCats(Animal::getIndex)     //在Kotlin中这段代码是合法的。Animal是Cat的超类型，而Int是Number的子类型

//    printContents(listOf("abc","bac"))      //完全安全

//    val serviceImpl = ServiceLoader.load(Service::class.java)
//    val serviceImpl = loadService<Service>()
    //使用标准库函数filterIsInstance

//    val items = listOf("one", 2, "three")
//    p(items.filterIsInstance<String>())

//    p(isA<String>("abc"))
//    p(isA<String>(123))

//    p(printSum1(listOf(1,2,3)))
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