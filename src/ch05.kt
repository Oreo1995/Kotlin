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

fun main(args: Array<String>) {
    val errors = listOf("403 Forbidden", "404 Not Found")
    printMessageWithPrefix(errors, "Error: ")

//    val sum = { x: Int, y: Int ->
//        println("Computing the sum of $x and $y...")
//        x + y
//    }
//    println(sum(1,5))

//    val sum = { x: Int, y: Int -> x + y }
//    p(sum(1, 2))
//    { println(42) }()
//    run { println(42) }

//    val people = listOf(Person6("Alice", 29), Person6("Bob", 31))
//    val names = people.joinToString(separator = " ", transform = { p: Person6 -> p.name })
//    val names = people.joinToString(" ") { p: Person6 -> p.name }
//    p(names)
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