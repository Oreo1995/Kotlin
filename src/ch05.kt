import java.io.File
import java.lang.StringBuilder
import kotlin.math.max

data class Person6(val name: String, val age: Int)

fun findTheOldest(people: List<Person6>) {
    var maxAge = 0
    var theOldest: Person6? = null
    for (person in people) {
        if (person.age > maxAge) {
            maxAge = person.age
            theOldest = person
        }
    }
    p(theOldest)
}

fun printMessageWithPrefix(messages: Collection<String>, prefix: String) {
    messages.forEach {
        println("$prefix $it")
    }
}

fun printProblemCounts(responses: Collection<String>) {
    var clientErrors = 0
    var serverErrors = 0
    responses.forEach {
        if (it.startsWith("4"))
            clientErrors++
        else if (it.startsWith("5"))
            serverErrors++
    }
    p("$clientErrors client errors, $serverErrors server errors")
}

class Button3() {
    var clicks = 0
    var listener: OnClickListener? = null
    fun onClick(listener: OnClickListener) {
        this.listener = listener
    }

    fun onClick2(): Int {
        return 0
    }

}

interface OnClickListener {
    fun click()
}

fun tryToCountButtonClicks(button: Button3): Int {
    var clickst = 0

//    button.onClick {}

//    button.onClick(object : OnClickListener {
//        override fun click() {
//            clickst++
//        }
//    })
//    (object:OnClickListener{
//        override fun click() {
//            clickst++
//        }
//    })
//    button.onClick()
    return button.clicks
}

fun salute() = println("Salute")

fun sendEmail(person: Person6, message: String) = println("${person.name}, $message")

class Book(val title: String, val authors: List<String>)

fun File.isInsideHiddenDirectory() =
        generateSequence(this) { it.parentFile }.any { it.isHidden }

fun createAllDoneRunnable(): Runnable {
    return Runnable { p("All Done!") }
}

fun alphabet(): String {
    val result = StringBuilder()
    for (letter in 'A'..'Z') {
        result.append(letter)
    }
    result.append("\nNow I know the alphabet!")
    return result.toString()
}

fun alphabet1(): String {
    val result = StringBuilder()

    //in these codes,this can not be write
    return with(result) {
        for (letter in 'A'..'Z') {
            this.append(letter)
        }
        append("\nNow I know the alphabet!1")
        this.toString()
    }
}

fun alphabet2() = with(StringBuilder()) {
    for (letter in 'A'..'Z') {
        append(letter)
    }
    append("\nNow I know the alphabet2!")
    toString()
}

fun alphabet3() = StringBuilder().apply {
    for (letter in 'A'..'Z') {
        append(letter)
    }
    append("\nNow I know the alphabet!3")
}.toString()


//this function can create a StringBuilder and invoke its toString
fun alphabet4() = buildString {
    for (letter in 'A'..'Z') {
        append(letter)
    }
    append("\nNow I know the alphabet!4")
}

fun main(args: Array<String>) {
//    createAllDoneRunnable().run()
//    p(alphabet())
    p(alphabet1())
    p(alphabet2())
    p(alphabet3())
    p(alphabet4())
//    val file = File("/H:test/.HiddenDir/a.txt")
//    p(file.isInsideHiddenDirectory())

//    val people = listOf(Person6("Alice", 29), Person6("Bob", 31),
//            Person6("Charles", 31), Person6("Dan", 21))
//    p(people.asSequence().map(Person6::name).filter { it.length < 4 }.toList())
//    p(people.asSequence().filter { it.name.length < 4 }.map(Person6::name).toList())   //first to use filter to change less
//
//    val naturalNumbers = generateSequence(0) { it + 1 }
//    val numbersTo100 = naturalNumbers.takeWhile { it <= 100 }
//    p(numbersTo100.sum())

//    listOf(1, 2, 3, 4).asSequence()
//            .map { print("map($it) ");it * it }
//            .filter { p("filter($it) ");it % 2 == 0 }
//            .toList()       //if toList not exist,console will print nothing,because the Sequence cause map and filter lazy
//
//    p(listOf(1, 2, 3, 4).asSequence().map { it * it }.find { it > 3 })

//    val books = listOf(Book("Thursday Next", listOf("Jasper Fforde")),
//            Book("Mort", listOf("Terry Pratchett")),
//            Book("Good Omens", listOf("Terry Pratchett", "Neil Gaiman")))
//    p(books.flatMap { it.authors }.toSet())

//    val strings = listOf("abc","def")
//    p(strings.flatMap { it.toList() })

//    val list = listOf(1, 2, 3, 4)
//    p(list.filter { it % 2 == 0 })
//    p(list.map { it * it })

//    val list = listOf("a", "ab", "b")
//    p(list.groupBy { it.first() })      //{a=[a,ab],b=[b]
//    p(list.groupBy(String::first))      //{a=[a,ab],b=[b]
//
//    val people = listOf(Person6("Alice", 29), Person6("Bob", 31))
//
//    p(people.map(Person6::name).filter { it.startsWith("A") })
//    p(people.asSequence()                   //Serial lazy
//            .map(Person6::name)
//            .filter { it.startsWith("A") }
//            .toList())


//    p(people.groupBy { it.age })        //return a map, key to value
//    p(people.filter { it.age > 30 })
//    p(people.map { it.name })
//    people.forEach{
//        p(it.name)
//    }

//    p(people.map(Person6::name))
//    p(people.filter { it.age > 30 }.map(Person6::name))
//    p(people.filter { it.age == people.maxBy(Person6::age)?.age })        //if have 100 peoples,run 100
//    val maxAge = people.maxBy(Person6::age)!!.age
//    p(people.filter { it.age == maxAge })
//
//    val numbers = mapOf(0 to "zero", 1 to "one")
//    p(numbers.mapValues { it.value.toUpperCase() })
//
//    val people2 = listOf(Person6("Alice", 27), Person6("Bob", 31))
//    val canBeInClub27 = { p: Person6 -> p.age <= 27 }
//    p(people2.all(canBeInClub27))       //false -> all
//    p(people2.any(canBeInClub27))       //true -> any
//    p(people2.count(canBeInClub27))     //1 -> count
//    p(people2.find(canBeInClub27))      //Person(name=Alice,age-27) ->  find
//    p(people2.filter(canBeInClub27).size)       //1 -> filter.size
//
//    val list2 = listOf(1, 2, 3)
//    p(list2.all { it == 3 })
//    p(list2.any { it == 3 })
//    p(!list2.all { it == 3 })
//    p(list2.any { it != 3 })

//    var counters = 0
//    val inc = {++counters}
//    p(inc())

//    val p = Person6("Dmitry",34)
//    val personAgeFunction = Person6::age
//    p(personAgeFunction(p))
//
//    val dmitrysAgeFunction = p::age
//    p(dmitrysAgeFunction())

//    val createPerson = ::Person6
//    val p = createPerson("Hong",19)
//    p(p)
//
//    fun Person6.isAdult() = age >= 21
//    val predicate = Person6::isAdult
//    p(predicate(p))

//    val action = {person:Person6,message:String ->
//        sendEmail(person,message)
//    }
//
//    val nextAction = ::sendEmail

//    action(Person6("Wang",19),"Welcome to HongKong!")
//    nextAction(Person6("Ruan",20),"Hello my wife")

//    val getAge = {person:Person6 -> person.age }
//    p(getAge(Person6("haha",6)))

//    run (::salute)

//    val bt = Button3()
//    p(tryToCountButtonClicks(bt))
//    val response = listOf<String>("200 OK", "418 I'm a teapot",
//            "500 Internal Server Error")
//    printProblemCounts(response)
//
//    val errors = listOf("403 Forbidden", "404 Not Found")
//    printMessageWithPrefix(errors, "Error: ")

//    val sum = { x: Int, y: Int ->
//        print("Computing the sum of $x and $y...")
//        x + y
//    }
//    println(sum(1,5))

//    val sum = { x: Int, y: Int -> x + y }
//    p(sum(1, 2))
//    { println(42) }()
//    run { println(42) }

//    val people = listOf(Person6("Alice", 29), Person6("Bob", 31))
//    val names1 = people.joinToString(separator = " ", transform = { p: Person6 -> p.name })
//    val names2 = people.joinToString(" ") { p: Person6 -> p.name }
//    p(names2)
//    findTheOldest(people)
//    p(people.maxBy { it.age })
//    p(people.maxBy { p: Person6 -> p.age })
//    p(people.maxBy(Person6::age))
//    p(people.maxBy { it.age })
//    p(people.maxBy{ it.age })
//    people.maxBy { p: Person6 -> p.age }
//    people.maxBy { p -> p.age }
//    val getAge = { p: Person6 -> p.age }
//    people.maxBy(getAge)
}