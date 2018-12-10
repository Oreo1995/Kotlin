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

fun main(args: Array<String>) {
    p(listOf(1,2,3,4).penultimate)

//    val letters = ('a'..'z').toList()
//    p(letters.slice<Char>(0..2))        //显式地指定类型实参
//    p(letters.slice(10..13))        //编译器推导出这里的T是Char（与上作用一样）

//    val list = listOf(1,2,3,4)
//    p(list.slice(2..3))
}