import java.lang.StringBuilder

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

fun main(args: Array<String>) {
//    twoAndThree(sum)
//    twoAndThree { a, b -> a + b }
//    twoAndThree { a, b -> a * b }

    p("ab1c".filter { it in 'a'..'z' })     //传递一个lambda作为"predicate"参数

//    val url = "http://kotl.in"
//    performRequest(url) { code, content -> /*...*/ }    //可以使用API中提供的参数名字作为lambda参数的名字。。。
//    performRequest(url) { code, page -> /*...*/ }       //。。。或者你可以改变参数的名字
}

