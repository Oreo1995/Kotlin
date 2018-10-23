import java.io.File
import javax.naming.Context
import javax.swing.text.AttributeSet

interface Clickable {
    fun click()
    fun showOff() = println("I'm clickable")
}

interface Focusable {
    fun setFocus(b: Boolean) =
            println("I ${if (b) "got" else "lost"} focus")

    fun showOff() = println("I'm focusable")
}

class Button : Clickable, Focusable {
    override fun click() {
        println("I was clicked")
    }

    override fun showOff() {
        super<Clickable>.showOff()
        super<Focusable>.showOff()
    }
}

open class RichButton : Clickable {         //open can inherit
    fun disable() {}                         //final,can't be override
    open fun animate() {}                   //open,can be override
    //    override fun click() {}                 //open,can be override
    final override fun click() {}           //not delete final,because it is open that not have final
    //final,can't be override
}

abstract class Animated {            //this class is abstract,can not instance it
    abstract fun animate()          //abstract class' abstract fun is open default,and it must be override
    open fun stopAnimating() {}      //abstract class's abstractness fun is not default open
    fun animateTwice() {}
}

internal open class TalkativeButton : Focusable {
    private fun yell() = println("Hey!")
    protected fun whisper() = println("Let's talk!")
}

//fun TalkativeButton.giveSpeech() {
//    yell()
//    whisper()
//}

class Outer {
    inner class Inner {
        fun getOuterReference(): Outer = this@Outer
    }
}

sealed class Expr1 {
    class Num1(val value: Int) : Expr1()        //kotlin 1.0
    class Sum1(val left: Expr1, val right: Expr1) : Expr1()
}

//class Num1(val value: Int) : Expr1()      //kotlin 1.1
//class Sum1(val left: Expr1, val right: Expr1) : Expr1()

fun eval(e: Expr1): Int =
        when (e) {
            is Expr1.Num1 -> e.value
            is Expr1.Sum1 -> eval(e.left) + eval(e.right)
        }

open class User1(val nickname: String)

class User2(_nickname: String) {
    val nickname: String

    init {
        nickname = _nickname
    }
}

class TwitterUser(nickname: String) : User1(nickname) {
    //
}

open class Button2

class RadioButton : Button2()

class Secretive private constructor() {}     //cannot be instance

open class View1 {
    constructor(ctx: Context) {}
    constructor(ctx: Context, attr: AttributeSet) {}
}

class MyButton : View1 {
    constructor(ctx: Context) : super(ctx) {
        //
    }

    constructor(ctx: Context, attr: AttributeSet)
            : super(ctx, attr) {
        //
    }
}

//class MyButton1 : View1 {
//    val attr: AttributeSet? = null
//
//    constructor(ctx: Context) : this(ctx, attr) {     1->2
//        //
//    }
//
//    constructor(ctx: Context, attr: AttributeSet)
//            : super(ctx, attr) {
//        //
//    }
//}

interface User3 {
    val nickname: String
}

class PrivateUser(override val nickname: String) : User3

class SubscribingUser(val email: String) : User3 {
    override val nickname: String
        get() = email.substringBefore("@")
}

class FaceBookUser(val accountId: Int) : User3 {
    override val nickname = getFaceBookName(accountId)
}

class User4(val name: String) {
    var address: String = "unspecified"
        set(value: String) {
            println("""
            Address was changed for $name:
            "$field" -> "$value".""".trimIndent())
            field = value
        }
}

class LengthCounter {
    var counter: Int = 0
        private set

    fun addWord(word: String) {
        counter += word.length
    }
}

class Client(val name: String, val postalCode: Int) {
    override fun toString() = "Client (name=$name, postalCode=$postalCode)"
    override fun equals(other: Any?): Boolean {
        if (other == null || other !is Client)
            return false
        return name == other.name && postalCode == other.postalCode
    }

    override fun hashCode(): Int = name.hashCode() * 31 + postalCode
}

data class Client1(val name: String, val postalCode: Int)

class DelegatingCollection<T> : Collection<T> {
    private val innerList = arrayListOf<T>()

    override val size: Int get() = innerList.size
    override fun isEmpty(): Boolean = innerList.isEmpty()
    override fun iterator(): Iterator<T> = innerList.iterator()
    override fun contains(element: T): Boolean = innerList.contains(element)
    override fun containsAll(elements: Collection<T>): Boolean =
            innerList.containsAll(elements)
}

class DelegatingCollection2<T>(
        innerList: Collection<T> = ArrayList<T>()
) : Collection<T> by innerList

class CountingSet<T>(
        val innerSet: MutableCollection<T> = HashSet<T>()
) : MutableCollection<T> by innerSet {
    var objectAdded = 0

    override fun add(element: T): Boolean {
        objectAdded++
        return innerSet.add(element)
    }

    override fun addAll(elements: Collection<T>): Boolean {
        objectAdded += elements.size
        return innerSet.addAll(elements)
    }
}

class Person1(val name: String, val sex: Int)

object Payroll {
    val allEmployees = arrayListOf<Person1>()

    fun calculateSalary() {
        for (Person in allEmployees) {

        }
    }
}

object CaseInsensitiveFileComparator : Comparator<File> {
    override fun compare(file1: File, file2: File): Int {
        return file1.path.compareTo(file2.path, ignoreCase = true)
    }
}

data class Person2(val name: String) {
    object NameComparator : Comparator<Person2> {
        override fun compare(p1: Person2, p2: Person2): Int =
                p1.name.compareTo(p2.name)

    }
}

class User5 {
    val nickname: String

    constructor(email: String) {
        nickname = email.substringBefore("@")
    }

    constructor(facebookAccountId: Int) {
        nickname = "0"
        getFaceBookName(facebookAccountId)
    }
}

fun getFaceBookName(facebookAccountId: Int) = "${facebookAccountId + 1}"

class User6 private constructor(val name: String) {
    companion object {
        fun newSubscribingUser(email: String) =
                User6(email.substringBefore("@"))

        fun newFaceBookUser(accountId: Int) =
                User6(getFaceBookName(accountId))

    }
}

class A {
    companion object {
        fun bar() {
            p("Companion object called")
        }
    }
}

class Person3(val name: String) {
    companion object Loader {
        fun fromJson(jsonText: String): Person2 = Person2(jsonText.substring(2))
    }
}

interface JsonFactory<T> {
    fun fromJson(jsonText: String): T
}

class Person4(val name: String) {
    companion object : JsonFactory<Person4> {
        override fun fromJson(jsonText: String): Person4 {
            return Person4(jsonText)
        }
    }
}

//fun loadFromJson<T>(factory: JsonFactory<T>): T {
//
//}

//business logic module
class Person5(val firstName: String, val lastName: String) {
    companion object {

    }
}

//client/server communication module
fun Person5.Companion.fromJson(json: String): Person5 = Person5(json, json.substring(1, 3))

fun main(args: Array<String>) {


//    val p = Person5.Companion.fromJson("12357")
//    p(p.firstName)
//    p(p.lastName)

//    loadFromJson(Person4)
//    p(Person3.fromJson("kangkang").name)
//    p(Person3.Loader.fromJson("WangSF").name)

//    A.bar()

//    val subscribingUser = User6.newSubscribingUser("bob@gmail.com")
//    val faceBookUser = User6.newFaceBookUser(10010)
//    p(subscribingUser.name)
//    p(faceBookUser.name)

//    val person1 = User5("Alice")
//    val person2 = User5(10010)

//    val persons = listOf<Person2>(Person2("Bob"),Person2("Alice"))
//    p(persons.sortedWith(Person2.NameComparator))

//    val files = listOf<File>(File("/Z"), File("/a"))
//    p(files.sortedWith(CaseInsensitiveFileComparator))
//    p(CaseInsensitiveFileComparator.compare(File("/User"), File("/user")))

//    Payroll.allEmployees.add(Person1("ly",1))
//    Payroll.calculateSalary()

//    val cset = CountingSet<Int>()
//    cset.addAll(listOf(1,2,3,4,5))
//    println("${cset.objectAdded} objects were added, ${cset.size} remain")

//    val client1 = Client("Alice", 342562)
//    val client2 = Client("Alice", 342562)

//    val client1 = Client1("Alice", 342562)
//    val client2 = Client1("Alice", 342562)
//    p(client1)
//    p(client1.equals(client2))
//    p(client1 == client2)
//    val processed = hashSetOf(client1)
//    p(processed.contains(client1))
//    p(processed.contains(client2))
//    p(client1.hashCode())
//    p(client2.hashCode())

//    val lengthCounter = LengthCounter()
//    lengthCounter.addWord("Hello World!")
//    p(lengthCounter.counter)

//    val user = User4("Alice")
//    user.address = "Elsenheimerstrasse 47, 80687 Muenchen"

//    p(PrivateUser("test@kotlinlang.org").nickname)
//    p(SubscribingUser("test@kotlinlang.org").nickname)

//    object:Clickable{
//        override fun click() {
//            println("Hello World")
//        }
//    }.click()

//    val button = Button()
//    button.click()
//    button.setFocus(true)
//    button.showOff()
}